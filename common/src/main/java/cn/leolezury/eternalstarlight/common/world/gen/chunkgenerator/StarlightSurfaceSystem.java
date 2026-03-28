package cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator;

import cn.leolezury.eternalstarlight.common.world.gen.biomesource.ESBiomeSource;

public interface StarlightSurfaceSystem {
	void setStarlightBiomeSource(ESBiomeSource biomeSource);

	ESBiomeSource getStarlightBiomeSource();
}
