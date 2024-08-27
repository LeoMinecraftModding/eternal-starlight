package cn.leolezury.eternalstarlight.common.block.entity.spawner;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.TangledHatred;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class TangledHatredSpawnerBlockEntity extends BossSpawnerBlockEntity<TangledHatred> {
	public TangledHatredSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.TANGLED_HATRED_SPAWNER.get(), ESEntities.TANGLED_HATRED.get(), pos, state);
	}

	@Override
	protected boolean spawnBoss(ServerLevelAccessor accessor) {
		if (!ESConfig.INSTANCE.mobsConfig.tangledHatred.canSpawn()) {
			return false;
		}
		return super.spawnBoss(accessor);
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ESSmokeParticleOptions.LUNAR_SHORT;
	}
}
