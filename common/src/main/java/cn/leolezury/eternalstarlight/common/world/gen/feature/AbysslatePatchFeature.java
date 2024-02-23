package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AbysslatePatchFeature extends Feature<AbysslatePatchFeature.Configuration> {
    public AbysslatePatchFeature(Codec<Configuration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Configuration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        Configuration config = context.config();
        for (int x = -4; x <= 4; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -4; z <= 4; z++) {
                    if (ESMathUtil.isPointInEllipsoid(x, y, z, 5 + random.nextInt(3) - 1, 5 + random.nextInt(3) - 1, 5 + random.nextInt(3) - 1)) {
                        BlockPos placePos = pos.offset(x, y, z);
                        if (level.getBlockState(placePos).is(config.stone())) {
                            // then replace with our block
                            if (level.getBlockState(placePos.above()).isAir() || !level.getBlockState(placePos.above()).getFluidState().isEmpty()) {
                                if (random.nextInt(8) == 0) {
                                    setBlock(level, placePos, random.nextBoolean() ? config.magma().getState(random, placePos) : config.geyser().getState(random, placePos));
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public record Configuration(Block stone, BlockStateProvider magma, BlockStateProvider geyser) implements FeatureConfiguration {
        public static final Codec<AbysslatePatchFeature.Configuration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("stone").forGetter(AbysslatePatchFeature.Configuration::stone), BlockStateProvider.CODEC.fieldOf("magma").forGetter(AbysslatePatchFeature.Configuration::magma),  BlockStateProvider.CODEC.fieldOf("geyser").forGetter(AbysslatePatchFeature.Configuration::geyser)).apply(instance, AbysslatePatchFeature.Configuration::new));
    }
}
