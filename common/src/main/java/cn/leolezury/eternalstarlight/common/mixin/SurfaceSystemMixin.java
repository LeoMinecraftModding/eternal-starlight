package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;
import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.StarlightSurfaceSystem;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SurfaceSystem.class)
public abstract class SurfaceSystemMixin implements StarlightSurfaceSystem {
	@Unique
	private ESBiomeSource starlightBiomeSource;

	@Override
	public void setStarlightBiomeSource(ESBiomeSource biomeSource) {
		this.starlightBiomeSource = biomeSource;
	}

	@Override
	public ESBiomeSource getStarlightBiomeSource() {
		return starlightBiomeSource;
	}
}
