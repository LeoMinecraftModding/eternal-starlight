package cn.leolezury.eternalstarlight.common.world.gen.system;

import cn.leolezury.eternalstarlight.common.data.ESBiomeData;
import cn.leolezury.eternalstarlight.common.data.ESDataTransformers;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformer;
import net.minecraft.core.Registry;

public class WorldArea {
	public final WorldGenProvider provider;
	public final long seed;
	public final int areaX;
	public final int areaZ;
	private int[][] biomes;
	private int[] finalBiomes;
	private int[][] heights;
	private int[] finalHeights;
	public int size;

	public WorldArea(WorldGenProvider provider, int areaX, int areaZ, int size, long seed) {
		this.provider = provider;
		this.areaX = areaX;
		this.areaZ = areaZ;
		this.size = size;
		this.seed = seed;
	}

	public void initBiomes() {
		this.biomes = new int[this.size][this.size];
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				this.biomes[x][z] = provider.getBiomeDataId(provider.biomeDataRegistry.get(ESBiomeData.STARLIGHT_FOREST));
			}
		}
	}

	public void transformBiomes(DataTransformer transformer, long seedAddition) {
		this.biomes = transformer.transform(this.biomes, this.biomes, provider, areaX, areaZ, size, seed, seedAddition);
		this.size = this.biomes.length;
	}

	public void initHeights() {
		this.heights = new int[this.size][this.size];
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				this.heights[x][z] = provider.getBiomeDataById(biomes[x][z]).height();
			}
		}
	}

	public void transformHeights(DataTransformer transformer, long seedAddition) {
		this.heights = transformer.transform(this.heights, this.biomes, provider, areaX, areaZ, size, seed, seedAddition);
	}

	public void finalizeAll(Registry<DataTransformer> registry) {
		int tempSize = this.size; // 2048
		transformBiomes(registry.getOrThrow(ESDataTransformers.FINALIZE_BIOMES), 0);
		int finalSize = this.size; // 1024
		this.size = tempSize; // 2048
		transformHeights(registry.getOrThrow(ESDataTransformers.FINALIZE_HEIGHTS), 0);
		this.size = finalSize; // 1024
		this.finalBiomes = new int[this.size * this.size];
		for (int i = 0; i < this.size; i++) {
			System.arraycopy(this.biomes[i], 0, this.finalBiomes, i * this.size, this.size);
		}
		this.finalHeights = new int[this.size * this.size];
		for (int i = 0; i < this.size; i++) {
			System.arraycopy(this.heights[i], 0, this.finalHeights, i * this.size, this.size);
		}
	}

	public BiomeData getBiomeData(int x, int z) {
		return provider.getBiomeDataById(getBiome(x, z));
	}

	public int getBiome(int x, int z) {
		int dataX = (size == 1024 ? x : ((int) Math.floor(x * size / 1024d))) & (size - 1);
		int dataZ = (size == 1024 ? z : ((int) Math.floor(z * size / 1024d))) & (size - 1);
		return finalBiomes[size * dataX + dataZ];
	}

	public int getHeight(int x, int z) {
		int dataX = (size == 1024 ? x : ((int) Math.floor(x * size / 1024d))) & (size - 1);
		int dataZ = (size == 1024 ? z : ((int) Math.floor(z * size / 1024d))) & (size - 1);
		return finalHeights[size * dataX + dataZ];
	}
}
