package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.SeekerModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Seeker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

@Environment(EnvType.CLIENT)
public class SeekerGlowLayer<T extends Seeker, M extends SeekerModel<T>> extends EyesLayer<T, M> {
	private static final RenderType GLOW = RenderType.eyes(EternalStarlight.id("textures/entity/seeker_glow.png"));

	public SeekerGlowLayer(RenderLayerParent<T, M> parent) {
		super(parent);
	}

	@Override
	public RenderType renderType() {
		return GLOW;
	}
}
