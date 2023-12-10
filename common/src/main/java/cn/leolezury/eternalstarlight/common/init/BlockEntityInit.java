package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.ESHangingSignBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.ESSignBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.LunarMonstrositySpawnerEntity;
import cn.leolezury.eternalstarlight.common.block.entity.StarlightGolemSpawnerEntity;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityInit {
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSignBlockEntity>> SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("sign_block_entity", () -> BlockEntityType.Builder.of(ESSignBlockEntity::new, BlockInit.LUNAR_WALL_SIGN.get(), BlockInit.LUNAR_SIGN.get(), BlockInit.NORTHLAND_WALL_SIGN.get(), BlockInit.NORTHLAND_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESHangingSignBlockEntity>> HANGING_SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("hanging_sign_block_entity", () -> BlockEntityType.Builder.of(ESHangingSignBlockEntity::new, BlockInit.LUNAR_WALL_HANGING_SIGN.get(), BlockInit.LUNAR_HANGING_SIGN.get(), BlockInit.NORTHLAND_WALL_HANGING_SIGN.get(), BlockInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StarlightGolemSpawnerEntity>> STARLIGHT_GOLEM_SPAWNER = BLOCK_ENTITIES.register("starlight_golem_spawner", () -> BlockEntityType.Builder.of(StarlightGolemSpawnerEntity::new, BlockInit.STARLIGHT_GOLEM_SPAWNER.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarMonstrositySpawnerEntity>> LUNAR_MONSTROSITY_SPAWNER = BLOCK_ENTITIES.register("lunar_monstrosity_spawner", () -> BlockEntityType.Builder.of(LunarMonstrositySpawnerEntity::new, BlockInit.LUNAR_MONSTROSITY_SPAWNER.get()).build(null));
    public static void loadClass() {}
}
