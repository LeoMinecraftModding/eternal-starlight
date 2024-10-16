package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESWorldCarvers;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class ESConfiguredWorldCarvers {
	public static final ResourceKey<ConfiguredWorldCarver<?>> CAVES = create("caves");

	public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
		HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);
		context.register(CAVES, ESWorldCarvers.CAVES.get().configured(new CaveCarverConfiguration(0.5F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), blockHolderGetter.getOrThrow(ESTags.Blocks.STARLIGHT_CARVER_REPLACEABLES), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
	}

	private static ResourceKey<ConfiguredWorldCarver<?>> create(String name) {
		return ResourceKey.create(Registries.CONFIGURED_CARVER, EternalStarlight.id(name));
	}
}
