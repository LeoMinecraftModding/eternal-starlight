package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.world.gen.valuemap.MergedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.RotatedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.SpikeProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.ValueMapGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class IceSpikeFeature extends ESFeature<NoneFeatureConfiguration> {
	public IceSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		List<MergedProvider.Entry> entries = new ArrayList<>();
		int count = random.nextInt(1, 5);
		for (int i = 0; i < count; i++) {
			entries.add(new MergedProvider.Entry(new RotatedProvider(new SpikeProvider(4f + random.nextFloat() * 3, 12f + random.nextFloat() * 3), random.nextInt(20, 70), random.nextFloat() * 360), Vec3.ZERO));
		}
		entries.add(new MergedProvider.Entry(new SpikeProvider(4f + random.nextFloat() * 3, 30f + random.nextFloat() * 5), Vec3.ZERO));
		ValueMapGenerator.place(new MergedProvider(entries), (pos, value) -> setBlockIfEmpty(level, pos.offset(origin), Blocks.PACKED_ICE.defaultBlockState()));
		return true;
	}
}

