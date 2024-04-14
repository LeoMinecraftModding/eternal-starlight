package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarMonstrosityModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.LunarMonstrosity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LunarMonstrosityRenderer<T extends LunarMonstrosity> extends MobRenderer<T, LunarMonstrosityModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/lunar_monstrosity.png");
    private static final ResourceLocation ENTITY_PHASE_2_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/lunar_monstrosity_1.png");

    public LunarMonstrosityRenderer(EntityRendererProvider.Context context) {
        super(context, new LunarMonstrosityModel<>(context.bakeLayer(LunarMonstrosityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    protected float getFlipDegrees(T livingEntity) {
        return 0;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.getPhase() == 0 ? ENTITY_TEXTURE : ENTITY_PHASE_2_TEXTURE;
    }
}
