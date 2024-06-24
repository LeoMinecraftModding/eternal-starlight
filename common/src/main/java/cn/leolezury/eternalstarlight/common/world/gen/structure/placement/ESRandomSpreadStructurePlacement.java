package cn.leolezury.eternalstarlight.common.world.gen.structure.placement;

import cn.leolezury.eternalstarlight.common.registry.ESStructurePlacementTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.Optional;

public class ESRandomSpreadStructurePlacement extends RandomSpreadStructurePlacement {
	public static final MapCodec<ESRandomSpreadStructurePlacement> CODEC = RecordCodecBuilder.<ESRandomSpreadStructurePlacement>mapCodec((instance) -> placementCodec(instance).and(instance.group(
		Codec.intRange(0, 4096).fieldOf("spacing").forGetter(RandomSpreadStructurePlacement::spacing),
		Codec.intRange(0, 4096).fieldOf("separation").forGetter(RandomSpreadStructurePlacement::separation),
		Codec.intRange(0, 4096).fieldOf("minimum_distance_from_spawn").forGetter(placement -> placement.minDistFromSpawn)
	)).apply(instance, ESRandomSpreadStructurePlacement::new)).validate(ESRandomSpreadStructurePlacement::validate);

	private final int minDistFromSpawn;

	private static DataResult<ESRandomSpreadStructurePlacement> validate(ESRandomSpreadStructurePlacement placement) {
		return placement.spacing() <= placement.separation() ? DataResult.error(() -> {
			return "Spacing has to be larger than separation";
		}) : DataResult.success(placement);
	}

	public ESRandomSpreadStructurePlacement(Vec3i vec3i, FrequencyReductionMethod reductionMethod, float frequency, int salt, Optional<ExclusionZone> exclusionZone, int spacing, int separation, int minDistFromSpawn) {
		super(vec3i, reductionMethod, frequency, salt, exclusionZone, spacing, separation, RandomSpreadType.LINEAR);
		this.minDistFromSpawn = minDistFromSpawn;
	}

	public int minDistFromSpawn() {
		return this.minDistFromSpawn;
	}

	protected boolean isPlacementChunk(ChunkGeneratorStructureState structureState, int chunkX, int chunkZ) {
		ChunkPos chunkPos = this.getPotentialStructureChunk(structureState.getLevelSeed(), chunkX, chunkZ);
		return super.isPlacementChunk(structureState, chunkX, chunkZ) && Math.pow(chunkPos.x, 2) + Math.pow(chunkPos.z, 2) >= Math.pow(minDistFromSpawn, 2);
	}

	public StructurePlacementType<?> type() {
		return ESStructurePlacementTypes.ES_RANDOM_SPREAD.get();
	}
}
