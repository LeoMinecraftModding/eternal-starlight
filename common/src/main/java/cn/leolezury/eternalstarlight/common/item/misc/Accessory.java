package cn.leolezury.eternalstarlight.common.item.misc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.Optional;

public record Accessory(TagKey<Item> combinationTarget, ItemAttributeModifiers attributeModifiers, List<Component> extraDescription, Optional<ResourceLocation> overlay) {
	public static final Codec<Accessory> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		TagKey.codec(Registries.ITEM).fieldOf("combination_target").forGetter(Accessory::combinationTarget),
		ItemAttributeModifiers.CODEC.fieldOf("attribute_modifiers").forGetter(Accessory::attributeModifiers),
		ComponentSerialization.CODEC.listOf().fieldOf("extra_description").forGetter(Accessory::extraDescription),
		ResourceLocation.CODEC.optionalFieldOf("overlay").forGetter(Accessory::overlay)
	).apply(instance, Accessory::new));

	public static final StreamCodec<? super RegistryFriendlyByteBuf, Accessory> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.fromCodec(TagKey.codec(Registries.ITEM)), Accessory::combinationTarget,
		ItemAttributeModifiers.STREAM_CODEC, Accessory::attributeModifiers,
		ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs.list()), Accessory::extraDescription,
		ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), Accessory::overlay,
		Accessory::new
	);
}
