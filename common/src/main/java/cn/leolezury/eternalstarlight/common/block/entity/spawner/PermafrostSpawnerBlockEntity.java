package cn.leolezury.eternalstarlight.common.block.entity.spawner;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class PermafrostSpawnerBlockEntity extends BossSpawnerBlockEntity<Permafrost> {
	public PermafrostSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.PERMAFROST_SPAWNER.get(), ESEntities.PERMAFROST.get(), pos, state);
	}

	@Override
	protected boolean spawnBoss(ServerLevelAccessor accessor) {
		if (!ESConfig.INSTANCE.mobsConfig.permafrost.canSpawn()) {
			return false;
		}
		return super.spawnBoss(accessor);
	}

	@Override
	protected int getRange() {
		return 20;
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ParticleTypes.SNOWFLAKE;
	}
}
