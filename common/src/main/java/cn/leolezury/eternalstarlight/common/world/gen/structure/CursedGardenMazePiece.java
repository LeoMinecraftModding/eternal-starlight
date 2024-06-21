package cn.leolezury.eternalstarlight.common.world.gen.structure;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESStructurePieceTypes;
import cn.leolezury.eternalstarlight.common.util.MazeGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

public class CursedGardenMazePiece extends StructurePiece {
    public static final int MAZE_SIZE = 37;
    public static final int STRUCTURE_SCALE = 3;
    public static final int STRUCTURE_SIZE = MAZE_SIZE * STRUCTURE_SCALE;
    public static final int STRUCTURE_HEIGHT = 35;

    public CursedGardenMazePiece(int x, int y, int z) {
        super(ESStructurePieceTypes.CURSED_GARDEN_MAZE.get(), 0, new BoundingBox(
                x - STRUCTURE_SIZE / 2,
                y - 1,
                z - STRUCTURE_SIZE / 2,
                x + STRUCTURE_SIZE / 2,
                y + STRUCTURE_HEIGHT,
                z + STRUCTURE_SIZE / 2
        ));
        setOrientation(Direction.SOUTH);
    }

    public CursedGardenMazePiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(ESStructurePieceTypes.CURSED_GARDEN_MAZE.get(), tag);
        setOrientation(Direction.SOUTH);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {

    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) {
        MazeGenerator mazeGenerator = new MazeGenerator(MAZE_SIZE, RandomSource.create(level.getSeed() + blockPos.asLong()));
        boolean[][] maze = mazeGenerator.generateMaze(1, 1);
        maze[0][(MAZE_SIZE - 1) / 2] = false;
        maze[(MAZE_SIZE - 1) / 2][0] = false;
        maze[MAZE_SIZE - 1][(MAZE_SIZE - 1) / 2] = false;
        maze[(MAZE_SIZE - 1) / 2][MAZE_SIZE - 1] = false;
        for (int x = 0; x < MAZE_SIZE; x++) {
            for (int z = 0; z < MAZE_SIZE; z++) {
                for (int blockX = x * STRUCTURE_SCALE; blockX < x * STRUCTURE_SCALE + STRUCTURE_SCALE; blockX++) {
                    for (int blockZ = z * STRUCTURE_SCALE; blockZ < z * STRUCTURE_SCALE + STRUCTURE_SCALE; blockZ++) {
                        placeBlock(level, x != 0 && x != MAZE_SIZE - 1 && z != 0 && z != MAZE_SIZE - 1 ? ESBlocks.SHADEGRIEVE.get().defaultBlockState() : ESBlocks.VOIDSTONE_BRICKS.get().defaultBlockState(), blockX, 0, blockZ, box);
                        if (maze[x][z]) {
                            for (int y = 0; y < STRUCTURE_HEIGHT; y++) {
                                placeBlock(level, (y < STRUCTURE_HEIGHT / 7 + random.nextInt(STRUCTURE_HEIGHT / 7) && x != 0 && x != MAZE_SIZE - 1 && z != 0 && z != MAZE_SIZE - 1) ? ESBlocks.SHADEGRIEVE.get().defaultBlockState() : ESBlocks.VOIDSTONE_BRICKS.get().defaultBlockState(), blockX, y + 1, blockZ, box);
                            }
                        } else {
                            for (int y = 0; y < STRUCTURE_HEIGHT; y++) {
                                placeBlock(level, Blocks.AIR.defaultBlockState(), blockX, y + 1, blockZ, box);
                            }
                        }
                    }
                }
            }
        }
        for (int x = 1; x < STRUCTURE_SIZE; x++) {
            for (int z = 1; z < STRUCTURE_SIZE; z++) {
                if (!getBlock(level, x, 1, z, box).isAir()) {
                    for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                        int vineX = x + direction.getStepX();
                        int vineZ = z + direction.getStepZ();
                        if (getBlock(level, vineX, 1, vineZ, box).isAir()) {
                            int yTo = STRUCTURE_HEIGHT - random.nextInt(STRUCTURE_HEIGHT / 5 * 4);
                            int yFrom = random.nextInt(yTo - 3);
                            direction = direction.getAxis() == Direction.Axis.X ? direction.getOpposite() : direction;
                            for (int y = yFrom; y < yTo; y++) {
                                if (getBlock(level, vineX, y, vineZ, box).isAir() && (getBlock(level, x, y, z, box).is(ESBlocks.VOIDSTONE_BRICKS.get()) || getBlock(level, x, y, z, box).is(ESBlocks.SHADEGRIEVE.get()))) {
                                    placeBlock(level, Blocks.VINE.defaultBlockState().setValue(VineBlock.PROPERTY_BY_DIRECTION.get(direction), true), vineX, y, vineZ, box);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
