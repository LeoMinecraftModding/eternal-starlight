package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.PlanetProjectile;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SolarCreeperGalaxyPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 13;
	public static final int DURATION = 300;

	private final List<PlanetProjectile> planets = new ArrayList<>();

	private float[] planetOrbitRadii;
	private float[] planetOrbitSpeeds;

	public SolarCreeperGalaxyPhase() {
		super(ID, 1, DURATION, 500);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return cooldownOver && canReachTarget(entity, 5);
	}

	@Override
	public void onStart(SolarCreeper entity) {
		planets.clear();
		LivingEntity target = entity.getTarget();
		if (target == null) {
			return;
		}
		Vec3 planeNormal = target.position().subtract(entity.position()).normalize();
		int count = 7;
		planetOrbitRadii = new float[count];
		planetOrbitSpeeds = new float[count];
		for (int i = 0; i < count; i++) {
			float orbitRadius = 3f + i * 0.7f;
			float orbitSpeed = -1.5f + i * 0.55f;
			float orbitAngle = (Mth.TWO_PI / count) * i + entity.getRandom().nextFloat() * 0.5f;
			planetOrbitRadii[i] = orbitRadius;
			planetOrbitSpeeds[i] = orbitSpeed;
			PlanetProjectile planet = new PlanetProjectile(ESEntities.PLANET_PROJECTILE.get(), entity.level());
			planet.setPos(entity.getX() + Math.cos(orbitAngle) * orbitRadius, entity.getY() + entity.getBbHeight() / 2, entity.getZ() + Math.sin(orbitAngle) * orbitRadius);
			planet.setOwner(entity);
			planet.setOrbitTarget(entity);
			planet.setTarget(target);
			planet.setOrbitRadius(orbitRadius);
			planet.setOrbitSpeed(orbitSpeed);
			planet.setOrbitAngle(orbitAngle);
			planet.setMaxOrbitTicks(250);
			entity.level().addFreshEntity(planet);
			planets.add(planet);
		}
	}

	@Override
	public void tick(SolarCreeper entity) {
		LivingEntity target = entity.getTarget();
		int ticks = entity.getBehaviorTicks();
		if (target == null) {
			return;
		}
		if (ticks >= 50 && ticks <= 250) {
			planets.removeIf(p -> p.isRemoved());
			for (int i = 0; i < planets.size(); i++) {
				PlanetProjectile planet = planets.get(i);
				if (planet.isRemoved() || i >= planetOrbitRadii.length) {
					continue;
				}
				float drift = Mth.clamp((ticks - 50f) / 200f, 0f, 1f);
				float newRadius = Mth.lerp(drift, planetOrbitRadii[i], planetOrbitRadii[i] * 0.5f);
				planet.setOrbitRadius(newRadius);
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}

	@Override
	public void onStop(SolarCreeper entity) {
		planets.clear();
	}
}
