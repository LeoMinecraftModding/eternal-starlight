package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.attack.SolarStrikeEntity;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SolarCreeperJumpEndPhase extends BehaviorPhase<SolarCreeper> {
	public static final int ID = 6;

	public SolarCreeperJumpEndPhase() {
		super(ID, 1, 22, 0);
	}

	@Override
	public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(SolarCreeper entity) {
		Level level = entity.level();
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		if (entity.getBehaviorTicks() == 2) {
			BlockHitResult toGround = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			if (toGround.getType() != HitResult.Type.MISS && level instanceof ServerLevel serverLevel) {
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.FLARE, entity.getX(), entity.getY(), entity.getZ(), 0, 0.1, 0));
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(entity.getOnPos())), entity.getX(), entity.getY(), entity.getZ(), 150, 0.5, 0.5, 0.5, 0.15);
				ScreenShakeVfx.createInstance(level.dimension(), entity.position(), 40, 20, 0.2f, 0.3f, 3, 5.5f).send(serverLevel);
			}
			performDefaultMeleeAttack(entity, 3, true, 180, e -> {
				e.hurtMarked = true;
				e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(1.25, 0.5, 1.25));
			});
			for (int i = 0; i < 8; i++) {
				float radius = (i + 1) * 1.2f;
				int num = 5;
				float startAngle = i * 0.06f * Mth.PI;
				for (int j = 0; j < num; j++) {
					float angle = startAngle + (Mth.TWO_PI / num) * j;
					SolarStrikeEntity strike = new SolarStrikeEntity(ESEntities.SOLAR_STRIKE.get(), entity.level());
					strike.setPos(entity.getX() + Math.cos(angle) * radius, entity.getY(), entity.getZ() + Math.sin(angle) * radius);
					strike.setOwner(entity);
					strike.setChargeDuration(25 + i * 8);
					strike.setBeamTarget(strike.position().add((entity.getRandom().nextDouble() - 0.5) * 2, 20, (entity.getRandom().nextDouble() - 0.5) * 2));
					entity.level().addFreshEntity(strike);
				}
			}
		}
	}

	@Override
	public boolean canContinue(SolarCreeper entity) {
		return true;
	}
}
