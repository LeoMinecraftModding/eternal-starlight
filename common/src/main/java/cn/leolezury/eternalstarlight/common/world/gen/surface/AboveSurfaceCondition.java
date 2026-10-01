package cn.leolezury.eternalstarlight.common.world.gen.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.SurfaceRules;

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
