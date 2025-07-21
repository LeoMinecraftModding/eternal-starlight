package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class HugeMarimoldFeature extends Feature<HugeMarimoldFeature.Configuration> {
	public HugeMarimoldFeature(Codec<Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Configuration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		Configuration config = context.config();
		List<BlockPos> stemBlocks = new ArrayList<>();
		List<BlockPos> mushroomBlocks = new ArrayList<>();
		int trunkHeight = config.trunkHeight().sample(random);
		int xzRadius = config.capRadius().sample(random);
		int foliageHeight = config.capHeight().sample(random);
		for (int y = 0; y <= trunkHeight; y++) {
			stemBlocks.add(pos.offset(0, y, 0));
		}
		mushroomBlocks.add(pos.offset(0, trunkHeight + 1, 0));
		for (int y = 0; y >= -foliageHeight; y--) {
			int radius = Mth.lerpInt((float) -y / foliageHeight, 0, xzRadius);
			int radiusNext = Mth.lerpInt((float) (1 - y) / foliageHeight, 0, xzRadius);
			if (radius < radiusNext) {
				radius = random.nextInt(radius, radiusNext);
			}
			if (radius < 1) {
				radius = 1;
			}
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					if (x * x + z * z <= radius * radius) {
						mushroomBlocks.add(pos.offset(x, y + trunkHeight, z));
					}
				}
			}
		}
		for (BlockPos blockPos : stemBlocks) {
			if (!level.getBlockState(blockPos).is(Blocks.WATER)) {
				return false;
			}
		}
		for (BlockPos blockPos : mushroomBlocks) {
			if (!level.getBlockState(blockPos).is(Blocks.WATER)) {
				return false;
			}
		}
		for (BlockPos blockPos : mushroomBlocks) {
			setBlock(level, blockPos, ESBlocks.MARIMOLD_BLOCK.get().defaultBlockState());
		}
		for (BlockPos blockPos : stemBlocks) {
			setBlock(level, blockPos, ESBlocks.MARIMOLD_STEM.get().defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(Direction.UP), blockPos.getY() - pos.getY() == trunkHeight).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(Direction.DOWN), blockPos.getY() == pos.getY()));
		}
		return true;
	}

	public record Configuration(IntProvider trunkHeight, IntProvider capRadius, IntProvider capHeight) implements FeatureConfiguration {
		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			IntProvider.CODEC.fieldOf("trunk_height").forGetter(Configuration::trunkHeight),
			IntProvider.CODEC.fieldOf("cap_radius").forGetter(Configuration::capRadius),
			IntProvider.CODEC.fieldOf("cap_height").forGetter(Configuration::capHeight)
		).apply(instance, Configuration::new));
	}
}
