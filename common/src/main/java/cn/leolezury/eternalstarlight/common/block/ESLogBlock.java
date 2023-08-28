package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.modifier.ModifiedBlock;
import cn.leolezury.eternalstarlight.common.block.modifier.ModifierContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

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
