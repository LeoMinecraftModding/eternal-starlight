package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.util.CropUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Optional;

public class SubCropBlock extends BasicCropBlock {
	public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
	public static final BooleanProperty IS_NODE = BooleanProperty.create("is_node");
	public static final IntegerProperty BRANCHES_COUNT = IntegerProperty.create("branches_count", 0, 8);

	private final int maxHeight;
	private final ResourceKey<Block> origin;
	private final Optional<ResourceKey<Block>> extensionCrop;

	public SubCropBlock(Properties properties, CropUtil.CropParam param) {
		super(properties, param);
		this.maxHeight = param.getMaxHeight();
		this.origin = param.getOrigin().get();
		this.extensionCrop = param.getSubCrop();

		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0).setValue(WITHERED, false).setValue(WATERLOGGED, false).setValue(ETHERLOGGED, false).setValue(IS_TOP, false).setValue(IS_NODE, false).setValue(BRANCHES_COUNT, 0));
	}

	private boolean checkBelow(BlockGetter getter, BlockPos selfPos) {
		var belowState = getter.getBlockState(selfPos.below(1));
		return belowState.is(BuiltInRegistries.BLOCK.get(this.origin)) || (belowState.is(this) && this.extensionCrop.isPresent());
	}

	private boolean checkHeight(BlockGetter getter, BlockPos selfPos) {
		for (int i = 1; i < this.maxHeight; i++) {
			if (getter.getBlockState(selfPos.below(i)).is(BuiltInRegistries.BLOCK.get(this.origin))) {
				return true;
			}
		}
		return false;
	}

	private boolean isTree() {
		return true;
	}

	@Override
	protected void subCropExecute(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
		blockState.setValue(IS_TOP, checkHeight(level, blockPos.above()));

		if (this.extensionCrop.isPresent() && this.getAge(blockState) == this.getMaxAge()) {
			if (checkHeight(level, blockPos) && checkBelow(level, blockPos)) {
				var nextSubPos = blockPos.above(1);
				var nextSubState = level.getBlockState(nextSubPos);
				if (nextSubState.isAir()) {
					var subCrop = this;
					level.setBlock(nextSubPos, subCrop.defaultBlockState(), 2);
					nextSubState = level.getBlockState(nextSubPos);
					subCropExecute(nextSubState, level, nextSubPos, randomSource);
				}
			}
		}
	}

	@Override
	protected int detectBoxBottomModifier() {
		return this.maxHeight + 1;
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return checkBelow(blockGetter, blockPos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(IS_TOP).add(BRANCHES_COUNT).add(IS_NODE));
	}
}
