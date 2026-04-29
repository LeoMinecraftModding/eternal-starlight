package cn.leolezury.eternalstarlight.common.entity.living.phase;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.function.Consumer;

public abstract class BehaviorPhase<T extends LivingEntity & MultiBehaviorUser> {
	private final int id;
	private final int priority;
	private final int duration;
	private final int cooldown;
	private final int turnsInto;

	public BehaviorPhase(int id, int priority, int duration, int cooldown) {
		this(id, priority, duration, cooldown, 0);
	}

	public BehaviorPhase(int id, int priority, int duration, int cooldown, int turnsInto) {
		this.id = id;
		this.priority = priority;
		this.duration = duration;
		this.cooldown = cooldown;
		this.turnsInto = turnsInto;
	}

	public int getId() {
		return id;
	}

	public int getPriority() {
		return priority;
	}

	public int getDuration() {
		return duration;
	}

	public int getCooldown() {
		return cooldown;
	}

	public abstract boolean canStart(T entity, boolean cooldownOver);

	public void onStart(T entity) {

	}

	public abstract void tick(T entity);

	public abstract boolean canContinue(T entity);

	public void onStop(T entity) {

	}

	public void start(T entity, BehaviorManager<T> manager) {
		entity.setBehaviorState(getId());
		entity.setBehaviorTicks(0);
		onStart(entity);
		manager.getCooldowns().put(getId(), getCooldown());
	}

	public void stop(T entity, BehaviorManager<T> manager) {
		if (turnsInto == 0) {
			entity.setBehaviorState(turnsInto);
			entity.setBehaviorTicks(0);
		} else {
			manager.getAllPhases().stream().filter(p -> turnsInto == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
		}
		onStop(entity);
	}

	public boolean canReachTarget(T entity, double range) {
		if (entity instanceof Targeting targeting) {
			LivingEntity target = targeting.getTarget();
			if (target == null) {
				return false;
			}
			return entity.distanceTo(target) <= range + entity.getBbWidth() / 2f + target.getBbWidth() / 2f;
		} else {
			return false;
		}
	}

	public boolean canReachTargetXZ(T entity, double range) {
		if (entity instanceof Targeting targeting) {
			LivingEntity target = targeting.getTarget();
			if (target == null) {
				return false;
			}
			return Mth.sqrt((float) ((entity.getX() - target.getX()) * (entity.getX() - target.getX()) + (entity.getZ() - target.getZ()) * (entity.getZ() - target.getZ()))) <= range + entity.getBbWidth() / 2f + target.getBbWidth() / 2f;
		} else {
			return false;
		}
	}

	public boolean isFacingEntity(T entity, LivingEntity target, float maxDiff, float yAngle) {
		if (target == null) {
			return false;
		}
		float angle = ESMathUtil.positionToYaw(entity.position(), target.position());
		return Mth.degreesDifferenceAbs(angle, yAngle + 90) <= maxDiff;
	}

	public void performDefaultMeleeAttack(T entity, double range) {
		performDefaultMeleeAttack(entity, range, false, 0, e -> {
		});
	}

	public void performDefaultMeleeAttack(T entity, double range, boolean attackOtherEnemies, float maxAngleForOtherEnemies, Consumer<LivingEntity> onSuccess) {
		performMeleeAttack(entity, range, attackOtherEnemies, maxAngleForOtherEnemies, living -> {
			if (entity.doHurtTarget(living)) {
				onSuccess.accept(living);
			}
		});
	}

	public void performMeleeAttack(T entity, double range, boolean attackOtherEnemies, float maxAngleForOtherEnemies, Consumer<LivingEntity> hurt) {
		if (entity instanceof Targeting targeting) {
			LivingEntity target = targeting.getTarget();
			for (LivingEntity livingEntity : entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox().inflate(range))) {
				if (livingEntity == target || (attackOtherEnemies && livingEntity instanceof Targeting t && t.getTarget() == entity
					&& (isFacingEntity(entity, livingEntity, maxAngleForOtherEnemies, entity.getYRot())
					|| isFacingEntity(entity, livingEntity, maxAngleForOtherEnemies, entity.yBodyRot)))) {
					hurt.accept(livingEntity);
				}
			}
		}
	}
}
