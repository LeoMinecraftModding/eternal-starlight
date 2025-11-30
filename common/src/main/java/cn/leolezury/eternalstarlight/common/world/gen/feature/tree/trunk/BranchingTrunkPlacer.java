package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.trunk;

import cn.leolezury.eternalstarlight.common.registry.ESTreePlacers;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.BiConsumer;

public class BranchingTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<BranchingTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> trunkPlacerParts(instance)
		.and(IntProvider.codec(0, 3).fieldOf("trunk_size").forGetter((placer) -> placer.trunkSize))
		.and(IntProvider.codec(0, 24).fieldOf("branch_length").forGetter((placer) -> placer.branchLen))
		.and(IntProvider.codec(0, 10).fieldOf("branch_layer_num").forGetter((placer) -> placer.branchLayerNum))
		.and(IntProvider.codec(0, 10).fieldOf("branch_num").forGetter((placer) -> placer.branchNum))
		.and(Codec.BOOL.fieldOf("vanilla_giant_trunk").forGetter((placer) -> placer.vanillaGiantTrunk))
		.apply(instance, BranchingTrunkPlacer::new));

	private final IntProvider trunkSize;
	private final IntProvider branchLen;
	private final IntProvider branchLayerNum;
	private final IntProvider branchNum;
	private final boolean vanillaGiantTrunk;
	private static final double SQRT_3 = Math.sqrt(3);

	public BranchingTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB, IntProvider branchLen, IntProvider branchNum) {
		this(baseHeight, randomHeightA, randomHeightB, ConstantInt.of(1), branchLen, ConstantInt.of(4), branchNum, false);
	}

	public BranchingTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB, IntProvider trunkSize, IntProvider branchLen, IntProvider branchLayerNum, IntProvider branchNum, boolean vanillaGiantTrunk) {
		super(baseHeight, randomHeightA, randomHeightB);
		this.trunkSize = trunkSize;
		this.branchLen = branchLen;
		this.branchLayerNum = branchLayerNum;
		this.branchNum = branchNum;
		this.vanillaGiantTrunk = vanillaGiantTrunk;
	}

	@Override
	protected TrunkPlacerType<BranchingTrunkPlacer> type() {
		return ESTreePlacers.TRUNK_BRANCHING.get();
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> placer, RandomSource random, int height, BlockPos startPos, TreeConfiguration config) {
		int numBranchesLayer = branchLayerNum.sample(random);
		int numBranches = branchNum.sample(random);
		int lenBranches = branchLen.sample(random);
		// this seems to make no sense, but it actually fixed a stupid block update bug that occurs when a mega tree is placed
		// hideous code, but just works
		for (int x = 0; x <= 1; x++) {
			for (int z = 0; z <= 1; z++) {
				BlockPos pos = startPos.offset(x, 0, z);
				if (reader.isStateAtPosition(pos, state -> state.isAir() || state.is(BlockTags.SAPLINGS))) {
					placer.accept(pos, config.trunkProvider.getState(random, pos));
					placer.accept(pos, Blocks.AIR.defaultBlockState());
				}
			}
		}
		return placeBranchingTrunk(reader, placer, startPos, random, height, numBranchesLayer, numBranches, lenBranches, config);
	}

	private List<FoliagePlacer.FoliageAttachment> placeBranchingTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer, BlockPos origin, RandomSource random, int height, int numBranchesLayer, int numBranches, int lenBranches, TreeConfiguration config) {
		List<FoliagePlacer.FoliageAttachment> leafAttachments = Lists.newArrayList();

		int distBetweenLayers = height / (2 * numBranchesLayer);
		int size = trunkSize.sample(random);

		leafAttachments.add(new FoliagePlacer.FoliageAttachment(origin.offset(0, height, 0), 2, false));

		for (int y = 0; y <= height; y++) {
			boolean shouldAddLayer = (y >= height / 2 && y < height - distBetweenLayers && y % distBetweenLayers == 0) || y == height - 2;
			boolean bigLayer = y == height - 2;
			int startValue = vanillaGiantTrunk ? -(int) Math.floor((size - 1) / 2.0) : -size;
			int endValue = vanillaGiantTrunk ? (int) Math.ceil((size - 1) / 2.0) : size;
			for (int x = startValue; x <= endValue; x++) {
				for (int z = startValue; z <= endValue; z++) {
					if (size != 0 && !vanillaGiantTrunk && (x == size && (z == size || z == -size) || x == -size && (z == size || z == -size))) {
						continue;
					}
					safeSetDirt(level, placer, random, origin.offset(x, -1, z), config);
					BlockPos pos = origin.offset(x, y, z);
					if (shouldAddLayer && x == 0 & z == 0) {
						BlockPos branchLayerPos;
						float yawOffset = random.nextFloat() * 360;
						for (int i = 0; i < numBranches; i++) {
							branchLayerPos = pos.offset(0, random.nextInt(5) - 2, 0);
							Vec3 vec3 = ESMathUtil.rotationToPosition(new Vec3(branchLayerPos.getX(), branchLayerPos.getY(), branchLayerPos.getZ()), (lenBranches - (bigLayer ? 2 : 0) / 2f) * (float) SQRT_3, 30, (360f / (float) numBranches) * i + yawOffset);
							BlockPos endPos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
							List<int[]> points = ESMathUtil.getBresenham3DPoints(branchLayerPos.getX(), branchLayerPos.getY(), branchLayerPos.getZ(), endPos.getX(), endPos.getY(), endPos.getZ());
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

		return leafAttachments;
	}

	protected static void safeSetDirt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> placer, RandomSource random, BlockPos pos, TreeConfiguration config) {
		if ((level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir) || level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::canBeReplaced) || level.isStateAtPosition(pos, state -> state.getBlock() instanceof LiquidBlock)) && (config.forceDirt || !isDirt(level, pos))) {
			placer.accept(pos, config.dirtProvider.getState(random, pos));
		}
	}

	private static boolean isDirt(LevelSimulatedReader level, BlockPos blockPos) {
		return level.isStateAtPosition(blockPos, (blockState) -> Feature.isDirt(blockState) && !blockState.is(Blocks.GRASS_BLOCK) && !blockState.is(Blocks.MYCELIUM));
	}
}
