package cn.leolezury.eternalstarlight.common.world.gen.areasystem.biomeprovider;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.HeightsContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.biome.BiomeDataRegistry;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.BiomeTransformers;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.HeightTransformers;

public class ESBiomeProvider extends AbstractBiomeProvider {
    public ESBiomeProvider(int maxHeight, int minHeight) {
        super(maxHeight, minHeight);
    }

    @Override
    int[] getLandBiomes() {
        return new int[]{BiomeDataRegistry.STARLIGHT_FOREST, BiomeDataRegistry.STARLIGHT_DENSE_FOREST, BiomeDataRegistry.DARK_SWAMP, BiomeDataRegistry.STARLIGHT_PERMAFROST_FOREST};
    }

    @Override
    int[] getOceanBiomes() {
        return new int[]{BiomeDataRegistry.STARLIT_SEA};
    }

    @Override
    void doBiomesTransformation(BiomesContainer container) {
        container.processData(BiomeTransformers.ADD_OCEAN, 0);
        container.processData(BiomeTransformers.APPLY_BIOMES, 0);
        container.doubleSize();
        doubleAndProcessEdges(container, 0);
        doubleAndProcessEdges(container, 1);
        doubleAndProcessEdges(container, 2);
        doubleAndProcessEdges(container, 3);
        doubleAndProcessEdges(container, 4);
        doubleAndProcessEdges(container, 5);
        container.processData(BiomeTransformers.ASSIMILATE_LONELY, 0);
        container.processData(BiomeTransformers.ADD_BEACHES, 0);
        container.processData(BiomeTransformers.ADD_RIVER, 0);
        for (int i = 0; i < 2; i++) {
            container.processData(BiomeTransformers.ADD_RIVER_TRANSITION, 0);
        }
        container.doubleSize();
        container.doubleSize();
    }

    @Override
    void doHeightsTransformation(HeightsContainer container) {
        container.processData(HeightTransformers.SMOOTH_LARGE, 0);
        container.processData(HeightTransformers.NOISE, 0);
        container.processData(HeightTransformers.SMOOTH_SMALL, 0);
    }
}
