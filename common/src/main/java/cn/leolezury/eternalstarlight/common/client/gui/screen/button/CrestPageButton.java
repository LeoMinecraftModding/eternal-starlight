package cn.leolezury.eternalstarlight.common.client.gui.screen.button;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CrestPageButton extends Button {
    private static final ResourceLocation NEXT_PAGE_ENABLED = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/crest_selection/next_page_enabled.png");
    private static final ResourceLocation PREVIOUS_PAGE_ENABLED = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/crest_selection/previous_page_enabled.png");
    private static final ResourceLocation NEXT_PAGE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/crest_selection/next_page.png");
    private static final ResourceLocation PREVIOUS_PAGE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/crest_selection/previous_page.png");
    private final boolean nextPage;

    public CrestPageButton(int x, int y, int width, int height, boolean nextPage, Component component, OnPress onPress) {
        super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
        this.nextPage = nextPage;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blit(this.active ? (this.nextPage ? NEXT_PAGE_ENABLED : PREVIOUS_PAGE_ENABLED) : (this.nextPage ? NEXT_PAGE : PREVIOUS_PAGE), this.getX(), this.getY(), 0, 0, this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
