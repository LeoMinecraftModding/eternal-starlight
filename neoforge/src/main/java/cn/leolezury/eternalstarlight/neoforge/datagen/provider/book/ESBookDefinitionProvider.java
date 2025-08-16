package cn.leolezury.eternalstarlight.neoforge.datagen.provider.book;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.*;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import cn.leolezury.eternalstarlight.common.client.book.text.BookText;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.google.common.collect.Sets;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ESBookDefinitionProvider extends BookDefinitionProvider {
	public ESBookDefinitionProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void gather(HolderLookup.Provider provider) {
		BookDefinition main = new BookDefinition(List.of(
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("index"), new HashSet<>(), List.of(
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.SEEKING_EYE.get().getDescriptionId()), EternalStarlight.id("seeking_eye_display"), Sets.newHashSet(EternalStarlight.id("seeking_eye")), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.SEEKING_EYE.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.ORB_OF_PROPHECY.get().getDescriptionId()), EternalStarlight.id("orb_of_prophecy_display"), Sets.newHashSet(EternalStarlight.id("orb_of_prophecy")), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.ORB_OF_PROPHECY.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}))
				), 20, 20, 130, 12))
			),
			// seeking eye
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), 60)
					.textDisplay(ESItems.SEEKING_EYE.get().getDescription().copy().withColor(0xacfffc), 65, 55, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.SEEKING_EYE.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeking_eye"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), simpleColoredTranslatedBookContent("seeking_eye"), 10, 20, 130, 12))
			),
			// orb of prophecy
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("orb_of_prophecy_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), 60)
					.textDisplay(ESItems.ORB_OF_PROPHECY.get().getDescription().copy().withColor(0xacfffc), 65, 55, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.ORB_OF_PROPHECY.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("orb_of_prophecy"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), simpleColoredTranslatedBookContent("orb_of_prophecy"), 10, 20, 130, 12))
			)
		), 150, 187, 10,
			4, 4, 2, 174, 6,
			4, 170, 140, 5, 2, FastColor.ARGB32.color(172, 255, 252),
			new BookDefinition.Textures(EternalStarlight.id("textures/gui/screen/book/book.png"),
				EternalStarlight.id("textures/gui/screen/book/book_overlay.png"),
				EternalStarlight.id("textures/gui/screen/book/up.png"),
				EternalStarlight.id("textures/gui/screen/book/down.png")));
		add(EternalStarlight.id("main"), main);
	}

	private static BookContent simpleTranslatedBookContent(String id) {
		return new BookContent(List.of(new BookText(true, "book." + EternalStarlight.ID + "." + id)));
	}

	private static BookContent simpleColoredTranslatedBookContent(String id) {
		return new BookContent(List.of(new BookText(false, "${color: #acfffc}"), new BookText(true, "book." + EternalStarlight.ID + "." + id)));
	}

	private static BookContent simpleColoredTranslated(String id) {
		return new BookContent(List.of(new BookText(false, "${color: #acfffc}"), new BookText(true, id)));
	}
}
