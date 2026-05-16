package cn.leolezury.eternalstarlight.neoforge.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.neoforge.platform.ESNeoPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class ESRegistryRemapper {
	public static void addAliases() {
		Optional<DeferredRegister<?>> itemsRegister = ESNeoPlatform.REGISTERS.stream().filter(register -> register.getRegistryName().equals(Registries.ITEM.location())).findFirst();
		Optional<DeferredRegister<?>> blocksRegister = ESNeoPlatform.REGISTERS.stream().filter(register -> register.getRegistryName().equals(Registries.BLOCK.location())).findFirst();
		if (itemsRegister.isPresent() && blocksRegister.isPresent()) {
			// banyin
			addAlias("starlight_mangrove_sapling", "banyin_sapling", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_leaves", "banyin_leaves", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_log", "banyin_log", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_wood", "banyin_wood", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_planks", "banyin_planks", itemsRegister.get(), blocksRegister.get());
			addAlias("stripped_starlight_mangrove_log", "stripped_banyin_log", itemsRegister.get(), blocksRegister.get());
			addAlias("stripped_starlight_mangrove_wood", "stripped_banyin_wood", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_door", "banyin_door", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_trapdoor", "banyin_trapdoor", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_pressure_plate", "banyin_pressure_plate", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_button", "banyin_button", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_fence", "banyin_fence", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_fence_gate", "banyin_fence_gate", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_slab", "banyin_slab", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_stairs", "banyin_stairs", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_roots", "banyin_roots", itemsRegister.get(), blocksRegister.get());
			addAlias("muddy_starlight_mangrove_roots", "muddy_banyin_roots", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_sign", "banyin_sign", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_hanging_sign", "banyin_hanging_sign", itemsRegister.get(), blocksRegister.get());
			addAlias("starlight_mangrove_boat", "banyin_boat", itemsRegister.get());
			addAlias("starlight_mangrove_chest_boat", "banyin_chest_boat", itemsRegister.get());
			addAlias("starlight_mangrove_starfire_bird_aviary", "banyin_starfire_bird_aviary", itemsRegister.get(), blocksRegister.get());

			// starcore
			addAlias("atalphaite_block", "starcore_block", itemsRegister.get(), blocksRegister.get());
			addAlias("blazing_atalphaite_block", "blazing_starcore_block", itemsRegister.get(), blocksRegister.get());
			addAlias("atalphaite_light", "starcore_light", itemsRegister.get(), blocksRegister.get());
			addAlias("grimstone_atalphaite_ore", "grimstone_starcore_ore", itemsRegister.get(), blocksRegister.get());
			addAlias("voidstone_atalphaite_ore", "voidstone_starcore_ore", itemsRegister.get(), blocksRegister.get());
			addAlias("eternal_ice_atalphaite_ore", "eternal_ice_starcore_ore", itemsRegister.get(), blocksRegister.get());
			addAlias("haze_ice_atalphaite_ore", "haze_ice_starcore_ore", itemsRegister.get(), blocksRegister.get());
			addAlias("atalphaite", "starcore", itemsRegister.get());

			// deepsilver
			addAlias("swamp_silver_ore", "nightfall_mud_deepsilver_ore", itemsRegister.get(), blocksRegister.get());
			addAlias("swamp_silver_block", "deepsilver_block", itemsRegister.get(), blocksRegister.get());
			addAlias("swamp_silver_ingot", "deepsilver_ingot", itemsRegister.get());
			addAlias("swamp_silver_nugget", "deepsilver_nugget", itemsRegister.get());
			addAlias("swamp_silver_sword", "deepsilver_sword", itemsRegister.get());
			addAlias("swamp_silver_pickaxe", "deepsilver_pickaxe", itemsRegister.get());
			addAlias("swamp_silver_axe", "deepsilver_axe", itemsRegister.get());
			addAlias("swamp_silver_sickle", "deepsilver_sickle", itemsRegister.get());
			addAlias("swamp_silver_helmet", "deepsilver_helmet", itemsRegister.get());
			addAlias("swamp_silver_chestplate", "deepsilver_chestplate", itemsRegister.get());
			addAlias("swamp_silver_leggings", "deepsilver_leggings", itemsRegister.get());
			addAlias("swamp_silver_boots", "deepsilver_boots", itemsRegister.get());

			// soul dew
			addAlias("trapped_soul", "soul_dew", itemsRegister.get());

			// crest pot
			addAlias(EternalStarlight.id("crest_pot"), ResourceLocation.withDefaultNamespace("flower_pot"), itemsRegister.get(), blocksRegister.get());
		}
		addAlias("in_abyssal_fire_ticks", "abyssal_fire_ticks", ESNeoPlatform.ATTACHMENT_TYPE_REGISTER);
	}

	private static void addAlias(String old, String replacement, DeferredRegister<?>... registers) {
		addAlias(EternalStarlight.id(old), EternalStarlight.id(replacement), registers);
	}

	private static void addAlias(ResourceLocation old, ResourceLocation replacement, DeferredRegister<?>... registers) {
		for (DeferredRegister<?> register : registers) {
			register.addAlias(old, replacement);
		}
	}
}
