package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.client.model.entity.CreteorModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Creteor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class CreteorPowerLayer<T extends Creteor> extends EnergySwirlLayer<T, CreteorModel<T>> {
	private static final ResourceLocation POWER_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper_armor.png");
	private final CreteorModel<T> model;

	public CreteorPowerLayer(RenderLayerParent<T, CreteorModel<T>> parent, EntityModelSet modelSet) {
		super(parent);
		this.model = new CreteorModel<>(modelSet.bakeLayer(CreteorModel.ARMOR_LOCATION));
	}

	@Override
	protected float xOffset(float f) {
		return f * 0.01F;
	}

	@Override
	protected ResourceLocation getTextureLocation() {
		return POWER_LOCATION;
	}

	@Override
	protected EntityModel<T> model() {
		return this.model;
	}
}
