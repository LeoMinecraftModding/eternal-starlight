package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class PermafrostMeleeEndPhase extends BehaviorPhase<Permafrost> {
	public static final int ID = 3;

	public PermafrostMeleeEndPhase() {
		super(ID, 1, 35, 0);
	}

	@Override
	public boolean canStart(Permafrost entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(Permafrost entity) {
		Level level = entity.level();
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		BlockHitResult result = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
		if (result.getType() != HitResult.Type.MISS) {
			entity.setPos(result.getLocation());
		}
		if (entity.getBehaviorTicks() == 3 || (entity.getBehaviorTicks() >= 10 && entity.getBehaviorTicks() <= 25)) {
			if (entity.getBehaviorTicks() == 3) {
				BlockHitResult toGround = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
				if (toGround.getType() != HitResult.Type.MISS && level instanceof ServerLevel serverLevel) {
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.ENERGY, entity.getX(), entity.getY(), entity.getZ(), 0, 0.1, 0));
					serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(entity.blockPosition().below())), entity.getX(), entity.getY(), entity.getZ(), 150, 0.5, 0.5, 0.5, 0.15);
					ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 40, 20, 0.2f, 0.3f, 3, 5.5f).send(serverLevel);
				}
			}
			performDefaultMeleeAttack(entity, entity.getBehaviorTicks() == 3 ? 4.5 : 1.5, true, 360, e -> {
				e.hurtMarked = true;
				e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(1.25, 0.5, 1.25));
			});
		}
	}

	@Override
	public boolean canContinue(Permafrost entity) {
		return true;
	}

}
