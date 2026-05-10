package cn.leolezury.eternalstarlight.neoforge.datagen.provider.book;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.*;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import cn.leolezury.eternalstarlight.common.client.book.text.BookText;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESStructures;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
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
			// index
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("cover"), new HashSet<>(), 180)
					.textDisplay(new BookContent(List.of(new BookText(false, "${color: #acfffc}${link: " + EternalStarlight.ID + ":index}"), new BookText(true, ESItems.BOOK.get().getDescriptionId()))), true, 65, 100, 110, 12, 8, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/icon.png"), 45, 30, 40, 40)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 33, 75, 64, 16)),
				new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("index"), new HashSet<>(), List.of(
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.SEEKING_EYE.get().getDescriptionId()), EternalStarlight.id("seeking_eye_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.SEEKING_EYE.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.ORB_OF_PROPHECY.get().getDescriptionId()), EternalStarlight.id("orb_of_prophecy_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.ORB_OF_PROPHECY.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslatedBookContent("accessories.title"), EternalStarlight.id("accessories_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.CRESCENT_PENDANT.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), EternalStarlight.id("starlight_golem_display"), Sets.newHashSet(EternalStarlight.id("freeze_display"), EternalStarlight.id("permafrost_display"), EternalStarlight.id("energy_transmitter_display"), EternalStarlight.id("accumulator_display")), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.CHISELED_GOLEM_STEEL_BLOCK.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), EternalStarlight.id("lunar_monstrosity_display"), Sets.newHashSet(EternalStarlight.id("tangled_display")), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.PARASOL_GRASS.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESWeathers.METEOR_SHOWER.get().getDescriptionId()), EternalStarlight.id("meteor_shower_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.AETHERSTRIKE_ROCKET.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.THIRST_WALKER.get().getDescriptionId()), EternalStarlight.id("thirst_walker_display"), Sets.newHashSet(EternalStarlight.id("dagger_of_hunger_display"), EternalStarlight.id("crystalborn_catalyst_display")), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.TOOTH_OF_HUNGER.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.STRANGHOUL.get().getDescriptionId()), EternalStarlight.id("stranghoul_display"), Sets.newHashSet(EternalStarlight.id("pungency_fruit_display"), EternalStarlight.id("silver_pungency_fruit_display")), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.MALARITE_SWORD.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.SHIMMER_LACEWING.get().getDescriptionId()), EternalStarlight.id("shimmer_lacewing_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.STARLIT_LILY_PAD.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.TWILIGHT_GAZE.get().getDescriptionId()), EternalStarlight.id("twilight_gaze_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.ABYSSAL_MAGMA_BLOCK.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.NOCTURNAL_MILLET.get().getDescriptionId()), EternalStarlight.id("nocturnal_millet_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.NOCTURNAL_MILLET.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}))
				), 20, 20, 125, 12))
			),
			// seeking eye
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.SEEKING_EYE.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.SEEKING_EYE.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeking_eye"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), simpleColoredTranslatedBookContent("seeking_eye"), 10, 20, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeking_eye_locators"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), simpleColoredTranslatedBookContent("seeking_eye.locator_items"), 0, 0, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_locators_starlight_golem"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 50)
					.textDisplay(new BookContent(List.of(new BookText(false, "${color: #acfffc}${link: " + EternalStarlight.ID + ":starlight_golem_display}"), new BookText(true, ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()))), true, 65, 35, 110, 9, 6, 1)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 5, 20, 20)
					.itemTagDisplay(ESTags.Items.GOLEM_FORGE_LOCATORS, 55 + 2, 5 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_locators_lunar_monstrosity"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 50)
					.textDisplay(new BookContent(List.of(new BookText(false, "${color: #acfffc}${link: " + EternalStarlight.ID + ":lunar_monstrosity_display}"), new BookText(true, ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()))), true, 65, 35, 110, 9, 6, 1)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 5, 20, 20)
					.itemTagDisplay(ESTags.Items.CURSED_GARDEN_LOCATORS, 55 + 2, 5 + 2))
			),
			// orb of prophecy
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("orb_of_prophecy_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.ORB_OF_PROPHECY.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.ORB_OF_PROPHECY.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("orb_of_prophecy"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), simpleColoredTranslatedBookContent("orb_of_prophecy"), 10, 20, 130, 12))
			),
			// accessories
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("accessories_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 60)
					.textDisplay(simpleColoredTranslatedBookContent("accessories.title"), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemTagDisplay(ESTags.Items.ACCESSORIES, 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("accessories"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), simpleColoredTranslatedBookContent("accessories"), 10, 0, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("butterfly_wings_amulet_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 30)
					.textDisplay(simpleColoredTranslatedBookContent("accessories.butterfly_wings_amulet"), false, 25, 10, 100, 12, 8, 1)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 0, 5, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.BUTTERFLY_WINGS_AMULET.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 2, 5 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("pearl_necklace_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 30)
					.textDisplay(simpleColoredTranslatedBookContent("accessories.pearl_necklace"), false, 25, 10, 100, 12, 8, 1)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 0, 5, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.PEARL_NECKLACE.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 2, 5 + 2))
			),
			// starlight golem
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("starlight_golem_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.STARLIGHT_GOLEM.getId().toString());
						return tag;
					}), 65, 80, -25, 210, 22, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("starlight_golem_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), ESEntities.STARLIGHT_GOLEM.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.GOLEM_FORGE.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("starlight_golem.defense"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("starlight_golem.speed"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("starlight_golem"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), simpleColoredTranslatedBookContent("starlight_golem"), 10, 5, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("starlight_golem_index"), new HashSet<>(), List.of(
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.FREEZE.get().getDescriptionId()), EternalStarlight.id("freeze_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.FROZEN_TUBE.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.PERMAFROST.get().getDescriptionId()), EternalStarlight.id("permafrost_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.COLDSNAP.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.ENERGY_TRANSMITTER.get().getDescriptionId()), EternalStarlight.id("energy_transmitter_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.ENERGY_TRANSMITTER.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.ACCUMULATOR.get().getDescriptionId()), EternalStarlight.id("accumulator_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.ACCUMULATOR.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}))
				), 0, 20, 125, 12))
			),
			// freeze
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("freeze_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_freeze"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.FREEZE.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.FREEZE.getId().toString());
						return tag;
					}), 65, 80, -25, 210, 40, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("freeze_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_freeze"))
				)), ESEntities.FREEZE.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.GOLEM_FORGE.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("freeze.attack"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ATTACK_DAMAGE.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_attack_damage.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.FLYING_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("freeze"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_freeze"))
				)), simpleColoredTranslatedBookContent("freeze"), 10, 20, 130, 12))
			),
			// permafrost
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("permafrost_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_permafrost"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.PERMAFROST.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.PERMAFROST.getId().toString());
						return tag;
					}), 65, 80, -25, 210, 22, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("permafrost_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_permafrost"))
				)), ESEntities.PERMAFROST.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.GOLEM_FORGE.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("freeze.attack"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ATTACK_DAMAGE.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_attack_damage.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.FLYING_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("permafrost"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_permafrost"))
				)), simpleColoredTranslatedBookContent("permafrost"), 10, 20, 130, 12))
			),
			// energy transmitter
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("energy_transmitter_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_ingot")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_nugget"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.ENERGY_TRANSMITTER.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.ENERGY_TRANSMITTER.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("energy_transmitter_recipe"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_ingot")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_nugget"))
				)), 90)
					.craftingRecipeDisplay(EternalStarlight.id("energy_transmitter"), 35, 10, 20, 20)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("energy_transmitter"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_ingot")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_nugget"))
				)), simpleColoredTranslatedBookContent("energy_transmitter"), 10, 20, 130, 12))
			),
			// accumulator
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("accumulator_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_ingot")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_nugget"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.ACCUMULATOR.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.ACCUMULATOR.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("accumulator_recipe"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_ingot")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_nugget"))
				)), 90)
					.craftingRecipeDisplay(EternalStarlight.id("accumulator"), 35, 10, 20, 20)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("accumulator"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_ingot")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_nugget"))
				)), simpleColoredTranslatedBookContent("accumulator"), 10, 20, 130, 12))
			),
			// lunar monstrosity
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("lunar_monstrosity_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.LUNAR_MONSTROSITY.getId().toString());
						return tag;
					}), 65, 80, -25, 210, 16, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("lunar_monstrosity_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), ESEntities.LUNAR_MONSTROSITY.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.CURSED_GARDEN.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("lunar_monstrosity.defense"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("lunar_monstrosity.speed"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("lunar_monstrosity"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), simpleColoredTranslatedBookContent("lunar_monstrosity"), 10, 5, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("lunar_monstrosity_index"), new HashSet<>(), List.of(
					new IndexBookComponent.Entry(simpleColoredTranslated(ESEntities.TANGLED.get().getDescriptionId()), EternalStarlight.id("tangled_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.TANGLED_SKULL.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}))
				), 0, 20, 125, 12))
			),
			// tangled
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("tangled_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_tangled"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.TANGLED.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.TANGLED.getId().toString());
						return tag;
					}), 65, 85, -25, 210, 30, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("tangled_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_tangled"))
				)), ESEntities.TANGLED.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.CURSED_GARDEN.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ATTACK_DAMAGE.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_attack_damage.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("tangled"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_tangled"))
				)), simpleColoredTranslatedBookContent("tangled"), 10, 20, 130, 12))
			),
			// meteor shower
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("meteor_shower_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESWeathers.METEOR_SHOWER.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.AETHERSTRIKE_ROCKET.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("meteor_shower"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))
				)), simpleColoredTranslatedBookContent("meteor_shower"), 10, 20, 130, 12))
			),
			// thirst walker
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("thirst_walker_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_thirst_walker"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.THIRST_WALKER.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.THIRST_WALKER.getId().toString());
						return tag;
					}), 65, 85, -25, 210, 25, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("thirst_walker_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_thirst_walker"))
				)), ESEntities.THIRST_WALKER.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("biome", ESBiomes.CRYSTALLIZED_DESERT.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_biome.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ATTACK_DAMAGE.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_attack_damage.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("thirst_walker"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_thirst_walker"))
				)), simpleColoredTranslatedBookContent("thirst_walker"), 10, 5, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("thirst_walker_index"), new HashSet<>(), List.of(
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.DAGGER_OF_HUNGER.get().getDescriptionId()), EternalStarlight.id("dagger_of_hunger_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.DAGGER_OF_HUNGER.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.CRYSTALBORN_CATALYST.get().getDescriptionId()), EternalStarlight.id("crystalborn_catalyst_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.CRYSTALBORN_CATALYST.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}))
				), 0, 20, 125, 12))
			),
			// dagger of hunger
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("dagger_of_hunger_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_killed_thirst_walker"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.DAGGER_OF_HUNGER.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.DAGGER_OF_HUNGER.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("dagger_of_hunger_recipe"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_killed_thirst_walker"))
				)), 90)
					.craftingRecipeDisplay(EternalStarlight.id("dagger_of_hunger"), 35, 10, 20, 20)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("dagger_of_hunger"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_killed_thirst_walker"))
				)), simpleColoredTranslatedBookContent("dagger_of_hunger"), 10, 20, 130, 12))
			),
			// crystalborn catalyst
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("crystalborn_catalyst_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_killed_thirst_walker"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.CRYSTALBORN_CATALYST.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.CRYSTALBORN_CATALYST.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("crystalborn_catalyst_recipe"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_killed_thirst_walker"))
				)), 90)
					.craftingRecipeDisplay(EternalStarlight.id("crystalborn_catalyst"), 35, 10, 20, 20)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("crystalborn_catalyst"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_killed_thirst_walker"))
				)), simpleColoredTranslatedBookContent("crystalborn_catalyst"), 10, 20, 130, 12))
			),
			// stranghoul
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("stranghoul_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.STRANGHOUL.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.STRANGHOUL.getId().toString());
						return tag;
					}), 65, 85, -25, 210, 30, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("stranghoul_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"))
				)), ESEntities.STRANGHOUL.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("biome", ESBiomes.DARK_SWAMP.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_biome.png")),
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.STRANGHOUL_DEN.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ATTACK_DAMAGE.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_attack_damage.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("stranghoul"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"))
				)), simpleColoredTranslatedBookContent("stranghoul"), 10, 5, 130, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("stranghoul_index"), new HashSet<>(), List.of(
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.PUNGENCY_FRUIT.get().getDescriptionId()), EternalStarlight.id("pungency_fruit_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.PUNGENCY_FRUIT.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.SILVER_PUNGENCY_FRUIT.get().getDescriptionId()), EternalStarlight.id("silver_pungency_fruit_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.SILVER_PUNGENCY_FRUIT.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.SEEDS_LAUNCHER.get().getDescriptionId()), EternalStarlight.id("seeds_launcher_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.SEEDS_LAUNCHER.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					})),
					new IndexBookComponent.Entry(simpleColoredTranslated(ESItems.DRYING_RACK.get().getDescriptionId()), EternalStarlight.id("drying_rack_display"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
						ItemStack stack = ESItems.DRYING_RACK.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}))
				), 0, 20, 125, 12))
			),
			// pungency fruit
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("pungency_fruit_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_pungency_fruit"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.PUNGENCY_FRUIT.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.PUNGENCY_FRUIT.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("pungency_fruit"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_pungency_fruit"))
				)), simpleColoredTranslatedBookContent("pungency_fruit"), 10, 20, 130, 12))
			),
			// silver pungency fruit
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("silver_pungency_fruit_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_pungency_fruit")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_silver_pungency_fruit"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.SILVER_PUNGENCY_FRUIT.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.SILVER_PUNGENCY_FRUIT.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("silver_pungency_fruit_recipe"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_pungency_fruit")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_silver_pungency_fruit"))
				)), 90)
					.craftingRecipeDisplay(EternalStarlight.id("silver_pungency_fruit"), 35, 10, 20, 20)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("silver_pungency_fruit"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_pungency_fruit")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_silver_pungency_fruit"))
				)), simpleColoredTranslatedBookContent("silver_pungency_fruit"), 10, 20, 130, 12))
			),
			// seeds launcher
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeds_launcher_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.SEEDS_LAUNCHER.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.SEEDS_LAUNCHER.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeds_launcher"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"))
				)), simpleColoredTranslatedBookContent("seeds_launcher"), 10, 20, 130, 12))
			),
			// drying rack
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("drying_rack_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.DRYING_RACK.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.DRYING_RACK.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("drying_rack"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"))
				)), simpleColoredTranslatedBookContent("drying_rack"), 10, 20, 130, 12))
			),
			// shimmer lacewing
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("shimmer_lacewing_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_shimmer_lacewing"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.SHIMMER_LACEWING.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.SHIMMER_LACEWING.getId().toString());
						return tag;
					}), 65, 75, -25, 210, 40, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("shimmer_lacewing_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_shimmer_lacewing"))
				)), ESEntities.SHIMMER_LACEWING.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("biome", ESBiomes.SHIMMER_RIVER.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_biome.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.FLYING_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("shimmer_lacewing"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_shimmer_lacewing"))
				)), simpleColoredTranslatedBookContent("shimmer_lacewing"), 10, 20, 130, 12))
			),
			// twilight gaze
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("twilight_gaze_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_twilight_gaze"))
				)), 125)
					.textDisplay(simpleColoredTranslated(ESEntities.TWILIGHT_GAZE.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
					.entityDisplay(Util.make(() -> {
						CompoundTag tag = new CompoundTag();
						tag.putString(Entity.ID_TAG, ESEntities.TWILIGHT_GAZE.getId().toString());
						return tag;
					}), 65, 70, -25, 210, 40, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))),
				new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("twilight_gaze_info"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_twilight_gaze"))
				)), ESEntities.TWILIGHT_GAZE.getId(), List.of(
					new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("biome", ESBiomes.THE_ABYSS.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_biome.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ATTACK_DAMAGE.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_attack_damage.png")),
					new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
				), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("twilight_gaze"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_twilight_gaze"))
				)), simpleColoredTranslatedBookContent("twilight_gaze"), 10, 20, 130, 12))
			),
			// nocturnal millet
			List.of(
				new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("nocturnal_millet_display"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_nocturnal_millet_seeds")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_nocturnal_millet")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_forgotten_nocturnal_millet"))
				)), 60)
					.textDisplay(simpleColoredTranslated(ESItems.NOCTURNAL_MILLET.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
					.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
					.itemDisplay(Util.make(() -> {
						ItemStack stack = ESItems.NOCTURNAL_MILLET.get().getDefaultInstance();
						return (CompoundTag) stack.save(provider);
					}), 55 + 2, 10 + 2)),
				new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("nocturnal_millet"), new HashSet<>(Set.of(
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_nocturnal_millet_seeds")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_nocturnal_millet")),
					Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_forgotten_nocturnal_millet"))
				)), simpleColoredTranslatedBookContent("nocturnal_millet"), 10, 20, 130, 12))
			)
		), 150, 187, 10,
			new BookDefinition.Buttons(
				8, 8, 0, 172, 4, 14, 7, 10, 30, 178
			),
			new BookDefinition.Scrollbar(
				4, 170, 140, 5, 2, FastColor.ARGB32.color(172, 255, 252)
			),
			new BookDefinition.Textures(
				EternalStarlight.id("textures/gui/screen/book/background.png"),
				EternalStarlight.id("textures/gui/screen/book/overlay.png"),
				EternalStarlight.id("textures/gui/screen/book/top_overlay.png"),
				EternalStarlight.id("textures/gui/screen/book/up.png"),
				EternalStarlight.id("textures/gui/screen/book/down.png"),
				EternalStarlight.id("textures/gui/screen/book/left_history.png"),
				EternalStarlight.id("textures/gui/screen/book/right_history.png")
			)
		);
		add(EternalStarlight.id("main"), main);
	}

	private static BookContent simpleColoredTranslatedBookContent(String id) {
		return new BookContent(List.of(new BookText(false, "${color: #acfffc}"), new BookText(true, "book." + EternalStarlight.ID + "." + id)));
	}

	private static BookContent simpleColoredTranslated(String id) {
		return new BookContent(List.of(new BookText(false, "${color: #acfffc}"), new BookText(true, id)));
	}
}
