package cn.leolezury.eternalstarlight.common.client.book;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.DisplayBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.IndexBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.TextBookComponent;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class ESGuideBookProvider {
	public static Book getBook(Set<ResourceLocation> unlocked) {
		DisplayBookComponent title = new DisplayBookComponent(105, 130)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESItems.BOOK.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent preface = new TextBookComponent(translatedBookText("preface"), false, 105, 125);

		IndexBookComponent index = buildIndex(unlocked);

		TextBookComponent mainStory = new TextBookComponent(translatedBookText("main_story"), 105, 125);

		DisplayBookComponent seekingEyeDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESItems.SEEKING_EYE.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.SEEKING_EYE.get().getDefaultInstance(), 44, 82);

		TextBookComponent seekingEye = new TextBookComponent(translatedBookText("seeking_eye"), 105, 125);

		DisplayBookComponent orbDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESItems.ORB_OF_PROPHECY.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.ORB_OF_PROPHECY.get().getDefaultInstance(), 44, 82);

		TextBookComponent orb = new TextBookComponent(translatedBookText("orb_of_prophecy"), 105, 125);

		DisplayBookComponent golemDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.STARLIGHT_GOLEM.get(), 52, 75, -25, 210, 20, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), 52, 115, 1.2f);

		DisplayBookComponent golemLocator = new DisplayBookComponent(105, 130)
			.textDisplay(translatedBookText("locators"), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemTagDisplay(ESTags.Items.GOLEM_FORGE_LOCATORS, 44, 82);

		TextBookComponent golem = new TextBookComponent(translatedBookText("starlight_golem"), 105, 125);

		TextBookComponent golemSeen = new TextBookComponent(translatedBookText("starlight_golem_seen"), 105, 125);

		DisplayBookComponent freezeDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.FREEZE.get(), 52, 80, -25, 210, 40, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.FREEZE.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent freeze = new TextBookComponent(translatedBookText("freeze"), 105, 125);

		DisplayBookComponent lunarMonstrosityDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.LUNAR_MONSTROSITY.get(), 52, 80, -25, 210, 16, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), 52, 115, 1.1f);

		DisplayBookComponent lunarMonstrosityLocator = new DisplayBookComponent(105, 130)
			.textDisplay(translatedBookText("locators"), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemTagDisplay(ESTags.Items.CURSED_GARDEN_LOCATORS, 44, 82);

		TextBookComponent lunarMonstrosity = new TextBookComponent(translatedBookText("lunar_monstrosity"), 105, 125);

		TextBookComponent lunarMonstrositySeen = new TextBookComponent(translatedBookText("lunar_monstrosity_seen"), 105, 125);

		DisplayBookComponent tangledHatredDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESEntities.TANGLED_HATRED.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.TENACIOUS_VINE.get().getDefaultInstance(), 44, 82);

		TextBookComponent tangledHatred = new TextBookComponent(translatedBookText("tangled_hatred"), 105, 125);

		DisplayBookComponent tangledDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.TANGLED.get(), 52, 80, -25, 210, 30, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.TANGLED.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent tangled = new TextBookComponent(translatedBookText("tangled"), 105, 125);

		DisplayBookComponent meteorShowerDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESWeathers.METEOR_SHOWER.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.RAW_AETHERSENT.get().getDefaultInstance(), 44, 82);

		TextBookComponent meteorShower = new TextBookComponent(translatedBookText("meteor_shower"), 105, 125);

		DisplayBookComponent thirstWalkerDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.THIRST_WALKER.get(), 52, 80, 0, 210, 25, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.THIRST_WALKER.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent thirstWalker = new TextBookComponent(translatedBookText("thirst_walker"), 105, 125);

		DisplayBookComponent daggerOfHungerDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESItems.DAGGER_OF_HUNGER.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.DAGGER_OF_HUNGER.get().getDefaultInstance(), 44, 82);

		TextBookComponent daggerOfHunger = new TextBookComponent(translatedBookText("dagger_of_hunger"), 105, 125);

		DisplayBookComponent crystalbornCatalystDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESItems.CRYSTALBORN_CATALYST.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.CRYSTALBORN_CATALYST.get().getDefaultInstance(), 44, 82);

		TextBookComponent crystalbornCatalyst = new TextBookComponent(translatedBookText("crystalborn_catalyst"), 105, 125);

		DisplayBookComponent stranghoulDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.STRANGHOUL.get(), 52, 80, -25, 210, 30, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.STRANGHOUL.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent stranghoul = new TextBookComponent(translatedBookText("stranghoul"), 105, 125);

		DisplayBookComponent pungencyFruitDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESItems.PUNGENCY_FRUIT.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.PUNGENCY_FRUIT.get().getDefaultInstance(), 44, 82);

		TextBookComponent pungencyFruit = new TextBookComponent(translatedBookText("pungency_fruit"), 105, 125);

		DisplayBookComponent silverPungencyFruitDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESItems.SILVER_PUNGENCY_FRUIT.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.SILVER_PUNGENCY_FRUIT.get().getDefaultInstance(), 44, 82);

		TextBookComponent silverPungencyFruit = new TextBookComponent(translatedBookText("silver_pungency_fruit"), 105, 125);

		DisplayBookComponent seedsLauncherDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESItems.SEEDS_LAUNCHER.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.SEEDS_LAUNCHER.get().getDefaultInstance(), 44, 82);

		TextBookComponent seedsLauncher = new TextBookComponent(translatedBookText("seeds_launcher"), 105, 125);

		DisplayBookComponent dryingRackDisplay = new DisplayBookComponent(105, 130)
			.textDisplay(Component.translatable(ESItems.DRYING_RACK.get().getDescriptionId()), 52, 28, 1)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 45, 96, 11)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 36, 74, 32, 32)
			.itemDisplay(ESItems.DRYING_RACK.get().getDefaultInstance(), 44, 82);

		TextBookComponent dryingRack = new TextBookComponent(translatedBookText("drying_rack"), 105, 125);

		DisplayBookComponent twilightGazeDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.TWILIGHT_GAZE.get(), 52, 60, 0, 210, 25, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.TWILIGHT_GAZE.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent twilightGaze = new TextBookComponent(translatedBookText("twilight_gaze"), 105, 125);

		DisplayBookComponent shimmerLacewingDisplay = new DisplayBookComponent(105, 130)
			.entityDisplay(ESEntities.SHIMMER_LACEWING.get(), 52, 65, 0, 210, 25, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
			.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
			.textDisplay(Component.translatable(ESEntities.SHIMMER_LACEWING.get().getDescriptionId()), 52, 115, 1.2f);

		TextBookComponent shimmerLacewing = new TextBookComponent(translatedBookText("shimmer_lacewing"), 105, 125);

		return new Book(Lists.newArrayList(
			new BookComponentDefinition(title, EternalStarlight.id("title"), 11, 6, 5, 6),
			new BookComponentDefinition(preface, EternalStarlight.id("preface"), 11, 12, 5, 12),
			new BookComponentDefinition(index, EternalStarlight.id("index"), 11, 12, 5, 12),
			new BookComponentDefinition(mainStory, EternalStarlight.id("main_story"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(seekingEyeDisplay, EternalStarlight.id("seeking_eye_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(seekingEye, EternalStarlight.id("seeking_eye"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(orbDisplay, EternalStarlight.id("orb_of_prophecy_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(orb, EternalStarlight.id("orb_of_prophecy"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(golemDisplay, EternalStarlight.id("starlight_golem_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(golemLocator, EternalStarlight.id("starlight_golem_locator"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(golem, EternalStarlight.id("starlight_golem"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(golemSeen, EternalStarlight.id("starlight_golem_seen"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight")) && unlocked.contains(EternalStarlight.id("starlight_golem_seen"))),
			new BookComponentDefinition(freezeDisplay, EternalStarlight.id("freeze_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight")) && unlocked.contains(EternalStarlight.id("freeze"))),
			new BookComponentDefinition(freeze, EternalStarlight.id("freeze"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight")) && unlocked.contains(EternalStarlight.id("freeze"))),
			new BookComponentDefinition(lunarMonstrosityDisplay, EternalStarlight.id("lunar_monstrosity_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(lunarMonstrosityLocator, EternalStarlight.id("lunar_monstrosity_locator"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(lunarMonstrosity, EternalStarlight.id("lunar_monstrosity"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(lunarMonstrositySeen, EternalStarlight.id("lunar_monstrosity_seen"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight")) && unlocked.contains(EternalStarlight.id("lunar_monstrosity_seen"))),
			new BookComponentDefinition(tangledHatredDisplay, EternalStarlight.id("tangled_hatred_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight")) && unlocked.contains(EternalStarlight.id("tangled_hatred"))),
			new BookComponentDefinition(tangledHatred, EternalStarlight.id("tangled_hatred"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight")) && unlocked.contains(EternalStarlight.id("tangled_hatred"))),
			new BookComponentDefinition(tangledDisplay, EternalStarlight.id("tangled_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight")) && unlocked.contains(EternalStarlight.id("tangled"))),
			new BookComponentDefinition(tangled, EternalStarlight.id("tangled"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight")) && unlocked.contains(EternalStarlight.id("tangled"))),
			new BookComponentDefinition(meteorShowerDisplay, EternalStarlight.id("meteor_shower_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(meteorShower, EternalStarlight.id("meteor_shower"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new BookComponentDefinition(thirstWalkerDisplay, EternalStarlight.id("thirst_walker_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new BookComponentDefinition(thirstWalker, EternalStarlight.id("thirst_walker"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new BookComponentDefinition(daggerOfHungerDisplay, EternalStarlight.id("dagger_of_hunger_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new BookComponentDefinition(daggerOfHunger, EternalStarlight.id("dagger_of_hunger"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new BookComponentDefinition(crystalbornCatalystDisplay, EternalStarlight.id("crystalborn_catalyst_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new BookComponentDefinition(crystalbornCatalyst, EternalStarlight.id("crystalborn_catalyst"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new BookComponentDefinition(stranghoulDisplay, EternalStarlight.id("stranghoul_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("stranghoul"))),
			new BookComponentDefinition(stranghoul, EternalStarlight.id("stranghoul"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("stranghoul"))),
			new BookComponentDefinition(pungencyFruitDisplay, EternalStarlight.id("pungency_fruit_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("stranghoul")) || unlocked.contains(EternalStarlight.id("pungency_fruit"))),
			new BookComponentDefinition(pungencyFruit, EternalStarlight.id("pungency_fruit"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("stranghoul")) || unlocked.contains(EternalStarlight.id("pungency_fruit"))),
			new BookComponentDefinition(silverPungencyFruitDisplay, EternalStarlight.id("silver_pungency_fruit_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("stranghoul")) || unlocked.contains(EternalStarlight.id("pungency_fruit"))),
			new BookComponentDefinition(silverPungencyFruit, EternalStarlight.id("silver_pungency_fruit"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("stranghoul")) || unlocked.contains(EternalStarlight.id("pungency_fruit"))),
			new BookComponentDefinition(seedsLauncherDisplay, EternalStarlight.id("seeds_launcher_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("stranghoul"))),
			new BookComponentDefinition(seedsLauncher, EternalStarlight.id("seeds_launcher"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("stranghoul"))),
			new BookComponentDefinition(dryingRackDisplay, EternalStarlight.id("drying_rack_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("drying_rack"))),
			new BookComponentDefinition(dryingRack, EternalStarlight.id("drying_rack"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("drying_rack"))),
			new BookComponentDefinition(twilightGazeDisplay, EternalStarlight.id("twilight_gaze_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("twilight_gaze"))),
			new BookComponentDefinition(twilightGaze, EternalStarlight.id("twilight_gaze"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("twilight_gaze"))),
			new BookComponentDefinition(shimmerLacewingDisplay, EternalStarlight.id("shimmer_lacewing_display"), 11, 6, 5, 6, unlocked.contains(EternalStarlight.id("shimmer_lacewing"))),
			new BookComponentDefinition(shimmerLacewing, EternalStarlight.id("shimmer_lacewing"), 11, 12, 5, 12, unlocked.contains(EternalStarlight.id("shimmer_lacewing")))
		), 240, 165, 18, 7, 12, 27,
			EternalStarlight.id("textures/gui/screen/book/book.png"),
			EternalStarlight.id("textures/gui/screen/book/book_cover.png"),
			EternalStarlight.id("textures/gui/screen/book/book_back_cover.png"),
			EternalStarlight.id("textures/gui/screen/book/book_flip_left.png"),
			EternalStarlight.id("textures/gui/screen/book/book_flip_right.png"));
	}

	public static IndexBookComponent buildIndex(Set<ResourceLocation> unlocked) {
		return new IndexBookComponent(translatedBookText("index"), List.of(
			new IndexBookComponent.IndexItem(translatedBookText("main_story_title"), EternalStarlight.id("main_story"), unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESItems.SEEKING_EYE.get().getDescriptionId()), EternalStarlight.id("seeking_eye_display"), unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESItems.ORB_OF_PROPHECY.get().getDescriptionId()), EternalStarlight.id("orb_of_prophecy_display"), unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), EternalStarlight.id("starlight_golem_display"), unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.FREEZE.get().getDescriptionId()), EternalStarlight.id("freeze_display"), unlocked.contains(EternalStarlight.id("freeze"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), EternalStarlight.id("lunar_monstrosity_display"), unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.TANGLED_HATRED.get().getDescriptionId()), EternalStarlight.id("tangled_hatred_display"), unlocked.contains(EternalStarlight.id("tangled_hatred"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.TANGLED.get().getDescriptionId()), EternalStarlight.id("tangled_display"), unlocked.contains(EternalStarlight.id("tangled"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESWeathers.METEOR_SHOWER.get().getDescriptionId()), EternalStarlight.id("meteor_shower_display"), unlocked.contains(EternalStarlight.id("enter_starlight"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.THIRST_WALKER.get().getDescriptionId()), EternalStarlight.id("thirst_walker_display"), unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESItems.DAGGER_OF_HUNGER.get().getDescriptionId()), EternalStarlight.id("dagger_of_hunger_display"), unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESItems.CRYSTALBORN_CATALYST.get().getDescriptionId()), EternalStarlight.id("crystalborn_catalyst_display"), unlocked.contains(EternalStarlight.id("thirst_walker"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.STRANGHOUL.get().getDescriptionId()), EternalStarlight.id("stranghoul_display"), unlocked.contains(EternalStarlight.id("stranghoul"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESItems.PUNGENCY_FRUIT.get().getDescriptionId()), EternalStarlight.id("pungency_fruit_display"), unlocked.contains(EternalStarlight.id("stranghoul")) || unlocked.contains(EternalStarlight.id("pungency_fruit"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESItems.SILVER_PUNGENCY_FRUIT.get().getDescriptionId()), EternalStarlight.id("silver_pungency_fruit_display"), unlocked.contains(EternalStarlight.id("stranghoul")) || unlocked.contains(EternalStarlight.id("pungency_fruit"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESItems.SEEDS_LAUNCHER.get().getDescriptionId()), EternalStarlight.id("seeds_launcher_display"), unlocked.contains(EternalStarlight.id("stranghoul"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESItems.DRYING_RACK.get().getDescriptionId()), EternalStarlight.id("drying_rack_display"), unlocked.contains(EternalStarlight.id("drying_rack"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.TWILIGHT_GAZE.get().getDescriptionId()), EternalStarlight.id("twilight_gaze_display"), unlocked.contains(EternalStarlight.id("twilight_gaze"))),
			new IndexBookComponent.IndexItem(Component.translatable(ESEntities.SHIMMER_LACEWING.get().getDescriptionId()), EternalStarlight.id("shimmer_lacewing_display"), unlocked.contains(EternalStarlight.id("shimmer_lacewing")))
		), 105, 125);
	}

	public static Component translatedBookText(String id) {
		return Component.translatable("book." + EternalStarlight.ID + "." + id);
	}
}
