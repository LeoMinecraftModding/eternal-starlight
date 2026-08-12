package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class BasicCropBlock extends BushBlock {
	public final IntegerProperty age;
	public static final BooleanProperty WITHERED = BooleanProperty.create("withered");
	public static final MapCodec<BasicCropBlock> CODEC = RecordCodecBuilder.mapCodec((self) -> self.group(
		propertiesCodec(),
		Codec.FLOAT.fieldOf("growChance").forGetter((block) -> block.growChance),
		Codec.INT.fieldOf("maxAge").forGetter((block) -> block.maxAge),
		Codec.list(Codec.pair(Codec.DOUBLE, Codec.DOUBLE)).fieldOf("shapes_x").forGetter((block) -> block.shapeX),
		Codec.list(Codec.pair(Codec.DOUBLE, Codec.DOUBLE)).fieldOf("shapes_y").forGetter((block) -> block.shapeY),
		Codec.list(Codec.pair(Codec.DOUBLE, Codec.DOUBLE)).fieldOf("shapes_z").forGetter((block) -> block.shapeZ),
		Codec.list(Codec.pair(BlockState.CODEC, Codec.pair(Codec.pair(Codec.INT, Codec.INT), Codec.BOOL))).fieldOf("opposition_crops").forGetter((block) -> block.effectableCrops),
		Codec.pair(Codec.INT, Codec.INT).fieldOf("light_level_range").forGetter((block) -> block.lightLevelRange),
		Codec.FLOAT.fieldOf("light_effect").forGetter((block) -> block.lightEffect),
		Codec.FLOAT.fieldOf("moist_effect").forGetter((block) -> block.moistEffect),
		Codec.INT.fieldOf("growMaximum").forGetter((block) -> block.growMaximum)
	).apply(self, BasicCropBlock::new));

	private final List<Pair<Double, Double>> shapeX;
	private final List<Pair<Double, Double>> shapeY;
	private final List<Pair<Double, Double>> shapeZ;
	private final ArrayList<VoxelShape> shapes;
	private final List<Pair<BlockState, Pair<Pair<Integer, Integer>, Boolean>>> effectableCrops;
	private final float growChance;
	private final int maxAge;
	private final Pair<Integer, Integer> lightLevelRange;
	private final float lightEffect;
	private final float moistEffect;
	private final int growMaximum;

	private BasicCropBlock(
		Properties properties,
		float growChance,
		int maxAge,
		List<Pair<Double, Double>> shapeX,
		List<Pair<Double, Double>> shapeY,
		List<Pair<Double, Double>> shapeZ,
		List<Pair<BlockState, Pair<Pair<Integer, Integer>, Boolean>>> effectableCrops,
		Pair<Integer, Integer> lightLevelRange,
		float lightEffect,
		float moistEffect,
		int growMaximum
	) {
		super(properties);
		this.growChance = growChance;
		this.maxAge = maxAge;
		this.shapeX = shapeX;
		this.shapeY = shapeY;
		this.shapeZ = shapeZ;
		ArrayList<VoxelShape> shapes = new ArrayList<>();
		for (int i = 0; i <= maxAge; i++) {
			double x_s = shapeX.get(i).getFirst();
			double x_e = shapeX.get(i).getSecond();
			double y_s = shapeY.get(i).getFirst();
			double y_e = shapeY.get(i).getSecond();
			double z_s = shapeZ.get(i).getFirst();
			double z_e = shapeZ.get(i).getSecond();
			VoxelShape box = Block.box(x_s,x_e,y_s,y_e,z_s,z_e);
			shapes.add(i, box);
		}
		this.shapes = shapes;
		this.effectableCrops = effectableCrops;
		this.lightLevelRange = lightLevelRange;
		this.lightEffect = lightEffect;
		this.moistEffect = moistEffect;
		this.growMaximum = growMaximum;
		this.age = IntegerProperty.create("age", 0, maxAge);
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0).setValue(WITHERED, false));
	}

	public BasicCropBlock(
		Properties properties,
		CropUtil.CropParam param
	) {
		this(
			properties,
			param.getGrowChance(),
			param.getMaxAge(),
			param.getShapeX(),
			param.getShapeY(),
			param.getShapeZ(),
			param.getEffectableCrops(),
			param.getLightLevelRange(),
			param.getLightEffect(),
			param.getMoistEffect(),
			param.getGrowMaximum()
		);
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return CODEC;
	}

	protected float getGrowthSpeed(Block block, BlockGetter getter, BlockPos pos) {
		final float[] speed = {0f};
		int lowest_light_level = this.lightLevelRange.getFirst();
		int highest_light_level = this.lightLevelRange.getSecond();
		if (this.isWithered(getter.getBlockState(pos))) {
			this.effectableCrops.forEach(crop -> {
				int range = crop.getSecond().getFirst().getFirst();
				int efficient = crop.getSecond().getFirst().getSecond();
				boolean symbiosis = crop.getSecond().getSecond();
				getter.getBlockStates(new AABB(-range, -1, -range, range, 1, range)).forEach((blockState) -> {
					if (blockState.equals(crop.getFirst())) {
						if (symbiosis) {
							speed[0] += efficient;
						} else {
							speed[0] -= efficient;
						}
					}
				});
			});

			int light_level =getter.getLightEmission(pos);
			if (light_level >= lowest_light_level && light_level <= highest_light_level) {
				speed[0] += this.lightEffect;
			} else {
				speed[0] -= this.lightEffect;
			}

			BlockState farmland = getter.getBlockState(pos.below());
			if (farmland.is(ESTags.Blocks.FARMLAND)) {
				speed[0] += this.moistEffect * farmland.getValue(FarmBlock.MOISTURE);
			}
		}
		return speed[0];
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		int age = this.getAge(blockState);

		if (age < this.getMaxAge() && !blockState.getValue(WITHERED)) {
			float speed = getGrowthSpeed(this, serverLevel, blockPos);
			float decay = (float) randomSource.nextIntBetweenInclusive(60, 130) / 100;
			int progression = randomSource.nextIntBetweenInclusive(0, growMaximum);
			int random_param = randomSource.nextIntBetweenInclusive(0, 1);

			if (this.growChance > random_param) {
				if (decay < speed) {
					this.grow(progression, blockState, serverLevel, blockPos);
				} else {
					if (age == 0 && decay > 1.08) {
						blockState.setValue(WITHERED, true);
					}
					this.grow(-progression, blockState, serverLevel, blockPos);
				}
			}
		} else {
			int decay = randomSource.nextIntBetweenInclusive(-1, growMaximum + 3);
			if (decay < 0 && randomSource.nextBoolean()) {
				this.grow(-1, blockState, serverLevel, blockPos);
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(age, WITHERED);;
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		//todo more farmland
		return blockState.is(ESBlocks.NIGHTFALL_FARMLAND.get());
	}

	public int getAge(BlockState blockState) {
		return blockState.getValue(this.getAgeProperty());
	}

	public boolean isWithered(BlockState state) {
		return state.getValue(WITHERED);
	}

	public void grow(int progress, BlockState self, ServerLevel level, BlockPos pos) {
		level.setBlock(pos, this.defaultBlockState().setValue(this.getAgeProperty(), getAge(self) + progress), 2);
	}

	protected IntegerProperty getAgeProperty() {
		return age;
	}

	public int getMaxAge() {
		return maxAge;
	}

	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return this.shapes.get(getAge(blockState));
	}
}
