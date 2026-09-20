package cn.leolezury.eternalstarlight.common.entity.living.goal;

import cn.leolezury.eternalstarlight.common.client.sound.DrillSoundInstance;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ConicerasChargeDrillGoal extends Goal {
	private final PathfinderMob charger;
	private final double speed;
	private final double targetBoatRange;
	private final float attackRange;
	private int attackCooldown;
	private int runningTicks;
	private Entity target;

	public ConicerasChargeDrillGoal(PathfinderMob charger, double speed, float attackRange, double targetBoatRange) {
		this.charger = charger;
		this.speed = speed;
		this.attackRange = attackRange;
		this.targetBoatRange = targetBoatRange;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canUse() {
		target = charger.getTarget();
		if (target != null && target.isAlive()) {
			if (target.distanceTo(charger) > 20) return false;
			return charger.getSensing().hasLineOfSight(target);
		}
		for (Boat boat : charger.level().getEntitiesOfClass(Boat.class, charger.getBoundingBox().inflate(targetBoatRange))) {
			if (boat.isRemoved()) {
				continue;
			}
			if (charger.distanceToSqr(boat) > targetBoatRange * targetBoatRange) {
				continue;
			}
			target = boat;
			return true;
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		if (runningTicks >= 120) {
			return false;
		}
		if (target == null || target.isRemoved() || !target.isAlive()) {
			return false;
		}
		double range = target instanceof LivingEntity ? 20.0D : targetBoatRange;
		return charger.distanceToSqr(target) <= range * range;
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
		if (target == null || !target.isAlive()) {
			return;
		}

		charger.getLookControl().setLookAt(target, 360.0F, 360.0F);

		Vec3 targetPos = target.getBoundingBox().getCenter();
		if (runningTicks <= 40) {
			holdPosition();
			charger.walkAnimation.setSpeed(charger.walkAnimation.speed() + 0.5F);
			if (charger instanceof Charger chargingMob) {
				chargingMob.setCharging(true);
				Minecraft.getInstance().getSoundManager().play(new DrillSoundInstance(ESSoundEvents.CONICERAS_DRILL.get(), charger,charger.position()));
			}
		} else if (charger.distanceToSqr(target) <= (double) attackRange * attackRange) {
			holdPosition();
			if (attackCooldown > 0) {
				attackCooldown--;
			} else {
				attackCooldown = 2;
				target.invulnerableTime = 0;
				target.hurt(charger.damageSources().mobAttack(charger), (float) charger.getAttributeValue(Attributes.ATTACK_DAMAGE));
			}
		} else {
			Vec3 dir = targetPos.subtract(charger.position()).normalize();
			charger.setDeltaMovement(dir.scale(speed * 0.1D * speedDecay()));
		}
	}

	private double speedDecay() {
		if (runningTicks <= 90) {
			return 1.0D;
		}
		float progress = (float) (runningTicks - 90) / (30F);
		progress = Math.min(1.0F, progress);
		return Math.cos((double) progress * Math.PI / 2.0D);
	}

	private void holdPosition() {
		charger.setDeltaMovement(0.0D, charger.isInWater() ? -0.005D : 0.0D, 0.0D);
	}

	@Override
	public void stop() {
		super.stop();
		attackCooldown = 0;
		charger.setTarget(null);
		charger.getNavigation().stop();
		if (charger instanceof Charger chargingMob) {
			chargingMob.setCharging(false);
		}
	}
}
