package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.entity.boss.StarlightGolem;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class StarlightGolemSpawnerEntity extends BossSpawnerBlockEntity<StarlightGolem> {
    public StarlightGolemSpawnerEntity(BlockPos pos, BlockState state) {
        super(ESBlockEntities.STARLIGHT_GOLEM_SPAWNER.get(), ESEntities.STARLIGHT_GOLEM.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return ESParticles.ENERGY.get();
    }
}
