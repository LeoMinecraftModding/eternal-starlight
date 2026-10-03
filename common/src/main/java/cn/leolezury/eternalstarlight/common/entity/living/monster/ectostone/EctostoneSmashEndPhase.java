package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class EctostoneSmashEndPhase extends BehaviorPhase<Ectostone> {
	public static final int ID = 4;

	public EctostoneSmashEndPhase() {
		super(ID, 1, 35, 0);
	}

	@Override
	public boolean canStart(Ectostone entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(Ectostone entity) {
		Level level = entity.level();
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		BlockHitResult ground = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
		if (ground.getType() != HitResult.Type.MISS) {
			entity.setPos(ground.getLocation());
		}
		if (entity.getBehaviorTicks() == 3) {
			BlockHitResult toGround = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			if (toGround.getType() == HitResult.Type.MISS || !(level instanceof ServerLevel serverLevel)) {
				return;
			}
			for (int i = 0; i < 5; i++) {
				Vec3 speed = new Vec3((entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F, entity.getRandom().nextFloat() * 0.05F, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F).normalize();
				ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(
					ExplosionShockParticleOptions.ECTOSTONE,
					entity.getX(), entity.getY() + 0.25 * entity.getBbHeight(), entity.getZ(),
					speed.x, speed.y, speed.z));
			}
			entity.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 0.7f, 1.2f);
			ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.fromIntColor(new Vector3f(255, 71, 180), new Vector3f(151, 63, 115), 2), entity.getX(), entity.getY(), entity.getZ(), 0, 0.1, 0));
			serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(entity.getOnPos())), entity.getX(), entity.getY(), entity.getZ(), 150, 0.5, 0.5, 0.5, 0.15);
			ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 40, 20, 0.2f, 0.3f, 3, 5.5f).send(serverLevel);
			performDefaultMeleeAttack(entity, 1.5, true, 180, e -> {
				e.hurtMarked = true;
				e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(0.75, 0.5, 0.75));
			});
		}
	}
}
