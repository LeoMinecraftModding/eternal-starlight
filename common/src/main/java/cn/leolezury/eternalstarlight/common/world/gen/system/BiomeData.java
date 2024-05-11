package cn.leolezury.eternalstarlight.common.world.gen.system;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public record BiomeData(Holder<Biome> biome,
                        Holder<Block> fluidBlock,
                        List<Temperature> temperatures,
                        int height, int variance,
                        boolean hasBeaches, boolean hasRivers, boolean isOcean) {
    public static final Codec<BiomeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Biome.CODEC.fieldOf("biome").forGetter(BiomeData::biome),
            BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("fluid").forGetter(BiomeData::fluidBlock),
            Temperature.CODEC.listOf().fieldOf("temperatures").forGetter(BiomeData::temperatures),
            Codec.INT.fieldOf("height").forGetter(BiomeData::height),
            Codec.INT.fieldOf("variance").forGetter(BiomeData::variance),
            Codec.BOOL.fieldOf("has_beaches").forGetter(BiomeData::hasBeaches),
            Codec.BOOL.fieldOf("has_rivers").forGetter(BiomeData::hasRivers),
            Codec.BOOL.fieldOf("is_ocean").forGetter(BiomeData::isOcean)
    ).apply(instance, BiomeData::new));

    public enum Temperature implements StringRepresentable {
        COLD_EXTREME("cold_extreme"),
        COLD("cold"),
        NEUTRAL("neutral"),
        HOT("hot"),
        HOT_EXTREME("hot_extreme");

        public static final Codec<Temperature> CODEC = StringRepresentable.fromEnum(Temperature::values);
        private final String name;

        Temperature(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public boolean canHaveBeaches() {
        return (!this.isOcean) && this.hasBeaches;
    }

    public boolean canHaveRivers() {
        return (!this.isOcean) && this.hasRivers;
    }

    public static class Builder {
        private final Holder<Biome> biome;
        private Holder<Block> fluidBlock = Blocks.WATER.builtInRegistryHolder();
        private final List<Temperature> temperatures = new ArrayList<>();
        private final int height;
        private final int variance;
        private boolean hasBeaches = true;
        private boolean hasRivers = true;
        private boolean isOcean = false;

        public Builder(Holder<Biome> biome, int height, int variance) {
            this.biome = biome;
            this.height = height;
            this.variance = variance;
        }

        public Builder withFluid(Holder<Block> fluid) {
            this.fluidBlock = fluid;
            return this;
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
            return new BiomeData(biome, fluidBlock, temperatures, height, variance, hasBeaches, hasRivers, isOcean);
        }
    }
}
