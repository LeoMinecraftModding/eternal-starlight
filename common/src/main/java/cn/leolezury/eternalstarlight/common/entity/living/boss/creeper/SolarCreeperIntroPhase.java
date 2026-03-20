package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class SolarCreeperIntroPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 1;
	public static final int DURATION = 120;

	public static final SmoothSegmentedValue SHINE_SCALE = SmoothSegmentedValue
		.of(Easing.IN_CIRC, 0, 0.5f, 0.6f)
		.add(Easing.OUT_BACK, 0.5f, 1, 0.1f)
		.add(v -> v, 1, 1, 0.2f)
		.add(Easing.IN_OUT_CIRC, 1, 0, 0.1f);

	public static final SmoothSegmentedValue SUN_SCALE = SmoothSegmentedValue
		.of(Easing.OUT_BOUNCE, 0, 0.6f, 0.1f)
		.add(Easing.IN_OUT_CIRC, 0.6f, 1, 0.6f)
		.add(Easing.OUT_QUART, 1, 0, 0.2f)
		.add(v -> v, 0, 0, 0.1f);

	public static final SmoothSegmentedValue BODY_SCALE = SmoothSegmentedValue
		.of(v -> v, 0, 0, 0.7f)
		.add(Easing.OUT_ELASTIC, 0, 1, 0.2f)
		.add(v -> v, 1, 1, 0.1f);

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
					serverLevel.sendParticles(RingParticleOptions.getFlare(interval / 20f), entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 1, 0, 0, 0, 0);
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
