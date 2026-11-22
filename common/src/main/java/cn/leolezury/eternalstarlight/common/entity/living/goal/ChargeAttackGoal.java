package cn.leolezury.eternalstarlight.common.entity.living.goal;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ChargeAttackGoal extends Goal {
	private static final double MIN_SPEED = 0.01D;
	private static final int MAX_CLIFF_DROP = 3;
	private static final int STUCK_TICKS = 10;

	private final PathfinderMob mob;
	private final double speed;
	private final int windupTicks;
	private final float windupWalkAnimationSpeed;
	private final double maxChargeDistance;
	private final double overshoot;
	private final float attackRange;
	private final int cooldownTicks;
	private final boolean avoidDangerousBlocks;
	private final boolean avoidCliffs;
	private final int dashTicks;
	private final int decelTicks;

	@Nullable
	private Entity chargeTarget;
	private Vec3 chargeTargetPos = Vec3.ZERO;
	private Vec3 dashStart = Vec3.ZERO;
	private Vec3 dashDirection = Vec3.ZERO;
	private float dashYaw;
	private double dashDistance;
	private double dashSpeed;
	private int chargeTick;
	private int dashTick;
	private int cooldownTicksLeft;
	private int stuckTicks;
	private boolean dashing;
	private boolean finished;

	public ChargeAttackGoal(PathfinderMob mob, double speed, int windupTicks, float windupWalkAnimationSpeed, double maxChargeDistance, double overshoot, float attackRange) {
		this(mob, speed, windupTicks, windupWalkAnimationSpeed, maxChargeDistance, overshoot, attackRange, 0);
	}

	public ChargeAttackGoal(PathfinderMob mob, double speed, int windupTicks, float windupWalkAnimationSpeed, double maxChargeDistance, double overshoot, float attackRange, int cooldownTicks) {
		this(mob, speed, windupTicks, windupWalkAnimationSpeed, maxChargeDistance, overshoot, attackRange, cooldownTicks, false, false);
	}

	public ChargeAttackGoal(PathfinderMob mob, double speed, int windupTicks, float windupWalkAnimationSpeed, double maxChargeDistance, double overshoot, float attackRange, int cooldownTicks, boolean avoidDangerousBlocks, boolean avoidCliffs) {
		this(mob, speed, windupTicks, windupWalkAnimationSpeed, maxChargeDistance, overshoot, attackRange, cooldownTicks, avoidDangerousBlocks, avoidCliffs, -1, 0);
	}

	public ChargeAttackGoal(PathfinderMob mob, double speed, int windupTicks, float windupWalkAnimationSpeed, double maxChargeDistance, double overshoot, float attackRange, int cooldownTicks, boolean avoidDangerousBlocks, boolean avoidCliffs, int dashTicks, int decelTicks) {
		this.mob = mob;
		this.speed = Math.max(speed, MIN_SPEED);
		this.windupTicks = Math.max(0, windupTicks);
		this.windupWalkAnimationSpeed = windupWalkAnimationSpeed;
		this.maxChargeDistance = Math.max(0.0D, maxChargeDistance);
		this.overshoot = Math.max(0.0D, overshoot);
		this.attackRange = Math.max(0.0F, attackRange);
		this.cooldownTicks = Math.max(0, cooldownTicks);
		this.avoidDangerousBlocks = avoidDangerousBlocks;
		this.avoidCliffs = avoidCliffs;
		this.dashTicks = dashTicks < 0 ? -1 : Math.max(0, dashTicks);
		this.decelTicks = Math.max(0, decelTicks);
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canUse() {
		if (cooldownTicksLeft > 0) {
			cooldownTicksLeft--;
			return false;
		}
		Entity target = findTarget();
		if (target == null || isHazardAhead(directionTo(target))) {
			return false;
		}
		chargeTarget = target;
		chargeTargetPos = target.getBoundingBox().getCenter();
		return true;
	}

	@Override
	public boolean canContinueToUse() {
		if (finished) {
			return false;
		}
		if (dashing && isHazardAhead(dashDirection)) {
			return false;
		}
		if (dashTicks >= 0) {
			return true;
		}
		return chargeTarget != null && !chargeTarget.isRemoved() && chargeTarget.isAlive();
	}

	@Override
	public void start() {
		super.start();
		chargeTick = 0;
		dashTick = 0;
		stuckTicks = 0;
		dashing = false;
		finished = false;
		dashDirection = Vec3.ZERO;
		mob.getNavigation().stop();
		if (mob instanceof Charger charger) {
			charger.setCharging(true);
		}
		updateChargeTargetPos();
	}

	@Override
	public void tick() {
		if (!dashing) {
			updateChargeTargetPos();
		}
		if (chargeTick < windupTicks) {
			float lookSpeed = getWindupLookSpeed(chargeTick);
			if (chargeTarget != null && !chargeTarget.isRemoved()) {
				mob.getLookControl().setLookAt(chargeTarget, lookSpeed, lookSpeed);
			} else {
				mob.getLookControl().setLookAt(chargeTargetPos.x, chargeTargetPos.y, chargeTargetPos.z, lookSpeed, lookSpeed);
			}
			holdPosition();
			mob.walkAnimation.setSpeed(windupWalkAnimationSpeed);
			chargeTick++;
			return;
		}
		if (!dashing) {
			beginDash();
			if (!dashing) {
				chargeTick++;
				return;
			}
		}
		moveAlongDash();
		checkHit();
		updateDashProgress();
		updateStuck();
		chargeTick++;
	}

	@Override
	public void stop() {
		super.stop();
		chargeTarget = null;
		dashing = false;
		finished = false;
		chargeTick = 0;
		dashTick = 0;
		stuckTicks = 0;
		dashSpeed = 0.0D;
		dashDirection = Vec3.ZERO;
		cooldownTicksLeft = cooldownTicks;
		mob.getNavigation().stop();
		mob.setDeltaMovement(0.0D, mob.getDeltaMovement().y, 0.0D);
		if (mob instanceof Charger charger) {
			charger.setCharging(false);
		}
	}

	@Nullable
	protected Entity findTarget() {
		LivingEntity target = mob.getTarget();
		if (target == null || !target.isAlive() || !mob.canAttack(target)) {
			return null;
		}
		if (mob.distanceToSqr(target) > maxChargeDistance * maxChargeDistance) {
			return null;
		}
		if (!mob.getSensing().hasLineOfSight(target)) {
			return null;
		}
		return target;
	}

	protected void onChargeHit(Entity target) {
		mob.doHurtTarget(target);
	}

	protected double getDashSpeed(double distance) {
		return speed;
	}

	/**
	 * Head turn speed while winding up. The first tick may return a large value to snap the mob
	 * to its target immediately instead of turning over several ticks.
	 */
	protected float getWindupLookSpeed(int chargeTick) {
		return 30.0F;
	}

	private void updateChargeTargetPos() {
		if (chargeTarget != null && !chargeTarget.isRemoved()) {
			chargeTargetPos = chargeTarget.getBoundingBox().getCenter();
		}
	}

	private void beginDash() {
		dashing = true;
		dashStart = mob.position();
		Vec3 offset = chargeTargetPos.subtract(dashStart);
		boolean vertical = isVerticalDash();
		Vec3 horizontal = new Vec3(offset.x, 0.0D, offset.z);
		if (offset.lengthSqr() < 1.0E-4D || (!vertical && horizontal.lengthSqr() < 1.0E-4D)) {
			finished = true;
			dashing = false;
			return;
		}
		dashDirection = vertical ? offset.normalize() : horizontal.normalize();
		double distance = vertical ? offset.length() : horizontal.length();
		if (dashTicks >= 0) {
			dashSpeed = Math.max(getDashSpeed(distance), MIN_SPEED);
		} else {
			dashSpeed = speed;
			dashDistance = distance + overshoot;
			if (dashDistance <= 0.0D) {
				finished = true;
				dashing = false;
				return;
			}
		}
		dashYaw = (float) (Mth.atan2(dashDirection.z, dashDirection.x) * (180.0D / Math.PI)) - 90.0F;
		mob.setYRot(dashYaw);
		mob.setYHeadRot(dashYaw);
		mob.yBodyRot = dashYaw;
	}

	private void moveAlongDash() {
		if (dashDirection.lengthSqr() < 1.0E-6D) {
			finished = true;
			return;
		}
		mob.setYRot(dashYaw);
		mob.setYHeadRot(dashYaw);
		mob.yBodyRot = dashYaw;
		mob.getLookControl().setLookAt(mob.getX() + dashDirection.x * 2.0D, mob.getEyeY() + dashDirection.y * 2.0D, mob.getZ() + dashDirection.z * 2.0D, 360.0F, 360.0F);
		double currentSpeed = currentSpeed();
		double deltaY = isVerticalDash() ? dashDirection.y * currentSpeed : mob.getDeltaMovement().y;
		mob.setDeltaMovement(dashDirection.x * currentSpeed, deltaY, dashDirection.z * currentSpeed);
		if (!isVerticalDash() && mob.onGround() && mob.horizontalCollision) {
			mob.getJumpControl().jump();
		}
	}

	private void checkHit() {
		if (chargeTarget == null || chargeTarget.isRemoved()) {
			return;
		}
		if (mob.getBoundingBox().inflate(attackRange).intersects(chargeTarget.getBoundingBox())) {
			onChargeHit(chargeTarget);
		}
	}

	private void updateDashProgress() {
		if (dashTicks >= 0) {
			if (chargeTick + 1 >= windupTicks + dashTicks + decelTicks) {
				finished = true;
			}
			return;
		}
		dashTick++;
		if (mob.position().distanceTo(dashStart) >= dashDistance) {
			finished = true;
			return;
		}
		if (dashTick > (int) Math.ceil(dashDistance / speed) + 10) {
			finished = true;
		}
	}

	private void updateStuck() {
		if (mob.onGround() && mob.horizontalCollision) {
			stuckTicks++;
			if (stuckTicks > STUCK_TICKS) {
				finished = true;
			}
		} else {
			stuckTicks = 0;
		}
	}

	private double currentSpeed() {
		if (dashTicks < 0 || decelTicks <= 0) {
			return dashSpeed;
		}
		int decelStart = windupTicks + dashTicks;
		if (chargeTick < decelStart) {
			return dashSpeed;
		}
		double progress = Math.min(1.0D, (double) (chargeTick - decelStart + 1) / decelTicks);
		return dashSpeed * Math.cos(progress * Math.PI / 2.0D);
	}

	private void holdPosition() {
		mob.setDeltaMovement(0.0D, mob.getDeltaMovement().y, 0.0D);
	}

	private Vec3 directionTo(Entity target) {
		Vec3 offset = target.getBoundingBox().getCenter().subtract(mob.position());
		return new Vec3(offset.x, 0.0D, offset.z);
	}

	private boolean isVerticalDash() {
		return mob.isInWater() || mob.isInLava() || mob.isNoGravity();
	}

	private boolean isHazardAhead(Vec3 direction) {
		if ((!avoidDangerousBlocks && !avoidCliffs) || direction.lengthSqr() < 1.0E-6D) {
			return false;
		}
		Vec3 lookAhead = direction.normalize().scale(mob.getBbWidth() * 0.5D + 0.5D);
		BlockPos ahead = BlockPos.containing(mob.getX() + lookAhead.x, mob.getY() + 0.1D, mob.getZ() + lookAhead.z);
		BlockPos standPos = standablePos(ahead);
		if (avoidDangerousBlocks && GoalUtils.hasMalus(mob, standPos)) {
			return true;
		}
		return avoidCliffs && !hasWalkableFloor(standPos);
	}

	private BlockPos standablePos(BlockPos pos) {
		BlockState state = mob.level().getBlockState(pos);
		return state.getCollisionShape(mob.level(), pos).isEmpty() ? pos : pos.above();
	}

	private boolean hasWalkableFloor(BlockPos standPos) {
		if (isVerticalDash()) {
			return true;
		}
		for (int drop = 0; drop <= MAX_CLIFF_DROP; drop++) {
			BlockPos pos = standPos.below(drop);
			if (GoalUtils.hasMalus(mob, pos)) {
				continue;
			}
			if (mob.getNavigation().isStableDestination(pos) || hasCollisionBelow(pos)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasCollisionBelow(BlockPos pos) {
		BlockPos below = pos.below();
		return !mob.level().getBlockState(below).getCollisionShape(mob.level(), below).isEmpty();
	}
}
