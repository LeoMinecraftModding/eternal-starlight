package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemLaserBeam;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class StarlightGolemBeamRenderer extends LaserBeamRenderer<GolemLaserBeam> {
	private static final ResourceLocation TEXTURE = EternalStarlight.id("textures/entity/golem_laser_beam.png");

	public StarlightGolemBeamRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(GolemLaserBeam entity) {
		return TEXTURE;
	}
}
