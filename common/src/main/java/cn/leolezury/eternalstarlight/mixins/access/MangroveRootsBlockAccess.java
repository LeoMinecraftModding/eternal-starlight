package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MangroveRootsBlock.class)
public interface MangroveRootsBlockAccess {
    @Invoker("<init>")
    static MangroveRootsBlock create(BlockBehaviour.Properties $$0) {
        throw new UnsupportedOperationException();
    }
}
