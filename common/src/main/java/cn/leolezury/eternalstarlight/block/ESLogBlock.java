package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.modifier.ModifiedBlock;
import cn.leolezury.eternalstarlight.block.modifier.ModifierContainer;
import cn.leolezury.eternalstarlight.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ESLogBlock extends RotatedPillarBlock implements ModifiedBlock<RotatedPillarBlock> {
    private ModifierContainer<Block> modifierContainer;
    public ESLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ESLogBlock modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<Block>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
