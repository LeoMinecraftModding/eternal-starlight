package cn.leolezury.eternalstarlight.common.client.shaders;

import net.minecraft.client.renderer.ShaderInstance;

public class ESShaders {
    private static ShaderInstance crestSelectionGui;
    private static ShaderInstance renderTypeMeteorRain;
    private static ShaderInstance renderTypeLaserBeam;
    private static ShaderInstance renderTypeStarlightPortal;

    public static ShaderInstance getCrestSelectionGui() {
        return crestSelectionGui;
    }

    public static void setCrestSelectionGui(ShaderInstance crestSelectionGui) {
        ESShaders.crestSelectionGui = crestSelectionGui;
    }

    public static ShaderInstance getRenderTypeMeteorRain() {
        return renderTypeMeteorRain;
    }

    public static void setRenderTypeMeteorRain(ShaderInstance renderTypeMeteorRain) {
        ESShaders.renderTypeMeteorRain = renderTypeMeteorRain;
    }

    public static ShaderInstance getRenderTypeLaserBeam() {
        return renderTypeLaserBeam;
    }

    public static void setRenderTypeLaserBeam(ShaderInstance renderTypeLaserBeam) {
        ESShaders.renderTypeLaserBeam = renderTypeLaserBeam;
    }

    public static ShaderInstance getRenderTypeStarlightPortal() {
        return renderTypeStarlightPortal;
    }

    public static void setRenderTypeStarlightPortal(ShaderInstance renderTypeStarlightPortal) {
        ESShaders.renderTypeStarlightPortal = renderTypeStarlightPortal;
    }
}
