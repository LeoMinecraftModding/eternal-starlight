package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TwilightGaze extends WaterAnimal {
    private BlockPos abyssPos;

    public TwilightGaze(EntityType<? extends TwilightGaze> type, Level level) {
        super(type, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public AnimationState swimAnimationState = new AnimationState();

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        this.setAirSupply(this.getMaxAirSupply());
        this.setXRot(0.0F);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.addGoal(1, new TwilightGazeRandomSwimmingGoal(1.0, 10));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this, TwilightGaze.class).setAlertOthers());
    }
    
    private class TwilightGazeRandomSwimmingGoal extends RandomSwimmingGoal {
        public TwilightGazeRandomSwimmingGoal(double d, int i) {
            super(TwilightGaze.this, d, i);
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            Vec3 pos = super.getPosition();
            if (TwilightGaze.this.abyssPos == null) {
                return pos;
            }
            return pos == null ? null : (TwilightGaze.this.level().getBiome(BlockPos.containing(pos)).is(ESBiomes.THE_ABYSS) ? pos : TwilightGaze.this.abyssPos.getCenter());
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 1.2).add(Attributes.ATTACK_DAMAGE, 3.0);
    }

    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    public int getMaxHeadXRot() {
        return 1;
    }

    public int getMaxHeadYRot() {
        return 1;
    }

    protected boolean canRide(Entity entity) {
        return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide) {
            if (tickCount % 20 == 0 && level().getBiome(blockPosition()).is(ESBiomes.THE_ABYSS)) {
                abyssPos = blockPosition();
            }
        } else {
            swimAnimationState.startIfStopped(tickCount);
        }
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return ESSoundEvents.TWILIGHT_GAZE_HURT.get();
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return ESSoundEvents.TWILIGHT_GAZE_DEATH.get();
    }

    public void travel(Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(vec3);
        }
    }

    public boolean canBeLeashed(Player pPlayer) {
        return true;
    }
}

