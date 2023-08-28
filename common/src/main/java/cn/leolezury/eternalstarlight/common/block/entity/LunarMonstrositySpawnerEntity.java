package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.entity.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.init.BlockEntityInit;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public class LunarMonstrositySpawnerEntity extends BossSpawnerBlockEntity<LunarMonstrosity> {
    public LunarMonstrositySpawnerEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.LUNAR_MONSTROSITY_SPAWNER.get(), EntityInit.LUNAR_MONSTROSITY.get(), pos, state);
    }

    @Override
    public ParticleOptions getSpawnerParticle() {
        return ParticleInit.POISON.get();
    }
}
