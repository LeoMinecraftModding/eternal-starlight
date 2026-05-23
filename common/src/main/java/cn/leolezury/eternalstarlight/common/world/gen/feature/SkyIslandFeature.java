package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class SkyIslandFeature extends ESFeature<SkyIslandFeature.Configuration> {
	private long lastSeed;
	private PerlinSimplexNoise noise = new PerlinSimplexNoise(RandomSource.create(lastSeed), List.of(0));

	public SkyIslandFeature(Codec<Configuration> codec) {
		super(codec);
	}

	private void setSeed(long seed) {
		if (seed != lastSeed) {
			this.noise = new PerlinSimplexNoise(RandomSource.create(seed), List.of(0));
			lastSeed = seed;
		}
	}

	@Override
	public boolean place(FeaturePlaceContext<Configuration> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		ChunkGenerator chunkGenerator = context.chunkGenerator();
		Configuration config = context.config();
		BlockPos origin = getChunkCoordinate(context.origin()).offset(8, 0, 8);

		setSeed(level.getSeed());

		int sizeXZ = config.size().sample(random);
		int sizeY = config.height().sample(random);
		float noiseScale = config.noiseScale();

		Set<BlockPos> positions = new HashSet<>();
		BlockPos.MutableBlockPos placePos = new BlockPos.MutableBlockPos();
		double angleOffset = random.nextDouble() * 360;

		for (int x = -sizeXZ; x <= sizeXZ; x++) {
			placePos.setX(origin.getX() + x);
			for (int z = -sizeXZ; z <= sizeXZ; z++) {
				placePos.setZ(origin.getZ() + z);
				double angle = Mth.wrapDegrees(Mth.atan2(x, z) * Mth.RAD_TO_DEG + angleOffset) * Mth.DEG_TO_RAD;
				double shapeNoise = noise.getValue(
					(int) origin.asLong(),
					(int) origin.asLong() + angle * 8 * noiseScale,
					false
				);
				if (angle < -Mth.PI * 0.8) {
					shapeNoise *= 1 - (-Mth.PI * 0.8 - angle) / (Mth.PI * 0.2);
				}
				if (angle > Mth.PI * 0.8) {
					shapeNoise *= 1 - (angle - Mth.PI * 0.8) / (Mth.PI * 0.2);
				}
				double currentRadius = (1 - (0.5 * Mth.clamp(shapeNoise, -1, 1) + 0.5) * 0.4) * sizeXZ;
				double upHeightFade = Easing.OUT_QUART.calculate((float) (1 - Mth.sqrt(x * x + z * z) / currentRadius));
				double downHeightFade = 1 - Mth.sqrt(x * x + z * z) / currentRadius;
				double upHeightNoise = noise.getValue(
					(origin.getX() + x) * noiseScale,
					(origin.getZ() + z) * noiseScale,
					false
				);
				double downHeightNoise = noise.getValue(
					(origin.getX() + x + 1024) * noiseScale,
					(origin.getZ() + z + 1024) * noiseScale,
					false
				);
				int upHeight = (int) (sizeY * (1 + (0.5 * Mth.clamp(upHeightNoise, -1, 1) + 0.5) * 0.3) * upHeightFade);
				int downHeight = (int) (sizeY * (1 + (0.5 * Mth.clamp(downHeightNoise, -1, 1) + 0.5) * 0.3) * downHeightFade);
				if (upHeightFade > 0 && downHeightFade > 0) {
					for (int y = -downHeight; y <= upHeight; y++) {
						placePos.setY(origin.getY() + y);
						positions.add(placePos.immutable());
					}
				}
			}
		}

		List<BlockPos> topSurface = new ArrayList<>();
		List<BlockPos> bottomSurface = new ArrayList<>();
		List<BlockPos> inner = new ArrayList<>();

		for (BlockPos pos : positions) {
			BlockPos above = pos.above();
			BlockPos below = pos.below();
			boolean topExposed = !positions.contains(above);
			boolean bottomExposed = !positions.contains(below);
			if (topExposed) {
				topSurface.add(pos);
			}
			if (bottomExposed) {
				bottomSurface.add(pos);
			}
			if (!topExposed && !bottomExposed) {
				inner.add(pos);
			}
		}

		int dirtDepth = 3;
		Set<BlockPos> dirtSet = new HashSet<>(topSurface);
		Set<BlockPos> surfaceSet = new HashSet<>(topSurface);
		Set<BlockPos> bottomSet = new HashSet<>(bottomSurface);
		for (int depth = 1; depth < dirtDepth; depth++) {
			for (BlockPos tp : topSurface) {
				BlockPos below = tp.below(depth);
				if (inner.contains(below) || bottomSet.contains(below)) {
					dirtSet.add(below);
					inner.remove(below);
					bottomSet.remove(below);
				}
			}
		}

		for (BlockPos pos : inner) {
			setBlock(level, pos, config.composition().getState(random, pos));
		}
		for (BlockPos pos : bottomSet) {
			setBlock(level, pos, config.composition().getState(random, pos));
		}
		for (BlockPos pos : dirtSet) {
			if (!surfaceSet.contains(pos)) {
				setBlock(level, pos, config.dirt().getState(random, pos));
			}
		}
		for (BlockPos pos : surfaceSet) {
			setBlock(level, pos, config.surface().getState(random, pos));
		}

		for (BlockPos pos : surfaceSet) {
			config.decorations().forEach(decorationEntry -> {
				if (random.nextFloat() < decorationEntry.chance() && pos.getCenter().subtract(origin.getCenter()).horizontalDistance() < decorationEntry.maxDistance()) {
					decorationEntry.feature().value().place(level, chunkGenerator, random, pos.above());
				}
			});
		}

		for (BlockPos pos : bottomSurface) {
			config.bottomDecorations().forEach(bottomDecorationEntry -> {
				if (random.nextFloat() < bottomDecorationEntry.chance() && pos.getCenter().subtract(origin.getCenter()).horizontalDistance() < bottomDecorationEntry.maxDistance()) {
					bottomDecorationEntry.feature().value().place(level, chunkGenerator, random, pos.below());
				}
			});
		}

		return true;
	}

	public record Configuration(
		BlockStateProvider composition,
		BlockStateProvider surface,
		BlockStateProvider dirt,
		List<DecorationEntry> decorations,
		List<DecorationEntry> bottomDecorations,
		IntProvider size,
		IntProvider height,
		float noiseScale
	) implements FeatureConfiguration {
		public record DecorationEntry(Holder<PlacedFeature> feature, float chance, int maxDistance) {
			public static final Codec<DecorationEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				PlacedFeature.CODEC.fieldOf("feature").forGetter(DecorationEntry::feature),
				Codec.floatRange(0, 1).fieldOf("chance").forGetter(DecorationEntry::chance),
				Codec.INT.fieldOf("max_distance").forGetter(DecorationEntry::maxDistance)
			).apply(instance, DecorationEntry::new));
		}

		public static final Codec<Configuration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.CODEC.fieldOf("composition").forGetter(Configuration::composition),
			BlockStateProvider.CODEC.fieldOf("surface").forGetter(Configuration::surface),
			BlockStateProvider.CODEC.fieldOf("dirt").forGetter(Configuration::dirt),
			DecorationEntry.CODEC.listOf().fieldOf("decorations").forGetter(Configuration::decorations),
			DecorationEntry.CODEC.listOf().fieldOf("bottom_decorations").forGetter(Configuration::bottomDecorations),
			IntProvider.CODEC.fieldOf("size").forGetter(Configuration::size),
			IntProvider.CODEC.fieldOf("height").forGetter(Configuration::height),
			Codec.FLOAT.fieldOf("noise_scale").forGetter(Configuration::noiseScale)
		).apply(instance, Configuration::new));

		@Override
		public Stream<ConfiguredFeature<?, ?>> getFeatures() {
			return Stream.concat(
				decorations.stream().flatMap(entry -> entry.feature().value().getFeatures()),
				bottomDecorations.stream().flatMap(entry -> entry.feature().value().getFeatures())
			);
		}
	}
}
