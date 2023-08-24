package cn.leolezury.eternalstarlight.mixins.access;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoliagePlacerType.class)
public interface FoliagePlacerTypeAccess {
    @Invoker("<init>")
    static FoliagePlacerType create(Codec<?> $$1) {
        throw new UnsupportedOperationException();
    }
}
