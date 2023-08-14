package cn.leolezury.eternalstarlight.mixins.access;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TrunkPlacerType.class)
public interface TrunkPlacerTypeAccess {
    @Invoker("<init>")
    static TrunkPlacerType create(Codec<?> $$0) {
        throw new UnsupportedOperationException();
    }
}
