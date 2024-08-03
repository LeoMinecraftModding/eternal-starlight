package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ESGrassBlock extends ESSpreadingSnowyDirtBlock implements BonemealableBlock {
	public static final MapCodec<ESGrassBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		BuiltInRegistries.BLOCK.byNameCodec().fieldOf("spreads_on").forGetter((block) -> block.spreadsOn),
		ResourceKey.codec(Registries.PLACED_FEATURE).fieldOf("grass").forGetter((block) -> block.grassFeature),
		propertiesCodec()
	).apply(instance, ESGrassBlock::new));

	private final ResourceKey<PlacedFeature> grassFeature;

	public ESGrassBlock(Block spreadOn, ResourceKey<PlacedFeature> grassFeature, BlockBehaviour.Properties properties) {
		super(spreadOn, properties);
		this.grassFeature = grassFeature;
	}

	@Override
	protected MapCodec<? extends SnowyDirtBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return levelReader.getBlockState(blockPos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.above();
		BlockState blockstate = Blocks.SHORT_GRASS.defaultBlockState();

		label46:
		for (int i = 0; i < 128; ++i) {
			BlockPos blockpos1 = blockpos;

			for (int j = 0; j < i / 16; ++j) {
				blockpos1 = blockpos1.offset(randomSource.nextInt(3) - 1, (randomSource.nextInt(3) - 1) * randomSource.nextInt(3) / 2, randomSource.nextInt(3) - 1);
				if (!serverLevel.getBlockState(blockpos1.below()).is(this) || serverLevel.getBlockState(blockpos1).isCollisionShapeFullBlock(serverLevel, blockpos1)) {
					continue label46;
				}
			}

			BlockState blockstate1 = serverLevel.getBlockState(blockpos1);
			if (blockstate1.is(blockstate.getBlock()) && randomSource.nextInt(10) == 0) {
				((BonemealableBlock) blockstate.getBlock()).performBonemeal(serverLevel, randomSource, blockpos1, blockstate1);
			}

			if (blockstate1.isAir()) {
				Holder<PlacedFeature> holder;

				HolderGetter<PlacedFeature> placedFeatureHolderGetter = serverLevel.holderLookup(Registries.PLACED_FEATURE);
				holder = placedFeatureHolderGetter.getOrThrow(grassFeature);

				holder.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), randomSource, blockpos1);
			}
		}

	}
}
