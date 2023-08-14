package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.interfaces.ModifierBlock;
import cn.leolezury.eternalstarlight.block.interfaces.ModifierContainer;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ESBlock extends Block implements ModifierBlock {
    private ModifierContainer modifierContainer;

    public ESBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ESBlock modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
