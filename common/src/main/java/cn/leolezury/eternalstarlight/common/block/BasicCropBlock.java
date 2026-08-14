package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.mojang.datafixers.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicCropBlock extends BushBlock {
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 7);
	public static final BooleanProperty WITHERED = BooleanProperty.create("withered");

	public static final MapCodec<BasicCropBlock> CODEC = RecordCodecBuilder.mapCodec((self) -> self.group(
		propertiesCodec(),
		Codec.FLOAT.fieldOf("growChance").forGetter((block) -> block.growChance),
		Codec.INT.fieldOf("maxAge").forGetter((block) -> block.maxAge),
		Codec.list(Codec.pair(Codec.DOUBLE, Codec.DOUBLE)).fieldOf("shapes_x").forGetter((block) -> block.shapeX),
		Codec.list(Codec.pair(Codec.DOUBLE, Codec.DOUBLE)).fieldOf("shapes_y").forGetter((block) -> block.shapeY),
		Codec.list(Codec.pair(Codec.DOUBLE, Codec.DOUBLE)).fieldOf("shapes_z").forGetter((block) -> block.shapeZ),
		Codec.list(Codec.pair(Codec.pair(Codec.optionalField("block_with_state", Codec.pair(Codec.STRING, Codec.pair(Codec.optionalField("int_value_range", Codec.pair(Codec.INT, Codec.pair(Codec.INT, Codec.INT)), true).codec(), Codec.optionalField("bool_value", Codec.BOOL, true).codec())), true).codec(), ResourceKey.codec(BuiltInRegistries.BLOCK.key())), Codec.pair(Codec.pair(Codec.INT, Codec.FLOAT), Codec.BOOL))).fieldOf("relative_blocks").forGetter((block) -> block.relativeBlocks),
		Codec.pair(Codec.INT, Codec.INT).fieldOf("light_level_range").forGetter((block) -> block.lightLevelRange),
		Codec.FLOAT.fieldOf("light_effect").forGetter((block) -> block.lightEffect),
		Codec.FLOAT.fieldOf("moist_effect").forGetter((block) -> block.moistEffect),
		Codec.INT.fieldOf("growMaximum").forGetter((block) -> block.growMaximum),
		Codec.INT.fieldOf("unstable_age").forGetter((block) -> block.unstableAge)
	).apply(self, BasicCropBlock::new));
	private static final Logger log = LoggerFactory.getLogger(BasicCropBlock.class);

	private final List<Pair<Double, Double>> shapeX;
	private final List<Pair<Double, Double>> shapeY;
	private final List<Pair<Double, Double>> shapeZ;
	private final ArrayList<VoxelShape> shapes;
	private final List<
		Pair<
			Pair<
				Optional<
					Pair<
						String, //property name
						Pair<
							Optional<Pair<Integer, Pair<Integer, Integer>>>, //is int value(target age, age range)
							Optional<Boolean> //is bool value
							>
						>
					>,
				ResourceKey<Block> //target block
				>,
			Pair<
				Pair<Integer, Float>, Boolean> //range, effect, symbiosis
			>
		> relativeBlocks;
	private final float growChance;
	private final int maxAge;
	private final Pair<Integer, Integer> lightLevelRange;
	private final float lightEffect;
	private final float moistEffect;
	private final int growMaximum;
	private final int unstableAge;

	private BasicCropBlock(
		Properties properties,
		float growChance,
		int maxAge,
		List<Pair<Double, Double>> shapeX,
		List<Pair<Double, Double>> shapeY,
		List<Pair<Double, Double>> shapeZ,
		List<Pair<Pair<Optional<Pair<String, Pair<Optional<Pair<Integer, Pair<Integer, Integer>>>, Optional<Boolean>>>>, ResourceKey<Block>>, Pair<Pair<Integer, Float>, Boolean>>> relativeBlocks,
		Pair<Integer, Integer> lightLevelRange,
		float lightEffect,
		float moistEffect,
		int growMaximum,
		int unstableAge
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
			VoxelShape box = Block.box(x_s,y_s,z_s,x_e,y_e,z_e);
			shapes.add(i, box);
		}
		this.shapes = shapes;

		this.relativeBlocks = relativeBlocks;
		this.lightLevelRange = lightLevelRange;
		this.lightEffect = lightEffect;
		this.moistEffect = moistEffect;
		this.growMaximum = growMaximum;
		this.unstableAge = unstableAge;
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
			param.getRelativeBlocks(),
			param.getLightLevelRange(),
			param.getLightEffect(),
			param.getMoistEffect(),
			param.getGrowMaximum(),
			param.getUnstableAge()
		);
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return CODEC;
	}

	protected float getGrowthSpeed(ServerLevel getter, BlockPos pos) {
		final float[] speed = {0f};
		int lowestLightLevel = this.lightLevelRange.getFirst();
		int highestLightLevel = this.lightLevelRange.getSecond();
		if (!this.isWithered(getter.getBlockState(pos))) {
			this.relativeBlocks.forEach(crop -> {
				int range = crop.getSecond().getFirst().getFirst();
				float efficient = crop.getSecond().getFirst().getSecond();
				boolean symbiosis = crop.getSecond().getSecond();
				AABB detectBox = new AABB(pos.getX() - range, pos.getY() -1, pos.getZ() - range, pos.getX() + range, pos.getY() + 1, pos.getZ() + range);
				log.info(detectBox.toString());
				getter.getBlockStates(detectBox).forEach((blockState) -> {
					log.info(blockState.toString());
					var nvPair = crop.getFirst().getFirst();

					Block block = BuiltInRegistries.BLOCK.get(crop.getFirst().getSecond());
					if (blockState.getBlock().equals(block)) {
						log.info("ok");
						if (nvPair.isPresent()) {
							var pair = nvPair.get();
							String name = pair.getFirst();
							if (pair.getSecond().getFirst().isPresent()) {
								int target_age = pair.getSecond().getFirst().get().getFirst();
								var m = pair.getSecond().getFirst().get().getSecond();
								IntegerProperty property = IntegerProperty.create(name, m.getFirst(), m.getSecond());
								if (blockState.getValue(property) == target_age) {
									if (symbiosis) {
										speed[0] += efficient;
									} else {
										speed[0] -= efficient;
									}
								}
							} else {
								BooleanProperty property = BooleanProperty.create(name);
								if (pair.getSecond().getSecond().get() == blockState.getValue(property)) {
									if (symbiosis) {
										speed[0] += efficient;
									} else {
										speed[0] -= efficient;
									}
								}
							}
						} else {
							if (symbiosis) {
								speed[0] += efficient;
							} else {
								speed[0] -= efficient;
							}
						}
					}
				});
			});

			int light_level = getter.getRawBrightness(pos, 0);
			if (light_level >= lowestLightLevel && light_level <= highestLightLevel) {
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
			float speed = getGrowthSpeed(serverLevel, blockPos);
			float decay = (float) randomSource.nextIntBetweenInclusive(60, 130) / 100;
			int progression = randomSource.nextIntBetweenInclusive(0, growMaximum);
			float random_param = (float) randomSource.nextIntBetweenInclusive(0, 80) / 100;

			log.info(String.valueOf(decay));
			log.info(String.valueOf(speed));
			log.info(String.valueOf(random_param));

			if (this.growChance > random_param) {
				if (decay < speed) {
					this.grow(progression, blockState, serverLevel, blockPos);
				} else {
					if (age <= unstableAge) {
						if (decay >= 1.15) {
							blockState.setValue(WITHERED, true);
						} else {
							if (age > 0) {
								this.grow(-progression, blockState, serverLevel, blockPos);
							}
						}
					}
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
		builder.add(AGE, WITHERED);;
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
		return AGE;
	}

	public int getMaxAge() {
		return maxAge;
	}

	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return this.shapes.get(getAge(blockState));
	}
}
