package cn.leolezury.eternalstarlight.common.entity.living.goal;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

// [Vanilla copy] MeleeAttackGoal, melee attack and dead path penalty branches removed
public class MoveToTargetGoal extends Goal {
	protected final PathfinderMob mob;
	private final double speedModifier;
	private final boolean followingTargetEvenIfNotSeen;
	private Path path;
	private double pathedTargetX;
	private double pathedTargetY;
	private double pathedTargetZ;
	private int ticksUntilNextPathRecalculation;
	private long lastCanUseCheck;
	private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;

	public MoveToTargetGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		long gameTime = this.mob.level().getGameTime();
		if (gameTime - this.lastCanUseCheck < COOLDOWN_BETWEEN_CAN_USE_CHECKS) {
			return false;
		}
		this.lastCanUseCheck = gameTime;
		LivingEntity target = this.mob.getTarget();
		if (target == null || !target.isAlive()) {
			return false;
		}
		this.path = this.mob.getNavigation().createPath(target, 0);
		return this.path != null;
	}

	@Override
	public boolean canContinueToUse() {
		LivingEntity target = this.mob.getTarget();
		if (target == null || !target.isAlive()) {
			return false;
		}
		if (!this.followingTargetEvenIfNotSeen) {
			return !this.mob.getNavigation().isDone();
		}
		return this.mob.isWithinRestriction(target.blockPosition())
			&& (!(target instanceof Player) || !target.isSpectator() && !((Player) target).isCreative());
	}

	@Override
	public void start() {
		this.mob.getNavigation().moveTo(this.path, this.speedModifier);
		this.mob.setAggressive(true);
		this.ticksUntilNextPathRecalculation = 0;
	}

	@Override
	public void stop() {
		LivingEntity target = this.mob.getTarget();
		if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
			this.mob.setTarget(null);
		}
		this.mob.setAggressive(false);
		this.mob.getNavigation().stop();
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		LivingEntity target = this.mob.getTarget();
		if (target == null) {
			return;
		}
		this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
		this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
		if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(target))
			&& this.ticksUntilNextPathRecalculation <= 0
			&& (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
			|| target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
			|| this.mob.getRandom().nextFloat() < 0.05F)) {
			this.pathedTargetX = target.getX();
			this.pathedTargetY = target.getY();
			this.pathedTargetZ = target.getZ();
			this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
			double distSqr = this.mob.distanceToSqr(target);
			if (distSqr > 1024.0) {
				this.ticksUntilNextPathRecalculation += 10;
			} else if (distSqr > 256.0) {
				this.ticksUntilNextPathRecalculation += 5;
			}
			if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
				this.ticksUntilNextPathRecalculation += 15;
			}
			this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
		}
	}
}
