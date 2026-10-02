package cn.leolezury.eternalstarlight.common.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

// UV rect of an atlas sprite inset by half a texel
// the sprite border itself is exclusive and would sample the neighboring sprite, which shows up as a foreign colored strip on quads and ribbons
public record SpriteUv(float u0, float u1, float v0, float v1) {
	public static SpriteUv of(TextureAtlasSprite sprite) {
		float u0 = sprite.getU0();
		float u1 = sprite.getU1();
		float v0 = sprite.getV0();
		float v1 = sprite.getV1();
		float insetU = 0.5f * (u1 - u0) / sprite.contents().width();
		float insetV = 0.5f * (v1 - v0) / sprite.contents().height();
		return new SpriteUv(u0 + insetU, u1 - insetU, v0 + insetV, v1 - insetV);
	}
}
