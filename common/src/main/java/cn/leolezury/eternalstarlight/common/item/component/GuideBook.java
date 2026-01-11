package cn.leolezury.eternalstarlight.common.item.component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;

public record GuideBook(ResourceLocation id, HashSet<String> listeningNamespaces) {
	public static final Codec<GuideBook> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
		ResourceLocation.CODEC.fieldOf("id").forGetter(GuideBook::id),
		Codec.STRING.listOf().xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("listening_namespaces").forGetter(GuideBook::listeningNamespaces)
	).apply(instance, GuideBook::new));

	public static final StreamCodec<? super RegistryFriendlyByteBuf, GuideBook> STREAM_CODEC = StreamCodec.composite(
		ResourceLocation.STREAM_CODEC, GuideBook::id,
		ByteBufCodecs.fromCodec(Codec.STRING.listOf().xmap(Sets::newHashSet, Lists::newArrayList)), GuideBook::listeningNamespaces,
		GuideBook::new
	);
}
