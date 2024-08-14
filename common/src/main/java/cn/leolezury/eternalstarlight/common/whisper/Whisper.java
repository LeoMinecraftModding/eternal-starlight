package cn.leolezury.eternalstarlight.common.whisper;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record Whisper(Component content, Optional<List<ResourceLocation>> requirements) {
	public static final Codec<Whisper> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ComponentSerialization.CODEC.fieldOf("content").forGetter(Whisper::content),
		ResourceLocation.CODEC.listOf().optionalFieldOf("requirements").forGetter(Whisper::requirements)
	).apply(instance, Whisper::new));
}
