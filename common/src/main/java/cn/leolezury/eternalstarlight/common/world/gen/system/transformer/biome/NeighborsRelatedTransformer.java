package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.SkippingIterationTransformer;

import java.util.Random;

public abstract class NeighborsRelatedTransformer extends SkippingIterationTransformer {
	@Override
	public int transform(int[][] original, int[][] related, WorldGenProvider provider, Random random, int x, int z, int areaX, int areaZ, int size) {
		return transform(provider, random, original[x][z], getDataSafe(original, x, z + 1, size), getDataSafe(original, x, z - 1, size), getDataSafe(original, x - 1, z, size), getDataSafe(original, x + 1, z, size));
	}

	public abstract int transform(WorldGenProvider provider, Random random, int original, int up, int down, int left, int right);

	protected int chooseRandomly(Random random, int... i) {
		return i[random.nextInt(i.length)];
	}
}
