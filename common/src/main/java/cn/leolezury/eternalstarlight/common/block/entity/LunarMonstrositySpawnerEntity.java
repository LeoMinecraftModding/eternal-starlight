package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.entity.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class LunarMonstrositySpawnerEntity extends BossSpawnerBlockEntity<LunarMonstrosity> {
    public LunarMonstrositySpawnerEntity(BlockPos pos, BlockState state) {
        super(ESBlockEntities.LUNAR_MONSTROSITY_SPAWNER.get(), ESEntities.LUNAR_MONSTROSITY.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return ESParticles.POISON.get();
    }
}
