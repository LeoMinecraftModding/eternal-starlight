package cn.leolezury.eternalstarlight.common.world.gen.structure;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESStructurePieceTypes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.material.FluidState;

import java.util.Set;

public class StranghoulDenMainPiece extends StructurePiece {
	private static final Set<Direction> HORIZONTAL_DIRECTIONS = Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

	public StranghoulDenMainPiece(int x, int y, int z) {
		super(ESStructurePieceTypes.STRANGHOUL_DEN_MAIN.get(), 0, new BoundingBox(
			x - 7,
			y - 2,
			z - 7,
			x + 7,
			y,
			z + 7
		));
		setOrientation(Direction.SOUTH);
	}

	public StranghoulDenMainPiece(StructurePieceSerializationContext context, CompoundTag tag) {
		super(ESStructurePieceTypes.STRANGHOUL_DEN_MAIN.get(), tag);
		setOrientation(Direction.SOUTH);
	}

	@Override
	protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {

	}

	private boolean cannotSupport(BlockState state) {
		return !state.is(BlockTags.DIRT) && !state.is(ESTags.Blocks.BASE_STONE_STARLIGHT);
	}

	// changed Block.UPDATE_ALL
	@Override
	protected void placeBlock(WorldGenLevel worldGenLevel, BlockState blockState, int i, int j, int k, BoundingBox boundingBox) {
		BlockPos blockPos = this.getWorldPos(i, j, k);
		if (boundingBox.isInside(blockPos)) {
			if (this.canBeReplaced(worldGenLevel, i, j, k, boundingBox)) {
				if (this.getMirror() != Mirror.NONE) {
					blockState = blockState.mirror(this.getMirror());
				}

				if (this.getRotation() != Rotation.NONE) {
					blockState = blockState.rotate(this.getRotation());
				}

				worldGenLevel.setBlock(blockPos, blockState, Block.UPDATE_ALL);
				FluidState fluidState = worldGenLevel.getFluidState(blockPos);
				if (!fluidState.isEmpty()) {
					worldGenLevel.scheduleTick(blockPos, fluidState.getType(), 0);
				}
			}
		}
	}

	@Override
	public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) {
		boolean rackPlaced = false;
		for (int y = 0; y >= -2; y--) {
			for (int x = -7; x <= 7; x++) {
				for (int z = -7; z <= 7; z++) {
					if (x * x + z * z < (7 + y) * (7 + y)) {
						placeBlock(level, level.getBlockState(getWorldPos(x + 7, y + 1, z + 7)).getFluidState().isEmpty() ? Blocks.AIR.defaultBlockState() : ESBlocks.NIGHTFALL_MUD.get().defaultBlockState(), x + 7, y, z + 7, box);
						if (getBlock(level, x + 7, y - 1, z + 7, box).is(ESBlocks.NIGHTFALL_MUD.get())) {
							placeBlock(level, ESBlocks.FANTASY_GRASS_BLOCK.get().defaultBlockState(), x + 7, y - 1, z + 7, box);
						}
						for (Direction direction : HORIZONTAL_DIRECTIONS) {
							if ((x + direction.getStepX()) * (x + direction.getStepX()) + (z + direction.getStepZ()) * (z + direction.getStepZ()) >= (7 + y) * (7 + y) && cannotSupport(getBlock(level, x + 7 + direction.getStepX(), y, z + 7 + direction.getStepZ(), box))) {
								int additionalY = random.nextInt(6, 10);
								for (int i = 0; i >= -additionalY; i--) {
									placeBlock(level, level.isEmptyBlock(getWorldPos(x + 7 + direction.getStepX(), y + i + 1, z + 7 + direction.getStepZ())) ? ESBlocks.FANTASY_GRASS_BLOCK.get().defaultBlockState() : ESBlocks.NIGHTFALL_MUD.get().defaultBlockState(), x + 7 + direction.getStepX(), y + i, z + 7 + direction.getStepZ(), box);
									if (!cannotSupport(getBlock(level, x + 7 + direction.getStepX(), y + i, z + 7 + direction.getStepZ(), box))) {
										break;
									}
								}
							}
						}
						if (y == -2) {
							if (x * x + z * z < 3 * 3) {
								if (x == 0 && z == 0) {
									placeBlock(level, Blocks.WATER.defaultBlockState(), x + 7, -3, z + 7, box);
								} else {
									placeBlock(level, ESBlocks.NIGHTFALL_FARMLAND.get().defaultBlockState().setValue(FarmBlock.MOISTURE, 7), x + 7, -3, z + 7, box);
								}
							} else {
								if (cannotSupport(getBlock(level, x + 7, -3, z + 7, box))) {
									placeBlock(level, ESBlocks.NIGHTFALL_MUD.get().defaultBlockState(), x + 7, -3, z + 7, box);
								}
								placeBlock(level, ESBlocks.FANTASY_GRASS_CARPET.get().defaultBlockState(), x + 7, -2, z + 7, box);
								if (!rackPlaced && random.nextInt(50) == 0) {
									placeBlock(level, ESBlocks.DRYING_RACK.get().defaultBlockState(), x + 7, -2, z + 7, box);
									rackPlaced = true;
								}
							}
						}
					}
				}
			}
		}
	}
}
