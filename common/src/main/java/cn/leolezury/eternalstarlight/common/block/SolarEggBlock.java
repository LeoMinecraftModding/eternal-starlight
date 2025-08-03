package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.SolarEggBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SolarEggBlock extends BaseEntityBlock {
	public static final MapCodec<SolarEggBlock> CODEC = simpleCodec(SolarEggBlock::new);
	public static final IntegerProperty X_OFFSET = IntegerProperty.create("x_offset", 0, 2);
	public static final IntegerProperty Y_OFFSET = IntegerProperty.create("y_offset", 0, 2);
	public static final IntegerProperty Z_OFFSET = IntegerProperty.create("z_offset", 0, 2);

	public SolarEggBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(X_OFFSET, 1).setValue(Y_OFFSET, 0).setValue(Z_OFFSET, 1));
	}

	@Override
	protected MapCodec<SolarEggBlock> codec() {
		return CODEC;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		CollisionContext collisionContext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
		VoxelShape shape = Shapes.empty();
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					if (!(level.getBlockState(pos.offset(x, y, z)).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(pos.offset(x, y, z)))) {
						return null;
					}
					shape = Shapes.join(shape, defaultBlockState().setValue(X_OFFSET, x + 1).setValue(Y_OFFSET, y).setValue(Z_OFFSET, z + 1).getCollisionShape(level, pos, collisionContext).move(pos.getX() + x, pos.getY() + y, pos.getZ() + z), BooleanOp.OR);
				}
			}
		}
		if (!level.isUnobstructed(null, shape)) {
			return null;
		}
		return super.getStateForPlacement(context);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					if (!(x == 0 && y == 0 && z == 0)) {
						level.setBlockAndUpdate(pos.offset(x, y, z), defaultBlockState().setValue(X_OFFSET, x + 1).setValue(Y_OFFSET, y).setValue(Z_OFFSET, z + 1));
					}
				}
			}
		}
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (state.getValue(X_OFFSET) == 1 && state.getValue(Y_OFFSET) == 0 && state.getValue(Z_OFFSET) == 1
			&& !(newState.is(this) && newState.getValue(X_OFFSET) == 1 && newState.getValue(Y_OFFSET) == 0 && newState.getValue(Z_OFFSET) == 1)) {
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					for (int y = 0; y <= 2; y++) {
						if (level.getBlockState(pos.offset(x, y, z)).is(this) && !(x == 0 && y == 0 && z == 0)) {
							level.destroyBlock(pos.offset(x, y, z), true);
						}
					}
				}
			}
		} else {
			checkStructure(level, pos.offset(1 - state.getValue(X_OFFSET), -state.getValue(Y_OFFSET), 1 - state.getValue(Z_OFFSET)));
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		checkStructure(level, pos.offset(1 - state.getValue(X_OFFSET), -state.getValue(Y_OFFSET), 1 - state.getValue(Z_OFFSET)));
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	private void checkStructure(LevelAccessor level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.is(this) && state.getValue(X_OFFSET) == 1 && state.getValue(Y_OFFSET) == 0 && state.getValue(Z_OFFSET) == 1) {
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					for (int y = 0; y <= 2; y++) {
						BlockState partState = level.getBlockState(pos.offset(x, y, z));
						if (!(partState.is(this) && partState.getValue(X_OFFSET) == x + 1 && partState.getValue(Y_OFFSET) == y && partState.getValue(Z_OFFSET) == z + 1)) {
							level.destroyBlock(pos, true);
							return;
						}
					}
				}
			}
		}
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(Math.max(0.0, 1.0 - state.getValue(X_OFFSET)), 0.0, Math.max(0.0, 1.0 - state.getValue(Z_OFFSET)), Math.min(16.0, 17.0 - state.getValue(X_OFFSET)), state.getValue(Y_OFFSET) == 2 ? 14.0 : 16.0, Math.min(16.0, 17.0 - state.getValue(Z_OFFSET)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(X_OFFSET, Z_OFFSET, Y_OFFSET);
	}

	@Override
	protected RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SolarEggBlockEntity(pos, state);
	}
}
