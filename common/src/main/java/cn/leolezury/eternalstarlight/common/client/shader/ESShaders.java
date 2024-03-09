package cn.leolezury.eternalstarlight.common.client.shader;

import net.minecraft.client.renderer.ShaderInstance;

import java.util.Random;

public class ESShaders {
    private static ShaderInstance crestSelectionGui;
    private static ShaderInstance meteorRain;
    private static ShaderInstance renderTypeLaserBeam;
    private static ShaderInstance renderTypeStarlightPortal;
    private static ShaderInstance crystallineInfection;

    public static ShaderInstance getCrestSelectionGui() {
        return crestSelectionGui;
    }

    public static void setCrestSelectionGui(ShaderInstance crestSelectionGui) {
        ESShaders.crestSelectionGui = crestSelectionGui;
    }

    public static ShaderInstance getMeteorRain() {
        return meteorRain;
    }

    public static void setMeteorRain(ShaderInstance meteorRain) {
        ESShaders.meteorRain = meteorRain;
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

    public static ShaderInstance getCrystallineInfection() {
        return crystallineInfection;
    }

    public static void setCrystallineInfection(ShaderInstance crystallineInfection) {
        ESShaders.crystallineInfection = crystallineInfection;
    }
}
