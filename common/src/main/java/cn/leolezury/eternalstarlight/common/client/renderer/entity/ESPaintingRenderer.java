package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.data.ESPaintingVariant;
import cn.leolezury.eternalstarlight.common.entity.misc.ESPainting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.WeakHashMap;

public class ESPaintingRenderer extends EntityRenderer<ESPainting> {
	private static final float HALF_DEPTH = 0.0625F / 2.0F;
	private static final float PIXEL = 1.0F / 16.0F;
	private static final ResourceLocation PAINTING_ATLAS = ResourceLocation.withDefaultNamespace("textures/atlas/paintings.png");
	private static final Map<TextureAtlasSprite, AlphaMask> MASK_CACHE = new WeakHashMap<>();

	public ESPaintingRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	public static void clearCache() {
		MASK_CACHE.clear();
	}

	// [Vanilla copy] net.minecraft.client.renderer.entity.PaintingRenderer#render, extended for irregular silhouettes and per-variant back/side textures
	@Override
	public void render(ESPainting painting, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		ESPaintingVariant variant = painting.getVariant().value();
		TextureAtlas atlas = getPaintingAtlas();
		TextureAtlasSprite frontSprite = atlas.getSprite(variant.texture());
		TextureAtlasSprite backSprite = atlas.getSprite(variant.backTexture());
		boolean pixelAccurate = frontSprite.contents().width() == variant.width() * 16 && frontSprite.contents().height() == variant.height() * 16;
		if (pixelAccurate) {
			AlphaMask mask = MASK_CACHE.computeIfAbsent(frontSprite, ESPaintingRenderer::createMask);
			renderThickPainting(painting, entityYaw, variant, frontSprite, backSprite, mask, poseStack, buffer);
		} else {
			renderSolidPainting(painting, entityYaw, variant, frontSprite, backSprite, poseStack, buffer);
		}
		super.render(painting, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	// [Vanilla copy] net.minecraft.client.renderer.entity.PaintingRenderer#getTextureLocation
	@Override
	public ResourceLocation getTextureLocation(ESPainting painting) {
		return PAINTING_ATLAS;
	}

	private static TextureAtlas getPaintingAtlas() {
		return (TextureAtlas) Minecraft.getInstance().getTextureManager().getTexture(PAINTING_ATLAS);
	}

	private static AlphaMask createMask(TextureAtlasSprite sprite) {
		int pixelWidth = sprite.contents().width();
		int pixelHeight = sprite.contents().height();
		boolean[] opaque = new boolean[pixelWidth * pixelHeight];
		boolean hasTransparency = false;
		for (int pixelY = 0; pixelY < pixelHeight; pixelY++) {
			for (int pixelX = 0; pixelX < pixelWidth; pixelX++) {
				boolean solid = !sprite.contents().isTransparent(0, pixelX, pixelY);
				opaque[pixelY * pixelWidth + pixelX] = solid;
				hasTransparency |= !solid;
			}
		}
		return new AlphaMask(pixelWidth, pixelHeight, opaque, hasTransparency);
	}

	private void renderThickPainting(ESPainting painting, float entityYaw, ESPaintingVariant variant, TextureAtlasSprite frontSprite, TextureAtlasSprite backSprite, AlphaMask mask, PoseStack poseStack, MultiBufferSource buffer) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
		PoseStack.Pose pose = poseStack.last();

		int width = variant.width();
		int height = variant.height();
		float halfWidth = (float) (-width) / 2.0F;
		float halfHeight = (float) (-height) / 2.0F;
		int backWidth = backSprite.contents().width();
		int backHeight = backSprite.contents().height();

		VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(frontSprite.atlasLocation()));

		boolean[] blockHasPixels = new boolean[width * height];
		boolean[] blockFullyOpaque = new boolean[width * height];
		int[] blockLight = new int[width * height];
		boolean fullyOpaque = !mask.hasTransparency();
		for (int blockY = 0; blockY < height; blockY++) {
			for (int blockX = 0; blockX < width; blockX++) {
				int index = blockY * width + blockX;
				if (fullyOpaque) {
					blockHasPixels[index] = true;
					blockFullyOpaque[index] = true;
				} else {
					int opaqueCount = 0;
					for (int pixelY = blockY * 16; pixelY < blockY * 16 + 16; pixelY++) {
						for (int pixelX = blockX * 16; pixelX < blockX * 16 + 16; pixelX++) {
							if (isOpaqueAt(mask, pixelX, pixelY)) {
								opaqueCount++;
							}
						}
					}
					blockHasPixels[index] = opaqueCount > 0;
					blockFullyOpaque[index] = opaqueCount == 256;
				}
				blockLight[index] = getLight(painting, blockX, blockY, halfWidth, halfHeight);
			}
		}

		// Front faces
		for (int blockY = 0; blockY < height; blockY++) {
			for (int blockX = 0; blockX < width; blockX++) {
				if (!blockHasPixels[blockY * width + blockX]) {
					continue;
				}
				renderFrontBlock(pose, consumer, frontSprite, blockX, blockY, width, height, halfWidth, halfHeight, blockLight[blockY * width + blockX]);
			}
		}

		// Back faces
		if (isCameraBehind(painting)) {
			for (int blockY = 0; blockY < height; blockY++) {
				for (int blockX = 0; blockX < width; blockX++) {
					int index = blockY * width + blockX;
					if (!blockHasPixels[index]) {
						continue;
					}
					if (blockFullyOpaque[index]) {
						renderBackRect(pose, consumer, backSprite, backWidth, backHeight, blockX * 16, blockX * 16 + 16, blockY * 16, blockY * 16 + 16, halfWidth, halfHeight, blockLight[index]);
					} else {
						renderBackRuns(pose, consumer, mask, backSprite, backWidth, backHeight, blockX, blockY, halfWidth, halfHeight, blockLight[index]);
					}
				}
			}
		}

		renderSides(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, variant.sidesFromPainting(), halfWidth, halfHeight, blockLight, width);

		poseStack.popPose();
	}

