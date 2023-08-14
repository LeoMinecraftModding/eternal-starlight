package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SaplingBlock.class)
public interface SaplingBlockAccess {
    @Invoker("<init>")
    static SaplingBlock createSpaling(AbstractTreeGrower $$0, BlockBehaviour.Properties $$1) {
        throw new UnsupportedOperationException();
    }
}
