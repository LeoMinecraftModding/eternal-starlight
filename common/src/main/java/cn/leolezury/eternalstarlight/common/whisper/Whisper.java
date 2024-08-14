package cn.leolezury.eternalstarlight.common.whisper;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record Whisper(Component from, Component context, Optional<List<ResourceLocation>> require) {
	public static final Codec<Whisper> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ComponentSerialization.CODEC.fieldOf("from").forGetter(Whisper::from),
		ComponentSerialization.CODEC.fieldOf("context").forGetter(Whisper::context),
		ResourceLocation.CODEC.listOf().optionalFieldOf("require").forGetter(Whisper::require)
	).apply(instance, Whisper::new));
}
