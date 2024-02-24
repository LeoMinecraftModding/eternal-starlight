package cn.leolezury.eternalstarlight.common.entity.animal;

import cn.leolezury.eternalstarlight.common.entity.ai.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class CrystallizedMoth extends Animal implements FlyingAnimal {
    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(CrystallizedMoth.class, EntityDataSerializers.INT);
    public int getAttackTicks() {
        return entityData.get(ATTACK_TICKS);
    }
    public void setAttackTicks(int attackTicks) {
        entityData.set(ATTACK_TICKS, attackTicks);
    }

    public CrystallizedMoth(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public AnimationState idleAnimationState = new AnimationState();

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos blockPos) {
                return super.isStableDestination(blockPos) && level.getBlockState(blockPos.below()).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_TICKS, 0);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new LookAtTargetGoal(this));
        goalSelector.addGoal(2, new CrystallizedMothAttackGoal());
        goalSelector.addGoal(3, new CrystallizedMothRandomMoveGoal());
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));

        targetSelector.addGoal(0, new HurtByTargetGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Bat.class, false));
    }

    private class CrystallizedMothRandomMoveGoal extends Goal {
        public CrystallizedMothRandomMoveGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }
        
        public boolean canUse() {
            return CrystallizedMoth.this.navigation.isDone() && CrystallizedMoth.this.random.nextInt(5) == 0;
        }
        
        public boolean canContinueToUse() {
            return CrystallizedMoth.this.navigation.isInProgress();
        }

        public void start() {
            Vec3 target = this.getRandomPos();
            if (target != null) {
                CrystallizedMoth.this.navigation.moveTo(target.x, target.y, target.z, 1d);
            }
        }

        @Nullable
        private Vec3 getRandomPos() {
            Vec3 vec3 = CrystallizedMoth.this.getViewVector(0.0F);
            Vec3 vec32 = HoverRandomPos.getPos(CrystallizedMoth.this, 8, 7, vec3.x, vec3.z, Mth.HALF_PI, 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(CrystallizedMoth.this, 8, 4, -2, vec3.x, vec3.z, Mth.HALF_PI);
        }
    }

    private class CrystallizedMothAttackGoal extends Goal {
        public CrystallizedMothAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return CrystallizedMoth.this.getTarget() != null && CrystallizedMoth.this.getAttackTicks() == 0 && CrystallizedMoth.this.random.nextInt(100) == 0;
        }

        public boolean canContinueToUse() {
            return CrystallizedMoth.this.getAttackTicks() < 100;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            CrystallizedMoth.this.setAttackTicks(CrystallizedMoth.this.getAttackTicks() + 1);
            if (CrystallizedMoth.this.getTarget() != null) {
                LivingEntity target = CrystallizedMoth.this.getTarget();
                Vec3 selfPos = CrystallizedMoth.this.position().add(0, CrystallizedMoth.this.getBbHeight() / 2f, 0);
                Vec3 pos = target.position().add(0, target.getBbHeight() / 2f, 0);
                if (CrystallizedMoth.this.hasLineOfSight(target)) {
                    CrystallizedMoth.this.doHurtTarget(target);
                }
                if (CrystallizedMoth.this.level() instanceof ServerLevel serverLevel && CrystallizedMoth.this.getAttackTicks() % 5 == 0) {
                    Vec3 delta = pos.subtract(selfPos).normalize().scale(0.8);
                    ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ESParticlePacket(ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), selfPos.x, selfPos.y, selfPos.z, delta.x, delta.y, delta.z));
                }
            }
        }

        @Override
        public void stop() {
            CrystallizedMoth.this.setAttackTicks(0);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.ATTACK_DAMAGE, 1.5).add(Attributes.FOLLOW_RANGE, 50).add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FLYING_SPEED, 1);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide) {
            idleAnimationState.startIfStopped(tickCount);
        }
    }

    @Override
    public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    public static boolean checkMothSpawnRules(EntityType<? extends CrystallizedMoth> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.canSeeSky(pos) && pos.getY() < 60;
    }
}
