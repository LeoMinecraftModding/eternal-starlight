package cn.leolezury.eternalstarlight.common.client.shaders;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;

public class ESShaderRenderType extends RenderType {
    public static final ShaderStateShard CREST_SELECT_GUI = new ShaderStateShard(ShaderInstances::getCrestSelectGui);

    public ESShaderRenderType(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
    }

    public static RenderType getCrestSelectGui() {
        CompositeState renderTypeState = CompositeState.builder().setShaderState(CREST_SELECT_GUI).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(BLOCK_SHEET_MIPPED).setLayeringState(LayeringStateShard.NO_LAYERING).createCompositeState(true);
        return create("crest_select_gui", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, true, true, renderTypeState);
    }

//    public static RenderType golemLaser(ResourceLocation arg, float f, float g) {
//    }
}
