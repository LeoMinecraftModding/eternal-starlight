package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.OrbitalPlanet;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;

public class SolarCreeperGalaxyPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 13;
	public static final int DURATION = 300;

	public static final SmoothSegmentedValue BODY_SCALE = SmoothSegmentedValue
		.of(Easing.IN_CUBIC, 1, 0.8f, 80f / DURATION)
		.add(Easing.IN_OUT_SINE, 0.8f, 1, 10f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 210f / DURATION);

	public static final SmoothSegmentedValue JITTER_FREQ = SmoothSegmentedValue
		.of(Easing.IN_CUBIC, 0, 3, 80f / DURATION)
		.add(Easing.IDENTITY, 0, 0, 220f / DURATION);

	public static final SmoothSegmentedValue SHINE_SCALE = SmoothSegmentedValue
		.of(Easing.IN_CUBIC, 0, 1, 80f / DURATION)
		.add(Easing.IN_OUT_SINE, 1, 0, 10f / DURATION)
		.add(Easing.IDENTITY, 0, 0, 210f / DURATION);

	public SolarCreeperGalaxyPhase() {
		super(ID, 1, DURATION, 500);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTarget(entity, 5) && entity.getTarget() != null;
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		RandomSource random = entity.getRandom();
		int ticks = entity.getBehaviorTicks();
		if (ticks == 80 && target != null && entity.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 4; i++) {
				RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel, entity.getX() + (random.nextFloat() - random.nextFloat()) * entity.getBbWidth() * 1.5, entity.getY() + entity.getBbHeight() / 2 + (random.nextFloat() - random.nextFloat()) * entity.getBbHeight() * 0.75, entity.getZ() + (random.nextFloat() - random.nextFloat()) * entity.getBbWidth() * 1.5, random, Mth.randomBetween(random, 1.8f, 2.4f), Mth.randomBetween(random, 0.3f, 0.7f), random.nextBoolean() ? RippleParticleOptions.PURPLE : RippleParticleOptions.ORANGE, RippleParticleOptions.GOLD);
			}
			ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 60, 30, 0.5f, 0.5f, 4.5f, 5.5f).send(serverLevel);

			float[] radii = {4, 6, 8, 10, 12, 14};
			float[] speeds = {5, -5, 6, -6, 7, -7};
			int[] preFireTicks = {220, 200, 180, 160, 140, 120};

			for (int i = 0; i < 6; i++) {
				OrbitalPlanet planet = new OrbitalPlanet(ESEntities.ORBITAL_PLANET.get(), entity.level());
				planet.moveTo(entity.position().add(0, entity.getBbHeight() / 2, 0));
				planet.setOwner(entity);
				planet.setTarget(target);
				float initialAngle = entity.getRandom().nextFloat() * 360;
				planet.setOrbitData(radii[i], speeds[i], initialAngle, preFireTicks[i]);
				entity.level().addFreshEntity(planet);
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
