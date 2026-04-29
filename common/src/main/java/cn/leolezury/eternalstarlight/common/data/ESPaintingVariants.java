package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class ESPaintingVariants {
	public static final ResourceKey<PaintingVariant> GUARDIAN = create("guardian");
	public static final ResourceKey<PaintingVariant> ENERGIZED = create("energized");
	public static final ResourceKey<PaintingVariant> ENERGIZED_SPECIAL = create("energized_special");
	public static final ResourceKey<PaintingVariant> ABSOLUTE_ZERO = create("absolute_zero");
	public static final ResourceKey<PaintingVariant> ABSOLUTE_ZERO_SPECIAL = create("absolute_zero_special");
	public static final ResourceKey<PaintingVariant> MONSTROUS = create("monstrous");
	public static final ResourceKey<PaintingVariant> MONSTROUS_SPECIAL = create("monstrous_special");
	public static final ResourceKey<PaintingVariant> THE_CATALYST = create("the_catalyst");
	public static final ResourceKey<PaintingVariant> CRYSTALBORN = create("crystalborn");
	public static final ResourceKey<PaintingVariant> VOIDSTONE_APPLE = create("voidstone_apple");
	public static final ResourceKey<PaintingVariant> PUNGENCY_FRUIT_FEAST = create("pungency_fruit_feast");
	public static final ResourceKey<PaintingVariant> STARFLOWERS = create("starflowers");
	public static final ResourceKey<PaintingVariant> COLD_ANOMALY = create("cold_anomaly");
	public static final ResourceKey<PaintingVariant> NORTHLAND_HUT = create("northland_hut");
	public static final ResourceKey<PaintingVariant> RAT_HEAD = create("rat_head");
	public static final ResourceKey<PaintingVariant> ORIGINAL_CRETEOR = create("original_creteor");
	public static final ResourceKey<PaintingVariant> TWILIGHT_SQUID = create("twilight_squid");
	public static final ResourceKey<PaintingVariant> POWER = create("power");
	public static final ResourceKey<PaintingVariant> POT_OF_CRESTS = create("pot_of_crests");
	public static final ResourceKey<PaintingVariant> THIRSTY = create("thirsty");
	public static final ResourceKey<PaintingVariant> STACK = create("stack");
	public static final ResourceKey<PaintingVariant> CARVED_SLATE = create("carved_slate");
	public static final ResourceKey<PaintingVariant> INSECT_SPECIMEN = create("insect_specimen");
	public static final ResourceKey<PaintingVariant> SWORD_OF_THE_LAKE = create("sword_of_the_lake");
	public static final ResourceKey<PaintingVariant> SKELETON_LONESTAR = create("skeleton_lonestar");
	public static final ResourceKey<PaintingVariant> HYMN_OF_THE_RATS = create("hymn_of_the_rats");
	public static final ResourceKey<PaintingVariant> COOLER = create("cooler");
	public static final ResourceKey<PaintingVariant> RIVEN_WELKIN = create("riven_welkin");
	public static final ResourceKey<PaintingVariant> UMBROUS_ALCHEMIST = create("umbrous_alchemist");
	public static final ResourceKey<PaintingVariant> EXTINGUISHED_SUN = create("extinguished_sun");
	public static final ResourceKey<PaintingVariant> NOVUS_SOL = create("novus_sol");

	public static void bootstrap(BootstrapContext<PaintingVariant> context) {
		register(context, GUARDIAN, 2, 2);
		register(context, ENERGIZED, 2, 2);
		register(context, ENERGIZED_SPECIAL, 2, 2);
		register(context, ABSOLUTE_ZERO, 2, 2);
		register(context, ABSOLUTE_ZERO_SPECIAL, 2, 2);
		register(context, MONSTROUS, 2, 2);
		register(context, MONSTROUS_SPECIAL, 2, 2);
		register(context, THE_CATALYST, 2, 2);
		register(context, CRYSTALBORN, 2, 2);
		register(context, VOIDSTONE_APPLE, 2, 2);
		register(context, PUNGENCY_FRUIT_FEAST, 1, 2);
		register(context, STARFLOWERS, 1, 2);
		register(context, COLD_ANOMALY, 1, 1);
		register(context, NORTHLAND_HUT, 3, 2);
		register(context, RAT_HEAD, 2, 1);
		register(context, ORIGINAL_CRETEOR, 1, 2);
		register(context, TWILIGHT_SQUID, 1, 1);
		register(context, POWER, 1, 2);
		register(context, POT_OF_CRESTS, 1, 1);
		register(context, THIRSTY, 1, 2);
		register(context, STACK, 2, 2);
		register(context, CARVED_SLATE, 2, 1);
		register(context, INSECT_SPECIMEN, 2, 1);
		register(context, SWORD_OF_THE_LAKE, 1, 1);
		register(context, SKELETON_LONESTAR, 1, 1);
		register(context, HYMN_OF_THE_RATS, 2, 3);
		register(context, COOLER, 1, 2);
		register(context, RIVEN_WELKIN, 3, 3);
		register(context, UMBROUS_ALCHEMIST, 2, 2);
		register(context, EXTINGUISHED_SUN, 2, 1);
		register(context, NOVUS_SOL, 2, 1);
	}

	private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int xSize, int ySize) {
		context.register(key, new PaintingVariant(xSize, ySize, key.location()));
	}

	public static ResourceKey<PaintingVariant> create(String name) {
		return ResourceKey.create(Registries.PAINTING_VARIANT, EternalStarlight.id(name));
	}
}
