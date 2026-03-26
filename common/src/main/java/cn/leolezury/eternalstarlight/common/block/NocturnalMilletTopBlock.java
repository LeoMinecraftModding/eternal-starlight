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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NocturnalMilletTopBlock extends CropBlock implements NocturnalMillet {
	public static final MapCodec<NocturnalMilletTopBlock> CODEC = simpleCodec(NocturnalMilletTopBlock::new);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

	@Override
	public MapCodec<NocturnalMilletTopBlock> codec() {
		return CODEC;
	}

	public NocturnalMilletTopBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(getStateDefinition().any().setValue(this.getAgeProperty(), 0).setValue(FORGOTTEN, false).setValue(WITHER, false));
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return blockState.is(ESBlocks.NOCTURNAL_MILLET_STALK.get());
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ESItems.NOCTURNAL_MILLET_SEEDS.get();
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[this.getAge(state) > 0 ? 1 : 0];
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
		builder.add(AGE, FORGOTTEN, WITHER);
	}

	@Override
	protected boolean isRandomlyTicking(BlockState blockState) {
		return true;
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (serverLevel.getRawBrightness(blockPos, 0) >= 7) {
			int i = this.getAge(blockState);
			if (blockState.getValue(WITHER)) {
				for (int x = -5; x <= 5; x++) {
					for (int y = -1; y <= 1; y++) {
						for (int z = -5; z <= 5; z++) {
							BlockPos nearbyPos = blockPos.offset(x, y, z);
							Block block = serverLevel.getBlockState(nearbyPos).getBlock();
							if (block instanceof CropBlock && !(block instanceof NocturnalMilletBottomBlock) && !(block instanceof NocturnalMilletTopBlock)) {
								serverLevel.destroyBlock(nearbyPos, false);
							}
						}
					}
				}
			} else if (blockState.getValue(FORGOTTEN)) {
				if (i == 0) {
					serverLevel.setBlock(blockPos, blockState.setValue(AGE, i + 1).setValue(FORGOTTEN, true), 2);
				} else if (i == 1) {
					int absorption = 0;
					for (int x = -5; x <= 5; x++) {
						for (int y = -1; y <= 1; y++) {
							for (int z = -5; z <= 5; z++) {
								BlockPos nearbyPos = blockPos.offset(x, y, z);
								Block block = serverLevel.getBlockState(nearbyPos).getBlock();
								if (block instanceof CropBlock && !(block instanceof NocturnalMilletBottomBlock) && !(block instanceof NocturnalMilletTopBlock)) {
									serverLevel.destroyBlock(nearbyPos, false);
									absorption++;
								}
								if (absorption == 2) break;
							}
						}
					}

					serverLevel.setBlock(blockPos, blockState.setValue(AGE, randomSource.nextInt(4 - absorption) == 0 ? 2 : i).setValue(FORGOTTEN, true), 2);
				}
			} else {
				if (i < this.getMaxAge() - 1) {
					serverLevel.setBlock(blockPos, this.getStateForAge(i + randomSource.nextInt(2)), 2);
				} else {
					serverLevel.setBlock(blockPos, this.getStateForAge(getMaxAge()), 2);
				}
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return blockState.getValue(WITHER);
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		BlockState belowBlockState = serverLevel.getBlockState(blockPos.below());
		serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(WITHER, false));
		if (belowBlockState.is(ESBlocks.NOCTURNAL_MILLET_STALK.get()) && belowBlockState.getValue(WITHER)) {
			serverLevel.setBlockAndUpdate(blockPos.below(), belowBlockState.setValue(WITHER, false));
		}
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		int i = blockState.getValue(AGE);
		boolean isForgotten = blockState.getValue(FORGOTTEN);
		if (i == 2) {
			popResource(level, blockPos, new ItemStack(isForgotten ? ESItems.FORGOTTEN_NOCTURNAL_MILLET.get() : ESItems.NOCTURNAL_MILLET.get(), 1));
			level.playSound(null, blockPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
			BlockState blockState2;
			if (!isForgotten) {
				if (Math.random() < 0.2) {
					blockState2 = blockState.setValue(AGE, 1).setValue(WITHER, true);
					level.setBlock(blockPos.below(), level.getBlockState(blockPos.below()).setValue(WITHER, true), 2);
				} else {
					blockState2 = blockState.setValue(AGE, 1);
				}
			} else {
				blockState2 = blockState.setValue(AGE, 1);
			}
			level.setBlock(blockPos, blockState2, 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockState2));
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult);
		}
	}
}
