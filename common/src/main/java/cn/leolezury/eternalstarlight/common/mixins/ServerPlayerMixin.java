package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.item.magic.OrbOfProphecyItem;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Shadow public abstract ServerLevel serverLevel();

    @Inject(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;dropAllDeathLoot(Lnet/minecraft/world/damagesource/DamageSource;)V"))
    private void es_die(DamageSource damageSource, CallbackInfo ci) {
        Inventory inventory = ((Player) (Object) this).getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item.is(ESItems.ORB_OF_PROPHECY.get()) && !ESCrestUtil.getCrests((Player) (Object) this, "OwnedCrests").isEmpty()) {
                CompoundTag tag = ESEntityUtil.getPersistentData((Player) (Object) this).getCompound("OwnedCrests");
                OrbOfProphecyItem.recordCrests(serverLevel().registryAccess(), item, tag);
                return;
            }
        }
        ItemStack itemStack = ESItems.ORB_OF_PROPHECY.get().getDefaultInstance();
        if (!ESCrestUtil.getCrests((Player) (Object) this, "OwnedCrests").isEmpty()) {
            CompoundTag tag = ESEntityUtil.getPersistentData((Player) (Object) this).getCompound("OwnedCrests");
            OrbOfProphecyItem.recordCrests(serverLevel().registryAccess(), itemStack, tag);
            OrbOfProphecyItem.setTemporary(itemStack);
            if (!inventory.add(itemStack)) {
                ItemEntity itemEntity = new ItemEntity(serverLevel(), ((Player) (Object) this).getX(), ((Player) (Object) this).getY(), ((Player) (Object) this).getZ(), itemStack);
                serverLevel().addFreshEntity(itemEntity);
            }
        }
    }
}
