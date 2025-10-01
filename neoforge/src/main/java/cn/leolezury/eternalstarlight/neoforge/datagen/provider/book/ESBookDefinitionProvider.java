package cn.leolezury.eternalstarlight.neoforge.datagen.provider.book;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.*;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import cn.leolezury.eternalstarlight.common.client.book.text.BookText;
import cn.leolezury.eternalstarlight.common.data.ESStructures;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.collect.Sets;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.joml.Quaternionf;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), EternalStarlight.id("starlight_golem_display"), Sets.newHashSet(EternalStarlight.id("starlight_golem")), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.CHISELED_GOLEM_STEEL_BLOCK.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), EternalStarlight.id("lunar_monstrosity_display"), Sets.newHashSet(EternalStarlight.id("lunar_monstrosity")), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.PARASOL_GRASS.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}))
				), 20, 20, 130, 12))
			),
			// seeking eye
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.SEEKING_EYE.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.SEEKING_EYE.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeking_eye"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), simpleColoredTranslatedBookContent("seeking_eye"), 10, 20, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeking_eye_locators"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), simpleColoredTranslatedBookContent("seeking_eye.locator_items"), 0, 0, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_locators_starlight_golem"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), 50)
					.textDisplay(new BookContent(List.of(new BookText(false, "${color: #acfffc}${link: " + EternalStarlight.ID + ":starlight_golem_display}"), new BookText(true, ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()))), true, 65, 35, 110, 9, 6, 1)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 5, 20, 20)
					.itemTagDisplay(ESTags.Items.GOLEM_FORGE_LOCATORS, 55 + 2, 5 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_locators_lunar_monstrosity"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), 50)
					.textDisplay(new BookContent(List.of(new BookText(false, "${color: #acfffc}${link: " + EternalStarlight.ID + ":lunar_monstrosity_display}"), new BookText(true, ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()))), true, 65, 35, 110, 9, 6, 1)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 5, 20, 20)
					.itemTagDisplay(ESTags.Items.CURSED_GARDEN_LOCATORS, 55 + 2, 5 + 2))
			),
			// orb of prophecy
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("orb_of_prophecy_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.ORB_OF_PROPHECY.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.ORB_OF_PROPHECY.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("orb_of_prophecy"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), simpleColoredTranslatedBookContent("orb_of_prophecy"), 10, 20, 130, 12))
			),
			// starlight golem
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("starlight_golem_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.STARLIGHT_GOLEM.getId().toString());
						return tag;
					}), 65, 80, -25, 210, 22, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("starlight_golem_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), ESEntities.STARLIGHT_GOLEM.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.GOLEM_FORGE.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 8, 8, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 8, 8, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("starlight_golem.defense"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 8, 8, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("starlight_golem.speed"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 8, 8, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 8, 8, 8, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("starlight_golem"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), simpleColoredTranslatedBookContent("starlight_golem"), 10, 20, 130, 12))
			),
			// lunar monstrosity
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("lunar_monstrosity_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.LUNAR_MONSTROSITY.getId().toString());
						return tag;
					}), 65, 80, -25, 210, 16, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("lunar_monstrosity_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), ESEntities.LUNAR_MONSTROSITY.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.CURSED_GARDEN.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 8, 8, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 8, 8, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("lunar_monstrosity.defense"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 8, 8, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("lunar_monstrosity.speed"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 8, 8, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 8, 8, 8, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("lunar_monstrosity"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("enter_starlight"))
				)), simpleColoredTranslatedBookContent("lunar_monstrosity"), 10, 20, 130, 12))
			)
		), 150, 187, 10,
			8, 8, 0, 172, 4,
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
