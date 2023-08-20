package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.api.ModifierBlock;
import cn.leolezury.eternalstarlight.block.api.ModifierContainer;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ESModifierBlock extends Block implements ModifierBlock {
    private ModifierContainer modifierContainer;
    public ESModifierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ESModifierBlock modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
