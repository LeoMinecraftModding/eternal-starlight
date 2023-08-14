package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PressurePlateBlock.class)
public interface PressurePlateBlockAccess {
    @Invoker("<init>")
    static PressurePlateBlock createPressurePlateBlock(PressurePlateBlock.Sensitivity $$0, BlockBehaviour.Properties properties, BlockSetType type) {
        throw new UnsupportedOperationException();
    }
}
