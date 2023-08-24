package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.modifier.ModifiedBlock;
import cn.leolezury.eternalstarlight.block.modifier.ModifierContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;

import java.util.function.Consumer;

public class ESLeavesBlock extends LeavesBlock implements ModifiedBlock<LeavesBlock> {
    private ModifierContainer<Block> modifierContainer;
    public ESLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ESLeavesBlock modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<Block>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
