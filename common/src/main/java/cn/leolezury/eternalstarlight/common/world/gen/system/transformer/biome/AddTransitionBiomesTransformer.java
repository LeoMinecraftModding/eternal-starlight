package cn.leolezury.eternalstarlight.common.world.gen.system.transformer.biome;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESDataTransformerTypes;
import cn.leolezury.eternalstarlight.common.world.gen.system.BiomeData;
import cn.leolezury.eternalstarlight.common.world.gen.system.WorldGenProvider;
import cn.leolezury.eternalstarlight.common.world.gen.system.transformer.DataTransformerType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.List;
import java.util.Random;

public class AddTransitionBiomesTransformer extends NeighborsRelatedTransformer {
	public static final MapCodec<AddTransitionBiomesTransformer> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BiomeWithTransition.CODEC.listOf().fieldOf("transitions").forGetter(o -> o.transitions)
	).apply(instance, AddTransitionBiomesTransformer::new));

	private final List<BiomeWithTransition> transitions;
	private final Int2IntLinkedOpenHashMap idMap = new Int2IntLinkedOpenHashMap();

	public AddTransitionBiomesTransformer(List<BiomeWithTransition> transitions) {
		this.transitions = transitions;
	}

	@Override
	public int transform(WorldGenProvider provider, Random random, int original, int up, int down, int left, int right) {
		if (idMap.isEmpty()) {
			for (BiomeWithTransition transition : transitions) {
				idMap.put(provider.getBiomeDataId(transition.biome().value()), provider.getBiomeDataId(transition.transitionBiome().value()));
			}
		}
		for (int id : idMap.keySet()) {
			if (original == id && (up != id || down != id || left != id || right != id)) {
				return idMap.get(id);
			}
		}
		return original;
	}

	@Override
	public DataTransformerType<?> type() {
		return ESDataTransformerTypes.ADD_TRANSITIONS.get();
	}

	public record BiomeWithTransition(Holder<BiomeData> biome, Holder<BiomeData> transitionBiome) {
		public static final Codec<BiomeWithTransition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("biome").forGetter(BiomeWithTransition::biome),
			RegistryFileCodec.create(ESRegistries.BIOME_DATA, BiomeData.CODEC).fieldOf("transition_biome").forGetter(BiomeWithTransition::transitionBiome)
		).apply(instance, BiomeWithTransition::new));
	}
}
