package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.Comparator;
import java.util.function.Predicate;

public class ESCreativeModeTabs {
	public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, EternalStarlight.ID);
	public static final RegistryObject<CreativeModeTab, CreativeModeTab> ETERNAL_STARLIGHT = TABS.register("eternal_starlight", ESPlatform.INSTANCE::getESTab);

	private static final Comparator<Holder<PaintingVariant>> PAINTING_COMPARATOR = Comparator.comparing(Holder::value, Comparator.comparingInt(PaintingVariant::area).thenComparing(PaintingVariant::width));

	public static void generatePresetPaintings(CreativeModeTab.Output output, HolderLookup.Provider provider, HolderLookup.RegistryLookup<PaintingVariant> registryLookup, Predicate<Holder<PaintingVariant>> predicate) {
		RegistryOps<Tag> registryOps = provider.createSerializationContext(NbtOps.INSTANCE);
		registryLookup.listElements().filter(predicate).sorted(PAINTING_COMPARATOR).forEach((reference) -> {
			CustomData customData = CustomData.EMPTY.update(registryOps, Painting.VARIANT_MAP_CODEC, reference).getOrThrow().update((compoundTag) -> {
				compoundTag.putString("id", "minecraft:painting");
			});
			ItemStack itemStack = new ItemStack(ESItems.STARLIT_PAINTING.get());
			itemStack.set(DataComponents.ENTITY_DATA, customData);
			output.accept(itemStack);
		});
	}

	public static void loadClass() {
	}
}
