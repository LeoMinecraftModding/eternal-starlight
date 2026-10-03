package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.monster.ectostone.EctostoneVariant;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;

public class ESEctostoneVariants {
	public static final ResourceKey<EctostoneVariant> GRIMSTONE = create("grimstone");
	public static final ResourceKey<EctostoneVariant> VOIDSTONE = create("voidstone");
	public static final ResourceKey<EctostoneVariant> RADIANITE = create("radianite");
	public static final ResourceKey<EctostoneVariant> TOXITE = create("toxite");
	public static final ResourceKey<EctostoneVariant> SPRINGSTONE = create("springstone");

	public static void bootstrap(BootstrapContext<EctostoneVariant> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(GRIMSTONE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/grimstone"), EternalStarlight.id("entity/ectostone/grimstone_glow"), stones(
			ESBlocks.GRIMSTONE.get(), ESBlocks.COBBLED_GRIMSTONE.get(), ESBlocks.GLOWING_GRIMSTONE.get()), biomes.getOrThrow(ESTags.Biomes.IS_STARLIGHT)));
		context.register(VOIDSTONE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/voidstone"), EternalStarlight.id("entity/ectostone/voidstone_glow"), stones(
			ESBlocks.VOIDSTONE.get(), ESBlocks.COBBLED_VOIDSTONE.get(), ESBlocks.GLOWING_VOIDSTONE.get()), biomes.getOrThrow(ESTags.Biomes.IS_STARLIGHT)));
		context.register(RADIANITE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/radianite"), EternalStarlight.id("entity/ectostone/radianite_glow"), stones(
			ESBlocks.RADIANITE.get(), ESBlocks.COBBLED_RADIANITE.get()), true, HolderSet.direct(biomes.getOrThrow(ESBiomes.SOLARIS_ISLES))));
		context.register(TOXITE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/toxite"), EternalStarlight.id("entity/ectostone/toxite_glow"), stones(
			ESBlocks.TOXITE.get(), ESBlocks.THIOQUARTZ_BLOCK.get()), HolderSet.direct(biomes.getOrThrow(ESBiomes.ETHER_RIVER), biomes.getOrThrow(ESBiomes.TORREYA_FOREST))));
		context.register(SPRINGSTONE, new EctostoneVariant(EternalStarlight.id("entity/ectostone/springstone"), EternalStarlight.id("entity/ectostone/springstone_glow"), stones(
			ESBlocks.SPRINGSTONE.get(), ESBlocks.THERMAL_SPRINGSTONE.get()), biomes.getOrThrow(ESTags.Biomes.IS_STARLIGHT)));
	}

	private static HolderSet<Block> stones(Block... blocks) {
		return HolderSet.direct(Arrays.stream(blocks).map(Block::builtInRegistryHolder).toList());
	}

	public static ResourceKey<EctostoneVariant> create(String name) {
		return ResourceKey.create(ESRegistries.ECTOSTONE_VARIANT, EternalStarlight.id(name));
	}
}
