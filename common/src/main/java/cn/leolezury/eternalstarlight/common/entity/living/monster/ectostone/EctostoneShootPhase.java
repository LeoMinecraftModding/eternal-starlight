package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.MagicStone;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ParticleFacing;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.EasingCurve;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class EctostoneShootPhase extends BehaviorPhase<Ectostone> {
	public static final int ID = 1;

	public EctostoneShootPhase() {
		super(ID, 1, 35, 160);
	}

	@Override
	public boolean canStart(Ectostone entity, boolean cooldownOver) {
		return cooldownOver && !entity.isDormant() && canReachTarget(entity, 20);
	}

	@Override
	public void tick(Ectostone entity) {
		Level level = entity.level();
		if (entity.getTarget() != null) {
			LivingEntity target = entity.getTarget();
			Vec3 aimPos = target.position().add(0, target.getEyeHeight(), 0);
			ESEntityUtil.instantLook(entity, aimPos);
			Vec3 launchPos = entity.position().add(0, entity.getBbHeight() / 2f, 0);

			if (entity.getBehaviorTicks() == 10) {
				Vec3 direction = Vec3.directionFromRotation(0.0F, entity.yHeadRot).scale(0.8);
				entity.setDeltaMovement(-direction.x(), 0.0, -direction.z());
				Vec3 delta = aimPos.subtract(launchPos).normalize();
				for (int i = 0; i < 4; i++) {
					MagicStone stone = new MagicStone(level, entity);
					stone.shoot(delta.x, delta.y, delta.z, 1.25f, 16.0f);
					stone.setPos(launchPos);
					stone.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 0.7f, 1.2f);
					level.addFreshEntity(stone);
				}
				if (entity.level() instanceof ServerLevel serverLevel) {
					Vec3 pos = entity.position().add(Vec3.directionFromRotation(0.0F, entity.yBodyRot).scale(0.5));
					RippleParticleOptions ripple = new RippleParticleOptions(ESParticles.RIPPLE.get(),
						ParticleFacing.fromNormal(delta),
						EasingCurve.of(Easing.OUT_QUAD, 0, 0.6f, 1),
						EasingCurve.of(Easing.IN_OUT_QUAD, 0.25f, 0, 1),
						new Vector3f(232 / 255f, 117 / 255f, 194 / 255f), 10);
					ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(ripple, pos.x, pos.y + 0.625 * entity.getBbHeight(), pos.z, 0, 0, 0));
					for (int i = 0; i < 5; i++) {
						Vec3 speed = new Vec3(entity.getRandom().nextFloat() - entity.getRandom().nextFloat(), entity.getRandom().nextFloat() - entity.getRandom().nextFloat(), entity.getRandom().nextFloat() - entity.getRandom().nextFloat()).normalize();
						ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(
							ExplosionShockParticleOptions.ECTOSTONE,
							pos.x, pos.y + 0.625 * entity.getBbHeight(), pos.z,
							speed.x, speed.y, speed.z));
					}
				}
			}
		}
	}

}