package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class TreeNodeBlockEntity extends BlockEntity {
	private final Optional<Pair<CropUtil.TreeParam, List<CropUtil.TreeParam>>> param;

	public TreeNodeBlockEntity(BlockPos blockPos, BlockState blockState, Optional<Pair<CropUtil.TreeParam, List<CropUtil.TreeParam>>> param) {
		super(ESBlockEntities.TREE_NODE.get(), blockPos, blockState);
		this.param = param;
	}

	public TreeNodeBlockEntity(BlockPos blockPos, BlockState blockState) {
		this(blockPos, blockState, Optional.empty());
	}

	@Override
	protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);

	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
	}
}
