package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


public class LonestarSkeletonRenderer extends SkeletonRenderer {
    private static final ResourceLocation LONESTAR_SKELETON_LOCATION = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/lonestar_skeleton.png");

    public static final ModelLayerLocation LONESTAR = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "lonestar_skeleton"), "main");
    public static final ModelLayerLocation LONESTAR_INNER_ARMOR = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "lonestar_skeleton"), "inner_armor");
    public static final ModelLayerLocation LONESTAR_OUTER_ARMOR = new ModelLayerLocation(new ResourceLocation(EternalStarlight.MOD_ID, "lonestar_skeleton"), "outer_armor");

    public LonestarSkeletonRenderer(EntityRendererProvider.Context p_174409_) {
        super(p_174409_, LONESTAR, LONESTAR_INNER_ARMOR, LONESTAR_OUTER_ARMOR);
    }

    public ResourceLocation getTextureLocation(AbstractSkeleton p_116049_) {
        return LONESTAR_SKELETON_LOCATION;
    }
}
