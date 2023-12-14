package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class ESBushBlock extends BushBlock {
    public static final MapCodec<ESBushBlock> CODEC = simpleCodec(ESBushBlock::new);

    protected ESBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
