package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ESPaintingVariants {
	public static final ResourceLocation DEFAULT_BACK_TEXTURE = EternalStarlight.id("painting_back");

	public static final ResourceKey<ESPaintingVariant> GUARDIAN = create("guardian");
	public static final ResourceKey<ESPaintingVariant> GUARDIAN_SPECIAL = create("guardian_special");
	public static final ResourceKey<ESPaintingVariant> ENERGIZED = create("energized");
	public static final ResourceKey<ESPaintingVariant> ENERGIZED_SPECIAL = create("energized_special");
	public static final ResourceKey<ESPaintingVariant> ABSOLUTE_ZERO = create("absolute_zero");
	public static final ResourceKey<ESPaintingVariant> ABSOLUTE_ZERO_SPECIAL = create("absolute_zero_special");
	public static final ResourceKey<ESPaintingVariant> MONSTROUS = create("monstrous");
	public static final ResourceKey<ESPaintingVariant> MONSTROUS_SPECIAL = create("monstrous_special");
	public static final ResourceKey<ESPaintingVariant> THE_CATALYST = create("the_catalyst");
	public static final ResourceKey<ESPaintingVariant> CRYSTALBORN = create("crystalborn");
	public static final ResourceKey<ESPaintingVariant> VOIDSTONE_APPLE = create("voidstone_apple");
	public static final ResourceKey<ESPaintingVariant> PUNGENCY_FRUIT_FEAST = create("pungency_fruit_feast");
	public static final ResourceKey<ESPaintingVariant> STARFLOWERS = create("starflowers");
	public static final ResourceKey<ESPaintingVariant> COLD_ANOMALY = create("cold_anomaly");
	public static final ResourceKey<ESPaintingVariant> NORTHLAND_HUT = create("northland_hut");
	public static final ResourceKey<ESPaintingVariant> RAT_HEAD = create("rat_head");
	public static final ResourceKey<ESPaintingVariant> ORIGINAL_CRETEOR = create("original_creteor");
	public static final ResourceKey<ESPaintingVariant> TWILIGHT_SQUID = create("twilight_squid");
	public static final ResourceKey<ESPaintingVariant> POWER = create("power");
	public static final ResourceKey<ESPaintingVariant> POT_OF_CRESTS = create("pot_of_crests");
	public static final ResourceKey<ESPaintingVariant> THIRSTY = create("thirsty");
	public static final ResourceKey<ESPaintingVariant> STACK = create("stack");
	public static final ResourceKey<ESPaintingVariant> CARVED_SLATE = create("carved_slate");
	public static final ResourceKey<ESPaintingVariant> INSECT_SPECIMEN = create("insect_specimen");
	public static final ResourceKey<ESPaintingVariant> SWORD_OF_THE_LAKE = create("sword_of_the_lake");
	public static final ResourceKey<ESPaintingVariant> SKELETON_LONESTAR = create("skeleton_lonestar");
	public static final ResourceKey<ESPaintingVariant> HYMN_OF_THE_RATS = create("hymn_of_the_rats");
	public static final ResourceKey<ESPaintingVariant> COOLER = create("cooler");
	public static final ResourceKey<ESPaintingVariant> RIVEN_WELKIN = create("riven_welkin");
	public static final ResourceKey<ESPaintingVariant> UMBROUS_ALCHEMIST = create("umbrous_alchemist");
	public static final ResourceKey<ESPaintingVariant> EXTINGUISHED_SUN = create("extinguished_sun");
	public static final ResourceKey<ESPaintingVariant> NOVUS_SOL = create("novus_sol");
	public static final ResourceKey<ESPaintingVariant> MONSTROSITY_HUNTER = create("monstrosity_hunter");
	public static final ResourceKey<ESPaintingVariant> IMAGINARY = create("imaginary");
	public static final ResourceKey<ESPaintingVariant> A_THOUSAND_SUNS = create("a_thousand_suns");
	public static final ResourceKey<ESPaintingVariant> THE_DARK_SIDE_OF_A_STAR = create("the_dark_side_of_a_star");
	public static final ResourceKey<ESPaintingVariant> SPACE_OF_COLOR_LUMPS = create("space_of_color_lumps");
	public static final ResourceKey<ESPaintingVariant> JORMUNGAND = create("jormungand");

	public static void bootstrap(BootstrapContext<ESPaintingVariant> context) {
		register(context, GUARDIAN, 2, 2, true);
		register(context, GUARDIAN_SPECIAL, 2, 2, true);
		register(context, ENERGIZED, 2, 2, true);
		register(context, ENERGIZED_SPECIAL, 2, 2, true);
		register(context, ABSOLUTE_ZERO, 2, 2, true);
		register(context, ABSOLUTE_ZERO_SPECIAL, 2, 2, true);
		register(context, MONSTROUS, 2, 2, true);
		register(context, MONSTROUS_SPECIAL, 2, 2, true);
		register(context, THE_CATALYST, 2, 2, false);
		register(context, CRYSTALBORN, 2, 2, false);
		register(context, VOIDSTONE_APPLE, 2, 2, true);
		register(context, PUNGENCY_FRUIT_FEAST, 1, 2, false);
		register(context, STARFLOWERS, 1, 2, false);
		register(context, COLD_ANOMALY, 1, 1, false);
		register(context, NORTHLAND_HUT, 3, 2, false);
		register(context, RAT_HEAD, 2, 1, false);
		register(context, ORIGINAL_CRETEOR, 1, 2, true);
		register(context, TWILIGHT_SQUID, 1, 1, true);
		register(context, POWER, 1, 2, false);
		register(context, POT_OF_CRESTS, 1, 1, false);
		register(context, THIRSTY, 1, 2, false);
		register(context, STACK, 2, 2, true);
		register(context, CARVED_SLATE, 2, 1, true);
		register(context, INSECT_SPECIMEN, 2, 1, true);
		register(context, SWORD_OF_THE_LAKE, 1, 1, false);
		register(context, SKELETON_LONESTAR, 1, 1, false);
		register(context, HYMN_OF_THE_RATS, 3, 4, true);
		register(context, COOLER, 1, 2, true);
		register(context, RIVEN_WELKIN, 3, 3, true);
		register(context, UMBROUS_ALCHEMIST, 2, 2, true);
		register(context, EXTINGUISHED_SUN, 2, 1, true);
		register(context, NOVUS_SOL, 2, 1, true);
		register(context, MONSTROSITY_HUNTER, 3, 3, true);
		register(context, IMAGINARY, 2, 2, true);
		register(context, A_THOUSAND_SUNS, 3, 3, true);
		register(context, THE_DARK_SIDE_OF_A_STAR, 1, 1, true);
		register(context, SPACE_OF_COLOR_LUMPS, 1, 1, false);
		register(context, JORMUNGAND, 2, 1, true);
	}

	private static void register(BootstrapContext<ESPaintingVariant> context, ResourceKey<ESPaintingVariant> key, int width, int height, boolean sidesFromPainting) {
		context.register(key, new ESPaintingVariant(width, height, key.location(), DEFAULT_BACK_TEXTURE, sidesFromPainting));
	}

	public static ResourceKey<ESPaintingVariant> create(String name) {
		return ResourceKey.create(ESRegistries.PAINTING_VARIANT, EternalStarlight.id(name));
	}
}
