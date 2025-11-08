package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class ESMossBlock extends Block implements BonemealableBlock {
	private final ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature;
	private final Optional<Holder<ParticleType<?>>> fallingParticle;

	public static final MapCodec<ESMossBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("bonemeal_feature").forGetter((block) -> block.bonemealFeature),
		BuiltInRegistries.PARTICLE_TYPE.holderByNameCodec().optionalFieldOf("falling_particle").forGetter((block) -> block.fallingParticle),
		propertiesCodec()
	).apply(instance, ESMossBlock::new));

	@Override
	public MapCodec<ESMossBlock> codec() {
		return CODEC;
	}

	public ESMossBlock(ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature, Holder<ParticleType<?>> fallingParticle, BlockBehaviour.Properties properties) {
		this(bonemealFeature, Optional.of(fallingParticle), properties);
	}

	public ESMossBlock(ResourceKey<ConfiguredFeature<?, ?>> bonemealFeature, Optional<Holder<ParticleType<?>>> fallingParticle, BlockBehaviour.Properties properties) {
		super(properties);
		this.bonemealFeature = bonemealFeature;
		this.fallingParticle = fallingParticle;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return levelReader.getBlockState(blockPos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		serverLevel.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((registry) -> registry.getHolder(bonemealFeature)).ifPresent((reference) -> reference.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), randomSource, blockPos.above()));
	}

	@Override
	public BonemealableBlock.Type getType() {
		return Type.NEIGHBOR_SPREADER;
	}

	@Override
	public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
		super.animateTick(blockState, level, blockPos, randomSource);
		if (fallingParticle.map(Holder::value).orElse(null) instanceof SimpleParticleType type && randomSource.nextInt(10) == 0) {
			BlockPos blockPos2 = blockPos.below();
			BlockState blockState2 = level.getBlockState(blockPos2);
			if (!isFaceFull(blockState2.getCollisionShape(level, blockPos2), Direction.UP)) {
				ParticleUtils.spawnParticleBelow(level, blockPos, randomSource, type);
			}
		}
	}
}
