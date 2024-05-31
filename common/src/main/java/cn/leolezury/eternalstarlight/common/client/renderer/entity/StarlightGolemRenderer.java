package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarlightGolemModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.StarlightGolemEyesLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.StarlightGolemGlowLayer;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Calendar;

@Environment(EnvType.CLIENT)
public class StarlightGolemRenderer<T extends StarlightGolem> extends MobRenderer<T, StarlightGolemModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/starlight_golem/starlight_golem.png");
    private static final ResourceLocation HALLOWEEN_TEXTURE = EternalStarlight.id("textures/entity/starlight_golem/starlight_golem_halloween.png");

    public StarlightGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new StarlightGolemModel<>(context.bakeLayer(StarlightGolemModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new StarlightGolemGlowLayer<>(this, context.getModelSet()));
        this.addLayer(new StarlightGolemEyesLayer<>(this));
    }

    @Override
    protected float getFlipDegrees(T entity) {
        return 0;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return isHalloween() ? HALLOWEEN_TEXTURE : ENTITY_TEXTURE;
    }

    public static boolean isHalloween() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) == Calendar.NOVEMBER && calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }
}
