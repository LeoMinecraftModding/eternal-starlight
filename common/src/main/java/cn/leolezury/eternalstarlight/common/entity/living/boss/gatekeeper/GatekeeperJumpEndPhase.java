package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.item.combat.HammerItem;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class GatekeeperJumpEndPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 4;

	public GatekeeperJumpEndPhase() {
		super(ID, 1, 22, 0);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(TheGatekeeper entity) {
		Level level = entity.level();
		LivingEntity target = entity.getTarget();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
		}
		if (entity.getBehaviorTicks() == 2) {
			boolean hammer = entity.getMainHandItem().getItem() instanceof HammerItem;
			entity.playSound(SoundEvents.MACE_SMASH_GROUND_HEAVY);
			BlockHitResult toGround = level.clip(new ClipContext(entity.position().add(0, entity.getBbHeight(), 0), entity.position().subtract(0, 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
			if (toGround.getType() != HitResult.Type.MISS && level instanceof ServerLevel serverLevel) {
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.SHOCKWAVE, entity.getX(), entity.getY(), entity.getZ(), 0, 0.1, 0));
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(entity.blockPosition().below())), entity.getX(), entity.getY(), entity.getZ(), 150, 0.5, 0.5, 0.5, 0.15);
				ScreenShakeVfx.createInstance(level.dimension(), entity.position(), 40, 20, 0.2f, 0.3f, 3, 5.5f).send(serverLevel);
			}
			performDefaultMeleeAttack(entity, hammer ? 3 : 2, true, 360, e -> {
				e.hurtMarked = true;
				e.addDeltaMovement(e.position().subtract(entity.position()).normalize().multiply(1.25, 0.5, 1.25));
			});
			if (hammer) {
				for (int x = -3; x <= 3; x++) {
					for (int y = -2; y <= 2; y++) {
						for (int z = -3; z <= 3; z++) {
							BlockPos pos = entity.blockPosition().offset(x, y, z);
							if (level.getBlockState(pos.above()).isAir() && pos.getCenter().distanceTo(entity.position()) <= 3) {
								ESFallingBlock fallingBlock = new ESFallingBlock(level, pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, level.getBlockState(pos), 100, false);
								fallingBlock.push(0, entity.getRandom().nextDouble() / 6 + 0.25, 0);
								level.addFreshEntity(fallingBlock);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canContinue(TheGatekeeper entity) {
		return true;
	}

	@Override
	public void stop(TheGatekeeper entity, BehaviorManager<TheGatekeeper> manager) {
		entity.setBehaviorState(0);
		entity.setBehaviorTicks(0);
		int newId = canReachTarget(entity, 5) ? GatekeeperStepBackPhase.ID : 0;
		if (manager.getCooldowns().getOrDefault(newId, 0) <= 0) {
			manager.getAllPhases().stream().filter(p -> newId == p.getId()).findFirst().ifPresent(p -> p.start(entity, manager));
		}
		onStop(entity);
	}
}
