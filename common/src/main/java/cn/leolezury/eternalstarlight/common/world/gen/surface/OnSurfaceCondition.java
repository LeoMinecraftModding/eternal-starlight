package cn.leolezury.eternalstarlight.common.world.gen.surface;

import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.StarlightSurfaceSystem;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class OnSurfaceCondition implements SurfaceRules.ConditionSource {
	public static final OnSurfaceCondition INSTANCE = new OnSurfaceCondition();
	public static final KeyDispatchDataCodec<OnSurfaceCondition> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE));

	@Override
	public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
		return CODEC;
	}

	@Override
	public SurfaceRules.Condition apply(SurfaceRules.Context context) {
		if (context.system instanceof StarlightSurfaceSystem system && system.getStarlightBiomeSource() != null) {
			return () -> context.blockY >= system.getStarlightBiomeSource().getHeight(context.blockX, context.blockZ, context.randomState.sampler()) - 8;
		}
		return () -> false;
	}
}