	// [Vanilla copy] net.minecraft.client.renderer.entity.PaintingRenderer#renderPainting front face
	private void renderFrontBlock(PoseStack.Pose pose, VertexConsumer consumer, TextureAtlasSprite frontSprite, int blockX, int blockY, int width, int height, float halfWidth, float halfHeight, int light) {
		float x0 = halfWidth + blockX;
		float x1 = x0 + 1.0F;
		float y0 = halfHeight + blockY;
		float y1 = y0 + 1.0F;
		float uLeft = frontSprite.getU((float) (width - blockX) / width);
		float uRight = frontSprite.getU((float) (width - blockX - 1) / width);
		float vBottom = frontSprite.getV((float) (height - blockY) / height);
		float vTop = frontSprite.getV((float) (height - blockY - 1) / height);
		vertex(pose, consumer, x1, y0, uRight, vBottom, -HALF_DEPTH, 0, 0, -1, light);
		vertex(pose, consumer, x0, y0, uLeft, vBottom, -HALF_DEPTH, 0, 0, -1, light);
		vertex(pose, consumer, x0, y1, uLeft, vTop, -HALF_DEPTH, 0, 0, -1, light);
		vertex(pose, consumer, x1, y1, uRight, vTop, -HALF_DEPTH, 0, 0, -1, light);
	}

	private void renderBackRect(PoseStack.Pose pose, VertexConsumer consumer, TextureAtlasSprite backSprite, int backWidth, int backHeight, int minX, int maxX, int minY, int maxY, float halfWidth, float halfHeight, int light) {
		for (int pixelY = minY; pixelY < maxY; ) {
			int stripMaxY = Math.min(maxY, pixelY - (pixelY % backHeight) + backHeight);
			for (int pixelX = minX; pixelX < maxX; ) {
				int stripMaxX = Math.min(maxX, pixelX - (pixelX % backWidth) + backWidth);
				float x0 = halfWidth + pixelX * PIXEL;
				float x1 = halfWidth + stripMaxX * PIXEL;
				float y0 = halfHeight + pixelY * PIXEL;
				float y1 = halfHeight + stripMaxY * PIXEL;
				float uLeft = backSprite.getU((float) (pixelX % backWidth) / backWidth);
				float uRight = backSprite.getU((float) ((stripMaxX - 1) % backWidth + 1) / backWidth);
				int texelRowBottom = backHeight - 1 - (pixelY % backHeight);
				float vBottom = backSprite.getV((float) (texelRowBottom + 1) / backHeight);
				int texelRowTop = backHeight - 1 - ((stripMaxY - 1) % backHeight);
				float vTop = backSprite.getV((float) texelRowTop / backHeight);
				vertex(pose, consumer, x1, y1, uRight, vTop, HALF_DEPTH, 0, 0, 1, light);
				vertex(pose, consumer, x0, y1, uLeft, vTop, HALF_DEPTH, 0, 0, 1, light);
				vertex(pose, consumer, x0, y0, uLeft, vBottom, HALF_DEPTH, 0, 0, 1, light);
				vertex(pose, consumer, x1, y0, uRight, vBottom, HALF_DEPTH, 0, 0, 1, light);
				pixelX = stripMaxX;
			}
			pixelY = stripMaxY;
		}
	}

