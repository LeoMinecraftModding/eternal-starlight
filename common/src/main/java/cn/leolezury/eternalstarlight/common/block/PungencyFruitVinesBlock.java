package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PungencyFruitVinesBlock extends CropBlock {
	public static final MapCodec<PungencyFruitVinesBlock> CODEC = simpleCodec(PungencyFruitVinesBlock::new);
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)};

	@Override
	public MapCodec<PungencyFruitVinesBlock> codec() {
		return CODEC;
	}

	public PungencyFruitVinesBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(ESTags.Blocks.BASE_STONE_STARLIGHT) || blockState.is(BlockTags.DIRT);
	}

	public boolean canPlaceSeeds(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos);
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ESItems.PUNGENCY_FRUIT_SEEDS.get();
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[this.getAge(state)];
	}
}