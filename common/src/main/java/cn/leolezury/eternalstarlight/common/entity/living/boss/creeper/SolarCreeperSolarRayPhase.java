package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.joml.Vector3f;

public class SolarCreeperSolarRayPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 11;
	public static final int DURATION = 200;
	public static final float MAX_LENGTH = 20;

	public static final SmoothSegmentedValue SHINE_SCALE = SmoothSegmentedValue
		.of(Easing.OUT_BOUNCE, 0, 0.8f, 50f / DURATION)
		.add(Easing.IDENTITY, 0.8f, 0.8f, 120f / DURATION)
		.add(Easing.IN_CIRC, 0.8f, 0, 30f / DURATION);

	public static final SmoothSegmentedValue SUN_SCALE = SmoothSegmentedValue
		.of(Easing.OUT_BOUNCE, 0, 3, 30f / DURATION)
		.add(Easing.IDENTITY, 3, 3, 150f / DURATION)
		.add(Easing.IN_CIRC, 3, 0, 20f / DURATION);

	public static final SmoothSegmentedValue GROUP_1 = SmoothSegmentedValue
		.of(Easing.OUT_CIRC, 0, 1, 10f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 90f / DURATION)
		.add(Easing.OUT_CUBIC, 1, 0, 20f / DURATION)
		.add(Easing.IDENTITY, 0, 0, 80f / DURATION);

	public static final SmoothSegmentedValue GROUP_2 = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 80f / DURATION)
		.add(Easing.OUT_CIRC, 0, 1, 10f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 90f / DURATION)
		.add(Easing.OUT_CUBIC, 1, 0, 20f / DURATION);

	public static final SmoothSegmentedValue ROTATION_SPEED = SmoothSegmentedValue
		.of(Easing.IN_QUART, 1, 1.2f, 20f / DURATION)
		.add(Easing.IN_OUT_CIRC, 1.2f, 1.5f, 60f / DURATION)
		.add(Easing.IN_OUT_BACK, 1.5f, -1.5f, 100f / DURATION)
		.add(Easing.OUT_CUBIC, -1.5f, -0.5f, 20f / DURATION);

	public SolarCreeperSolarRayPhase() {
		super(ID, 1, DURATION, 500);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTarget(entity, 5) && entity.getTarget() != null;
	}

	@Override
	public void tick(SolarCreeper entity) {
		if (!entity.level().isClientSide) {
			int ticks = entity.getBehaviorTicks();
			float progress = ticks / (float) DURATION;

			LivingEntity attackTarget = entity.getTarget();

			if (attackTarget != null) {
				Vec3 sunPos = entity.getSunAbovePos(0);
				Vec3 toTarget = attackTarget.position().add(0, attackTarget.getBbHeight() / 2, 0).subtract(sunPos);
				Vec3 upNormal = toTarget.cross(new Vec3(0, 1, 0)).normalize();
				Vec3 normal = toTarget.cross(upNormal);
				if (normal.lengthSqr() < 0.01) {
					normal = new Vec3(0, 1, 0);
				}
				entity.setSolarRayNormal(normal.toVector3f());
			}

			entity.setSolarRayAngle(entity.getSolarRayAngle() + ROTATION_SPEED.calculate(progress));

			for (int i = 0; i < 6; i++) {
				boolean isGroup1 = i % 2 == 0;
				float groupScale = isGroup1 ? GROUP_1.calculate(progress) : GROUP_2.calculate(progress);
				entity.setSolarRayLength(i, groupScale * MAX_LENGTH);
			}

			Vec3 sunPos = entity.getSunAbovePos(0);
			Vector3f normalVec = entity.getSolarRayNormal();
			Vec3 normal = new Vec3(normalVec);
			if (normal.lengthSqr() < 0.01) {
				normal = new Vec3(0, 0, 1);
			}
			normal = normal.normalize();
			float angle = entity.getSolarRayAngle();

			for (int i = 0; i < 6; i++) {
				float laserLen = entity.getSolarRayLength(i);
				if (laserLen > 0.5f) {
					Vec3 idealEndPos = ESMathUtil.rotateAroundAxis(sunPos, normal, i * 60 + angle, laserLen);

					Vec3 endPos = idealEndPos;
					ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(entity.level(), CollisionContext.of(entity), sunPos, idealEndPos);
					boolean hasBlock = result.blockHitResult() != null && result.blockHitResult().getType() != HitResult.Type.MISS;
					if (hasBlock) {
						endPos = result.blockHitResult().getLocation();
					}
					entity.setSolarRayLength(i, (float) endPos.distanceTo(sunPos));

					result = ESEntityUtil.raytrace(entity.level(), CollisionContext.of(entity), sunPos, endPos);
					for (Entity target : result.entities()) {
						if (target instanceof LivingEntity living && living != entity && ESEntityUtil.shouldHarm(entity, living)) {
							if (living.hurt(ESDamageTypes.getIndirectEntityDamageSource(entity.level(), ESDamageTypes.LASER, entity, entity), 4f)) {
								living.setRemainingFireTicks(Math.max(living.getRemainingFireTicks(), 100));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
