package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarlightGolemModel;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.StarlightGolemRenderer;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

@Environment(EnvType.CLIENT)
public class StarlightGolemEyesLayer<T extends StarlightGolem, M extends StarlightGolemModel<T>> extends EyesLayer<T, M> {
    private static final RenderType EYES = RenderType.eyes(EternalStarlight.id("textures/entity/starlight_golem/starlight_golem_eyes.png"));
    private static final RenderType EYES_HALLOWEEN = RenderType.eyes(EternalStarlight.id("textures/entity/starlight_golem/starlight_golem_eyes_halloween.png"));

    public StarlightGolemEyesLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public RenderType renderType() {
        return StarlightGolemRenderer.isHalloween() ? EYES_HALLOWEEN : EYES;
    }
}
