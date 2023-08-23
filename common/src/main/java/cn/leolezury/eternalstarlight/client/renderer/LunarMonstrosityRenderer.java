package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.LunarMonstrosityModel;
import cn.leolezury.eternalstarlight.entity.boss.LunarMonstrosity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class LunarMonstrosityRenderer<T extends LunarMonstrosity> extends MobRenderer<T, LunarMonstrosityModel<T>> {
    ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/lunar_monstrosity.png");
    ResourceLocation ENTITY_PHASE_2_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/lunar_monstrosity_1.png");

    public LunarMonstrosityRenderer(EntityRendererProvider.Context context) {
        super(context, new LunarMonstrosityModel<>(context.bakeLayer(LunarMonstrosityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    protected float getFlipDegrees(T p_115337_) {
        return 0;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.getPhase() == 0 ? ENTITY_TEXTURE : ENTITY_PHASE_2_TEXTURE;
    }
}
