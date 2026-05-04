package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.MergedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.RotatedProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.SpikeProvider;
import cn.leolezury.eternalstarlight.common.world.gen.valuemap.ValueMapGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SpikeFeature extends ESFeature<SpikeFeature.Configuration> {
	public SpikeFeature(Codec<SpikeFeature.Configuration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<SpikeFeature.Configuration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		RandomSource random = context.random();
		Configuration config = context.config();
		List<MergedProvider.Entry> entries = new ArrayList<>();
		int count = config.minorCount().sample(random);
		for (int i = 0; i < count; i++) {
			entries.add(new MergedProvider.Entry(new RotatedProvider(new SpikeProvider(config.minorRadius().sample(random), config.minorHeight().sample(random)), random.nextInt(20, 70), random.nextFloat() * 360), Vec3.ZERO));
		}
		entries.add(new MergedProvider.Entry(new SpikeProvider(config.radius().sample(random), config.height().sample(random)), Vec3.ZERO));
		ValueMapGenerator.place(new MergedProvider(entries), (pos, value) -> setBlockIfEmpty(level, pos.offset(origin), config.spike().getState(random, pos.offset(origin)), true, s -> s.is(ESTags.Blocks.BASE_STONE_STARLIGHT) || s.canBeReplaced()));
		return true;
	}

	public record Configuration(BlockStateProvider spike, IntProvider height, IntProvider radius, IntProvider minorCount, IntProvider minorHeight, IntProvider minorRadius) implements FeatureConfiguration {
		public static final Codec<SpikeFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BlockStateProvider.CODEC.fieldOf("spike").forGetter(SpikeFeature.Configuration::spike),
			IntProvider.CODEC.fieldOf("height").forGetter(SpikeFeature.Configuration::height),
			IntProvider.CODEC.fieldOf("radius").forGetter(SpikeFeature.Configuration::radius),
			IntProvider.CODEC.fieldOf("minor_count").forGetter(SpikeFeature.Configuration::minorCount),
			IntProvider.CODEC.fieldOf("minor_height").forGetter(SpikeFeature.Configuration::minorHeight),
			IntProvider.CODEC.fieldOf("minor_radius").forGetter(SpikeFeature.Configuration::minorRadius)
		).apply(instance, SpikeFeature.Configuration::new));
	}
}
