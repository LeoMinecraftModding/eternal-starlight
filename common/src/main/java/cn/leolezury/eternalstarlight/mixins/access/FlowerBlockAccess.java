package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FlowerBlock.class)
public interface FlowerBlockAccess {
    @Invoker("<init>")
    static FlowerBlock create(MobEffect $$0, int $$1, BlockBehaviour.Properties $$2) {
        throw new UnsupportedOperationException();
    }
}
