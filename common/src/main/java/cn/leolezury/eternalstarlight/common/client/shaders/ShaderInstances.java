package cn.leolezury.eternalstarlight.common.client.shaders;

import net.minecraft.client.renderer.ShaderInstance;

public class ShaderInstances {
    public static ShaderInstance CREST_SELECT_GUI;
    public static ShaderInstance METEOR_RAIN;
    public static ShaderInstance GOLEM_LASER;
    public static ShaderInstance ES_PORTAL;

    public static ShaderInstance getCrestSelectGui() {
        return CREST_SELECT_GUI;
    }
    public static ShaderInstance getMeteorRain() {
        return METEOR_RAIN;
    }
    public static ShaderInstance getGolemLaser() {
        return GOLEM_LASER;
    }

    public static ShaderInstance getEsPortal() {
        return ES_PORTAL;
    }
}
