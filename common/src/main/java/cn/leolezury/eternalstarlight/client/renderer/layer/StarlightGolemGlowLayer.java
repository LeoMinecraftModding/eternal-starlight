package cn.leolezury.eternalstarlight.client.renderer.layer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.StarlightGolemModel;
import cn.leolezury.eternalstarlight.entity.boss.StarlightGolem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class StarlightGolemGlowLayer<T extends StarlightGolem, M extends StarlightGolemModel<T>> extends EyesLayer<T, M> {
    private static final RenderType GLOW = RenderType.eyes(new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/starlight_golem_glow.png"));

    public StarlightGolemGlowLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    public RenderType renderType() {
        return GLOW;
    }
}
