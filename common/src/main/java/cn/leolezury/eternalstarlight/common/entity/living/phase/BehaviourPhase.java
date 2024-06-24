package cn.leolezury.eternalstarlight.common.entity.living.phase;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public abstract class BehaviourPhase<T extends LivingEntity & MultiBehaviourUser> {
	private final int id;
	private final int priority;
	private final int duration;
	private final int cooldown;
	private final int turnsInto;

	public BehaviourPhase(int id, int priority, int duration, int cooldown) {
		this(id, priority, duration, cooldown, 0);
	}

	public BehaviourPhase(int id, int priority, int duration, int cooldown, int turnsInto) {
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

	public abstract void onStart(T entity);

	public abstract void tick(T entity);

	public abstract boolean canContinue(T entity);

	public abstract void onStop(T entity);

	public void start(T entity) {
		entity.setBehaviourState(getId());
		entity.setBehaviourTicks(0);
		onStart(entity);
	}

	public void stop(T entity) {
		entity.setBehaviourState(turnsInto);
		onStop(entity);
		entity.setBehaviourTicks(0);
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

	public boolean performMeleeAttack(T entity, double range) {
		if (entity instanceof Targeting targeting) {
			LivingEntity target = targeting.getTarget();
			if (target == null) {
				return false;
			}
			for (LivingEntity livingEntity : entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox().inflate(range))) {
				if (livingEntity.getUUID().equals(target.getUUID()) && canReachTarget(entity, range)) {
					return entity.doHurtTarget(livingEntity);
				}
			}
			return false;
		} else {
			return false;
		}
	}
}
