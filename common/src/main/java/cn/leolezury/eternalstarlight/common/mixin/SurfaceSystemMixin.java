package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.StarlightSurfaceSystem;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SurfaceSystem.class)
public abstract class SurfaceSystemMixin implements StarlightSurfaceSystem {
	@Unique
	private ESChunkGenerator starlightGenerator;

	@Override
	public void setStarlightChunkGenerator(ESChunkGenerator generator) {
		starlightGenerator = generator;
	}

	@Override
	public ESChunkGenerator getStarlightChunkGenerator() {
		return starlightGenerator;
	}
}
