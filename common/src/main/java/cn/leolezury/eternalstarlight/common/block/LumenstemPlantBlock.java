package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class LumenstemPlantBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer {
	public static final MapCodec<LumenstemPlantBlock> CODEC = simpleCodec(LumenstemPlantBlock::new);
	public static final EnumProperty<LumenstemState> LUMENSTEM_STATE = EnumProperty.create("lumenstem_state", LumenstemState.class);

	public LumenstemPlantBlock(Properties properties) {
		super(properties, Direction.UP, LumenstemBlock.SHAPE, false);
		this.registerDefaultState(defaultBlockState().setValue(LUMENSTEM_STATE, LumenstemState.MIDDLE));
	}

	@Override
	protected MapCodec<LumenstemPlantBlock> codec() {
		return CODEC;
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return ESItems.LUMENSTEM.get().getDefaultInstance();
	}

	@Override
	protected GrowingPlantHeadBlock getHeadBlock() {
		return ESBlocks.LUMENSTEM.get();
	}

	@Override
	public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
		return false;
	}

	@Override
	public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
		return false;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 ? getSuitableState(super.getStateForPlacement(context), context.getLevel(), context.getClickedPos()) : null;
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		return getSuitableState(super.updateShape(state, direction, neighborState, level, pos, neighborPos), level, pos);
	}

	private BlockState getSuitableState(BlockState state, LevelReader level, BlockPos pos) {
		if (state == null) {
			return null;
		}
		BlockState upper = level.getBlockState(pos.relative(growthDirection));
		BlockState lower = level.getBlockState(pos.relative(growthDirection.getOpposite()));
		if (upper.is(getHeadBlock()) && lower.is(this)) {
			return state.setValue(LUMENSTEM_STATE, LumenstemState.TOP);
		}
		if (upper.is(this) && lower.is(this)) {
			return state.setValue(LUMENSTEM_STATE, LumenstemState.MIDDLE);
		}
		if (upper.is(this) && !lower.is(this)) {
			return state.setValue(LUMENSTEM_STATE, LumenstemState.BOTTOM);
		}
		if (upper.is(getHeadBlock()) && !lower.is(this)) {
			return state.setValue(LUMENSTEM_STATE, LumenstemState.SINGLE);
		}
		return state;
	}

	@Override
	public FluidState getFluidState(BlockState blockState) {
		return Fluids.WATER.getSource(false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LUMENSTEM_STATE);
	}

	public enum LumenstemState implements StringRepresentable {
		TOP("top"),
		MIDDLE("middle"),
		BOTTOM("bottom"),
		SINGLE("single");

		private final String name;

		LumenstemState(final String string) {
			this.name = string;
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
