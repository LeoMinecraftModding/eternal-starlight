package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemLaserBeam;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class StarlightGolemBeamRenderer extends LaserBeamRenderer<GolemLaserBeam> {
    private static final ResourceLocation TEXTURE = EternalStarlight.id("textures/entity/golem_laser_beam.png");

    public StarlightGolemBeamRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public float getBeamRadius() {
        return 0.75f;
    }

    @Override
    public ResourceLocation getTextureLocation(GolemLaserBeam entity) {
        return StarlightGolemBeamRenderer.TEXTURE;
    }
}
