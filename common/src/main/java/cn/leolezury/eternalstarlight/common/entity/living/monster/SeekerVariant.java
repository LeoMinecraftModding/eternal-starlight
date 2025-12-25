package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.data.ESSeekerVariants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.Optional;

public record SeekerVariant(HolderSet<Biome> biomes, ResourceLocation texture) {
	public static final Codec<SeekerVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(SeekerVariant::biomes),
		ResourceLocation.CODEC.fieldOf("texture").forGetter(SeekerVariant::texture)
	).apply(instance, SeekerVariant::new));

	public static Holder<SeekerVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> holder) {
		Registry<SeekerVariant> registry = registryAccess.registryOrThrow(ESRegistries.SEEKER_VARIANT);
		Optional<Holder.Reference<SeekerVariant>> optional = registry.holders().filter(reference -> reference.value().biomes().contains(holder)).findFirst().or(() -> registry.getHolder(ESSeekerVariants.LUNAR));
		Objects.requireNonNull(registry);
		return optional.or(registry::getAny).orElseThrow();
	}
}
