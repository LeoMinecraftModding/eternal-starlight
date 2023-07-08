package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.block.entity.LunarMonstrositySpawnerEntity;
import cn.leolezury.eternalstarlight.block.entity.SLHangingSignBlockEntity;
import cn.leolezury.eternalstarlight.block.entity.SLSignBlockEntity;
import cn.leolezury.eternalstarlight.block.entity.StarlightGolemSpawnerEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EternalStarlight.MOD_ID);
    public static final RegistryObject<BlockEntityType<SLSignBlockEntity>> SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("sign_block_entity", () -> BlockEntityType.Builder.of(SLSignBlockEntity::new, BlockInit.LUNAR_WALL_SIGN.get(), BlockInit.LUNAR_SIGN.get(), BlockInit.NORTHLAND_WALL_SIGN.get(), BlockInit.NORTHLAND_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<SLHangingSignBlockEntity>> HANGING_SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("hanging_sign_block_entity", () -> BlockEntityType.Builder.of(SLHangingSignBlockEntity::new, BlockInit.LUNAR_WALL_HANGING_SIGN.get(), BlockInit.LUNAR_HANGING_SIGN.get(), BlockInit.NORTHLAND_WALL_HANGING_SIGN.get(), BlockInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<StarlightGolemSpawnerEntity>> STARLIGHT_GOLEM_SPAWNER = BLOCK_ENTITIES.register("starlight_golem_spawner", () -> BlockEntityType.Builder.of(StarlightGolemSpawnerEntity::new, BlockInit.STARLIGHT_GOLEM_SPAWNER.get()).build(null));
    public static final RegistryObject<BlockEntityType<LunarMonstrositySpawnerEntity>> LUNAR_MONSTROSITY_SPAWNER = BLOCK_ENTITIES.register("lunar_monstrosity_spawner", () -> BlockEntityType.Builder.of(LunarMonstrositySpawnerEntity::new, BlockInit.LUNAR_MONSTROSITY_SPAWNER.get()).build(null));
}
