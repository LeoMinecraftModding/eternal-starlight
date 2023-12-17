package cn.leolezury.eternalstarlight.common.entity.animal;

import cn.leolezury.eternalstarlight.common.init.SoundEventInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LuminoFish extends AbstractSchoolingFish {
    public LuminoFish(EntityType<? extends AbstractSchoolingFish> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEventInit.LUMINO_FISH_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventInit.LUMINO_FISH_DEATH.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEventInit.LUMINO_FISH_FLOP.get();
    }

    @Override
    public ItemStack getBucketItemStack() {
        // todo: bucket item
        return null;
    }
}
