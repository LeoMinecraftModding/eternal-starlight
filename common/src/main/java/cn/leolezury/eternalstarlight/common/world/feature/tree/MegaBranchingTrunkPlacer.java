package cn.leolezury.eternalstarlight.common.world.feature.tree;

import cn.leolezury.eternalstarlight.common.init.PlacerInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class MegaBranchingTrunkPlacer extends TrunkPlacer {
    public static final Codec<MegaBranchingTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).and(IntProvider.codec(0, 24).fieldOf("branch_length").forGetter((placer) -> placer.branchLen)).and(IntProvider.codec(0, 10).fieldOf("branch_num").forGetter((placer) -> placer.branchNum)).apply(instance, MegaBranchingTrunkPlacer::new));

    private final IntProvider branchLen;
    private final IntProvider branchNum;


    public MegaBranchingTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB, IntProvider branchLen, IntProvider branchNum) {
        super(baseHeight, randomHeightA, randomHeightB);
        this.branchLen = branchLen;
        this.branchNum = branchNum;
    }


    protected TrunkPlacerType<MegaBranchingTrunkPlacer> type() {
        return PlacerInit.MEGA_TRUNK_BRANCHING.get();
    }


    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> placer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        int numBranches = branchNum.sample(random);
        int lenBranches = branchLen.sample(random);
        return genBranchAt(reader, placer, startPos, random, height, numBranches, lenBranches, true, config);
    }

    protected void placeLogWithRotation(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer, RandomSource random, BlockPos pos, BlockPos centerPos, int pitch, int roll, TreeConfiguration config) {
        placeLog(level, placer, random, ESUtil.rotateBlockPos(centerPos, pos, pitch, roll), config);
    }

    private List<FoliagePlacer.FoliageAttachment> genBranchAt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer, BlockPos origin, RandomSource random, int len, int numBranches, int lenBranches, boolean mainTrunk, TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> leafAttachments = Lists.newArrayList();

        int pitch = mainTrunk ? random.nextInt(-10, 10) : (random.nextBoolean() ? random.nextInt(30, 50) : random.nextInt(-50, -30));
        int roll = mainTrunk ? random.nextInt(-10, 10) : (random.nextBoolean() ? random.nextInt(30, 50) : random.nextInt(-50, -30));
        int i = len / (mainTrunk ? 5 : 2);
        int yStart = mainTrunk ? -5 : 0;
        int r = mainTrunk ? 1 : 0;

        // generate the top leaves
        BlockPos topLeavesPos = ESUtil.rotateBlockPos(origin, origin.offset(0, len, 0), pitch, roll);
        leafAttachments.add(new FoliagePlacer.FoliageAttachment(topLeavesPos, 0, false));

        for (int y = yStart; y <= len; y++) {
            if (mainTrunk) {
                for (int x = -r; x <= r; x++) {
                    for (int z = -r; z <= r; z++) {
                        if (x == r && (z == r || z == -r) || x == -r && (z == r || z == -r)) {
                            continue;
                        }
                        if (y > len / numBranches && y < len && y % i == 0 && (x == -r || x == r || z == -r || z == r)) {
                            BlockPos rotated = ESUtil.rotateBlockPos(origin, origin.offset(x, y, z), pitch, roll);
                            // leafAttachments.add(new FoliagePlacer.FoliageAttachment(rotated, 0, false));
                            leafAttachments.addAll(genBranchAt(level, placer, rotated, random, lenBranches, numBranches, lenBranches, false, config));
                        }
                        placeLogWithRotation(level, placer, random, origin.offset(x, y, z), origin, pitch, roll, config);
                    }
                }
            } else {
                placeLogWithRotation(level, placer, random, origin.offset(0, y, 0), origin, pitch, roll, config);
            }
        }

        return leafAttachments;
    }
}
