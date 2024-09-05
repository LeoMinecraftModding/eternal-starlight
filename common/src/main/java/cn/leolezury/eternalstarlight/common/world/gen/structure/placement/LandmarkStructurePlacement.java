package cn.leolezury.eternalstarlight.common.world.gen.structure.placement;

import cn.leolezury.eternalstarlight.common.data.ESStructures;
import cn.leolezury.eternalstarlight.common.registry.ESStructurePlacementTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.List;
import java.util.Optional;

public class LandmarkStructurePlacement extends StructurePlacement {
	public static final MapCodec<LandmarkStructurePlacement> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		ResourceKey.codec(Registries.STRUCTURE).fieldOf("landmark").forGetter(o -> o.landmark)
	).apply(instance, LandmarkStructurePlacement::new));

	private static final List<StructureInfo> STRUCTURES = List.of(
		new StructureInfo(ESStructures.GOLEM_FORGE, 20),
		new StructureInfo(ESStructures.CURSED_GARDEN, 40)
	);

	private final ResourceKey<Structure> landmark;

	public LandmarkStructurePlacement(ResourceKey<Structure> landmark) {
		super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1, 0, Optional.empty());
		this.landmark = landmark;
	}

	public static ChunkPos getRegionLandmarkPos(ChunkGeneratorStructureState structureState, int x, int z) {
		int centerX = (x >> 5) * 32 + 16;
		int centerZ = (z >> 5) * 32 + 16;
		int seed = (int) structureState.getLevelSeed() - centerX * 20090707 + centerZ * 2024;
		int seed1 = (int) structureState.getLevelSeed() + centerX * 10370 - centerZ * 1274;
		double random = (Math.sin(seed) * 10000.0) - Math.floor((Math.sin(seed) * 10000.0));
		double random1 = (Math.sin(seed1) * 10000.0) - Math.floor((Math.sin(seed1) * 10000.0));
		centerX += (int) (-8 + random * 16);
		centerZ += (int) (-8 + random1 * 16);
		return new ChunkPos(centerX, centerZ);
	}

	@Override
	protected boolean isPlacementChunk(ChunkGeneratorStructureState structureState, int x, int z) {
		List<StructureInfo> possibleStructures = STRUCTURES.stream().filter(i -> Math.pow(x, 2) + Math.pow(z, 2) >= Math.pow(i.minSpawnDistance(), 2)).toList();
		if (possibleStructures.isEmpty()) {
			return false;
		}
		ChunkPos chunkPos = getRegionLandmarkPos(structureState, x, z);
		if (chunkPos.x != x || chunkPos.z != z) {
			return false;
		}
		return possibleStructures.get(Math.floorMod(((x >> 5) + (z >> 5)), possibleStructures.size())).structure().location().equals(landmark.location());
	}

	@Override
	public StructurePlacementType<?> type() {
		return ESStructurePlacementTypes.LANDMARK.get();
	}

	private record StructureInfo(ResourceKey<Structure> structure, int minSpawnDistance) {

	}
}
