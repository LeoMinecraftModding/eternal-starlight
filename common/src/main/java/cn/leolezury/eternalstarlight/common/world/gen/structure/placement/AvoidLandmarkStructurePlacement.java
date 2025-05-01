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
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

import java.util.Optional;

public class AvoidLandmarkStructurePlacement extends RandomSpreadStructurePlacement {
	public static final MapCodec<AvoidLandmarkStructurePlacement> CODEC = RecordCodecBuilder.<AvoidLandmarkStructurePlacement>mapCodec(
		instance -> placementCodec(instance)
			.and(instance.group(
				Codec.intRange(0, 4096).fieldOf("spacing").forGetter(AvoidLandmarkStructurePlacement::spacing),
				Codec.intRange(0, 4096).fieldOf("separation").forGetter(AvoidLandmarkStructurePlacement::separation),
				RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(AvoidLandmarkStructurePlacement::spreadType)
			)).apply(instance, AvoidLandmarkStructurePlacement::new)).validate(AvoidLandmarkStructurePlacement::validate);

	private static DataResult<AvoidLandmarkStructurePlacement> validate(AvoidLandmarkStructurePlacement placement) {
		return placement.spacing() <= placement.separation() ? DataResult.error(() -> "Spacing has to be larger than separation") : DataResult.success(placement);
	}

	public AvoidLandmarkStructurePlacement(Vec3i locateOffset, StructurePlacement.FrequencyReductionMethod frequencyReductionMethod, float frequency, int salt, Optional<ExclusionZone> exclusionZone, int spacing, int separation, RandomSpreadType spreadType) {
		super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone, spacing, separation, spreadType);
	}

	public AvoidLandmarkStructurePlacement(int spacing, int separation, RandomSpreadType spreadType, int salt) {
		this(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1.0F, salt, Optional.empty(), spacing, separation, spreadType);
	}

	@Override
	protected boolean isPlacementChunk(ChunkGeneratorStructureState state, int x, int z) {
		ChunkPos landmarkPos = LandmarkStructurePlacement.getRegionLandmarkPos(state, x, z);
		return super.isPlacementChunk(state, x, z) && !(x == landmarkPos.x && z == landmarkPos.z);
	}

	@Override
	public StructurePlacementType<?> type() {
		return ESStructurePlacementTypes.AVOID_LANDMARK.get();
	}
}
