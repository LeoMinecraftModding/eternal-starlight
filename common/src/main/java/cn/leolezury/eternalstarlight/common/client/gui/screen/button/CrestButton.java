package cn.leolezury.eternalstarlight.common.client.gui.screen.button;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class CrestButton extends Button {
    private static final int CREST_WIDTH = 72;
    private static final int CREST_HEIGHT = 72;
    private Crest crest;

    private int prevHoverProgress;
    private int hoverProgress;

    private int prevX;
    private int prevY;

    public CrestButton(int x, int y, int width, int height, Component component, OnPress onPress) {
        super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
    }

    public CrestButton setCrest(Crest crest) {
        if (this.crest != crest && Minecraft.getInstance().level != null) {
            if (crest == null) {
                setTooltip(Tooltip.create(Component.empty()));
            } else {
                Registry<Crest> registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ESRegistries.CREST);
                MutableComponent nameComponent = Component.translatable(Util.makeDescriptionId("crest", registry.getKey(crest)));
                MutableComponent typeComponent = Component.translatable(Util.makeDescriptionId("mana_type", new ResourceLocation(EternalStarlight.MOD_ID, crest.type().getSerializedName()))).withColor(crest.type().getColor());
                MutableComponent descComponent = Component.translatable(Util.makeDescriptionId("crest", registry.getKey(crest)) + ".desc");
                setTooltip(Tooltip.create(nameComponent.append("\n").append(typeComponent).append("\n").append(descComponent)));
            }
        }
        this.crest = crest;
        return this;
    }

    public Crest getCrest() {
        return crest;
    }

    public boolean isEmpty() {
        return this.crest == null;
    }

    public void tick() {
        prevHoverProgress = hoverProgress;
        prevX = getX();
        prevY = getY();
        if (isHovered()) {
            if (hoverProgress < 5) {
                hoverProgress++;
            }
        } else {
            if (hoverProgress > 0) {
                hoverProgress--;
            }
        }
        this.active = crest != null;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        if (crest != null) {
            float partialTicks = Minecraft.getInstance().getFrameTime();
            float progress = (Mth.lerp(partialTicks, prevHoverProgress, hoverProgress) / 40f) + 1f;
            float width = CREST_WIDTH * progress;
            float height = CREST_HEIGHT * progress;
            blit(guiGraphics, crest.texture(), (Mth.lerp(partialTicks, prevX, getX()) - (width - getWidth()) / 2f), (Mth.lerp(partialTicks, prevY, getY()) - (height - getHeight()) / 2f), width, height, width, height);
        }
    }
    
    // from GuiGraphics, changed int -> float
    public void blit(GuiGraphics graphics, ResourceLocation resourceLocation, float x, float y, float width, float height, float texWidth, float texHeight) {
        this.innerBlit(graphics, resourceLocation, x, x + width, y, y + height, width / texWidth, height / texHeight);
    }
    
    void innerBlit(GuiGraphics graphics, ResourceLocation resourceLocation, float x, float xTo, float y, float yTo, float u, float v) {
        RenderSystem.setShaderTexture(0, resourceLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = graphics.pose().last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x, y, 0).uv(0, 0).endVertex();
        bufferBuilder.vertex(matrix4f, x, yTo, 0).uv(0, v).endVertex();
        bufferBuilder.vertex(matrix4f, xTo, yTo, 0).uv(u, v).endVertex();
        bufferBuilder.vertex(matrix4f, xTo, y, 0).uv(u, 0).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }
}
