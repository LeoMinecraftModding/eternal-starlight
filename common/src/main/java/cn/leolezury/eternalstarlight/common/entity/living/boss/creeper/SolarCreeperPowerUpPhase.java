package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SolarCreeperPowerUpPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 14;
	public static final int DURATION = 240;

	public static final List<String> PART_NAMES = List.of("root", "body", "head", "leg1", "leg2", "leg3", "leg4");

	public static final SmoothSegmentedValue SHATTER_DEGREE = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 30f / DURATION)
		.add(Easing.OUT_CIRC, 0, 0.9f, 15f / DURATION)
		.add(Easing.OUT_QUART, 0.9f, 1, 45f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 80f / DURATION)
		.add(Easing.IN_EXPO, 1, 0, 20f / DURATION)
		.add(Easing.IDENTITY, 0, 0, 50f / DURATION);

	public static final SmoothSegmentedValue STAR_SCALE = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 80f / DURATION)
		.add(Easing.OUT_BACK, 0, 1.5f, 10f / DURATION)
		.add(Easing.IDENTITY, 1.5f, 1.5f, 100f / DURATION)
		.add(Easing.IN_OUT_CIRC, 1.5f, 0, 10f / DURATION)
		.add(Easing.IDENTITY, 0, 0, 40f / DURATION);

	public static final SmoothSegmentedValue CONNECTION_DEGREE = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 90f / DURATION)
		.add(Easing.IN_OUT_SINE, 0, 1, 80f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 20f / DURATION)
		.add(Easing.OUT_QUART, 1, 0, 50f / DURATION);

	public static final SmoothSegmentedValue OUTLINE_DEGREE = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 90f / DURATION)
		.add(Easing.IN_OUT_SINE, 0, 1, 80f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 70f / DURATION);

	public SolarCreeperPowerUpPhase() {
		super(ID, 0, DURATION, 0);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return entity.getPhase() == 0 && entity.getHealth() / entity.getMaxHealth() < 0.5;
	}

	@Override
	public void onStart(SolarCreeper entity) {
		entity.setPhase(1);
	}

	@Override
	public void tick(SolarCreeper entity) {
		int ticks = entity.getBehaviorTicks();
		if (entity.level() instanceof ServerLevel serverLevel) {
			if (ticks < 30) {
				entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.05, 0));
			}
			if (ticks == 30) {
				entity.setDeltaMovement(Vec3.ZERO);
				RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel,
					entity.getX(), entity.getY() + entity.getBbHeight() / 2,
					entity.getZ(), entity.getRandom(), 4.5f,
					RippleParticleOptions.PURPLE, RippleParticleOptions.GOLD);
				ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 40, 20, 0.5f, 0.7f, 3, 5.5f).send(serverLevel);
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
