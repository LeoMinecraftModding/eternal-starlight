package cn.leolezury.eternalstarlight.common.world.gen.structure;

import cn.leolezury.eternalstarlight.common.entity.living.monster.Tangled;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESStructureTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class CursedGardenStructure extends Structure {
	public static final MapCodec<CursedGardenStructure> CODEC = simpleCodec(CursedGardenStructure::new);

	public CursedGardenStructure(StructureSettings structureSettings) {
		super(structureSettings);
	}

	@Override
	protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
		return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, builder -> {
			ChunkPos chunkPos = context.chunkPos();
			int x = chunkPos.getMiddleBlockX();
			int z = chunkPos.getMiddleBlockZ();
			int y = context.chunkGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()) + 1;
			builder.addPiece(new CursedGardenMazePiece(x, y, z));
		});
	}

	@Override
	public void afterPlace(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, PiecesContainer piecesContainer) {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		BoundingBox piecesBox = piecesContainer.calculateBoundingBox();

		for (int x = boundingBox.minX(); x <= boundingBox.maxX(); x++) {
			for (int z = boundingBox.minZ(); z <= boundingBox.maxZ(); z++) {
				for (int y = piecesBox.minY(); y <= piecesBox.maxY(); y++) {
					pos.set(x, y, z);
					if (piecesBox.isInside(pos) && piecesContainer.isInsidePiece(pos)) {
						if ((level.isEmptyBlock(pos) || level.getBlockState(pos).is(Blocks.VINE)) && (level.isEmptyBlock(pos.above()) || level.getBlockState(pos.above()).is(Blocks.VINE)) && level.getBlockState(pos.below()).is(ESBlocks.SHADEGRIEVE.get())) {
							if (random.nextInt(85) == 0) {
								Tangled tangled = new Tangled(ESEntities.TANGLED.get(), level.getLevel());
								tangled.setPos(pos.getBottomCenter());
								tangled.setPersistenceRequired();
								level.addFreshEntity(tangled);
							}
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public StructureType<?> type() {
		return ESStructureTypes.CURSED_GARDEN.get();
	}
}
