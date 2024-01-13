package cn.leolezury.eternalstarlight.common.world.gen.system.transformer;

import com.mojang.serialization.Codec;

public interface DataTransformerType<T extends DataTransformer> {
    Codec<T> codec();
}
