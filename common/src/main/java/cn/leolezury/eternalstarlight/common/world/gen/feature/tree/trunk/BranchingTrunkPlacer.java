package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.trunk;

import cn.leolezury.eternalstarlight.common.init.PlacerInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.BiConsumer;

public class BranchingTrunkPlacer extends TrunkPlacer {
    public static final Codec<BranchingTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance)
            .and(IntProvider.codec(0, 3).fieldOf("trunk_radius").forGetter((placer) -> placer.trunkRadius))
            .and(IntProvider.codec(0, 24).fieldOf("branch_length").forGetter((placer) -> placer.branchLen))
            .and(IntProvider.codec(0, 10).fieldOf("branch_layer_num").forGetter((placer) -> placer.branchLayerNum))
            .and(IntProvider.codec(0, 10).fieldOf("branch_num").forGetter((placer) -> placer.branchNum))
            .apply(instance, BranchingTrunkPlacer::new));

    private final IntProvider trunkRadius;
    private final IntProvider branchLen;
    private final IntProvider branchLayerNum;
    private final IntProvider branchNum;
    private static final double SQRT_3 = Math.sqrt(3);

    public BranchingTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB, IntProvider branchLen, IntProvider branchNum) {
        this(baseHeight, randomHeightA, randomHeightB, ConstantInt.of(1), branchLen, ConstantInt.of(4), branchNum);
    }

    public BranchingTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB, IntProvider trunkRadius, IntProvider branchLen, IntProvider branchLayerNum, IntProvider branchNum) {
        super(baseHeight, randomHeightA, randomHeightB);
        this.trunkRadius = trunkRadius;
        this.branchLen = branchLen;
        this.branchLayerNum = branchLayerNum;
        this.branchNum = branchNum;
    }

    protected TrunkPlacerType<BranchingTrunkPlacer> type() {
        return PlacerInit.TRUNK_BRANCHING.get();
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> placer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
        int numBranchesLayer = branchLayerNum.sample(random);
        int numBranches = branchNum.sample(random);
        int lenBranches = branchLen.sample(random);
        return placeBranchingTrunk(reader, placer, startPos, random, height, numBranchesLayer, numBranches, lenBranches, config);
    }

    private List<FoliagePlacer.FoliageAttachment> placeBranchingTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer, BlockPos origin, RandomSource random, int height, int numBranchesLayer, int numBranches, int lenBranches, TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> leafAttachments = Lists.newArrayList();

        int distBetweenLayers = height / (2 * numBranchesLayer);
        int radius = trunkRadius.sample(random);

        leafAttachments.add(new FoliagePlacer.FoliageAttachment(origin.offset(0, height, 0), 2, false));

        for (int y = 0; y <= height; y++) {
            boolean shouldAddLayer = (y >= height / 2 && y < height - distBetweenLayers && y % distBetweenLayers == 0) || y == height - 2;
            boolean bigLayer = y == height - 2;
            if (radius == 0) {
                BlockPos pos = origin.offset(0, y, 0);
                if (shouldAddLayer) {
                    BlockPos branchLayerPos;
                    float yawOffset = random.nextFloat() * 360;
                    for (int i = 0; i < numBranches; i++) {
                        branchLayerPos = pos.offset(0, random.nextInt(5) - 2, 0);
                        Vec3 vec3 = ESUtil.rotationToPosition(new Vec3(branchLayerPos.getX(), branchLayerPos.getY(), branchLayerPos.getZ()), (lenBranches - (bigLayer ? 2 : 0) / 2f) * (float) SQRT_3, 30, (360f / (float) numBranches) * i + yawOffset);
                        BlockPos endPos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
                        List<int[]> points = ESUtil.getBresenham3DPoints(branchLayerPos.getX(), branchLayerPos.getY(), branchLayerPos.getZ(), endPos.getX(), endPos.getY(), endPos.getZ());
                        for (int[] point : points) {
                            placeLog(level, placer, random, new BlockPos(point[0], point[1], point[2]), config);
                        }
                        int len = points.size();
                        placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0], points.get(len - 1)[1], points.get(len - 1)[2]), config);
                        placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0] + 1, points.get(len - 1)[1], points.get(len - 1)[2] + 1), config);
                        placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0] + 1, points.get(len - 1)[1], points.get(len - 1)[2] - 1), config);
                        placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0] - 1, points.get(len - 1)[1], points.get(len - 1)[2] + 1), config);
                        placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0] - 1, points.get(len - 1)[1], points.get(len - 1)[2] - 1), config);
                        leafAttachments.add(new FoliagePlacer.FoliageAttachment(endPos, bigLayer ? 1 : 0, false));
                    }
                }
                placeLog(level, placer, random, pos, config);
                if (y == height) {
                    placeLog(level, placer, random, pos.offset(1, 0, 1), config);
                    placeLog(level, placer, random, pos.offset(1, 0, -1), config);
                    placeLog(level, placer, random, pos.offset(-1, 0, 1), config);
                    placeLog(level, placer, random, pos.offset(-1, 0, -1), config);
                }
            } else {
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (x == radius && (z == radius || z == -radius) || x == -radius && (z == radius || z == -radius)) {
                            continue;
                        }
                        BlockPos pos = origin.offset(x, y, z);
                        if (shouldAddLayer && x == 0 & z == 0) {
                            BlockPos branchLayerPos;
                            float yawOffset = random.nextFloat() * 360;
                            for (int i = 0; i < numBranches; i++) {
                                branchLayerPos = pos.offset(0, random.nextInt(5) - 2, 0);
                                Vec3 vec3 = ESUtil.rotationToPosition(new Vec3(branchLayerPos.getX(), branchLayerPos.getY(), branchLayerPos.getZ()), (lenBranches - (bigLayer ? 2 : 0) / 2f) * (float) SQRT_3, 30, (360f / (float) numBranches) * i + yawOffset);
                                BlockPos endPos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
                                List<int[]> points = ESUtil.getBresenham3DPoints(branchLayerPos.getX(), branchLayerPos.getY(), branchLayerPos.getZ(), endPos.getX(), endPos.getY(), endPos.getZ());
                                for (int[] point : points) {
                                    placeLog(level, placer, random, new BlockPos(point[0], point[1], point[2]), config);
                                }
                                int len = points.size();
                                placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0] + 1, points.get(len - 1)[1], points.get(len - 1)[2] + 1), config);
                                placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0] + 1, points.get(len - 1)[1], points.get(len - 1)[2] - 1), config);
                                placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0] - 1, points.get(len - 1)[1], points.get(len - 1)[2] + 1), config);
                                placeLog(level, placer, random, new BlockPos(points.get(len - 1)[0] - 1, points.get(len - 1)[1], points.get(len - 1)[2] - 1), config);
                                leafAttachments.add(new FoliagePlacer.FoliageAttachment(endPos, bigLayer ? 1 : 0, false));
                            }
                        }
                        placeLog(level, placer, random, pos, config);
                        if (y == height) {
                            placeLog(level, placer, random, pos.offset(1, 0, 1), config);
                            placeLog(level, placer, random, pos.offset(1, 0, -1), config);
                            placeLog(level, placer, random, pos.offset(-1, 0, 1), config);
                            placeLog(level, placer, random, pos.offset(-1, 0, -1), config);
                        }
                    }
                }
            }
        }

        return leafAttachments;
    }
}
