package cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone;

import cn.leolezury.eternalstarlight.common.data.ESEctostoneVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record EctostoneVariant(ResourceLocation texture, ResourceLocation textureFull, ResourceLocation glowTexture, ResourceLocation glowTextureFull, HolderSet<Block> stones, boolean skyFlying, HolderSet<Biome> biomes) {
	public static final Codec<EctostoneVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ResourceLocation.CODEC.fieldOf("texture").forGetter(EctostoneVariant::texture),
		ResourceLocation.CODEC.fieldOf("glow_texture").forGetter(EctostoneVariant::glowTexture),
		RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("stones", HolderSet.direct(List.of())).forGetter(EctostoneVariant::stones),
		Codec.BOOL.optionalFieldOf("sky_flying", false).forGetter(EctostoneVariant::skyFlying),
		RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(EctostoneVariant::biomes)
	).apply(instance, EctostoneVariant::new));

	public EctostoneVariant(ResourceLocation texture, ResourceLocation glowTexture, HolderSet<Block> stones, HolderSet<Biome> biomes) {
		this(texture, glowTexture, stones, false, biomes);
	}

	public EctostoneVariant(ResourceLocation texture, ResourceLocation glowTexture, HolderSet<Block> stones, boolean skyFlying, HolderSet<Biome> biomes) {
		this(texture, fullTextureId(texture), glowTexture, fullTextureId(glowTexture), stones, skyFlying, biomes);
	}

	private static ResourceLocation fullTextureId(ResourceLocation location) {
		return location.withPath((string) -> "textures/" + string + ".png");
	}

	public static List<Holder.Reference<EctostoneVariant>> getCandidates(RegistryAccess registryAccess, Holder<Biome> biome) {
		Registry<EctostoneVariant> registry = registryAccess.registryOrThrow(ESRegistries.ECTOSTONE_VARIANT);
		return registry.holders().filter(reference -> reference.value().biomes().contains(biome)).toList();
	}

	public boolean matchesStone(BlockGetter level, BlockPos pos) {
		for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-2, -3, -2), pos.offset(2, -1, 2))) {
			if (level.getBlockState(blockPos).is(stones)) {
				return true;
			}
		}
		return false;
	}

	public static Holder<EctostoneVariant> getDefault(RegistryAccess registryAccess) {
		Registry<EctostoneVariant> registry = registryAccess.registryOrThrow(ESRegistries.ECTOSTONE_VARIANT);
		return registry.getHolder(ESEctostoneVariants.GRIMSTONE).or(registry::getAny).orElseThrow();
	}

	public static Holder<EctostoneVariant> getSpawnVariant(RegistryAccess registryAccess, Holder<Biome> biome, BlockGetter level, BlockPos pos, RandomSource random) {
		List<Holder.Reference<EctostoneVariant>> candidates = getCandidates(registryAccess, biome);
		List<Holder.Reference<EctostoneVariant>> matching = candidates.stream().filter(candidate -> candidate.value().matchesStone(level, pos)).toList();
		if (!matching.isEmpty()) {
			return matching.get(random.nextInt(matching.size()));
		}
		if (!candidates.isEmpty()) {
			return candidates.get(random.nextInt(candidates.size()));
		}
		return getDefault(registryAccess);
	}
}
