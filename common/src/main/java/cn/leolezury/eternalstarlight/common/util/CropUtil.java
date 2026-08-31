package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class CropUtil {
	public static Optional<ResourceKey<Item>> cropKey(String id) {
		return Optional.of(ResourceKey.create(BuiltInRegistries.ITEM.key(), EternalStarlight.id(id)));
	}

	public static ResourceKey<Block> esKey(String id) {
		return ResourceKey.create(BuiltInRegistries.BLOCK.key(), EternalStarlight.id(id));
	}

	public static ResourceKey<Block> vanillaKey(String id) {
		return ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("minecraft", id));
	}

	public record NodeDecorator(int branchCount, int branchLength, boolean leafOnly, int layerAbove, int layerBelow, int offsetAbove, int offsetBelow) {
		public static Codec<NodeDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("branch_count").forGetter(ins -> ins.branchCount),
			Codec.INT.fieldOf("branch_length").forGetter(ins -> ins.branchLength),
			Codec.BOOL.fieldOf("leaf_only").forGetter(ins -> ins.leafOnly),
			Codec.INT.fieldOf("layer_above").forGetter(ins -> ins.layerAbove),
			Codec.INT.fieldOf("layer_below").forGetter(ins -> ins.layerBelow),
			Codec.INT.fieldOf("offset_above").forGetter(ins -> ins.offsetAbove),
			Codec.INT.fieldOf("offset_below").forGetter(ins -> ins.offsetBelow)
		).apply(instance, NodeDecorator::new));

		public static NodeDecorator createNodeParam(int branchCount, int branchLength, boolean leafOnly, int layerAbove, int layerBelow, int offsetAbove, int offsetBelow) {
			return new NodeDecorator(branchCount, branchLength, leafOnly, layerAbove, layerBelow, offsetAbove, offsetBelow);
		}
	}

	public record TreeParam(
		int regenOffset,
		int randomOffset,
		int randomBranchLengthModifier,
		int regenBranchLengthModifier,
		int regenBranchCountModifier,
		int randomBranchCountModifier,
		int branchCount,
		int branchLength,
		boolean leafOnly,
		Pair<Integer, Integer> nodePos,
		Pair<Integer, Integer> loopOffset,
		Pair<Integer, Integer> loopTimes,
		List<NodeDecorator> additionalNodeDecorator
	) {
		public static Codec<TreeParam> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
				Codec.INT.fieldOf("regen_offset").forGetter(block -> block.regenOffset),
				Codec.INT.fieldOf("random_offset").forGetter(block -> block.randomOffset),
				Codec.INT.fieldOf("random_branch_length_modifier").forGetter(block -> block.randomBranchLengthModifier),
				Codec.INT.fieldOf("regen_branch_length_modifier").forGetter(block -> block.regenBranchLengthModifier),
				Codec.INT.fieldOf("regen_branch_count_modifier").forGetter(block -> block.regenBranchCountModifier),
				Codec.INT.fieldOf("random_branch_count_modifier").forGetter(block -> block.randomBranchCountModifier),
				Codec.INT.fieldOf("branch_count").forGetter(block -> block.branchCount),
				Codec.INT.fieldOf("branch_length").forGetter(block -> block.branchLength),
				Codec.BOOL.fieldOf("leaf_only").forGetter(block -> block.leafOnly),
				Codec.pair(Codec.INT, Codec.INT).fieldOf("node_pos").forGetter(block -> block.nodePos),
				Codec.pair(Codec.INT, Codec.INT).fieldOf("loop_offset").forGetter(block -> block.loopOffset),
				Codec.pair(Codec.INT, Codec.INT).fieldOf("loop_times").forGetter(block -> block.loopTimes),
				Codec.list(NodeDecorator.CODEC).fieldOf("additional_layer_decorator").forGetter(block -> block.additionalNodeDecorator)
			).apply(instance, TreeParam::new)
		);

		public static TreeParam create(
			int regenOffset,
			int randomOffset,
			int randomBranchLengthModifier,
			int regenBranchLengthModifier,
			int regenBranchCountModifier,
			int randomBranchCountModifier,
			int branchCount,
			boolean leafOnly,
			int firstBranchLength,
			int firstNodePos,
			int topNodePos,
			int upLoopOffset,
			int downLoopOffset,
			int upLoopTimes,
			int downLoopTimes,
			NodeDecorator topNode,
			Optional<List<NodeDecorator>> additionalNodeDecorator
		) {
			var list = new ArrayList<NodeDecorator>();
			additionalNodeDecorator.ifPresent(list::addAll);
			list.addLast(topNode);
			return new TreeParam(
				regenOffset,
				randomOffset,
				randomBranchLengthModifier,
				regenBranchLengthModifier,
				regenBranchCountModifier,
				randomBranchCountModifier,
				branchCount,
				firstBranchLength,
				leafOnly,
				Pair.of(firstNodePos, topNodePos),
				Pair.of(upLoopOffset, downLoopOffset),
				Pair.of(upLoopTimes, downLoopTimes),
				list
			);
		}

		public static TreeParam createEqualized(
			int regenOffset,
			int regenBranchLengthModifier,
			int regenBranchCountModifier,
			int branchCount,
			boolean leafOnly,
			int firstBranchLength,
			int firstNodePos,
			int topNodePos,
			int upLoopOffset,
			int downLoopOffset,
			int upLoopTimes,
			int downLoopTimes,
			NodeDecorator topNode,
			List<NodeDecorator> additionalNodeDecorator
		) {
			return create(
				regenOffset,
				0,
				0,
				regenBranchLengthModifier,
				regenBranchCountModifier,
				0,
				branchCount,
				leafOnly,
				firstBranchLength,
				firstNodePos,
				topNodePos,
				upLoopOffset,
				downLoopOffset,
				upLoopTimes,
				downLoopTimes,
				topNode,
				Optional.of(additionalNodeDecorator)
			);
		}

		public static TreeParam createEqualized(
			int regenOffset,
			int regenBranchLengthModifier,
			int regenBranchCountModifier,
			int branchCount,
			boolean leafOnly,
			int firstBranchLength,
			int firstNodePos,
			int topNodePos,
			int upLoopOffset,
			int downLoopOffset,
			int upLoopTimes,
			int downLoopTimes,
			NodeDecorator topNode
		) {
			return create(
				regenOffset,
				0,
				0,
				regenBranchLengthModifier,
				regenBranchCountModifier,
				0,
				branchCount,
				leafOnly,
				firstBranchLength,
				firstNodePos,
				topNodePos,
				upLoopOffset,
				downLoopOffset,
				upLoopTimes,
				downLoopTimes,
				topNode,
				Optional.empty()
			);
		}

		public static TreeParam createChaos(
			int randomOffset,
			int randomBranchLengthModifier,
			int randomBranchCountModifier,
			int branchCount,
			boolean leafOnly,
			int firstBranchLength,
			int firstNodePos,
			int topNodePos,
			int upLoopOffset,
			int downLoopOffset,
			int upLoopTimes,
			int downLoopTimes,
			NodeDecorator topNode
		) {
			return create(
				0,
				randomOffset,
				randomBranchLengthModifier,
				0,
				0,
				randomBranchCountModifier,
				branchCount,
				leafOnly,
				firstBranchLength,
				firstNodePos,
				topNodePos,
				upLoopOffset,
				downLoopOffset,
				upLoopTimes,
				downLoopTimes,
				topNode,
				Optional.empty()
			);
		}

		public static TreeParam createChaos(
			int randomOffset,
			int randomBranchLengthModifier,
			int randomBranchCountModifier,
			int branchCount,
			boolean leafOnly,
			int firstBranchLength,
			int firstNodePos,
			int topNodePos,
			int upLoopOffset,
			int downLoopOffset,
			int upLoopTimes,
			int downLoopTimes,
			NodeDecorator topNode,
			List<NodeDecorator> additionalNodeDecorator
		) {
			return create(
				0,
				randomOffset,
				randomBranchLengthModifier,
				0,
				0,
				randomBranchCountModifier,
				branchCount,
				leafOnly,
				firstBranchLength,
				firstNodePos,
				topNodePos,
				upLoopOffset,
				downLoopOffset,
				upLoopTimes,
				downLoopTimes,
				topNode,
				Optional.of(additionalNodeDecorator)
			);
		}
	}

	public static class CropParam {
		private final ArrayList<Pair<Double, Double>> shapeX;
		private final ArrayList<Pair<Double, Double>> shapeY;
		private final ArrayList<Pair<Double, Double>> shapeZ;
		private final ArrayList<Pair<Pair<Optional<Pair<String, Pair<Optional<Pair<Integer, Pair<Integer, Integer>>>, Optional<Boolean>>>>, ResourceKey<Block>>, Pair<Pair<Integer, Float>, Boolean>>> effectableCrops;
		private final Pair<Integer, Integer> lightLevelRange;
		private final float lightEffect;
		private final float moistEffect;
		private final int growMaximum;
		private final float growChance;
		private final int maxAge;
		private final int unstableAge;
		private final boolean isAquatic;
		private final boolean isEtherFillable;
		private final Optional<Pair<ResourceKey<Block>, Optional<ResourceKey<Block>>>> subCropBlock;
		private final int maxHeight;
		private final Optional<TreeParam> treeParam;

		public int getMaxHeight() {
			return maxHeight;
		}

		public Optional<ResourceKey<Block>> getSubCrop() {
			if (this.subCropBlock.isPresent()) {
				return this.subCropBlock.get().getSecond();
			} else {
				return Optional.empty();
			}
		}

		public Optional<ResourceKey<Block>> getOrigin() {
			return this.subCropBlock.map(Pair::getFirst);
		}

		public boolean isEtherFillable() {
			return isEtherFillable;
		}

		public boolean isAquatic() {
			return isAquatic;
		}

		public List<Pair<Double, Double>> getShapeZ() {
			return shapeZ;
		}

		public List<Pair<Pair<Optional<Pair<String, Pair<Optional<Pair<Integer, Pair<Integer, Integer>>>, Optional<Boolean>>>>, ResourceKey<Block>>, Pair<Pair<Integer, Float>, Boolean>>> getRelativeBlocks() {
			return effectableCrops;
		}

		public List<Pair<Double, Double>> getShapeY() {
			return shapeY;
		}

		public List<Pair<Double, Double>> getShapeX() {
			return shapeX;
		}

		public Pair<Integer, Integer> getLightLevelRange() {
			return lightLevelRange;
		}

		public float getLightEffect() {
			return lightEffect;
		}

		public float getMoistEffect() {
			return moistEffect;
		}

		public int getGrowMaximum() {
			return growMaximum;
		}

		public float getGrowChance() {
			return growChance;
		}

		public int getMaxAge() {
			return maxAge;
		}

		public int getUnstableAge() {
			return unstableAge;
		}

		private CropParam(
			float growChance,
			int maxAge,
			float lightEffect,
			float moistEffect,
			int growMaximum,
			ArrayList<Pair<Double, Double>> shapeX,
			ArrayList<Pair<Double, Double>> shapeY,
			ArrayList<Pair<Double, Double>> shapeZ,
			ArrayList<Pair<Pair<Optional<Pair<String, Pair<Optional<Pair<Integer, Pair<Integer, Integer>>>, Optional<Boolean>>>>, ResourceKey<Block>>, Pair<Pair<Integer, Float>, Boolean>>> effectableCrops,
			int minimumLightLevel,
			int maximumLightLevel,
			int unstableAge,
			boolean isAquatic,
			boolean isEtherFillable,
			Optional<Pair<ResourceKey<Block>, Optional<ResourceKey<Block>>>> subCropBlock,
			int maxHeight,
			Optional<TreeParam> treeParam
		) {
			this.effectableCrops = effectableCrops;
			this.shapeZ = shapeZ;
			this.shapeY = shapeY;
			this.shapeX = shapeX;
			this.lightLevelRange = Pair.of(minimumLightLevel, maximumLightLevel);
			this.lightEffect = lightEffect;;
			this.moistEffect = moistEffect;
			this.growMaximum = growMaximum;
			this.maxAge = maxAge;
			this.growChance = growChance;
			this.unstableAge = unstableAge;
			this.isAquatic = isAquatic;
			this.isEtherFillable = isEtherFillable;
			this.subCropBlock = subCropBlock;
			this.maxHeight = maxHeight;
			this.treeParam = treeParam;
		}

		public static CropParam createMultipart(int maxHeight, float growChance, int maxAge, float lightEffect, float moistEffect, int growMaximum, int minimumLightLevel, int maximumLightLevel, int unstableAge, boolean isAquatic, boolean isEtherFillable, Optional<ResourceKey<Block>> subCropBlock, ResourceKey<Block> origin) {
			return new CropParam(
				growChance,
				maxAge,
				lightEffect,
				moistEffect,
				growMaximum,
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				minimumLightLevel,
				maximumLightLevel,
				unstableAge,
				isAquatic,
				isEtherFillable,
				Optional.of(Pair.of(origin, subCropBlock)),
				maxHeight,
				Optional.empty()
			);
		}

		public static CropParam createCommon(float growChance, float lightEffect, float moistEffect, int growMaximum, int minimumLightLevel, int maximumLightLevel, int unstableAge) {
			return new CropParam(
				growChance,
				7,
				lightEffect,
				moistEffect,
				growMaximum,
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				minimumLightLevel,
				maximumLightLevel,
				unstableAge,
				false,
				false,
				Optional.empty(),
				0,
				Optional.empty()
			);
		}

		public CropParam addModelXYZ(int age, double x_start, double y_start, double z_start, double x_end, double y_end, double z_end) {
			this.shapeX.add(age, Pair.of(x_start, x_end));
			this.shapeY.add(age, Pair.of(y_start, y_end));
			this.shapeZ.add(age, Pair.of(z_start, z_end));
			return this;
		}

		public CropParam addRelationship(ResourceKey<Block> block, int range, float effect, boolean isSymbiosis) {
			this.effectableCrops.add(Pair.of(Pair.of(Optional.empty(), block), Pair.of(Pair.of(range, effect), isSymbiosis)));
			return this;
		}

		public CropParam addRelationship(ResourceKey<Block> block, int range, float effect, boolean isSymbiosis, String stateName, boolean boolValue) {
			this.effectableCrops.add(Pair.of(Pair.of(Optional.of(Pair.of(stateName, Pair.of(Optional.empty(), Optional.of(boolValue)))), block), Pair.of(Pair.of(range, effect), isSymbiosis)));
			return this;
		}

		public CropParam addRelationship(ResourceKey<Block> block, int range, float effect, boolean isSymbiosis, String stateName, int targetAge, int minimumAge, int maximumAge) {
			this.effectableCrops.add(Pair.of(Pair.of(Optional.of(Pair.of(stateName, Pair.of(Optional.of(Pair.of(targetAge, Pair.of(minimumAge, maximumAge))), Optional.empty()))), block), Pair.of(Pair.of(range, effect), isSymbiosis)));
			return this;
		}

		public Optional<TreeParam> getTreeParam() {
			return treeParam;
		}
	}
}
