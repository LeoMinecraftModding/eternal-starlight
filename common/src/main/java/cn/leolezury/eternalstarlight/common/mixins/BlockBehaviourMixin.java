package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.data.DamageTypeInit;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
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

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {
    @Inject(method = "entityInside", at = @At(value = "HEAD"))
    public void es_entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (blockState.is(BlockInit.ETHER.get())) {
            if (entity instanceof LivingEntity livingEntity) {
                CompoundTag compoundTag = ESUtil.getPersistentData(livingEntity);
                int inEtherTicks = compoundTag.getInt("InEtherTicks");
                compoundTag.putInt("InEtherTicks", inEtherTicks + 1);
                AttributeInstance armorInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
                if (armorInstance != null && armorInstance.getValue() <= 0) {
                    if (entity.hurt(DamageTypeInit.getDamageSource(level, DamageTypeInit.ETHER), 1) && level instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 5; i++) {
                            serverLevel.sendParticles(ParticleInit.STARLIGHT.get(), entity.getX() + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), entity.getY() + entity.getBbHeight() / 2d + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbHeight(), entity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), 20, 0.1, 0.1, 0.1, 0);
                        }
                    }
                }
            } else {
                entity.hurt(DamageTypeInit.getDamageSource(level, DamageTypeInit.ETHER), 1);
            }
        }
    }
}
