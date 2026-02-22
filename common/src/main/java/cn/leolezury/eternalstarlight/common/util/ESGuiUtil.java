package cn.leolezury.eternalstarlight.common.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class ESGuiUtil {
	// from GuiGraphics, changed int -> float
	public static void blitFloat(GuiGraphics graphics, ResourceLocation resourceLocation, float x, float y, float width, float height, float texWidth, float texHeight) {
		innerBlitFloat(graphics, resourceLocation, x, x + width, y, y + height, width / texWidth, height / texHeight);
	}

	private static void innerBlitFloat(GuiGraphics graphics, ResourceLocation resourceLocation, float x, float xTo, float y, float yTo, float u, float v) {
		RenderSystem.setShaderTexture(0, resourceLocation);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		Matrix4f matrix4f = graphics.pose().last().pose();
		BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.addVertex(matrix4f, x, y, 0).setUv(0, 0);
		bufferBuilder.addVertex(matrix4f, x, yTo, 0).setUv(0, v);
		bufferBuilder.addVertex(matrix4f, xTo, yTo, 0).setUv(u, v);
		bufferBuilder.addVertex(matrix4f, xTo, y, 0).setUv(u, 0);
		BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
	}
}
