package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESJukeboxSongs;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import cn.leolezury.eternalstarlight.common.item.armor.AethersentArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.AirSacArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.DeepsilverArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.GlaciteArmorItem;
import cn.leolezury.eternalstarlight.common.item.combat.*;
import cn.leolezury.eternalstarlight.common.item.component.Accessory;
import cn.leolezury.eternalstarlight.common.item.component.GuideBook;
import cn.leolezury.eternalstarlight.common.item.magic.LivingArmItem;
import cn.leolezury.eternalstarlight.common.item.magic.OrbOfProphecyItem;
import cn.leolezury.eternalstarlight.common.item.misc.*;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.material.Fluids;

import java.util.*;
import java.util.function.Supplier;

public class ESItems {
	public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, EternalStarlight.ID);

	public static final List<ResourceKey<Item>> REGISTERED_ITEMS = new ArrayList<>();

	private static RegistryObject<Item, Item> registerItem(String name, Supplier<? extends Item> supplier) {
		REGISTERED_ITEMS.add(ResourceKey.create(Registries.ITEM, EternalStarlight.id(name)));
		return ITEMS.register(name, supplier);
	}

	public static final RegistryObject<Item, Item> BOARWARF_SPAWN_EGG = registerItem("boarwarf_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.BOARWARF::get, 0x36425e, 0xb16d63, new Item.Properties()));
	public static final RegistryObject<Item, Item> ASTRAL_GOLEM_SPAWN_EGG = registerItem("astral_golem_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.ASTRAL_GOLEM::get, 0x8b93af, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item, Item> GLEECH_SPAWN_EGG = registerItem("gleech_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.GLEECH::get, 0x86a6bc, 0xaa6b9c, new Item.Properties()));
	public static final RegistryObject<Item, Item> LONESTAR_SKELETON_SPAWN_EGG = registerItem("lonestar_skeleton_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.LONESTAR_SKELETON::get, 0xd9d9d9, 0x4f5158, new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_SPIDER_SPAWN_EGG = registerItem("nightfall_spider_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.NIGHTFALL_SPIDER::get, 0x233e47, 0x879cca, new Item.Properties()));
	public static final RegistryObject<Item, Item> SEEKER_SPAWN_EGG = registerItem("seeker_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.SEEKER::get, 0x42447d, 0xe9a4c8, new Item.Properties()));
	public static final RegistryObject<Item, Item> THIRST_WALKER_SPAWN_EGG = registerItem("thirst_walker_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.THIRST_WALKER::get, 0x352b2f, 0xa4e0f2, new Item.Properties()));
	public static final RegistryObject<Item, Item> CRETEOR_SPAWN_EGG = registerItem("creteor_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.CRETEOR::get, 0x53235b, 0xe9aded, new Item.Properties()));
	public static final RegistryObject<Item, Item> TINY_CRETEOR_SPAWN_EGG = registerItem("tiny_creteor_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.TINY_CRETEOR::get, 0x53235b, 0xe9aded, new Item.Properties()));
	public static final RegistryObject<Item, Item> STRANGHOUL_SPAWN_EGG = registerItem("stranghoul_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.STRANGHOUL::get, 0x31383a, 0x849496, new Item.Properties()));
	public static final RegistryObject<Item, Item> ENT_SPAWN_EGG = registerItem("ent_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.ENT::get, 0x633737, 0x86b8a9, new Item.Properties()));
	public static final RegistryObject<Item, Item> RATLIN_SPAWN_EGG = registerItem("ratlin_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.RATLIN::get, 0x544e4e, 0xd7959a, new Item.Properties()));
	public static final RegistryObject<Item, Item> ZOMBIFIED_RATLIN_SPAWN_EGG = registerItem("zombified_ratlin_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.ZOMBIFIED_RATLIN::get, 0x565b4f, 0xd7959a, new Item.Properties()));
	public static final RegistryObject<Item, Item> SHADOW_SNAIL_SPAWN_EGG = registerItem("shadow_snail_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.SHADOW_SNAIL::get, 0x5263a6, 0x2f2644, new Item.Properties()));
	public static final RegistryObject<Item, Item> YETI_SPAWN_EGG = registerItem("yeti_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.YETI::get, 0xe8edff, 0xbda1bc, new Item.Properties()));
	public static final RegistryObject<Item, Item> AURORA_DEER_SPAWN_EGG = registerItem("aurora_deer_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.AURORA_DEER::get, 0x3f4e6d, 0x276365, new Item.Properties()));
	public static final RegistryObject<Item, Item> CRYSTALLIZED_MOTH_SPAWN_EGG = registerItem("crystallized_moth_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.CRYSTALLIZED_MOTH::get, 0x361d20, 0xff5cbb, new Item.Properties()));
	public static final RegistryObject<Item, Item> SHIMMER_LACEWING_SPAWN_EGG = registerItem("shimmer_lacewing_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.SHIMMER_LACEWING::get, 0x061d85, 0x15c3cd, new Item.Properties()));
	public static final RegistryObject<Item, Item> STARFIRE_BIRD_SPAWN_EGG = registerItem("starfire_bird_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.STARFIRE_BIRD::get, 0x831a1a, 0xd29615, new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_GOLEM_SPAWN_EGG = registerItem("grimstone_golem_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.GRIMSTONE_GOLEM::get, 0x51525c, 0xf7a2ff, new Item.Properties()));
	public static final RegistryObject<Item, Item> AETHERSENT_GOLEM_SPAWN_EGG = registerItem("aethersent_golem_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.AETHERSENT_GOLEM::get, 0x53235b, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item, Item> ROOKFISH_SPAWN_EGG = registerItem("rookfish_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.ROOKFISH::get, 0x1d2223, 0x55605a, new Item.Properties()));
	public static final RegistryObject<Item, Item> LUMINOFISH_SPAWN_EGG = registerItem("luminofish_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.LUMINOFISH::get, 0x35293a, 0xf1ffc8, new Item.Properties()));
	public static final RegistryObject<Item, Item> LUMINARIS_SPAWN_EGG = registerItem("luminaris_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.LUMINARIS::get, 0x3e3a46, 0x91807c, new Item.Properties()));
	public static final RegistryObject<Item, Item> TWILIGHT_GAZE_SPAWN_EGG = registerItem("twilight_gaze_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.TWILIGHT_GAZE::get, 0x1e135a, 0x8f56ec, new Item.Properties()));
	public static final RegistryObject<Item, Item> THE_GATEKEEPER_SPAWN_EGG = registerItem("the_gatekeeper_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.THE_GATEKEEPER::get, 0x1a1932, 0xffffff, new Item.Properties()));
	public static final RegistryObject<Item, Item> STARLIGHT_GOLEM_SPAWN_EGG = registerItem("starlight_golem_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.STARLIGHT_GOLEM::get, 0x525050, 0x00fff4, new Item.Properties()));
	public static final RegistryObject<Item, Item> FREEZE_SPAWN_EGG = registerItem("freeze_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.FREEZE::get, 0x525050, 0x68ccff, new Item.Properties()));
	public static final RegistryObject<Item, Item> PERMAFROST_SPAWN_EGG = registerItem("permafrost_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.PERMAFROST::get, 0x525050, 0x68ccff, new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_MONSTROSITY_SPAWN_EGG = registerItem("lunar_monstrosity_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.LUNAR_MONSTROSITY::get, 0x322579, 0x99bbe5, new Item.Properties()));
	public static final RegistryObject<Item, Item> TANGLED_SPAWN_EGG = registerItem("tangled_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.TANGLED::get, 0x96b3bb, 0x6275c1, new Item.Properties()));
	public static final RegistryObject<Item, Item> TANGLED_SKULL_SPAWN_EGG = registerItem("tangled_skull_spawn_egg", () -> ESPlatform.INSTANCE.createSpawnEgg(ESEntities.TANGLED_SKULL::get, 0x838c8d, 0x445955, new Item.Properties()));

	// lunar wood
	public static final RegistryObject<Item, Item> LUNAR_SAPLING = registerItem("lunar_sapling", () -> new BlockItem(ESBlocks.LUNAR_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_LEAVES = registerItem("lunar_leaves", () -> new BlockItem(ESBlocks.LUNAR_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CYAN_LUNAR_LEAVES = registerItem("cyan_lunar_leaves", () -> new BlockItem(ESBlocks.CYAN_LUNAR_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PURPLE_LUNAR_LEAVES = registerItem("purple_lunar_leaves", () -> new BlockItem(ESBlocks.PURPLE_LUNAR_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_LOG = registerItem("lunar_log", () -> new BlockItem(ESBlocks.LUNAR_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_WOOD = registerItem("lunar_wood", () -> new BlockItem(ESBlocks.LUNAR_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_PLANKS = registerItem("lunar_planks", () -> new BlockItem(ESBlocks.LUNAR_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_LUNAR_LOG = registerItem("stripped_lunar_log", () -> new BlockItem(ESBlocks.STRIPPED_LUNAR_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_LUNAR_WOOD = registerItem("stripped_lunar_wood", () -> new BlockItem(ESBlocks.STRIPPED_LUNAR_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_DOOR = registerItem("lunar_door", () -> new BlockItem(ESBlocks.LUNAR_DOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_TRAPDOOR = registerItem("lunar_trapdoor", () -> new BlockItem(ESBlocks.LUNAR_TRAPDOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_PRESSURE_PLATE = registerItem("lunar_pressure_plate", () -> new BlockItem(ESBlocks.LUNAR_PRESSURE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_BUTTON = registerItem("lunar_button", () -> new BlockItem(ESBlocks.LUNAR_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_FENCE = registerItem("lunar_fence", () -> new BlockItem(ESBlocks.LUNAR_FENCE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_FENCE_GATE = registerItem("lunar_fence_gate", () -> new BlockItem(ESBlocks.LUNAR_FENCE_GATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_SLAB = registerItem("lunar_slab", () -> new BlockItem(ESBlocks.LUNAR_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_STAIRS = registerItem("lunar_stairs", () -> new BlockItem(ESBlocks.LUNAR_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEAD_LUNAR_LOG = registerItem("dead_lunar_log", () -> new BlockItem(ESBlocks.DEAD_LUNAR_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_CRYSTALLIZED_LUNAR_LOG = registerItem("red_crystallized_lunar_log", () -> new BlockItem(ESBlocks.RED_CRYSTALLIZED_LUNAR_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_CRYSTALLIZED_LUNAR_LOG = registerItem("blue_crystallized_lunar_log", () -> new BlockItem(ESBlocks.BLUE_CRYSTALLIZED_LUNAR_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_SIGN = registerItem("lunar_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ESBlocks.LUNAR_SIGN.get(), ESBlocks.LUNAR_WALL_SIGN.get()));
	public static final RegistryObject<Item, Item> LUNAR_HANGING_SIGN = registerItem("lunar_hanging_sign", () -> new HangingSignItem(ESBlocks.LUNAR_HANGING_SIGN.get(), ESBlocks.LUNAR_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> LUNAR_BOAT = registerItem("lunar_boat", () -> new ESBoatItem(false, ESBoat.Type.LUNAR, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> LUNAR_CHEST_BOAT = registerItem("lunar_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.LUNAR, new Item.Properties().stacksTo(1)));

	// northland wood
	public static final RegistryObject<Item, Item> NORTHLAND_SAPLING = registerItem("northland_sapling", () -> new BlockItem(ESBlocks.NORTHLAND_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_LEAVES = registerItem("northland_leaves", () -> new BlockItem(ESBlocks.NORTHLAND_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_LOG = registerItem("northland_log", () -> new BlockItem(ESBlocks.NORTHLAND_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_WOOD = registerItem("northland_wood", () -> new BlockItem(ESBlocks.NORTHLAND_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_PLANKS = registerItem("northland_planks", () -> new BlockItem(ESBlocks.NORTHLAND_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_NORTHLAND_LOG = registerItem("stripped_northland_log", () -> new BlockItem(ESBlocks.STRIPPED_NORTHLAND_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_NORTHLAND_WOOD = registerItem("stripped_northland_wood", () -> new BlockItem(ESBlocks.STRIPPED_NORTHLAND_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_DOOR = registerItem("northland_door", () -> new BlockItem(ESBlocks.NORTHLAND_DOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_TRAPDOOR = registerItem("northland_trapdoor", () -> new BlockItem(ESBlocks.NORTHLAND_TRAPDOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_PRESSURE_PLATE = registerItem("northland_pressure_plate", () -> new BlockItem(ESBlocks.NORTHLAND_PRESSURE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_BUTTON = registerItem("northland_button", () -> new BlockItem(ESBlocks.NORTHLAND_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_FENCE = registerItem("northland_fence", () -> new BlockItem(ESBlocks.NORTHLAND_FENCE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_FENCE_GATE = registerItem("northland_fence_gate", () -> new BlockItem(ESBlocks.NORTHLAND_FENCE_GATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_SLAB = registerItem("northland_slab", () -> new BlockItem(ESBlocks.NORTHLAND_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_STAIRS = registerItem("northland_stairs", () -> new BlockItem(ESBlocks.NORTHLAND_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_SIGN = registerItem("northland_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ESBlocks.NORTHLAND_SIGN.get(), ESBlocks.NORTHLAND_WALL_SIGN.get()));
	public static final RegistryObject<Item, Item> NORTHLAND_HANGING_SIGN = registerItem("northland_hanging_sign", () -> new HangingSignItem(ESBlocks.NORTHLAND_HANGING_SIGN.get(), ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> NORTHLAND_BOAT = registerItem("northland_boat", () -> new ESBoatItem(false, ESBoat.Type.NORTHLAND, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> NORTHLAND_CHEST_BOAT = registerItem("northland_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.NORTHLAND, new Item.Properties().stacksTo(1)));

	// starlight mangrove wood
	public static final RegistryObject<Item, Item> BANYIN_SAPLING = registerItem("banyin_sapling", () -> new BlockItem(ESBlocks.BANYIN_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_LEAVES = registerItem("banyin_leaves", () -> new BlockItem(ESBlocks.BANYIN_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_LOG = registerItem("banyin_log", () -> new BlockItem(ESBlocks.BANYIN_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_WOOD = registerItem("banyin_wood", () -> new BlockItem(ESBlocks.BANYIN_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_PLANKS = registerItem("banyin_planks", () -> new BlockItem(ESBlocks.BANYIN_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_BANYIN_LOG = registerItem("stripped_banyin_log", () -> new BlockItem(ESBlocks.STRIPPED_BANYIN_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_BANYIN_WOOD = registerItem("stripped_banyin_wood", () -> new BlockItem(ESBlocks.STRIPPED_BANYIN_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_DOOR = registerItem("banyin_door", () -> new BlockItem(ESBlocks.BANYIN_DOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_TRAPDOOR = registerItem("banyin_trapdoor", () -> new BlockItem(ESBlocks.BANYIN_TRAPDOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_PRESSURE_PLATE = registerItem("banyin_pressure_plate", () -> new BlockItem(ESBlocks.BANYIN_PRESSURE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_BUTTON = registerItem("banyin_button", () -> new BlockItem(ESBlocks.BANYIN_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_FENCE = registerItem("banyin_fence", () -> new BlockItem(ESBlocks.BANYIN_FENCE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_FENCE_GATE = registerItem("banyin_fence_gate", () -> new BlockItem(ESBlocks.BANYIN_FENCE_GATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_SLAB = registerItem("banyin_slab", () -> new BlockItem(ESBlocks.BANYIN_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_STAIRS = registerItem("banyin_stairs", () -> new BlockItem(ESBlocks.BANYIN_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_ROOTS = registerItem("banyin_roots", () -> new BlockItem(ESBlocks.BANYIN_ROOTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MUDDY_BANYIN_ROOTS = registerItem("muddy_banyin_roots", () -> new BlockItem(ESBlocks.MUDDY_BANYIN_ROOTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_SIGN = registerItem("banyin_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ESBlocks.BANYIN_SIGN.get(), ESBlocks.BANYIN_WALL_SIGN.get()));
	public static final RegistryObject<Item, Item> BANYIN_HANGING_SIGN = registerItem("banyin_hanging_sign", () -> new HangingSignItem(ESBlocks.BANYIN_HANGING_SIGN.get(), ESBlocks.BANYIN_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> BANYIN_BOAT = registerItem("banyin_boat", () -> new ESBoatItem(false, ESBoat.Type.BANYIN, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> BANYIN_CHEST_BOAT = registerItem("banyin_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.BANYIN, new Item.Properties().stacksTo(1)));

	// scarlet wood
	public static final RegistryObject<Item, Item> SCARLET_SAPLING = registerItem("scarlet_sapling", () -> new BlockItem(ESBlocks.SCARLET_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_LEAVES = registerItem("scarlet_leaves", () -> new BlockItem(ESBlocks.SCARLET_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_LEAVES_PILE = registerItem("scarlet_leaves_pile", () -> new BlockItem(ESBlocks.SCARLET_LEAVES_PILE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_LOG = registerItem("scarlet_log", () -> new BlockItem(ESBlocks.SCARLET_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_WOOD = registerItem("scarlet_wood", () -> new BlockItem(ESBlocks.SCARLET_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_PLANKS = registerItem("scarlet_planks", () -> new BlockItem(ESBlocks.SCARLET_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_SCARLET_LOG = registerItem("stripped_scarlet_log", () -> new BlockItem(ESBlocks.STRIPPED_SCARLET_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_SCARLET_WOOD = registerItem("stripped_scarlet_wood", () -> new BlockItem(ESBlocks.STRIPPED_SCARLET_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_DOOR = registerItem("scarlet_door", () -> new BlockItem(ESBlocks.SCARLET_DOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_TRAPDOOR = registerItem("scarlet_trapdoor", () -> new BlockItem(ESBlocks.SCARLET_TRAPDOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_PRESSURE_PLATE = registerItem("scarlet_pressure_plate", () -> new BlockItem(ESBlocks.SCARLET_PRESSURE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_BUTTON = registerItem("scarlet_button", () -> new BlockItem(ESBlocks.SCARLET_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_FENCE = registerItem("scarlet_fence", () -> new BlockItem(ESBlocks.SCARLET_FENCE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_FENCE_GATE = registerItem("scarlet_fence_gate", () -> new BlockItem(ESBlocks.SCARLET_FENCE_GATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_SLAB = registerItem("scarlet_slab", () -> new BlockItem(ESBlocks.SCARLET_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_STAIRS = registerItem("scarlet_stairs", () -> new BlockItem(ESBlocks.SCARLET_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_SIGN = registerItem("scarlet_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ESBlocks.SCARLET_SIGN.get(), ESBlocks.SCARLET_WALL_SIGN.get()));
	public static final RegistryObject<Item, Item> SCARLET_HANGING_SIGN = registerItem("scarlet_hanging_sign", () -> new HangingSignItem(ESBlocks.SCARLET_HANGING_SIGN.get(), ESBlocks.SCARLET_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> SCARLET_BOAT = registerItem("scarlet_boat", () -> new ESBoatItem(false, ESBoat.Type.SCARLET, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> SCARLET_CHEST_BOAT = registerItem("scarlet_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.SCARLET, new Item.Properties().stacksTo(1)));

	// torreya wood
	public static final RegistryObject<Item, Item> TORREYA_SAPLING = registerItem("torreya_sapling", () -> new BlockItem(ESBlocks.TORREYA_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_LEAVES = registerItem("torreya_leaves", () -> new BlockItem(ESBlocks.TORREYA_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_LOG = registerItem("torreya_log", () -> new BlockItem(ESBlocks.TORREYA_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_WOOD = registerItem("torreya_wood", () -> new BlockItem(ESBlocks.TORREYA_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_PLANKS = registerItem("torreya_planks", () -> new BlockItem(ESBlocks.TORREYA_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_TORREYA_LOG = registerItem("stripped_torreya_log", () -> new BlockItem(ESBlocks.STRIPPED_TORREYA_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_TORREYA_WOOD = registerItem("stripped_torreya_wood", () -> new BlockItem(ESBlocks.STRIPPED_TORREYA_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_DOOR = registerItem("torreya_door", () -> new BlockItem(ESBlocks.TORREYA_DOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_TRAPDOOR = registerItem("torreya_trapdoor", () -> new BlockItem(ESBlocks.TORREYA_TRAPDOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_PRESSURE_PLATE = registerItem("torreya_pressure_plate", () -> new BlockItem(ESBlocks.TORREYA_PRESSURE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_BUTTON = registerItem("torreya_button", () -> new BlockItem(ESBlocks.TORREYA_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_FENCE = registerItem("torreya_fence", () -> new BlockItem(ESBlocks.TORREYA_FENCE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_FENCE_GATE = registerItem("torreya_fence_gate", () -> new BlockItem(ESBlocks.TORREYA_FENCE_GATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_SLAB = registerItem("torreya_slab", () -> new BlockItem(ESBlocks.TORREYA_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_STAIRS = registerItem("torreya_stairs", () -> new BlockItem(ESBlocks.TORREYA_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_SIGN = registerItem("torreya_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ESBlocks.TORREYA_SIGN.get(), ESBlocks.TORREYA_WALL_SIGN.get()));
	public static final RegistryObject<Item, Item> TORREYA_HANGING_SIGN = registerItem("torreya_hanging_sign", () -> new HangingSignItem(ESBlocks.TORREYA_HANGING_SIGN.get(), ESBlocks.TORREYA_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> TORREYA_BOAT = registerItem("torreya_boat", () -> new ESBoatItem(false, ESBoat.Type.TORREYA, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> TORREYA_CHEST_BOAT = registerItem("torreya_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.TORREYA, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> TORREYA_VINES = registerItem("torreya_vines", () -> new BlockItem(ESBlocks.TORREYA_VINES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_CAMPFIRE = registerItem("torreya_campfire", () -> new BlockItem(ESBlocks.TORREYA_CAMPFIRE.get(), new Item.Properties().stacksTo(1)));

	// jinglestem wood
	public static final RegistryObject<Item, Item> JINGLESTEM_SAPLING = registerItem("jinglestem_sapling", () -> new BlockItem(ESBlocks.JINGLESTEM_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HANGING_ALGALEAVES = registerItem("hanging_algaleaves", () -> new BlockItem(ESBlocks.HANGING_ALGALEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ALGALEAVES = registerItem("algaleaves", () -> new BlockItem(ESBlocks.ALGALEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_LOG = registerItem("jinglestem_log", () -> new BlockItem(ESBlocks.JINGLESTEM_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_WOOD = registerItem("jinglestem_wood", () -> new BlockItem(ESBlocks.JINGLESTEM_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_PLANKS = registerItem("jinglestem_planks", () -> new BlockItem(ESBlocks.JINGLESTEM_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_JINGLESTEM_LOG = registerItem("stripped_jinglestem_log", () -> new BlockItem(ESBlocks.STRIPPED_JINGLESTEM_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_JINGLESTEM_WOOD = registerItem("stripped_jinglestem_wood", () -> new BlockItem(ESBlocks.STRIPPED_JINGLESTEM_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_DOOR = registerItem("jinglestem_door", () -> new BlockItem(ESBlocks.JINGLESTEM_DOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_TRAPDOOR = registerItem("jinglestem_trapdoor", () -> new BlockItem(ESBlocks.JINGLESTEM_TRAPDOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_PRESSURE_PLATE = registerItem("jinglestem_pressure_plate", () -> new BlockItem(ESBlocks.JINGLESTEM_PRESSURE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_BUTTON = registerItem("jinglestem_button", () -> new BlockItem(ESBlocks.JINGLESTEM_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_FENCE = registerItem("jinglestem_fence", () -> new BlockItem(ESBlocks.JINGLESTEM_FENCE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_FENCE_GATE = registerItem("jinglestem_fence_gate", () -> new BlockItem(ESBlocks.JINGLESTEM_FENCE_GATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_SLAB = registerItem("jinglestem_slab", () -> new BlockItem(ESBlocks.JINGLESTEM_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_STAIRS = registerItem("jinglestem_stairs", () -> new BlockItem(ESBlocks.JINGLESTEM_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_SIGN = registerItem("jinglestem_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ESBlocks.JINGLESTEM_SIGN.get(), ESBlocks.JINGLESTEM_WALL_SIGN.get()));
	public static final RegistryObject<Item, Item> JINGLESTEM_HANGING_SIGN = registerItem("jinglestem_hanging_sign", () -> new HangingSignItem(ESBlocks.JINGLESTEM_HANGING_SIGN.get(), ESBlocks.JINGLESTEM_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> JINGLESTEM_RAFT = registerItem("jinglestem_raft", () -> new ESBoatItem(false, ESBoat.Type.JINGLESTEM, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> JINGLESTEM_CHEST_RAFT = registerItem("jinglestem_chest_raft", () -> new ESBoatItem(true, ESBoat.Type.JINGLESTEM, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> JINGLESTEM_CRISP = registerItem("jinglestem_crisp", () -> new Item(new Item.Properties().food(ESFoods.JINGLESTEM_CRISP.get())));
	public static final RegistryObject<Item, Item> JINGLESTEM_SANDWICH = registerItem("jinglestem_sandwich", () -> new Item(new Item.Properties().food(ESFoods.JINGLESTEM_SANDWICH.get())));

	// cradlewood wood
	public static final RegistryObject<Item, Item> CRADLEWOOD_SAPLING = registerItem("cradlewood_sapling", () -> new BlockItem(ESBlocks.CRADLEWOOD_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_LEAVES = registerItem("cradlewood_leaves", () -> new BlockItem(ESBlocks.CRADLEWOOD_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_LOG = registerItem("cradlewood_log", () -> new BlockItem(ESBlocks.CRADLEWOOD_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_WOOD = registerItem("cradlewood_wood", () -> new BlockItem(ESBlocks.CRADLEWOOD_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_PLANKS = registerItem("cradlewood_planks", () -> new BlockItem(ESBlocks.CRADLEWOOD_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_CRADLEWOOD_LOG = registerItem("stripped_cradlewood_log", () -> new BlockItem(ESBlocks.STRIPPED_CRADLEWOOD_LOG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STRIPPED_CRADLEWOOD_WOOD = registerItem("stripped_cradlewood_wood", () -> new BlockItem(ESBlocks.STRIPPED_CRADLEWOOD_WOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_DOOR = registerItem("cradlewood_door", () -> new BlockItem(ESBlocks.CRADLEWOOD_DOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_TRAPDOOR = registerItem("cradlewood_trapdoor", () -> new BlockItem(ESBlocks.CRADLEWOOD_TRAPDOOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_PRESSURE_PLATE = registerItem("cradlewood_pressure_plate", () -> new BlockItem(ESBlocks.CRADLEWOOD_PRESSURE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_BUTTON = registerItem("cradlewood_button", () -> new BlockItem(ESBlocks.CRADLEWOOD_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_FENCE = registerItem("cradlewood_fence", () -> new BlockItem(ESBlocks.CRADLEWOOD_FENCE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_FENCE_GATE = registerItem("cradlewood_fence_gate", () -> new BlockItem(ESBlocks.CRADLEWOOD_FENCE_GATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_SLAB = registerItem("cradlewood_slab", () -> new BlockItem(ESBlocks.CRADLEWOOD_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_STAIRS = registerItem("cradlewood_stairs", () -> new BlockItem(ESBlocks.CRADLEWOOD_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_SIGN = registerItem("cradlewood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ESBlocks.CRADLEWOOD_SIGN.get(), ESBlocks.CRADLEWOOD_WALL_SIGN.get()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_HANGING_SIGN = registerItem("cradlewood_hanging_sign", () -> new HangingSignItem(ESBlocks.CRADLEWOOD_HANGING_SIGN.get(), ESBlocks.CRADLEWOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> CRADLEWOOD_BOAT = registerItem("cradlewood_boat", () -> new ESBoatItem(false, ESBoat.Type.CRADLEWOOD, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> CRADLEWOOD_CHEST_BOAT = registerItem("cradlewood_chest_boat", () -> new ESBoatItem(true, ESBoat.Type.CRADLEWOOD, new Item.Properties().stacksTo(1)));

	// grimstone
	public static final RegistryObject<Item, Item> GRIMSTONE = registerItem("grimstone", () -> new BlockItem(ESBlocks.GRIMSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_SLAB = registerItem("grimstone_slab", () -> new BlockItem(ESBlocks.GRIMSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_STAIRS = registerItem("grimstone_stairs", () -> new BlockItem(ESBlocks.GRIMSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_WALL = registerItem("grimstone_wall", () -> new BlockItem(ESBlocks.GRIMSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_GRIMSTONE = registerItem("cobbled_grimstone", () -> new BlockItem(ESBlocks.COBBLED_GRIMSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_GRIMSTONE_SLAB = registerItem("cobbled_grimstone_slab", () -> new BlockItem(ESBlocks.COBBLED_GRIMSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_GRIMSTONE_STAIRS = registerItem("cobbled_grimstone_stairs", () -> new BlockItem(ESBlocks.COBBLED_GRIMSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_GRIMSTONE_WALL = registerItem("cobbled_grimstone_wall", () -> new BlockItem(ESBlocks.COBBLED_GRIMSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_BRICKS = registerItem("grimstone_bricks", () -> new BlockItem(ESBlocks.GRIMSTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_BRICK_SLAB = registerItem("grimstone_brick_slab", () -> new BlockItem(ESBlocks.GRIMSTONE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_BRICK_STAIRS = registerItem("grimstone_brick_stairs", () -> new BlockItem(ESBlocks.GRIMSTONE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_BRICK_WALL = registerItem("grimstone_brick_wall", () -> new BlockItem(ESBlocks.GRIMSTONE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRACKED_GRIMSTONE_BRICKS = registerItem("cracked_grimstone_bricks", () -> new BlockItem(ESBlocks.CRACKED_GRIMSTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE = registerItem("polished_grimstone", () -> new BlockItem(ESBlocks.POLISHED_GRIMSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_SLAB = registerItem("polished_grimstone_slab", () -> new BlockItem(ESBlocks.POLISHED_GRIMSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_STAIRS = registerItem("polished_grimstone_stairs", () -> new BlockItem(ESBlocks.POLISHED_GRIMSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_WALL = registerItem("polished_grimstone_wall", () -> new BlockItem(ESBlocks.POLISHED_GRIMSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_TILES = registerItem("grimstone_tiles", () -> new BlockItem(ESBlocks.GRIMSTONE_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_TILE_SLAB = registerItem("grimstone_tile_slab", () -> new BlockItem(ESBlocks.GRIMSTONE_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_TILE_STAIRS = registerItem("grimstone_tile_stairs", () -> new BlockItem(ESBlocks.GRIMSTONE_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_TILE_WALL = registerItem("grimstone_tile_wall", () -> new BlockItem(ESBlocks.GRIMSTONE_TILE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRACKED_GRIMSTONE_TILES = registerItem("cracked_grimstone_tiles", () -> new BlockItem(ESBlocks.CRACKED_GRIMSTONE_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_TILES = registerItem("polished_grimstone_tiles", () -> new BlockItem(ESBlocks.POLISHED_GRIMSTONE_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_TILE_SLAB = registerItem("polished_grimstone_tile_slab", () -> new BlockItem(ESBlocks.POLISHED_GRIMSTONE_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_TILE_STAIRS = registerItem("polished_grimstone_tile_stairs", () -> new BlockItem(ESBlocks.POLISHED_GRIMSTONE_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_GRIMSTONE_TILE_WALL = registerItem("polished_grimstone_tile_wall", () -> new BlockItem(ESBlocks.POLISHED_GRIMSTONE_TILE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_GRIMSTONE = registerItem("chiseled_grimstone", () -> new BlockItem(ESBlocks.CHISELED_GRIMSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_GRIMSTONE = registerItem("glowing_grimstone", () -> new BlockItem(ESBlocks.GLOWING_GRIMSTONE.get(), new Item.Properties()));

	// voidstone
	public static final RegistryObject<Item, Item> VOIDSTONE = registerItem("voidstone", () -> new BlockItem(ESBlocks.VOIDSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_SLAB = registerItem("voidstone_slab", () -> new BlockItem(ESBlocks.VOIDSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_STAIRS = registerItem("voidstone_stairs", () -> new BlockItem(ESBlocks.VOIDSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_WALL = registerItem("voidstone_wall", () -> new BlockItem(ESBlocks.VOIDSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_VOIDSTONE = registerItem("cobbled_voidstone", () -> new BlockItem(ESBlocks.COBBLED_VOIDSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_VOIDSTONE_SLAB = registerItem("cobbled_voidstone_slab", () -> new BlockItem(ESBlocks.COBBLED_VOIDSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_VOIDSTONE_STAIRS = registerItem("cobbled_voidstone_stairs", () -> new BlockItem(ESBlocks.COBBLED_VOIDSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_VOIDSTONE_WALL = registerItem("cobbled_voidstone_wall", () -> new BlockItem(ESBlocks.COBBLED_VOIDSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_BRICKS = registerItem("voidstone_bricks", () -> new BlockItem(ESBlocks.VOIDSTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_BRICK_SLAB = registerItem("voidstone_brick_slab", () -> new BlockItem(ESBlocks.VOIDSTONE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_BRICK_STAIRS = registerItem("voidstone_brick_stairs", () -> new BlockItem(ESBlocks.VOIDSTONE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_BRICK_WALL = registerItem("voidstone_brick_wall", () -> new BlockItem(ESBlocks.VOIDSTONE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRACKED_VOIDSTONE_BRICKS = registerItem("cracked_voidstone_bricks", () -> new BlockItem(ESBlocks.CRACKED_VOIDSTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_VOIDSTONE = registerItem("polished_voidstone", () -> new BlockItem(ESBlocks.POLISHED_VOIDSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_VOIDSTONE_SLAB = registerItem("polished_voidstone_slab", () -> new BlockItem(ESBlocks.POLISHED_VOIDSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_VOIDSTONE_STAIRS = registerItem("polished_voidstone_stairs", () -> new BlockItem(ESBlocks.POLISHED_VOIDSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_VOIDSTONE_WALL = registerItem("polished_voidstone_wall", () -> new BlockItem(ESBlocks.POLISHED_VOIDSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_TILES = registerItem("voidstone_tiles", () -> new BlockItem(ESBlocks.VOIDSTONE_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_TILE_SLAB = registerItem("voidstone_tile_slab", () -> new BlockItem(ESBlocks.VOIDSTONE_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_TILE_STAIRS = registerItem("voidstone_tile_stairs", () -> new BlockItem(ESBlocks.VOIDSTONE_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_TILE_WALL = registerItem("voidstone_tile_wall", () -> new BlockItem(ESBlocks.VOIDSTONE_TILE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRACKED_VOIDSTONE_TILES = registerItem("cracked_voidstone_tiles", () -> new BlockItem(ESBlocks.CRACKED_VOIDSTONE_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_VOIDSTONE = registerItem("chiseled_voidstone", () -> new BlockItem(ESBlocks.CHISELED_VOIDSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_VOIDSTONE = registerItem("glowing_voidstone", () -> new BlockItem(ESBlocks.GLOWING_VOIDSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_SPIKE = registerItem("voidstone_spike", () -> new BlockItem(ESBlocks.VOIDSTONE_SPIKE.get(), new Item.Properties()));

	// eternal ice
	public static final RegistryObject<Item, Item> ETERNAL_ICE = registerItem("eternal_ice", () -> new BlockItem(ESBlocks.ETERNAL_ICE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THIN_ETERNAL_ICE = registerItem("thin_eternal_ice", () -> new BlockItem(ESBlocks.THIN_ETERNAL_ICE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_BRICKS = registerItem("eternal_ice_bricks", () -> new BlockItem(ESBlocks.ETERNAL_ICE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_BRICK_SLAB = registerItem("eternal_ice_brick_slab", () -> new BlockItem(ESBlocks.ETERNAL_ICE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_BRICK_STAIRS = registerItem("eternal_ice_brick_stairs", () -> new BlockItem(ESBlocks.ETERNAL_ICE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_BRICK_WALL = registerItem("eternal_ice_brick_wall", () -> new BlockItem(ESBlocks.ETERNAL_ICE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_LANTERN = registerItem("eternal_ice_lantern", () -> new BlockItem(ESBlocks.ETERNAL_ICE_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE = registerItem("haze_ice", () -> new BlockItem(ESBlocks.HAZE_ICE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_BRICKS = registerItem("haze_ice_bricks", () -> new BlockItem(ESBlocks.HAZE_ICE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_BRICK_SLAB = registerItem("haze_ice_brick_slab", () -> new BlockItem(ESBlocks.HAZE_ICE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_BRICK_STAIRS = registerItem("haze_ice_brick_stairs", () -> new BlockItem(ESBlocks.HAZE_ICE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_BRICK_WALL = registerItem("haze_ice_brick_wall", () -> new BlockItem(ESBlocks.HAZE_ICE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_LANTERN = registerItem("haze_ice_lantern", () -> new BlockItem(ESBlocks.HAZE_ICE_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> REINFORCED_ICE = registerItem("reinforced_ice", () -> new BlockItem(ESBlocks.REINFORCED_ICE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> REINFORCED_ICE_PANE = registerItem("reinforced_ice_pane", () -> new BlockItem(ESBlocks.REINFORCED_ICE_PANE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ICICLE = registerItem("icicle", () -> new BlockItem(ESBlocks.ICICLE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ASHEN_SNOW = registerItem("ashen_snow", () -> new BlockItem(ESBlocks.ASHEN_SNOW.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ASHEN_SNOWBALL = registerItem("ashen_snowball", () -> new AshenSnowballItem(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> FROZEN_BOMB = registerItem("frozen_bomb", () -> new FrozenBombItem(new Item.Properties().stacksTo(16)));

	// nebulaite
	public static final RegistryObject<Item, Item> NEBULAITE = registerItem("nebulaite", () -> new BlockItem(ESBlocks.NEBULAITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NEBULAITE_BRICKS = registerItem("nebulaite_bricks", () -> new BlockItem(ESBlocks.NEBULAITE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NEBULAITE_BRICK_SLAB = registerItem("nebulaite_brick_slab", () -> new BlockItem(ESBlocks.NEBULAITE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NEBULAITE_BRICK_STAIRS = registerItem("nebulaite_brick_stairs", () -> new BlockItem(ESBlocks.NEBULAITE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NEBULAITE_BRICK_WALL = registerItem("nebulaite_brick_wall", () -> new BlockItem(ESBlocks.NEBULAITE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_NEBULAITE_BRICKS = registerItem("chiseled_nebulaite_bricks", () -> new BlockItem(ESBlocks.CHISELED_NEBULAITE_BRICKS.get(), new Item.Properties()));

	// solar
	public static final RegistryObject<Item, Item> STARCORE = registerItem("starcore", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> STARCORE_BLOCK = registerItem("starcore_block", () -> new BlockItem(ESBlocks.STARCORE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLAZING_STARCORE_BLOCK = registerItem("blazing_starcore_block", () -> new BlockItem(ESBlocks.BLAZING_STARCORE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STARCORE_LIGHT = registerItem("starcore_light", () -> new BlockItem(ESBlocks.STARCORE_LIGHT.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRIMSTONE_STARCORE_ORE = registerItem("grimstone_starcore_ore", () -> new BlockItem(ESBlocks.GRIMSTONE_STARCORE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_STARCORE_ORE = registerItem("voidstone_starcore_ore", () -> new BlockItem(ESBlocks.VOIDSTONE_STARCORE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_STARCORE_ORE = registerItem("eternal_ice_starcore_ore", () -> new BlockItem(ESBlocks.ETERNAL_ICE_STARCORE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_STARCORE_ORE = registerItem("haze_ice_starcore_ore", () -> new BlockItem(ESBlocks.HAZE_ICE_STARCORE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CINDER_BRICK = registerItem("cinder_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE = registerItem("radianite", () -> new BlockItem(ESBlocks.RADIANITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE_SLAB = registerItem("radianite_slab", () -> new BlockItem(ESBlocks.RADIANITE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE_STAIRS = registerItem("radianite_stairs", () -> new BlockItem(ESBlocks.RADIANITE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE_WALL = registerItem("radianite_wall", () -> new BlockItem(ESBlocks.RADIANITE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_RADIANITE = registerItem("cobbled_radianite", () -> new BlockItem(ESBlocks.COBBLED_RADIANITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_RADIANITE_SLAB = registerItem("cobbled_radianite_slab", () -> new BlockItem(ESBlocks.COBBLED_RADIANITE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_RADIANITE_STAIRS = registerItem("cobbled_radianite_stairs", () -> new BlockItem(ESBlocks.COBBLED_RADIANITE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> COBBLED_RADIANITE_WALL = registerItem("cobbled_radianite_wall", () -> new BlockItem(ESBlocks.COBBLED_RADIANITE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE_PILLAR = registerItem("radianite_pillar", () -> new BlockItem(ESBlocks.RADIANITE_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_RADIANITE = registerItem("polished_radianite", () -> new BlockItem(ESBlocks.POLISHED_RADIANITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_RADIANITE_SLAB = registerItem("polished_radianite_slab", () -> new BlockItem(ESBlocks.POLISHED_RADIANITE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_RADIANITE_STAIRS = registerItem("polished_radianite_stairs", () -> new BlockItem(ESBlocks.POLISHED_RADIANITE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_RADIANITE_WALL = registerItem("polished_radianite_wall", () -> new BlockItem(ESBlocks.POLISHED_RADIANITE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE_BRICKS = registerItem("radianite_bricks", () -> new BlockItem(ESBlocks.RADIANITE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE_BRICK_SLAB = registerItem("radianite_brick_slab", () -> new BlockItem(ESBlocks.RADIANITE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE_BRICK_STAIRS = registerItem("radianite_brick_stairs", () -> new BlockItem(ESBlocks.RADIANITE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RADIANITE_BRICK_WALL = registerItem("radianite_brick_wall", () -> new BlockItem(ESBlocks.RADIANITE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_RADIANITE = registerItem("chiseled_radianite", () -> new BlockItem(ESBlocks.CHISELED_RADIANITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_BRICK = registerItem("flare_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_BRICKS = registerItem("flare_bricks", () -> new BlockItem(ESBlocks.FLARE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_BRICK_SLAB = registerItem("flare_brick_slab", () -> new BlockItem(ESBlocks.FLARE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_BRICK_STAIRS = registerItem("flare_brick_stairs", () -> new BlockItem(ESBlocks.FLARE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_BRICK_WALL = registerItem("flare_brick_wall", () -> new BlockItem(ESBlocks.FLARE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_FLARE_BRICKS = registerItem("cut_flare_bricks", () -> new BlockItem(ESBlocks.CUT_FLARE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_FLARE_BRICK_SLAB = registerItem("cut_flare_brick_slab", () -> new BlockItem(ESBlocks.CUT_FLARE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_FLARE_BRICK_STAIRS = registerItem("cut_flare_brick_stairs", () -> new BlockItem(ESBlocks.CUT_FLARE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_FLARE_BRICK_WALL = registerItem("cut_flare_brick_wall", () -> new BlockItem(ESBlocks.CUT_FLARE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_TILES = registerItem("flare_tiles", () -> new BlockItem(ESBlocks.FLARE_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_TILE_SLAB = registerItem("flare_tile_slab", () -> new BlockItem(ESBlocks.FLARE_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_TILE_STAIRS = registerItem("flare_tile_stairs", () -> new BlockItem(ESBlocks.FLARE_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_TILE_WALL = registerItem("flare_tile_wall", () -> new BlockItem(ESBlocks.FLARE_TILE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_FLARE_TILES = registerItem("cut_flare_tiles", () -> new BlockItem(ESBlocks.CUT_FLARE_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_FLARE_TILE_SLAB = registerItem("cut_flare_tile_slab", () -> new BlockItem(ESBlocks.CUT_FLARE_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_FLARE_TILE_STAIRS = registerItem("cut_flare_tile_stairs", () -> new BlockItem(ESBlocks.CUT_FLARE_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_FLARE_TILE_WALL = registerItem("cut_flare_tile_wall", () -> new BlockItem(ESBlocks.CUT_FLARE_TILE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_FLARE_PILLAR = registerItem("chiseled_flare_pillar", () -> new BlockItem(ESBlocks.CHISELED_FLARE_PILLAR.get(), new Item.Properties()));

	// stellagmite
	public static final RegistryObject<Item, Item> STELLAGMITE = registerItem("stellagmite", () -> new BlockItem(ESBlocks.STELLAGMITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STELLAGMITE_SLAB = registerItem("stellagmite_slab", () -> new BlockItem(ESBlocks.STELLAGMITE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STELLAGMITE_STAIRS = registerItem("stellagmite_stairs", () -> new BlockItem(ESBlocks.STELLAGMITE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STELLAGMITE_WALL = registerItem("stellagmite_wall", () -> new BlockItem(ESBlocks.STELLAGMITE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MOLTEN_STELLAGMITE = registerItem("molten_stellagmite", () -> new BlockItem(ESBlocks.MOLTEN_STELLAGMITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MOLTEN_STELLAGMITE_SLAB = registerItem("molten_stellagmite_slab", () -> new BlockItem(ESBlocks.MOLTEN_STELLAGMITE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MOLTEN_STELLAGMITE_STAIRS = registerItem("molten_stellagmite_stairs", () -> new BlockItem(ESBlocks.MOLTEN_STELLAGMITE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MOLTEN_STELLAGMITE_WALL = registerItem("molten_stellagmite_wall", () -> new BlockItem(ESBlocks.MOLTEN_STELLAGMITE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_STELLAGMITE = registerItem("polished_stellagmite", () -> new BlockItem(ESBlocks.POLISHED_STELLAGMITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_STELLAGMITE_SLAB = registerItem("polished_stellagmite_slab", () -> new BlockItem(ESBlocks.POLISHED_STELLAGMITE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_STELLAGMITE_STAIRS = registerItem("polished_stellagmite_stairs", () -> new BlockItem(ESBlocks.POLISHED_STELLAGMITE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_STELLAGMITE_WALL = registerItem("polished_stellagmite_wall", () -> new BlockItem(ESBlocks.POLISHED_STELLAGMITE_WALL.get(), new Item.Properties()));

	// the abyss
	public static final RegistryObject<Item, Item> ABYSSLATE = registerItem("abysslate", () -> new BlockItem(ESBlocks.ABYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE = registerItem("polished_abysslate", () -> new BlockItem(ESBlocks.POLISHED_ABYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_SLAB = registerItem("polished_abysslate_slab", () -> new BlockItem(ESBlocks.POLISHED_ABYSSLATE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_STAIRS = registerItem("polished_abysslate_stairs", () -> new BlockItem(ESBlocks.POLISHED_ABYSSLATE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_WALL = registerItem("polished_abysslate_wall", () -> new BlockItem(ESBlocks.POLISHED_ABYSSLATE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_BRICKS = registerItem("polished_abysslate_bricks", () -> new BlockItem(ESBlocks.POLISHED_ABYSSLATE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_BRICK_SLAB = registerItem("polished_abysslate_brick_slab", () -> new BlockItem(ESBlocks.POLISHED_ABYSSLATE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_BRICK_STAIRS = registerItem("polished_abysslate_brick_stairs", () -> new BlockItem(ESBlocks.POLISHED_ABYSSLATE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_ABYSSLATE_BRICK_WALL = registerItem("polished_abysslate_brick_wall", () -> new BlockItem(ESBlocks.POLISHED_ABYSSLATE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_POLISHED_ABYSSLATE = registerItem("chiseled_polished_abysslate", () -> new BlockItem(ESBlocks.CHISELED_POLISHED_ABYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ABYSSAL_MAGMA_BLOCK = registerItem("abyssal_magma_block", () -> new BlockItem(ESBlocks.ABYSSAL_MAGMA_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ABYSSAL_GEYSER = registerItem("abyssal_geyser", () -> new BlockItem(ESBlocks.ABYSSAL_GEYSER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMABYSSLATE = registerItem("thermabysslate", () -> new BlockItem(ESBlocks.THERMABYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE = registerItem("polished_thermabysslate", () -> new BlockItem(ESBlocks.POLISHED_THERMABYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_SLAB = registerItem("polished_thermabysslate_slab", () -> new BlockItem(ESBlocks.POLISHED_THERMABYSSLATE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_STAIRS = registerItem("polished_thermabysslate_stairs", () -> new BlockItem(ESBlocks.POLISHED_THERMABYSSLATE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_WALL = registerItem("polished_thermabysslate_wall", () -> new BlockItem(ESBlocks.POLISHED_THERMABYSSLATE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_BRICKS = registerItem("polished_thermabysslate_bricks", () -> new BlockItem(ESBlocks.POLISHED_THERMABYSSLATE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_BRICK_SLAB = registerItem("polished_thermabysslate_brick_slab", () -> new BlockItem(ESBlocks.POLISHED_THERMABYSSLATE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_BRICK_STAIRS = registerItem("polished_thermabysslate_brick_stairs", () -> new BlockItem(ESBlocks.POLISHED_THERMABYSSLATE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_THERMABYSSLATE_BRICK_WALL = registerItem("polished_thermabysslate_brick_wall", () -> new BlockItem(ESBlocks.POLISHED_THERMABYSSLATE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_POLISHED_THERMABYSSLATE = registerItem("chiseled_polished_thermabysslate", () -> new BlockItem(ESBlocks.CHISELED_POLISHED_THERMABYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMABYSSAL_MAGMA_BLOCK = registerItem("thermabyssal_magma_block", () -> new BlockItem(ESBlocks.THERMABYSSAL_MAGMA_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMABYSSAL_GEYSER = registerItem("thermabyssal_geyser", () -> new BlockItem(ESBlocks.THERMABYSSAL_GEYSER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRYOBYSSLATE = registerItem("cryobysslate", () -> new BlockItem(ESBlocks.CRYOBYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE = registerItem("polished_cryobysslate", () -> new BlockItem(ESBlocks.POLISHED_CRYOBYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_SLAB = registerItem("polished_cryobysslate_slab", () -> new BlockItem(ESBlocks.POLISHED_CRYOBYSSLATE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_STAIRS = registerItem("polished_cryobysslate_stairs", () -> new BlockItem(ESBlocks.POLISHED_CRYOBYSSLATE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_WALL = registerItem("polished_cryobysslate_wall", () -> new BlockItem(ESBlocks.POLISHED_CRYOBYSSLATE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_BRICKS = registerItem("polished_cryobysslate_bricks", () -> new BlockItem(ESBlocks.POLISHED_CRYOBYSSLATE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_BRICK_SLAB = registerItem("polished_cryobysslate_brick_slab", () -> new BlockItem(ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_BRICK_STAIRS = registerItem("polished_cryobysslate_brick_stairs", () -> new BlockItem(ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_CRYOBYSSLATE_BRICK_WALL = registerItem("polished_cryobysslate_brick_wall", () -> new BlockItem(ESBlocks.POLISHED_CRYOBYSSLATE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_POLISHED_CRYOBYSSLATE = registerItem("chiseled_polished_cryobysslate", () -> new BlockItem(ESBlocks.CHISELED_POLISHED_CRYOBYSSLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRYOBYSSAL_MAGMA_BLOCK = registerItem("cryobyssal_magma_block", () -> new BlockItem(ESBlocks.CRYOBYSSAL_MAGMA_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRYOBYSSAL_GEYSER = registerItem("cryobyssal_geyser", () -> new BlockItem(ESBlocks.CRYOBYSSAL_GEYSER.get(), new Item.Properties()));

	// mud
	public static final RegistryObject<Item, Item> NIGHTFALL_MUD = registerItem("nightfall_mud", () -> new BlockItem(ESBlocks.NIGHTFALL_MUD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_NIGHTFALL_MUD = registerItem("glowing_nightfall_mud", () -> new BlockItem(ESBlocks.GLOWING_NIGHTFALL_MUD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PACKED_NIGHTFALL_MUD = registerItem("packed_nightfall_mud", () -> new BlockItem(ESBlocks.PACKED_NIGHTFALL_MUD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_MUD_BRICKS = registerItem("nightfall_mud_bricks", () -> new BlockItem(ESBlocks.NIGHTFALL_MUD_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_MUD_BRICK_SLAB = registerItem("nightfall_mud_brick_slab", () -> new BlockItem(ESBlocks.NIGHTFALL_MUD_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_MUD_BRICK_STAIRS = registerItem("nightfall_mud_brick_stairs", () -> new BlockItem(ESBlocks.NIGHTFALL_MUD_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_MUD_BRICK_WALL = registerItem("nightfall_mud_brick_wall", () -> new BlockItem(ESBlocks.NIGHTFALL_MUD_BRICK_WALL.get(), new Item.Properties()));

	// sand
	public static final RegistryObject<Item, Item> TWILIGHT_SAND = registerItem("twilight_sand", () -> new BlockItem(ESBlocks.TWILIGHT_SAND.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TWILIGHT_SANDSTONE = registerItem("twilight_sandstone", () -> new BlockItem(ESBlocks.TWILIGHT_SANDSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TWILIGHT_SANDSTONE_SLAB = registerItem("twilight_sandstone_slab", () -> new BlockItem(ESBlocks.TWILIGHT_SANDSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TWILIGHT_SANDSTONE_STAIRS = registerItem("twilight_sandstone_stairs", () -> new BlockItem(ESBlocks.TWILIGHT_SANDSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TWILIGHT_SANDSTONE_WALL = registerItem("twilight_sandstone_wall", () -> new BlockItem(ESBlocks.TWILIGHT_SANDSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_TWILIGHT_SANDSTONE = registerItem("cut_twilight_sandstone", () -> new BlockItem(ESBlocks.CUT_TWILIGHT_SANDSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_TWILIGHT_SANDSTONE_SLAB = registerItem("cut_twilight_sandstone_slab", () -> new BlockItem(ESBlocks.CUT_TWILIGHT_SANDSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_TWILIGHT_SANDSTONE_STAIRS = registerItem("cut_twilight_sandstone_stairs", () -> new BlockItem(ESBlocks.CUT_TWILIGHT_SANDSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CUT_TWILIGHT_SANDSTONE_WALL = registerItem("cut_twilight_sandstone_wall", () -> new BlockItem(ESBlocks.CUT_TWILIGHT_SANDSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_TWILIGHT_SANDSTONE = registerItem("chiseled_twilight_sandstone", () -> new BlockItem(ESBlocks.CHISELED_TWILIGHT_SANDSTONE.get(), new Item.Properties()));

	// gravel
	public static final RegistryObject<Item, Item> DUSTED_GRAVEL = registerItem("dusted_gravel", () -> new BlockItem(ESBlocks.DUSTED_GRAVEL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DUSTED_SHARD = registerItem("dusted_shard", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> DUSTED_BRICKS = registerItem("dusted_bricks", () -> new BlockItem(ESBlocks.DUSTED_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DUSTED_BRICK_SLAB = registerItem("dusted_brick_slab", () -> new BlockItem(ESBlocks.DUSTED_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DUSTED_BRICK_STAIRS = registerItem("dusted_brick_stairs", () -> new BlockItem(ESBlocks.DUSTED_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DUSTED_BRICK_WALL = registerItem("dusted_brick_wall", () -> new BlockItem(ESBlocks.DUSTED_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MOSSY_DUSTED_GRAVEL = registerItem("mossy_dusted_gravel", () -> new BlockItem(ESBlocks.MOSSY_DUSTED_GRAVEL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_MOSSY_DUSTED_GRAVEL = registerItem("glowing_mossy_dusted_gravel", () -> new BlockItem(ESBlocks.GLOWING_MOSSY_DUSTED_GRAVEL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SUSPICIOUS_DUSTED_GRAVEL = registerItem("suspicious_dusted_gravel", () -> new BlockItem(ESBlocks.SUSPICIOUS_DUSTED_GRAVEL.get(), new Item.Properties()));

	// slag
	public static final RegistryObject<Item, Item> DIMSLAG = registerItem("dimslag", () -> new BlockItem(ESBlocks.DIMSLAG.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SUSPICIOUS_DIMSLAG = registerItem("suspicious_dimslag", () -> new BlockItem(ESBlocks.SUSPICIOUS_DIMSLAG.get(), new Item.Properties()));

	// common plant
	public static final RegistryObject<Item, Item> STARLIGHT_FLOWER = registerItem("starlight_flower", () -> new BlockItem(ESBlocks.STARLIGHT_FLOWER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AUREATE_FLOWER = registerItem("aureate_flower", () -> new BlockItem(ESBlocks.AUREATE_FLOWER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CONEBLOOM = registerItem("conebloom", () -> new BlockItem(ESBlocks.CONEBLOOM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFAN = registerItem("nightfan", () -> new BlockItem(ESBlocks.NIGHTFAN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PINK_ROSE = registerItem("pink_rose", () -> new BlockItem(ESBlocks.PINK_ROSE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STARLIGHT_TORCHFLOWER = registerItem("starlight_torchflower", () -> new BlockItem(ESBlocks.STARLIGHT_TORCHFLOWER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFAN_BUSH = registerItem("nightfan_bush", () -> new BlockItem(ESBlocks.NIGHTFAN_BUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PINK_ROSE_BUSH = registerItem("pink_rose_bush", () -> new BlockItem(ESBlocks.PINK_ROSE_BUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHT_SPROUTS = registerItem("night_sprouts", () -> new BlockItem(ESBlocks.NIGHT_SPROUTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_NIGHT_SPROUTS = registerItem("glowing_night_sprouts", () -> new BlockItem(ESBlocks.GLOWING_NIGHT_SPROUTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SMALL_NIGHT_SPROUTS = registerItem("small_night_sprouts", () -> new BlockItem(ESBlocks.SMALL_NIGHT_SPROUTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SMALL_GLOWING_NIGHT_SPROUTS = registerItem("small_glowing_night_sprouts", () -> new BlockItem(ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_GRASS = registerItem("lunar_grass", () -> new BlockItem(ESBlocks.LUNAR_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_LUNAR_GRASS = registerItem("glowing_lunar_grass", () -> new BlockItem(ESBlocks.GLOWING_LUNAR_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRESCENT_GRASS = registerItem("crescent_grass", () -> new BlockItem(ESBlocks.CRESCENT_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_CRESCENT_GRASS = registerItem("glowing_crescent_grass", () -> new BlockItem(ESBlocks.GLOWING_CRESCENT_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PARASOL_GRASS = registerItem("parasol_grass", () -> new BlockItem(ESBlocks.PARASOL_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_PARASOL_GRASS = registerItem("glowing_parasol_grass", () -> new BlockItem(ESBlocks.GLOWING_PARASOL_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_BUSH = registerItem("lunar_bush", () -> new BlockItem(ESBlocks.LUNAR_BUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_LUNAR_BUSH = registerItem("glowing_lunar_bush", () -> new BlockItem(ESBlocks.GLOWING_LUNAR_BUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TALL_CRESCENT_GRASS = registerItem("tall_crescent_grass", () -> new BlockItem(ESBlocks.TALL_CRESCENT_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TALL_GLOWING_CRESCENT_GRASS = registerItem("tall_glowing_crescent_grass", () -> new BlockItem(ESBlocks.TALL_GLOWING_CRESCENT_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_REED = registerItem("lunar_reed", () -> new BlockItem(ESBlocks.LUNAR_REED.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WHISPERBLOOM = registerItem("whisperbloom", () -> new BlockItem(ESBlocks.WHISPERBLOOM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLADESPIKE = registerItem("gladespike", () -> new BlockItem(ESBlocks.GLADESPIKE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VIVIDSTALK = registerItem("vividstalk", () -> new BlockItem(ESBlocks.VIVIDSTALK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TALL_GLADESPIKE = registerItem("tall_gladespike", () -> new BlockItem(ESBlocks.TALL_GLADESPIKE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MOONLIGHT_BUSH = registerItem("moonlight_bush", () -> new BlockItem(ESBlocks.MOONLIGHT_BUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLINTGRASS = registerItem("glintgrass", () -> new BlockItem(ESBlocks.GLINTGRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_MUSHROOM = registerItem("glowing_mushroom", () -> new BlockItem(ESBlocks.GLOWING_MUSHROOM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_MUSHROOM_BLOCK = registerItem("glowing_mushroom_block", () -> new BlockItem(ESBlocks.GLOWING_MUSHROOM_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWING_MUSHROOM_STEM = registerItem("glowing_mushroom_stem", () -> new BlockItem(ESBlocks.GLOWING_MUSHROOM_STEM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SHINING_MUSHROOM = registerItem("shining_mushroom", () -> new BlockItem(ESBlocks.SHINING_MUSHROOM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SHINING_MUSHROOM_BLOCK = registerItem("shining_mushroom_block", () -> new BlockItem(ESBlocks.SHINING_MUSHROOM_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SHINING_MUSHROOM_STEM = registerItem("shining_mushroom_stem", () -> new BlockItem(ESBlocks.SHINING_MUSHROOM_STEM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_BERRIES = registerItem("lunar_berries", () -> new ItemNameBlockItem(ESBlocks.BERRIES_VINES.get(), new Item.Properties().food(ESFoods.LUNAR_BERRIES.get())));
	public static final RegistryObject<Item, Item> CAVE_MOSS = registerItem("cave_moss", () -> new BlockItem(ESBlocks.CAVE_MOSS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CAVE_MOSS_BLOCK = registerItem("cave_moss_block", () -> new BlockItem(ESBlocks.CAVE_MOSS_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CAVE_MOSS_CARPET = registerItem("cave_moss_carpet", () -> new BlockItem(ESBlocks.CAVE_MOSS_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BOULDERSHROOM = registerItem("bouldershroom", () -> new BlockItem(ESBlocks.BOULDERSHROOM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BOULDERSHROOM_BLOCK = registerItem("bouldershroom_block", () -> new BlockItem(ESBlocks.BOULDERSHROOM_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BOULDERSHROOM_STEM = registerItem("bouldershroom_stem", () -> new BlockItem(ESBlocks.BOULDERSHROOM_STEM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BOULDERSHROOM_ROOTS = registerItem("bouldershroom_roots", () -> new BlockItem(ESBlocks.BOULDERSHROOM_ROOTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BOULDERSHROOM_STEW = registerItem("bouldershroom_stew", () -> new Item(new Item.Properties().stacksTo(1).food(ESFoods.BOULDERSHROOM_STEW.get())));

	// swamp plant
	public static final RegistryObject<Item, Item> SWAMP_ROSE = registerItem("swamp_rose", () -> new BlockItem(ESBlocks.SWAMP_ROSE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FANTABUD = registerItem("fantabud", () -> new BlockItem(ESBlocks.FANTABUD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GREEN_FANTABUD = registerItem("green_fantabud", () -> new BlockItem(ESBlocks.GREEN_FANTABUD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FANTAFERN = registerItem("fantafern", () -> new BlockItem(ESBlocks.FANTAFERN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GREEN_FANTAFERN = registerItem("green_fantafern", () -> new BlockItem(ESBlocks.GREEN_FANTAFERN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FANTAGRASS = registerItem("fantagrass", () -> new BlockItem(ESBlocks.FANTAGRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GREEN_FANTAGRASS = registerItem("green_fantagrass", () -> new BlockItem(ESBlocks.GREEN_FANTAGRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HANGING_FANTAGRASS = registerItem("hanging_fantagrass", () -> new BlockItem(ESBlocks.HANGING_FANTAGRASS.get(), new Item.Properties()));

	// scarlet forest plant
	public static final RegistryObject<Item, Item> ORANGE_SCARLET_BUD = registerItem("orange_scarlet_bud", () -> new BlockItem(ESBlocks.ORANGE_SCARLET_BUD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PURPLE_SCARLET_BUD = registerItem("purple_scarlet_bud", () -> new BlockItem(ESBlocks.PURPLE_SCARLET_BUD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_SCARLET_BUD = registerItem("red_scarlet_bud", () -> new BlockItem(ESBlocks.RED_SCARLET_BUD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_GRASS = registerItem("scarlet_grass", () -> new BlockItem(ESBlocks.SCARLET_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MAUVE_FERN = registerItem("mauve_fern", () -> new BlockItem(ESBlocks.MAUVE_FERN.get(), new Item.Properties()));

	// torreya forest plant
	public static final RegistryObject<Item, Item> WITHERED_STARLIGHT_FLOWER = registerItem("withered_starlight_flower", () -> new BlockItem(ESBlocks.WITHERED_STARLIGHT_FLOWER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_GRASS = registerItem("amaramber_grass", () -> new BlockItem(ESBlocks.AMARAMBER_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_GRASS_BUSH = registerItem("amaramber_grass_bush", () -> new BlockItem(ESBlocks.AMARAMBER_GRASS_BUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOOMCANDLE_ROOT = registerItem("gloomcandle_root", () -> new BlockItem(ESBlocks.GLOOMCANDLE_ROOT.get(), new Item.Properties()));

	// desert stuff
	public static final RegistryObject<Item, Item> RED_STARLIGHT_CRYSTAL_BLOCK = registerItem("red_starlight_crystal_block", () -> new BlockItem(ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_STARLIGHT_CRYSTAL_BLOCK = registerItem("blue_starlight_crystal_block", () -> new BlockItem(ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_STARLIGHT_CRYSTAL_CLUSTER = registerItem("red_starlight_crystal_cluster", () -> new BlockItem(ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_STARLIGHT_CRYSTAL_CLUSTER = registerItem("blue_starlight_crystal_cluster", () -> new BlockItem(ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_STARLIGHT_CRYSTAL_SHARD = registerItem("red_starlight_crystal_shard", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_STARLIGHT_CRYSTAL_SHARD = registerItem("blue_starlight_crystal_shard", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_STARLIGHT_CRYSTAL_LANTERN = registerItem("red_starlight_crystal_lantern", () -> new BlockItem(ESBlocks.RED_STARLIGHT_CRYSTAL_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_STARLIGHT_CRYSTAL_LANTERN = registerItem("blue_starlight_crystal_lantern", () -> new BlockItem(ESBlocks.BLUE_STARLIGHT_CRYSTAL_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEAD_LUNAR_BUSH = registerItem("dead_lunar_bush", () -> new BlockItem(ESBlocks.DEAD_LUNAR_BUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DESERT_AMETHYSIA = registerItem("desert_amethysia", () -> new BlockItem(ESBlocks.DESERT_AMETHYSIA.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WITHERED_DESERT_AMETHYSIA = registerItem("withered_desert_amethysia", () -> new BlockItem(ESBlocks.WITHERED_DESERT_AMETHYSIA.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SUNSET_THORNBLOOM = registerItem("sunset_thornbloom", () -> new BlockItem(ESBlocks.SUNSET_THORNBLOOM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMETHYSIA_GRASS = registerItem("amethysia_grass", () -> new BlockItem(ESBlocks.AMETHYSIA_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNARIS_CACTUS = registerItem("lunaris_cactus", () -> new BlockItem(ESBlocks.LUNARIS_CACTUS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNARIS_CACTUS_GEL_BLOCK = registerItem("lunaris_cactus_gel_block", () -> new BlockItem(ESBlocks.LUNARIS_CACTUS_GEL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNARIS_CACTUS_FRUIT = registerItem("lunaris_cactus_fruit", () -> new Item(new Item.Properties().food(ESFoods.LUNARIS_CACTUS_FRUIT.get())));
	public static final RegistryObject<Item, Item> LUNARIS_CACTUS_GEL = registerItem("lunaris_cactus_gel", () -> new Item(new Item.Properties().food(ESFoods.LUNARIS_CACTUS_GEL.get())));
	public static final RegistryObject<Item, Item> CARVED_LUNARIS_CACTUS_FRUIT = registerItem("carved_lunaris_cactus_fruit", () -> new BlockItem(ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNARIS_CACTUS_FRUIT_LANTERN = registerItem("lunaris_cactus_fruit_lantern", () -> new BlockItem(ESBlocks.LUNARIS_CACTUS_FRUIT_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER = registerItem("blooming_red_starlight_crystal_cluster", () -> new BlockItem(ESBlocks.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER = registerItem("blooming_blue_starlight_crystal_cluster", () -> new BlockItem(ESBlocks.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_CRYSTALFLEUR = registerItem("red_crystalfleur", () -> new BlockItem(ESBlocks.RED_CRYSTALFLEUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_CRYSTALFLEUR = registerItem("blue_crystalfleur", () -> new BlockItem(ESBlocks.BLUE_CRYSTALFLEUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_CRYSTALFLEUR_VINE = registerItem("red_crystalfleur_vine", () -> new BlockItem(ESBlocks.RED_CRYSTALFLEUR_VINE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_CRYSTALFLEUR_VINE = registerItem("blue_crystalfleur_vine", () -> new BlockItem(ESBlocks.BLUE_CRYSTALFLEUR_VINE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_CRYSTAL_MOSS_BLOCK = registerItem("red_crystal_moss_block", () -> new BlockItem(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_CRYSTAL_MOSS_BLOCK = registerItem("blue_crystal_moss_block", () -> new BlockItem(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_CRYSTAL_MOSS_CARPET = registerItem("red_crystal_moss_carpet", () -> new BlockItem(ESBlocks.RED_CRYSTAL_MOSS_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_CRYSTAL_MOSS_CARPET = registerItem("blue_crystal_moss_carpet", () -> new BlockItem(ESBlocks.BLUE_CRYSTAL_MOSS_CARPET.get(), new Item.Properties()));

	// beach plant
	public static final RegistryObject<Item, Item> FIRE_ORCHID = registerItem("fire_orchid", () -> new BlockItem(ESBlocks.FIRE_ORCHID.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLAZEBANK_GRASS = registerItem("blazebank_grass", () -> new BlockItem(ESBlocks.BLAZEBANK_GRASS.get(), new Item.Properties()));

	// water plant
	public static final RegistryObject<Item, Item> MOONLIGHT_LILY_PAD = registerItem("moonlight_lily_pad", () -> new PlaceOnWaterBlockItem(ESBlocks.MOONLIGHT_LILY_PAD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STARLIT_LILY_PAD = registerItem("starlit_lily_pad", () -> new PlaceOnWaterBlockItem(ESBlocks.STARLIT_LILY_PAD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MOONLIGHT_DUCKWEED = registerItem("moonlight_duckweed", () -> new PlaceOnWaterBlockItem(ESBlocks.MOONLIGHT_DUCKWEED.get(), new Item.Properties()));

	// sea stuff
	public static final RegistryObject<Item, Item> ABYSSAL_FRUIT = registerItem("abyssal_fruit", () -> new ItemNameBlockItem(ESBlocks.ABYSSAL_KELP.get(), new Item.Properties().food(ESFoods.ABYSSAL_FRUIT.get())));
	public static final RegistryObject<Item, Item> ORBFLORA = registerItem("orbflora", () -> new BlockItem(ESBlocks.ORBFLORA.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ORBFLORA_LIGHT = registerItem("orbflora_light", () -> new BlockItem(ESBlocks.ORBFLORA_LIGHT.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPIRAL_KELP = registerItem("spiral_kelp", () -> new BlockItem(ESBlocks.SPIRAL_KELP.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SEA_ROSA = registerItem("sea_rosa", () -> new BlockItem(ESBlocks.SEA_ROSA.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WICK_GRASS = registerItem("wick_grass", () -> new BlockItem(ESBlocks.WICK_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUMENSTEM = registerItem("lumenstem", () -> new BlockItem(ESBlocks.LUMENSTEM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MARIMOLD = registerItem("marimold", () -> new BlockItem(ESBlocks.MARIMOLD.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MARIMOLD_BLOCK = registerItem("marimold_block", () -> new BlockItem(ESBlocks.MARIMOLD_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MARIMOLD_STEM = registerItem("marimold_stem", () -> new BlockItem(ESBlocks.MARIMOLD_STEM.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CIRCULUSH = registerItem("circulush", () -> new BlockItem(ESBlocks.CIRCULUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STONETT = registerItem("stonett", () -> new BlockItem(ESBlocks.STONETT.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUMINIS = registerItem("luminis", () -> new BlockItem(ESBlocks.LUMINIS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOWLIS = registerItem("glowlis", () -> new BlockItem(ESBlocks.GLOWLIS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLOREED = registerItem("gloreed", () -> new BlockItem(ESBlocks.GLOREED.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STARLIGHT_SEAGRASS = registerItem("starlight_seagrass", () -> new BlockItem(ESBlocks.STARLIGHT_SEAGRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLING_PICKLE = registerItem("jingling_pickle", () -> new BlockItem(ESBlocks.JINGLING_PICKLE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEAD_TENTACLES_CORAL = registerItem("dead_tentacles_coral", () -> new BlockItem(ESBlocks.DEAD_TENTACLES_CORAL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TENTACLES_CORAL = registerItem("tentacles_coral", () -> new BlockItem(ESBlocks.TENTACLES_CORAL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEAD_TENTACLES_CORAL_FAN = registerItem("dead_tentacles_coral_fan", () -> new StandingAndWallBlockItem(ESBlocks.DEAD_TENTACLES_CORAL_FAN.get(), ESBlocks.DEAD_TENTACLES_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item, Item> TENTACLES_CORAL_FAN = registerItem("tentacles_coral_fan", () -> new StandingAndWallBlockItem(ESBlocks.TENTACLES_CORAL_FAN.get(), ESBlocks.TENTACLES_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item, Item> DEAD_TENTACLES_CORAL_BLOCK = registerItem("dead_tentacles_coral_block", () -> new BlockItem(ESBlocks.DEAD_TENTACLES_CORAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TENTACLES_CORAL_BLOCK = registerItem("tentacles_coral_block", () -> new BlockItem(ESBlocks.TENTACLES_CORAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEAD_GOLDEN_CORAL = registerItem("dead_golden_coral", () -> new BlockItem(ESBlocks.DEAD_GOLDEN_CORAL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLDEN_CORAL = registerItem("golden_coral", () -> new BlockItem(ESBlocks.GOLDEN_CORAL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEAD_GOLDEN_CORAL_FAN = registerItem("dead_golden_coral_fan", () -> new StandingAndWallBlockItem(ESBlocks.DEAD_GOLDEN_CORAL_FAN.get(), ESBlocks.DEAD_GOLDEN_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item, Item> GOLDEN_CORAL_FAN = registerItem("golden_coral_fan", () -> new StandingAndWallBlockItem(ESBlocks.GOLDEN_CORAL_FAN.get(), ESBlocks.GOLDEN_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item, Item> DEAD_GOLDEN_CORAL_BLOCK = registerItem("dead_golden_coral_block", () -> new BlockItem(ESBlocks.DEAD_GOLDEN_CORAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLDEN_CORAL_BLOCK = registerItem("golden_coral_block", () -> new BlockItem(ESBlocks.GOLDEN_CORAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEAD_CRYSTALLUM_CORAL = registerItem("dead_crystallum_coral", () -> new BlockItem(ESBlocks.DEAD_CRYSTALLUM_CORAL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRYSTALLUM_CORAL = registerItem("crystallum_coral", () -> new BlockItem(ESBlocks.CRYSTALLUM_CORAL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEAD_CRYSTALLUM_CORAL_FAN = registerItem("dead_crystallum_coral_fan", () -> new StandingAndWallBlockItem(ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN.get(), ESBlocks.DEAD_CRYSTALLUM_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item, Item> CRYSTALLUM_CORAL_FAN = registerItem("crystallum_coral_fan", () -> new StandingAndWallBlockItem(ESBlocks.CRYSTALLUM_CORAL_FAN.get(), ESBlocks.CRYSTALLUM_CORAL_WALL_FAN.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item, Item> DEAD_CRYSTALLUM_CORAL_BLOCK = registerItem("dead_crystallum_coral_block", () -> new BlockItem(ESBlocks.DEAD_CRYSTALLUM_CORAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRYSTALLUM_CORAL_BLOCK = registerItem("crystallum_coral_block", () -> new BlockItem(ESBlocks.CRYSTALLUM_CORAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VELVETUMOSS = registerItem("velvetumoss", () -> new BlockItem(ESBlocks.VELVETUMOSS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VELVETUMOSS_BALL = registerItem("velvetumoss_ball", () -> new Item(new Item.Properties().food(ESFoods.VELVETUMOSS_BALL.get())));
	public static final RegistryObject<Item, Item> VELVETUMOSS_VILLI = registerItem("velvetumoss_villi", () -> new BlockItem(ESBlocks.VELVETUMOSS_VILLI.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_VELVETUMOSS = registerItem("red_velvetumoss", () -> new BlockItem(ESBlocks.RED_VELVETUMOSS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_VELVETUMOSS_VILLI = registerItem("red_velvetumoss_villi", () -> new BlockItem(ESBlocks.RED_VELVETUMOSS_VILLI.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_VELVETUMOSS_FLOWER = registerItem("red_velvetumoss_flower", () -> new BlockItem(ESBlocks.RED_VELVETUMOSS_FLOWER.get(), new Item.Properties()));

	// crystal caves plant
	public static final RegistryObject<Item, Item> CRYSTALLIZED_LUNAR_GRASS = registerItem("crystallized_lunar_grass", () -> new BlockItem(ESBlocks.CRYSTALLIZED_LUNAR_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_CRYSTAL_ROOTS = registerItem("red_crystal_roots", () -> new BlockItem(ESBlocks.RED_CRYSTAL_ROOTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_CRYSTAL_ROOTS = registerItem("blue_crystal_roots", () -> new BlockItem(ESBlocks.BLUE_CRYSTAL_ROOTS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TWILVEWRYM_HERB = registerItem("twilvewyrm_herb", () -> new BlockItem(ESBlocks.TWILVEWRYM_HERB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STELLAFLY_BUSH = registerItem("stellafly_bush", () -> new BlockItem(ESBlocks.STELLAFLY_BUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLIMMERFLY_BUSH = registerItem("glimmerfly_bush", () -> new BlockItem(ESBlocks.GLIMMERFLY_BUSH.get(), new Item.Properties()));

	// solaris isles plant
	public static final RegistryObject<Item, Item> SACRED_STARLIGHT_FLOWER = registerItem("sacred_starlight_flower", () -> new BlockItem(ESBlocks.SACRED_STARLIGHT_FLOWER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRESCENTLEAF = registerItem("crescentleaf", () -> new BlockItem(ESBlocks.CRESCENTLEAF.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLDEN_GRASS = registerItem("golden_grass", () -> new BlockItem(ESBlocks.GOLDEN_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TALL_GOLDEN_GRASS = registerItem("tall_golden_grass", () -> new BlockItem(ESBlocks.TALL_GOLDEN_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SACRED_LANTERNVINE = registerItem("sacred_lanternvine", () -> new StandingAndHangingVineBlockItem(ESBlocks.SACRED_LANTERNVINE.get(), ESBlocks.HANGING_SACRED_LANTERNVINE.get(), new Item.Properties()));

	// dirt & grass blocks
	public static final RegistryObject<Item, Item> NIGHTFALL_DIRT = registerItem("nightfall_dirt", () -> new BlockItem(ESBlocks.NIGHTFALL_DIRT.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_FARMLAND = registerItem("nightfall_farmland", () -> new BlockItem(ESBlocks.NIGHTFALL_FARMLAND.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_DIRT_PATH = registerItem("nightfall_dirt_path", () -> new BlockItem(ESBlocks.NIGHTFALL_DIRT_PATH.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_GRASS_BLOCK = registerItem("nightfall_grass_block", () -> new BlockItem(ESBlocks.NIGHTFALL_GRASS_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_PODZOL = registerItem("nightfall_podzol", () -> new BlockItem(ESBlocks.NIGHTFALL_PODZOL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TENACIOUS_NIGHTFALL_GRASS_BLOCK = registerItem("tenacious_nightfall_grass_block", () -> new BlockItem(ESBlocks.TENACIOUS_NIGHTFALL_GRASS_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FANTASY_GRASS_BLOCK = registerItem("fantasy_grass_block", () -> new BlockItem(ESBlocks.FANTASY_GRASS_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FANTASY_GRASS_CARPET = registerItem("fantasy_grass_carpet", () -> new BlockItem(ESBlocks.FANTASY_GRASS_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLDEN_GRASS_BLOCK = registerItem("golden_grass_block", () -> new BlockItem(ESBlocks.GOLDEN_GRASS_BLOCK.get(), new Item.Properties()));

	// crops
	public static final RegistryObject<Item, Item> CRINOA_SEEDS = registerItem("crinoa_seeds", () -> new ItemNameBlockItem(ESBlocks.CRINOA.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRINOA = registerItem("crinoa", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> CRINOA_BALE = registerItem("crinoa_bale", () -> new BlockItem(ESBlocks.CRINOA_BALE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRINOA_BALL = registerItem("crinoa_ball", () -> new Item(new Item.Properties().food(ESFoods.CRINOA_BALL.get())));
	public static final RegistryObject<Item, Item> CRINOA_PORRIDGE = registerItem("crinoa_porridge", () -> new Item(new Item.Properties().stacksTo(1).food(ESFoods.CRINOA_PORRIDGE.get())));
	public static final RegistryObject<Item, Item> NOCTURNAL_MILLET_SEEDS = registerItem("nocturnal_millet_seeds", () -> new ItemNameBlockItem(ESBlocks.NOCTURNAL_MILLET_STALK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NOCTURNAL_MILLET = registerItem("nocturnal_millet", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> FORGOTTEN_NOCTURNAL_MILLET = registerItem("forgotten_nocturnal_millet", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> POPPED_NOCTURNAL_MILLET_BUCKET = registerItem("popped_nocturnal_millet_bucket", () -> new Item(new Item.Properties().durability(10).food(ESFoods.POPPED_NOCTURNAL_MILLET_BUCKET.get())));
	public static final RegistryObject<Item, Item> ROASTED_FORGOTTEN_NOCTURNAL_MILLET = registerItem("roasted_forgotten_nocturnal_millet", () -> new Item(new Item.Properties().food(ESFoods.ROASTED_FORGOTTEN_NOCTURNAL_MILLET.get())));

	// aethersent
	public static final RegistryObject<Item, Item> CRETEOR_HIDE = registerItem("creteor_hide", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> RAW_AETHERSENT = registerItem("raw_aethersent", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> AETHERSENT_INGOT = registerItem("aethersent_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> AETHERSENT_NUGGET = registerItem("aethersent_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> RAW_AETHERSENT_BLOCK = registerItem("raw_aethersent_block", () -> new BlockItem(ESBlocks.RAW_AETHERSENT_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AETHERSENT_BLOCK = registerItem("aethersent_block", () -> new BlockItem(ESBlocks.AETHERSENT_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AETHERSENT_ARROW = registerItem("aethersent_arrow", () -> new AethersentArrowItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> RAGE_OF_STARS = registerItem("rage_of_stars", () -> new RageOfStarsItem(ESItemTiers.AETHERSENT, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.AETHERSENT, 3, -2.4F))));
	public static final RegistryObject<Item, Item> STARFALL_LONGBOW = registerItem("starfall_longbow", () -> new StarfallLongbowItem(new Item.Properties().durability(2000)));
	public static final RegistryObject<Item, Item> AETHERSENT_HOOD = registerItem("aethersent_hood",
		() -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(45))));
	public static final RegistryObject<Item, Item> AETHERSENT_CAPE = registerItem("aethersent_cape",
		() -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT.asHolder(), ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(45)).component(ESDataComponents.ACCESSORY_SLOT_COUNT.get(), 2)));
	public static final RegistryObject<Item, Item> AETHERSENT_BOTTOMS = registerItem("aethersent_bottoms",
		() -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT.asHolder(), ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(45))));
	public static final RegistryObject<Item, Item> AETHERSENT_BOOTS = registerItem("aethersent_boots",
		() -> new AethersentArmorItem(ESArmorMaterials.AETHERSENT.asHolder(), ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(45))));
	public static final RegistryObject<Item, Item> GALACTIC_QUIVER = registerItem("galactic_quiver", () -> new GalacticQuiverItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> AETHERSTRIKE_ROCKET = registerItem("aetherstrike_rocket", () -> new AetherstrikeRocketItem(new Item.Properties()));

	// thermal springstone
	public static final RegistryObject<Item, Item> SPRINGSTONE = registerItem("springstone", () -> new BlockItem(ESBlocks.SPRINGSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPRINGSTONE_SLAB = registerItem("springstone_slab", () -> new BlockItem(ESBlocks.SPRINGSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPRINGSTONE_STAIRS = registerItem("springstone_stairs", () -> new BlockItem(ESBlocks.SPRINGSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPRINGSTONE_WALL = registerItem("springstone_wall", () -> new BlockItem(ESBlocks.SPRINGSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPRINGSTONE_BRICKS = registerItem("springstone_bricks", () -> new BlockItem(ESBlocks.SPRINGSTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPRINGSTONE_BRICK_SLAB = registerItem("springstone_brick_slab", () -> new BlockItem(ESBlocks.SPRINGSTONE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPRINGSTONE_BRICK_STAIRS = registerItem("springstone_brick_stairs", () -> new BlockItem(ESBlocks.SPRINGSTONE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPRINGSTONE_BRICK_WALL = registerItem("springstone_brick_wall", () -> new BlockItem(ESBlocks.SPRINGSTONE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_SPRINGSTONE = registerItem("polished_springstone", () -> new BlockItem(ESBlocks.POLISHED_SPRINGSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_SPRINGSTONE_SLAB = registerItem("polished_springstone_slab", () -> new BlockItem(ESBlocks.POLISHED_SPRINGSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_SPRINGSTONE_STAIRS = registerItem("polished_springstone_stairs", () -> new BlockItem(ESBlocks.POLISHED_SPRINGSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_SPRINGSTONE_WALL = registerItem("polished_springstone_wall", () -> new BlockItem(ESBlocks.POLISHED_SPRINGSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_SPRINGSTONE = registerItem("chiseled_springstone", () -> new BlockItem(ESBlocks.CHISELED_SPRINGSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE = registerItem("thermal_springstone", () -> new BlockItem(ESBlocks.THERMAL_SPRINGSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_SLAB = registerItem("thermal_springstone_slab", () -> new BlockItem(ESBlocks.THERMAL_SPRINGSTONE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_STAIRS = registerItem("thermal_springstone_stairs", () -> new BlockItem(ESBlocks.THERMAL_SPRINGSTONE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_WALL = registerItem("thermal_springstone_wall", () -> new BlockItem(ESBlocks.THERMAL_SPRINGSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_BRICKS = registerItem("thermal_springstone_bricks", () -> new BlockItem(ESBlocks.THERMAL_SPRINGSTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_BRICK_SLAB = registerItem("thermal_springstone_brick_slab", () -> new BlockItem(ESBlocks.THERMAL_SPRINGSTONE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_BRICK_STAIRS = registerItem("thermal_springstone_brick_stairs", () -> new BlockItem(ESBlocks.THERMAL_SPRINGSTONE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_BRICK_WALL = registerItem("thermal_springstone_brick_wall", () -> new BlockItem(ESBlocks.THERMAL_SPRINGSTONE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_INGOT = registerItem("thermal_springstone_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_SWORD = registerItem("thermal_springstone_sword",
		() -> new SwordItem(ESItemTiers.THERMAL_SPRINGSTONE, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.THERMAL_SPRINGSTONE, 3, -2.4F))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_PICKAXE = registerItem("thermal_springstone_pickaxe",
		() -> new PickaxeItem(ESItemTiers.THERMAL_SPRINGSTONE, new Item.Properties().attributes(PickaxeItem.createAttributes(ESItemTiers.THERMAL_SPRINGSTONE, 1, -2.8F))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_AXE = registerItem("thermal_springstone_axe",
		() -> new AxeItem(ESItemTiers.THERMAL_SPRINGSTONE, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.THERMAL_SPRINGSTONE, 6, -3.1F))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_HOE = registerItem("thermal_springstone_hoe",
		() -> new HoeItem(ESItemTiers.THERMAL_SPRINGSTONE, new Item.Properties().attributes(HoeItem.createAttributes(ESItemTiers.THERMAL_SPRINGSTONE, -2, -1.0F))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_SHOVEL = registerItem("thermal_springstone_shovel",
		() -> new ShovelItem(ESItemTiers.THERMAL_SPRINGSTONE, new Item.Properties().attributes(ShovelItem.createAttributes(ESItemTiers.THERMAL_SPRINGSTONE, 1.5F, -3F))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_SCYTHE = registerItem("thermal_springstone_scythe",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.THERMAL_SPRINGSTONE, false, new Item.Properties().attributes(ScytheItem.createAttributes(ESItemTiers.THERMAL_SPRINGSTONE, 4, -2.7F, 0.5F, 0.25F))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_HAMMER = registerItem("thermal_springstone_hammer",
		() -> ESPlatform.INSTANCE.createHammer(ESItemTiers.THERMAL_SPRINGSTONE, () -> null, Holder.direct(SoundEvents.MACE_SMASH_GROUND_HEAVY), new Item.Properties().attributes(DiggerItem.createAttributes(ESItemTiers.THERMAL_SPRINGSTONE, 7, -3F))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_HELMET = registerItem("thermal_springstone_helmet",
		() -> ESPlatform.INSTANCE.createThermalSpringstoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_CHESTPLATE = registerItem("thermal_springstone_chestplate",
		() -> ESPlatform.INSTANCE.createThermalSpringstoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE.asHolder(), ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_LEGGINGS = registerItem("thermal_springstone_leggings",
		() -> ESPlatform.INSTANCE.createThermalSpringstoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE.asHolder(), ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
	public static final RegistryObject<Item, Item> THERMAL_SPRINGSTONE_BOOTS = registerItem("thermal_springstone_boots",
		() -> ESPlatform.INSTANCE.createThermalSpringstoneArmor(ESArmorMaterials.THERMAL_SPRINGSTONE.asHolder(), ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));

	// glacite
	public static final RegistryObject<Item, Item> GLACITE = registerItem("glacite", () -> new BlockItem(ESBlocks.GLACITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLACITE_SHARD = registerItem("glacite_shard", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> GLACITE_BLOCK = registerItem("glacite_block", () -> new BlockItem(ESBlocks.GLACITE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GLACITE_ARROW = registerItem("glacite_arrow", () -> new GlaciteArrowItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> GLACITE_SWORD = registerItem("glacite_sword",
		() -> new SwordItem(ESItemTiers.GLACITE, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.GLACITE, 3, -2.4F))));
	public static final RegistryObject<Item, Item> GLACITE_PICKAXE = registerItem("glacite_pickaxe",
		() -> new PickaxeItem(ESItemTiers.GLACITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ESItemTiers.GLACITE, 1, -2.8F))));
	public static final RegistryObject<Item, Item> GLACITE_AXE = registerItem("glacite_axe",
		() -> new AxeItem(ESItemTiers.GLACITE, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.GLACITE, 6, -3))));
	public static final RegistryObject<Item, Item> GLACITE_HOE = registerItem("glacite_hoe",
		() -> new HoeItem(ESItemTiers.GLACITE, new Item.Properties().attributes(HoeItem.createAttributes(ESItemTiers.GLACITE, -2.5F, 0))));
	public static final RegistryObject<Item, Item> GLACITE_SHOVEL = registerItem("glacite_shovel",
		() -> new ShovelItem(ESItemTiers.GLACITE, new Item.Properties().attributes(ShovelItem.createAttributes(ESItemTiers.GLACITE, 1.5F, -3F))));
	public static final RegistryObject<Item, Item> GLACITE_SCYTHE = registerItem("glacite_scythe",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.GLACITE, false, new Item.Properties().attributes(ScytheItem.createAttributes(ESItemTiers.GLACITE, 4, -2.7F, 0.5F, 0.3F))));
	public static final RegistryObject<Item, Item> GLACITE_HELMET = registerItem("glacite_helmet",
		() -> new GlaciteArmorItem(ESArmorMaterials.GLACITE.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
	public static final RegistryObject<Item, Item> GLACITE_CHESTPLATE = registerItem("glacite_chestplate",
		() -> new GlaciteArmorItem(ESArmorMaterials.GLACITE.asHolder(), ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15)).component(ESDataComponents.ACCESSORY_SLOT_COUNT.get(), 2)));
	public static final RegistryObject<Item, Item> GLACITE_LEGGINGS = registerItem("glacite_leggings",
		() -> new GlaciteArmorItem(ESArmorMaterials.GLACITE.asHolder(), ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
	public static final RegistryObject<Item, Item> GLACITE_BOOTS = registerItem("glacite_boots",
		() -> new GlaciteArmorItem(ESArmorMaterials.GLACITE.asHolder(), ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));
	public static final RegistryObject<Item, Item> GLACITE_SHIELD = registerItem("glacite_shield",
		() -> new GlaciteShieldItem(new Item.Properties().durability(500)));

	// starlit diamond
	public static final RegistryObject<Item, Item> GRIMSTONE_STARLIT_DIAMOND_ORE = registerItem("grimstone_starlit_diamond_ore", () -> new BlockItem(ESBlocks.GRIMSTONE_STARLIT_DIAMOND_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_STARLIT_DIAMOND_ORE = registerItem("voidstone_starlit_diamond_ore", () -> new BlockItem(ESBlocks.VOIDSTONE_STARLIT_DIAMOND_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_STARLIT_DIAMOND_ORE = registerItem("eternal_ice_starlit_diamond_ore", () -> new BlockItem(ESBlocks.ETERNAL_ICE_STARLIT_DIAMOND_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_STARLIT_DIAMOND_ORE = registerItem("haze_ice_starlit_diamond_ore", () -> new BlockItem(ESBlocks.HAZE_ICE_STARLIT_DIAMOND_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND = registerItem("starlit_diamond", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_BLOCK = registerItem("starlit_diamond_block", () -> new BlockItem(ESBlocks.STARLIT_DIAMOND_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_SWORD = registerItem("starlit_diamond_sword",
		() -> new SwordItem(ESItemTiers.STARLIT_DIAMOND, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.STARLIT_DIAMOND, 3, -2.6F))));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_PICKAXE = registerItem("starlit_diamond_pickaxe",
		() -> new PickaxeItem(ESItemTiers.STARLIT_DIAMOND, new Item.Properties().attributes(PickaxeItem.createAttributes(ESItemTiers.STARLIT_DIAMOND, 1, -3.0F))));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_AXE = registerItem("starlit_diamond_axe",
		() -> new AxeItem(ESItemTiers.STARLIT_DIAMOND, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.STARLIT_DIAMOND, 6, -3.2F))));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_HOE = registerItem("starlit_diamond_hoe",
		() -> new HoeItem(ESItemTiers.STARLIT_DIAMOND, new Item.Properties().attributes(HoeItem.createAttributes(ESItemTiers.STARLIT_DIAMOND, -4, -0.2F))));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_SHOVEL = registerItem("starlit_diamond_shovel",
		() -> new ShovelItem(ESItemTiers.STARLIT_DIAMOND, new Item.Properties().attributes(ShovelItem.createAttributes(ESItemTiers.STARLIT_DIAMOND, 1.5F, -3.2F))));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_HELMET = registerItem("starlit_diamond_helmet",
		() -> ESPlatform.INSTANCE.createStarlitDiamondArmor(ESArmorMaterials.STARLIT_DIAMOND.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(45))));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_CHESTPLATE = registerItem("starlit_diamond_chestplate",
		() -> ESPlatform.INSTANCE.createStarlitDiamondArmor(ESArmorMaterials.STARLIT_DIAMOND.asHolder(), ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(45)).component(ESDataComponents.ACCESSORY_SLOT_COUNT.get(), 3)));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_LEGGINGS = registerItem("starlit_diamond_leggings",
		() -> ESPlatform.INSTANCE.createStarlitDiamondArmor(ESArmorMaterials.STARLIT_DIAMOND.asHolder(), ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(45))));
	public static final RegistryObject<Item, Item> STARLIT_DIAMOND_BOOTS = registerItem("starlit_diamond_boots",
		() -> ESPlatform.INSTANCE.createStarlitDiamondArmor(ESArmorMaterials.STARLIT_DIAMOND.asHolder(), ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(45))));

	// deepsilver
	public static final RegistryObject<Item, Item> GRIMSTONE_DEEPSILVER_ORE = registerItem("grimstone_deepsilver_ore", () -> new BlockItem(ESBlocks.GRIMSTONE_DEEPSILVER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_DEEPSILVER_ORE = registerItem("voidstone_deepsilver_ore", () -> new BlockItem(ESBlocks.VOIDSTONE_DEEPSILVER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_DEEPSILVER_ORE = registerItem("eternal_ice_deepsilver_ore", () -> new BlockItem(ESBlocks.ETERNAL_ICE_DEEPSILVER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_DEEPSILVER_ORE = registerItem("haze_ice_deepsilver_ore", () -> new BlockItem(ESBlocks.HAZE_ICE_DEEPSILVER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_MUD_DEEPSILVER_ORE = registerItem("nightfall_mud_deepsilver_ore", () -> new BlockItem(ESBlocks.NIGHTFALL_MUD_DEEPSILVER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE = registerItem("packed_nightfall_mud_deepsilver_ore", () -> new BlockItem(ESBlocks.PACKED_NIGHTFALL_MUD_DEEPSILVER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RAW_DEEPSILVER = registerItem("raw_deepsilver", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> DEEPSILVER_INGOT = registerItem("deepsilver_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> DEEPSILVER_NUGGET = registerItem("deepsilver_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> RAW_DEEPSILVER_BLOCK = registerItem("raw_deepsilver_block", () -> new BlockItem(ESBlocks.RAW_DEEPSILVER_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEEPSILVER_BLOCK = registerItem("deepsilver_block", () -> new BlockItem(ESBlocks.DEEPSILVER_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEEPSILVER_GRATE = registerItem("deepsilver_grate", () -> new BlockItem(ESBlocks.DEEPSILVER_GRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEEPSILVER_BARS = registerItem("deepsilver_bars", () -> new BlockItem(ESBlocks.DEEPSILVER_BARS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DEEPSILVER_SWORD = registerItem("deepsilver_sword",
		() -> new SwordItem(ESItemTiers.DEEPSILVER, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.DEEPSILVER, 3, -2.4F))));
	public static final RegistryObject<Item, Item> DEEPSILVER_PICKAXE = registerItem("deepsilver_pickaxe",
		() -> new PickaxeItem(ESItemTiers.DEEPSILVER, new Item.Properties().attributes(PickaxeItem.createAttributes(ESItemTiers.DEEPSILVER, 1, -2.8F))));
	public static final RegistryObject<Item, Item> DEEPSILVER_AXE = registerItem("deepsilver_axe",
		() -> new AxeItem(ESItemTiers.DEEPSILVER, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.DEEPSILVER, 6, -3))));
	public static final RegistryObject<Item, Item> DEEPSILVER_HOE = registerItem("deepsilver_hoe",
		() -> new HoeItem(ESItemTiers.DEEPSILVER, new Item.Properties().attributes(HoeItem.createAttributes(ESItemTiers.DEEPSILVER, -2, -1.0F))));
	public static final RegistryObject<Item, Item> DEEPSILVER_SHOVEL = registerItem("deepsilver_shovel",
		() -> new ShovelItem(ESItemTiers.DEEPSILVER, new Item.Properties().attributes(ShovelItem.createAttributes(ESItemTiers.DEEPSILVER, 1.5F, -3F))));
	public static final RegistryObject<Item, Item> DEEPSILVER_SICKLE = registerItem("deepsilver_sickle",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.DEEPSILVER, true, new Item.Properties().attributes(DiggerItem.createAttributes(ESItemTiers.DEEPSILVER, 1, -1.5F))));
	public static final RegistryObject<Item, Item> DEEPSILVER_BRUSH = registerItem("deepsilver_brush", () -> new BrushItem(new Item.Properties().durability(256)));
	public static final RegistryObject<Item, Item> DEEPSILVER_HELMET = registerItem("deepsilver_helmet",
		() -> new DeepsilverArmorItem(ESArmorMaterials.DEEPSILVER.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(30))));
	public static final RegistryObject<Item, Item> DEEPSILVER_CHESTPLATE = registerItem("deepsilver_chestplate",
		() -> new DeepsilverArmorItem(ESArmorMaterials.DEEPSILVER.asHolder(), ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(30)).component(ESDataComponents.ACCESSORY_SLOT_COUNT.get(), 2)));
	public static final RegistryObject<Item, Item> DEEPSILVER_LEGGINGS = registerItem("deepsilver_leggings",
		() -> new DeepsilverArmorItem(ESArmorMaterials.DEEPSILVER.asHolder(), ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(30))));
	public static final RegistryObject<Item, Item> DEEPSILVER_BOOTS = registerItem("deepsilver_boots",
		() -> new DeepsilverArmorItem(ESArmorMaterials.DEEPSILVER.asHolder(), ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(30))));

	// unrealium
	public static final RegistryObject<Item, Item> UNREALIUM_INGOT = registerItem("unrealium_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> UNREALIUM_NUGGET = registerItem("unrealium_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> UNREALIUM_BLOCK = registerItem("unrealium_block", () -> new BlockItem(ESBlocks.UNREALIUM_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> UNREALIUM_BARS = registerItem("unrealium_bars", () -> new BlockItem(ESBlocks.UNREALIUM_BARS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> UNREALIUM_SWORD = registerItem("unrealium_sword",
		() -> new SwordItem(ESItemTiers.UNREALIUM, new Item.Properties().attributes(createUnrealiumAttributes(ESItemTiers.UNREALIUM, 3, -2.4F))));
	public static final RegistryObject<Item, Item> UNREALIUM_PICKAXE = registerItem("unrealium_pickaxe",
		() -> new PickaxeItem(ESItemTiers.UNREALIUM, new Item.Properties().attributes(createUnrealiumAttributes(ESItemTiers.UNREALIUM, 1, -2.8F))));
	public static final RegistryObject<Item, Item> UNREALIUM_AXE = registerItem("unrealium_axe",
		() -> new AxeItem(ESItemTiers.UNREALIUM, new Item.Properties().attributes(createUnrealiumAttributes(ESItemTiers.UNREALIUM, 6, -3))));
	public static final RegistryObject<Item, Item> UNREALIUM_HOE = registerItem("unrealium_hoe",
		() -> new HoeItem(ESItemTiers.UNREALIUM, new Item.Properties().attributes(createUnrealiumAttributes(ESItemTiers.UNREALIUM, -3, -1.0F))));
	public static final RegistryObject<Item, Item> UNREALIUM_SHOVEL = registerItem("unrealium_shovel",
		() -> new ShovelItem(ESItemTiers.UNREALIUM, new Item.Properties().attributes(createUnrealiumAttributes(ESItemTiers.UNREALIUM, 1.5F, -3F))));
	public static final RegistryObject<Item, Item> UNREALIUM_SICKLE = registerItem("unrealium_sickle",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.UNREALIUM, true, new Item.Properties().attributes(createUnrealiumAttributes(ESItemTiers.UNREALIUM, 1, -1.5F))));
	public static final RegistryObject<Item, Item> UNREALIUM_CROSSBOW = registerItem("unrealium_crossbow", () -> new UnrealiumCrossbowItem(new Item.Properties().durability(1200)));
	public static final RegistryObject<Item, Item> UNREALIUM_HELMET = registerItem("unrealium_helmet",
		() -> ESPlatform.INSTANCE.createUnrealiumArmor(ESArmorMaterials.UNREALIUM.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(50))));
	public static final RegistryObject<Item, Item> UNREALIUM_CHESTPLATE = registerItem("unrealium_chestplate",
		() -> ESPlatform.INSTANCE.createUnrealiumArmor(ESArmorMaterials.UNREALIUM.asHolder(), ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(50)).component(ESDataComponents.ACCESSORY_SLOT_COUNT.get(), 4)));
	public static final RegistryObject<Item, Item> UNREALIUM_LEGGINGS = registerItem("unrealium_leggings",
		() -> ESPlatform.INSTANCE.createUnrealiumArmor(ESArmorMaterials.UNREALIUM.asHolder(), ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(50))));
	public static final RegistryObject<Item, Item> UNREALIUM_BOOTS = registerItem("unrealium_boots",
		() -> ESPlatform.INSTANCE.createUnrealiumArmor(ESArmorMaterials.UNREALIUM.asHolder(), ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(50))));

	public static final RegistryObject<Item, Item> GRIMSTONE_MALARITE_ORE = registerItem("grimstone_malarite_ore", () -> new BlockItem(ESBlocks.GRIMSTONE_MALARITE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_MALARITE_ORE = registerItem("voidstone_malarite_ore", () -> new BlockItem(ESBlocks.VOIDSTONE_MALARITE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NIGHTFALL_MUD_MALARITE_ORE = registerItem("nightfall_mud_malarite_ore", () -> new BlockItem(ESBlocks.NIGHTFALL_MUD_MALARITE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PACKED_NIGHTFALL_MUD_MALARITE_ORE = registerItem("packed_nightfall_mud_malarite_ore", () -> new BlockItem(ESBlocks.PACKED_NIGHTFALL_MUD_MALARITE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MALARITE = registerItem("malarite", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> MALARITE_BLOCK = registerItem("malarite_block", () -> new BlockItem(ESBlocks.MALARITE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MALARITE_ARROW = registerItem("malarite_arrow", () -> new MalariteArrowItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> MALARITE_SWORD = registerItem("malarite_sword",
		() -> new SwordItem(ESItemTiers.MALARITE, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.MALARITE, 3, -2.4F))));
	public static final RegistryObject<Item, Item> MALARITE_PICKAXE = registerItem("malarite_pickaxe",
		() -> new PickaxeItem(ESItemTiers.MALARITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ESItemTiers.MALARITE, 1, -2.8F))));
	public static final RegistryObject<Item, Item> MALARITE_AXE = registerItem("malarite_axe",
		() -> new AxeItem(ESItemTiers.MALARITE, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.MALARITE, 6, -3))));
	public static final RegistryObject<Item, Item> MALARITE_HOE = registerItem("malarite_hoe",
		() -> new HoeItem(ESItemTiers.MALARITE, new Item.Properties().attributes(HoeItem.createAttributes(ESItemTiers.MALARITE, -2, 0))));
	public static final RegistryObject<Item, Item> MALARITE_SHOVEL = registerItem("malarite_shovel",
		() -> new ShovelItem(ESItemTiers.MALARITE, new Item.Properties().attributes(ShovelItem.createAttributes(ESItemTiers.MALARITE, 1.5F, -3F))));
	public static final RegistryObject<Item, Item> MALARITE_SICKLE = registerItem("malarite_sickle",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.MALARITE, true, new Item.Properties().attributes(DiggerItem.createAttributes(ESItemTiers.MALARITE, 1, -1.5F))));
	public static final RegistryObject<Item, Item> MALARITE_SPEAR = registerItem("malarite_spear",
		() -> new MalariteSpearItem(ESItemTiers.MALARITE, new Item.Properties().attributes(SpearItem.createAttributes(ESItemTiers.MALARITE, 3, -2.7F))));

	public static final RegistryObject<Item, Item> PUNGENCY_FRUIT_SEEDS = registerItem("pungency_fruit_seeds", () -> new PungencyFruitSeedsItem(ESBlocks.PUNGENCY_FRUIT_VINES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PUNGENCY_FRUIT = registerItem("pungency_fruit", () -> new Item(new Item.Properties().food(ESFoods.PUNGENCY_FRUIT.get())));
	public static final RegistryObject<Item, Item> PUNGENCY_STEW = registerItem("pungency_stew", () -> new Item(new Item.Properties().stacksTo(1).food(ESFoods.PUNGENCY_STEW.get())));
	public static final RegistryObject<Item, Item> SILVER_PUNGENCY_FRUIT = registerItem("silver_pungency_fruit", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ESFoods.SILVER_PUNGENCY_FRUIT.get())));
	public static final RegistryObject<Item, Item> TEAR_BOMB = registerItem("tear_bomb", () -> new BlockItem(ESBlocks.TEAR_BOMB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TEAR_BOMB_MINECART = registerItem("tear_bomb_minecart", () -> new TearBombMinecartItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> PUNGENCY_FRUIT_UPGRADE_SMITHING_TEMPLATE = registerItem("pungency_fruit_upgrade_smithing_template", () -> new SmithingTemplateItem(
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.pungency_fruit_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.pungency_fruit_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
		Component.translatable(Util.makeDescriptionId("upgrade", EternalStarlight.id("pungency_fruit_upgrade"))).withStyle(ChatFormatting.GRAY),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.pungency_fruit_upgrade.base_slot_description"))),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.pungency_fruit_upgrade.additions_slot_description"))),
		List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_axe")),
		List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_diamond"))
	));
	public static final RegistryObject<Item, Item> PUNGENCY_FRUIT_AXE = registerItem("pungency_fruit_axe",
		() -> new AxeItem(ESItemTiers.PUNGENCY_FRUIT, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.PUNGENCY_FRUIT, 6, -3))));
	public static final RegistryObject<Item, Item> PUNGENCY_FRUIT_SPEAR = registerItem("pungency_fruit_spear",
		() -> new PungencyFruitSpearItem(ESItemTiers.PUNGENCY_FRUIT, new Item.Properties().attributes(SpearItem.createAttributes(ESItemTiers.PUNGENCY_FRUIT, 3, -2.7F))));

	public static final RegistryObject<Item, Item> SEEDS_LAUNCHER = registerItem("seeds_launcher", () -> new SeedsLauncherItem(new Item.Properties().durability(500)));

	public static final RegistryObject<Item, Item> DRYING_RACK = registerItem("drying_rack", () -> new BlockItem(ESBlocks.DRYING_RACK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ROTTEN_FLESH_JERKY = registerItem("rotten_flesh_jerky", () -> new Item(new Item.Properties().food(ESFoods.ROTTEN_FLESH_JERKY.get())));

	public static final RegistryObject<Item, Item> STARFIRE_BIRD_NEST = registerItem("starfire_bird_nest", () -> new BlockItem(ESBlocks.STARFIRE_BIRD_NEST.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OAK_STARFIRE_BIRD_AVIARY = registerItem("oak_starfire_bird_aviary", () -> new BlockItem(ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SPRUCE_STARFIRE_BIRD_AVIARY = registerItem("spruce_starfire_bird_aviary", () -> new BlockItem(ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BIRCH_STARFIRE_BIRD_AVIARY = registerItem("birch_starfire_bird_aviary", () -> new BlockItem(ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ACACIA_STARFIRE_BIRD_AVIARY = registerItem("acacia_starfire_bird_aviary", () -> new BlockItem(ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHERRY_STARFIRE_BIRD_AVIARY = registerItem("cherry_starfire_bird_aviary", () -> new BlockItem(ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JUNGLE_STARFIRE_BIRD_AVIARY = registerItem("jungle_starfire_bird_aviary", () -> new BlockItem(ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DARK_OAK_STARFIRE_BIRD_AVIARY = registerItem("dark_oak_starfire_bird_aviary", () -> new BlockItem(ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRIMSON_STARFIRE_BIRD_AVIARY = registerItem("crimson_starfire_bird_aviary", () -> new BlockItem(ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WARPED_STARFIRE_BIRD_AVIARY = registerItem("warped_starfire_bird_aviary", () -> new BlockItem(ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MANGROVE_STARFIRE_BIRD_AVIARY = registerItem("mangrove_starfire_bird_aviary", () -> new BlockItem(ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BAMBOO_STARFIRE_BIRD_AVIARY = registerItem("bamboo_starfire_bird_aviary", () -> new BlockItem(ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_STARFIRE_BIRD_AVIARY = registerItem("lunar_starfire_bird_aviary", () -> new BlockItem(ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> NORTHLAND_STARFIRE_BIRD_AVIARY = registerItem("northland_starfire_bird_aviary", () -> new BlockItem(ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BANYIN_STARFIRE_BIRD_AVIARY = registerItem("banyin_starfire_bird_aviary", () -> new BlockItem(ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SCARLET_STARFIRE_BIRD_AVIARY = registerItem("scarlet_starfire_bird_aviary", () -> new BlockItem(ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_STARFIRE_BIRD_AVIARY = registerItem("torreya_starfire_bird_aviary", () -> new BlockItem(ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> JINGLESTEM_STARFIRE_BIRD_AVIARY = registerItem("jinglestem_starfire_bird_aviary", () -> new BlockItem(ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRADLEWOOD_STARFIRE_BIRD_AVIARY = registerItem("cradlewood_starfire_bird_aviary", () -> new BlockItem(ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STARFIRE_BIRD_EGG = registerItem("starfire_bird_egg", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item, Item> STARFIRE = registerItem("starfire", () -> new StarfireItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> STARFIRE_UPGRADE_SMITHING_TEMPLATE = registerItem("starfire_upgrade_smithing_template", () -> new SmithingTemplateItem(
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.starfire_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.starfire_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
		Component.translatable(Util.makeDescriptionId("upgrade", EternalStarlight.id("starfire_upgrade"))).withStyle(ChatFormatting.GRAY),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.starfire_upgrade.base_slot_description"))),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.starfire_upgrade.additions_slot_description"))),
		List.of(
			ResourceLocation.withDefaultNamespace("item/empty_slot_sword"),
			ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe"),
			ResourceLocation.withDefaultNamespace("item/empty_slot_axe"),
			ResourceLocation.withDefaultNamespace("item/empty_slot_hoe"),
			ResourceLocation.withDefaultNamespace("item/empty_slot_shovel")
		),
		List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_diamond"))
	));
	public static final RegistryObject<Item, Item> STARFIRE_SWORD = registerItem("starfire_sword",
		() -> new SwordItem(ESItemTiers.STARFIRE, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.STARFIRE, 3, -2.4F))));
	public static final RegistryObject<Item, Item> STARFIRE_PICKAXE = registerItem("starfire_pickaxe",
		() -> new PickaxeItem(ESItemTiers.STARFIRE, new Item.Properties().attributes(PickaxeItem.createAttributes(ESItemTiers.STARFIRE, 1, -2.8F))));
	public static final RegistryObject<Item, Item> STARFIRE_AXE = registerItem("starfire_axe",
		() -> new AxeItem(ESItemTiers.STARFIRE, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.STARFIRE, 6, -3))));
	public static final RegistryObject<Item, Item> STARFIRE_HOE = registerItem("starfire_hoe",
		() -> new HoeItem(ESItemTiers.STARFIRE, new Item.Properties().attributes(HoeItem.createAttributes(ESItemTiers.STARFIRE, -2.5F, -1.0F))));
	public static final RegistryObject<Item, Item> STARFIRE_SHOVEL = registerItem("starfire_shovel",
		() -> new ShovelItem(ESItemTiers.STARFIRE, new Item.Properties().attributes(ShovelItem.createAttributes(ESItemTiers.STARFIRE, 1.5F, -3F))));
	public static final RegistryObject<Item, Item> STARFIRE_SCYTHE = registerItem("starfire_scythe",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.STARFIRE, false, new Item.Properties().attributes(ScytheItem.createAttributes(ESItemTiers.STARFIRE, 4, -2.7F, 0.5F, 0.25F))));
	public static final RegistryObject<Item, Item> STARFIRE_HAMMER = registerItem("starfire_hammer",
		() -> ESPlatform.INSTANCE.createHammer(ESItemTiers.STARFIRE, ESParticles.STARFIRE_EXPLOSION::get, ESSoundEvents.STARFIRE_WHOOSH.asHolder(), new Item.Properties().attributes(DiggerItem.createAttributes(ESItemTiers.STARFIRE, 7, -3F))));
	public static final RegistryObject<Item, Item> STARFIRE_CROSSBOW = registerItem("starfire_crossbow", () -> new StarfireCrossbowItem(new Item.Properties().durability(875)));

	public static final RegistryObject<Item, Item> RAW_FLOWGLAZE = registerItem("raw_flowglaze", () -> new BlockItem(ESBlocks.RAW_FLOWGLAZE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLOWGLAZE = registerItem("flowglaze", () -> new BlockItem(ESBlocks.FLOWGLAZE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLOWGLAZE_PANE = registerItem("flowglaze_pane", () -> new BlockItem(ESBlocks.FLOWGLAZE_PANE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLOWGLAZE_BRICKS = registerItem("flowglaze_bricks", () -> new BlockItem(ESBlocks.FLOWGLAZE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLOWGLAZE_BRICK_SLAB = registerItem("flowglaze_brick_slab", () -> new BlockItem(ESBlocks.FLOWGLAZE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLOWGLAZE_BRICK_STAIRS = registerItem("flowglaze_brick_stairs", () -> new BlockItem(ESBlocks.FLOWGLAZE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLOWGLAZE_BRICK_WALL = registerItem("flowglaze_brick_wall", () -> new BlockItem(ESBlocks.FLOWGLAZE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLOWGLAZE_UPGRADE_SMITHING_TEMPLATE = registerItem("flowglaze_upgrade_smithing_template", () -> new SmithingTemplateItem(
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.flowglaze_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.flowglaze_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
		Component.translatable(Util.makeDescriptionId("upgrade", EternalStarlight.id("flowglaze_upgrade"))).withStyle(ChatFormatting.GRAY),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.flowglaze_upgrade.base_slot_description"))),
		Component.translatable(Util.makeDescriptionId("item", EternalStarlight.id("smithing_template.flowglaze_upgrade.additions_slot_description"))),
		List.of(
			ResourceLocation.withDefaultNamespace("item/empty_slot_sword"),
			ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe"),
			ResourceLocation.withDefaultNamespace("item/empty_slot_axe"),
			ResourceLocation.withDefaultNamespace("item/empty_slot_hoe"),
			ResourceLocation.withDefaultNamespace("item/empty_slot_shovel")
		),
		List.of(ResourceLocation.withDefaultNamespace("item/empty_slot_diamond"))
	));
	public static final RegistryObject<Item, Item> FLOWGLAZE_SWORD = registerItem("flowglaze_sword",
		() -> new SwordItem(ESItemTiers.FLOWGLAZE, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.FLOWGLAZE, 3, -2.4F))));
	public static final RegistryObject<Item, Item> FLOWGLAZE_PICKAXE = registerItem("flowglaze_pickaxe",
		() -> new PickaxeItem(ESItemTiers.FLOWGLAZE, new Item.Properties().attributes(PickaxeItem.createAttributes(ESItemTiers.FLOWGLAZE, 1, -2.8F))));
	public static final RegistryObject<Item, Item> FLOWGLAZE_AXE = registerItem("flowglaze_axe",
		() -> new AxeItem(ESItemTiers.FLOWGLAZE, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.FLOWGLAZE, 6, -3))));
	public static final RegistryObject<Item, Item> FLOWGLAZE_HOE = registerItem("flowglaze_hoe",
		() -> new HoeItem(ESItemTiers.FLOWGLAZE, new Item.Properties().attributes(HoeItem.createAttributes(ESItemTiers.FLOWGLAZE, -2.5F, -1.0F))));
	public static final RegistryObject<Item, Item> FLOWGLAZE_SHOVEL = registerItem("flowglaze_shovel",
		() -> new ShovelItem(ESItemTiers.FLOWGLAZE, new Item.Properties().attributes(ShovelItem.createAttributes(ESItemTiers.FLOWGLAZE, 1.5F, -3F))));
	public static final RegistryObject<Item, Item> FLOWGLAZE_SCYTHE = registerItem("flowglaze_scythe",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.FLOWGLAZE, false, new Item.Properties().attributes(ScytheItem.createAttributes(ESItemTiers.FLOWGLAZE, 4, -2.7F, 0.5F, 0.3F))));
	public static final RegistryObject<Item, Item> FLOWGLAZE_BOW = registerItem("flowglaze_bow", () -> new FlowglazeBowItem(new Item.Properties().durability(875)));
	public static final RegistryObject<Item, Item> FLOWGLAZE_SHIELD = registerItem("flowglaze_shield",
		() -> new FlowglazeShieldItem(new Item.Properties().durability(1000)));

	public static final RegistryObject<Item, Item> GRIMSTONE_SALTPETER_ORE = registerItem("grimstone_saltpeter_ore", () -> new BlockItem(ESBlocks.GRIMSTONE_SALTPETER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_SALTPETER_ORE = registerItem("voidstone_saltpeter_ore", () -> new BlockItem(ESBlocks.VOIDSTONE_SALTPETER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_SALTPETER_ORE = registerItem("eternal_ice_saltpeter_ore", () -> new BlockItem(ESBlocks.ETERNAL_ICE_SALTPETER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_SALTPETER_ORE = registerItem("haze_ice_saltpeter_ore", () -> new BlockItem(ESBlocks.HAZE_ICE_SALTPETER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SALTPETER_BLOCK = registerItem("saltpeter_block", () -> new BlockItem(ESBlocks.SALTPETER_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SALTPETER_POWDER = registerItem("saltpeter_powder", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> SALTPETER_MATCHBOX = registerItem("saltpeter_matchbox", () -> new FlintAndSteelItem(new Item.Properties().durability(20)));

	public static final RegistryObject<Item, Item> RAW_AMARAMBER = registerItem("raw_amaramber", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_INGOT = registerItem("amaramber_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_NUGGET = registerItem("amaramber_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> RAW_AMARAMBER_BLOCK = registerItem("raw_amaramber_block", () -> new BlockItem(ESBlocks.RAW_AMARAMBER_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_LANTERN = registerItem("amaramber_lantern", () -> new BlockItem(ESBlocks.AMARAMBER_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_CANDLE = registerItem("amaramber_candle", () -> new BlockItem(ESBlocks.AMARAMBER_CANDLE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_BRICKS = registerItem("amaramber_bricks", () -> new BlockItem(ESBlocks.AMARAMBER_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_BRICK_SLAB = registerItem("amaramber_brick_slab", () -> new BlockItem(ESBlocks.AMARAMBER_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_BRICK_STAIRS = registerItem("amaramber_brick_stairs", () -> new BlockItem(ESBlocks.AMARAMBER_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_BRICK_WALL = registerItem("amaramber_brick_wall", () -> new BlockItem(ESBlocks.AMARAMBER_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_TILES = registerItem("torreya_tiles", () -> new BlockItem(ESBlocks.TORREYA_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_TILE_SLAB = registerItem("torreya_tile_slab", () -> new BlockItem(ESBlocks.TORREYA_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_TILE_STAIRS = registerItem("torreya_tile_stairs", () -> new BlockItem(ESBlocks.TORREYA_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TORREYA_TILE_WALL = registerItem("torreya_tile_wall", () -> new BlockItem(ESBlocks.TORREYA_TILE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_ARROW = registerItem("amaramber_arrow", () -> new AmaramberArrowItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> AMARAMBER_SWORD = registerItem("amaramber_sword",
		() -> new SwordItem(ESItemTiers.AMARAMBER, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.AMARAMBER, 3, -2.4F))));
	public static final RegistryObject<Item, Item> AMARAMBER_PICKAXE = registerItem("amaramber_pickaxe",
		() -> new PickaxeItem(ESItemTiers.AMARAMBER, new Item.Properties().attributes(PickaxeItem.createAttributes(ESItemTiers.AMARAMBER, 1, -2.8F))));
	public static final RegistryObject<Item, Item> AMARAMBER_AXE = registerItem("amaramber_axe",
		() -> new AxeItem(ESItemTiers.AMARAMBER, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.AMARAMBER, 6, -3.2F))));
	public static final RegistryObject<Item, Item> AMARAMBER_HOE = registerItem("amaramber_hoe",
		() -> new HoeItem(ESItemTiers.AMARAMBER, new Item.Properties().attributes(HoeItem.createAttributes(ESItemTiers.AMARAMBER, -2, -3.0F))));
	public static final RegistryObject<Item, Item> AMARAMBER_SHOVEL = registerItem("amaramber_shovel",
		() -> new ShovelItem(ESItemTiers.AMARAMBER, new Item.Properties().attributes(ShovelItem.createAttributes(ESItemTiers.AMARAMBER, 1.5F, -3F))));
	public static final RegistryObject<Item, Item> AMARAMBER_SICKLE = registerItem("amaramber_sickle",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.AMARAMBER, true, new Item.Properties().attributes(DiggerItem.createAttributes(ESItemTiers.AMARAMBER, 1, -1.5F))));
	public static final RegistryObject<Item, Item> CANDLASH = registerItem("candlash", () -> new CandlashItem(ESItemTiers.AMARAMBER, new Item.Properties().attributes(WhipItem.createAttributes(ESItemTiers.AMARAMBER, 2.8F, -3.0F))));
	public static final RegistryObject<Item, Item> AMARAMBER_MASK = registerItem("amaramber_mask",
		() -> new ArmorItem(ESArmorMaterials.AMARAMBER.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
	public static final RegistryObject<Item, Item> AMARAMBER_CHESTPLATE = registerItem("amaramber_chestplate",
		() -> new ArmorItem(ESArmorMaterials.AMARAMBER.asHolder(), ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));

	// ether
	public static final RegistryObject<Item, Item> ETHER_BUCKET = registerItem("ether_bucket", () -> new BucketItem(ESFluids.ETHER_STILL.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item, Item> THIOQUARTZ_BLOCK = registerItem("thioquartz_block", () -> new BlockItem(ESBlocks.THIOQUARTZ_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BUDDING_THIOQUARTZ = registerItem("budding_thioquartz", () -> new BlockItem(ESBlocks.BUDDING_THIOQUARTZ.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THIOQUARTZ_CLUSTER = registerItem("thioquartz_cluster", () -> new BlockItem(ESBlocks.THIOQUARTZ_CLUSTER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> THIOQUARTZ_SHARD = registerItem("thioquartz_shard", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> THIOQUARTZ_ARROW = registerItem("thioquartz_arrow", () -> new ThioquartzArrowItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> ETHERIC_EYE = registerItem("etheric_eye", () -> new EthericEyeItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> TOXITE = registerItem("toxite", () -> new BlockItem(ESBlocks.TOXITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOXITE_SLAB = registerItem("toxite_slab", () -> new BlockItem(ESBlocks.TOXITE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOXITE_STAIRS = registerItem("toxite_stairs", () -> new BlockItem(ESBlocks.TOXITE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOXITE_WALL = registerItem("toxite_wall", () -> new BlockItem(ESBlocks.TOXITE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOXITE_BRICKS = registerItem("toxite_bricks", () -> new BlockItem(ESBlocks.TOXITE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOXITE_BRICK_SLAB = registerItem("toxite_brick_slab", () -> new BlockItem(ESBlocks.TOXITE_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOXITE_BRICK_STAIRS = registerItem("toxite_brick_stairs", () -> new BlockItem(ESBlocks.TOXITE_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOXITE_BRICK_WALL = registerItem("toxite_brick_wall", () -> new BlockItem(ESBlocks.TOXITE_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_TOXITE = registerItem("polished_toxite", () -> new BlockItem(ESBlocks.POLISHED_TOXITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_TOXITE_SLAB = registerItem("polished_toxite_slab", () -> new BlockItem(ESBlocks.POLISHED_TOXITE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_TOXITE_STAIRS = registerItem("polished_toxite_stairs", () -> new BlockItem(ESBlocks.POLISHED_TOXITE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_TOXITE_WALL = registerItem("polished_toxite_wall", () -> new BlockItem(ESBlocks.POLISHED_TOXITE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_TOXITE = registerItem("chiseled_toxite", () -> new BlockItem(ESBlocks.CHISELED_TOXITE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ALCHEMIST_MASK = registerItem("alchemist_mask",
		() -> ESPlatform.INSTANCE.createAlchemistArmor(ESArmorMaterials.ALCHEMIST.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))));
	public static final RegistryObject<Item, Item> ALCHEMIST_ROBE = registerItem("alchemist_robe",
		() -> ESPlatform.INSTANCE.createAlchemistArmor(ESArmorMaterials.ALCHEMIST.asHolder(), ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10)).component(ESDataComponents.ACCESSORY_SLOT_COUNT.get(), 3)));

	public static final RegistryObject<Item, Item> GRIMSTONE_REDSTONE_ORE = registerItem("grimstone_redstone_ore", () -> new BlockItem(ESBlocks.GRIMSTONE_REDSTONE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> VOIDSTONE_REDSTONE_ORE = registerItem("voidstone_redstone_ore", () -> new BlockItem(ESBlocks.VOIDSTONE_REDSTONE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ETERNAL_ICE_REDSTONE_ORE = registerItem("eternal_ice_redstone_ore", () -> new BlockItem(ESBlocks.ETERNAL_ICE_REDSTONE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> HAZE_ICE_REDSTONE_ORE = registerItem("haze_ice_redstone_ore", () -> new BlockItem(ESBlocks.HAZE_ICE_REDSTONE_ORE.get(), new Item.Properties()));

	// mob stuff
	public static final RegistryObject<Item, Item> ROOKFISH_BUCKET = registerItem("rookfish_bucket", () -> new MobBucketItem(ESEntities.ROOKFISH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
	public static final RegistryObject<Item, Item> ROOKFISH = registerItem("rookfish", () -> new Item(new Item.Properties().food(ESFoods.ROOKFISH.get())));
	public static final RegistryObject<Item, Item> COOKED_ROOKFISH = registerItem("cooked_rookfish", () -> new Item(new Item.Properties().food(ESFoods.COOKED_ROOKFISH.get())));
	public static final RegistryObject<Item, Item> ROOKFISH_SKEWER = registerItem("rookfish_skewer", () -> new Item(new Item.Properties().food(ESFoods.ROOKFISH_SKEWER.get())));
	public static final RegistryObject<Item, Item> ROOKFISH_AIR_SAC = registerItem("rookfish_air_sac", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> AIR_SAC_ARROW = registerItem("air_sac_arrow", () -> new AirSacArrowItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> AIR_SAC_MASK = registerItem("air_sac_mask",
		() -> new ArmorItem(ESArmorMaterials.AIR_SAC.asHolder(), ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
	public static final RegistryObject<Item, Item> AIR_SAC_BOOTS = registerItem("air_sac_boots",
		() -> new AirSacArmorItem(ESArmorMaterials.AIR_SAC.asHolder(), ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));

	public static final RegistryObject<Item, Item> LUMINOFISH_BUCKET = registerItem("luminofish_bucket", () -> new MobBucketItem(ESEntities.LUMINOFISH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
	public static final RegistryObject<Item, Item> LUMINOFISH = registerItem("luminofish", () -> new Item(new Item.Properties().food(ESFoods.LUMINOFISH.get())));
	public static final RegistryObject<Item, Item> COOKED_LUMINOFISH = registerItem("cooked_luminofish", () -> new Item(new Item.Properties().food(ESFoods.COOKED_LUMINOFISH.get())));
	public static final RegistryObject<Item, Item> LUMINARIS_BUCKET = registerItem("luminaris_bucket", () -> new MobBucketItem(ESEntities.LUMINARIS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));
	public static final RegistryObject<Item, Item> LUMINARIS = registerItem("luminaris", () -> new Item(new Item.Properties().food(ESFoods.LUMINARIS.get())));
	public static final RegistryObject<Item, Item> COOKED_LUMINARIS = registerItem("cooked_luminaris", () -> new Item(new Item.Properties().food(ESFoods.COOKED_LUMINARIS.get())));

	public static final RegistryObject<Item, Item> STARLIT_PAINTING = registerItem("starlit_painting", () -> new ESPaintingItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> WHITE_YETI_FUR = registerItem("white_yeti_fur", () -> new BlockItem(ESBlocks.WHITE_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ORANGE_YETI_FUR = registerItem("orange_yeti_fur", () -> new BlockItem(ESBlocks.ORANGE_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MAGENTA_YETI_FUR = registerItem("magenta_yeti_fur", () -> new BlockItem(ESBlocks.MAGENTA_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LIGHT_BLUE_YETI_FUR = registerItem("light_blue_yeti_fur", () -> new BlockItem(ESBlocks.LIGHT_BLUE_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> YELLOW_YETI_FUR = registerItem("yellow_yeti_fur", () -> new BlockItem(ESBlocks.YELLOW_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LIME_YETI_FUR = registerItem("lime_yeti_fur", () -> new BlockItem(ESBlocks.LIME_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PINK_YETI_FUR = registerItem("pink_yeti_fur", () -> new BlockItem(ESBlocks.PINK_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRAY_YETI_FUR = registerItem("gray_yeti_fur", () -> new BlockItem(ESBlocks.GRAY_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LIGHT_GRAY_YETI_FUR = registerItem("light_gray_yeti_fur", () -> new BlockItem(ESBlocks.LIGHT_GRAY_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CYAN_YETI_FUR = registerItem("cyan_yeti_fur", () -> new BlockItem(ESBlocks.CYAN_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PURPLE_YETI_FUR = registerItem("purple_yeti_fur", () -> new BlockItem(ESBlocks.PURPLE_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_YETI_FUR = registerItem("blue_yeti_fur", () -> new BlockItem(ESBlocks.BLUE_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BROWN_YETI_FUR = registerItem("brown_yeti_fur", () -> new BlockItem(ESBlocks.BROWN_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GREEN_YETI_FUR = registerItem("green_yeti_fur", () -> new BlockItem(ESBlocks.GREEN_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_YETI_FUR = registerItem("red_yeti_fur", () -> new BlockItem(ESBlocks.RED_YETI_FUR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLACK_YETI_FUR = registerItem("black_yeti_fur", () -> new BlockItem(ESBlocks.BLACK_YETI_FUR.get(), new Item.Properties()));

	public static final RegistryObject<Item, Item> WHITE_YETI_FUR_CARPET = registerItem("white_yeti_fur_carpet", () -> new BlockItem(ESBlocks.WHITE_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ORANGE_YETI_FUR_CARPET = registerItem("orange_yeti_fur_carpet", () -> new BlockItem(ESBlocks.ORANGE_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MAGENTA_YETI_FUR_CARPET = registerItem("magenta_yeti_fur_carpet", () -> new BlockItem(ESBlocks.MAGENTA_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LIGHT_BLUE_YETI_FUR_CARPET = registerItem("light_blue_yeti_fur_carpet", () -> new BlockItem(ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> YELLOW_YETI_FUR_CARPET = registerItem("yellow_yeti_fur_carpet", () -> new BlockItem(ESBlocks.YELLOW_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LIME_YETI_FUR_CARPET = registerItem("lime_yeti_fur_carpet", () -> new BlockItem(ESBlocks.LIME_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PINK_YETI_FUR_CARPET = registerItem("pink_yeti_fur_carpet", () -> new BlockItem(ESBlocks.PINK_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GRAY_YETI_FUR_CARPET = registerItem("gray_yeti_fur_carpet", () -> new BlockItem(ESBlocks.GRAY_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LIGHT_GRAY_YETI_FUR_CARPET = registerItem("light_gray_yeti_fur_carpet", () -> new BlockItem(ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CYAN_YETI_FUR_CARPET = registerItem("cyan_yeti_fur_carpet", () -> new BlockItem(ESBlocks.CYAN_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PURPLE_YETI_FUR_CARPET = registerItem("purple_yeti_fur_carpet", () -> new BlockItem(ESBlocks.PURPLE_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLUE_YETI_FUR_CARPET = registerItem("blue_yeti_fur_carpet", () -> new BlockItem(ESBlocks.BLUE_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BROWN_YETI_FUR_CARPET = registerItem("brown_yeti_fur_carpet", () -> new BlockItem(ESBlocks.BROWN_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GREEN_YETI_FUR_CARPET = registerItem("green_yeti_fur_carpet", () -> new BlockItem(ESBlocks.GREEN_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> RED_YETI_FUR_CARPET = registerItem("red_yeti_fur_carpet", () -> new BlockItem(ESBlocks.RED_YETI_FUR_CARPET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLACK_YETI_FUR_CARPET = registerItem("black_yeti_fur_carpet", () -> new BlockItem(ESBlocks.BLACK_YETI_FUR_CARPET.get(), new Item.Properties()));

	public static final RegistryObject<Item, Item> AURORA_DEER_ANTLER = registerItem("aurora_deer_antler",
		() -> ESPlatform.INSTANCE.createScythe(ESItemTiers.AURORA_DEER_ANTLER, true, new Item.Properties().attributes(ScytheItem.createAttributes(ESItemTiers.AURORA_DEER_ANTLER, 2, -1F, 0.5F, 0))));
	public static final RegistryObject<Item, Item> AURORA_DEER_STEAK = registerItem("aurora_deer_steak", () -> new Item(new Item.Properties().food(ESFoods.AURORA_DEER_STEAK.get())));
	public static final RegistryObject<Item, Item> COOKED_AURORA_DEER_STEAK = registerItem("cooked_aurora_deer_steak", () -> new Item(new Item.Properties().food(ESFoods.COOKED_AURORA_DEER_STEAK.get())));

	public static final RegistryObject<Item, Item> RATLIN_MEAT = registerItem("ratlin_meat", () -> new Item(new Item.Properties().food(ESFoods.RATLIN_MEAT.get())));
	public static final RegistryObject<Item, Item> COOKED_RATLIN_MEAT = registerItem("cooked_ratlin_meat", () -> new Item(new Item.Properties().food(ESFoods.COOKED_RATLIN_MEAT.get())));

	public static final RegistryObject<Item, Item> SHADOW_SNAIL_MEAT = registerItem("shadow_snail_meat", () -> new Item(new Item.Properties().food(ESFoods.SHADOW_SNAIL_MEAT.get())));
	public static final RegistryObject<Item, Item> COOKED_SHADOW_SNAIL_MEAT = registerItem("cooked_shadow_snail_meat", () -> new Item(new Item.Properties().food(ESFoods.COOKED_SHADOW_SNAIL_MEAT.get())));
	public static final RegistryObject<Item, Item> SHADOW_SNAIL_SHELL = registerItem("shadow_snail_shell", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> SHADOW_SNAIL_SHELL_POWDER = registerItem("shadow_snail_shell_powder", () -> new BoneMealItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> SHADOW_SNAIL_PIE = registerItem("shadow_snail_pie", () -> new Item(new Item.Properties().food(ESFoods.SHADOW_SNAIL_PIE.get())));
	public static final RegistryObject<Item, Item> SHADOW_ESCARGOT = registerItem("shadow_escargot", () -> new Item(new Item.Properties().food(ESFoods.SHADOW_ESCARGOT.get())));

	public static final RegistryObject<Item, Item> FROZEN_TUBE = registerItem("frozen_tube", () -> new FrozenTubeItem(new Item.Properties()));

	public static final RegistryObject<Item, Item> SHIVERING_GEL = registerItem("shivering_gel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> SONAR_BOMB = registerItem("sonar_bomb", () -> new SonarBombItem(new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item, Item> NIGHTFALL_SPIDER_EYE = registerItem("nightfall_spider_eye", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item, Item> SEEKER_TENTACLE = registerItem("seeker_tentacle", () -> new Item(new Item.Properties().food(ESFoods.SEEKER_TENTACLE.get())));
	public static final RegistryObject<Item, Item> TENTACLE_SPIKE = registerItem("tentacle_spike", () -> new TentacleSpikeItem(ESItemTiers.TENTACLE_SPIKE, new Item.Properties().attributes(WhipItem.createAttributes(ESItemTiers.TENTACLE_SPIKE, 2.8F, -3.0F))));

	public static final RegistryObject<Item, Item> TANGLED_SKULL = registerItem("tangled_skull", () -> new TangledSkullItem(ESBlocks.TANGLED_SKULL.get(), ESBlocks.TANGLED_WALL_SKULL.get(), new Item.Properties().rarity(Rarity.UNCOMMON), Direction.DOWN));

	public static final RegistryObject<Item, Item> GLEECH_EGG = registerItem("gleech_egg", () -> new GleechEggItem(new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item, Item> SHATTERED_SWORD = registerItem("shattered_sword", () -> new ShatteredSwordItem(ESItemTiers.DEEPSILVER, new Item.Properties().rarity(Rarity.RARE).attributes(SwordItem.createAttributes(ESItemTiers.DEEPSILVER, 3, -2.4F))));
	public static final RegistryObject<Item, Item> SHATTERED_SWORD_BLADE = registerItem("shattered_sword_blade", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

	public static final RegistryObject<Item, Item> TOOTH_OF_HUNGER = registerItem("tooth_of_hunger", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> DAGGER_OF_HUNGER = registerItem("dagger_of_hunger", () -> new DaggerOfHungerItem(ESItemTiers.TOOTH_OF_HUNGER, new Item.Properties().rarity(Rarity.RARE).attributes(DaggerOfHungerItem.DEFAULT_ATTRIBUTE)));
	public static final RegistryObject<Item, Item> VORACIOUS_ARROW = registerItem("voracious_arrow", () -> new VoraciousArrowItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> TOOTH_OF_HUNGER_TILES = registerItem("tooth_of_hunger_tiles", () -> new BlockItem(ESBlocks.TOOTH_OF_HUNGER_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOOTH_OF_HUNGER_TILE_SLAB = registerItem("tooth_of_hunger_tile_slab", () -> new BlockItem(ESBlocks.TOOTH_OF_HUNGER_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOOTH_OF_HUNGER_TILE_STAIRS = registerItem("tooth_of_hunger_tile_stairs", () -> new BlockItem(ESBlocks.TOOTH_OF_HUNGER_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> TOOTH_OF_HUNGER_TILE_WALL = registerItem("tooth_of_hunger_tile_wall", () -> new BlockItem(ESBlocks.TOOTH_OF_HUNGER_TILE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_TOOTH_OF_HUNGER_TILES = registerItem("chiseled_tooth_of_hunger_tiles", () -> new BlockItem(ESBlocks.CHISELED_TOOTH_OF_HUNGER_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRYSTALBORN_CATALYST = registerItem("crystalborn_catalyst", () -> new BlockItem(ESBlocks.CRYSTALBORN_CATALYST.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CRYSTALLIZED_SAND = registerItem("crystallized_sand", () -> new BlockItem(ESBlocks.CRYSTALLIZED_SAND.get(), new Item.Properties()));

	// accessories
	public static final RegistryObject<Item, Item> BATTLEAXE_PENDANT = registerItem("battleaxe_pendant", () -> new Item(new Item.Properties()
		.component(ESDataComponents.ACCESSORY.get(), new Accessory(
			ESTags.Items.AXE_ACCESSORY_APPLICABLE,
			Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_combination_target.axe").withStyle(ChatFormatting.BLUE),
			ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(EternalStarlight.id("battleaxe_pendant_attack_speed"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
				.build(),
			List.of(
				Component.translatable("tooltip." + EternalStarlight.ID + ".battleaxe_pendant.durability").withStyle(ChatFormatting.BLUE),
				Component.translatable("tooltip." + EternalStarlight.ID + ".battleaxe_pendant.strip").withStyle(ChatFormatting.BLUE)
			),
			Optional.of(Style.EMPTY.withColor(0x415548)),
			Optional.of(EternalStarlight.id("textures/accessory/battleaxe_pendant_overlay"))
		))
		.stacksTo(1)));
	public static final RegistryObject<Item, Item> WARHAMMER_PENDANT = registerItem("warhammer_pendant", () -> new Item(new Item.Properties()
		.component(ESDataComponents.ACCESSORY.get(), new Accessory(
			ESTags.Items.HAMMER_ACCESSORY_APPLICABLE,
			Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_combination_target.hammer").withStyle(ChatFormatting.BLUE),
			ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(EternalStarlight.id("warhammer_pendant_attack_speed"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
				.build(),
			List.of(
				Component.translatable("tooltip." + EternalStarlight.ID + ".warhammer_pendant.speed_based_damage").withStyle(ChatFormatting.BLUE)
			),
			Optional.of(Style.EMPTY.withColor(0x8797b8)),
			Optional.of(EternalStarlight.id("textures/accessory/warhammer_pendant_overlay"))
		))
		.stacksTo(1)));
	public static final RegistryObject<Item, Item> BUTTERFLY_WINGS_AMULET = registerItem("butterfly_wings_amulet", () -> new Item(new Item.Properties()
		.component(ESDataComponents.ACCESSORY.get(), new Accessory(
			ESTags.Items.CHEST_ARMOR_ACCESSORY_APPLICABLE,
			Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_combination_target.chestplate").withStyle(ChatFormatting.BLUE),
			ItemAttributeModifiers.builder()
				.add(ESAttributes.ETHER_RESISTANCE.asHolder(), new AttributeModifier(EternalStarlight.id("butterfly_wings_amulet_ether_resistance"), 0.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST)
				.build(),
			List.of(
				Component.translatable("tooltip." + EternalStarlight.ID + ".butterfly_wings_amulet.armor_erosion").withStyle(ChatFormatting.BLUE)
			),
			Optional.of(Style.EMPTY.withColor(0xd1ffe1)),
			Optional.of(EternalStarlight.id("textures/accessory/butterfly_wings_amulet_overlay"))
		))
		.stacksTo(1)));
	public static final RegistryObject<Item, Item> FUNGUS_AMULET = registerItem("fungus_amulet", () -> new Item(new Item.Properties()
		.component(ESDataComponents.ACCESSORY.get(), new Accessory(
			ESTags.Items.CHEST_ARMOR_ACCESSORY_APPLICABLE,
			Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_combination_target.chestplate").withStyle(ChatFormatting.BLUE),
			ItemAttributeModifiers.builder()
				.add(ESAttributes.HEAL_MULTIPLIER.asHolder(), new AttributeModifier(EternalStarlight.id("fungus_amulet_heal_multiplier"), 0.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST)
				.build(),
			List.of(
				Component.translatable("tooltip." + EternalStarlight.ID + ".fungus_amulet.consume_fungus").withStyle(ChatFormatting.BLUE)
			),
			Optional.of(Style.EMPTY.withColor(0xccbb98)),
			Optional.of(EternalStarlight.id("textures/accessory/fungus_amulet_overlay"))
		))
		.stacksTo(1)));
	public static final RegistryObject<Item, Item> PEARL_NECKLACE = registerItem("pearl_necklace", () -> new Item(new Item.Properties()
		.component(ESDataComponents.ACCESSORY.get(), new Accessory(
			ESTags.Items.CHEST_ARMOR_ACCESSORY_APPLICABLE,
			Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_combination_target.chestplate").withStyle(ChatFormatting.BLUE),
			ItemAttributeModifiers.builder()
				.add(Attributes.OXYGEN_BONUS, new AttributeModifier(EternalStarlight.id("pearl_necklace_oxygen_bonus"), 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST)
				.add(Attributes.SUBMERGED_MINING_SPEED, new AttributeModifier(EternalStarlight.id("pearl_necklace_submerged_mining_speed"), 4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.CHEST)
				.build(),
			List.of(
				Component.translatable("tooltip." + EternalStarlight.ID + ".pearl_necklace.instant_air_recovery").withStyle(ChatFormatting.BLUE)
			),
			Optional.of(Style.EMPTY.withColor(0xcfddb4)),
			Optional.of(EternalStarlight.id("textures/accessory/pearl_necklace_overlay"))
		))
		.stacksTo(1)));
	public static final RegistryObject<Item, Item> CRESCENT_PENDANT = registerItem("crescent_pendant", () -> new Item(new Item.Properties()
		.rarity(Rarity.RARE)
		.component(ESDataComponents.ACCESSORY.get(), new Accessory(
			ESTags.Items.CHEST_ARMOR_ACCESSORY_APPLICABLE,
			Component.translatable("tooltip." + EternalStarlight.ID + ".accessory_combination_target.chestplate").withStyle(ChatFormatting.BLUE),
			ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(EternalStarlight.id("crescent_pendant_attack_damage"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.CHEST)
				.build(),
			List.of(
				Component.translatable("tooltip." + EternalStarlight.ID + ".crescent_pendant.damage_cap").withStyle(ChatFormatting.BLUE),
				Component.translatable("tooltip." + EternalStarlight.ID + ".crescent_pendant.repair").withStyle(ChatFormatting.BLUE)
			),
			Optional.of(Style.EMPTY.withColor(0x6666a3)),
			Optional.of(EternalStarlight.id("textures/accessory/crescent_pendant_overlay"))
		))
		.stacksTo(1)));

	// loot chest
	public static final RegistryObject<Item, Item> LOOT_CHEST = registerItem("loot_chest", () -> new BlockItem(ESBlocks.LOOT_CHEST.get(), new Item.Properties()));

	// boss spawners
	public static final RegistryObject<Item, Item> THE_GATEKEEPER_SPAWNER = registerItem("the_gatekeeper_spawner", () -> new BlockItem(ESBlocks.THE_GATEKEEPER_SPAWNER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> STARLIGHT_GOLEM_SPAWNER = registerItem("starlight_golem_spawner", () -> new BlockItem(ESBlocks.STARLIGHT_GOLEM_SPAWNER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> PERMAFROST_SPAWNER = registerItem("permafrost_spawner", () -> new BlockItem(ESBlocks.PERMAFROST_SPAWNER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_MONSTROSITY_SPAWNER = registerItem("lunar_monstrosity_spawner", () -> new BlockItem(ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> SOLAR_EGG = registerItem("solar_egg", () -> new BlockItem(ESBlocks.SOLAR_EGG.get(), new Item.Properties()));

	// gatekeeper
	public static final RegistryObject<Item, Item> LOOT_BAG = registerItem("loot_bag", () -> new LootBagItem(new Item.Properties().fireResistant().rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> STARLIGHT_SILVER_COIN = registerItem("starlight_silver_coin", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> BOOK = registerItem("book", () -> new ESBookItem(new Item.Properties().component(ESDataComponents.BOOK.get(), new GuideBook(EternalStarlight.id("main"), new HashSet<>(Set.of(EternalStarlight.ID)))).rarity(Rarity.RARE).stacksTo(1)));
	public static final RegistryObject<Item, Item> SEEKING_EYE = registerItem("seeking_eye", () -> new SeekingEyeItem(new Item.Properties()));
	public static final RegistryObject<Item, Item> GLISTERING_SWORD = registerItem("glistering_sword",
		() -> new SwordItem(ESItemTiers.GLISTERING, new Item.Properties().rarity(Rarity.RARE).attributes(SwordItem.createAttributes(ESItemTiers.GLISTERING, 3, -2.4F))));
	public static final RegistryObject<Item, Item> GLISTERING_GREATSWORD = registerItem("glistering_greatsword",
		() -> new GreatswordItem(ESItemTiers.GLISTERING, new Item.Properties().rarity(Rarity.RARE).attributes(GreatswordItem.createAttributes(ESItemTiers.GLISTERING, 6, -2.8F, 0.5F))));
	public static final RegistryObject<Item, Item> GLISTERING_MORNING_STAR = registerItem("glistering_morning_star",
		() -> ESPlatform.INSTANCE.createHammer(ESItemTiers.GLISTERING, () -> null, Holder.direct(SoundEvents.MACE_SMASH_GROUND_HEAVY), new Item.Properties().rarity(Rarity.RARE).attributes(DiggerItem.createAttributes(ESItemTiers.GLISTERING, 7, -3F))));
	public static final RegistryObject<Item, Item> GLISTERING_BOW = registerItem("glistering_bow", () -> new GlisteringBowItem(new Item.Properties().rarity(Rarity.RARE).durability(8000)));

	// starlight golem
	public static final RegistryObject<Item, Item> GOLEM_STEEL_INGOT = registerItem("golem_steel_ingot", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_INGOT = registerItem("oxidized_golem_steel_ingot", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_NUGGET = registerItem("golem_steel_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_NUGGET = registerItem("oxidized_golem_steel_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> COLDSNAP = registerItem("coldsnap", () -> new ColdsnapItem(ESItemTiers.GOLEM_STEEL, new Item.Properties().attributes(WhipItem.createAttributes(ESItemTiers.GOLEM_STEEL, 4.7F, -3.2F))));
	public static final RegistryObject<Item, Item> ENERGY_SWORD = registerItem("energy_sword", () -> new EnergySwordItem(ESItemTiers.GOLEM_STEEL, new Item.Properties().rarity(Rarity.RARE).attributes(SwordItem.createAttributes(ESItemTiers.GOLEM_STEEL, 3, -2.4F))));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_GREATSWORD = registerItem("golem_steel_greatsword", () -> new GolemSteelGreatswordItem(ESItemTiers.GOLEM_STEEL, new Item.Properties().rarity(Rarity.RARE).attributes(GreatswordItem.createAttributes(ESItemTiers.GOLEM_STEEL, 6, -2.8F, 0.5F))));
	public static final RegistryObject<Item, Item> MECHANICAL_CROSSBOW = registerItem("mechanical_crossbow", () -> new MechanicalCrossbowItem(new Item.Properties().rarity(Rarity.RARE).durability(1600)));
	public static final RegistryObject<Item, Item> ENERGY_BOOMERANG = registerItem("energy_boomerang", () -> new EnergyBoomerangItem(ESItemTiers.GOLEM_STEEL, new Item.Properties().rarity(Rarity.RARE).attributes(BoomerangItem.createAttributes(ESItemTiers.GOLEM_STEEL, 4, -3.0F))));
	public static final RegistryObject<Item, Item> UNDERMINER = registerItem("underminer", () -> new PickaxeItem(ESItemTiers.GOLEM_STEEL, new Item.Properties().rarity(Rarity.RARE).attributes(PickaxeItem.createAttributes(ESItemTiers.GOLEM_STEEL, 1, -2.8F))));
	public static final RegistryObject<Item, Item> CRYSTAL_GREATSWORD = registerItem("crystal_greatsword", () -> new CrystalGreatswordItem(ESItemTiers.GOLEM_STEEL, new Item.Properties().rarity(Rarity.RARE).attributes(GreatswordItem.createAttributes(ESItemTiers.GOLEM_STEEL, 5, -2.8F, 0.5F))));
	public static final RegistryObject<Item, Item> CRYSTAL_CROSSBOW = registerItem("crystal_crossbow", () -> new CrystalCrossbowItem(new Item.Properties().rarity(Rarity.RARE).durability(1600)));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_BLOCK = registerItem("golem_steel_block", () -> new BlockItem(ESBlocks.GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_BLOCK = registerItem("waxed_golem_steel_block", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_BLOCK = registerItem("oxidized_golem_steel_block", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_SLAB = registerItem("golem_steel_slab", () -> new BlockItem(ESBlocks.GOLEM_STEEL_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_SLAB = registerItem("waxed_golem_steel_slab", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_SLAB = registerItem("oxidized_golem_steel_slab", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_STAIRS = registerItem("golem_steel_stairs", () -> new BlockItem(ESBlocks.GOLEM_STEEL_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_STAIRS = registerItem("waxed_golem_steel_stairs", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_STAIRS = registerItem("oxidized_golem_steel_stairs", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_TILES = registerItem("golem_steel_tiles", () -> new BlockItem(ESBlocks.GOLEM_STEEL_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_TILES = registerItem("waxed_golem_steel_tiles", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_TILES = registerItem("oxidized_golem_steel_tiles", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_TILE_SLAB = registerItem("golem_steel_tile_slab", () -> new BlockItem(ESBlocks.GOLEM_STEEL_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_TILE_SLAB = registerItem("waxed_golem_steel_tile_slab", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_TILE_SLAB = registerItem("oxidized_golem_steel_tile_slab", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_TILE_STAIRS = registerItem("golem_steel_tile_stairs", () -> new BlockItem(ESBlocks.GOLEM_STEEL_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_TILE_STAIRS = registerItem("waxed_golem_steel_tile_stairs", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_TILE_STAIRS = registerItem("oxidized_golem_steel_tile_stairs", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_GRATE = registerItem("golem_steel_grate", () -> new BlockItem(ESBlocks.GOLEM_STEEL_GRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_GRATE = registerItem("waxed_golem_steel_grate", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_GRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_GRATE = registerItem("oxidized_golem_steel_grate", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_PILLAR = registerItem("golem_steel_pillar", () -> new BlockItem(ESBlocks.GOLEM_STEEL_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_PILLAR = registerItem("waxed_golem_steel_pillar", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_PILLAR = registerItem("oxidized_golem_steel_pillar", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_BARS = registerItem("golem_steel_bars", () -> new BlockItem(ESBlocks.GOLEM_STEEL_BARS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_BARS = registerItem("waxed_golem_steel_bars", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_BARS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_BARS = registerItem("oxidized_golem_steel_bars", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_BARS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_GOLEM_STEEL_BLOCK = registerItem("chiseled_golem_steel_block", () -> new BlockItem(ESBlocks.CHISELED_GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_CHISELED_GOLEM_STEEL_BLOCK = registerItem("waxed_chiseled_golem_steel_block", () -> new BlockItem(ESBlocks.WAXED_CHISELED_GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK = registerItem("oxidized_chiseled_golem_steel_block", () -> new BlockItem(ESBlocks.OXIDIZED_CHISELED_GOLEM_STEEL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_JET = registerItem("golem_steel_jet", () -> new BlockItem(ESBlocks.GOLEM_STEEL_JET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_GOLEM_STEEL_JET = registerItem("waxed_golem_steel_jet", () -> new BlockItem(ESBlocks.WAXED_GOLEM_STEEL_JET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_GOLEM_STEEL_JET = registerItem("oxidized_golem_steel_jet", () -> new BlockItem(ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> GOLEM_STEEL_CRATE = registerItem("golem_steel_crate", () -> new ContainerBlockItem(ESBlocks.GOLEM_STEEL_CRATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ENERGY_TRANSMITTER = registerItem("energy_transmitter", () -> new ContainerBlockItem(ESBlocks.ENERGY_TRANSMITTER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ACCUMULATOR = registerItem("accumulator", () -> new ContainerBlockItem(ESBlocks.ACCUMULATOR.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> MECHANICAL_SPAWNER = registerItem("mechanical_spawner", () -> new BlockItem(ESBlocks.MECHANICAL_SPAWNER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ALLOY_FURNACE = registerItem("alloy_furnace", () -> new BlockItem(ESBlocks.ALLOY_FURNACE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> WAXED_ALLOY_FURNACE = registerItem("waxed_alloy_furnace", () -> new BlockItem(ESBlocks.WAXED_ALLOY_FURNACE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> OXIDIZED_ALLOY_FURNACE = registerItem("oxidized_alloy_furnace", () -> new BlockItem(ESBlocks.OXIDIZED_ALLOY_FURNACE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ENERGY_BLOCK = registerItem("energy_block", () -> new BlockItem(ESBlocks.ENERGY_BLOCK.get(), new Item.Properties()));

	// lunar monstrosity
	public static final RegistryObject<Item, Item> TENACIOUS_PETAL = registerItem("tenacious_petal", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> TENACIOUS_VINE = registerItem("tenacious_vine", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> SOUL_DEW = registerItem("soul_dew", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> SOULIT_SPECTATOR = registerItem("soulit_spectator", () -> new SoulitSpectatorItem(new Item.Properties().rarity(Rarity.RARE).durability(750)));
	public static final RegistryObject<Item, Item> WILTED_CROSSBOW = registerItem("wilted_crossbow", () -> new WiltedCrossbowItem(new Item.Properties().rarity(Rarity.RARE).durability(1600)));
	public static final RegistryObject<Item, Item> MOONRING_BOW = registerItem("moonring_bow", () -> new MoonringBowItem(new Item.Properties().rarity(Rarity.RARE).durability(1600)));
	public static final RegistryObject<Item, Item> MOONRING_GREATSWORD = registerItem("moonring_greatsword", () -> new MoonringGreatswordItem(ESItemTiers.PETAL, new Item.Properties().rarity(Rarity.RARE).attributes(GreatswordItem.createAttributes(ESItemTiers.PETAL, 6, -2.8F, 0.5F))));
	public static final RegistryObject<Item, Item> PETAL_SCYTHE = registerItem("petal_scythe", () -> ESPlatform.INSTANCE.createPetalScythe(ESItemTiers.PETAL, false, new Item.Properties().rarity(Rarity.RARE).attributes(ScytheItem.createAttributes(ESItemTiers.PETAL, 4, -2.7F, 0.5F, 0.45F))));
	public static final RegistryObject<Item, Item> WAND_OF_TELEPORTATION = registerItem("wand_of_teleportation", () -> new WandOfTeleportationItem(new Item.Properties().rarity(Rarity.RARE).durability(800)));
	public static final RegistryObject<Item, Item> CHAIN_OF_SOULS = registerItem("chain_of_souls", () -> new ChainOfSoulsItem(new Item.Properties().rarity(Rarity.RARE).durability(1200)));
	public static final RegistryObject<Item, Item> CRESCENT_SPEAR = registerItem("crescent_spear", () -> ESPlatform.INSTANCE.createCrescentSpear(new Item.Properties().rarity(Rarity.RARE).attributes(CrescentSpearItem.createAttributes()).component(DataComponents.TOOL, CrescentSpearItem.createToolProperties()).durability(750)));
	public static final RegistryObject<Item, Item> SHADEGRIEVE = registerItem("shadegrieve", () -> new BlockItem(ESBlocks.SHADEGRIEVE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> BLOOMING_SHADEGRIEVE = registerItem("blooming_shadegrieve", () -> new BlockItem(ESBlocks.BLOOMING_SHADEGRIEVE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_VINE = registerItem("lunar_vine", () -> new BlockItem(ESBlocks.LUNAR_VINE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_MOSAIC = registerItem("lunar_mosaic", () -> new BlockItem(ESBlocks.LUNAR_MOSAIC.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_MOSAIC_SLAB = registerItem("lunar_mosaic_slab", () -> new BlockItem(ESBlocks.LUNAR_MOSAIC_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_MOSAIC_STAIRS = registerItem("lunar_mosaic_stairs", () -> new BlockItem(ESBlocks.LUNAR_MOSAIC_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_MOSAIC_FENCE = registerItem("lunar_mosaic_fence", () -> new BlockItem(ESBlocks.LUNAR_MOSAIC_FENCE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_MOSAIC_FENCE_GATE = registerItem("lunar_mosaic_fence_gate", () -> new BlockItem(ESBlocks.LUNAR_MOSAIC_FENCE_GATE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> LUNAR_MAT = registerItem("lunar_mat", () -> new BlockItem(ESBlocks.LUNAR_MAT.get(), new Item.Properties()));

	// solar creeper
	public static final RegistryObject<Item, Item> GRAVITY_PICKAXE = registerItem("gravity_pickaxe", () -> new PickaxeItem(ESItemTiers.AETHERSENT, new Item.Properties().rarity(Rarity.EPIC).attributes(PickaxeItem.createAttributes(ESItemTiers.AETHERSENT, 1, -2.8F))));
	public static final RegistryObject<Item, Item> DUSK_GLASS = registerItem("dusk_glass", () -> new BlockItem(ESBlocks.DUSK_GLASS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DUSK_LIGHT = registerItem("dusk_light", () -> new BlockItem(ESBlocks.DUSK_LIGHT.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> REINFORCED_DUSK_LIGHT = registerItem("reinforced_dusk_light", () -> new BlockItem(ESBlocks.REINFORCED_DUSK_LIGHT.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DUSK_EMITTER = registerItem("dusk_emitter", () -> new BlockItem(ESBlocks.DUSK_EMITTER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DUSK_LOCKBOX = registerItem("dusk_lockbox", () -> new BlockItem(ESBlocks.DUSK_LOCKBOX.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> FLARE_SPAWNER = registerItem("flare_spawner", () -> new BlockItem(ESBlocks.FLARE_SPAWNER.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> ECLIPSE_CORE = registerItem("eclipse_core", () -> new BlockItem(ESBlocks.ECLIPSE_CORE.get(), new Item.Properties()));

	// doomeden
	public static final RegistryObject<Item, Item> BROKEN_DOOMEDEN_BONE = registerItem("broken_doomeden_bone", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> BONEMORE = registerItem("bonemore", () -> new GreatswordItem(ESItemTiers.DOOMEDEN, new Item.Properties().rarity(Rarity.EPIC).attributes(GreatswordItem.createAttributes(ESItemTiers.DOOMEDEN, 6, -2.8F, 0.5F))));
	public static final RegistryObject<Item, Item> BOW_OF_BLOOD = registerItem("bow_of_blood", () -> new BloodBowItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item, Item> LIVING_ARM = registerItem("living_arm", () -> new LivingArmItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> DOOMED_TORCH = registerItem("doomed_torch", () -> new StandingAndWallBlockItem(ESBlocks.DOOMED_TORCH.get(), ESBlocks.WALL_DOOMED_TORCH.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item, Item> DOOMED_REDSTONE_TORCH = registerItem("doomed_redstone_torch", () -> new StandingAndWallBlockItem(ESBlocks.DOOMED_REDSTONE_TORCH.get(), ESBlocks.WALL_DOOMED_REDSTONE_TORCH.get(), new Item.Properties(), Direction.DOWN));
	public static final RegistryObject<Item, Item> DOOMEDEN_CARRION = registerItem("doomeden_carrion", () -> new Item(new Item.Properties().food(ESFoods.DOOMEDEN_CARRION.get())));
	public static final RegistryObject<Item, Item> ROTTEN_HAM = registerItem("rotten_ham", () -> new Item(new Item.Properties().food(ESFoods.ROTTEN_HAM.get())));
	public static final RegistryObject<Item, Item> EYE_OF_DOOM = registerItem("eye_of_doom", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_RAG = registerItem("doomeden_rag", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item, Item> FLESH_GRINDER = registerItem("flesh_grinder", () -> new AxeItem(ESItemTiers.DOOMEDEN, new Item.Properties().attributes(AxeItem.createAttributes(ESItemTiers.DOOMEDEN, 5, -2F))));
	public static final RegistryObject<Item, Item> DOOMEDEN_RAPIER = registerItem("doomeden_rapier", () -> new SwordItem(ESItemTiers.DOOMEDEN, new Item.Properties().attributes(SwordItem.createAttributes(ESItemTiers.DOOMEDEN, 4, -1.8F))));
	public static final RegistryObject<Item, Item> DOOMEDEN_BRICKS = registerItem("doomeden_bricks", () -> new BlockItem(ESBlocks.DOOMEDEN_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_BRICK_SLAB = registerItem("doomeden_brick_slab", () -> new BlockItem(ESBlocks.DOOMEDEN_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_BRICK_STAIRS = registerItem("doomeden_brick_stairs", () -> new BlockItem(ESBlocks.DOOMEDEN_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_BRICK_WALL = registerItem("doomeden_brick_wall", () -> new BlockItem(ESBlocks.DOOMEDEN_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_DOOMEDEN_BRICKS = registerItem("polished_doomeden_bricks", () -> new BlockItem(ESBlocks.POLISHED_DOOMEDEN_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_DOOMEDEN_BRICK_SLAB = registerItem("polished_doomeden_brick_slab", () -> new BlockItem(ESBlocks.POLISHED_DOOMEDEN_BRICK_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_DOOMEDEN_BRICK_STAIRS = registerItem("polished_doomeden_brick_stairs", () -> new BlockItem(ESBlocks.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> POLISHED_DOOMEDEN_BRICK_WALL = registerItem("polished_doomeden_brick_wall", () -> new BlockItem(ESBlocks.POLISHED_DOOMEDEN_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_TILES = registerItem("doomeden_tiles", () -> new BlockItem(ESBlocks.DOOMEDEN_TILES.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_TILE_SLAB = registerItem("doomeden_tile_slab", () -> new BlockItem(ESBlocks.DOOMEDEN_TILE_SLAB.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_TILE_STAIRS = registerItem("doomeden_tile_stairs", () -> new BlockItem(ESBlocks.DOOMEDEN_TILE_STAIRS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_TILE_WALL = registerItem("doomeden_tile_wall", () -> new BlockItem(ESBlocks.DOOMEDEN_TILE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHISELED_POLISHED_DOOMEDEN_BRICKS = registerItem("chiseled_polished_doomeden_bricks", () -> new BlockItem(ESBlocks.CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS = registerItem("charged_chiseled_polished_doomeden_bricks", () -> new BlockItem(ESBlocks.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_LIGHT = registerItem("doomeden_light", () -> new BlockItem(ESBlocks.DOOMEDEN_LIGHT.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> DOOMEDEN_KEYHOLE = registerItem("doomeden_keyhole", () -> new BlockItem(ESBlocks.DOOMEDEN_KEYHOLE.get(), new Item.Properties()));
	public static final RegistryObject<Item, Item> REDSTONE_DOOMEDEN_KEYHOLE = registerItem("redstone_doomeden_keyhole", () -> new BlockItem(ESBlocks.REDSTONE_DOOMEDEN_KEYHOLE.get(), new Item.Properties()));

	// magic
	public static final RegistryObject<Item, Item> ORB_OF_PROPHECY = registerItem("orb_of_prophecy", () -> new OrbOfProphecyItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item, Item> STELLAR_RACK = registerItem("stellar_rack", () -> new BlockItem(ESBlocks.STELLAR_RACK.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> ENCHANTED_GRIMSTONE_BRICKS = registerItem("enchanted_grimstone_bricks", () -> new BlockItem(ESBlocks.ENCHANTED_GRIMSTONE_BRICKS.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> MANA_CRYSTAL = registerItem("mana_crystal", () -> new ManaCrystalItem(ManaType.EMPTY, new Item.Properties().durability(1800).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> TERRA_CRYSTAL = registerItem("terra_crystal", () -> new ManaCrystalItem(ManaType.TERRA, new Item.Properties().durability(300).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> WIND_CRYSTAL = registerItem("wind_crystal", () -> new ManaCrystalItem(ManaType.WIND, new Item.Properties().durability(300).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> WATER_CRYSTAL = registerItem("water_crystal", () -> new ManaCrystalItem(ManaType.WATER, new Item.Properties().durability(300).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> LUNAR_CRYSTAL = registerItem("lunar_crystal", () -> new ManaCrystalItem(ManaType.LUNAR, new Item.Properties().durability(300).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> BLAZE_CRYSTAL = registerItem("blaze_crystal", () -> new ManaCrystalItem(ManaType.BLAZE, new Item.Properties().durability(300).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> LIGHT_CRYSTAL = registerItem("light_crystal", () -> new ManaCrystalItem(ManaType.LIGHT, new Item.Properties().durability(300).rarity(Rarity.RARE)));
	public static final RegistryObject<Item, Item> MANA_CRYSTAL_SHARD = registerItem("mana_crystal_shard", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

	// trim
	public static final RegistryObject<Item, Item> KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("keeper_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(EternalStarlight.id("keeper")));
	public static final RegistryObject<Item, Item> FORGE_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("forge_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(EternalStarlight.id("forge")));
	public static final RegistryObject<Item, Item> BLOOMING_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("blooming_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(EternalStarlight.id("blooming")));
	public static final RegistryObject<Item, Item> TWINING_ARMOR_TRIM_SMITHING_TEMPLATE = registerItem("twining_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(EternalStarlight.id("twining")));

	// music discs
	public static final RegistryObject<Item, Item> MUSIC_DISC_WHISPER_OF_THE_STARS = registerItem("music_disc_whisper_of_the_stars", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.WHISPER_OF_THE_STARS)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_DUSK_O_EREYESTERDAY = registerItem("music_disc_dusk_o_ereyesterday", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.DUSK_O_EREYESTERDAY)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_TRANQUILITY = registerItem("music_disc_tranquility", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.TRANQUILITY)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_NEST = registerItem("music_disc_nest", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.NEST)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_POSTERITY = registerItem("music_disc_posterity", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.POSTERITY)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_THE_THORNY_REIGN = registerItem("music_disc_the_thorny_reign", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.THE_THORNY_REIGN)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_PROFUNDITY = registerItem("music_disc_profundity", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.PROFUNDITY)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_WAILING_WELL = registerItem("music_disc_wailing_well", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.WAILING_WELL)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_STARS_SHINING_UPON_THE_SEA = registerItem("music_disc_stars_shining_upon_the_sea", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.STARS_SHINING_UPON_THE_SEA)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_OPTIMIZED_OPTION = registerItem("music_disc_optimized_option", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.OPTIMIZED_OPTION)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_MECHANICAL_FOSSIL = registerItem("music_disc_mechanical_fossil", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.MECHANICAL_FOSSIL)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_FAKE_LIGHT = registerItem("music_disc_fake_light", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.FAKE_LIGHT)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_TRANQUILITY_II = registerItem("music_disc_tranquility_ii", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.TRANQUILITY_II)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_ATLANTIS = registerItem("music_disc_atlantis", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.ATLANTIS)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_SACRED_DESERT = registerItem("music_disc_sacred_desert", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.SACRED_DESERT)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_SPIRIT = registerItem("music_disc_spirit", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.SPIRIT)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_ETHER_RAIN = registerItem("music_disc_ether_rain", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.ETHER_RAIN)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_BRISK = registerItem("music_disc_brisk", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.BRISK)));
	public static final RegistryObject<Item, Item> MUSIC_DISC_MOONLIGHT = registerItem("music_disc_moonlight", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ESJukeboxSongs.MOONLIGHT)));

	// hide it from creative mode tab
	public static final RegistryObject<Item, Item> BLOSSOM_OF_STARS = ITEMS.register("blossom_of_stars", () -> new BlossomOfStarsItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).effect(new MobEffectInstance(ESMobEffects.DREAM_CATCHER.asHolder(), 2400, 0), 1F).alwaysEdible().build()).rarity(Rarity.RARE)));

	public static ItemAttributeModifiers createUnrealiumAttributes(Tier tier, float damage, float speed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(EternalStarlight.id("weapon.attack_knockback"), -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	public static void loadClass() {
	}
}