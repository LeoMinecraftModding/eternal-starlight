package cn.leolezury.eternalstarlight.common.world.gen.structure.garden;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.LunarVineBlock;
import cn.leolezury.eternalstarlight.common.block.ShadegrieveBlock;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESStructurePieceTypes;
import cn.leolezury.eternalstarlight.common.util.MazeGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CursedGardenMazePiece extends StructurePiece {
	private static final int MAZE_SIZE = 37;
	private static final int STRUCTURE_SIZE = MAZE_SIZE * 3;
	private static final int STRUCTURE_HEIGHT = 12;
	private static final int CENTER_SIZE = 20;
	private static final int LUNAR_VINE_SIZE = 21;
	private static final Set<Direction> HORIZONTAL_DIRECTIONS = Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

	private StructureTemplate noConnection;
	private StructureTemplate singleConnection;
	private StructureTemplate doubleConnectionStraight;
	private StructureTemplate doubleConnectionCorner;
	private StructureTemplate tripleConnection;
	private StructureTemplate allConnection;

	private boolean[][] maze = new boolean[MAZE_SIZE][MAZE_SIZE];

	public CursedGardenMazePiece(StructureTemplateManager templateManager, long seed, int x, int y, int z) {
		super(ESStructurePieceTypes.CURSED_GARDEN_MAZE.get(), 0, new BoundingBox(
			x - STRUCTURE_SIZE / 2,
			y - 1,
			z - STRUCTURE_SIZE / 2,
			x + STRUCTURE_SIZE / 2,
			y + STRUCTURE_HEIGHT,
			z + STRUCTURE_SIZE / 2
		));
		setOrientation(Direction.SOUTH);
		loadTemplates(templateManager);
		MazeGenerator mazeGenerator = new MazeGenerator(MAZE_SIZE, RandomSource.create(seed + new BlockPos(x, y, z).asLong()));
		maze = mazeGenerator.generateMaze(1, 1);
		maze[0][(MAZE_SIZE - 1) / 2] = false;
		maze[1][(MAZE_SIZE - 1) / 2] = false;
		maze[(MAZE_SIZE - 1) / 2][0] = false;
		maze[(MAZE_SIZE - 1) / 2][1] = false;
		maze[MAZE_SIZE - 1][(MAZE_SIZE - 1) / 2] = false;
		maze[MAZE_SIZE - 2][(MAZE_SIZE - 1) / 2] = false;
		maze[(MAZE_SIZE - 1) / 2][MAZE_SIZE - 1] = false;
		maze[(MAZE_SIZE - 1) / 2][MAZE_SIZE - 2] = false;
		for (int i = 0; i < MAZE_SIZE; i++) {
			for (int j = 0; j < MAZE_SIZE; j++) {
				if (Math.pow(i * 3 + 1 - (double) STRUCTURE_SIZE / 2, 2) + Math.pow(j * 3 + 1 - (double) STRUCTURE_SIZE / 2, 2) < CENTER_SIZE * CENTER_SIZE) {
					maze[i][j] = false;
				}
			}
		}
	}

	public CursedGardenMazePiece(StructurePieceSerializationContext context, CompoundTag tag) {
		super(ESStructurePieceTypes.CURSED_GARDEN_MAZE.get(), tag);
		setOrientation(Direction.SOUTH);
		loadTemplates(context.structureTemplateManager());
		ListTag xTag = tag.getList("maze", CompoundTag.TAG_LIST);
		for (int x = 0; x < MAZE_SIZE; x++) {
			if (x < xTag.size()) {
				Tag yTag = xTag.get(x);
				if (yTag instanceof ListTag yList) {
					for (int y = 0; y < MAZE_SIZE; y++) {
						if (y < yList.size()) {
							if (yList.get(y) instanceof ByteTag byteTag) {
								maze[x][y] = byteTag.getAsByte() != 0;
							}
						}
					}
				}
			}
		}
	}

	private void loadTemplates(StructureTemplateManager templateManager) {
		this.noConnection = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_no_connection"));
		this.singleConnection = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_single_connection"));
		this.doubleConnectionStraight = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_double_connection_straight"));
		this.doubleConnectionCorner = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_double_connection_corner"));
		this.tripleConnection = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_triple_connection"));
		this.allConnection = templateManager.getOrCreate(EternalStarlight.id("cursed_garden/maze_all_connection"));
	}

	@Override
	protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
		ListTag xTag = new ListTag();
		for (int x = 0; x < MAZE_SIZE; x++) {
			ListTag yTag = new ListTag();
			for (int y = 0; y < MAZE_SIZE; y++) {
				yTag.add(ByteTag.valueOf(maze[x][y]));
			}
			xTag.add(yTag);
		}
		tag.put("maze", xTag);
	}

	@Override
	public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) {
		for (int x = 0; x < MAZE_SIZE; x++) {
			for (int z = 0; z < MAZE_SIZE; z++) {
				if (maze[x][z]) {
					BlockPos pillarPos = getWorldPos(x * 3, 1, z * 3);
					Set<Direction> connectedDirs = new HashSet<>();
					for (Direction direction : HORIZONTAL_DIRECTIONS) {
						int connectedX = x + direction.getStepX();
						int connectedZ = z + direction.getStepZ();
						if (connectedX >= 0 && connectedX < MAZE_SIZE && connectedZ >= 0 && connectedZ < MAZE_SIZE && maze[connectedX][connectedZ]) {
							connectedDirs.add(direction);
						}
					}
					switch (connectedDirs.size()) {
						case 0 -> noConnection.placeInWorld(level, pillarPos, pillarPos, new StructurePlaceSettings().setBoundingBox(box), random, Block.UPDATE_CLIENTS);
						case 1 -> connectedDirs.stream().findFirst().ifPresent(direction -> singleConnection.placeInWorld(level, pillarPos, pillarPos, new StructurePlaceSettings().setBoundingBox(box).setRotation(switch (Mth.wrapDegrees((int) (direction.toYRot() - Direction.EAST.toYRot()))) {
							case 90 -> Rotation.CLOCKWISE_90;
							case -180 -> Rotation.CLOCKWISE_180;
							case -90 -> Rotation.COUNTERCLOCKWISE_90;
							default -> Rotation.NONE;
						}).setRotationPivot(new BlockPos(1, 0, 1)), random, Block.UPDATE_CLIENTS));
						case 2 -> connectedDirs.stream().findFirst().ifPresent(direction -> {
							if (connectedDirs.contains(direction.getOpposite())) {
								doubleConnectionStraight.placeInWorld(level, pillarPos, pillarPos, new StructurePlaceSettings().setBoundingBox(box).setRotation(switch (Mth.wrapDegrees((int) (direction.toYRot() - Direction.EAST.toYRot()))) {
									case 90 -> Rotation.CLOCKWISE_90;
									case -180 -> Rotation.CLOCKWISE_180;
									case -90 -> Rotation.COUNTERCLOCKWISE_90;
									default -> Rotation.NONE;
								}).setRotationPivot(new BlockPos(1, 0, 1)), random, Block.UPDATE_CLIENTS);
							} else {
								connectedDirs.stream().max((o1, o2) -> (int) Math.signum(Mth.degreesDifference(o1.toYRot(), o2.toYRot()))).ifPresent(dir -> doubleConnectionCorner.placeInWorld(level, pillarPos, pillarPos, new StructurePlaceSettings().setBoundingBox(box).setRotation(switch (Mth.wrapDegrees((int) (dir.toYRot() - Direction.EAST.toYRot()))) {
									case 90 -> Rotation.CLOCKWISE_90;
									case -180 -> Rotation.CLOCKWISE_180;
									case -90 -> Rotation.COUNTERCLOCKWISE_90;
									default -> Rotation.NONE;
								}).setRotationPivot(new BlockPos(1, 0, 1)), random, Block.UPDATE_CLIENTS));
							}
						});
						case 3 -> new HashSet<>(HORIZONTAL_DIRECTIONS).stream().filter(d -> !connectedDirs.contains(d)).findFirst().ifPresent(direction -> tripleConnection.placeInWorld(level, pillarPos, pillarPos, new StructurePlaceSettings().setBoundingBox(box).setRotation(switch (Mth.wrapDegrees((int) (direction.toYRot() - Direction.NORTH.toYRot()))) {
							case 90 -> Rotation.CLOCKWISE_90;
							case -180 -> Rotation.CLOCKWISE_180;
							case -90 -> Rotation.COUNTERCLOCKWISE_90;
							default -> Rotation.NONE;
						}).setRotationPivot(new BlockPos(1, 0, 1)), random, Block.UPDATE_CLIENTS));
						case 4 -> allConnection.placeInWorld(level, pillarPos, pillarPos, new StructurePlaceSettings().setBoundingBox(box), random, Block.UPDATE_CLIENTS);
					}
				}
				for (int blockX = x * 3; blockX < x * 3 + 3; blockX++) {
					for (int blockZ = z * 3; blockZ < z * 3 + 3; blockZ++) {
						placeBlock(level, x != 0 && x != MAZE_SIZE - 1 && z != 0 && z != MAZE_SIZE - 1 ? ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get().defaultBlockState() : ESBlocks.GRIMSTONE_BRICKS.get().defaultBlockState(), blockX, 0, blockZ, box);
						if (maze[x][z]) {
							int leavesHeight = (STRUCTURE_HEIGHT - 1) / 3 + random.nextInt((STRUCTURE_HEIGHT - 1) / 3);
							for (int y = 0; y < STRUCTURE_HEIGHT - 1; y++) {
								placeBlock(level, (y <= leavesHeight && x != 0 && x != MAZE_SIZE - 1 && z != 0 && z != MAZE_SIZE - 1) ? (random.nextInt(3) == 0 ? ESBlocks.BLOOMING_SHADEGRIEVE.get() : ESBlocks.SHADEGRIEVE.get()).defaultBlockState().setValue(ShadegrieveBlock.TOP, y == leavesHeight) : (y == STRUCTURE_HEIGHT - 2 ? ESBlocks.GRIMSTONE_TILE_SLAB.get().defaultBlockState() : ESBlocks.GRIMSTONE_BRICKS.get().defaultBlockState()), blockX, y + 2, blockZ, box);
							}
						} else {
							for (int y = 0; y < STRUCTURE_HEIGHT; y++) {
								BlockState state = getBlock(level, blockX, y + 1, blockZ, box);
								if (!state.is(BlockTags.LOGS) && !state.is(BlockTags.LEAVES) && !state.isAir()) {
									placeBlock(level, Blocks.AIR.defaultBlockState(), blockX, y + 1, blockZ, box);
								}
							}
						}
					}
				}
			}
		}
		placeBlock(level, ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get().defaultBlockState(), STRUCTURE_SIZE / 2, 1, STRUCTURE_SIZE / 2, box);

		List<BlockPos> chestPositions = new ArrayList<>();

		for (int x = 1; x < STRUCTURE_SIZE; x++) {
			for (int z = 1; z < STRUCTURE_SIZE; z++) {
				if (!getBlock(level, x, 1, z, box).isAir() && !getBlock(level, x, 1, z, box).is(Blocks.VINE) && !getBlock(level, x, 1, z, box).is(ESBlocks.LUNAR_VINE.get())) {
					for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
						int vineX = x + direction.getStepX();
						int vineZ = z + direction.getStepZ();
						if (getBlock(level, vineX, 1, vineZ, box).isAir()) {
							double distSqr = Math.pow(vineX - (double) STRUCTURE_SIZE / 2, 2) + Math.pow(vineZ - (double) STRUCTURE_SIZE / 2, 2);
							boolean lunarVines = distSqr < LUNAR_VINE_SIZE * LUNAR_VINE_SIZE;
							boolean bossRoom = distSqr < CENTER_SIZE * CENTER_SIZE;
							int yTo = STRUCTURE_HEIGHT - random.nextInt(STRUCTURE_HEIGHT / 5 * 4) - 1;
							int yFrom = random.nextInt(yTo - 3) + 2;
							direction = direction.getAxis() == Direction.Axis.X ? direction.getOpposite() : direction;
							for (int y = yFrom; y < yTo; y++) {
								if (getBlock(level, vineX, y, vineZ, box).isAir() && (getBlock(level, x, y, z, box).is(ESBlocks.GRIMSTONE_BRICKS.get()) || getBlock(level, x, y, z, box).is(ESBlocks.SHADEGRIEVE.get()))) {
									placeBlock(level, lunarVines ? ESBlocks.LUNAR_VINE.get().defaultBlockState().setValue(LunarVineBlock.FACING, direction) : Blocks.VINE.defaultBlockState().setValue(VineBlock.PROPERTY_BY_DIRECTION.get(direction), true), vineX, y, vineZ, box);
								}
							}
							if (!bossRoom && random.nextInt(45) == 0 && chestPositions.stream().noneMatch(pos -> new BlockPos(vineX, 1, vineZ).distSqr(pos) <= 10 * 10)) {
								chestPositions.add(new BlockPos(vineX, 1, vineZ));
								createChest(level, box, random, vineX, 1, vineZ, ESLootTables.CHEST_CURSED_GARDEN);
							}
						}
					}
				}
			}
		}
	}
}
