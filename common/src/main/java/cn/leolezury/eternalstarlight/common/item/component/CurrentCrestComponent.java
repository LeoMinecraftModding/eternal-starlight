package cn.leolezury.eternalstarlight.common.item.component;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;

public record CurrentCrestComponent(Holder<Crest> crest) {
	public static final Codec<CurrentCrestComponent> CODEC = RegistryFixedCodec.create(ESRegistries.CREST).xmap(CurrentCrestComponent::new, CurrentCrestComponent::crest);
	public static final StreamCodec<RegistryFriendlyByteBuf, CurrentCrestComponent> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);
}
