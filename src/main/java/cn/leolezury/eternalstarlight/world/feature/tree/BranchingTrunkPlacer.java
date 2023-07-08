package cn.leolezury.eternalstarlight.world.feature.tree;

import cn.leolezury.eternalstarlight.init.PlacerInit;
import cn.leolezury.eternalstarlight.util.VoxelBresenhamIterator;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class BranchingTrunkPlacer extends TrunkPlacer {
    public static final Codec<BranchingTrunkPlacer> CODEC = RecordCodecBuilder.create((p_70090_) -> {
        return trunkPlacerParts(p_70090_).apply(p_70090_, BranchingTrunkPlacer::new);
    });

    public BranchingTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB) {
        super(baseHeight, randomHeightA, randomHeightB);
    }


    protected TrunkPlacerType<BranchingTrunkPlacer> type() {
        return PlacerInit.TRUNK_BRANCHING.get();
    }


    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader worldReader, BiConsumer<BlockPos, BlockState> worldPlacer, RandomSource random, int height, BlockPos startPos, TreeConfiguration treeConfig) {
        List<FoliagePlacer.FoliageAttachment> leafAttachments = Lists.newArrayList();

        for (int y = 0; y <= height; y++) {
            if (!placeLog(worldReader, worldPlacer, random, startPos.above(y), treeConfig)) {
                height = y;

                break;
            }
        }
        leafAttachments.add(new FoliagePlacer.FoliageAttachment(startPos.above(height), 0, false));

        int numBranches = 2 + random.nextInt(2 + 1);
        float offset = random.nextFloat();
        for (int b = 0; b < numBranches; b++) {
            buildBranch(worldReader, worldPlacer, startPos, leafAttachments, height - 6 + b, 6, 0.3 * b + offset, 0.25, random, treeConfig, false);
        }

        return leafAttachments;
    }

    public static BlockPos translate(BlockPos pos, double distance, double angle, double tilt) {
        double rangle = angle * 2.0D * Math.PI;
        double rtilt = tilt * Math.PI;

        return pos.offset(
                (int) Math.round(Math.sin(rangle) * Math.sin(rtilt) * distance),
                (int) Math.round(Math.cos(rtilt) * distance),
                (int) Math.round(Math.cos(rangle) * Math.sin(rtilt) * distance));
    }

    private void buildBranch(LevelSimulatedReader worldReader, BiConsumer<BlockPos, BlockState> worldPlacer, BlockPos pos, List<FoliagePlacer.FoliageAttachment> leafBlocks, int height, double length, double angle, double tilt, RandomSource treeRNG, TreeConfiguration treeConfig, boolean perpendicularBranches) {
        BlockPos src = pos.above(height);
        BlockPos dest = translate(src, length, angle, tilt);

        if (perpendicularBranches) {
            drawBresenhamBranch(worldReader, worldPlacer, treeRNG, src, new BlockPos(dest.getX(), src.getY(), dest.getZ()), treeConfig);

            int max = Math.max(src.getY(), dest.getY());

            for (int i = Math.min(src.getY(), dest.getY()); i < max + 1; i++) {
                placeLog(worldReader, worldPlacer, treeRNG, new BlockPos(dest.getX(), i, dest.getZ()), treeConfig);
            }
        } else {
            drawBresenhamBranch(worldReader, worldPlacer, treeRNG, src, dest, treeConfig);
        }

        placeLog(worldReader, worldPlacer, treeRNG, dest.east(), treeConfig);
        placeLog(worldReader, worldPlacer, treeRNG, dest.west(), treeConfig);
        placeLog(worldReader, worldPlacer, treeRNG, dest.south(), treeConfig);
        placeLog(worldReader, worldPlacer, treeRNG, dest.north(), treeConfig);

        leafBlocks.add(new FoliagePlacer.FoliageAttachment(dest, 0, false));
    }

    private void drawBresenhamBranch(LevelSimulatedReader worldReader, BiConsumer<BlockPos, BlockState> worldPlacer, RandomSource random, BlockPos from, BlockPos to, TreeConfiguration config) {
        for (BlockPos pixel : new VoxelBresenhamIterator(from, to))
            placeLog(worldReader, worldPlacer, random, pixel, config);
    }
}
