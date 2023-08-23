package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TrapDoorBlock.class)
public interface TrapDoorBlockAccess {
    @Invoker("<init>")
    static TrapDoorBlock create(BlockBehaviour.Properties properties, BlockSetType type) {
        throw new UnsupportedOperationException();
    }
}
