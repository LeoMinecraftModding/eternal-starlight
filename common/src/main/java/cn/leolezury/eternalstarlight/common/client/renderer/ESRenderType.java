package cn.leolezury.eternalstarlight.common.client.renderer;

import cn.leolezury.eternalstarlight.common.client.shaders.ShaderInstances;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public abstract class ESRenderType extends RenderType {
    public ESRenderType(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(string, vertexFormat, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    public static RenderType glow(ResourceLocation location, float f, float g) {
        RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(location, false, false);
        return create("golem_laser", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, RenderType.CompositeState.builder()
                .setShaderState(new ShaderStateShard(ShaderInstances::getGolemLaser))
                .setTextureState(textureStateShard)
                .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(f, g))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .setWriteMaskState(COLOR_WRITE)
                .setOverlayState(NO_OVERLAY)
                .createCompositeState(false)
        );
//        return create("glow_effect", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, compositeState);
    }

    public static RenderType portal(ResourceLocation location, ResourceLocation location2) {
         return create("es_portal", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 1536, false, false, RenderType.CompositeState.builder()
                 .setShaderState(new ShaderStateShard(ShaderInstances::getEsPortal))
                 .setTextureState(MultiTextureStateShard.builder()
                         .add(location, false, false)
                         .add(location2, false, false).build())
                 .createCompositeState(false));
    }
}
