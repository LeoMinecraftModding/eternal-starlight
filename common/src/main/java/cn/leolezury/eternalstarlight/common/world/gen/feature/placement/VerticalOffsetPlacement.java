package cn.leolezury.eternalstarlight.common.world.gen.feature.placement;

import cn.leolezury.eternalstarlight.common.registry.ESPlacementModifierTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

/**
 * Moves placement up by a sampled amount; the vanilla random offset placement clamps its spread to 16 blocks.
 */
public class VerticalOffsetPlacement extends PlacementModifier {
	public static final MapCodec<VerticalOffsetPlacement> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			IntProvider.NON_NEGATIVE_CODEC.fieldOf("offset").forGetter(p -> p.offset)
		).apply(instance, VerticalOffsetPlacement::new)
	);

	private final IntProvider offset;

	public VerticalOffsetPlacement(IntProvider offset) {
		this.offset = offset;
	}

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		return Stream.of(pos.above(this.offset.sample(random)));
	}

	@Override
	public PlacementModifierType<?> type() {
		return ESPlacementModifierTypes.VERTICAL_OFFSET.get();
	}
}
