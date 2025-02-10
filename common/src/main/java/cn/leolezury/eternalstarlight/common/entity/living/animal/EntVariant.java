package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.data.ESEntVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.Optional;

public record EntVariant(HolderSet<Biome> biomes, ResourceLocation texture) {
	public static final Codec<EntVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(EntVariant::biomes),
		ResourceLocation.CODEC.fieldOf("texture").forGetter(EntVariant::texture)
	).apply(instance, EntVariant::new));

	public static Holder<EntVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
		Registry<EntVariant> registry = registryAccess.registryOrThrow(ESRegistries.ENT_VARIANT);
		Optional<Holder.Reference<EntVariant>> optional = registry.holders().filter(reference -> reference.value().biomes().contains(holder)).findFirst().or(() -> registry.getHolder(ESEntVariants.LUNAR));
		Objects.requireNonNull(registry);
		return optional.or(registry::getAny).orElseThrow();
	}
}
