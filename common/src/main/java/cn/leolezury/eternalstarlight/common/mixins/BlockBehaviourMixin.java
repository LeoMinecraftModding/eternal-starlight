package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// todo don't use mixin if mojang fixed the MapCodec<SomeBlock> problem
@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @Inject(method = "entityInside", at = @At(value = "HEAD"))
    public void es_entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (blockState.is(ESBlocks.ETHER.get())) {
            if (entity instanceof LivingEntity livingEntity) {
                AttributeInstance armorInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
                if (armorInstance != null && armorInstance.getValue() <= 0) {
                    if (entity.hurt(ESDamageTypes.getDamageSource(level, ESDamageTypes.ETHER), 1) && level instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 5; i++) {
                            serverLevel.sendParticles(ESParticles.STARLIGHT.get(), entity.getX() + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), entity.getY() + entity.getBbHeight() / 2d + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbHeight(), entity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), 20, 0.1, 0.1, 0.1, 0);
                        }
                    }
                }
                CompoundTag compoundTag = ESEntityUtil.getPersistentData(livingEntity);
                if (armorInstance == null || armorInstance.getValue() > 0) {
                    int inEtherTicks = compoundTag.getInt("InEtherTicks");
                    compoundTag.putInt("InEtherTicks", inEtherTicks + 1);
                }
                if (level.isClientSide) {
                    int clientEtherTicks = compoundTag.getInt("ClientEtherTicks");
                    if (clientEtherTicks < 140) {
                        compoundTag.putInt("ClientEtherTicks", clientEtherTicks + 1);
                    }
                }
            } else {
                entity.hurt(ESDamageTypes.getDamageSource(level, ESDamageTypes.ETHER), 1);
            }
        }
    }
}
