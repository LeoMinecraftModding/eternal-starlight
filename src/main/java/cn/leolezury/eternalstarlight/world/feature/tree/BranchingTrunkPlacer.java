package cn.leolezury.eternalstarlight.world.feature.tree;

import cn.leolezury.eternalstarlight.init.PlacerInit;
import cn.leolezury.eternalstarlight.util.VoxelBresenhamIterator;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class BranchingTrunkPlacer extends TrunkPlacer {
    public static final Codec<BranchingTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).and(IntProvider.codec(0, 24).fieldOf("branch_length").forGetter((placer) -> placer.branchLen)).and(IntProvider.codec(0, 10).fieldOf("branch_num").forGetter((placer) -> placer.branchNum)).apply(instance, BranchingTrunkPlacer::new));

    private final IntProvider branchLen;
    private final IntProvider branchNum;


    public BranchingTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB, IntProvider branchLen, IntProvider branchNum) {
        super(baseHeight, randomHeightA, randomHeightB);
        this.branchLen = branchLen;
        this.branchNum = branchNum;
    }


    protected TrunkPlacerType<BranchingTrunkPlacer> type() {
        return PlacerInit.TRUNK_BRANCHING.get();
    }


    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> placer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> leafAttachments = Lists.newArrayList();

        for (int y = 0; y <= height; y++) {
            if (!placeLog(reader, placer, random, startPos.above(y), config)) {
                height = y;
                break;
            }
        }
        leafAttachments.add(new FoliagePlacer.FoliageAttachment(startPos.above(height), 0, false));

        int numBranches = branchNum.sample(random);

        for (int i = 0; i < numBranches; i++) {
            int len = branchLen.sample(random);
            placeBranch(reader, placer, startPos, leafAttachments, height - numBranches + i, len, 0.3 * i + random.nextFloat(), 0.25, random, config);
        }

        return leafAttachments;
    }

    public static BlockPos rotationToPosition(BlockPos pos, double distance, double angle, double tilt) {
        double angle1 = angle * 2.0D * Math.PI;
        double tilt1 = tilt * Math.PI;
        return pos.offset((int) Math.round(Math.sin(angle1) * Math.sin(tilt1) * distance), (int) Math.round(Math.cos(tilt1) * distance), (int) Math.round(Math.cos(angle1) * Math.sin(tilt1) * distance));
    }

    private void placeBranch(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> placer, BlockPos pos, List<FoliagePlacer.FoliageAttachment> leafAttachments, int height, double length, double angle, double roll, RandomSource randomSource, TreeConfiguration config) {
        BlockPos src = pos.above(height);
        BlockPos dest = rotationToPosition(src, length, angle, roll);
        
        drawBresenhamBranch(reader, placer, randomSource, src, dest, config);

        placeLog(reader, placer, randomSource, dest.east(), config);
        placeLog(reader, placer, randomSource, dest.west(), config);
        placeLog(reader, placer, randomSource, dest.south(), config);
        placeLog(reader, placer, randomSource, dest.north(), config);

        leafAttachments.add(new FoliagePlacer.FoliageAttachment(dest, 0, false));
    }

    private void drawBresenhamBranch(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> placer, RandomSource random, BlockPos from, BlockPos to, TreeConfiguration config) {
        for (BlockPos pos : new VoxelBresenhamIterator(from, to))
            placeLog(reader, placer, random, pos, config);
    }
}
