package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.GrimstoneGolemModel;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

public class GrimstoneGolemGlowLayer<T extends GrimstoneGolem, M extends GrimstoneGolemModel<T>> extends EyesLayer<T, M> {
	private static final RenderType GLOW = RenderType.eyes(EternalStarlight.id("textures/entity/grimstone_golem_glow.png"));

	public GrimstoneGolemGlowLayer(RenderLayerParent<T, M> parent) {
		super(parent);
	}

	@Override
	public RenderType renderType() {
		return GLOW;
	}
}
