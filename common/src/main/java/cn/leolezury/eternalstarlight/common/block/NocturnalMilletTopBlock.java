package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NocturnalMilletTopBlock extends CropBlock {
	public static final MapCodec<NocturnalMilletTopBlock> CODEC = simpleCodec(NocturnalMilletTopBlock::new);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final BooleanProperty FORGOTTEN = BooleanProperty.create("forgotten");
	public static final BooleanProperty WITHERED = BooleanProperty.create("withered");
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

	@Override
	public MapCodec<NocturnalMilletTopBlock> codec() {
		return CODEC;
	}

	public NocturnalMilletTopBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(getStateDefinition().any().setValue(this.getAgeProperty(), 0).setValue(FORGOTTEN, false).setValue(WITHERED, false));
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(ESBlocks.NOCTURNAL_MILLET_STALK.get());
	}

	@Override
	protected IntegerProperty getAgeProperty() {
		return AGE;
	}

	@Override
	public int getMaxAge() {
		return 2;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE, FORGOTTEN, WITHERED);
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return true;
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (level.getRawBrightness(pos, 0) >= 7) {
			int age = this.getAge(state);
			if (state.getValue(WITHERED)) {
				for (int x = -5; x <= 5; x++) {
					for (int y = -1; y <= 1; y++) {
						for (int z = -5; z <= 5; z++) {
							BlockPos nearbyPos = pos.offset(x, y, z);
							Block block = level.getBlockState(nearbyPos).getBlock();
							if (block instanceof CropBlock && !(block instanceof NocturnalMilletBottomBlock) && !(block instanceof NocturnalMilletTopBlock)) {
								level.destroyBlock(nearbyPos, false);
							}
						}
					}
				}
			} else if (state.getValue(FORGOTTEN)) {
				if (age == 0) {
					level.setBlock(pos, state.setValue(AGE, age + 1).setValue(FORGOTTEN, true), Block.UPDATE_CLIENTS);
				} else if (age == 1) {
					int absorption = 0;
					for (int x = -5; x <= 5; x++) {
						for (int y = -1; y <= 1; y++) {
							for (int z = -5; z <= 5; z++) {
								BlockPos nearbyPos = pos.offset(x, y, z);
								Block block = level.getBlockState(nearbyPos).getBlock();
								if (block instanceof CropBlock && !(block instanceof NocturnalMilletBottomBlock) && !(block instanceof NocturnalMilletTopBlock)) {
									level.destroyBlock(nearbyPos, false);
									absorption++;
								}
								if (absorption == 2) break;
							}
						}
					}

					level.setBlock(pos, state.setValue(AGE, random.nextInt(4 - absorption) == 0 ? 2 : age).setValue(FORGOTTEN, true), Block.UPDATE_CLIENTS);
				}
			} else {
				if (age < this.getMaxAge() - 1) {
					level.setBlock(pos, this.getStateForAge(age + random.nextInt(2)), Block.UPDATE_CLIENTS);
				} else {
					level.setBlock(pos, this.getStateForAge(getMaxAge()), Block.UPDATE_CLIENTS);
				}
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return state.getValue(WITHERED);
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockState belowState = level.getBlockState(pos.below());
		level.setBlockAndUpdate(pos, state.setValue(WITHERED, false));
		if (belowState.is(ESBlocks.NOCTURNAL_MILLET_STALK.get()) && belowState.getValue(WITHERED)) {
			level.setBlockAndUpdate(pos.below(), belowState.setValue(WITHERED, false));
		}
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		int age = state.getValue(AGE);
		boolean forgotten = state.getValue(FORGOTTEN);
		if (age == 2) {
			popResource(level, pos, new ItemStack(forgotten ? ESItems.FORGOTTEN_NOCTURNAL_MILLET.get() : ESItems.NOCTURNAL_MILLET.get(), 1));
			level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
			BlockState newState;
			if (!forgotten) {
				if (level.getRandom().nextDouble() < 0.2) {
					newState = state.setValue(AGE, 1).setValue(WITHERED, true);
					level.setBlock(pos.below(), level.getBlockState(pos.below()).setValue(WITHERED, true), 2);
				} else {
					newState = state.setValue(AGE, 1);
				}
			} else {
				newState = state.setValue(AGE, 1);
			}
			level.setBlock(pos, newState, Block.UPDATE_CLIENTS);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.useWithoutItem(state, level, pos, player, hitResult);
		}
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ESItems.NOCTURNAL_MILLET_SEEDS.get();
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[this.getAge(state) > 0 ? 1 : 0];
	}
}