	private void renderBackRuns(PoseStack.Pose pose, VertexConsumer consumer, AlphaMask mask, TextureAtlasSprite backSprite, int backWidth, int backHeight, int blockX, int blockY, float halfWidth, float halfHeight, int light) {
		for (int pixelY = blockY * 16; pixelY < blockY * 16 + 16; pixelY++) {
			int runStart = -1;
			for (int pixelX = blockX * 16; pixelX <= blockX * 16 + 16; pixelX++) {
				boolean solid = pixelX < blockX * 16 + 16 && isOpaqueLocal(mask, pixelX, pixelY);
				if (solid && runStart < 0) {
					runStart = pixelX;
				} else if (!solid && runStart >= 0) {
					renderBackRect(pose, consumer, backSprite, backWidth, backHeight, runStart, pixelX, pixelY, pixelY + 1, halfWidth, halfHeight, light);
					runStart = -1;
				}
			}
		}
	}

	private void renderSides(PoseStack.Pose pose, VertexConsumer consumer, AlphaMask mask, TextureAtlasSprite frontSprite, TextureAtlasSprite backSprite, int backWidth, int backHeight, boolean sidesFromPainting, float halfWidth, float halfHeight, int[] blockLight, int width) {
		int pixelWidth = mask.pixelWidth();
		int pixelHeight = mask.pixelHeight();
		int blockWidth = pixelWidth / 16;
		int blockHeight = pixelHeight / 16;

		if (!mask.hasTransparency()) {
			renderOpaqueBoundarySides(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, halfWidth, halfHeight, blockLight, width, blockWidth, blockHeight);
			return;
		}

		// Left faces
		for (int pixelX = 0; pixelX < pixelWidth; pixelX++) {
			boolean atBorder = pixelX == 0;
			for (int blockY = 0; blockY < blockHeight; blockY++) {
				int light = blockLight[blockY * width + (pixelX / 16)];
				int runStart = -1;
				for (int pixelY = blockY * 16; pixelY <= blockY * 16 + 16; pixelY++) {
					boolean exposed = pixelY < blockY * 16 + 16 && isOpaqueLocal(mask, pixelX, pixelY) && (atBorder || !isOpaqueLocal(mask, pixelX - 1, pixelY));
					boolean wrap = exposed && runStart >= 0 && !sidesFromPainting && pixelY % backHeight == 0;
					if (exposed && !wrap && runStart < 0) {
						runStart = pixelY;
					} else if ((!exposed || wrap) && runStart >= 0) {
						renderVerticalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, pixelX, runStart, pixelY - 1, true, halfWidth, halfHeight, light);
						runStart = wrap ? pixelY : -1;
					}
				}
			}
		}

