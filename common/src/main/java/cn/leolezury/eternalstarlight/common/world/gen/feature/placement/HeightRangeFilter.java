package cn.leolezury.eternalstarlight.common.world.gen.feature.placement;

import cn.leolezury.eternalstarlight.common.registry.ESPlacementModifierTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class HeightRangeFilter extends PlacementFilter {
	public static final MapCodec<HeightRangeFilter> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(f -> f.minInclusive),
			VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(f -> f.maxInclusive)
		).apply(instance, HeightRangeFilter::new)
	);

	private final VerticalAnchor minInclusive;
	private final VerticalAnchor maxInclusive;

	public HeightRangeFilter(VerticalAnchor minInclusive, VerticalAnchor maxInclusive) {
		this.minInclusive = minInclusive;
		this.maxInclusive = maxInclusive;
	}

	@Override
	protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
		int minY = this.minInclusive.resolveY(context);
		int maxY = this.maxInclusive.resolveY(context);
		return pos.getY() >= minY && pos.getY() <= maxY;
	}

	@Override
	public PlacementModifierType<?> type() {
		return ESPlacementModifierTypes.HEIGHT_RANGE_FILTER.get();
	}
}
