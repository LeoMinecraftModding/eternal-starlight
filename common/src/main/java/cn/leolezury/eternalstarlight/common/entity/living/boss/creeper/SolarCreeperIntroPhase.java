package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class SolarCreeperIntroPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 1;
	public static final int DURATION = 120;

	public static final SmoothSegmentedValue SHINE_SCALE = SmoothSegmentedValue
		.of(Easing.IN_CIRC, 0, 0.5f, 72f / DURATION)
		.add(Easing.OUT_BACK, 0.5f, 1, 12f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 24f / DURATION)
		.add(Easing.IN_OUT_CIRC, 1, 0, 12f / DURATION);

	public static final SmoothSegmentedValue SUN_SCALE = SmoothSegmentedValue
		.of(Easing.OUT_BOUNCE, 0, 0.6f, 12f / DURATION)
		.add(Easing.IN_OUT_CIRC, 0.6f, 1, 72f / DURATION)
		.add(Easing.OUT_QUART, 1, 0, 24f / DURATION)
		.add(Easing.IDENTITY, 0, 0, 12f / DURATION);

	public static final SmoothSegmentedValue BODY_SCALE = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 84f / DURATION)
		.add(Easing.OUT_ELASTIC, 0, 1, 24f / DURATION)
		.add(Easing.IDENTITY, 1, 1, 12f / DURATION);

	public SolarCreeperIntroPhase() {
		super(ID, 1, DURATION, 0);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(SolarCreeper entity) {
		RandomSource random = entity.getRandom();
		int ticks = entity.getBehaviorTicks();
		if (entity.level() instanceof ServerLevel serverLevel) {
			if (ticks <= 0.7 * DURATION) {
				int interval = Mth.lerpInt(ticks / (0.7f * DURATION), 10, 3);
				if (ticks % interval == 0) {
					RippleParticleOptions shrinkingRipple = new RippleParticleOptions(ESParticles.RIPPLE.get(),
						SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, 3f, 0, 1),
						SmoothSegmentedValue.of(Easing.OUT_QUART, 0, 0.4f, 1),
						random.nextFloat() < 0.2F ? RippleParticleOptions.PURPLE : RippleParticleOptions.GOLD, interval * 3);
					ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(shrinkingRipple, entity.getX() + (random.nextFloat() - random.nextFloat()) * 0.15F, entity.getY() + entity.getBbHeight() / 2 + (random.nextFloat() - random.nextFloat()) * 0.15F, entity.getZ() + (random.nextFloat() - random.nextFloat()) * 0.15F, 0, 0, 0));
					for (int i = 0; i < 3; i++) {
						double dx = (random.nextDouble() * 3 + 2) * (random.nextBoolean() ? 1 : -1);
						double dy = (random.nextDouble() * 3 + 2) * (random.nextBoolean() ? 1 : -1);
						double dz = (random.nextDouble() * 3 + 2) * (random.nextBoolean() ? 1 : -1);
						ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(GatheringTrailParticleOptions.FLARE, entity.getX() - dx, entity.getY() + entity.getBbHeight() / 2 - dy, entity.getZ() - dz, dx, dy, dz));
					}
				}
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}

	@Override
	public void onStop(SolarCreeper entity) {
		entity.finishIntro();
	}
}
