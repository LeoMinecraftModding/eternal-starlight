package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.menu.AlloyFurnaceMenu;
import cn.leolezury.eternalstarlight.common.item.menu.CrateMenu;
import cn.leolezury.eternalstarlight.common.item.menu.CrystalbornCatalystMenu;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ESMenuTypes {
	public static final RegistrationProvider<MenuType<?>> MENU_TYPES = RegistrationProvider.get(Registries.MENU, EternalStarlight.ID);
	public static final RegistryObject<MenuType<?>, MenuType<CrateMenu>> CRATE = MENU_TYPES.register("crate", () -> new MenuType<>(CrateMenu::new, FeatureFlags.VANILLA_SET));
	public static final RegistryObject<MenuType<?>, MenuType<CrystalbornCatalystMenu>> CRYSTALBORN_CATALYST = MENU_TYPES.register("crystalborn_catalyst", () -> new MenuType<>(CrystalbornCatalystMenu::new, FeatureFlags.VANILLA_SET));
	public static final RegistryObject<MenuType<?>, MenuType<AlloyFurnaceMenu>> ALLOY_FURNACE = MENU_TYPES.register("alloy_furnace", () -> new MenuType<>(AlloyFurnaceMenu::new, FeatureFlags.VANILLA_SET));

	public static void loadClass() {
	}
}
