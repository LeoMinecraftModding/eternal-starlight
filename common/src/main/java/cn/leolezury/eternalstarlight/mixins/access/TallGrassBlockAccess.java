package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TallGrassBlock.class)
public interface TallGrassBlockAccess {
    @Invoker("<init>")
    static TallGrassBlock create(BlockBehaviour.Properties $$0) {
        throw new UnsupportedOperationException();
    }
}
