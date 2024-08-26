package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class ESPaintingVariants {
	public static final ResourceKey<PaintingVariant> ENERGIZED = create("energized");
	public static final ResourceKey<PaintingVariant> MONSTROUS = create("monstrous");
	public static final ResourceKey<PaintingVariant> THE_CATALYST = create("the_catalyst");
	public static final ResourceKey<PaintingVariant> CRYSTALBORN = create("crystalborn");

	public static void bootstrap(BootstrapContext<PaintingVariant> context) {
		register(context, ENERGIZED, 2, 2);
		register(context, MONSTROUS, 2, 2);
		register(context, THE_CATALYST, 2, 2);
		register(context, CRYSTALBORN, 2, 2);
	}

	private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int xSize, int ySize) {
		context.register(key, new PaintingVariant(xSize, ySize, key.location()));
	}

	public static ResourceKey<PaintingVariant> create(String name) {
		return ResourceKey.create(Registries.PAINTING_VARIANT, EternalStarlight.id(name));
	}
}
