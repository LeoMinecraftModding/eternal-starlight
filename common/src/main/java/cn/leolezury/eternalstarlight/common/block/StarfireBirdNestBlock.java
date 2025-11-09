package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StarfireBirdNestBlock extends BaseEntityBlock {
	public static final MapCodec<StarfireBirdNestBlock> CODEC = simpleCodec(StarfireBirdNestBlock::new);
	public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 0, 3);

	@Override
	protected MapCodec<? extends StarfireBirdNestBlock> codec() {
		return CODEC;
	}

	public StarfireBirdNestBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(EGGS, 0));
	}

	public float getSeedsRenderOffset() {
		return 0.0625F * 2;
	}

	public boolean canAccessNestContent(BlockState state) {
		return true;
	}

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (canAccessNestContent(state) && stack.is(ESTags.Items.STARFIRE_BIRD_FOOD) && level.getBlockEntity(pos) instanceof StarfireBirdNestBlockEntity nest && nest.getItems().stream().anyMatch(ItemStack::isEmpty)) {
			if (!level.isClientSide && nest.addSeeds(stack.copyWithCount(1))) {
				nest.setLastSeedPlayer(player);
				if (player instanceof ServerPlayer serverPlayer) {
					ESCriteriaTriggers.PUT_SEEDS_INTO_STARFIRE_BIRD_NEST.get().trigger(serverPlayer);
				}
				stack.consume(1, player);
			}
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		if (canAccessNestContent(state) && state.getValue(EGGS) < 3 && stack.is(ESItems.STARFIRE_BIRD_EGG.get())) {
			stack.consume(1, player);
			level.setBlockAndUpdate(pos, state.setValue(EGGS, state.getValue(EGGS) + 1));
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		if (canAccessNestContent(state) && state.getValue(EGGS) > 0 && (stack.isEmpty() || (stack.is(ESItems.STARFIRE_BIRD_EGG.get()) && stack.getCount() < stack.getMaxStackSize()))) {
			if (stack.isEmpty()) {
				player.setItemInHand(hand, ESItems.STARFIRE_BIRD_EGG.get().getDefaultInstance());
			} else {
				stack.grow(1);
			}
			level.setBlockAndUpdate(pos, state.setValue(EGGS, state.getValue(EGGS) - 1));
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		super.playerDestroy(level, player, pos, state, blockEntity, stack);
		if (!level.isClientSide && blockEntity instanceof StarfireBirdNestBlockEntity nest) {
			if (!EnchantmentHelper.hasTag(stack, ESTags.Enchantments.PREVENTS_STARFIRE_BIRD_SPAWNS_WHEN_MINING)) {
				nest.releaseAllOccupants(state, true);
			}
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState aboveState = level.getBlockState(pos.above());
		return aboveState.is(BlockTags.LEAVES) || aboveState.is(ESTags.Blocks.STARFIRE_BIRD_NESTS);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor level, BlockPos pos, BlockPos blockPos) {
		return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, level, pos, blockPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, EGGS);
	}

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new StarfireBirdNestBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide ? createTickerHelper(type, ESBlockEntities.STARFIRE_BIRD_NEST.get(), StarfireBirdNestBlockEntity::clientTick) : createTickerHelper(type, ESBlockEntities.STARFIRE_BIRD_NEST.get(), StarfireBirdNestBlockEntity::serverTick);
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
		if (!level.isClientSide && player.isCreative() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
			BlockEntity blockEntity = level.getBlockEntity(blockPos);
			int eggs = blockState.getValue(EGGS);
			if (blockEntity instanceof StarfireBirdNestBlockEntity nest) {
				if (nest.getOccupantCount() > 0 || nest.getItems().stream().anyMatch(stack -> !stack.isEmpty()) || eggs > 0) {
					ItemStack stack = new ItemStack(this);
					stack.applyComponents(nest.collectComponents());
					stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(EGGS, eggs));
					ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
					itemEntity.setDefaultPickUpDelay();
					level.addFreshEntity(itemEntity);
				}
			}
		}

		return super.playerWillDestroy(level, blockPos, blockState, player);
	}

	@Override
	protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		Entity entity = builder.getOptionalParameter(LootContextParams.THIS_ENTITY);
		if (!(entity instanceof Player)) {
			BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
			if (blockEntity instanceof StarfireBirdNestBlockEntity nest) {
				nest.releaseAllOccupants(state, true);
			}
		}
		BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
		if (blockEntity instanceof StarfireBirdNestBlockEntity nest) {
			builder = builder.withDynamicDrop(ResourceLocation.withDefaultNamespace("contents"), (consumer) -> {
				for (int i = 0; i < nest.getContainerSize(); ++i) {
					consumer.accept(nest.getItem(i));
				}
			});
		}

		return super.getDrops(state, builder);
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
}
