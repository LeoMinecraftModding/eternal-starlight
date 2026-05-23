package cn.leolezury.eternalstarlight.common.block.entity.spawner;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class LunarMonstrositySpawnerBlockEntity extends BossSpawnerBlockEntity<LunarMonstrosity> {
	public LunarMonstrositySpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.LUNAR_MONSTROSITY_SPAWNER.get(), ESEntities.LUNAR_MONSTROSITY.get(), pos, state);
	}

	@Override
	protected boolean spawnBoss(Level level) {
		if (!ESConfig.INSTANCE.mobsConfig.lunarMonstrosity.canSpawn()) {
			return false;
		}
		return super.spawnBoss(level);
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return ESSmokeParticleOptions.LUNAR_SHORT;
	}
}
