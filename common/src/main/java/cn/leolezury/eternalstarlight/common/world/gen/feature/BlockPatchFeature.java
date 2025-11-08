package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BlockPatchFeature extends Feature<BlockPatchFeature.Configuration> {
	public BlockPatchFeature(Codec<Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Configuration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		Configuration config = context.config();
		int size = config.size().sample(random);
		BlockPos.MutableBlockPos placePos = new BlockPos.MutableBlockPos();
		for (int x = -size; x <= size; x++) {
			for (int y = (int) (-size / 1.8f); y <= size / 1.8f; y++) {
				for (int z = -size; z <= size; z++) {
					if (ESMathUtil.isPointInOrOnEllipsoid(x, y, z, size + random.nextInt(3) - 1, size + random.nextInt(3) - 1, size + random.nextInt(3) - 1)) {
						placePos.setWithOffset(pos, x, y, z);
						if (level.getBlockState(placePos).is(config.replaceable())) {
							setBlock(level, placePos, config.placeState().getState(random, placePos));
							level.getChunk(placePos).markPosForPostprocessing(placePos);
						}
					}
				}
			}
		}
		return true;
	}

	public record Configuration(BlockStateProvider placeState, TagKey<Block> replaceable, IntProvider size) implements FeatureConfiguration {
		public static final Codec<BlockPatchFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("place_state").forGetter(BlockPatchFeature.Configuration::placeState), TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter(BlockPatchFeature.Configuration::replaceable), IntProvider.CODEC.fieldOf("size").forGetter(BlockPatchFeature.Configuration::size)).apply(instance, BlockPatchFeature.Configuration::new));
	}
}
