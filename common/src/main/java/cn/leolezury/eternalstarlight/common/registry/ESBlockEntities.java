package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.*;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.LunarMonstrositySpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.PermafrostSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.StarlightGolemSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.TheGatekeeperSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ESBlockEntities {
	public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, EternalStarlight.ID);
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ESSignBlockEntity::new, ESBlocks.LUNAR_WALL_SIGN.get(), ESBlocks.LUNAR_SIGN.get(), ESBlocks.NORTHLAND_WALL_SIGN.get(), ESBlocks.NORTHLAND_SIGN.get(), ESBlocks.BANYIN_WALL_SIGN.get(), ESBlocks.BANYIN_SIGN.get(), ESBlocks.SCARLET_WALL_SIGN.get(), ESBlocks.SCARLET_SIGN.get(), ESBlocks.TORREYA_WALL_SIGN.get(), ESBlocks.TORREYA_SIGN.get(), ESBlocks.JINGLESTEM_WALL_SIGN.get(), ESBlocks.JINGLESTEM_SIGN.get(), ESBlocks.CRADLEWOOD_WALL_SIGN.get(), ESBlocks.CRADLEWOOD_SIGN.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESHangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(ESHangingSignBlockEntity::new, ESBlocks.LUNAR_WALL_HANGING_SIGN.get(), ESBlocks.LUNAR_HANGING_SIGN.get(), ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get(), ESBlocks.NORTHLAND_HANGING_SIGN.get(), ESBlocks.BANYIN_WALL_HANGING_SIGN.get(), ESBlocks.BANYIN_HANGING_SIGN.get(), ESBlocks.SCARLET_WALL_HANGING_SIGN.get(), ESBlocks.SCARLET_HANGING_SIGN.get(), ESBlocks.TORREYA_WALL_HANGING_SIGN.get(), ESBlocks.TORREYA_HANGING_SIGN.get(), ESBlocks.JINGLESTEM_WALL_HANGING_SIGN.get(), ESBlocks.JINGLESTEM_HANGING_SIGN.get(), ESBlocks.CRADLEWOOD_WALL_HANGING_SIGN.get(), ESBlocks.CRADLEWOOD_HANGING_SIGN.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<TorreyaCampfireBlockEntity>> TORREYA_CAMPFIRE = BLOCK_ENTITIES.register("torreya_campfire", () -> BlockEntityType.Builder.of(TorreyaCampfireBlockEntity::new, ESBlocks.TORREYA_CAMPFIRE.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<AbyssalGeyserBlockEntity>> ABYSSAL_GEYSER = BLOCK_ENTITIES.register("abyssal_geyser", () -> BlockEntityType.Builder.of(AbyssalGeyserBlockEntity::new, ESBlocks.ABYSSAL_GEYSER.get(), ESBlocks.THERMABYSSAL_GEYSER.get(), ESBlocks.CRYOBYSSAL_GEYSER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSkullBlockEntity>> SKULL = BLOCK_ENTITIES.register("skull", () -> BlockEntityType.Builder.of(ESSkullBlockEntity::new, ESBlocks.TANGLED_SKULL.get(), ESBlocks.TANGLED_WALL_SKULL.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESBrushableBlockEntity>> BRUSHABLE_BLOCK = BLOCK_ENTITIES.register("brushable_block", () -> BlockEntityType.Builder.of(ESBrushableBlockEntity::new, ESBlocks.SUSPICIOUS_DUSTED_GRAVEL.get(), ESBlocks.SUSPICIOUS_DIMSLAG.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<CrystalbornCatalystBlockEntity>> CRYSTALBORN_CATALYST = BLOCK_ENTITIES.register("crystalborn_catalyst", () -> BlockEntityType.Builder.of(CrystalbornCatalystBlockEntity::new, ESBlocks.CRYSTALBORN_CATALYST.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DryingRackBlockEntity>> DRYING_RACK = BLOCK_ENTITIES.register("drying_rack", () -> BlockEntityType.Builder.of(DryingRackBlockEntity::new, ESBlocks.DRYING_RACK.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StarfireBirdNestBlockEntity>> STARFIRE_BIRD_NEST = BLOCK_ENTITIES.register("starfire_bird_nest", () -> BlockEntityType.Builder.of(StarfireBirdNestBlockEntity::new,
		ESBlocks.STARFIRE_BIRD_NEST.get(),
		ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(),
		ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get()
	).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LootChestBlockEntity>> LOOT_CHEST = BLOCK_ENTITIES.register("loot_chest", () -> BlockEntityType.Builder.of(LootChestBlockEntity::new, ESBlocks.LOOT_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<TheGatekeeperSpawnerBlockEntity>> THE_GATEKEEPER_SPAWNER = BLOCK_ENTITIES.register("the_gatekeeper_spawner", () -> BlockEntityType.Builder.of(TheGatekeeperSpawnerBlockEntity::new, ESBlocks.THE_GATEKEEPER_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StarlightGolemSpawnerBlockEntity>> STARLIGHT_GOLEM_SPAWNER = BLOCK_ENTITIES.register("starlight_golem_spawner", () -> BlockEntityType.Builder.of(StarlightGolemSpawnerBlockEntity::new, ESBlocks.STARLIGHT_GOLEM_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<PermafrostSpawnerBlockEntity>> PERMAFROST_SPAWNER = BLOCK_ENTITIES.register("permafrost_spawner", () -> BlockEntityType.Builder.of(PermafrostSpawnerBlockEntity::new, ESBlocks.PERMAFROST_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarMonstrositySpawnerBlockEntity>> LUNAR_MONSTROSITY_SPAWNER = BLOCK_ENTITIES.register("lunar_monstrosity_spawner", () -> BlockEntityType.Builder.of(LunarMonstrositySpawnerBlockEntity::new, ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<GolemSteelJetBlockEntity>> GOLEM_STEEL_JET = BLOCK_ENTITIES.register("golem_steel_jet", () -> BlockEntityType.Builder.of(GolemSteelJetBlockEntity::new, ESBlocks.GOLEM_STEEL_JET.get(), ESBlocks.WAXED_GOLEM_STEEL_JET.get(), ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<EnergyTransmitterBlockEntity>> ENERGY_TRANSMITTER = BLOCK_ENTITIES.register("energy_transmitter", () -> BlockEntityType.Builder.of(EnergyTransmitterBlockEntity::new, ESBlocks.ENERGY_TRANSMITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<MechanicalSpawnerBlockEntity>> MECHANICAL_SPAWNER = BLOCK_ENTITIES.register("mechanical_spawner", () -> BlockEntityType.Builder.of(MechanicalSpawnerBlockEntity::new, ESBlocks.MECHANICAL_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE = BLOCK_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, ESBlocks.ALLOY_FURNACE.get(), ESBlocks.WAXED_ALLOY_FURNACE.get(), ESBlocks.OXIDIZED_ALLOY_FURNACE.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<SolarEggBlockEntity>> SOLAR_EGG = BLOCK_ENTITIES.register("solar_egg", () -> BlockEntityType.Builder.of(SolarEggBlockEntity::new, ESBlocks.SOLAR_EGG.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarVineBlockEntity>> LUNAR_VINE = BLOCK_ENTITIES.register("lunar_vine", () -> BlockEntityType.Builder.of(LunarVineBlockEntity::new, ESBlocks.LUNAR_VINE.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<CrateBlockEntity>> CRATE = BLOCK_ENTITIES.register("crate", () -> BlockEntityType.Builder.of(CrateBlockEntity::new, ESBlocks.GOLEM_STEEL_CRATE.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DuskLightBlockEntity>> DUSK_LIGHT = BLOCK_ENTITIES.register("dusk_light", () -> BlockEntityType.Builder.of(DuskLightBlockEntity::new, ESBlocks.DUSK_LIGHT.get(), ESBlocks.REINFORCED_DUSK_LIGHT.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DuskEmitterBlockEntity>> DUSK_EMITTER = BLOCK_ENTITIES.register("dusk_emitter", () -> BlockEntityType.Builder.of(DuskEmitterBlockEntity::new, ESBlocks.DUSK_EMITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DuskLockboxBlockEntity>> DUSK_LOCKBOX = BLOCK_ENTITIES.register("dusk_lockbox", () -> BlockEntityType.Builder.of(DuskLockboxBlockEntity::new, ESBlocks.DUSK_LOCKBOX.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<FlareSpawnerBlockEntity>> FLARE_SPAWNER = BLOCK_ENTITIES.register("flare_spawner", () -> BlockEntityType.Builder.of(FlareSpawnerBlockEntity::new, ESBlocks.FLARE_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<EclipseCoreBlockEntity>> ECLIPSE_CORE = BLOCK_ENTITIES.register("eclipse_core", () -> BlockEntityType.Builder.of(EclipseCoreBlockEntity::new, ESBlocks.ECLIPSE_CORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StellarRackBlockEntity>> STELLAR_RACK = BLOCK_ENTITIES.register("stellar_rack", () -> BlockEntityType.Builder.of(StellarRackBlockEntity::new, ESBlocks.STELLAR_RACK.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESPortalBlockEntity>> STARLIGHT_PORTAL = BLOCK_ENTITIES.register("starlight_portal", () -> BlockEntityType.Builder.of(ESPortalBlockEntity::new, ESBlocks.STARLIGHT_PORTAL.get()).build(null));

	public static void loadClass() {
	}
}
