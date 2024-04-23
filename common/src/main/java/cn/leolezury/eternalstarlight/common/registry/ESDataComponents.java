package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.component.ResourceKeyComponent;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.loot.LootTable;

public class ESDataComponents {
    public static final RegistrationProvider<DataComponentType<?>> DATA_COMPONENTS = RegistrationProvider.get(Registries.DATA_COMPONENT_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<DataComponentType<?>, DataComponentType<CustomData>> CRESTS = DATA_COMPONENTS.register("crests", () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());
    public static final RegistryObject<DataComponentType<?>, DataComponentType<Boolean>> ORB_OF_PROPHECY_TEMPORARY = DATA_COMPONENTS.register("orb_of_prophecy_temporary", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final RegistryObject<DataComponentType<?>, DataComponentType<ResourceKeyComponent<LootTable>>> LOOT_TABLE = DATA_COMPONENTS.register("loot_table", () -> DataComponentType.<ResourceKeyComponent<LootTable>>builder().persistent(ResourceKeyComponent.codec(Registries.LOOT_TABLE)).build());
    public static final RegistryObject<DataComponentType<?>, DataComponentType<Boolean>> HAS_BLADE = DATA_COMPONENTS.register("has_blade", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static void loadClass() {}
}
