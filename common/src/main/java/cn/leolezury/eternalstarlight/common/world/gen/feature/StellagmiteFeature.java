package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.RotatedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.SpikeProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.ValueMapGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.List;

public class StellagmiteFeature extends ESFeature<NoneFeatureConfiguration> {
	public StellagmiteFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		ValueMapGenerator.place(new RotatedProvider(new SpikeProvider(1.5f + random.nextFloat(), 10f + random.nextFloat() * 3), random.nextInt(-20, 20), random.nextInt(360)), (pos, value) -> {
			BlockState state = ESBlocks.STELLAGMITE.get().defaultBlockState();
			if (value <= 0.8 || random.nextFloat() <= 0.15) {
				state = ESBlocks.MOLTEN_STELLAGMITE.get().defaultBlockState();
			}
			setBlockIfEmpty(level, pos.offset(origin), state, true, s -> anyMatch(s, List.of(ESTags.Blocks.BASE_STONE_STARLIGHT)) || s.canBeReplaced());
		});
		return true;
	}
}

