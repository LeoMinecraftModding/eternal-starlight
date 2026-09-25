package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceCoolant;
import cn.leolezury.eternalstarlight.common.entity.living.animal.EntVariant;
import cn.leolezury.eternalstarlight.common.entity.living.animal.ShimmerLacewingVariant;
import cn.leolezury.eternalstarlight.common.entity.living.monster.SeekerVariant;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.BoarwarfType;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolemMaterial;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherAmmoType;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.world.gen.biome.BiomeData;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Climate;

public class ESRegistries {
	public static final ResourceKey<Registry<BiomeData>> BIOME_DATA = ResourceKey.createRegistryKey(EternalStarlight.id("biome_data"));
	public static final ResourceKey<Registry<Climate.ParameterList<Holder<BiomeData>>>> SURFACE_CLIMATE = ResourceKey.createRegistryKey(EternalStarlight.id("surface_climate"));
	public static final ResourceKey<Registry<BoarwarfType>> BOARWARF_TYPE = ResourceKey.createRegistryKey(EternalStarlight.id("boarwarf_type"));
	public static final ResourceKey<Registry<AstralGolemMaterial>> ASTRAL_GOLEM_MATERIAL = ResourceKey.createRegistryKey(EternalStarlight.id("astral_golem_material"));
	public static final ResourceKey<Registry<EntVariant>> ENT_VARIANT = ResourceKey.createRegistryKey(EternalStarlight.id("ent_variant"));
	public static final ResourceKey<Registry<ShimmerLacewingVariant>> SHIMMER_LACEWING_VARIANT = ResourceKey.createRegistryKey(EternalStarlight.id("shimmer_lacewing_variant"));
	public static final ResourceKey<Registry<SeekerVariant>> SEEKER_VARIANT = ResourceKey.createRegistryKey(EternalStarlight.id("seeker_variant"));
	public static final ResourceKey<Registry<SeedsLauncherAmmoType>> SEEDS_LAUNCHER_AMMO_TYPE = ResourceKey.createRegistryKey(EternalStarlight.id("seeds_launcher_ammo_type"));
	public static final ResourceKey<Registry<AlloyFurnaceCoolant>> ALLOY_FURNACE_COOLANT = ResourceKey.createRegistryKey(EternalStarlight.id("alloy_furnace_coolant"));
	public static final ResourceKey<Registry<ESPaintingVariant>> PAINTING_VARIANT = ResourceKey.createRegistryKey(EternalStarlight.id("painting_variant"));

	static {
		ESPlatform.INSTANCE.registerDatapackRegistry(BIOME_DATA, BiomeData.CODEC, BiomeData.CODEC);
		ESPlatform.INSTANCE.registerDatapackRegistry(BOARWARF_TYPE, BoarwarfType.CODEC, BoarwarfType.CODEC);
		ESPlatform.INSTANCE.registerDatapackRegistry(ASTRAL_GOLEM_MATERIAL, AstralGolemMaterial.CODEC, AstralGolemMaterial.CODEC);
		ESPlatform.INSTANCE.registerDatapackRegistry(ENT_VARIANT, EntVariant.CODEC, EntVariant.CODEC);
		ESPlatform.INSTANCE.registerDatapackRegistry(SHIMMER_LACEWING_VARIANT, ShimmerLacewingVariant.CODEC, ShimmerLacewingVariant.CODEC);
		ESPlatform.INSTANCE.registerDatapackRegistry(SEEKER_VARIANT, SeekerVariant.CODEC, SeekerVariant.CODEC);
		ESPlatform.INSTANCE.registerDatapackRegistry(SEEDS_LAUNCHER_AMMO_TYPE, SeedsLauncherAmmoType.CODEC, SeedsLauncherAmmoType.CODEC);
		ESPlatform.INSTANCE.registerDatapackRegistry(ALLOY_FURNACE_COOLANT, AlloyFurnaceCoolant.CODEC, AlloyFurnaceCoolant.CODEC);
		ESPlatform.INSTANCE.registerDatapackRegistry(PAINTING_VARIANT, ESPaintingVariant.CODEC, ESPaintingVariant.CODEC);
	}

	public static void loadClass() {
		// kept out of the static block: ESSurfaceClimate reads this key, so registering it there would hit a null codec
		ESPlatform.INSTANCE.registerDatapackRegistry(SURFACE_CLIMATE, ESSurfaceClimate.CODEC, null);
	}
}
