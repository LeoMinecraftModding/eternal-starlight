package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ESBlockEntities {
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ESSignBlockEntity::new, ESBlocks.LUNAR_WALL_SIGN.get(), ESBlocks.LUNAR_SIGN.get(), ESBlocks.NORTHLAND_WALL_SIGN.get(), ESBlocks.NORTHLAND_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_WALL_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_SIGN.get(), ESBlocks.SCARLET_WALL_SIGN.get(), ESBlocks.SCARLET_SIGN.get(), ESBlocks.TORREYA_WALL_SIGN.get(), ESBlocks.TORREYA_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESHangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(ESHangingSignBlockEntity::new, ESBlocks.LUNAR_WALL_HANGING_SIGN.get(), ESBlocks.LUNAR_HANGING_SIGN.get(), ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get(), ESBlocks.NORTHLAND_HANGING_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(), ESBlocks.STARLIGHT_MANGROVE_HANGING_SIGN.get(), ESBlocks.SCARLET_WALL_HANGING_SIGN.get(), ESBlocks.SCARLET_HANGING_SIGN.get(), ESBlocks.TORREYA_WALL_HANGING_SIGN.get(), ESBlocks.TORREYA_HANGING_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<AbyssalGeyserBlockEntity>> ABYSSAL_GEYSER = BLOCK_ENTITIES.register("abyssal_geyser", () -> BlockEntityType.Builder.of(AbyssalGeyserBlockEntity::new, ESBlocks.ABYSSAL_GEYSER.get(), ESBlocks.THERMABYSSAL_GEYSER.get(), ESBlocks.CRYOBYSSAL_GEYSER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StarlightGolemSpawnerEntity>> STARLIGHT_GOLEM_SPAWNER = BLOCK_ENTITIES.register("starlight_golem_spawner", () -> BlockEntityType.Builder.of(StarlightGolemSpawnerEntity::new, ESBlocks.STARLIGHT_GOLEM_SPAWNER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarMonstrositySpawnerEntity>> LUNAR_MONSTROSITY_SPAWNER = BLOCK_ENTITIES.register("lunar_monstrosity_spawner", () -> BlockEntityType.Builder.of(LunarMonstrositySpawnerEntity::new, ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESPortalBlockEntity>> STARLIGHT_PORTAL = BLOCK_ENTITIES.register("starlight_portal", () -> BlockEntityType.Builder.of(ESPortalBlockEntity::new, ESBlocks.STARLIGHT_PORTAL.get()).build(null));

    public static void loadClass() {}
}
