package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.modifier.ModifiedBlock;
import cn.leolezury.eternalstarlight.block.modifier.ModifierContainer;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ESModifiedBlock extends Block implements ModifiedBlock<Block> {
    private ModifierContainer<Block> modifierContainer;
    public ESModifiedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ESModifiedBlock modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<Block>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
