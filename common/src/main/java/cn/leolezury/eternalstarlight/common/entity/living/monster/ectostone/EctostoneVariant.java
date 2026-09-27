package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.data.ESEctostoneVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.Optional;

public record EctostoneVariant(ResourceLocation texture, ResourceLocation textureFull, ResourceLocation glowTexture, ResourceLocation glowTextureFull, HolderSet<Biome> biomes) {
	public static final Codec<EctostoneVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ResourceLocation.CODEC.fieldOf("texture").forGetter(EctostoneVariant::texture),
		ResourceLocation.CODEC.fieldOf("glow_texture").forGetter(EctostoneVariant::glowTexture),
		RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(EctostoneVariant::biomes)
	).apply(instance, EctostoneVariant::new));

	public EctostoneVariant(ResourceLocation texture, ResourceLocation glowTexture, HolderSet<Biome> biomes) {
		this(texture, fullTextureId(texture), glowTexture, fullTextureId(glowTexture), biomes);
	}

	private static ResourceLocation fullTextureId(ResourceLocation location) {
		return location.withPath((string) -> "textures/" + string + ".png");
	}

	public static Holder<EctostoneVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
		Registry<EctostoneVariant> registry = registryAccess.registryOrThrow(ESRegistries.ECTOSTONE_VARIANT);
		Optional<Holder.Reference<EctostoneVariant>> optional = registry.holders().filter(reference -> reference.value().biomes().contains(holder)).findFirst().or(() -> registry.getHolder(ESEctostoneVariants.GRIMSTONE));
		Objects.requireNonNull(registry);
		return optional.or(registry::getAny).orElseThrow();
	}
}
