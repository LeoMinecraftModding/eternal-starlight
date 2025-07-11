package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ConventionalTags {
	public static class Items {
		public static final TagKey<Item> MUSIC_DISCS = create("music_discs");
		public static final TagKey<Item> ENDER_PEARLS = create("ender_pearls");

		private static TagKey<Item> create(String string) {
			return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", string));
		}
	}
}
