package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public record AlloyFurnaceCoolant(HolderSet<Item> items, int duration, int efficiency) {
	public static final Codec<AlloyFurnaceCoolant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("items").forGetter(AlloyFurnaceCoolant::items),
		Codec.intRange(1, Integer.MAX_VALUE).fieldOf("duration").forGetter(AlloyFurnaceCoolant::duration),
		Codec.intRange(1, Integer.MAX_VALUE).fieldOf("efficiency").forGetter(AlloyFurnaceCoolant::efficiency)
	).apply(instance, AlloyFurnaceCoolant::new));

	@Nullable
	public static AlloyFurnaceCoolant getCoolant(HolderLookup.Provider registries, Item item) {
		return registries.lookupOrThrow(ESRegistries.ALLOY_FURNACE_COOLANT).listElements()
			.map(Holder.Reference::value)
			.filter(coolant -> coolant.items().contains(item.builtInRegistryHolder()))
			.findFirst().orElse(null);
	}

	public static Map<Item, AlloyFurnaceCoolant> getCoolantMap(HolderLookup.Provider registries) {
		Map<Item, AlloyFurnaceCoolant> map = new HashMap<>();
		registries.lookupOrThrow(ESRegistries.ALLOY_FURNACE_COOLANT).listElements().forEach(reference -> {
			AlloyFurnaceCoolant coolant = reference.value();
			for (Holder<Item> item : coolant.items()) {
				map.put(item.value(), coolant);
			}
		});
		return map;
	}
}
