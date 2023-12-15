package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.phys.Vec3;

public class AbyssalGeyserBlockEntity extends BlockEntity {
    private int ticksSinceLastErupt = 0;
    private final RandomSource random;

    public AbyssalGeyserBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityInit.ABYSSAL_GEYSER.get(), blockPos, blockState);
        this.random = new LegacyRandomSource(blockPos.asLong());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AbyssalGeyserBlockEntity entity) {
        entity.ticksSinceLastErupt++;
        entity.ticksSinceLastErupt = entity.ticksSinceLastErupt % 800; // 30 sec + 10 sec
        if (entity.ticksSinceLastErupt <= 200) { // erupt for 10 sec
            if (level.isClientSide) {
                Vec3 particlePos = pos.getCenter().add(0, 0.4, 0);
                level.addParticle(ParticleTypes.SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
                level.addParticle(ParticleTypes.LARGE_SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
                level.addParticle(ParticleTypes.WHITE_SMOKE, particlePos.x, particlePos.y, particlePos.z, (entity.random.nextFloat() - 0.5) / 5, 0.2 + entity.random.nextFloat() / 1.5, (entity.random.nextFloat() - 0.5) / 5);
            } else {
                // convert items above
            }
        }
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.ticksSinceLastErupt = compoundTag.getInt("TicksSinceLastErupt");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("TicksSinceLastErupt", this.ticksSinceLastErupt);
    }
}
