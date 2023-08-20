package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.BoarwarfModel;
import cn.leolezury.eternalstarlight.client.renderer.layer.BoarwarfBiomeLayer;
import cn.leolezury.eternalstarlight.client.renderer.layer.BoarwarfHairLayer;
import cn.leolezury.eternalstarlight.client.renderer.layer.BoarwarfProfessionLayer;
import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


public class BoarwarfRenderer<T extends Boarwarf> extends MobRenderer<T, BoarwarfModel<T>> {
    ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/boarwarf.png");

    public BoarwarfRenderer(EntityRendererProvider.Context context) {
        super(context, new BoarwarfModel<>(context.bakeLayer(BoarwarfModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new BoarwarfHairLayer<>(this, context.getModelSet()));
        this.addLayer(new BoarwarfBiomeLayer<>(this, context.getModelSet()));
        this.addLayer(new BoarwarfProfessionLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ENTITY_TEXTURE;
    }
}
