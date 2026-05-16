package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AlwaysSurvivingBushBlock extends BushBlock {
	public static final MapCodec<AlwaysSurvivingBushBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		Codec.INT.fieldOf("height").forGetter((block) -> block.height),
		propertiesCodec()
	).apply(instance, AlwaysSurvivingBushBlock::new));
	private final int height;
	private final VoxelShape shape;

	public AlwaysSurvivingBushBlock(Properties properties) {
		this(3, properties);
	}

	public AlwaysSurvivingBushBlock(int height, Properties properties) {
		super(properties);
		this.height = height;
		this.shape = Block.box(2.0D, 0.0D, 2.0D, 14.0D, height, 14.0D);
	}

	@Override
	protected MapCodec<AlwaysSurvivingBushBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		return shape;
	}
}
