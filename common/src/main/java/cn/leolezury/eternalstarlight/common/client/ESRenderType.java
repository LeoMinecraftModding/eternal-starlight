package cn.leolezury.eternalstarlight.common.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.shader.ESShaders;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public abstract class ESRenderType extends RenderType {
	public ESRenderType(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
		super(string, vertexFormat, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
	}

	public static RenderType laserBeam(ResourceLocation location) {
		return create(EternalStarlight.ID + ":laser_beam", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, RenderType.CompositeState.builder()
			.setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
			.setCullState(NO_CULL)
			.setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
			.setShaderState(new ShaderStateShard(ESShaders::getRenderTypeLaserBeam))
			.setLightmapState(LIGHTMAP)
			.createCompositeState(true));
	}

	public static RenderType portal() {
		return create(EternalStarlight.ID + ":starlight_portal", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, TRANSIENT_BUFFER_SIZE, true, true, RenderType.CompositeState.builder()
			.setShaderState(new ShaderStateShard(ESShaders::getRenderTypeStarlightPortal))
			.setTextureState(NO_TEXTURE)
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setCullState(NO_CULL)
			.setLightmapState(LIGHTMAP)
			.setOverlayState(OVERLAY)
			.createCompositeState(true));
	}

	public static RenderType particle() {
		return create(EternalStarlight.ID + ":particle", DefaultVertexFormat.PARTICLE, VertexFormat.Mode.QUADS, TRANSIENT_BUFFER_SIZE, true, true, RenderType.CompositeState.builder()
			.setShaderState(new ShaderStateShard(GameRenderer::getParticleShader))
			.setTextureState(new TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setCullState(NO_CULL)
			.setLightmapState(LIGHTMAP)
			.setOverlayState(OVERLAY)
			.createCompositeState(true));
	}

	public static RenderType glow(ResourceLocation location) {
		RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(location, false, false);
		RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setTextureState(textureStateShard).setShaderState(RENDERTYPE_BEACON_BEAM_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false);
		return create(EternalStarlight.ID + ":glow", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, compositeState);
	}
}
