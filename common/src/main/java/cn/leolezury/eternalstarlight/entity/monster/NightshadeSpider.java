package cn.leolezury.eternalstarlight.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class NightshadeSpider extends Spider {
    public NightshadeSpider(EntityType<? extends NightshadeSpider> p_32254_, Level p_32255_) {
        super(p_32254_, p_32255_);
    }

    public static AttributeSupplier.Builder createNightshadeSpider() {
        return Spider.createAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }

    public boolean doHurtTarget(Entity p_32257_) {
        if (super.doHurtTarget(p_32257_)) {
            if (p_32257_ instanceof LivingEntity) {
                int i = 0;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 0) {
                    ((LivingEntity)p_32257_).addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
                    ((LivingEntity)p_32257_).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, i * 20, 2), this);
                    if (!level().isClientSide) {
                        if (level().getBlockState(new BlockPos((int) p_32257_.getX(), (int) p_32257_.getY(), (int) p_32257_.getZ())).isAir()) {
                            level().setBlockAndUpdate(new BlockPos((int) p_32257_.getX(), (int) p_32257_.getY(), (int) p_32257_.getZ()), Blocks.COBWEB.defaultBlockState());
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_32259_, DifficultyInstance p_32260_, MobSpawnType p_32261_, @Nullable SpawnGroupData p_32262_, @Nullable CompoundTag p_32263_) {
        return p_32262_;
    }

    protected float getStandingEyeHeight(Pose p_32265_, EntityDimensions p_32266_) {
        return 0.45F;
    }
}
