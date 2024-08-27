package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AshenSnowFeature extends Feature<NoneFeatureConfiguration> {
	public AshenSnowFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		// scan for available dirt blocks
		BlockPos.MutableBlockPos placePos = new BlockPos.MutableBlockPos();
		for (int x = -4; x <= 4; x++) {
			for (int y = -2; y <= 2; y++) {
				for (int z = -4; z <= 4; z++) {
					if (x * x + z * z <= 4 * 4 && level.getBlockState(pos.offset(x, y, z)).is(ESTags.Blocks.BASE_STONE_STARLIGHT) && level.getBlockState(pos.offset(x, y + 1, z)).isAir() && random.nextBoolean()) {
						placePos.setWithOffset(pos, x, y + 1, z);
						BlockState pileState = ESBlocks.ASHEN_SNOW.get().defaultBlockState();
						if (pileState.hasProperty(BlockStateProperties.LAYERS)) {
							pileState = pileState.setValue(BlockStateProperties.LAYERS, random.nextInt(1, 5));
						}
						level.setBlock(placePos, pileState, 3);
					}
				}
			}
		}
		return true;
	}
}
