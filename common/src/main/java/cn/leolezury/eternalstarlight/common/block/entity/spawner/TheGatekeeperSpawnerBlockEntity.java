package cn.leolezury.eternalstarlight.common.block.entity.spawner;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TheGatekeeperSpawnerBlockEntity extends BossSpawnerBlockEntity<TheGatekeeper> {
	public TheGatekeeperSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.THE_GATEKEEPER_SPAWNER.get(), ESEntities.THE_GATEKEEPER.get(), pos, state);
	}

	@Override
	protected boolean spawnBoss(Level level) {
		if (!ESConfig.INSTANCE.mobsConfig.theGatekeeper.canSpawn()) {
			return false;
		}
		return super.spawnBoss(level);
	}

	@Override
	protected void initializeBoss(TheGatekeeper mob) {
		super.initializeBoss(mob);
		mob.setStandardFight(true);
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ParticleTypes.CRIT;
	}
}
