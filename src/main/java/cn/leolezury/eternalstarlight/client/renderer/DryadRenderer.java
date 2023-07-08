package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.DryadModel;
import cn.leolezury.eternalstarlight.entity.animal.Dryad;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DryadRenderer<T extends Dryad> extends MobRenderer<T, DryadModel<T>> {
    ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/dryad.png");
    ResourceLocation LUSH_ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/lush_dryad.png");

    public DryadRenderer(EntityRendererProvider.Context context) {
        super(context, new DryadModel<>(context.bakeLayer(DryadModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.hasLeaves() ? LUSH_ENTITY_TEXTURE : ENTITY_TEXTURE;
    }
}
