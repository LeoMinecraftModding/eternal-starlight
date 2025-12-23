package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.data.ESShimmerLacewingVariants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.Optional;

public record ShimmerLacewingVariant(HolderSet<Biome> biomes, ResourceLocation texture) {
	public static final Codec<ShimmerLacewingVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(ShimmerLacewingVariant::biomes),
		ResourceLocation.CODEC.fieldOf("texture").forGetter(ShimmerLacewingVariant::texture)
	).apply(instance, ShimmerLacewingVariant::new));

	public static Holder<ShimmerLacewingVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
		Registry<ShimmerLacewingVariant> registry = registryAccess.registryOrThrow(ESRegistries.SHIMMER_LACEWING_VARIANT);
		Optional<Holder.Reference<ShimmerLacewingVariant>> optional = registry.holders().filter(reference -> reference.value().biomes().contains(holder)).findFirst().or(() -> registry.getHolder(ESShimmerLacewingVariants.RIVER));
		Objects.requireNonNull(registry);
		return optional.or(registry::getAny).orElseThrow();
	}
}
