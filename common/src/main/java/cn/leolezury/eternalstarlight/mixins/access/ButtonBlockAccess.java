package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ButtonBlock.class)
public interface ButtonBlockAccess {
    @Invoker("<init>")
    static ButtonBlock create(BlockBehaviour.Properties $$0, BlockSetType $$1, int $$2, boolean $$3) {
        throw new UnsupportedOperationException();
    }
}
