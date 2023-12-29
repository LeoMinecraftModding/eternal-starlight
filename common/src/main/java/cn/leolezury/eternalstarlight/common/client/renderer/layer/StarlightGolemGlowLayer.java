package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarlightGolemModel;
import cn.leolezury.eternalstarlight.common.entity.boss.StarlightGolem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class StarlightGolemGlowLayer<T extends StarlightGolem, M extends StarlightGolemModel<T>> extends EyesLayer<T, M> {
    private static final RenderType GLOW = RenderType.eyes(new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/starlight_golem_glow.png"));

    public StarlightGolemGlowLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public RenderType renderType() {
        return GLOW;
    }
}
