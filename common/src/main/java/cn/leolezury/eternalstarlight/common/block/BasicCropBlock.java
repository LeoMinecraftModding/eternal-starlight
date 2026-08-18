package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.TreeNodeBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESFluids;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.CropUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BasicCropBlock extends BushBlock implements BucketPickup, LiquidBlockContainer, EntityBlock {
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 7);
	public static final BooleanProperty WITHERED = BooleanProperty.create("withered");
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty ETHERLOGGED = BooleanProperty.create("etherlogged");

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
		Codec.INT.fieldOf("unstable_age").forGetter((block) -> block.unstableAge),
		Codec.BOOL.fieldOf("is_aquatic").forGetter((block -> block.aquatic)),
		Codec.BOOL.fieldOf("is_ether_fillable").forGetter((block -> block.etherFillable)),
		ResourceKey.codec(BuiltInRegistries.BLOCK.key()).optionalFieldOf("sub_crop").forGetter((block -> block.subCrop)),
		Codec.pair(CropUtil.TreeParam.CODEC, Codec.list(CropUtil.TreeParam.CODEC)).optionalFieldOf("tree_param").forGetter((block -> block.treeParam))
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
	private final boolean aquatic;
	private final boolean etherFillable;
	private final Optional<ResourceKey<Block>> subCrop;
	private final Optional<Pair<CropUtil.TreeParam, List<CropUtil.TreeParam>>> treeParam;

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
		int unstableAge,
		boolean aquatic,
		boolean etherFillable,
		Optional<ResourceKey<Block>> subCrop,
		Optional<Pair<CropUtil.TreeParam, List<CropUtil.TreeParam>>> treeParam
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
		this.aquatic = aquatic;
		this.etherFillable = etherFillable;
		this.subCrop = subCrop;
		this.treeParam = treeParam;
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0).setValue(WITHERED, false).setValue(WATERLOGGED, false).setValue(ETHERLOGGED, false));
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
			param.getUnstableAge(),
			param.isAquatic(),
			param.isEtherFillable(),
			param.getSubCrop(),
			param.getTreeParam()
		);
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return CODEC;
	}

	protected float growSpeedDecorator(float rawSpeed) {
		return rawSpeed;
	}

	protected int detectBoxBottomModifier() {
		return 1;
	}

	protected void operateDetectedBlockState(BlockState state, BlockPos pos) {
		//todo Operate Event
	}

	protected void farmlandAdditional(boolean isAquatic, boolean isEtherFillable, BlockPos pos, ServerLevel level) {

	}

	protected final float getGrowthSpeed(ServerLevel getter, BlockPos pos, boolean triggerevent) {
		final float[] speed = {0f};
		int lowestLightLevel = this.lightLevelRange.getFirst();
		int highestLightLevel = this.lightLevelRange.getSecond();
		if (!this.isWithered(getter.getBlockState(pos))) {
			this.relativeBlocks.forEach(crop -> {
				int range = crop.getSecond().getFirst().getFirst();
				float efficient = crop.getSecond().getFirst().getSecond();
				boolean symbiosis = crop.getSecond().getSecond();
				AABB detectBox = new AABB(pos.getX() - range, pos.getY() - detectBoxBottomModifier(), pos.getZ() - range, pos.getX() + range, pos.getY() + 1, pos.getZ() + range);
				log.info(detectBox.toString());
				getter.getBlockStates(detectBox).forEach((blockState) -> {
					log.info(blockState.toString());
					var nvPair = crop.getFirst().getFirst();

					Block block = BuiltInRegistries.BLOCK.get(crop.getFirst().getSecond());
					if (triggerevent) {
						operateDetectedBlockState(blockState, pos);
					}
					if (blockState.getBlock().equals(block)) {
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
			if (farmland.is(ESTags.Blocks.FARMLAND) && !this.aquatic) {
				farmlandAdditional(aquatic, etherFillable, pos, getter);
				speed[0] += this.moistEffect * farmland.getValue(FarmBlock.MOISTURE);
			}
		}
		speed[0] = growSpeedDecorator(speed[0]);
		return speed[0];
	}

	protected void randomTickAddition(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource randomSource) {

	}

	protected void subCropExecute(BlockState originState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {

	}

	@Override
	protected final void randomTick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
		randomTickAddition(blockState, level, blockPos, randomSource);
		int age = this.getAge(blockState);

		if (!blockState.getValue(WITHERED)) {
			float random_param = (float) randomSource.nextIntBetweenInclusive(0, 80) / 100;
			if (this.growChance > random_param) {
				if (age < this.getMaxAge()) {
					float speed = getGrowthSpeed(level, blockPos, true);
					float decay = (float) randomSource.nextIntBetweenInclusive(60, 130) / 100;
					int progression = randomSource.nextIntBetweenInclusive(0, growMaximum);

					log.info(String.valueOf(decay));
					log.info(String.valueOf(speed));

					if (decay < speed) {
						this.grow(progression, blockState, level, blockPos);
					} else {
						if (age <= unstableAge) {
							if (decay >= 1.15) {
								blockState.setValue(WITHERED, true);
							} else {
								if (age > 0) {
									this.grow(-progression, blockState, level, blockPos);
								}
							}
						}
					}

				} else {
					if (this.subCrop.isEmpty()) {
						int decay = randomSource.nextIntBetweenInclusive(-1, growMaximum + 3);
						if (decay < 0 && randomSource.nextBoolean()) {
							this.grow(-1, blockState, level, blockPos);
						}
					} else {
						var subPos = blockPos.above(1);
						var subState = level.getBlockState(subPos);
						if (subState.isAir()) {
							log.info("ok");
							var subCrop = BuiltInRegistries.BLOCK.get(this.subCrop.get());
							level.setBlock(subPos, subCrop.defaultBlockState(), 2);
							subState = level.getBlockState(subPos);
							subCropExecute(subState, level, subPos, randomSource);
						}
					}
				}
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE, WITHERED, WATERLOGGED, ETHERLOGGED);;
	}

	@Override
	protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean bl) {
		if (!(this instanceof SubCropBlock) && !(this instanceof CropBranchBlock) && this.treeParam.isPresent()) {
			level.setBlockEntity(new TreeNodeBlockEntity(blockPos, blockState, this.treeParam));
		}
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

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return this.shapes.get(getAge(blockState));
	}

	@Override
	public ItemStack pickupBlock(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
		if (blockState.getValue(BlockStateProperties.WATERLOGGED)) {
			levelAccessor.setBlock(blockPos, blockState.setValue(BlockStateProperties.WATERLOGGED, false), 3);
			levelAccessor.destroyBlock(blockPos, true);

			return new ItemStack(Items.WATER_BUCKET);
		} else if (blockState.getValue(ETHERLOGGED)) {
			levelAccessor.setBlock(blockPos, blockState.setValue(ETHERLOGGED, false), 3);
			levelAccessor.destroyBlock(blockPos, true);

			return new ItemStack(ESItems.ETHER_BUCKET.get());
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public Optional<SoundEvent> getPickupSound() {
		if (aquatic) {
			return Fluids.WATER.getPickupSound();
		} else if (etherFillable) {
			return ESFluids.ETHER_STILL.get().getPickupSound();
		}
		return Optional.empty();
	}

	@Override
	public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
		return ((fluid == Fluids.WATER && this.aquatic) || (fluid == ESFluids.ETHER_STILL.get() && this.etherFillable));
	}

	@Override
	public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
		if (!blockState.getValue(BlockStateProperties.WATERLOGGED) && this.aquatic && fluidState.getType() == Fluids.WATER) {
			if (!levelAccessor.isClientSide()) {
				levelAccessor.setBlock(blockPos, blockState.setValue(BlockStateProperties.WATERLOGGED, true), 3);
				levelAccessor.scheduleTick(blockPos, fluidState.getType(), fluidState.getType().getTickDelay(levelAccessor));
			}
			return true;
		} else if (!blockState.getValue(ETHERLOGGED) && this.etherFillable && fluidState.getType() == ESFluids.ETHER_STILL.get()) {
			if (!levelAccessor.isClientSide()) {
				levelAccessor.setBlock(blockPos, blockState.setValue(ETHERLOGGED, true), 3);
				levelAccessor.scheduleTick(blockPos, fluidState.getType(), fluidState.getType().getTickDelay(levelAccessor));
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Nullable
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return null;
	}
}
