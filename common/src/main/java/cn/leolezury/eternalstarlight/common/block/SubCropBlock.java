package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.TreeNodeBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import com.mojang.datafixers.util.Pair;
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
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubCropBlock extends BasicCropBlock {
	public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
	public static final BooleanProperty IS_NODE = BooleanProperty.create("is_node");
	public static final IntegerProperty BRANCHES_COUNT = IntegerProperty.create("branches_count", 0, 8);

	private final ResourceKey<Block> origin;
	private final Optional<ResourceKey<Block>> extensionCrop;
	private final Optional<Pair<ResourceKey<Block>, ResourceKey<Block>>> branchAndLeaf;

	public SubCropBlock(Properties properties, CropUtil.CropParam param) {
		super(properties, param);
		this.origin = param.getOrigin().get();
		this.extensionCrop = param.getSubCrop();
		this.branchAndLeaf = param.getBranchAndLeaf();

		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0).setValue(WITHERED, false).setValue(WATERLOGGED, false).setValue(ETHERLOGGED, false).setValue(IS_TOP, false).setValue(IS_NODE, false).setValue(BRANCHES_COUNT, 0));
	}

	private boolean checkBelow(BlockGetter getter, BlockPos selfPos) {
		var belowState = getter.getBlockState(selfPos.below(1));
		return belowState.is(BuiltInRegistries.BLOCK.get(this.origin)) || (belowState.is(this) && this.extensionCrop.isPresent());
	}

	private boolean checkHeight(BlockGetter getter, BlockPos selfPos) {
		for (int i = 1; i < this.getMaxHeight(); i++) {
			if (getter.getBlockState(selfPos.below(i)).is(BuiltInRegistries.BLOCK.get(this.origin))) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void subCropExecute(BlockState blockState, ServerLevel level, BlockPos blockPos) {
		blockState.setValue(IS_TOP, checkHeight(level, blockPos.above()));

		if (this.extensionCrop.isPresent() && this.getAge(blockState) == this.getMaxAge()) {
			if (checkHeight(level, blockPos) && checkBelow(level, blockPos)) {
				var nextSubPos = blockPos.above(1);
				var nextSubState = level.getBlockState(nextSubPos);
				if (nextSubState.isAir()) {
					var subCrop = this;
					level.setBlock(nextSubPos, subCrop.defaultBlockState(), 2);
					nextSubState = level.getBlockState(nextSubPos);
					subCropExecute(nextSubState, level, nextSubPos);
				}
			}
		}
	}

	@Override
	protected void treeNodeExecute(ServerLevel level, BlockPos pos) {
		if (this.branchAndLeaf.isPresent() && level.getBlockEntity(pos) instanceof TreeNodeBlockEntity node) {
			var random = level.random;

			var branch =  branchAndLeaf.get().getFirst();
			var leaf = branchAndLeaf.get().getSecond();
			var param = node.getDecorator().get();
			var empty = new ArrayList<BlockPos>();

			for (int x = -1; x < 1; x++) {
				for (int z = -1; z < 1; z++) {
					var targetPos = new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z);
					if (!level.getBlockState(targetPos).is(branch)) {
						empty.add(targetPos);
					}
				}
			}

			if (empty.size() < param.branchCount()) {
				var nextPos = new ArrayList<>(List.of(pos.east(), pos.south(), pos.west(), pos.north()));
				var crossPos = new ArrayList<>(List.of(pos.east().north(), pos.east().south(), pos.west().north(), pos.west().north()));

				if (random.nextInt(0, 100) > 20) {
					empty.stream().filter(nextPos::contains).forEach(position -> {
						level.setBlock(position, BuiltInRegistries.BLOCK.get(branch).defaultBlockState(), 2);
					});
				} else {
					empty.stream().filter(crossPos::contains).forEach(position -> {
						level.setBlock(position, BuiltInRegistries.BLOCK.get(branch).defaultBlockState(), 2);
					});
				}
			}
		}
	}

	@Override
	protected int detectBoxBottomModifier() {
		return this.getMaxHeight() + 1;
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
