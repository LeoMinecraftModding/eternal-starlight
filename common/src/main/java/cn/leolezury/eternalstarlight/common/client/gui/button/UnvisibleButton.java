package cn.leolezury.eternalstarlight.common.client.gui.button;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class UnvisibleButton extends Button {
    public UnvisibleButton(int i, int j, int k, int l, Component component, OnPress onPress) {
        super(i, j, k, l, component, onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {

    }
}
