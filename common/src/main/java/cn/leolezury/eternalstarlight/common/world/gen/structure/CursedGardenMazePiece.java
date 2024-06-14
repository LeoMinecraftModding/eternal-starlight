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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

public class CursedGardenMazePiece extends StructurePiece {
    public static final int MAZE_SIZE = 51;
    public static final int STRUCTURE_SCALE = 2;
    public static final int STRUCTURE_SIZE = MAZE_SIZE * STRUCTURE_SCALE;

    public CursedGardenMazePiece(int x, int y, int z) {
        super(ESStructurePieceTypes.CURSED_GARDEN_MAZE.get(), 0, new BoundingBox(
                x - (STRUCTURE_SIZE - 1) / 2,
                y,
                z - (STRUCTURE_SIZE - 1) / 2,
                x + (STRUCTURE_SIZE - 1) / 2 + 1,
                y + 50,
                z + (STRUCTURE_SIZE - 1) / 2 + 1
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
        MazeGenerator mazeGenerator = new MazeGenerator(MAZE_SIZE, random);
        boolean[][] maze = mazeGenerator.generateMaze(1, 1);
        maze[0][(MAZE_SIZE - 1) / 2] = false;
        maze[(MAZE_SIZE - 1) / 2][0] = false;
        maze[MAZE_SIZE - 1][(MAZE_SIZE - 1) / 2] = false;
        maze[(MAZE_SIZE - 1) / 2][MAZE_SIZE - 1] = false;
        for (int x = 0; x < STRUCTURE_SIZE; x++) {
            for (int z = 0; z < STRUCTURE_SIZE; z++) {
                int mazeX = x / STRUCTURE_SCALE;
                int mazeZ = z / STRUCTURE_SCALE;
                if (maze[mazeX][mazeZ]) {
                    int height = random.nextInt(40, 45);
                    for (int y = 0; y < height; y++) {
                        placeBlock(level, ESBlocks.POLISHED_GRIMSTONE.get().defaultBlockState(), x, y, z, box);
                    }
                }
            }
        }
    }
}
