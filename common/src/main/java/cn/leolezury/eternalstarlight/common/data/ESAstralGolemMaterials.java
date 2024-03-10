package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolemMaterial;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class ESAstralGolemMaterials {
    public static final ResourceKey<AstralGolemMaterial> IRON = create("iron");
    public static final ResourceKey<AstralGolemMaterial> SWAMP_SILVER = create("swamp_silver");

    public static void bootstrap(BootstapContext<AstralGolemMaterial> context) {
        context.register(IRON, new AstralGolemMaterial(Items.IRON_INGOT, 1.0f, 1.0f, new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/golem/astral_golem_iron.png"), 0xffffff));
        context.register(SWAMP_SILVER, new AstralGolemMaterial(ESItems.SWAMP_SILVER_INGOT.get(), 1.2f, 1.2f, new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/golem/astral_golem_swamp_silver.png"), 0xffffff));
    }

    public static ResourceKey<AstralGolemMaterial> create(String name) {
        return ResourceKey.create(ESRegistries.ASTRAL_GOLEM_MATERIAL, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
