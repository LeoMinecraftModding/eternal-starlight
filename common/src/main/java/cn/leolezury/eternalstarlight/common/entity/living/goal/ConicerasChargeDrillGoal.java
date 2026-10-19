package cn.leolezury.eternalstarlight.common.entity.living.goal;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

// TODO: OVERHAUL CHARGE GOALS
public class ConicerasChargeDrillGoal extends Goal {
//	private final PathfinderMob charger;
//	private final double speed;
//	private final double targetBoatRange;
//	private final float attackRange;
//	private int attackCooldown;
//	private int runningTicks;
//	private Entity target;
// ===== 阶段常量 =====
	private static final int WINDUP_TICKS = 40;          // 蓄力时长
	private static final int MAX_DURATION = 120;         // 总持续时长
	private static final int DECAY_START = 90;           // 开始减速的 tick
	private static final int DECAY_DURATION = 30;        // 减速持续时间
	private static final double MAX_LIVING_TARGET_DISTANCE = 20.0D;

	// ===== 运动常量 =====
	private static final double DASH_SPEED_FACTOR = 0.1D;
	private static final double HOLD_SINK_SPEED = -0.005D;
	private static final float WINDUP_ANIM_SPEED = 0.5F;

	// ===== 攻击常量 =====
	private static final int ATTACK_INTERVAL = 2;

	private final PathfinderMob charger;
	private final double speed;
	private final double targetBoatRange;
	private final float attackRange;
	private final int randomFrequency;

	private int attackCooldown;
	private int runningTicks;
	private Entity target;

	public ConicerasChargeDrillGoal(PathfinderMob charger, double speed, float attackRange, double targetBoatRange, int randomFrequency) {
		this.charger = charger;
		this.speed = speed;
		this.attackRange = attackRange;
		this.targetBoatRange = targetBoatRange;
		this.randomFrequency = randomFrequency;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canUse() {
		LivingEntity mobTarget = charger.getTarget();
		if (mobTarget != null && mobTarget.isAlive()) {
			if (charger.distanceToSqr(mobTarget) > MAX_LIVING_TARGET_DISTANCE * MAX_LIVING_TARGET_DISTANCE) return false;
			if (!charger.getSensing().hasLineOfSight(mobTarget)) return false;
			if (charger.getRandom().nextInt(randomFrequency) == 0) return false;
			this.target = mobTarget;
			return true;
		}
		Boat nearestBoat = findNearestBoat();
		if (nearestBoat == null) return false;
		this.target = nearestBoat;
		return true;
	}

	@Nullable
	private Boat findNearestBoat() {
		Boat nearest = null;
		double nearestDistSqr = targetBoatRange * targetBoatRange;
		for (Boat boat : charger.level().getEntitiesOfClass(Boat.class, charger.getBoundingBox().inflate(targetBoatRange), EntitySelector.NO_SPECTATORS)) {
			if (boat.isRemoved()) {
				continue;
			}
			double distSqr = charger.distanceToSqr(boat);
			if (distSqr < nearestDistSqr) {
				nearestDistSqr = distSqr;
				nearest = boat;
			}
		}
		return nearest;
	}

	@Override
	public boolean canContinueToUse() {
		if (runningTicks >= MAX_DURATION) {
			return false;
		}
		if (target == null || target.isRemoved() || !target.isAlive()) {
			return false;
		}
		double maxRange = target instanceof LivingEntity ? MAX_LIVING_TARGET_DISTANCE : targetBoatRange;
		return charger.distanceToSqr(target) <= maxRange * maxRange;
	}

	@Override
	public void start() {
		super.start();
		attackCooldown = 0;
		runningTicks = 0;
		charger.getNavigation().stop();
	}

	@Override
	public void tick() {
		super.tick();
		runningTicks++;

		if (target == null || target.isRemoved() || !target.isAlive()) {
			return;
		}

		charger.getLookControl().setLookAt(target, 360.0F, 360.0F);
		if (runningTicks <= WINDUP_TICKS) {
			tickWindup();
		} else if (charger.distanceToSqr(target) <= (double) attackRange * attackRange) {
			tickAttack();
		} else {
			tickDash();
		}
	}

	private void tickWindup() {
		holdPosition();
		charger.walkAnimation.setSpeed(WINDUP_ANIM_SPEED);
		if (charger instanceof Charger chargingMob) {
			chargingMob.setCharging(true);
		}
	}

	private void tickDash() {
		Vec3 targetPos = target.getBoundingBox().getCenter();
		Vec3 dir = targetPos.subtract(charger.position()).normalize();
		double currentSpeed = speed * DASH_SPEED_FACTOR * speedDecay();
		charger.setDeltaMovement(dir.scale(currentSpeed));
	}

	private void tickAttack() {
		holdPosition();
		if (attackCooldown > 0) {
			attackCooldown--;
			return;
		}
		attackCooldown = ATTACK_INTERVAL;
		target.invulnerableTime = 0;
		target.hurt(charger.damageSources().mobAttack(charger), (float) charger.getAttributeValue(Attributes.ATTACK_DAMAGE));
	}

	private double speedDecay() {
		if (runningTicks <= DECAY_START) {
			return 1.0D;
		}
		float progress = (float) (runningTicks - DECAY_START) / (DECAY_DURATION);
		progress = Math.min(1.0F, progress);
		return Math.cos((double) progress * Math.PI / 2.0D);
	}

	private void holdPosition() {
		charger.setDeltaMovement(0.0D, charger.isInWater() ? HOLD_SINK_SPEED : 0.0D, 0.0D);
	}

	@Override
	public void stop() {
		super.stop();
		attackCooldown = 0;
		runningTicks = 0;
		if (target instanceof LivingEntity) {
			charger.setTarget(null);
		}
		target = null;
		charger.getNavigation().stop();
		if (charger instanceof Charger chargingMob) {
			chargingMob.setCharging(false);
		}
	}
}
