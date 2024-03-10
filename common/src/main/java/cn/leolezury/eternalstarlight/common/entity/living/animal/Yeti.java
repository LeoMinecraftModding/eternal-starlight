package cn.leolezury.eternalstarlight.common.entity.living.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Yeti extends Animal {
    protected static final EntityDataAccessor<Integer> ROLL_STATE = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.INT);
    public int getRollState() {
        return entityData.get(ROLL_STATE);
    }
    public void setRollState(int rollState) {
        entityData.set(ROLL_STATE, rollState);
    }

    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState rollStartAnimationState = new AnimationState();
    public AnimationState rollAnimationState = new AnimationState();
    public AnimationState rollEndAnimationState = new AnimationState();
    private int rollTicks = 0;
    public float rollAngle, prevRollAngle;

    public Yeti(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ROLL_STATE, 0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new StartRollGoal());
        this.goalSelector.addGoal(2, new StopRollGoal());
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (rollTicks > 0) {
            rollTicks--;
        }
        if (level().isClientSide) {
            idleAnimationState.startIfStopped(tickCount);
            prevRollAngle = rollAngle;
            rollAngle += (float) (position().subtract(new Vec3(xOld, yOld, zOld)).length() / (3f / 260f));
            rollAngle = rollAngle % 360;
        }
    }

    public void stopAllAnimStates() {
        rollStartAnimationState.stop();
        rollAnimationState.stop();
        rollEndAnimationState.stop();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (accessor.equals(ROLL_STATE) && getRollState() != 0) {
            stopAllAnimStates();
            switch (getRollState()) {
                case 1 -> {
                    rollAngle = 0;
                    prevRollAngle = 0;
                    rollStartAnimationState.start(tickCount);
                }
                case 2 -> rollAnimationState.start(tickCount);
                case 3 -> rollEndAnimationState.start(tickCount);
            }
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    public static boolean checkYetiSpawnRules(EntityType<? extends Yeti> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.DIRT);
    }

    class StartRollGoal extends Goal {
        private int rollStartTicks = 0;

        public StartRollGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return Yeti.this.getRollState() == 0 && !Yeti.this.getMoveControl().hasWanted() && Yeti.this.rollTicks == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return rollStartTicks <= 10;
        }

        @Override
        public void start() {
            Yeti.this.setRollState(1);
            Yeti.this.rollTicks = 400;
            rollStartTicks = 0;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            rollStartTicks++;
        }

        @Override
        public void stop() {
            Yeti.this.setRollState(2);
        }
    }

    class StopRollGoal extends Goal {
        private int rollEndTicks = 0;

        public StopRollGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return Yeti.this.getRollState() == 2 && !Yeti.this.getMoveControl().hasWanted() && Yeti.this.rollTicks == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return rollEndTicks <= 10;
        }

        @Override
        public void start() {
            Yeti.this.setRollState(3);
            rollEndTicks = 0;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            rollEndTicks++;
        }

        @Override
        public void stop() {
            Yeti.this.setRollState(0);
            rollTicks = 400;
        }
    }
}
