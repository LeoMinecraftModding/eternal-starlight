package cn.leolezury.eternalstarlight.item.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface TickableArmor {
    void tick(Level level, LivingEntity livingEntity, ItemStack armor);
}
