package cn.leolezury.eternalstarlight.common.world.gen.areasystem.biome;

import cn.leolezury.eternalstarlight.common.world.gen.chunkgenerator.ESChunkGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public record BiomeData(ResourceLocation name, ResourceLocation biome,
                        Temperature[] temperatures,
                        int height, int variance, int maxValleyDepth,
                        boolean hasBeaches, boolean hasRivers, boolean isOcean,
                        BlockStateTransformer blockStateTransformer, ChunkGenHeightTransformer heightTransformer) {
    public enum Temperature {
        COLD_EXTREME,
        COLD,
        NEUTRAL,
        HOT,
        HOT_EXTREME
    }
    public interface BlockStateTransformer {
        BlockStateTransformer DEFAULT = (state, generator, x, y, z, surfaceHeight) -> state;
        BlockState apply(BlockState state, ESChunkGenerator generator, int x, int y, int z, int surfaceHeight);
    }
    public interface ChunkGenHeightTransformer {
        ChunkGenHeightTransformer DEFAULT = (generator, x, z, surfaceHeight) -> surfaceHeight;
        int apply(ESChunkGenerator generator, int x, int z, int surfaceHeight);
    }

    public boolean canHaveBeaches() {
        return !isOcean && this.hasBeaches;
    }

    public boolean canHaveRivers() {
        return !isOcean && this.hasRivers;
    }

    public static class Builder {
        private final ResourceLocation name;
        private final ResourceLocation biome;
        private Temperature[] temperatures = {Temperature.NEUTRAL};
        private final int height;
        private final int variance;
        private int maxValleyDepth;
        private boolean hasBeaches = true;
        private boolean hasRivers = true;
        private boolean isOcean = false;
        BlockStateTransformer blockStateTransformer = BlockStateTransformer.DEFAULT;
        ChunkGenHeightTransformer heightTransformer = ChunkGenHeightTransformer.DEFAULT;

        public Builder(ResourceLocation name, int height, int variance) {
            this.name = name;
            this.biome = name;
            this.height = height;
            this.variance = variance;
            this.maxValleyDepth = variance / 2;
        }

        public Builder(ResourceLocation name, ResourceLocation biome, int height, int variance) {
            this.name = name;
            this.biome = biome;
            this.height = height;
            this.variance = variance;
        }

        public Builder withTemperatures(Temperature... temperatures) {
            this.temperatures = temperatures;
            return this;
        }

        public Builder fullRangeTemperatures() {
            return this.withTemperatures(Temperature.COLD_EXTREME, Temperature.COLD, Temperature.NEUTRAL, Temperature.HOT, Temperature.HOT_EXTREME);
        }

        public Builder withMaxValleyDepth(int maxValleyDepth) {
            this.maxValleyDepth = maxValleyDepth;
            return this;
        }

        public Builder withBlockStateTransformer(BlockStateTransformer transformer) {
            this.blockStateTransformer = transformer;
            return this;
        }

        public Builder withHeightTransformer(ChunkGenHeightTransformer transformer) {
            this.heightTransformer = transformer;
            return this;
        }

        public Builder hasBeaches(boolean hasBeaches) {
            this.hasBeaches = hasBeaches;
            return this;
        }

        public Builder hasRivers(boolean hasRivers) {
            this.hasRivers = hasRivers;
            return this;
        }

        public Builder isOcean(boolean isOcean) {
            this.isOcean = isOcean;
            return this;
        }

        public BiomeData build() {
            return new BiomeData(name, biome, temperatures, height, variance, maxValleyDepth, hasBeaches, hasRivers, isOcean, blockStateTransformer, heightTransformer);
        }
    }
}
