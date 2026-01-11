package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.item.component.Accessory;
import cn.leolezury.eternalstarlight.common.item.component.GuideBook;
import cn.leolezury.eternalstarlight.common.item.component.ItemStackList;
import cn.leolezury.eternalstarlight.common.item.component.LargeItemStackList;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;
import java.util.function.Function;

public class ESDataComponents {
	public static final RegistrationProvider<DataComponentType<?>> DATA_COMPONENTS = RegistrationProvider.get(Registries.DATA_COMPONENT_TYPE, EternalStarlight.ID);
	public static final RegistryObject<DataComponentType<?>, DataComponentType<GuideBook>> BOOK = DATA_COMPONENTS.register("book", () -> DataComponentType.<GuideBook>builder().persistent(GuideBook.CODEC).networkSynchronized(GuideBook.STREAM_CODEC).cacheEncoding().build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<Holder<Crest>>> CURRENT_CREST = DATA_COMPONENTS.register("current_crest", () -> DataComponentType.<Holder<Crest>>builder().persistent(RegistryFixedCodec.create(ESRegistries.CREST)).networkSynchronized(ByteBufCodecs.fromCodecWithRegistries(RegistryFixedCodec.create(ESRegistries.CREST))).build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<ResourceKey<LootTable>>> LOOT_TABLE = DATA_COMPONENTS.register("loot_table", () -> DataComponentType.<ResourceKey<LootTable>>builder().persistent(ResourceKey.codec(Registries.LOOT_TABLE)).networkSynchronized(ResourceKey.streamCodec(Registries.LOOT_TABLE)).build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<Boolean>> HAS_BLADE = DATA_COMPONENTS.register("has_blade", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).cacheEncoding().build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<Float>> HUNGER_LEVEL = DATA_COMPONENTS.register("hunger_level", () -> DataComponentType.<Float>builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).cacheEncoding().build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<List<StarfireBirdNestBlockEntity.Occupant>>> BIRDS = DATA_COMPONENTS.register("birds", () -> DataComponentType.<List<StarfireBirdNestBlockEntity.Occupant>>builder().persistent(StarfireBirdNestBlockEntity.Occupant.LIST_CODEC).networkSynchronized(StarfireBirdNestBlockEntity.Occupant.STREAM_CODEC.apply(ByteBufCodecs.list())).cacheEncoding().build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<Accessory>> ACCESSORY = DATA_COMPONENTS.register("accessory", () -> DataComponentType.<Accessory>builder().persistent(Accessory.CODEC).networkSynchronized(Accessory.STREAM_CODEC).cacheEncoding().build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<ItemStackList>> ACCESSORIES = DATA_COMPONENTS.register("accessories", () -> DataComponentType.<ItemStackList>builder().persistent(ItemStack.OPTIONAL_CODEC.listOf().xmap(ItemStackList::new, Function.identity())).networkSynchronized(ItemStack.OPTIONAL_LIST_STREAM_CODEC.map(ItemStackList::new, Function.identity())).cacheEncoding().build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<Integer>> ACCESSORY_SLOT_COUNT = DATA_COMPONENTS.register("accessory_slot_count", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).cacheEncoding().build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<LargeItemStackList>> ARROWS = DATA_COMPONENTS.register("arrows", () -> DataComponentType.<LargeItemStackList>builder().persistent(LargeItemStackList.LargeItemStack.CODEC.listOf().xmap(LargeItemStackList::new, Function.identity())).networkSynchronized(LargeItemStackList.LargeItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()).map(LargeItemStackList::new, Function.identity())).cacheEncoding().build());
	public static final RegistryObject<DataComponentType<?>, DataComponentType<Unit>> QUIVER_ARROW = DATA_COMPONENTS.register("quiver_arrow", () -> DataComponentType.<Unit>builder().persistent(Unit.CODEC).cacheEncoding().build());

	public static void loadClass() {
	}
}
