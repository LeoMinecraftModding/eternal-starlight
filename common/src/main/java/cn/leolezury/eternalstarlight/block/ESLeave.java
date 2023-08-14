package cn.leolezury.eternalstarlight.block;

import cn.leolezury.eternalstarlight.block.interfaces.ModifierBlock;
import cn.leolezury.eternalstarlight.block.interfaces.ModifierContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;

import java.util.function.Consumer;

public class ESLeave extends LeavesBlock implements ModifierBlock {
    private ModifierContainer modifierContainer;
    public ESLeave(Properties properties) {
        super(properties);
    }

    @Override
    public ESLeave modifiers(Consumer... modifiers) {
        modifierContainer = new ModifierContainer<>(this, modifiers);
        return this;
    }

    @Override
    public ModifierContainer<Block> getModifierContainer() {
        return modifierContainer;
    }
}
