package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CropUtil {
	public static Optional<ResourceKey<Item>> cropKey(String id) {
		return Optional.of(ResourceKey.create(BuiltInRegistries.ITEM.key(), EternalStarlight.id(id)));
	}

	public static class CropParam {
		private final List<Pair<Double, Double>> shapeX;
		private final List<Pair<Double, Double>> shapeY;
		private final List<Pair<Double, Double>> shapeZ;
		private final List<Pair<BlockState, Pair<Pair<Integer, Integer>, Boolean>>> effectableCrops;
		private final Pair<Integer, Integer> lightLevelRange;
		private final float lightEffect;
		private final float moistEffect;
		private final int growMaximum;
		private final float growChance;
		private final int maxAge;

		public List<Pair<Double, Double>> getShapeZ() {
			return shapeZ;
		}

		public List<Pair<BlockState, Pair<Pair<Integer, Integer>, Boolean>>> getEffectableCrops() {
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

		private CropParam(
			float growChance,
			int maxAge,
			float lightEffect,
			float moistEffect,
			int growMaximum,
			List<Pair<Double, Double>> shapeX,
			List<Pair<Double, Double>> shapeY,
			List<Pair<Double, Double>> shapeZ,
			List<Pair<BlockState, Pair<Pair<Integer, Integer>, Boolean>>> effectableCrops,
			int minimumLightLevel,
			int maximumLightLevel
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
		}

		public static CropParam create(float growChance, int maxAge, float lightEffect, float moistEffect, int growMaximum, int minimumLightLevel, int maximumLightLevel) {
			return new CropParam(growChance, maxAge, lightEffect, moistEffect, growMaximum, List.of(), List.of(), List.of(), List.of(), minimumLightLevel, maximumLightLevel);
		}

		public CropParam addModelXYZ(int age, double x_start, double y_start, double z_start, double x_end, double y_end, double z_end) {
			this.shapeX.add(age, Pair.of(x_start, x_end));
			this.shapeY.add(age, Pair.of(y_start, y_end));
			this.shapeZ.add(age, Pair.of(z_start, z_end));
			return this;
		}

		public CropParam addRelationship(BlockState block, int range, int effect, boolean isSymbiosis) {
			this.effectableCrops.add(Pair.of(block, Pair.of(Pair.of(range, effect), isSymbiosis)));
			return this;
		}
	}
}
