package cn.leolezury.eternalstarlight.common.client.gui.screens;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class CrestSelectionScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/crest_selection.png");
    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;
    private static final int GUI_RATIO = WIDTH / HEIGHT;

    public CrestSelectionScreen() {
        super(Component.empty());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        int currentGuiRatio = this.width / this.height * 2; // weird thing
        if (currentGuiRatio > GUI_RATIO) {
            guiGraphics.blit(BACKGROUND, 0, (this.height - this.width / GUI_RATIO) / 2, 0.0F, 0.0F, this.width, this.width / GUI_RATIO, this.width, this.width / GUI_RATIO);
        } else {
            guiGraphics.blit(BACKGROUND, (this.width - this.height * GUI_RATIO) / 2, 0, 0.0F, 0.0F, this.height * GUI_RATIO, this.height, this.height * GUI_RATIO, this.height);
        }
    }
}
