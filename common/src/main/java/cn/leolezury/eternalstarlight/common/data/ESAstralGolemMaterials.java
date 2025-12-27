package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolemMaterial;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;

public class ESAstralGolemMaterials {
	public static final ResourceKey<AstralGolemMaterial> IRON = create("iron");
	public static final ResourceKey<AstralGolemMaterial> DEEPSILVER = create("deepsilver");

	public static void bootstrap(BootstrapContext<AstralGolemMaterial> context) {
		context.register(IRON, new AstralGolemMaterial(Items.IRON_INGOT.builtInRegistryHolder(), 1.0f, 1.0f, EternalStarlight.id("entity/boarwarf/golem/astral_golem_iron"), 0xffffff));
		context.register(DEEPSILVER, new AstralGolemMaterial(ESItems.DEEPSILVER_INGOT.asHolder(), 1.2f, 1.2f, EternalStarlight.id("entity/boarwarf/golem/astral_golem_deepsilver"), 0xffffff));
	}

	public static ResourceKey<AstralGolemMaterial> create(String name) {
		return ResourceKey.create(ESRegistries.ASTRAL_GOLEM_MATERIAL, EternalStarlight.id(name));
	}
}
