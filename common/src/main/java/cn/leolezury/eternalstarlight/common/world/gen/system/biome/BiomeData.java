package cn.leolezury.eternalstarlight.common.world.gen.system.biome;

import net.minecraft.resources.ResourceLocation;

public record BiomeData(ResourceLocation name, ResourceLocation biome,
                        Temperature[] temperatures,
                        int height, int variance,
                        boolean hasBeaches, boolean hasRivers, boolean isOcean) {
    public enum Temperature {
        COLD_EXTREME,
        COLD,
        NEUTRAL,
        HOT,
        HOT_EXTREME
    }

    public boolean canHaveBeaches() {
        return (!this.isOcean) && this.hasBeaches;
    }

    public boolean canHaveRivers() {
        return (!this.isOcean) && this.hasRivers;
    }

    public static class Builder {
        private final ResourceLocation name;
        private final ResourceLocation biome;
        private Temperature[] temperatures = {Temperature.NEUTRAL};
        private final int height;
        private final int variance;
        private boolean hasBeaches = true;
        private boolean hasRivers = true;
        private boolean isOcean = false;

        public Builder(ResourceLocation name, int height, int variance) {
            this.name = name;
            this.biome = name;
            this.height = height;
            this.variance = variance;
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
            return new BiomeData(name, biome, temperatures, height, variance, hasBeaches, hasRivers, isOcean);
        }
    }
}
