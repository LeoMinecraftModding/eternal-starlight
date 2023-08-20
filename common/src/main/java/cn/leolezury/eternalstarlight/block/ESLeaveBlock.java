package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.api.ModifierBlock;
import cn.leolezury.eternalstarlight.block.api.ModifierContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;

import java.util.function.Consumer;

public class ESLeaveBlock extends LeavesBlock implements ModifierBlock {
    private ModifierContainer modifierContainer;
    public ESLeaveBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ESLeaveBlock modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
