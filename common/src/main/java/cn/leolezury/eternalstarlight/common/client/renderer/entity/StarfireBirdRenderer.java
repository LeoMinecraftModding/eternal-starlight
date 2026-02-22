package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarfireBirdModel;
import cn.leolezury.eternalstarlight.common.entity.living.animal.StarfireBird;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class StarfireBirdRenderer<T extends StarfireBird> extends MobRenderer<T, StarfireBirdModel<T>> {
	private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/starfire_bird.png");
	private static final ResourceLocation SPECIAL_TEXTURE = EternalStarlight.id("textures/entity/starfire_bird_special.png");
	private static final ResourceLocation BABY_TEXTURE = EternalStarlight.id("textures/entity/starfire_bird_baby.png");
	private static final ResourceLocation BABY_SPECIAL_TEXTURE = EternalStarlight.id("textures/entity/starfire_bird_baby_special.png");

	private final StarfireBirdModel<T> adultModel;
	private final StarfireBirdModel<T> babyModel;

	public StarfireBirdRenderer(EntityRendererProvider.Context context) {
		super(context, new StarfireBirdModel<>(context.bakeLayer(StarfireBirdModel.ADULT_LOCATION)), 0.15f);
		adultModel = getModel();
		babyModel = new StarfireBirdModel<>(context.bakeLayer(StarfireBirdModel.BABY_LOCATION));
	}

	@Override
	public void render(T entity, float yaw, float delta, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
		model = entity.isBaby() ? babyModel : adultModel;
		super.render(entity, yaw, delta, stack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return entity.isBaby() ? (entity.isSpecialVariant() ? BABY_SPECIAL_TEXTURE : BABY_TEXTURE) : (entity.isSpecialVariant() ? SPECIAL_TEXTURE : ENTITY_TEXTURE);
	}
}
