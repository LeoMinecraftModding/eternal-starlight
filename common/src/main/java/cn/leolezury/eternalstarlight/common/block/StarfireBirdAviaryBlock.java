package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StarfireBirdAviaryBlock extends StarfireBirdNestBlock {
	public static final MapCodec<StarfireBirdAviaryBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(BlockSetType.CODEC.fieldOf("block_set_type").forGetter((block) -> block.type), propertiesCodec()).apply(instance, StarfireBirdAviaryBlock::new));
	public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.75D, 16.0D); // fix lighting
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	private final BlockSetType type;

	public StarfireBirdAviaryBlock(BlockSetType type, Properties properties) {
		super(properties);
		this.registerDefaultState(defaultBlockState().setValue(OPEN, false));
		this.type = type;
	}

	@Override
	protected MapCodec<StarfireBirdAviaryBlock> codec() {
		return CODEC;
	}

	@Override
	public float getSeedsRenderOffset() {
		return 0.0625F * 3;
	}

	@Override
	public boolean canAccessNestContent(BlockState state) {
		return state.getValue(OPEN);
	}

	@Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (!level.isClientSide) {
			boolean open = state.getValue(OPEN);
			level.setBlockAndUpdate(pos, state.setValue(OPEN, !open));
			level.playSound(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, open ? type.trapdoorClose() : type.trapdoorOpen(), SoundSource.BLOCKS, 1.0F, 1.0F);
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return true;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(OPEN);
	}
}
