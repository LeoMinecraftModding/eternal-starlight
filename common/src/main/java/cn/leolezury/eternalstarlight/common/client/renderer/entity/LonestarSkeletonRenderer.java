package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LonestarSkeletonModel;
import cn.leolezury.eternalstarlight.common.entity.living.monster.LonestarSkeleton;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class LonestarSkeletonRenderer<T extends LonestarSkeleton> extends HumanoidMobRenderer<T, LonestarSkeletonModel<T>> {
	private static final ResourceLocation LONESTAR_SKELETON_LOCATION = EternalStarlight.id("textures/entity/lonestar_skeleton.png");

	public static final ModelLayerLocation LONESTAR = new ModelLayerLocation(EternalStarlight.id("lonestar_skeleton"), "main");
	public static final ModelLayerLocation LONESTAR_INNER_ARMOR = new ModelLayerLocation(EternalStarlight.id("lonestar_skeleton"), "inner_armor");
	public static final ModelLayerLocation LONESTAR_OUTER_ARMOR = new ModelLayerLocation(EternalStarlight.id("lonestar_skeleton"), "outer_armor");

	public LonestarSkeletonRenderer(EntityRendererProvider.Context context) {
		this(context, LONESTAR, LONESTAR_INNER_ARMOR, LONESTAR_OUTER_ARMOR);
	}

	public LonestarSkeletonRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, ModelLayerLocation innerLayer, ModelLayerLocation outerLayer) {
		this(context, innerLayer, outerLayer, new LonestarSkeletonModel<>(context.bakeLayer(layer)));
	}

	public LonestarSkeletonRenderer(EntityRendererProvider.Context context, ModelLayerLocation innerLayer, ModelLayerLocation outerLayer, LonestarSkeletonModel<T> skeletonModel) {
		super(context, skeletonModel, 0.5F);
		this.addLayer(new HumanoidArmorLayer<>(this, new LonestarSkeletonModel<>(context.bakeLayer(innerLayer)), new LonestarSkeletonModel<>(context.bakeLayer(outerLayer)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return LONESTAR_SKELETON_LOCATION;
	}

	@Override
	protected boolean isShaking(T entity) {
		return entity.isShaking();
	}
}