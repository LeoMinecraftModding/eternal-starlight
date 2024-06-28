package cn.leolezury.eternalstarlight.common.world.gen.system.transformer;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;

public class MergedIterationTransformer extends DataTransformer {
	public static final MapCodec<MergedIterationTransformer> CODEC = RegistryCodecs.homogeneousList(ESRegistries.DATA_TRANSFORMER, DataTransformer.CODEC).fieldOf("transformers").xmap(MergedIterationTransformer::new, transformer -> transformer.transformers);

	private final HolderSet<DataTransformer> transformers;

	public MergedIterationTransformer(HolderSet<DataTransformer> transformers) {
		this.transformers = transformers;
	}

	@Override
	public int[][] transform(int[][] original, int[][] related, WorldGenProvider provider, int areaX, int areaZ, int size, long seed, long seedAddition) {
		int[][] transformed = new int[size][size];
		int from = size <= 32 ? 0 : (size / 4 - 8);
		int to = size - from;

		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				if (x >= from && x <= to && z >= from && z <= to) {
					transformed[x][z] = original[x][z];
					for (Holder<DataTransformer> transformer : transformers) {
						if (transformer.value() instanceof SkippingIterationTransformer iterationTransformer) {
							int originalValue = original[x][z];
							original[x][z] = transformed[x][z];
							transformed[x][z] = iterationTransformer.transform(original, related, provider, getRandomForPos(x, z, areaX, areaZ, size, seed + seedAddition), x, z, areaX, areaZ, size);
							original[x][z] = originalValue;
						}
					}
				} else {
					transformed[x][z] = original[x][z];
				}
			}
		}
		return transformed;
	}

	@Override
	public DataTransformerType<?> type() {
		return ESDataTransformerTypes.MERGED_ITERATION.get();
	}
}
