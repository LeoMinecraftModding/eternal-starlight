package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityInit {
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ESSignBlockEntity::new, BlockInit.LUNAR_WALL_SIGN.get(), BlockInit.LUNAR_SIGN.get(), BlockInit.NORTHLAND_WALL_SIGN.get(), BlockInit.NORTHLAND_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.SCARLET_WALL_SIGN.get(), BlockInit.SCARLET_SIGN.get(), BlockInit.TORREYA_WALL_SIGN.get(), BlockInit.TORREYA_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESHangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(ESHangingSignBlockEntity::new, BlockInit.LUNAR_WALL_HANGING_SIGN.get(), BlockInit.LUNAR_HANGING_SIGN.get(), BlockInit.NORTHLAND_WALL_HANGING_SIGN.get(), BlockInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.SCARLET_WALL_HANGING_SIGN.get(), BlockInit.SCARLET_HANGING_SIGN.get(), BlockInit.TORREYA_WALL_HANGING_SIGN.get(), BlockInit.TORREYA_HANGING_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<AbyssalGeyserBlockEntity>> ABYSSAL_GEYSER = BLOCK_ENTITIES.register("abyssal_geyser", () -> BlockEntityType.Builder.of(AbyssalGeyserBlockEntity::new, BlockInit.ABYSSAL_GEYSER.get(), BlockInit.THERMABYSSAL_GEYSER.get(), BlockInit.CRYOBYSSAL_GEYSER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StarlightGolemSpawnerEntity>> STARLIGHT_GOLEM_SPAWNER = BLOCK_ENTITIES.register("starlight_golem_spawner", () -> BlockEntityType.Builder.of(StarlightGolemSpawnerEntity::new, BlockInit.STARLIGHT_GOLEM_SPAWNER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarMonstrositySpawnerEntity>> LUNAR_MONSTROSITY_SPAWNER = BLOCK_ENTITIES.register("lunar_monstrosity_spawner", () -> BlockEntityType.Builder.of(LunarMonstrositySpawnerEntity::new, BlockInit.LUNAR_MONSTROSITY_SPAWNER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESPortalBlockEntity>> STARLIGHT_PORTAL = BLOCK_ENTITIES.register("starlight_portal", () -> BlockEntityType.Builder.of(ESPortalBlockEntity::new, BlockInit.STARLIGHT_PORTAL.get()).build(null));

    public static void loadClass() {}
}
