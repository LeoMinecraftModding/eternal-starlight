package cn.leolezury.eternalstarlight.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public record ESPaintingVariant(int width, int height, ResourceLocation texture, ResourceLocation backTexture, boolean sidesFromPainting) {
	public static final Codec<ESPaintingVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ExtraCodecs.intRange(1, 16).fieldOf("width").forGetter(ESPaintingVariant::width),
		ExtraCodecs.intRange(1, 16).fieldOf("height").forGetter(ESPaintingVariant::height),
		ResourceLocation.CODEC.fieldOf("texture").forGetter(ESPaintingVariant::texture),
		ResourceLocation.CODEC.fieldOf("back_texture").forGetter(ESPaintingVariant::backTexture),
		Codec.BOOL.optionalFieldOf("sides_from_painting", false).forGetter(ESPaintingVariant::sidesFromPainting)
	).apply(instance, ESPaintingVariant::new));

	public int area() {
		return this.width() * this.height();
	}
}
