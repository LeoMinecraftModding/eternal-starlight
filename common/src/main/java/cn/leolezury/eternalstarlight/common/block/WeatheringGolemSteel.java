package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public interface WeatheringGolemSteel {
	Supplier<ImmutableMap<Block, Block>> TO_OXIDIZED = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
		.put(ESBlocks.GOLEM_STEEL_BLOCK.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get())
		.put(ESBlocks.GOLEM_STEEL_SLAB.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get())
		.put(ESBlocks.GOLEM_STEEL_STAIRS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get())
		.put(ESBlocks.GOLEM_STEEL_TILES.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get())
		.put(ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get())
		.put(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get())
		.put(ESBlocks.GOLEM_STEEL_GRATE.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get())
		.put(ESBlocks.GOLEM_STEEL_PILLAR.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_PILLAR.get())
		.put(ESBlocks.GOLEM_STEEL_BARS.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_BARS.get())
		.put(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get(), ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get())
		.put(ESBlocks.GOLEM_STEEL_JET.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get())
		.put(ESBlocks.ALLOY_FURNACE.get(), ESBlocks.OXIDIZED_ALLOY_FURNACE.get())
		.build());

	Supplier<ImmutableMap<Block, Block>> TO_WAXED = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
		.put(ESBlocks.GOLEM_STEEL_BLOCK.get(), ESBlocks.WAXED_GOLEM_STEEL_BLOCK.get())
		.put(ESBlocks.GOLEM_STEEL_SLAB.get(), ESBlocks.WAXED_GOLEM_STEEL_SLAB.get())
		.put(ESBlocks.GOLEM_STEEL_STAIRS.get(), ESBlocks.WAXED_GOLEM_STEEL_STAIRS.get())
		.put(ESBlocks.GOLEM_STEEL_TILES.get(), ESBlocks.WAXED_GOLEM_STEEL_TILES.get())
		.put(ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), ESBlocks.WAXED_GOLEM_STEEL_TILE_SLAB.get())
		.put(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), ESBlocks.WAXED_GOLEM_STEEL_TILE_STAIRS.get())
		.put(ESBlocks.GOLEM_STEEL_GRATE.get(), ESBlocks.WAXED_GOLEM_STEEL_GRATE.get())
		.put(ESBlocks.GOLEM_STEEL_PILLAR.get(), ESBlocks.WAXED_GOLEM_STEEL_PILLAR.get())
		.put(ESBlocks.GOLEM_STEEL_BARS.get(), ESBlocks.WAXED_GOLEM_STEEL_BARS.get())
		.put(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get(), ESBlocks.WAXED_CHISELED_GOLEM_STEEL_BLOCK.get())
		.put(ESBlocks.GOLEM_STEEL_JET.get(), ESBlocks.WAXED_GOLEM_STEEL_JET.get())
		.put(ESBlocks.ALLOY_FURNACE.get(), ESBlocks.WAXED_ALLOY_FURNACE.get())
		.build());

	default ItemInteractionResult use(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player) {
		Optional<Block> scraped = TO_OXIDIZED.get().entrySet().stream().filter(e -> e.getValue() == state.getBlock()).findFirst().map(Map.Entry::getKey);
		Optional<Block> unwaxed = TO_WAXED.get().entrySet().stream().filter(e -> e.getValue() == state.getBlock()).findFirst().map(Map.Entry::getKey);
		if (ESPlatform.INSTANCE.canScrape(stack)) {
			Block result = null;
			boolean waxSound = false;
			if (scraped.isPresent()) {
				result = scraped.get();
			} else if (unwaxed.isPresent()) {
				result = unwaxed.get();
				waxSound = true;
			}
			if (result != null) {
				placeTransformedBlock(level, pos, result.withPropertiesOf(state));
				spawnWaxOrScrapeParticles(level, pos, ParticleTypes.WAX_OFF);
				player.playSound(waxSound ? SoundEvents.AXE_WAX_OFF : SoundEvents.AXE_SCRAPE);
				stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		Optional<BlockState> waxed = getWaxedState(state);
		if ((stack.is(Items.HONEYCOMB) || stack.is(ESItems.RAW_AMARAMBER.get())) && waxed.isPresent()) {
			placeTransformedBlock(level, pos, waxed.get());
			spawnWaxOrScrapeParticles(level, pos, stack.is(Items.HONEYCOMB) ? ParticleTypes.WAX_ON : ESParticles.AMARAMBER_WAX_ON.get());
			player.playSound(SoundEvents.HONEYCOMB_WAX_ON);
			stack.consume(1, player);
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	default void spawnWaxOrScrapeParticles(Level level, BlockPos pos, ParticleOptions particle) {
		ParticleUtils.spawnParticlesOnBlockFaces(level, pos, particle, UniformInt.of(3, 5));
	}

	default void placeTransformedBlock(Level level, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos, state);
	}

	default boolean isOxidized() {
		if (this instanceof Block block) {
			return TO_OXIDIZED.get().containsValue(block);
		} else {
			return false;
		}
	}

	default boolean isWaxed() {
		if (this instanceof Block block) {
			return TO_WAXED.get().containsValue(block);
		} else {
			return false;
		}
	}

	default Optional<BlockState> getOxidizedState(BlockState blockState) {
		if (TO_OXIDIZED.get().containsKey(blockState.getBlock())) {
			return Optional.ofNullable(TO_OXIDIZED.get().get(blockState.getBlock())).map((block) -> block.withPropertiesOf(blockState));
		}
		return Optional.empty();
	}

	default Optional<BlockState> getWaxedState(BlockState blockState) {
		if (TO_WAXED.get().containsKey(blockState.getBlock())) {
			return Optional.ofNullable(TO_WAXED.get().get(blockState.getBlock())).map((block) -> block.withPropertiesOf(blockState));
		}
		return Optional.empty();
	}

	default void changeOverTime(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (randomSource.nextFloat() < 0.05688889F) {
			this.getNextState(blockState, serverLevel, blockPos, randomSource).ifPresent((state) -> {
				placeTransformedBlock(serverLevel, blockPos, state);
			});
		}
	}

	default Optional<BlockState> getNextState(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		boolean oxidized = this.isOxidized();
		int lessAffected = 0;
		int moreAffected = 0;

		for (BlockPos pos : BlockPos.withinManhattan(blockPos, 4, 4, 4)) {
			int distManhattan = pos.distManhattan(blockPos);
			if (distManhattan > 4) {
				break;
			}

			if (!pos.equals(blockPos)) {
				Block block = serverLevel.getBlockState(pos).getBlock();
				if (block instanceof WeatheringGolemSteel weatheringGolemSteel) {
					boolean otherBlockOxidized = weatheringGolemSteel.isOxidized();
					if (!otherBlockOxidized && oxidized) {
						return Optional.empty();
					}
					if (otherBlockOxidized && !oxidized) {
						++moreAffected;
					} else {
						++lessAffected;
					}
				}
			}
		}

		float oxidizeFactor = (float) (moreAffected + 1) / (float) (moreAffected + lessAffected + 1);
		return randomSource.nextFloat() < oxidizeFactor * oxidizeFactor * 0.75f ? this.getOxidizedState(blockState) : Optional.empty();
	}
}
