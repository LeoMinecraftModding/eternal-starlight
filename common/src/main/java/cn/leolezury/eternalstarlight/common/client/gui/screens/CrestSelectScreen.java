package cn.leolezury.eternalstarlight.common.client.gui.screens;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CrestSelectScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/crest_selecting_screen.png");
    public CrestSelectScreen() {
        super(Component.empty());
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderBackground(guiGraphics, i, j, f);
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, f);
        guiGraphics.blit(BACKGROUND, 0, 0, -90, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
