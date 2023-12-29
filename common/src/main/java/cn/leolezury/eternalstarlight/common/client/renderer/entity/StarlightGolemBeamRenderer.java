package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.beam.StarlightGolemBeam;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class StarlightGolemBeamRenderer extends AbstractLaserBeamRenderer<StarlightGolemBeam> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/starlight_golem_beam.png");

    public StarlightGolemBeamRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public float getBeamRadius() {
        return 0.75f;
    }

    @Override
    public float getStartRadius() {
        return 0.75f;
    }

    @Override
    public ResourceLocation getTextureLocation(StarlightGolemBeam entity) {
        return StarlightGolemBeamRenderer.TEXTURE;
    }
}
