package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherAmmoType;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;

public class ESSeedsLauncherAmmoTypes {
	public static final ResourceKey<SeedsLauncherAmmoType> WHEAT = create("wheat");
	public static final ResourceKey<SeedsLauncherAmmoType> TORCHFLOWER = create("torchflower");
	public static final ResourceKey<SeedsLauncherAmmoType> BEETROOT = create("beetroot");
	public static final ResourceKey<SeedsLauncherAmmoType> MELON = create("melon");
	public static final ResourceKey<SeedsLauncherAmmoType> PUMPKIN = create("pumpkin");
	public static final ResourceKey<SeedsLauncherAmmoType> CRINOA = create("crinoa");
	public static final ResourceKey<SeedsLauncherAmmoType> NOCTURNAL_MILLET = create("nocturnal_millet");
	public static final ResourceKey<SeedsLauncherAmmoType> PUNGENCY_FRUIT = create("pungency_fruit");

	public static void bootstrap(BootstrapContext<SeedsLauncherAmmoType> context) {
		context.register(WHEAT, new SeedsLauncherAmmoType(Items.WHEAT_SEEDS.builtInRegistryHolder(), 1, 1, 1));
		context.register(TORCHFLOWER, new SeedsLauncherAmmoType(Items.TORCHFLOWER_SEEDS.builtInRegistryHolder(), 1.5f, 1, 1.2f));
		context.register(BEETROOT, new SeedsLauncherAmmoType(Items.BEETROOT_SEEDS.builtInRegistryHolder(), 0.8f, 1.2f, 0.8f));
		context.register(MELON, new SeedsLauncherAmmoType(Items.MELON_SEEDS.builtInRegistryHolder(), 1.2f, 0.9f, 1.1f));
		context.register(PUMPKIN, new SeedsLauncherAmmoType(Items.PUMPKIN_SEEDS.builtInRegistryHolder(), 0.4f, 1.5f, 0.5f));
		context.register(CRINOA, new SeedsLauncherAmmoType(ESItems.CRINOA_SEEDS.get().builtInRegistryHolder(), 1.2f, 1, 1));
		context.register(NOCTURNAL_MILLET, new SeedsLauncherAmmoType(ESItems.NOCTURNAL_MILLET_SEEDS.get().builtInRegistryHolder(), 1.5f, 1.5f, 0.8f));
		context.register(PUNGENCY_FRUIT, new SeedsLauncherAmmoType(ESItems.PUNGENCY_FRUIT_SEEDS.get().builtInRegistryHolder(), 1.5f, 0.9f, 1.2f));
	}

	public static ResourceKey<SeedsLauncherAmmoType> create(String name) {
		return ResourceKey.create(ESRegistries.SEEDS_LAUNCHER_AMMO_TYPE, EternalStarlight.id(name));
	}
}
