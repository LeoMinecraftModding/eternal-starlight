package cn.leolezury.eternalstarlight.common.world.gen.structure;

import cn.leolezury.eternalstarlight.common.init.StructureInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Optional;

public final class LargeJigsawStructure extends Structure {
    public static final Codec<LargeJigsawStructure> CODEC = RecordCodecBuilder.<LargeJigsawStructure>mapCodec((instance) -> {
        return instance.group(settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((structure) -> {
            return structure.startPool;
        }), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((structure) -> {
            return structure.startJigsawName;
        }), Codec.intRange(0, 20).fieldOf("size").forGetter((structure) -> {
            return structure.maxDepth;
        }), HeightProvider.CODEC.fieldOf("start_height").forGetter((structure) -> {
            return structure.startHeight;
        }), Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((structure) -> {
            return structure.projectStartToHeightmap;
        }), Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter((structure) -> {
            return structure.maxDistanceFromCenter;
        })).apply(instance, LargeJigsawStructure::new);
    }).codec();
    
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public LargeJigsawStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        int i = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(), i, chunkpos.getMinBlockZ());
        return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.maxDepth, blockpos, false, this.projectStartToHeightmap, this.maxDistanceFromCenter);
    }

    public StructureType<?> type() {
        return StructureInit.LARGE_JIGSAW.get();
    }
}
