package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class CarvedLunarisCactusFruitBlock extends HorizontalDirectionalBlock {
	public static final MapCodec<CarvedLunarisCactusFruitBlock> CODEC = simpleCodec(CarvedLunarisCactusFruitBlock::new);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	@Nullable
	private BlockPattern snowGolemBase;
	@Nullable
	private BlockPattern snowGolemFull;
	@Nullable
	private BlockPattern ironGolemBase;
	@Nullable
	private BlockPattern ironGolemFull;
	@Nullable
	private BlockPattern grimstoneGolemBase;
	@Nullable
	private BlockPattern grimstoneGolemFull;
	private static final Predicate<BlockState> LUNARIS_CACTUS_FRUIT_PREDICATE = (state) -> state != null && (state.is(ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get()) || state.is(ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get()));

	public CarvedLunarisCactusFruitBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return CODEC;
	}

	@Override
	protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		super.onPlace(blockState, level, blockPos, blockState2, bl);
		if (!blockState2.is(blockState.getBlock())) {
			this.trySpawnGolem(level, blockPos);
		}
	}

	public boolean canSpawnGolem(LevelReader levelReader, BlockPos blockPos) {
		return this.getOrCreateSnowGolemBase().find(levelReader, blockPos) != null || this.getOrCreateIronGolemBase().find(levelReader, blockPos) != null || this.getOrCreateGrimstoneGolemBase().find(levelReader, blockPos) != null;
	}

	private void trySpawnGolem(Level level, BlockPos blockPos) {
		BlockPattern.BlockPatternMatch snowGolemMatch = this.getOrCreateSnowGolemFull().find(level, blockPos);
		BlockPattern.BlockPatternMatch ironGolemMatch = this.getOrCreateIronGolemFull().find(level, blockPos);
		BlockPattern.BlockPatternMatch grimstoneGolemMatch = this.getOrCreateGrimstoneGolemFull().find(level, blockPos);
		if (snowGolemMatch != null) {
			SnowGolem snowGolem = EntityType.SNOW_GOLEM.create(level);
			if (snowGolem != null) {
				spawnGolemInWorld(level, snowGolemMatch, snowGolem, snowGolemMatch.getBlock(0, 2, 0).getPos());
			}
		} else if (ironGolemMatch != null) {
			IronGolem ironGolem = EntityType.IRON_GOLEM.create(level);
			if (ironGolem != null) {
				ironGolem.setPlayerCreated(true);
				spawnGolemInWorld(level, ironGolemMatch, ironGolem, ironGolemMatch.getBlock(1, 2, 0).getPos());
			}
		} else if (grimstoneGolemMatch != null) {
			GrimstoneGolem grimstoneGolem = ESEntities.GRIMSTONE_GOLEM.get().create(level);
			if (grimstoneGolem != null) {
				spawnGolemInWorld(level, grimstoneGolemMatch, grimstoneGolem, grimstoneGolemMatch.getBlock(0, 1, 0).getPos());
			}
		}
	}

	private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch blockPatternMatch, Entity entity, BlockPos blockPos) {
		clearPatternBlocks(level, blockPatternMatch);
		entity.moveTo((double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.05, (double) blockPos.getZ() + 0.5, 0.0F, 0.0F);
		level.addFreshEntity(entity);

		for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(5.0))) {
			CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
		}

		updatePatternBlocks(level, blockPatternMatch);
	}

	public static void clearPatternBlocks(Level level, BlockPattern.BlockPatternMatch blockPatternMatch) {
		for (int i = 0; i < blockPatternMatch.getWidth(); ++i) {
			for (int j = 0; j < blockPatternMatch.getHeight(); ++j) {
				BlockInWorld blockInWorld = blockPatternMatch.getBlock(i, j, 0);
				level.setBlock(blockInWorld.getPos(), Blocks.AIR.defaultBlockState(), 2);
				level.levelEvent(2001, blockInWorld.getPos(), Block.getId(blockInWorld.getState()));
			}
		}

	}

	public static void updatePatternBlocks(Level level, BlockPattern.BlockPatternMatch blockPatternMatch) {
		for (int i = 0; i < blockPatternMatch.getWidth(); ++i) {
			for (int j = 0; j < blockPatternMatch.getHeight(); ++j) {
				BlockInWorld blockInWorld = blockPatternMatch.getBlock(i, j, 0);
				level.blockUpdated(blockInWorld.getPos(), Blocks.AIR);
			}
		}

	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	private BlockPattern getOrCreateSnowGolemBase() {
		if (this.snowGolemBase == null) {
			this.snowGolemBase = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}

		return this.snowGolemBase;
	}

	private BlockPattern getOrCreateSnowGolemFull() {
		if (this.snowGolemFull == null) {
			this.snowGolemFull = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', BlockInWorld.hasState(LUNARIS_CACTUS_FRUIT_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}

		return this.snowGolemFull;
	}

	private BlockPattern getOrCreateIronGolemBase() {
		if (this.ironGolemBase == null) {
			this.ironGolemBase = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', (blockInWorld) -> blockInWorld.getState().isAir()).build();
		}

		return this.ironGolemBase;
	}

	private BlockPattern getOrCreateIronGolemFull() {
		if (this.ironGolemFull == null) {
			this.ironGolemFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', BlockInWorld.hasState(LUNARIS_CACTUS_FRUIT_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', (blockInWorld) -> blockInWorld.getState().isAir()).build();
		}

		return this.ironGolemFull;
	}

	private BlockPattern getOrCreateGrimstoneGolemBase() {
		if (this.grimstoneGolemBase == null) {
			this.grimstoneGolemBase = BlockPatternBuilder.start().aisle(" ", "#").where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ESBlocks.GRIMSTONE_BRICKS.get()))).where('~', (blockInWorld) -> blockInWorld.getState().isAir()).build();
		}

		return this.grimstoneGolemBase;
	}

	private BlockPattern getOrCreateGrimstoneGolemFull() {
		if (this.grimstoneGolemFull == null) {
			this.grimstoneGolemFull = BlockPatternBuilder.start().aisle("^", "#").where('^', BlockInWorld.hasState(LUNARIS_CACTUS_FRUIT_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ESBlocks.GRIMSTONE_BRICKS.get()))).build();
		}

		return this.grimstoneGolemFull;
	}
}
