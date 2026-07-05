package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.OrbitalTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class SolarCreeperSupernovaPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 10;
	public static final int DURATION = 150;

	public static final SmoothSegmentedValue SHINE_SCALE = SmoothSegmentedValue
		.of(Easing.IN_CIRC, 0, 0.5f, 72f / DURATION)
		.add(Easing.OUT_BACK, 0.5f, 3, 12f / DURATION)
		.add(Easing.IDENTITY, 3, 3, 24f / DURATION)
		.add(Easing.IN_OUT_CIRC, 3, 0, 42f / DURATION);

	public static final SmoothSegmentedValue SUN_SCALE = SmoothSegmentedValue
		.of(Easing.IDENTITY, 0, 0, 72f / DURATION)
		.add(Easing.OUT_BOUNCE, 0, 5, 12f / DURATION)
		.add(Easing.IDENTITY, 5, 5, 54f / DURATION)
		.add(Easing.OUT_QUART, 5, 0, 12f / DURATION);

	public static final SmoothSegmentedValue BODY_SCALE = SmoothSegmentedValue
		.of(Easing.IN_OUT_CUBIC, 1, 0.5f, 72f / DURATION)
		.add(Easing.OUT_ELASTIC, 0.5f, 0, 36f / DURATION)
		.add(Easing.IDENTITY, 0, 0, 30f / DURATION)
		.add(Easing.IN_OUT_SINE, 0, 1, 12f / DURATION);

	public SolarCreeperSupernovaPhase() {
		super(ID, 1, DURATION, 400);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 8);
	}

	@Override
	public void tick(SolarCreeper entity) {
		RandomSource random = entity.getRandom();
		int ticks = entity.getBehaviorTicks();
		if (entity.level() instanceof ServerLevel serverLevel) {
			if (ticks <= 72) {
				int interval = Mth.lerpInt(ticks / 72f, 8, 2);
				if (ticks % interval == 0) {
					ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(RingParticleOptions.getFlare(interval / 20f), entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 0, 0, 0));
				}
			}
			if (ticks == 72) {
				RippleParticleOptions.addFlareExplosionRippleParticles(serverLevel, entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), random, 4.5f, RippleParticleOptions.PURPLE, RippleParticleOptions.GOLD);
				for (int i = 0; i < 8; i++) {
					float speed1 = 3.5f + random.nextFloat() * 2.5f;
					float speed2 = speed1 + 3.5f + random.nextFloat() * 5.5f;
					float speed3 = speed2 + 2.5f + random.nextFloat() * 2.5f;
					float length1 = 9 + random.nextFloat() * 6;
					float length2 = length1 + 2 + random.nextFloat() * 2;
					ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(new OrbitalTrailParticleOptions(
						ESParticles.ORBITAL_FLARE.get(),
						new Vec3(random.nextDouble() - random.nextDouble(), random.nextDouble() - random.nextDouble(), random.nextDouble() - random.nextDouble()).normalize().toVector3f(),
						SmoothSegmentedValue.constant(6 + random.nextFloat() * 1.5f),
						SmoothSegmentedValue
							.of(Easing.OUT_CIRC, speed1, speed2, 0.2f)
							.add(Easing.OUT_ELASTIC, speed2, speed3, 0.6f)
							.add(Easing.IN_OUT_SINE, speed3, speed1, 0.2f),
						0.3f,
						SmoothSegmentedValue
							.of(Easing.OUT_QUART, 0, length1, 0.4f)
							.add(Easing.IN_OUT_QUAD, length1, length2, 0.2f)
							.add(Easing.IN_OUT_SINE, length2, 0, 0.4f),
						new Vector3f(1 - random.nextFloat() * 0.05f, 1 - random.nextFloat() * 0.05f, 1 - random.nextFloat() * 0.05f),
						random.nextInt(51, 72)
					), entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 0, 0, 0));
				}
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
