package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public record BoarwarfType(Holder<Biome> biome, ResourceLocation texture) {
	public static final Codec<BoarwarfType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Biome.CODEC.fieldOf("biome").forGetter(BoarwarfType::biome),
		ResourceLocation.CODEC.fieldOf("texture").forGetter(BoarwarfType::texture)
	).apply(instance, BoarwarfType::new));
}
