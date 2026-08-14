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

	public static ResourceKey<Block> esKey(String id) {
		return ResourceKey.create(BuiltInRegistries.BLOCK.key(), EternalStarlight.id(id));
	}

	public static ResourceKey<Block> vanillaKey(String id) {
		return ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath("minecraft", id));
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
			boolean isEtherFillable
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
		}

		public static CropParam create(float growChance, int maxAge, float lightEffect, float moistEffect, int growMaximum, int minimumLightLevel, int maximumLightLevel, int unstableAge, boolean isAquatic, boolean isEtherFillable) {
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
				isEtherFillable
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
				false
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
	}

	public static class SubCropParam extends CropParam {
		private SubCropParam(float growChance, int maxAge, float lightEffect, float moistEffect, int growMaximum, ArrayList<Pair<Double, Double>> shapeX, ArrayList<Pair<Double, Double>> shapeY, ArrayList<Pair<Double, Double>> shapeZ, ArrayList<Pair<Pair<Optional<Pair<String, Pair<Optional<Pair<Integer, Pair<Integer, Integer>>>, Optional<Boolean>>>>, ResourceKey<Block>>, Pair<Pair<Integer, Float>, Boolean>>> effectableCrops, int minimumLightLevel, int maximumLightLevel, int unstableAge, boolean isAquatic, boolean isEtherFillable) {
			super(growChance, maxAge, lightEffect, moistEffect, growMaximum, shapeX, shapeY, shapeZ, effectableCrops, minimumLightLevel, maximumLightLevel, unstableAge, isAquatic, isEtherFillable);
		}
	}
}
