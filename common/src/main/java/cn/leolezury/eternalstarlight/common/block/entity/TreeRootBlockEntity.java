package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import cn.leolezury.eternalstarlight.common.util.ESCodecUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static cn.leolezury.eternalstarlight.common.util.CropUtil.NodeDecorator.createNodeParam;

public class TreeRootBlockEntity extends BlockEntity {
	private Map<Integer, CropUtil.NodeDecorator> nbMap;

	private TreeRootBlockEntity(BlockPos blockPos, BlockState blockState, Optional<CropUtil.TreeParam> param) {
		super(ESBlockEntities.TREE_NODE.get(), blockPos, blockState);
	}

	public TreeRootBlockEntity(BlockPos blockPos, BlockState blockState, CropUtil.TreeParam param) {
		this(blockPos, blockState, Optional.of(param));

		this.nbMap = generateNodes(param);
	}

	public TreeRootBlockEntity(BlockPos blockPos, BlockState blockState) {
		this(blockPos, blockState, Optional.empty());
	}

	@Override
	protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.put("node_params", ESCodecUtil.createCodecForMap(Codec.INT, CropUtil.NodeDecorator.CODEC).encodeStart(NbtOps.INSTANCE, nbMap).getOrThrow());
	}

	public Map<Integer, CropUtil.NodeDecorator> generateNodes(CropUtil.TreeParam param) {
		HashMap<Integer, CropUtil.NodeDecorator> nodes = new HashMap<>();

		int upLoopTimes = param.loopTimes().getFirst();
		int downLoopTimes = param.loopTimes().getSecond();
		int regenOffset = param.regenOffset();
		int loopOffsetUp = param.loopOffset().getFirst();
		int loopOffsetDown = param.loopOffset().getSecond();
		int lowestNode = param.nodePos().getFirst();
		int topNode = param.nodePos().getSecond();
		int randomOffset = param.randomOffset();
		int firstBranchLength = param.branchLength();
		int randomBranchLengthModifier = param.randomBranchLengthModifier();
		int regenBranchLengthModifier =  param.regenBranchLengthModifier();
		int firstBranchCount = param.branchCount();
		int randomBranchCountModifier = param.randomBranchCountModifier();
		int regenBranchCountModifier = param.regenBranchCountModifier();
		boolean leafOnly = param.leafOnly();
		List<CropUtil.NodeDecorator> decorators = param.additionalNodeDecorator();

		if (!this.level.isClientSide() && decorators.getLast() != null) {
			var random = this.level.random;

			nodes.put(lowestNode, new CropUtil.NodeDecorator(firstBranchCount, firstBranchLength, leafOnly, upLoopTimes, downLoopTimes, loopOffsetUp, loopOffsetDown));
			nodes.put(topNode, decorators.getLast());

			for (int i = 1; lowestNode + regenOffset * i + randomOffset < topNode; i++) {
				var length = firstBranchLength + regenBranchLengthModifier * i;
				var count = firstBranchCount + regenBranchCountModifier * i;
				var index = lowestNode + regenOffset * i + random.nextInt(-randomOffset, randomOffset);

				var branchLengthRandomOffset = random.nextInt(0, randomBranchLengthModifier);
				var branchCountRandomOffset = random.nextInt(0, randomBranchCountModifier);

				nodes.putIfAbsent(
					index,
					createNodeParam(
						length + branchLengthRandomOffset,
						count +  branchCountRandomOffset,
						leafOnly,
						upLoopTimes,
						downLoopTimes,
						loopOffsetUp,
						loopOffsetDown
					)
				);

				if (i != 1 && lowestNode + regenOffset * i != topNode) {
					decorators.removeLast();
					nodes.put(index, decorators.get(i));
				}
			}
		}

		return nodes;
	}

	public void applyNode(ServerLevel level, BlockPos pos, int topY) {
		int index = 0;
		for (int i = 1; i < topY; i++) {
			if (level.getBlockEntity(pos.above(i), ESBlockEntities.TREE_NODE.get()).isPresent()) {
				index = i;
			}
		}

		level.setBlockEntity(
			new TreeNodeBlockEntity(
				pos.above(index),
				level.getBlockState(pos.above(index)),
				nbMap.get(index)
			)
		);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return saveCustomOnly(provider);
	}
}
