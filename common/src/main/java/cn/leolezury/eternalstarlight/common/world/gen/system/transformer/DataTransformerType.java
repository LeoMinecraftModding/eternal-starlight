package cn.leolezury.eternalstarlight.common.world.gen.system.transformer;

import com.mojang.serialization.MapCodec;

public interface DataTransformerType<T extends DataTransformer> {
    MapCodec<T> codec();
}
