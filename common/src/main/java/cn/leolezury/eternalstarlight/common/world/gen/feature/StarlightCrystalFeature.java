package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.MergedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.RotatedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.SpikeProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.ValueMapGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class StarlightCrystalFeature extends ESFeature<NoneFeatureConfiguration> {
	public StarlightCrystalFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		boolean isRed = random.nextBoolean();
		BlockState crystalState = isRed ? ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState() : ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get().defaultBlockState();
		BlockState carpetState = isRed ? ESBlocks.RED_CRYSTAL_MOSS_CARPET.get().defaultBlockState() : ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get().defaultBlockState();
		// generate the spike
		List<MergedProvider.Entry> entries = new ArrayList<>();
		int count = random.nextInt(3, 7);
		if (count == 4 && random.nextBoolean()) {
			count = 3;
		}
		for (int i = 0; i < count; i++) {
			entries.add(new MergedProvider.Entry(new RotatedProvider(new SpikeProvider(1.5f + random.nextFloat(), 10f + random.nextFloat() * 3), random.nextInt(40, 60), (360f / count) * i), Vec3.ZERO));
		}
		entries.add(new MergedProvider.Entry(new SpikeProvider(2f + random.nextFloat(), 16f + random.nextFloat() * 5), Vec3.ZERO));
		ValueMapGenerator.place(new MergedProvider(entries), (pos, value) -> setBlockIfEmpty(level, pos.offset(origin), crystalState));
		// randomly place decorations
		for (int x = -5; x <= 5; x++) {
			for (int y = -4; y <= 4; y++) {
				for (int z = -5; z <= 5; z++) {
					if (x * x + z * z < 5 * 5) {
						if (random.nextBoolean()) {
							if (random.nextInt(10) == 0) {
								List<Direction> possibleDirs = new ArrayList<>();
								for (Direction direction : Direction.values()) {
									BlockPos relativePos = origin.offset(x, y, z).relative(direction);
									if (level.getBlockState(relativePos).is(crystalState.getBlock())) {
										possibleDirs.add(direction);
									}
								}
								if (!possibleDirs.isEmpty()) {
									Direction direction = possibleDirs.get(random.nextInt(possibleDirs.size())).getOpposite();
									BlockState clusterState = isRed ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction) : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction);
									setBlockIfEmpty(level, origin.offset(x, y, z), clusterState);
								}
								BlockPos relativePos = origin.offset(x, y - 1, z);
								if (level.getBlockState(relativePos).isFaceSturdy(level, relativePos, Direction.UP)) {
									BlockState clusterState = random.nextBoolean() ? ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState() : ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get().defaultBlockState();
									setBlockIfEmpty(level, origin.offset(x, y, z), clusterState);
								}
							}
						} else {
							BlockPos relativePos = origin.offset(x, y - 1, z);
							if (level.getBlockState(relativePos).is(ESTags.Blocks.BASE_STONE_STARLIGHT)) {
								setBlockIfEmpty(level, origin.offset(x, y, z), carpetState);
							}
						}
					}
				}
			}
		}
		return true;
	}
}

