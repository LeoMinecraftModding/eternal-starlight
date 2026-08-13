package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
		RandomSource random = entity.getRandom();
		if (entity.level() instanceof ServerLevel serverLevel) {
			if (ticks < 30) {
				entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.05, 0));
			}
			if (ticks == 30) {
				entity.setDeltaMovement(Vec3.ZERO);
				for (int i = 0; i < 3; i++) {
					RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel, entity.getX() + (random.nextFloat() - random.nextFloat()) * entity.getBbWidth() * 1.5, entity.getY() + entity.getBbHeight() / 2 + (random.nextFloat() - random.nextFloat()) * entity.getBbHeight() * 0.75, entity.getZ() + (random.nextFloat() - random.nextFloat()) * entity.getBbWidth() * 1.5, random, Mth.randomBetween(random, 1.8f, 2.4f), Mth.randomBetween(random, 0.4f, 0.9f), random.nextBoolean() ? RippleParticleOptions.PURPLE : RippleParticleOptions.ORANGE, RippleParticleOptions.GOLD);
				}
				ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 60, 40, 0.6f, 0.5f, 4.5f, 5.5f).send(serverLevel);
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
