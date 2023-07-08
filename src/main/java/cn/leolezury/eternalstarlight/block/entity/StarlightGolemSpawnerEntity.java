package cn.leolezury.eternalstarlight.block.entity;

import cn.leolezury.eternalstarlight.entity.boss.StarlightGolem;
import cn.leolezury.eternalstarlight.init.BlockEntityInit;
import cn.leolezury.eternalstarlight.init.EntityInit;
import cn.leolezury.eternalstarlight.init.ParticleInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class StarlightGolemSpawnerEntity extends BossSpawnerBlockEntity<StarlightGolem> {
    public StarlightGolemSpawnerEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.STARLIGHT_GOLEM_SPAWNER.get(), EntityInit.STARLIGHT_GOLEM.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return ParticleInit.ENERGY.get();
    }
}
