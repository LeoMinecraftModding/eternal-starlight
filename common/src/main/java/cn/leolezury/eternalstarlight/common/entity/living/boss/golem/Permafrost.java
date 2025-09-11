package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
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
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class Permafrost extends ESBoss {
	public Permafrost(EntityType<? extends Permafrost> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new FlyingMoveControl(this, 5, true);
	}

	private final BehaviorManager<Permafrost> behaviorManager = new BehaviorManager<>(this, List.of(
		new PermafrostMeleePhase(),
		new PermafrostMeleeTransitionPhase(),
		new PermafrostMeleeEndPhase(),
		new PermafrostRangedPhase()
	));

	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState meleeAnimationState = new AnimationState();
	public AnimationState meleeTransitionAnimationState = new AnimationState();
	public AnimationState meleeEndAnimationState = new AnimationState();
	public AnimationState rangedAnimationState = new AnimationState();

	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
			@Override
			public boolean isStableDestination(BlockPos blockPos) {
				return this.level.getBlockState(blockPos).isAir();
			}
		};
		navigation.setCanOpenDoors(true);
		navigation.setCanFloat(true);
		navigation.setCanPassDoors(true);
		return navigation;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new PermafrostFlyToTargetGoal());
		goalSelector.addGoal(2, new RandomFlyGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && Permafrost.this.getTarget() == null && Permafrost.this.getBehaviorState() == 0;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && Permafrost.this.getTarget() == null && Permafrost.this.getBehaviorState() == 0;
			}
		});
		goalSelector.addGoal(3, new LookAtTargetGoal(this));
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0F));

		targetSelector.addGoal(0, new HurtByTargetGoal(this, Permafrost.class).setAlertOthers());
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.permafrost.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.permafrost.armor())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.permafrost.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.FLYING_SPEED, 0.8)
			.add(Attributes.ATTACK_DAMAGE, 12)
			.add(Attributes.KNOCKBACK_RESISTANCE, 0.75);
	}

	public class PermafrostFlyToTargetGoal extends Goal {
		public PermafrostFlyToTargetGoal() {
			setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return Permafrost.this.getBehaviorState() == 0 && Permafrost.this.getTarget() != null;
		}

		@Override
		public boolean canContinueToUse() {
			return Permafrost.this.getBehaviorState() == 0 && Permafrost.this.getTarget() != null;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			LivingEntity entity = Permafrost.this.getTarget();
			if (entity != null) {
				Vec3 target = entity.position();
				BlockHitResult result = Permafrost.this.level().clip(new ClipContext(target.add(0, entity.getBbHeight() / 2, 0), target.add(0, entity.getBbHeight() / 2 + Permafrost.this.getBbHeight(), 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, Permafrost.this));
				if (result.getType() != HitResult.Type.MISS) {
					target = result.getLocation();
				} else {
					target = target.add(0, entity.getBbHeight() / 2 + Permafrost.this.getBbHeight(), 0);
				}
				Permafrost.this.getNavigation().moveTo(target.x, target.y - Permafrost.this.getBbHeight(), target.z, 1);
			}
		}
	}

	public void stopAllAnimStates() {
		meleeAnimationState.stop();
		meleeTransitionAnimationState.stop();
		meleeEndAnimationState.stop();
		rangedAnimationState.stop();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (accessor.equals(BEHAVIOR_STATE) && getBehaviorState() != 0) {
			stopAllAnimStates();
			switch (getBehaviorState()) {
				case PermafrostMeleePhase.ID -> meleeAnimationState.start(tickCount);
				case PermafrostMeleeTransitionPhase.ID -> meleeTransitionAnimationState.start(tickCount);
				case PermafrostMeleeEndPhase.ID -> meleeEndAnimationState.start(tickCount);
				case PermafrostRangedPhase.ID -> rangedAnimationState.start(tickCount);
			}
		}
		super.onSyncedDataUpdated(accessor);
	}

	@Override
	public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
		return false;
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.STARLIGHT_GOLEM_ALLYS);
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!level().isClientSide) {
			if (getTarget() != null && !getTarget().isAlive()) {
				setTarget(null);
			}
			if (!isNoAi() && isAlive()) {
				behaviorManager.tick();
			}
			if (getBehaviorState() == 0) {
				LivingEntity target = getTarget();
				if (target != null && level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(1)).contains(getTarget())) {
					hurtMarked = true;
					addDeltaMovement(position().subtract(target.position()).multiply(1, 0, 1).normalize().scale(0.1));
				}
			}
		} else {
			idleAnimationState.startIfStopped(tickCount);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean success = super.doHurtTarget(entity);
		if (success && entity.canFreeze()) {
			entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 150, 300));
		}
		return success;
	}

	@Override
	public boolean shouldPlayBossMusic() {
		return false;
	}

	public static boolean checkPermafrostSpawnRules(EntityType<? extends Permafrost> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.permafrost.canSpawn();
	}
}
