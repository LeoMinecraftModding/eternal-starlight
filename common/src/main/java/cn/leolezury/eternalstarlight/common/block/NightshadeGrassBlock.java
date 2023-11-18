package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public class NightshadeGrassBlock extends SpreadingSnowyNightshadeDirtBlock implements BonemealableBlock {
    private final ResourceKey<PlacedFeature> grassFeature;

    public NightshadeGrassBlock(BlockBehaviour.Properties properties, Supplier<? extends Block> spreadOn, ResourceKey<PlacedFeature> grassFeature) {
        super(properties, spreadOn);
        this.grassFeature = grassFeature;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos pos, BlockState state, boolean b) {
        return levelReader.getBlockState(pos.above()).isAir();
    }

    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = Blocks.GRASS.defaultBlockState();

        label46:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;

            for(int j = 0; j < i / 16; ++j) {
                blockpos1 = blockpos1.offset(randomSource.nextInt(3) - 1, (randomSource.nextInt(3) - 1) * randomSource.nextInt(3) / 2, randomSource.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockpos1.below()).is(this) || serverLevel.getBlockState(blockpos1).isCollisionShapeFullBlock(serverLevel, blockpos1)) {
                    continue label46;
                }
            }

            BlockState blockstate1 = serverLevel.getBlockState(blockpos1);
            if (blockstate1.is(blockstate.getBlock()) && randomSource.nextInt(10) == 0) {
                ((BonemealableBlock)blockstate.getBlock()).performBonemeal(serverLevel, randomSource, blockpos1, blockstate1);
            }

            if (blockstate1.isAir()) {
                Holder<PlacedFeature> holder;

                HolderGetter<PlacedFeature> placedFeatureHolderGetter =  serverLevel.holderLookup(Registries.PLACED_FEATURE);
                holder = placedFeatureHolderGetter.getOrThrow(grassFeature);

                holder.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), randomSource, blockpos1);
            }
        }

    }
}
