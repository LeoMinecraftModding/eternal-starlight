package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

public class LunarMonstrositySoulPhase extends BehaviorPhase<LunarMonstrosity> {
	public static final int ID = 8;

	public LunarMonstrositySoulPhase() {
		super(ID, 1, 100, 0);
	}

	@Override
	public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
		return false;
	}

	@Override
	public void tick(LunarMonstrosity entity) {
		if (entity.level() instanceof ServerLevel serverLevel && entity.getBehaviorTicks() < 70) {
			for (int i = 0; i < 2; i++) {
				RandomSource random = entity.getRandom();
				double dx = (random.nextDouble() * 4 + 2) * (random.nextBoolean() ? 1 : -1);
				double dy = (random.nextDouble() * 4 + 2) * (random.nextBoolean() ? 1 : -1);
				double dz = (random.nextDouble() * 4 + 2) * (random.nextBoolean() ? 1 : -1);
				ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, new ParticlePacket(GatheringTrailParticleOptions.SOUL, entity.getX() - dx, entity.getY() + entity.getBbHeight() / 2 - dy, entity.getZ() - dz, dx, dy, dz));
			}
		}
		if (entity.getBehaviorTicks() == 70) {
			entity.playSound(ESSoundEvents.LUNAR_MONSTROSITY_ROAR.get(), 0.5f, 1);
			entity.knockbackNearbyEntities(5, 2.5f, false);
			if (entity.level() instanceof ServerLevel serverLevel) {
				ScreenShakeVfx.createInstance(entity.level().dimension(), entity.position(), 30, 30, 0.15f, 0.24f, 4, 5).send(serverLevel);
			}
		}
	}

	@Override
	public boolean canContinue(LunarMonstrosity entity) {
		return true;
	}
}
