package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.npc.boarwarf.BoarwarfType;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ESBoarwarfTypes {
    public static final ResourceKey<BoarwarfType> FOREST = create("forest");
    public static final ResourceKey<BoarwarfType> FROZEN = create("frozen");
    public static final ResourceKey<BoarwarfType> SWAMP = create("swamp");

    public static void bootstrap(BootstapContext<BoarwarfType> context) {
        HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
        context.register(FOREST, new BoarwarfType(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_FOREST), new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/biome/forest.png")));
        context.register(FROZEN, new BoarwarfType(biomeHolderGetter.getOrThrow(ESBiomes.STARLIGHT_PERMAFROST_FOREST), new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/biome/frozen.png")));
        context.register(SWAMP, new BoarwarfType(biomeHolderGetter.getOrThrow(ESBiomes.DARK_SWAMP), new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/biome/swamp.png")));
    }

    public static ResourceKey<BoarwarfType> create(String name) {
        return ResourceKey.create(ESRegistries.BOARWARF_TYPE, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
