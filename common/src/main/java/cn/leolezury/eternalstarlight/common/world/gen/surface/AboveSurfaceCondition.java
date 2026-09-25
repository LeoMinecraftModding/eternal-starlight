package cn.leolezury.eternalstarlight.common.world.gen.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.SurfaceRules;

/**
 * True within {@code depth} blocks below the column's own floor. Vanilla's preliminary surface check interpolates the
 * level across a sixteen block surface cell, which is far too coarse for the twenty plus block deep river beds of this
 * dimension, so those beds fall outside the gate and lose their material. The floor comes from the chunk's own heightmap
 * instead, which is primed from the filled chunk the first time it is asked for.
 */
public record AboveSurfaceCondition(int depth) implements SurfaceRules.ConditionSource {
	public static final MapCodec<AboveSurfaceCondition> DATA_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.INT.fieldOf("depth").forGetter(AboveSurfaceCondition::depth)
	).apply(instance, AboveSurfaceCondition::new));
	public static final KeyDispatchDataCodec<AboveSurfaceCondition> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

	@Override
	public SurfaceRules.Condition apply(SurfaceRules.Context context) {
		// the floor only changes with the column, so it is looked up once per column instead of once per block
		return new SurfaceRules.Condition() {
			private int lastX = Integer.MIN_VALUE;
			private int lastZ = Integer.MIN_VALUE;
			private int floor;

			@Override
			public boolean test() {
				int x = context.blockX;
				int z = context.blockZ;
				if (x != lastX || z != lastZ) {
					lastX = x;
					lastZ = z;
					floor = context.chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z) - AboveSurfaceCondition.this.depth;
				}
				return context.blockY >= floor;
			}
		};
	}

	@Override
	public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
		return CODEC;
	}
}
