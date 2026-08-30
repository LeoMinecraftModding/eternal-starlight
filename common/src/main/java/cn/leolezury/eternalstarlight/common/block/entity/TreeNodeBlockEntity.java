package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class TreeNodeBlockEntity extends BlockEntity {
	private int branchCount;
	private int branchLength;
	private boolean leafOnly;

	private TreeNodeBlockEntity(BlockPos blockPos, BlockState blockState, Optional<CropUtil.NodeDecorator> param) {
		super(ESBlockEntities.TREE_NODE.get(), blockPos, blockState);
	}

	public TreeNodeBlockEntity(BlockPos blockPos, BlockState blockState, CropUtil.NodeDecorator param) {
		this(blockPos, blockState, Optional.of(param));
	}

	public TreeNodeBlockEntity(BlockPos blockPos, BlockState blockState) {
		this(blockPos, blockState, Optional.empty());
	}
}
