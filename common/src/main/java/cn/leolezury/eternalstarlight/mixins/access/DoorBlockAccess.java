package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DoorBlock.class)
public interface DoorBlockAccess {
    @Invoker("<init>")
    static DoorBlock create(BlockBehaviour.Properties properties, BlockSetType type) {
        throw new UnsupportedOperationException();
    }
}
