package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.*;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.LunarMonstrositySpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.StarlightGolemSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.TangledHatredSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.TheGatekeeperSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ESBlockEntities {
	public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, EternalStarlight.ID);
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ESSignBlockEntity::new, ESBlocks.LUNAR_WALL_SIGN.get(), ESBlocks.LUNAR_SIGN.get(), ESBlocks.NORTHLAND_WALL_SIGN.get(), ESBlocks.NORTHLAND_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_WALL_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_SIGN.get(), ESBlocks.SCARLET_WALL_SIGN.get(), ESBlocks.SCARLET_SIGN.get(), ESBlocks.TORREYA_WALL_SIGN.get(), ESBlocks.TORREYA_SIGN.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESHangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(ESHangingSignBlockEntity::new, ESBlocks.LUNAR_WALL_HANGING_SIGN.get(), ESBlocks.LUNAR_HANGING_SIGN.get(), ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get(), ESBlocks.NORTHLAND_HANGING_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_HANGING_SIGN.get(), ESBlocks.SCARLET_WALL_HANGING_SIGN.get(), ESBlocks.SCARLET_HANGING_SIGN.get(), ESBlocks.TORREYA_WALL_HANGING_SIGN.get(), ESBlocks.TORREYA_HANGING_SIGN.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<AbyssalGeyserBlockEntity>> ABYSSAL_GEYSER = BLOCK_ENTITIES.register("abyssal_geyser", () -> BlockEntityType.Builder.of(AbyssalGeyserBlockEntity::new, ESBlocks.ABYSSAL_GEYSER.get(), ESBlocks.THERMABYSSAL_GEYSER.get(), ESBlocks.CRYOBYSSAL_GEYSER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESCampfireBlockEntity>> CAMPFIRE = BLOCK_ENTITIES.register("campfire", () -> BlockEntityType.Builder.of(ESCampfireBlockEntity::new, ESBlocks.TORREYA_CAMPFIRE.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSkullBlockEntity>> SKULL = BLOCK_ENTITIES.register("skull", () -> BlockEntityType.Builder.of(ESSkullBlockEntity::new, ESBlocks.TANGLED_SKULL.get(), ESBlocks.TANGLED_WALL_SKULL.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<TheGatekeeperSpawnerBlockEntity>> THE_GATEKEEPER_SPAWNER = BLOCK_ENTITIES.register("the_gatekeeper_spawner", () -> BlockEntityType.Builder.of(TheGatekeeperSpawnerBlockEntity::new, ESBlocks.THE_GATEKEEPER_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StarlightGolemSpawnerBlockEntity>> STARLIGHT_GOLEM_SPAWNER = BLOCK_ENTITIES.register("starlight_golem_spawner", () -> BlockEntityType.Builder.of(StarlightGolemSpawnerBlockEntity::new, ESBlocks.STARLIGHT_GOLEM_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<TangledHatredSpawnerBlockEntity>> TANGLED_HATRED_SPAWNER = BLOCK_ENTITIES.register("tangled_hatred_spawner", () -> BlockEntityType.Builder.of(TangledHatredSpawnerBlockEntity::new, ESBlocks.TANGLED_HATRED_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarMonstrositySpawnerBlockEntity>> LUNAR_MONSTROSITY_SPAWNER = BLOCK_ENTITIES.register("lunar_monstrosity_spawner", () -> BlockEntityType.Builder.of(LunarMonstrositySpawnerBlockEntity::new, ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarVineBlockEntity>> LUNAR_VINE = BLOCK_ENTITIES.register("lunar_vine", () -> BlockEntityType.Builder.of(LunarVineBlockEntity::new, ESBlocks.LUNAR_VINE.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StellarRackBlockEntity>> STELLAR_RACK = BLOCK_ENTITIES.register("stellar_rack", () -> BlockEntityType.Builder.of(StellarRackBlockEntity::new, ESBlocks.STELLAR_RACK.get()).build(null));
	public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESPortalBlockEntity>> STARLIGHT_PORTAL = BLOCK_ENTITIES.register("starlight_portal", () -> BlockEntityType.Builder.of(ESPortalBlockEntity::new, ESBlocks.STARLIGHT_PORTAL.get()).build(null));

	public static void loadClass() {
	}
}
