package cn.leolezury.eternalstarlight.common.world.gen.system.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public record BiomeData(Holder<Biome> biome,
                        List<Temperature> temperatures,
                        int height, int variance,
                        boolean hasBeaches, boolean hasRivers, boolean isOcean) {
    public static final Codec<BiomeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Biome.CODEC.fieldOf("biome").forGetter(BiomeData::biome),
            Codec.STRING.xmap(s -> switch (s) {
                case "cold_extreme" -> Temperature.COLD_EXTREME;
                case "cold" -> Temperature.COLD;
                case "neutral" -> Temperature.NEUTRAL;
                case "hot" -> Temperature.HOT;
                case "hot_extreme" -> Temperature.HOT_EXTREME;
                default -> Temperature.NEUTRAL;
            }, temperature -> switch (temperature) {
                case COLD_EXTREME -> "cold_extreme";
                case COLD -> "cold";
                case NEUTRAL -> "neutral";
                case HOT -> "hot";
                case HOT_EXTREME -> "hot_extreme";
            }).listOf().fieldOf("temperatures").forGetter(BiomeData::temperatures),
            Codec.INT.fieldOf("height").forGetter(BiomeData::height),
            Codec.INT.fieldOf("variance").forGetter(BiomeData::variance),
            Codec.BOOL.fieldOf("has_beaches").forGetter(BiomeData::hasBeaches),
            Codec.BOOL.fieldOf("has_rivers").forGetter(BiomeData::hasRivers),
            Codec.BOOL.fieldOf("is_ocean").forGetter(BiomeData::isOcean)
    ).apply(instance, BiomeData::new));

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
        private final Holder<Biome> biome;
        private List<Temperature> temperatures = new ArrayList<>();
        private final int height;
        private final int variance;
        private boolean hasBeaches = true;
        private boolean hasRivers = true;
        private boolean isOcean = false;

        public Builder(Holder<Biome> biome, int height, int variance) {
            this.biome = biome;
            this.height = height;
            this.variance = variance;
            this.temperatures.add(Temperature.NEUTRAL);
        }

        public Builder withTemperatures(Temperature... temperatures) {
            this.temperatures.addAll(List.of(temperatures));
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
            return new BiomeData(biome, temperatures, height, variance, hasBeaches, hasRivers, isOcean);
        }
    }
}
