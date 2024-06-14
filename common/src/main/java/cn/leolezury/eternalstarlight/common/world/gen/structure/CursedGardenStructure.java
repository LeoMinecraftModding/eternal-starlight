package cn.leolezury.eternalstarlight.common.world.gen.structure;

import cn.leolezury.eternalstarlight.common.registry.ESStructureTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
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
            StructureTemplateManager manager = context.structureTemplateManager();
            ChunkPos chunkPos = context.chunkPos();
            WorldgenRandom random = context.random();
            int x = chunkPos.getMinBlockX();
            int z = chunkPos.getMinBlockZ();
            int y = context.chunkGenerator().getFirstOccupiedHeight(
                    x, z, Heightmap.Types.WORLD_SURFACE_WG,
                    context.heightAccessor(), context.randomState()) + 1;
            builder.addPiece(new CursedGardenMazePiece(x, y, z));
        });
    }

    @Override
    public StructureType<?> type() {
        return ESStructureTypes.CURSED_GARDEN.get();
    }
}
