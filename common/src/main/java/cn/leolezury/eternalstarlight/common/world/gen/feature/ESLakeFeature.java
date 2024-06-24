package cn.leolezury.eternalstarlight.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ESLakeFeature extends ESFeature<ESLakeFeature.Configuration> {
	public ESLakeFeature(Codec<Configuration> codec) {
		super(codec);
	}

	private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

	@Override
	public boolean place(FeaturePlaceContext<ESLakeFeature.Configuration> context) {
		// from vanilla's lake feature
		BlockPos blockPos = context.origin();
		WorldGenLevel worldGenLevel = context.level();
		RandomSource randomSource = context.random();
		ESLakeFeature.Configuration configuration = context.config();
		if (blockPos.getY() <= worldGenLevel.getMinBuildHeight() + 4) {
			return false;
		} else {
			blockPos = blockPos.below(4);
			boolean[] bls = new boolean[2048];
			int i = randomSource.nextInt(4) + 4;

			for (int j = 0; j < i; ++j) {
				double d = randomSource.nextDouble() * 6.0 + 3.0;
				double e = randomSource.nextDouble() * 4.0 + 2.0;
				double f = randomSource.nextDouble() * 6.0 + 3.0;
				double g = randomSource.nextDouble() * (16.0 - d - 2.0) + 1.0 + d / 2.0;
				double h = randomSource.nextDouble() * (8.0 - e - 4.0) + 2.0 + e / 2.0;
				double k = randomSource.nextDouble() * (16.0 - f - 2.0) + 1.0 + f / 2.0;

				for (int l = 1; l < 15; ++l) {
					for (int m = 1; m < 15; ++m) {
						for (int n = 1; n < 7; ++n) {
							double o = ((double) l - g) / (d / 2.0);
							double p = ((double) n - h) / (e / 2.0);
							double q = ((double) m - k) / (f / 2.0);
							double r = o * o + p * p + q * q;
							if (r < 1.0) {
								bls[(l * 16 + m) * 8 + n] = true;
							}
						}
					}
				}
			}

			BlockState blockState = configuration.fluid().getState(randomSource, blockPos);

			int t;
			boolean v;
			int s;
			int u;
			for (s = 0; s < 16; ++s) {
				for (t = 0; t < 16; ++t) {
					for (u = 0; u < 8; ++u) {
						v = !bls[(s * 16 + t) * 8 + u] && (s < 15 && bls[((s + 1) * 16 + t) * 8 + u] || s > 0 && bls[((s - 1) * 16 + t) * 8 + u] || t < 15 && bls[(s * 16 + t + 1) * 8 + u] || t > 0 && bls[(s * 16 + (t - 1)) * 8 + u] || u < 7 && bls[(s * 16 + t) * 8 + u + 1] || u > 0 && bls[(s * 16 + t) * 8 + (u - 1)]);
						if (v) {
							BlockState blockState2 = worldGenLevel.getBlockState(blockPos.offset(s, u, t));
							if (u >= 4 && blockState2.liquid()) {
								return false;
							}

							if (u < 4 && !blockState2.isSolid() && worldGenLevel.getBlockState(blockPos.offset(s, u, t)) != blockState) {
								return false;
							}
						}
					}
				}
			}

			boolean bl2;
			for (s = 0; s < 16; ++s) {
				for (t = 0; t < 16; ++t) {
					for (u = 0; u < 8; ++u) {
						if (bls[(s * 16 + t) * 8 + u]) {
							BlockPos blockPos2 = blockPos.offset(s, u, t);
							if (this.canReplaceBlock(worldGenLevel.getBlockState(blockPos2))) {
								bl2 = u >= 4;
								worldGenLevel.setBlock(blockPos2, bl2 ? AIR : blockState, 2);
								if (bl2) {
									worldGenLevel.scheduleTick(blockPos2, AIR.getBlock(), 0);
									this.markAboveForPostProcessing(worldGenLevel, blockPos2);
								}
							}
						}
					}
				}
			}

			BlockState blockState3 = configuration.barrier().getState(randomSource, blockPos);
			if (!blockState3.isAir()) {
				for (t = 0; t < 16; ++t) {
					for (u = 0; u < 16; ++u) {
						for (int r = 0; r < 8; ++r) {
							bl2 = !bls[(t * 16 + u) * 8 + r] && (t < 15 && bls[((t + 1) * 16 + u) * 8 + r] || t > 0 && bls[((t - 1) * 16 + u) * 8 + r] || u < 15 && bls[(t * 16 + u + 1) * 8 + r] || u > 0 && bls[(t * 16 + (u - 1)) * 8 + r] || r < 7 && bls[(t * 16 + u) * 8 + r + 1] || r > 0 && bls[(t * 16 + u) * 8 + (r - 1)]);
							if (bl2 && (r < 4 || randomSource.nextInt(2) != 0)) {
								BlockState blockState4 = worldGenLevel.getBlockState(blockPos.offset(t, r, u));
								if (blockState4.isSolid() && !blockState4.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
									BlockPos blockPos3 = blockPos.offset(t, r, u);
									// barrier should always be randomized
									blockState3 = configuration.barrier().getState(randomSource, blockPos);
									worldGenLevel.setBlock(blockPos3, blockState3, 2);
									this.markAboveForPostProcessing(worldGenLevel, blockPos3);
								}
							}
						}
					}
				}
			}

			// might cause server crash
            /*if (blockState.getFluidState().is(FluidTags.WATER)) {
                for(t = 0; t < 16; ++t) {
                    for(u = 0; u < 16; ++u) {
                        BlockPos blockPos4 = blockPos.offset(t, 4, u);
                        if (worldGenLevel.getBiome(blockPos4).value().shouldFreeze(worldGenLevel, blockPos4, false) && this.canReplaceBlock(worldGenLevel.getBlockState(blockPos4))) {
                            worldGenLevel.setBlock(blockPos4, Blocks.ICE.defaultBlockState(), 2);
                        }
                    }
                }
            }*/

			return true;
		}
	}

	private boolean canReplaceBlock(BlockState blockState) {
		return !blockState.is(BlockTags.FEATURES_CANNOT_REPLACE);
	}

	public record Configuration(BlockStateProvider fluid, BlockStateProvider barrier) implements FeatureConfiguration {
		public static final Codec<ESLakeFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Configuration::fluid), BlockStateProvider.CODEC.fieldOf("barrier").forGetter(Configuration::barrier)).apply(instance, Configuration::new));
	}
}