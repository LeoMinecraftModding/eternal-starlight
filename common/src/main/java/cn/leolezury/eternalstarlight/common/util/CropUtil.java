package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CropUtil {
	public static Optional<ResourceKey<Item>> cropKey(String id) {
		return Optional.of(ResourceKey.create(BuiltInRegistries.ITEM.key(), EternalStarlight.id(id)));
	}

	public static class CropParam {
		private final ArrayList<Pair<Double, Double>> shapeX;
		private final ArrayList<Pair<Double, Double>> shapeY;
		private final ArrayList<Pair<Double, Double>> shapeZ;
		private final ArrayList<Pair<Pair<Optional<Pair<String, Pair<Optional<Pair<Integer, Pair<Integer, Integer>>>, Optional<Boolean>>>>, ResourceKey<Block>>, Pair<Pair<Integer, Integer>, Boolean>>> effectableCrops;
		private final Pair<Integer, Integer> lightLevelRange;
		private final float lightEffect;
		private final float moistEffect;
		private final int growMaximum;
		private final float growChance;
		private final int maxAge;
		private final int unstableAge;

		public List<Pair<Double, Double>> getShapeZ() {
			return shapeZ;
		}

		public List<Pair<Pair<Optional<Pair<String, Pair<Optional<Pair<Integer, Pair<Integer, Integer>>>, Optional<Boolean>>>>, ResourceKey<Block>>, Pair<Pair<Integer, Integer>, Boolean>>> getRelativeBlocks() {
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
			ArrayList<Pair<Pair<Optional<Pair<String, Pair<Optional<Pair<Integer, Pair<Integer, Integer>>>, Optional<Boolean>>>>, ResourceKey<Block>>, Pair<Pair<Integer, Integer>, Boolean>>> effectableCrops,
			int minimumLightLevel,
			int maximumLightLevel,
			int unstableAge
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
		}

		public static CropParam create(float growChance, int maxAge, float lightEffect, float moistEffect, int growMaximum, int minimumLightLevel, int maximumLightLevel, int unstableAge) {
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
			    unstableAge
			);
		}

		public CropParam addModelXYZ(int age, double x_start, double y_start, double z_start, double x_end, double y_end, double z_end) {
			this.shapeX.add(age, Pair.of(x_start, x_end));
			this.shapeY.add(age, Pair.of(y_start, y_end));
			this.shapeZ.add(age, Pair.of(z_start, z_end));
			return this;
		}

		public CropParam addRelationship(String modid, String blockId, int range, int effect, boolean isSymbiosis) {
			var block = ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(modid, blockId));
			this.effectableCrops.add(Pair.of(Pair.of(Optional.empty(), block), Pair.of(Pair.of(range, effect), isSymbiosis)));
			return this;
		}

		public CropParam addRelationship(String modid, String blockId, int range, int effect, boolean isSymbiosis, String stateName, boolean boolValue) {
			var block = ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(modid, blockId));
			this.effectableCrops.add(Pair.of(Pair.of(Optional.of(Pair.of(stateName, Pair.of(Optional.empty(), Optional.of(boolValue)))), block), Pair.of(Pair.of(range, effect), isSymbiosis)));
			return this;
		}

		public CropParam addRelationship(String modid, String blockId, int range, int effect, boolean isSymbiosis, String stateName, int targetAge, int minimumAge, int maximumAge) {
			var block = ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(modid, blockId));
			this.effectableCrops.add(Pair.of(Pair.of(Optional.of(Pair.of(stateName, Pair.of(Optional.of(Pair.of(targetAge, Pair.of(minimumAge, maximumAge))), Optional.empty()))), block), Pair.of(Pair.of(range, effect), isSymbiosis)));
			return this;
		}
	}
}
