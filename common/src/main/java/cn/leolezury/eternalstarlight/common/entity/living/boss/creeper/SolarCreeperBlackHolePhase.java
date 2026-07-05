package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SolarCreeperBlackHolePhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 12;
	public static final int DURATION = 200;

	public static final SmoothSegmentedValue SUN_SCALE = SmoothSegmentedValue
		.of(Easing.OUT_BOUNCE, 0, 3, 90f / DURATION)
		.add(Easing.IN_OUT_SINE, 3, 0, 10f / DURATION)
		.add(Easing.IDENTITY, 0, 0, 100f / DURATION);

	public static final SmoothSegmentedValue SUN_REDNESS = SmoothSegmentedValue
		.of(Easing.IN_OUT_CIRC, 0, 1, 80f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 120f / DURATION);

	public static final SmoothSegmentedValue JITTER_FREQ = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 60f / DURATION)
		.add(Easing.IN_CUBIC, 0, 5, 40f / DURATION)
		.add(Easing.IDENTITY, 0.8f, 0.5f, 100f / DURATION);

	public static final SmoothSegmentedValue BLACK_HOLE_SCALE = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 100f / DURATION)
		.add(Easing.OUT_BOUNCE, 0, 3, 10f / DURATION)
		.add(Easing.IDENTITY, 3, 3, 85f / DURATION)
		.add(Easing.IN_OUT_SINE, 3, 0, 5f / DURATION);

	public static final SmoothSegmentedValue SHINE_SCALE = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 100f / DURATION)
		.add(Easing.OUT_BOUNCE, 0, 0.5f, 10f / DURATION)
		.add(Easing.IDENTITY, 0.5f, 0.5f, 85f / DURATION)
		.add(Easing.IN_OUT_SINE, 0.5f, 0, 5f / DURATION);

	public SolarCreeperBlackHolePhase() {
		super(ID, 1, DURATION, 600);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTargetXZ(entity, 10) && entity.getTarget() != null;
	}

	@Override
	public void tick(SolarCreeper entity) {
		int ticks = entity.getBehaviorTicks();
		float progress = ticks / (float) DURATION;

		if (!entity.level().isClientSide && progress > 0.75f) {
			Vec3 blackHolePos = entity.getSunAbovePos(1);
			AABB aabb = AABB.ofSize(blackHolePos, 14, 14, 14);
			for (Entity e : entity.level().getEntities(entity, aabb, e -> e instanceof LivingEntity living && ESEntityUtil.shouldHarm(entity, living))) {
				if (e instanceof LivingEntity living) {
					Vec3 pull = blackHolePos.subtract(living.position()).normalize().scale(0.08);
					living.setDeltaMovement(living.getDeltaMovement().add(pull));
					living.hurtMarked = true;
					if (ticks % 10 == 0) {
						living.hurt(ESDamageTypes.getIndirectEntityDamageSource(entity.level(), ESDamageTypes.LASER, entity, entity), 2);
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
