package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.OrbitalTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class SolarCreeperBlackHolePhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 12;
	public static final int DURATION = 200;

	public static final Vec3 BLACK_HOLE_RAY_NORMAL = new Vec3(1, 4, 1).normalize();

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
		RandomSource random = entity.getRandom();
		if (entity.level() instanceof ServerLevel serverLevel) {
			Vec3 blackHolePos = entity.getSunAbovePos(1);
			if (ticks > 100) {
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
				if (ticks % 8 == 0 && ticks <= 160) {
					for (int i = 0; i < 5; i++) {
						float speed1 = 1.75f + random.nextFloat() * 1.25f;
						float speed2 = speed1 + 1.75f + random.nextFloat() * 2.75f;
						float speed3 = speed2 + 1.25f + random.nextFloat() * 1.25f;
						float length1 = 4.5f + random.nextFloat() * 3;
						float length2 = length1 + 1 + random.nextFloat();
						ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(new OrbitalTrailParticleOptions(
							ESParticles.ORBITAL_SPACE_MATTER.get(),
							BLACK_HOLE_RAY_NORMAL.toVector3f(),
							SmoothSegmentedValue
								.of(Easing.IN_OUT_SINE, 6 + random.nextFloat() * 1.5f, 0, 1),
							SmoothSegmentedValue
								.of(Easing.OUT_CIRC, speed1, speed2, 0.2f)
								.add(Easing.OUT_ELASTIC, speed2, speed3, 0.6f)
								.add(Easing.IN_OUT_SINE, speed3, speed1, 0.2f),
							0.12f,
							SmoothSegmentedValue
								.of(Easing.OUT_QUART, 0, length1, 0.4f)
								.add(Easing.IN_OUT_QUAD, length1, length2, 0.2f)
								.add(Easing.IN_OUT_SINE, length2, 0, 0.4f),
							new Vector3f(1 - random.nextFloat() * 0.05f, 1 - random.nextFloat() * 0.05f, 1 - random.nextFloat() * 0.05f),
							random.nextInt(31, 42)
						), blackHolePos.x, blackHolePos.y, blackHolePos.z, 0, 0, 0));
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
