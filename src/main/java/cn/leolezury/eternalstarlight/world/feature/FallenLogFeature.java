package cn.leolezury.eternalstarlight.world.feature;

import cn.leolezury.eternalstarlight.util.SLTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FallenLogFeature extends Feature<FallenLogFeature.Configuration> {
    public FallenLogFeature(Codec<FallenLogFeature.Configuration> p_66003_) {
        super(p_66003_);
    }

    protected boolean setBlockIfEmpty(WorldGenLevel p_65791_, BlockPos p_65792_, BlockState p_65793_) {
        if (p_65791_.isEmptyBlock(p_65792_)) {
            setBlock(p_65791_, p_65792_, p_65793_);
            return true;
        }
        return false;
    }

    protected void placeLog(WorldGenLevel worldGenLevel, BlockPos blockPos, BlockStateProvider provider, boolean canReplace, boolean terrainAdapting, boolean ew) {
        RandomSource randomsource = worldGenLevel.getRandom();
        int length = randomsource.nextInt(5) + 4;
        for (int i = 0; i < length; i++) {
            BlockPos pos = blockPos.offset(ew ? i + length / 2 : 0, 0, ew ? 0 : i + length / 2);
            if (terrainAdapting) {
                for(; !worldGenLevel.getBlockState(pos).is(BlockTags.DIRT) && !worldGenLevel.getBlockState(pos).is(BlockTags.SNOW) && !worldGenLevel.getBlockState(pos).is(SLTags.Blocks.BASE_STONE_STARLIGHT) && pos.getY() > worldGenLevel.getMinBuildHeight() + 2; pos = pos.below()) {
                }
                pos = pos.above();
            }
            if (pos.getY() <= worldGenLevel.getMinBuildHeight() + 2) {
                return;
            }
            if (canReplace) {
                setBlock(worldGenLevel, pos, provider.getState(randomsource, pos).setValue(BlockStateProperties.AXIS, ew ? Direction.Axis.X : Direction.Axis.Z));
            } else {
                setBlockIfEmpty(worldGenLevel, pos, provider.getState(randomsource, pos).setValue(BlockStateProperties.AXIS, ew ? Direction.Axis.X : Direction.Axis.Z));
            }
            if (randomsource.nextBoolean()) {

                setBlockIfEmpty(worldGenLevel, pos.offset(ew ? 0 : 1, 0, ew ? 1 : 0), (randomsource.nextBoolean() ? Blocks.VINE : Blocks.GLOW_LICHEN).defaultBlockState().setValue(ew ? BlockStateProperties.NORTH : BlockStateProperties.WEST, true));
            }
            if (randomsource.nextBoolean()) {
                setBlockIfEmpty(worldGenLevel, pos.offset(ew ? 0 : -1, 0, ew ? -1 : 0), (randomsource.nextBoolean() ? Blocks.VINE : Blocks.GLOW_LICHEN).defaultBlockState().setValue(ew ? BlockStateProperties.SOUTH : BlockStateProperties.EAST, true));
            }
        }
    }

    public boolean place(FeaturePlaceContext<FallenLogFeature.Configuration> p_159882_) {
        RandomSource randomsource = p_159882_.random();
        BlockPos pos = p_159882_.origin();
        boolean isLarge = randomsource.nextBoolean();
        BlockStateProvider provider = p_159882_.config().log();
        boolean ew = randomsource.nextBoolean();
        if (!isLarge) {
            placeLog(p_159882_.level(), pos, provider, false, true, ew);
        } else {
            for(; !p_159882_.level().getBlockState(pos).is(BlockTags.DIRT) && !p_159882_.level().getBlockState(pos).is(BlockTags.SNOW) && !p_159882_.level().getBlockState(pos).is(SLTags.Blocks.BASE_STONE_STARLIGHT) && pos.getY() > p_159882_.level().getMinBuildHeight() + 2; pos = pos.below()) {
            }
            pos = pos.below();
            int size = randomsource.nextInt(3) + 2;
            for (int i = 0; i < size + 2; i++) {
                for (int j = 0; j < size + 2; j++) {
                    if ((i == 0 || i == size + 1 || j == 0 || j == size + 1) && !((i == 0 || i == size + 1) && (j == 0 || j == size + 1))) {
                        placeLog(p_159882_.level(), pos.offset(ew ? 0 : i, j, ew ? i : 0), provider, true, false, ew);
                    }
                }
            }
        }
        return true;
    }

    public static record Configuration(BlockStateProvider log) implements FeatureConfiguration {
        public static final Codec<FallenLogFeature.Configuration> CODEC = RecordCodecBuilder.create((p_190962_) -> {
            return p_190962_.group(BlockStateProvider.CODEC.fieldOf("log").forGetter(FallenLogFeature.Configuration::log)).apply(p_190962_, FallenLogFeature.Configuration::new);
        });
    }
}

