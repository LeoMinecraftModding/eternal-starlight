package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TreeRootBlockEntity extends BlockEntity {
	private final Optional<CropUtil.TreeParam> param;

	private int[] nodes;
	private int[] branchLengths;
	private int[] branchCounts;
	private int loopTimes;
	private int regenOffset;
	private int loopOffset;
	private int lowestNode;
	private int topNode;
	private int randomOffset;
	private int firstBranchLength;
	private int loopBranchLengthModifier;
	private int randomBranchLengthModifier;
	private int regenBranchLengthModifier;
	private int branchCount;
	private int loopBranchCountModifier;
	private int randomBranchCountModifier;
	private int regenBranchCountModifier;

	public TreeRootBlockEntity(BlockPos blockPos, BlockState blockState, Optional<CropUtil.TreeParam> param) {
		super(ESBlockEntities.TREE_NODE.get(), blockPos, blockState);
		this.param = param;

		var nbMap = generateNodes();
		this.nodes = nbMap.keySet().stream().mapToInt(Integer::intValue).toArray();
		this.branchLengths = nbMap.values().stream().map(Pair::getFirst).mapToInt(Integer::intValue).toArray();
		this.branchCounts = nbMap.values().stream().map(Pair::getSecond).mapToInt(Integer::intValue).toArray();
	}

	public TreeRootBlockEntity(BlockPos blockPos, BlockState blockState) {
		this(blockPos, blockState, Optional.empty());
	}

	@Override
	protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		this.loopTimes = compoundTag.getInt("loopTimes");
		this.regenOffset = compoundTag.getInt("regenOffset");
		this.loopOffset = compoundTag.getInt("loopOffset");
		this.lowestNode = compoundTag.getInt("lowestNode");
		this.topNode = compoundTag.getInt("topNode");
		this.randomOffset = compoundTag.getInt("randomOffset");
		this.firstBranchLength = compoundTag.getInt("firstBranchLength");
		this.loopBranchLengthModifier = compoundTag.getInt("loopBranchLengthModifier");
		this.randomBranchLengthModifier = compoundTag.getInt("randomBranchLengthModifier");
		this.regenBranchLengthModifier = compoundTag.getInt("regenBranchLengthModifier");
		this.branchCount = compoundTag.getInt("branchCount");
		this.loopBranchCountModifier = compoundTag.getInt("loopBranchCountModifier");
		this.randomBranchCountModifier = compoundTag.getInt("randomBranchCountModifier");
		this.regenBranchCountModifier = compoundTag.getInt("regenBranchCountModifier");
		this.nodes = compoundTag.getIntArray("nodes");
		this.branchLengths = compoundTag.getIntArray("branchLengths");
		this.branchCounts = compoundTag.getIntArray("branchCounts");
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		var defaultParam = this.param.get();
		compoundTag.putInt("loopTimes", defaultParam.getLoopTimes());
		compoundTag.putInt("regenOffset", defaultParam.getRegenOffset());
		compoundTag.putInt("loopOffset", defaultParam.getLoopOffset());
		compoundTag.putInt("lowestNode", defaultParam.getLowestNode());
		compoundTag.putInt("topNode", defaultParam.getTopNode());
		compoundTag.putInt("randomOffset", defaultParam.getRandomOffset());
		compoundTag.putInt("firstBranchLength", defaultParam.getFirstBranchLength());
		compoundTag.putInt("loopBranchLengthModifier", defaultParam.getLoopBranchLengthModifier());
		compoundTag.putInt("regenBranchLengthModifier", defaultParam.getRegenBranchLengthModifier());
		compoundTag.putInt("randomBranchLengthModifier", defaultParam.getRandomBranchLengthModifier());
		compoundTag.putInt("branchCount", defaultParam.getBranchCount());
		compoundTag.putInt("loopBranchCountModifier", defaultParam.getLoopBranchCountModifier());
		compoundTag.putInt("randomBranchCountModifier", defaultParam.getRandomBranchCountModifier());
		compoundTag.putInt("regenBranchCountModifier", defaultParam.getRegenBranchCountModifier());
		compoundTag.putIntArray("nodes", this.nodes);
		compoundTag.putIntArray("nodes", this.branchLengths);
		compoundTag.putIntArray("nodes", this.branchCounts);
	}

	public Map<Integer, Pair<Integer, Integer>> generateNodes() {
		var random = RandomSource.create();
		HashMap<Integer, Pair<Integer, Integer>> nodes = new HashMap<>();

		var nodeRandomOffset = random.nextInt(0, this.randomOffset);
		var branchLengthRandomOffset = random.nextInt(0, this.randomBranchLengthModifier);
		var branchCountRandomOffset = random.nextInt(0, this.randomBranchCountModifier);
		nodes.put(lowestNode, Pair.of(firstBranchLength, branchCount));
		for (int i = this.lowestNode; i < this.topNode; i += regenOffset + nodeRandomOffset) {
			nodes.putIfAbsent(i, Pair.of(firstBranchLength + regenBranchLengthModifier, branchCount + regenBranchCountModifier));
			for(int g = 0; g < this.loopTimes; g += loopOffset) {
				nodes.putIfAbsent(i+g, Pair.of(firstBranchLength + loopBranchLengthModifier * g + branchLengthRandomOffset, branchCount + loopBranchCountModifier * g + branchCountRandomOffset));
			}
		}
//		nodes.put(this.topNode,  Pair.of(firstBranchLength, branchCount));
		return nodes;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TreeRootBlockEntity entity) {
		var param = entity.param.get();

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
