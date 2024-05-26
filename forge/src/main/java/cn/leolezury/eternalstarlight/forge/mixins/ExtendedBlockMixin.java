package cn.leolezury.eternalstarlight.forge.mixins;

import cn.leolezury.eternalstarlight.common.block.ExtendedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ExtendedBlock.class)
public interface ExtendedBlockMixin extends IBlockExtension {
    @Shadow
    boolean isSticky(BlockState state);

    @Shadow
    boolean canStickToEachOther(BlockState state, BlockState other);

    @Override
    default boolean isStickyBlock(BlockState state) {
        return isSticky(state);
    }

    @Override
    default boolean canStickTo(BlockState state, BlockState other) {
        return canStickToEachOther(state, other);
    }
}