		// Right faces
		for (int pixelX = 0; pixelX < pixelWidth; pixelX++) {
			boolean atBorder = pixelX == pixelWidth - 1;
			for (int blockY = 0; blockY < blockHeight; blockY++) {
				int light = blockLight[blockY * width + (pixelX / 16)];
				int runStart = -1;
				for (int pixelY = blockY * 16; pixelY <= blockY * 16 + 16; pixelY++) {
					boolean exposed = pixelY < blockY * 16 + 16 && isOpaqueLocal(mask, pixelX, pixelY) && (atBorder || !isOpaqueLocal(mask, pixelX + 1, pixelY));
					boolean wrap = exposed && runStart >= 0 && !sidesFromPainting && pixelY % backHeight == 0;
					if (exposed && !wrap && runStart < 0) {
						runStart = pixelY;
					} else if ((!exposed || wrap) && runStart >= 0) {
						renderVerticalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, pixelX, runStart, pixelY - 1, false, halfWidth, halfHeight, light);
						runStart = wrap ? pixelY : -1;
					}
				}
			}
		}

		// Bottom faces
		for (int pixelY = 0; pixelY < pixelHeight; pixelY++) {
			boolean atBorder = pixelY == 0;
			for (int blockX = 0; blockX < blockWidth; blockX++) {
				int light = blockLight[(pixelY / 16) * width + blockX];
				int runStart = -1;
				for (int pixelX = blockX * 16; pixelX <= blockX * 16 + 16; pixelX++) {
					boolean exposed = pixelX < blockX * 16 + 16 && isOpaqueLocal(mask, pixelX, pixelY) && (atBorder || !isOpaqueLocal(mask, pixelX, pixelY - 1));
					boolean wrap = exposed && runStart >= 0 && !sidesFromPainting && pixelX % backWidth == 0;
					if (exposed && !wrap && runStart < 0) {
						runStart = pixelX;
					} else if ((!exposed || wrap) && runStart >= 0) {
						renderHorizontalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, pixelY, runStart, pixelX - 1, true, halfWidth, halfHeight, light);
						runStart = wrap ? pixelX : -1;
					}
				}
			}
		}

		// Top faces
		for (int pixelY = 0; pixelY < pixelHeight; pixelY++) {
			boolean atBorder = pixelY == pixelHeight - 1;
			for (int blockX = 0; blockX < blockWidth; blockX++) {
				int light = blockLight[(pixelY / 16) * width + blockX];
				int runStart = -1;
				for (int pixelX = blockX * 16; pixelX <= blockX * 16 + 16; pixelX++) {
					boolean exposed = pixelX < blockX * 16 + 16 && isOpaqueLocal(mask, pixelX, pixelY) && (atBorder || !isOpaqueLocal(mask, pixelX, pixelY + 1));
					boolean wrap = exposed && runStart >= 0 && !sidesFromPainting && pixelX % backWidth == 0;
					if (exposed && !wrap && runStart < 0) {
						runStart = pixelX;
					} else if ((!exposed || wrap) && runStart >= 0) {
						renderHorizontalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, pixelY, runStart, pixelX - 1, false, halfWidth, halfHeight, light);
						runStart = wrap ? pixelX : -1;
					}
				}
			}
		}
	}

	private void renderOpaqueBoundarySides(PoseStack.Pose pose, VertexConsumer consumer, AlphaMask mask, TextureAtlasSprite frontSprite, TextureAtlasSprite backSprite, int backWidth, int backHeight, boolean sidesFromPainting, float halfWidth, float halfHeight, int[] blockLight, int width, int blockWidth, int blockHeight) {
		for (int blockY = 0; blockY < blockHeight; blockY++) {
			renderOpaqueVerticalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, 0, blockY, true, halfWidth, halfHeight, blockLight[blockY * width]);
			renderOpaqueVerticalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, mask.pixelWidth() - 1, blockY, false, halfWidth, halfHeight, blockLight[blockY * width + width - 1]);
		}
		for (int blockX = 0; blockX < blockWidth; blockX++) {
			renderOpaqueHorizontalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, 0, blockX, true, halfWidth, halfHeight, blockLight[blockX]);
			renderOpaqueHorizontalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, sidesFromPainting, mask.pixelHeight() - 1, blockX, false, halfWidth, halfHeight, blockLight[(blockHeight - 1) * width + blockX]);
		}
	}

	private void renderOpaqueVerticalSide(PoseStack.Pose pose, VertexConsumer consumer, AlphaMask mask, TextureAtlasSprite frontSprite, TextureAtlasSprite backSprite, int backWidth, int backHeight, boolean sidesFromPainting, int pixelX, int blockY, boolean left, float halfWidth, float halfHeight, int light) {
		int startY = blockY * 16;
		int endY = startY + 16;
		if (sidesFromPainting) {
			renderVerticalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, true, pixelX, startY, endY - 1, left, halfWidth, halfHeight, light);
			return;
		}
		for (int y = startY; y < endY; ) {
			int stripEndY = Math.min(endY, y - (y % backHeight) + backHeight);
			renderVerticalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, false, pixelX, y, stripEndY - 1, left, halfWidth, halfHeight, light);
			y = stripEndY;
		}
	}

	private void renderOpaqueHorizontalSide(PoseStack.Pose pose, VertexConsumer consumer, AlphaMask mask, TextureAtlasSprite frontSprite, TextureAtlasSprite backSprite, int backWidth, int backHeight, boolean sidesFromPainting, int pixelY, int blockX, boolean bottom, float halfWidth, float halfHeight, int light) {
		int startX = blockX * 16;
		int endX = startX + 16;
		if (sidesFromPainting) {
			renderHorizontalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, true, pixelY, startX, endX - 1, bottom, halfWidth, halfHeight, light);
			return;
		}
		for (int x = startX; x < endX; ) {
			int stripEndX = Math.min(endX, x - (x % backWidth) + backWidth);
			renderHorizontalSide(pose, consumer, mask, frontSprite, backSprite, backWidth, backHeight, false, pixelY, x, stripEndX - 1, bottom, halfWidth, halfHeight, light);
			x = stripEndX;
		}
	}

	// [Vanilla copy] net.minecraft.client.renderer.entity.PaintingRenderer#renderPainting, used when the front texture resolution does not match the variant
	private void renderSolidPainting(ESPainting painting, float entityYaw, ESPaintingVariant variant, TextureAtlasSprite frontSprite, TextureAtlasSprite backSprite, PoseStack poseStack, MultiBufferSource buffer) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
		PoseStack.Pose pose = poseStack.last();
		int width = variant.width();
		int height = variant.height();
		float f = (float) (-width) / 2.0F;
		float f1 = (float) (-height) / 2.0F;
		float f3 = backSprite.getU0();
		float f4 = backSprite.getU1();
		float f5 = backSprite.getV0();
		float f6 = backSprite.getV1();
		float f7 = backSprite.getU0();
		float f8 = backSprite.getU1();
		float f9 = backSprite.getV0();
		float f10 = backSprite.getV(0.0625F);
		float f11 = backSprite.getU0();
		float f12 = backSprite.getU(0.0625F);
		float f13 = backSprite.getV0();
		float f14 = backSprite.getV1();
		double d0 = 1.0 / (double) width;
		double d1 = 1.0 / (double) height;
		VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(frontSprite.atlasLocation()));
		boolean renderBack = isCameraBehind(painting);
		for (int blockX = 0; blockX < width; blockX++) {
			for (int blockY = 0; blockY < height; blockY++) {
				float f15 = f + (float) (blockX + 1);
				float f16 = f + (float) blockX;
				float f17 = f1 + (float) (blockY + 1);
				float f18 = f1 + (float) blockY;
				int light = getLight(painting, blockX, blockY, f, f1);
				float f19 = frontSprite.getU((float) (d0 * (double) (width - blockX)));
				float f20 = frontSprite.getU((float) (d0 * (double) (width - (blockX + 1))));
				float f21 = frontSprite.getV((float) (d1 * (double) (height - blockY)));
				float f22 = frontSprite.getV((float) (d1 * (double) (height - (blockY + 1))));
				vertex(pose, consumer, f15, f18, f20, f21, -HALF_DEPTH, 0, 0, -1, light);
				vertex(pose, consumer, f16, f18, f19, f21, -HALF_DEPTH, 0, 0, -1, light);
				vertex(pose, consumer, f16, f17, f19, f22, -HALF_DEPTH, 0, 0, -1, light);
				vertex(pose, consumer, f15, f17, f20, f22, -HALF_DEPTH, 0, 0, -1, light);
				if (renderBack) {
					vertex(pose, consumer, f15, f17, f4, f5, HALF_DEPTH, 0, 0, 1, light);
					vertex(pose, consumer, f16, f17, f3, f5, HALF_DEPTH, 0, 0, 1, light);
					vertex(pose, consumer, f16, f18, f3, f6, HALF_DEPTH, 0, 0, 1, light);
					vertex(pose, consumer, f15, f18, f4, f6, HALF_DEPTH, 0, 0, 1, light);
				}
				vertex(pose, consumer, f15, f17, f7, f9, -HALF_DEPTH, 0, 1, 0, light);
				vertex(pose, consumer, f16, f17, f8, f9, -HALF_DEPTH, 0, 1, 0, light);
				vertex(pose, consumer, f16, f17, f8, f10, HALF_DEPTH, 0, 1, 0, light);
				vertex(pose, consumer, f15, f17, f7, f10, HALF_DEPTH, 0, 1, 0, light);
				vertex(pose, consumer, f15, f18, f7, f9, HALF_DEPTH, 0, -1, 0, light);
				vertex(pose, consumer, f16, f18, f8, f9, HALF_DEPTH, 0, -1, 0, light);
				vertex(pose, consumer, f16, f18, f8, f10, -HALF_DEPTH, 0, -1, 0, light);
				vertex(pose, consumer, f15, f18, f7, f10, -HALF_DEPTH, 0, -1, 0, light);
				vertex(pose, consumer, f15, f17, f12, f13, HALF_DEPTH, -1, 0, 0, light);
				vertex(pose, consumer, f15, f18, f12, f14, HALF_DEPTH, -1, 0, 0, light);
				vertex(pose, consumer, f15, f18, f11, f14, -HALF_DEPTH, -1, 0, 0, light);
				vertex(pose, consumer, f15, f17, f11, f13, -HALF_DEPTH, -1, 0, 0, light);
				vertex(pose, consumer, f16, f17, f12, f13, -HALF_DEPTH, 1, 0, 0, light);
				vertex(pose, consumer, f16, f18, f12, f14, -HALF_DEPTH, 1, 0, 0, light);
				vertex(pose, consumer, f16, f18, f11, f14, HALF_DEPTH, 1, 0, 0, light);
				vertex(pose, consumer, f16, f17, f11, f13, HALF_DEPTH, 1, 0, 0, light);
			}
		}
		poseStack.popPose();
	}

	private void renderVerticalSide(PoseStack.Pose pose, VertexConsumer consumer, AlphaMask mask, TextureAtlasSprite frontSprite, TextureAtlasSprite backSprite, int backWidth, int backHeight, boolean sidesFromPainting, int pixelX, int runMinY, int runMaxY, boolean left, float halfWidth, float halfHeight, int light) {
		int pixelWidth = mask.pixelWidth();
		int pixelHeight = mask.pixelHeight();
		float x = halfWidth + (pixelX + (left ? 0 : 1)) * PIXEL;
		float y0 = halfHeight + runMinY * PIXEL;
		float y1 = halfHeight + (runMaxY + 1) * PIXEL;
		float uFront;
		float uBack;
		float vBottom;
		float vTop;
		if (sidesFromPainting) {
			int textureX = pixelWidth - 1 - pixelX;
			uFront = frontSprite.getU((float) (textureX + 1) / pixelWidth);
			uBack = frontSprite.getU((float) textureX / pixelWidth);
			vBottom = frontSprite.getV((float) (pixelHeight - runMinY) / pixelHeight);
			vTop = frontSprite.getV((float) (pixelHeight - 1 - runMaxY) / pixelHeight);
		} else {
			int texelX = pixelX % backWidth;
			uFront = backSprite.getU((float) texelX / backWidth);
			uBack = backSprite.getU((float) (texelX + 1) / backWidth);
			int texelBottom = backHeight - 1 - (runMinY % backHeight);
			vBottom = backSprite.getV((float) (texelBottom + 1) / backHeight);
			int texelTop = backHeight - 1 - (runMaxY % backHeight);
			vTop = backSprite.getV((float) texelTop / backHeight);
		}
		int normalX = left ? -1 : 1;
		vertex(pose, consumer, x, y0, uFront, vBottom, -HALF_DEPTH, normalX, 0, 0, light);
		vertex(pose, consumer, x, y1, uFront, vTop, -HALF_DEPTH, normalX, 0, 0, light);
		vertex(pose, consumer, x, y1, uBack, vTop, HALF_DEPTH, normalX, 0, 0, light);
		vertex(pose, consumer, x, y0, uBack, vBottom, HALF_DEPTH, normalX, 0, 0, light);
	}

	private void renderHorizontalSide(PoseStack.Pose pose, VertexConsumer consumer, AlphaMask mask, TextureAtlasSprite frontSprite, TextureAtlasSprite backSprite, int backWidth, int backHeight, boolean sidesFromPainting, int pixelY, int runMinX, int runMaxX, boolean bottom, float halfWidth, float halfHeight, int light) {
		int pixelWidth = mask.pixelWidth();
		int pixelHeight = mask.pixelHeight();
		float y = halfHeight + (pixelY + (bottom ? 0 : 1)) * PIXEL;
		float x0 = halfWidth + runMinX * PIXEL;
		float x1 = halfWidth + (runMaxX + 1) * PIXEL;
		float uLeft;
		float uRight;
		float vFront;
		float vBack;
		if (sidesFromPainting) {
			int textureY = pixelHeight - 1 - pixelY;
			uLeft = frontSprite.getU((float) (pixelWidth - runMinX) / pixelWidth);
			uRight = frontSprite.getU((float) (pixelWidth - 1 - runMaxX) / pixelWidth);
			vFront = frontSprite.getV((float) (textureY + 1) / pixelHeight);
			vBack = frontSprite.getV((float) textureY / pixelHeight);
		} else {
			int texelY = backHeight - 1 - (pixelY % backHeight);
			uLeft = backSprite.getU((float) (runMinX % backWidth) / backWidth);
			uRight = backSprite.getU((float) ((runMaxX % backWidth) + 1) / backWidth);
			vFront = backSprite.getV((float) (texelY + 1) / backHeight);
			vBack = backSprite.getV((float) texelY / backHeight);
		}
		int normalY = bottom ? -1 : 1;
		if (bottom) {
			vertex(pose, consumer, x0, y, uLeft, vFront, -HALF_DEPTH, 0, normalY, 0, light);
			vertex(pose, consumer, x1, y, uRight, vFront, -HALF_DEPTH, 0, normalY, 0, light);
			vertex(pose, consumer, x1, y, uRight, vBack, HALF_DEPTH, 0, normalY, 0, light);
			vertex(pose, consumer, x0, y, uLeft, vBack, HALF_DEPTH, 0, normalY, 0, light);
		} else {
			vertex(pose, consumer, x0, y, uLeft, vBack, -HALF_DEPTH, 0, normalY, 0, light);
			vertex(pose, consumer, x1, y, uRight, vBack, -HALF_DEPTH, 0, normalY, 0, light);
			vertex(pose, consumer, x1, y, uRight, vFront, HALF_DEPTH, 0, normalY, 0, light);
			vertex(pose, consumer, x0, y, uLeft, vFront, HALF_DEPTH, 0, normalY, 0, light);
		}
	}

	private boolean isCameraBehind(ESPainting painting) {
		Direction facing = painting.getDirection();
		Vec3 toCamera = this.entityRenderDispatcher.camera.getPosition().subtract(painting.position());
		return toCamera.dot(Vec3.atLowerCornerOf(facing.getNormal())) < 0.0;
	}

	// [Vanilla copy] net.minecraft.client.renderer.entity.PaintingRenderer#renderPainting light lookup
	private int getLight(ESPainting painting, int blockX, int blockY, float halfWidth, float halfHeight) {
		float x0 = halfWidth + blockX;
		float x1 = x0 + 1.0F;
		float y0 = halfHeight + blockY;
		float y1 = y0 + 1.0F;
		float centerX = (x1 + x0) / 2.0F;
		int posX = painting.getBlockX();
		int posY = Mth.floor(painting.getY() + (y1 + y0) / 2.0F);
		int posZ = painting.getBlockZ();
		Direction direction = painting.getDirection();
		if (direction == Direction.NORTH) {
			posX = Mth.floor(painting.getX() + centerX);
		}
		if (direction == Direction.WEST) {
			posZ = Mth.floor(painting.getZ() - centerX);
		}
		if (direction == Direction.SOUTH) {
			posX = Mth.floor(painting.getX() - centerX);
		}
		if (direction == Direction.EAST) {
			posZ = Mth.floor(painting.getZ() + centerX);
		}
		return LevelRenderer.getLightColor(painting.level(), new BlockPos(posX, posY, posZ));
	}

	private static boolean isOpaqueAt(AlphaMask mask, int pixelX, int pixelY) {
		return mask.opaque()[pixelY * mask.pixelWidth() + pixelX];
	}

	private static boolean isOpaqueLocal(AlphaMask mask, int pixelX, int pixelY) {
		if (pixelX < 0 || pixelY < 0 || pixelX >= mask.pixelWidth() || pixelY >= mask.pixelHeight()) {
			return false;
		}
		return isOpaqueAt(mask, mask.pixelWidth() - 1 - pixelX, mask.pixelHeight() - 1 - pixelY);
	}

	// [Vanilla copy] net.minecraft.client.renderer.entity.PaintingRenderer#vertex
	private void vertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float u, float v, float z, int normalX, int normalY, int normalZ, int packedLight) {
		consumer.addVertex(pose, x, y, z)
			.setColor(-1)
			.setUv(u, v)
			.setOverlay(OverlayTexture.NO_OVERLAY)
			.setLight(packedLight)
			.setNormal(pose, (float) normalX, (float) normalY, (float) normalZ);
	}

	private record AlphaMask(int pixelWidth, int pixelHeight, boolean[] opaque, boolean hasTransparency) {
	}
}
