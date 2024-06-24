package cn.leolezury.eternalstarlight.common.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.shader.ESShaders;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
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
		return create(EternalStarlight.ID + ":starlight_portal", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 786432, true, true, RenderType.CompositeState.builder()
			.setCullState(NO_CULL)
			.setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
			.setOutputState(TRANSLUCENT_TARGET)
			.setShaderState(new ShaderStateShard(ESShaders::getRenderTypeStarlightPortal))
			.setLightmapState(LIGHTMAP)
			.setOverlayState(OVERLAY)
			.setWriteMaskState(COLOR_WRITE)
			.setDepthTestState(LEQUAL_DEPTH_TEST)
			.setLayeringState(VIEW_OFFSET_Z_LAYERING)
			.createCompositeState(true));
	}

	public static RenderType glow(ResourceLocation location) {
		RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(location, false, false);
		RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setTextureState(textureStateShard).setShaderState(RENDERTYPE_BEACON_BEAM_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false);
		return create(EternalStarlight.ID + ":glow", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, compositeState);
	}
}
