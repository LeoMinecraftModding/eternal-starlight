package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AlloyFurnaceBlock extends BaseEntityBlock implements WorldlyContainerHolder, WeatheringGolemSteel {
	public static final MapCodec<AlloyFurnaceBlock> CODEC = simpleCodec(AlloyFurnaceBlock::new);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty X_OFFSET = IntegerProperty.create("x_offset", 0, 1);
	public static final IntegerProperty Y_OFFSET = IntegerProperty.create("y_offset", 0, 2);
	public static final IntegerProperty Z_OFFSET = IntegerProperty.create("z_offset", 0, 2);
	private static final Map<Pair<Direction, Triple<Integer, Integer, Integer>>, VoxelShape> SHAPES = Util.make(() -> {
		Map<Pair<Direction, Triple<Integer, Integer, Integer>>, VoxelShape> map = new HashMap<>();
		for (int x = 0; x <= 2; x++) {
			for (int z = 0; z <= 2; z++) {
				for (int y = 0; y <= 2; y++) {
					VoxelShape shape = Shapes.block();
					if (x == 0 && y == 0 && z == 2) {
						shape = Shapes.or(
							Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
							Block.box(4.0, 0.0, 0.0, 16.0, 16.0, 6.0)
						);
					} else if (x == 1 && y == 0 && z == 2) {
						shape = Shapes.or(
							Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
							Block.box(4.0, 0.0, 10.0, 16.0, 16.0, 16.0)
						);
					} else if (x == 0 && y == 1 && z == 2) {
						shape = Shapes.or(
							Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
							Block.box(4.0, 0.0, 0.0, 16.0, 12.0, 6.0),
							Block.box(7.0, 12.0, 0.0, 13.0, 16.0, 3.0)
						);
					} else if (x == 1 && y == 1 && z == 2) {
						shape = Shapes.or(
							Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
							Block.box(4.0, 0.0, 10.0, 16.0, 12.0, 16.0),
							Block.box(7.0, 12.0, 13.0, 13.0, 16.0, 16.0)
						);
					} else if (x == 0 && y == 2 && z == 2) {
						shape = Block.box(0.0, 0.0, 0.0, 13.0, 6.0, 3.0);
					} else if (x == 1 && y == 2 && z == 2) {
						shape = Block.box(0.0, 0.0, 13.0, 13.0, 6.0, 16.0);
					} else if (x == 0 && y == 2 && z == 1) {
						shape = Block.box(7.0, 0.0, 0.0, 16.0, 6.0, 3.0);
					} else if (x == 1 && y == 2 && z == 1) {
						shape = Shapes.or(
							Block.box(7.0, 0.0, 13.0, 16.0, 6.0, 16.0),
							Block.box(0.0, 0.0, 7.0, 5.0, 8.0, 15.0),
							Block.box(0.0, 8.0, 6.0, 6.0, 12.0, 16.0)
						);
					} else if (x == 0 && y == 2) { // z is always 0 here
						shape = Shapes.or(
							Block.box(3.0, 0.0, 0.0, 13.0, 12.0, 9.0),
							Block.box(2.0, 12.0, 0.0, 14.0, 16.0, 10.0)
						);
					} else if (x == 1 && y == 2) { // z is always 0 here
						shape = Shapes.or(
							Block.box(3.0, 0.0, 15.0, 13.0, 12.0, 16.0),
							Block.box(2.0, 12.0, 14.0, 14.0, 16.0, 16.0),
							Block.box(13.0, 0.0, 7.0, 16.0, 8.0, 15.0),
							Block.box(12.0, 8.0, 6.0, 16.0, 12.0, 16.0)
						);
					}
					for (Direction direction : Direction.values()) {
						if (direction.getAxis() != Direction.Axis.Y) {
							map.put(Pair.of(direction, Triple.of(x, y, z)), ESBlockUtil.rotateVoxelShape(shape, -90, 180, direction));
						}
					}
				}
			}
		}
		return map;
	});

	private static final Map<Item, AlloyFurnaceCoolingItem> COOLING_REGISTRY = new HashMap<>();

	public AlloyFurnaceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(X_OFFSET, 0).setValue(Y_OFFSET, 0).setValue(Z_OFFSET, 1));
	}

	@Override
	protected MapCodec<AlloyFurnaceBlock> codec() {
		return CODEC;
	}

	public static void registerCoolingItem(Item item, AlloyFurnaceCoolingItem coolingItem) {
		COOLING_REGISTRY.put(item, coolingItem);
	}

	public static void registerCoolingItem(TagKey<Item> itemTag, AlloyFurnaceCoolingItem coolingItem) {
		for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(itemTag)) {
			COOLING_REGISTRY.put(holder.value(), coolingItem);
		}
	}

	public static AlloyFurnaceCoolingItem getCoolingItem(Item item) {
		return COOLING_REGISTRY.get(item);
	}

	public static int getCoolDuration(Item item) {
		AlloyFurnaceCoolingItem coolingItem = getCoolingItem(item);
		return coolingItem == null ? 0 : coolingItem.duration();
	}

	public static int getCoolEfficiency(Item item) {
		AlloyFurnaceCoolingItem coolingItem = getCoolingItem(item);
		return coolingItem == null ? 0 : coolingItem.efficiency();
	}

	public static Map<Item, AlloyFurnaceCoolingItem> getCoolingRegistry() {
		return Collections.unmodifiableMap(COOLING_REGISTRY);
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		CollisionContext collisionContext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
		Direction facing = context.getHorizontalDirection().getOpposite();
		VoxelShape shape = Shapes.empty();
		for (int x = 0; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					Vec3 rotated = new Vec3(x, 0, z).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
					int rotatedX = Math.round((float) rotated.x);
					int rotatedZ = Math.round((float) rotated.z);
					if (!(level.getBlockState(pos.offset(rotatedX, y, rotatedZ)).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(pos.offset(rotatedX, y, rotatedZ)))) {
						return null;
					}
					shape = Shapes.or(shape, defaultBlockState().setValue(X_OFFSET, x).setValue(Y_OFFSET, y).setValue(Z_OFFSET, z + 1).getCollisionShape(level, pos, collisionContext).move(pos.getX() + rotatedX, pos.getY() + y, pos.getZ() + rotatedZ));
				}
			}
		}
		if (!level.isUnobstructed(null, shape)) {
			return null;
		}
		BlockState state = super.getStateForPlacement(context);
		return state != null ? state.setValue(FACING, facing) : null;
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);
		Direction facing = state.getValue(FACING);
		for (int x = 0; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					if (!(x == 0 && y == 0 && z == 0)) {
						Vec3 rotated = new Vec3(x, 0, z).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
						int rotatedX = Math.round((float) rotated.x);
						int rotatedZ = Math.round((float) rotated.z);
						level.setBlockAndUpdate(pos.offset(rotatedX, y, rotatedZ), defaultBlockState().setValue(FACING, facing).setValue(X_OFFSET, x).setValue(Y_OFFSET, y).setValue(Z_OFFSET, z + 1));
					}
				}
			}
		}
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		return use(stack, state, level, pos, player);
	}

	@Override
	public void spawnWaxOrScrapeParticles(Level level, BlockPos pos, ParticleOptions particle) {
		BlockState state = level.getBlockState(pos);
		Direction facing = state.getValue(FACING);
		Vec3 centerRotated = new Vec3(state.getValue(X_OFFSET), 0, state.getValue(Z_OFFSET) - 1).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
		BlockPos centerPos = pos.offset(-Math.round((float) centerRotated.x), -state.getValue(Y_OFFSET), -Math.round((float) centerRotated.z));
		for (int x = 0; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 1; y++) {
					Vec3 rotated = new Vec3(x, 0, z).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
					int rotatedX = Math.round((float) rotated.x);
					int rotatedZ = Math.round((float) rotated.z);
					BlockPos partPos = centerPos.offset(rotatedX, y, rotatedZ);
					if (z == 1) {
						ParticleUtils.spawnParticleInBlock(level, partPos, 3, particle);
					} else {
						ParticleUtils.spawnParticlesOnBlockFaces(level, partPos, particle, UniformInt.of(3, 5));
					}
				}
			}
		}
	}

	@Override
	public void placeTransformedBlock(Level level, BlockPos pos, BlockState state) {
		Direction facing = state.getValue(FACING);
		Vec3 centerRotated = new Vec3(state.getValue(X_OFFSET), 0, state.getValue(Z_OFFSET) - 1).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
		BlockPos centerPos = pos.offset(-Math.round((float) centerRotated.x), -state.getValue(Y_OFFSET), -Math.round((float) centerRotated.z));
		level.setBlockAndUpdate(centerPos, state.setValue(X_OFFSET, 0).setValue(Y_OFFSET, 0).setValue(Z_OFFSET, 1));
		for (int x = 0; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					if (!(x == 0 && y == 0 && z == 0)) {
						Vec3 rotated = new Vec3(x, 0, z).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
						int rotatedX = Math.round((float) rotated.x);
						int rotatedZ = Math.round((float) rotated.z);
						BlockPos partPos = centerPos.offset(rotatedX, y, rotatedZ);
						if (level.getBlockState(partPos).canBeReplaced() && level.getWorldBorder().isWithinBounds(partPos)) {
							level.setBlockAndUpdate(partPos, state.setValue(X_OFFSET, x).setValue(Y_OFFSET, y).setValue(Z_OFFSET, z + 1));
						}
					}
				}
			}
		}
	}

	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		this.changeOverTime(blockState, serverLevel, blockPos, randomSource);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return !isOxidized() && !isWaxed();
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			Direction facing = state.getValue(FACING);
			int x = state.getValue(X_OFFSET);
			int z = state.getValue(Z_OFFSET) - 1;
			Vec3 rotated = new Vec3(x, 0, z).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
			int rotatedX = Math.round((float) rotated.x);
			int rotatedZ = Math.round((float) rotated.z);
			BlockPos centerPos = pos.offset(-rotatedX, -state.getValue(Y_OFFSET), -rotatedZ);
			BlockEntity blockEntity = level.getBlockEntity(centerPos);
			if (blockEntity instanceof AlloyFurnaceBlockEntity entity) {
				player.openMenu(entity);
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof AlloyFurnaceBlockEntity entity) {
				if (!entity.isValidBlockState(newState)) {
					if (level instanceof ServerLevel) {
						Containers.dropContents(level, pos, entity);
					}
					super.onRemove(state, level, pos, newState, isMoving);
				}
			} else {
				super.onRemove(state, level, pos, newState, isMoving);
			}
		}
		Direction facing = state.getValue(FACING);
		if (state.getValue(X_OFFSET) == 0 && state.getValue(Y_OFFSET) == 0 && state.getValue(Z_OFFSET) == 1
			&& !(newState.is(this) && newState.getValue(FACING) == facing && newState.getValue(X_OFFSET) == 0 && newState.getValue(Y_OFFSET) == 0 && newState.getValue(Z_OFFSET) == 1)) {
			for (int x = 0; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					for (int y = 0; y <= 2; y++) {
						Vec3 rotated = new Vec3(x, 0, z).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
						int rotatedX = Math.round((float) rotated.x);
						int rotatedZ = Math.round((float) rotated.z);
						if (level.getBlockState(pos.offset(rotatedX, y, rotatedZ)).is(this) && !(x == 0 && y == 0 && z == 0)) {
							level.destroyBlock(pos.offset(rotatedX, y, rotatedZ), false);
						}
					}
				}
			}
		} else {
			int x = state.getValue(X_OFFSET);
			int z = state.getValue(Z_OFFSET) - 1;
			Vec3 rotated = new Vec3(x, 0, z).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
			int rotatedX = Math.round((float) rotated.x);
			int rotatedZ = Math.round((float) rotated.z);
			checkStructure(level, pos.offset(-rotatedX, -state.getValue(Y_OFFSET), -rotatedZ));
		}
	}

	public void checkStructure(LevelAccessor level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.is(this) && state.getValue(X_OFFSET) == 0 && state.getValue(Y_OFFSET) == 0 && state.getValue(Z_OFFSET) == 1) {
			Direction facing = state.getValue(FACING);
			for (int x = 0; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					for (int y = 0; y <= 2; y++) {
						Vec3 rotated = new Vec3(x, 0, z).yRot((-facing.toYRot() + 90) * Mth.DEG_TO_RAD);
						int rotatedX = Math.round((float) rotated.x);
						int rotatedZ = Math.round((float) rotated.z);
						BlockState partState = level.getBlockState(pos.offset(rotatedX, y, rotatedZ));
						if (!(partState.is(this) && partState.getValue(FACING) == facing && partState.getValue(X_OFFSET) == x && partState.getValue(Y_OFFSET) == y && partState.getValue(Z_OFFSET) == z + 1)) {
							level.destroyBlock(pos, false);
							return;
						}
					}
				}
			}
		}
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES.getOrDefault(Pair.of(state.getValue(FACING), Triple.of(state.getValue(X_OFFSET), state.getValue(Y_OFFSET), state.getValue(Z_OFFSET))), Shapes.block());
	}

	@Override
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, X_OFFSET, Z_OFFSET, Y_OFFSET);
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return blockState.getValue(X_OFFSET) == 0 && blockState.getValue(Y_OFFSET) == 0 && blockState.getValue(Z_OFFSET) == 1 ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.INVISIBLE;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return blockState.getValue(X_OFFSET) == 0 && blockState.getValue(Y_OFFSET) == 0 && blockState.getValue(Z_OFFSET) == 1 ? new AlloyFurnaceBlockEntity(blockPos, blockState) : null;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return blockState.getValue(X_OFFSET) == 0 && blockState.getValue(Y_OFFSET) == 0 && blockState.getValue(Z_OFFSET) == 1 ? createTickerHelper(blockEntityType, ESBlockEntities.ALLOY_FURNACE.get(), AlloyFurnaceBlockEntity::tick) : null;
	}

	@Override
	public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos) {
		Direction facing = state.getValue(AlloyFurnaceBlock.FACING);
		int x = state.getValue(AlloyFurnaceBlock.X_OFFSET);
		int y = state.getValue(AlloyFurnaceBlock.Y_OFFSET);
		int z = state.getValue(AlloyFurnaceBlock.Z_OFFSET) - 1;
		Vec3 rotated = new Vec3(x, 0, z).yRot((((int) -facing.toYRot() + 90) % 360) * Mth.DEG_TO_RAD);
		int rotatedX = Math.round((float) rotated.x);
		int rotatedZ = Math.round((float) rotated.z);
		BlockPos centerPos = pos.offset(-rotatedX, -y, -rotatedZ);
		if (level.getBlockEntity(centerPos) instanceof AlloyFurnaceBlockEntity entity) {
			return entity;
		}
		return new EmptyContainer();
	}

	static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
		public EmptyContainer() {
			super(0);
		}

		@Override
		public int[] getSlotsForFace(Direction side) {
			return new int[0];
		}

		@Override
		public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
			return false;
		}

		@Override
		public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
			return false;
		}
	}
}
