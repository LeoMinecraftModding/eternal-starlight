package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FlowerPotBlock.class)
public interface FlowerPotBlockAccess {
    @Invoker("<init>")
    static FlowerPotBlock create(Block $$0, BlockBehaviour.Properties $$1) {
        throw new UnsupportedOperationException();
    }
}
