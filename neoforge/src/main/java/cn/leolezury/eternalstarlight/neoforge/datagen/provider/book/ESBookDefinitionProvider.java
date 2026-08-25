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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.joml.Quaternionf;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ESBookDefinitionProvider extends BookDefinitionProvider {
	private static final HashSet<ResourceLocation> ITEMS_LISTENING_IDS = Sets.newHashSet(
		EternalStarlight.id("seeking_eye_display"),
		EternalStarlight.id("orb_of_prophecy_display"),
		EternalStarlight.id("accessories_display"),
		EternalStarlight.id("nocturnal_millet_display"),
		EternalStarlight.id("amaramber_display")
	);

	private static final HashSet<ResourceLocation> MOBS_LISTENING_IDS = Sets.newHashSet(
		EternalStarlight.id("gleech_display"),
		EternalStarlight.id("lonestar_skeleton_display"),
		EternalStarlight.id("nightfall_spider_display"),
		EternalStarlight.id("seeker_display"),
		EternalStarlight.id("thirst_walker_display"),
		EternalStarlight.id("creteor_display"),
		EternalStarlight.id("tiny_creteor_display"),
		EternalStarlight.id("stranghoul_display"),
		EternalStarlight.id("ent_display"),
		EternalStarlight.id("ratlin_display"),
		EternalStarlight.id("zombified_ratlin_display"),
		EternalStarlight.id("shadow_snail_display"),
		EternalStarlight.id("yeti_display"),
		EternalStarlight.id("aurora_deer_display"),
		EternalStarlight.id("crystallized_moth_display"),
		EternalStarlight.id("shimmer_lacewing_display"),
		EternalStarlight.id("starfire_bird_display"),
		EternalStarlight.id("grimstone_golem_display"),
		EternalStarlight.id("aethersent_golem_display"),
		EternalStarlight.id("rookfish_display"),
		EternalStarlight.id("luminofish_display"),
		EternalStarlight.id("luminaris_display"),
		EternalStarlight.id("twilight_gaze_display")
	);

	private static final HashSet<ResourceLocation> BOSSES_LISTENING_IDS = Sets.newHashSet(
		EternalStarlight.id("the_gatekeeper_display"),
		EternalStarlight.id("starlight_golem_display"),
		EternalStarlight.id("lunar_monstrosity_display")
	);

	private static final HashSet<ResourceLocation> MISC_LISTENING_IDS = Sets.newHashSet(
		EternalStarlight.id("meteor_shower_display")
	);

	public ESBookDefinitionProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void gather(HolderLookup.Provider provider) {
		BookDefinition main = new BookDefinition(List.of(
			EternalStarlight.id("main_index"),
			EternalStarlight.id("items"),
			EternalStarlight.id("seeking_eye"),
			EternalStarlight.id("orb_of_prophecy"),
			EternalStarlight.id("accessories"),
			EternalStarlight.id("nocturnal_millet"),
			EternalStarlight.id("amaramber"),
			EternalStarlight.id("mobs"),
			EternalStarlight.id("bosses"),
			EternalStarlight.id("gleech"),
			EternalStarlight.id("lonestar_skeleton"),
			EternalStarlight.id("nightfall_spider"),
			EternalStarlight.id("seeker"),
			EternalStarlight.id("thirst_walker"),
			EternalStarlight.id("dagger_of_hunger"),
			EternalStarlight.id("crystalborn_catalyst"),
			EternalStarlight.id("creteor"),
			EternalStarlight.id("tiny_creteor"),
			EternalStarlight.id("stranghoul"),
			EternalStarlight.id("pungency_fruit"),
			EternalStarlight.id("silver_pungency_fruit"),
			EternalStarlight.id("seeds_launcher"),
			EternalStarlight.id("drying_rack"),
			EternalStarlight.id("ent"),
			EternalStarlight.id("ratlin"),
			EternalStarlight.id("zombified_ratlin"),
			EternalStarlight.id("shadow_snail"),
			EternalStarlight.id("yeti"),
			EternalStarlight.id("aurora_deer"),
			EternalStarlight.id("crystallized_moth"),
			EternalStarlight.id("shimmer_lacewing"),
			EternalStarlight.id("starfire_bird"),
			EternalStarlight.id("grimstone_golem"),
			EternalStarlight.id("aethersent_golem"),
			EternalStarlight.id("rookfish"),
			EternalStarlight.id("luminofish"),
			EternalStarlight.id("luminaris"),
			EternalStarlight.id("twilight_gaze"),
			EternalStarlight.id("the_gatekeeper"),
			EternalStarlight.id("starlight_golem"),
			EternalStarlight.id("freeze"),
			EternalStarlight.id("permafrost"),
			EternalStarlight.id("energy_transmitter"),
			EternalStarlight.id("accumulator"),
			EternalStarlight.id("lunar_monstrosity"),
			EternalStarlight.id("tangled"),
			EternalStarlight.id("tangled_skull"),
			EternalStarlight.id("misc"),
			EternalStarlight.id("meteor_shower")
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
		addSection(EternalStarlight.id("main_index"), mainIndexSection(provider));
		addSection(EternalStarlight.id("items"), itemsSection(provider));
		addSection(EternalStarlight.id("seeking_eye"), seekingEyeSection(provider));
		addSection(EternalStarlight.id("orb_of_prophecy"), orbOfProphecySection(provider));
		addSection(EternalStarlight.id("accessories"), accessoriesSection(provider));
		addSection(EternalStarlight.id("nocturnal_millet"), nocturnalMilletSection(provider));
		addSection(EternalStarlight.id("amaramber"), amaramberSection(provider));
		addSection(EternalStarlight.id("mobs"), mobsSection(provider));
		addSection(EternalStarlight.id("bosses"), bossesSection(provider));
		addSection(EternalStarlight.id("gleech"), gleechSection());
		addSection(EternalStarlight.id("lonestar_skeleton"), lonestarSkeletonSection());
		addSection(EternalStarlight.id("nightfall_spider"), nightfallSpiderSection());
		addSection(EternalStarlight.id("seeker"), seekerSection());
		addSection(EternalStarlight.id("thirst_walker"), thirstWalkerSection(provider));
		addSection(EternalStarlight.id("dagger_of_hunger"), daggerOfHungerSection(provider));
		addSection(EternalStarlight.id("crystalborn_catalyst"), crystalbornCatalystSection(provider));
		addSection(EternalStarlight.id("creteor"), creteorSection());
		addSection(EternalStarlight.id("tiny_creteor"), tinyCreteorSection());
		addSection(EternalStarlight.id("stranghoul"), stranghoulSection(provider));
		addSection(EternalStarlight.id("pungency_fruit"), pungencyFruitSection(provider));
		addSection(EternalStarlight.id("silver_pungency_fruit"), silverPungencyFruitSection(provider));
		addSection(EternalStarlight.id("seeds_launcher"), seedsLauncherSection(provider));
		addSection(EternalStarlight.id("drying_rack"), dryingRackSection(provider));
		addSection(EternalStarlight.id("ent"), entSection());
		addSection(EternalStarlight.id("ratlin"), ratlinSection());
		addSection(EternalStarlight.id("zombified_ratlin"), zombifiedRatlinSection());
		addSection(EternalStarlight.id("shadow_snail"), shadowSnailSection());
		addSection(EternalStarlight.id("yeti"), yetiSection());
		addSection(EternalStarlight.id("aurora_deer"), auroraDeerSection());
		addSection(EternalStarlight.id("crystallized_moth"), crystallizedMothSection());
		addSection(EternalStarlight.id("shimmer_lacewing"), shimmerLacewingSection());
		addSection(EternalStarlight.id("starfire_bird"), starfireBirdSection());
		addSection(EternalStarlight.id("grimstone_golem"), grimstoneGolemSection());
		addSection(EternalStarlight.id("aethersent_golem"), aethersentGolemSection());
		addSection(EternalStarlight.id("rookfish"), rookfishSection());
		addSection(EternalStarlight.id("luminofish"), luminofishSection());
		addSection(EternalStarlight.id("luminaris"), luminarisSection());
		addSection(EternalStarlight.id("twilight_gaze"), twilightGazeSection());
		addSection(EternalStarlight.id("the_gatekeeper"), theGatekeeperSection());
		addSection(EternalStarlight.id("starlight_golem"), starlightGolemSection(provider));
		addSection(EternalStarlight.id("freeze"), freezeSection());
		addSection(EternalStarlight.id("permafrost"), permafrostSection());
		addSection(EternalStarlight.id("energy_transmitter"), energyTransmitterSection(provider));
		addSection(EternalStarlight.id("accumulator"), accumulatorSection(provider));
		addSection(EternalStarlight.id("lunar_monstrosity"), lunarMonstrositySection(provider));
		addSection(EternalStarlight.id("tangled"), tangledSection());
		addSection(EternalStarlight.id("tangled_skull"), tangledSkullSection());
		addSection(EternalStarlight.id("misc"), miscSection(provider));
		addSection(EternalStarlight.id("meteor_shower"), meteorShowerSection(provider));
	}

	private static List<ConfiguredBookComponent<?, ?>> mainIndexSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("cover"), new HashSet<>(), 150)
				.textDisplay(new BookContent(List.of(new BookText(false, "${color: #acfffc}${link: " + EternalStarlight.ID + ":index}"), new BookText(true, ESItems.BOOK.get().getDescriptionId()))), true, 65, 100, 110, 12, 8, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/icon.png"), 45, 30, 40, 40)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/separator.png"), 33, 75, 64, 16)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("index"), new HashSet<>(), List.of(
				simpleIndexEntry(provider, simpleColoredTranslatedBookContent("items"), EternalStarlight.id("items_index"), ITEMS_LISTENING_IDS, ESItems.ORB_OF_PROPHECY.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslatedBookContent("mobs"), EternalStarlight.id("mobs_index"), MOBS_LISTENING_IDS, ESItems.STARFIRE_BIRD_EGG.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslatedBookContent("misc"), EternalStarlight.id("misc_index"), MISC_LISTENING_IDS, ESItems.AETHERSTRIKE_ROCKET.get().getDefaultInstance())
			), 20, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> itemsSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("items_display"), new HashSet<>(), 60)
				.textDisplay(simpleColoredTranslatedBookContent("items"), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.ORB_OF_PROPHECY.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("items_index"), new HashSet<>(), List.of(
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.SEEKING_EYE.get().getDescriptionId()), EternalStarlight.id("seeking_eye_display"), ESItems.SEEKING_EYE.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.ORB_OF_PROPHECY.get().getDescriptionId()), EternalStarlight.id("orb_of_prophecy_display"), ESItems.ORB_OF_PROPHECY.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslatedBookContent("accessories.title"), EternalStarlight.id("accessories_display"), ESItems.CRESCENT_PENDANT.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.NOCTURNAL_MILLET.get().getDescriptionId()), EternalStarlight.id("nocturnal_millet_display"), ESItems.NOCTURNAL_MILLET.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslatedBookContent("amaramber.title"), EternalStarlight.id("amaramber_display"), ESItems.RAW_AMARAMBER.get().getDefaultInstance())
			), 0, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> seekingEyeSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_display"), enterUnlock(), 60)
				.textDisplay(simpleColoredTranslated(ESItems.SEEKING_EYE.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.SEEKING_EYE.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeking_eye"), enterUnlock(), simpleColoredTranslatedBookContent("seeking_eye"), 10, 20, 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeking_eye_locators"), enterUnlock(), simpleColoredTranslatedBookContent("seeking_eye.locator_items"), 0, 0, 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_locators_starlight_golem"), enterUnlock(), 50)
				.textDisplay(new BookContent(List.of(new BookText(false, "${color: #acfffc}${link: " + EternalStarlight.ID + ":starlight_golem_display}"), new BookText(true, ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()))), true, 65, 35, 110, 9, 6, 1)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 5, 20, 20)
				.itemTagDisplay(ESTags.Items.GOLEM_FORGE_LOCATORS, 55 + 2, 5 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeking_eye_locators_lunar_monstrosity"), enterUnlock(), 50)
				.textDisplay(new BookContent(List.of(new BookText(false, "${color: #acfffc}${link: " + EternalStarlight.ID + ":lunar_monstrosity_display}"), new BookText(true, ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()))), true, 65, 35, 110, 9, 6, 1)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 5, 20, 20)
				.itemTagDisplay(ESTags.Items.CURSED_GARDEN_LOCATORS, 55 + 2, 5 + 2))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> orbOfProphecySection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("orb_of_prophecy_display"), new HashSet<>(), 60)
				.textDisplay(simpleColoredTranslated(ESItems.ORB_OF_PROPHECY.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.ORB_OF_PROPHECY.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("orb_of_prophecy"), new HashSet<>(), simpleColoredTranslatedBookContent("orb_of_prophecy"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> accessoriesSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("accessories_display"), enterUnlock(), 60)
				.textDisplay(simpleColoredTranslatedBookContent("accessories.title"), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemTagDisplay(ESTags.Items.ACCESSORIES, 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("accessories"), enterUnlock(), simpleColoredTranslatedBookContent("accessories"), 10, 0, 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("butterfly_wings_amulet_display"), enterUnlock(), 30)
				.textDisplay(simpleColoredTranslatedBookContent("accessories.butterfly_wings_amulet"), false, 25, 10, 100, 12, 8, 1)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 0, 5, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.BUTTERFLY_WINGS_AMULET.get()), 2, 5 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("pearl_necklace_display"), enterUnlock(), 30)
				.textDisplay(simpleColoredTranslatedBookContent("accessories.pearl_necklace"), false, 25, 10, 100, 12, 8, 1)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 0, 5, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.PEARL_NECKLACE.get()), 2, 5 + 2))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> nocturnalMilletSection(HolderLookup.Provider provider) {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_nocturnal_millet_seeds")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_nocturnal_millet")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_forgotten_nocturnal_millet"))
		));
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("nocturnal_millet_display"), conditions, 60)
				.textDisplay(simpleColoredTranslated(ESItems.NOCTURNAL_MILLET.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.NOCTURNAL_MILLET.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("nocturnal_millet"), conditions, simpleColoredTranslatedBookContent("nocturnal_millet"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> amaramberSection(HolderLookup.Provider provider) {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_raw_amaramber"))
		));
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("amaramber_display"), conditions, 60)
				.textDisplay(simpleColoredTranslatedBookContent("amaramber.title"), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.RAW_AMARAMBER.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("amaramber"), conditions, simpleColoredTranslatedBookContent("amaramber"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> mobsSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("mobs_display"), new HashSet<>(), 60)
				.textDisplay(simpleColoredTranslatedBookContent("mobs"), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.STARFIRE_BIRD_EGG.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("mobs_index"), new HashSet<>(), List.of(
				simpleIndexEntry(provider, simpleColoredTranslatedBookContent("mobs.bosses"), EternalStarlight.id("bosses_index"), BOSSES_LISTENING_IDS, ESItems.GLISTERING_MORNING_STAR.get().getDefaultInstance()),
				simpleMobIndexEntry(provider, "gleech", new HashSet<>()),
				simpleMobIndexEntry(provider, "lonestar_skeleton", new HashSet<>()),
				simpleMobIndexEntry(provider, "nightfall_spider", new HashSet<>()),
				simpleMobIndexEntry(provider, "seeker", new HashSet<>()),
				simpleMobIndexEntry(provider, "thirst_walker", Sets.newHashSet(EternalStarlight.id("dagger_of_hunger_display"), EternalStarlight.id("crystalborn_catalyst_display"))),
				simpleMobIndexEntry(provider, "creteor", new HashSet<>()),
				simpleMobIndexEntry(provider, "tiny_creteor", new HashSet<>()),
				simpleMobIndexEntry(provider, "stranghoul", Sets.newHashSet(EternalStarlight.id("pungency_fruit_display"), EternalStarlight.id("silver_pungency_fruit_display"), EternalStarlight.id("seeds_launcher_display"), EternalStarlight.id("drying_rack_display"))),
				simpleMobIndexEntry(provider, "ent", new HashSet<>()),
				simpleMobIndexEntry(provider, "ratlin", new HashSet<>()),
				simpleMobIndexEntry(provider, "zombified_ratlin", new HashSet<>()),
				simpleMobIndexEntry(provider, "shadow_snail", new HashSet<>()),
				simpleMobIndexEntry(provider, "yeti", new HashSet<>()),
				simpleMobIndexEntry(provider, "aurora_deer", new HashSet<>()),
				simpleMobIndexEntry(provider, "crystallized_moth", new HashSet<>()),
				simpleMobIndexEntry(provider, "shimmer_lacewing", new HashSet<>()),
				simpleMobIndexEntry(provider, "starfire_bird", new HashSet<>()),
				simpleMobIndexEntry(provider, "grimstone_golem", new HashSet<>()),
				simpleMobIndexEntry(provider, "aethersent_golem", new HashSet<>()),
				simpleMobIndexEntry(provider, "rookfish", new HashSet<>()),
				simpleMobIndexEntry(provider, "luminofish", new HashSet<>()),
				simpleMobIndexEntry(provider, "luminaris", new HashSet<>()),
				simpleMobIndexEntry(provider, "twilight_gaze", new HashSet<>())
			), 0, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> bossesSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("bosses_display"), new HashSet<>(), 60)
				.textDisplay(simpleColoredTranslatedBookContent("mobs.bosses"), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.GLISTERING_MORNING_STAR.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("bosses_index"), new HashSet<>(), List.of(
				simpleMobIndexEntry(provider, "the_gatekeeper", new HashSet<>()),
				simpleMobIndexEntry(provider, "starlight_golem", Sets.newHashSet(EternalStarlight.id("freeze_display"), EternalStarlight.id("permafrost_display"), EternalStarlight.id("energy_transmitter_display"), EternalStarlight.id("accumulator_display"))),
				simpleMobIndexEntry(provider, "lunar_monstrosity", Sets.newHashSet(EternalStarlight.id("tangled_display"), EternalStarlight.id("tangled_skull_display")))
			), 0, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> gleechSection() {
		return simpleMobPage("gleech", 67, 60, -25, 210, 45, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.CRYSTALLIZED_DESERT.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> lonestarSkeletonSection() {
		return simpleMobPage("lonestar_skeleton", 65, 85, -25, 210, 30, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> nightfallSpiderSection() {
		return simpleMobPage("nightfall_spider", 63, 70, -25, 210, 30, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> seekerSection() {
		return simpleMobPage("seeker", 65, 57, 0, 0, 25, new Quaternionf().rotationXYZ(5.1F, 0.5F, 2.7F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.FLYING_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> thirstWalkerSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("thirst_walker_display"), enterAndMobSeenUnlock("thirst_walker"), 125)
				.textDisplay(simpleColoredTranslated(ESEntities.THIRST_WALKER.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
				.entityDisplay(entityTag("thirst_walker"), 65, 85, -25, 210, 25, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F))),
			new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("thirst_walker_info"), enterAndMobSeenUnlock("thirst_walker"), ESEntities.THIRST_WALKER.getId(), List.of(
				biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.CRYSTALLIZED_DESERT.location())),
				attributeInfoEntry(Attributes.MAX_HEALTH.value()),
				attributeInfoEntry(Attributes.ARMOR.value()),
				attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
				attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
			), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("thirst_walker"), enterAndMobSeenUnlock("thirst_walker"), simpleColoredTranslatedBookContent("thirst_walker"), 10, 5, 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("thirst_walker_index"), enterAndMobSeenUnlock("thirst_walker"), List.of(
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.DAGGER_OF_HUNGER.get().getDescriptionId()), EternalStarlight.id("dagger_of_hunger_display"), ESItems.DAGGER_OF_HUNGER.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.CRYSTALBORN_CATALYST.get().getDescriptionId()), EternalStarlight.id("crystalborn_catalyst_display"), ESItems.CRYSTALBORN_CATALYST.get().getDefaultInstance())
			), 0, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> daggerOfHungerSection(HolderLookup.Provider provider) {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_thirst_walker"), EternalStarlight.id("entity_killed_thirst_walker")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_thirst_walker"), EternalStarlight.id("item_tooth_of_hunger"))
		));
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("dagger_of_hunger_display"), conditions, 60)
				.textDisplay(simpleColoredTranslated(ESItems.DAGGER_OF_HUNGER.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.DAGGER_OF_HUNGER.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("dagger_of_hunger_recipe"), conditions, 90)
				.craftingRecipeDisplay(EternalStarlight.id("dagger_of_hunger"), 35, 10, 20, 20)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("dagger_of_hunger"), conditions, simpleColoredTranslatedBookContent("dagger_of_hunger"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> crystalbornCatalystSection(HolderLookup.Provider provider) {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_thirst_walker"), EternalStarlight.id("entity_killed_thirst_walker")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_thirst_walker"), EternalStarlight.id("item_tooth_of_hunger"))
		));
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("crystalborn_catalyst_display"), conditions, 60)
				.textDisplay(simpleColoredTranslated(ESItems.CRYSTALBORN_CATALYST.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.CRYSTALBORN_CATALYST.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("crystalborn_catalyst_recipe"), conditions, 90)
				.craftingRecipeDisplay(EternalStarlight.id("crystalborn_catalyst"), 35, 10, 20, 20)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("crystalborn_catalyst"), conditions, simpleColoredTranslatedBookContent("crystalborn_catalyst"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> creteorSection() {
		return simpleMobPage("creteor", 65, 80, -25, 210, 35, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> tinyCreteorSection() {
		return simpleMobPage("tiny_creteor", 65, 65, -25, 210, 40, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.FLYING_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> stranghoulSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("stranghoul_display"), enterAndMobSeenUnlock("stranghoul"), 125)
				.textDisplay(simpleColoredTranslated(ESEntities.STRANGHOUL.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
				.entityDisplay(entityTag("stranghoul"), 65, 85, -25, 210, 30, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F))),
			new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("stranghoul_info"), enterAndMobSeenUnlock("stranghoul"), ESEntities.STRANGHOUL.getId(), List.of(
				biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.DARK_SWAMP.location())),
				structureInfoEntry(Util.makeDescriptionId("structure", ESStructures.STRANGHOUL_DEN.location())),
				attributeInfoEntry(Attributes.MAX_HEALTH.value()),
				attributeInfoEntry(Attributes.ARMOR.value()),
				attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
				attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
			), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("stranghoul"), enterAndMobSeenUnlock("stranghoul"), simpleColoredTranslatedBookContent("stranghoul"), 10, 5, 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("stranghoul_index"), enterAndMobSeenUnlock("stranghoul"), List.of(
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.PUNGENCY_FRUIT.get().getDescriptionId()), EternalStarlight.id("pungency_fruit_display"), ESItems.PUNGENCY_FRUIT.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.SILVER_PUNGENCY_FRUIT.get().getDescriptionId()), EternalStarlight.id("silver_pungency_fruit_display"), ESItems.SILVER_PUNGENCY_FRUIT.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.SEEDS_LAUNCHER.get().getDescriptionId()), EternalStarlight.id("seeds_launcher_display"), ESItems.SEEDS_LAUNCHER.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.DRYING_RACK.get().getDescriptionId()), EternalStarlight.id("drying_rack_display"), ESItems.DRYING_RACK.get().getDefaultInstance())
			), 0, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> pungencyFruitSection(HolderLookup.Provider provider) {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_pungency_fruit"))
		));
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("pungency_fruit_display"), conditions, 60)
				.textDisplay(simpleColoredTranslated(ESItems.PUNGENCY_FRUIT.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.PUNGENCY_FRUIT.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("pungency_fruit"), conditions, simpleColoredTranslatedBookContent("pungency_fruit"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> silverPungencyFruitSection(HolderLookup.Provider provider) {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_pungency_fruit")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_stranghoul"), EternalStarlight.id("item_silver_pungency_fruit"))
		));
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("silver_pungency_fruit_display"), conditions, 60)
				.textDisplay(simpleColoredTranslated(ESItems.SILVER_PUNGENCY_FRUIT.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.SILVER_PUNGENCY_FRUIT.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("silver_pungency_fruit_recipe"), conditions, 90)
				.craftingRecipeDisplay(EternalStarlight.id("silver_pungency_fruit"), 35, 10, 20, 20)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("silver_pungency_fruit"), conditions, simpleColoredTranslatedBookContent("silver_pungency_fruit"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> seedsLauncherSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("seeds_launcher_display"), enterAndMobSeenUnlock("stranghoul"), 60)
				.textDisplay(simpleColoredTranslated(ESItems.SEEDS_LAUNCHER.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.SEEDS_LAUNCHER.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("seeds_launcher"), enterAndMobSeenUnlock("stranghoul"), simpleColoredTranslatedBookContent("seeds_launcher"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> dryingRackSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("drying_rack_display"), enterAndMobSeenUnlock("stranghoul"), 60)
				.textDisplay(simpleColoredTranslated(ESItems.DRYING_RACK.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.DRYING_RACK.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("drying_rack"), enterAndMobSeenUnlock("stranghoul"), simpleColoredTranslatedBookContent("drying_rack"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> entSection() {
		return simpleMobPage("ent", 65, 80, -25, 210, 45, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> ratlinSection() {
		return simpleMobPage("ratlin", 65, 75, -25, 210, 40, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> zombifiedRatlinSection() {
		return simpleMobPage("zombified_ratlin", 65, 75, -25, 210, 40, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.DARK_SWAMP.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> shadowSnailSection() {
		return simpleMobPage("shadow_snail", 65, 70, -25, 210, 45, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> yetiSection() {
		return simpleMobPage("yeti", 65, 75, -25, 210, 35, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.STARLIGHT_PERMAFROST_FOREST.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> auroraDeerSection() {
		return simpleMobPage("aurora_deer", 65, 80, -25, 210, 25, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.STARLIGHT_PERMAFROST_FOREST.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> crystallizedMothSection() {
		return simpleMobPage("crystallized_moth", 65, 65, -10, 210, 30, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.CRYSTALLIZED_DESERT.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.FLYING_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> shimmerLacewingSection() {
		return simpleMobPage("shimmer_lacewing", 65, 75, -25, 210, 40, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.SHIMMER_RIVER.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.FLYING_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> starfireBirdSection() {
		return simpleMobPage("starfire_bird", 65, 72, -25, 210, 45, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.STARLIGHT_FOREST.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.FLYING_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> grimstoneGolemSection() {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_grimstone_golem")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_carved_lunaris_cactus_fruit"))
		));
		return simpleMobPage("grimstone_golem", conditions, 65, 75, -25, 210, 40, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> aethersentGolemSection() {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_aethersent_golem")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_carved_lunaris_cactus_fruit")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_raw_aethersent"))
		));
		return simpleMobPage("aethersent_golem", conditions, 65, 80, -25, 210, 30, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> rookfishSection() {
		return simpleMobPage("rookfish", 65, 65, -25, 210, 45, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> luminofishSection() {
		return simpleMobPage("luminofish", 65, 60, -25, 210, 50, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.THE_ABYSS.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> luminarisSection() {
		return simpleMobPage("luminaris", 65, 60, -25, 210, 50, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.THE_ABYSS.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> twilightGazeSection() {
		return simpleMobPage("twilight_gaze", 65, 70, -25, 210, 40, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			biomeInfoEntry(Util.makeDescriptionId("biome", ESBiomes.THE_ABYSS.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> theGatekeeperSection() {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("the_gatekeeper_display"), new HashSet<>(), 125)
				.textDisplay(simpleColoredTranslated(ESEntities.THE_GATEKEEPER.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
				.entityDisplay(entityTag("the_gatekeeper"), 65, 85, -25, 210, 30, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F))),
			new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("the_gatekeeper_info"), new HashSet<>(), EternalStarlight.id("the_gatekeeper"), List.of(
				attributeInfoEntry(Attributes.MAX_HEALTH.value()),
				attributeInfoEntry(Attributes.ARMOR.value()),
				attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
				attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
			), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("the_gatekeeper"), new HashSet<>(), simpleColoredTranslatedBookContent("the_gatekeeper"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> starlightGolemSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("starlight_golem_display"), enterUnlock(), 125)
				.textDisplay(simpleColoredTranslated(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
				.entityDisplay(entityTag("starlight_golem"), 65, 80, -25, 210, 22, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F))),
			new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("starlight_golem_info"), enterUnlock(), ESEntities.STARLIGHT_GOLEM.getId(), List.of(
				new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.GOLEM_FORGE.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
				new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
				new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("starlight_golem.defense"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
				new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("starlight_golem.speed"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
			), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("starlight_golem"), enterUnlock(), simpleColoredTranslatedBookContent("starlight_golem"), 10, 5, 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("starlight_golem_index"), enterUnlock(), List.of(
				simpleMobIndexEntry(provider, "freeze", new HashSet<>()),
				simpleMobIndexEntry(provider, "permafrost", new HashSet<>()),
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.ENERGY_TRANSMITTER.get().getDescriptionId()), EternalStarlight.id("energy_transmitter_display"), ESItems.ENERGY_TRANSMITTER.get().getDefaultInstance()),
				simpleIndexEntry(provider, simpleColoredTranslated(ESItems.ACCUMULATOR.get().getDescriptionId()), EternalStarlight.id("accumulator_display"), ESItems.ACCUMULATOR.get().getDefaultInstance())
			), 0, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> freezeSection() {
		return simpleMobPage("freeze", 65, 80, -25, 210, 40, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			structureInfoEntry(Util.makeDescriptionId("structure", ESStructures.GOLEM_FORGE.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("freeze.attack"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ATTACK_DAMAGE.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_attack_damage.png")),
			attributeInfoEntry(Attributes.FLYING_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> permafrostSection() {
		return simpleMobPage("permafrost", 65, 80, -25, 210, 22, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			structureInfoEntry(Util.makeDescriptionId("structure", ESStructures.GOLEM_FORGE.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("freeze.attack"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ATTACK_DAMAGE.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_attack_damage.png")),
			attributeInfoEntry(Attributes.FLYING_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> energyTransmitterSection(HolderLookup.Provider provider) {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_ingot")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_nugget"))
		));
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("energy_transmitter_display"), conditions, 60)
				.textDisplay(simpleColoredTranslated(ESItems.ENERGY_TRANSMITTER.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.ENERGY_TRANSMITTER.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("energy_transmitter_recipe"), conditions, 90)
				.craftingRecipeDisplay(EternalStarlight.id("energy_transmitter"), 35, 10, 20, 20)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("energy_transmitter"), conditions, simpleColoredTranslatedBookContent("energy_transmitter"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> accumulatorSection(HolderLookup.Provider provider) {
		HashSet<HashSet<ResourceLocation>> conditions = new HashSet<>(Set.of(
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_ingot")),
			Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("item_golem_steel_nugget"))
		));
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("accumulator_display"), conditions, 60)
				.textDisplay(simpleColoredTranslated(ESItems.ACCUMULATOR.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.ACCUMULATOR.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("accumulator_recipe"), conditions, 90)
				.craftingRecipeDisplay(EternalStarlight.id("accumulator"), 35, 10, 20, 20)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/crafting_3.png"), 35, 10, 60, 60)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/recipe_crafting.png"), 59, 75, 12, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("accumulator"), conditions, simpleColoredTranslatedBookContent("accumulator"), 10, 20, 130, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> lunarMonstrositySection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("lunar_monstrosity_display"), enterUnlock(), 125)
				.textDisplay(simpleColoredTranslated(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), true, 65, 110, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
				.entityDisplay(entityTag("lunar_monstrosity"), 65, 80, -25, 210, 16, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F))),
			new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id("lunar_monstrosity_info"), enterUnlock(), ESEntities.LUNAR_MONSTROSITY.getId(), List.of(
				new MobInfoBookComponent.Entry(simpleColoredTranslated(Util.makeDescriptionId("structure", ESStructures.CURSED_GARDEN.location())), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png")),
				new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MAX_HEALTH.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_max_health.png")),
				new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("lunar_monstrosity.defense"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.ARMOR.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_armor.png")),
				new MobInfoBookComponent.Entry(simpleColoredTranslatedBookContent("lunar_monstrosity.speed"), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(Attributes.MOVEMENT_SPEED.value())), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/attribute_movement_speed.png"))
			), 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("lunar_monstrosity"), enterUnlock(), simpleColoredTranslatedBookContent("lunar_monstrosity"), 10, 5, 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("lunar_monstrosity_index"), enterUnlock(), List.of(
				simpleMobIndexEntry(provider, "tangled", new HashSet<>()),
				simpleMobIndexEntry(provider, "tangled_skull", new HashSet<>())
			), 0, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> tangledSection() {
		return simpleMobPage("tangled", 65, 85, -25, 210, 30, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			structureInfoEntry(Util.makeDescriptionId("structure", ESStructures.CURSED_GARDEN.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value()),
			attributeInfoEntry(Attributes.MOVEMENT_SPEED.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> tangledSkullSection() {
		return simpleMobPage("tangled_skull", 65, 70, -25, 210, 50, new Quaternionf().rotationXYZ(0.45F, 0.0F, 3.14F), List.of(
			structureInfoEntry(Util.makeDescriptionId("structure", ESStructures.CURSED_GARDEN.location())),
			attributeInfoEntry(Attributes.MAX_HEALTH.value()),
			attributeInfoEntry(Attributes.ARMOR.value()),
			attributeInfoEntry(Attributes.ATTACK_DAMAGE.value())
		));
	}

	private static List<ConfiguredBookComponent<?, ?>> miscSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("misc_display"), enterUnlock(), 60)
				.textDisplay(simpleColoredTranslatedBookContent("misc"), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.AETHERSTRIKE_ROCKET.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("misc_index"), enterUnlock(), List.of(
				simpleIndexEntry(provider, simpleColoredTranslated(ESWeathers.METEOR_SHOWER.get().getDescriptionId()), EternalStarlight.id("meteor_shower_display"), ESItems.AETHERSTRIKE_ROCKET.get().getDefaultInstance())
			), 0, 20, 125, 12))
		);
	}

	private static List<ConfiguredBookComponent<?, ?>> meteorShowerSection(HolderLookup.Provider provider) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("meteor_shower_display"), enterUnlock(), 60)
				.textDisplay(simpleColoredTranslated(ESWeathers.METEOR_SHOWER.get().getDescriptionId()), true, 65, 45, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 55, 10, 20, 20)
				.itemDisplay(itemTag(provider, ESItems.AETHERSTRIKE_ROCKET.get()), 55 + 2, 10 + 2)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("meteor_shower"), enterUnlock(), simpleColoredTranslatedBookContent("meteor_shower"), 10, 20, 130, 12))
		);
	}

	private static HashSet<HashSet<ResourceLocation>> enterUnlock() {
		return new HashSet<>(Set.of(Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"))));
	}

	private static HashSet<HashSet<ResourceLocation>> enterAndMobSeenUnlock(String entityId) {
		return new HashSet<>(Set.of(Sets.newHashSet(EternalStarlight.id("advancement_enter_starlight"), EternalStarlight.id("entity_seen_" + entityId))));
	}

	private static List<ConfiguredBookComponent<?, ?>> simpleMobPage(String entityId, int x, int y, float xRot, float yRot, float scale, Quaternionf rotation, List<MobInfoBookComponent.Entry> infoEntries) {
		return simpleMobPage(entityId, enterAndMobSeenUnlock(entityId), x, y, xRot, yRot, scale, rotation, infoEntries);
	}

	private static List<ConfiguredBookComponent<?, ?>> simpleMobPage(String entityId, HashSet<HashSet<ResourceLocation>> unlockConditions, int x, int y, float xRot, float yRot, float scale, Quaternionf rotation, List<MobInfoBookComponent.Entry> infoEntries) {
		return List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id(entityId + "_display"), unlockConditions, 125)
				.textDisplay(defaultEntityName(entityId), true, 65, 110, 110, 12, 3, 1.5f)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/frame_72.png"), 29, 20, 72, 72)
				.entityDisplay(entityTag(entityId), x, y, xRot, yRot, scale, rotation)),
			new ConfiguredBookComponent<>(BookComponentRegistry.MOB_INFO, new MobInfoBookComponent.Config(EternalStarlight.id(entityId + "_info"), unlockConditions, EternalStarlight.id(entityId), infoEntries, 150, 65, 24, 12, 6, 10, EternalStarlight.id("textures/gui/screen/book/left.png"), EternalStarlight.id("textures/gui/screen/book/right.png"))),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id(entityId), unlockConditions, simpleColoredTranslatedBookContent(entityId), 10, 20, 130, 12))
		);
	}

	private static CompoundTag itemTag(HolderLookup.Provider provider, ItemLike item) {
		return (CompoundTag) new ItemStack(item).save(provider);
	}

	private static CompoundTag entityTag(String entityId) {
		return Util.make(() -> {
			CompoundTag tag = new CompoundTag();
			tag.putString(Entity.ID_TAG, EternalStarlight.id(entityId).toString());
			return tag;
		});
	}

	private static IndexBookComponent.Entry simpleIndexEntry(HolderLookup.Provider provider, BookContent text, ResourceLocation jumpTo, ItemStack icon) {
		return simpleIndexEntry(provider, text, jumpTo, new HashSet<>(), icon);
	}

	private static IndexBookComponent.Entry simpleIndexEntry(HolderLookup.Provider provider, BookContent text, ResourceLocation jumpTo, HashSet<ResourceLocation> listening, ItemStack icon) {
		return new IndexBookComponent.Entry(text, jumpTo, listening, 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), itemTag(provider, icon.getItem()));
	}

	private static IndexBookComponent.Entry simpleMobIndexEntry(HolderLookup.Provider provider, String entityId, HashSet<ResourceLocation> listening) {
		return new IndexBookComponent.Entry(defaultEntityName(entityId), EternalStarlight.id(entityId + "_display"), listening, 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), itemTag(provider, BuiltInRegistries.ITEM.get(EternalStarlight.id(entityId + "_spawn_egg"))));
	}

	private static BookContent defaultEntityName(String entityId) {
		return simpleColoredTranslated("entity." + EternalStarlight.ID + "." + entityId);
	}

	private static MobInfoBookComponent.Entry biomeInfoEntry(String biomeKey) {
		return new MobInfoBookComponent.Entry(simpleColoredTranslated(biomeKey), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_biome.png"));
	}

	private static MobInfoBookComponent.Entry structureInfoEntry(String structureKey) {
		return new MobInfoBookComponent.Entry(simpleColoredTranslated(structureKey), Optional.empty(), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/location_structure.png"));
	}

	private static MobInfoBookComponent.Entry attributeInfoEntry(Attribute attribute) {
		String icon = attribute == Attributes.MAX_HEALTH.value() ? "attribute_max_health"
			: attribute == Attributes.ARMOR.value() ? "attribute_armor"
			  : attribute == Attributes.ATTACK_DAMAGE.value() ? "attribute_attack_damage"
				: "attribute_movement_speed";
		return new MobInfoBookComponent.Entry(new BookContent(List.of()), Optional.ofNullable(BuiltInRegistries.ATTRIBUTE.getKeyOrNull(attribute)), Optional.of(Style.EMPTY.withColor(0xacfffc)), 12, 12, EternalStarlight.id("textures/gui/screen/book/" + icon + ".png"));
	}

	private static BookContent simpleColoredTranslatedBookContent(String id) {
		return new BookContent(List.of(new BookText(false, "${color: #acfffc}"), new BookText(true, "book." + EternalStarlight.ID + "." + id)));
	}

	private static BookContent simpleColoredTranslated(String id) {
		return new BookContent(List.of(new BookText(false, "${color: #acfffc}"), new BookText(true, id)));
	}
}
