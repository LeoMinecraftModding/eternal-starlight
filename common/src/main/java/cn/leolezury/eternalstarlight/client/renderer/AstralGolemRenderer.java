package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.AstralGolemModel;
import cn.leolezury.eternalstarlight.client.renderer.layer.AstralGolemArmorLayer;
import cn.leolezury.eternalstarlight.entity.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.entity.npc.boarwarf.golem.AstralGolemMaterials;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class AstralGolemRenderer<T extends AstralGolem> extends HumanoidMobRenderer<T, AstralGolemModel<T>> {
    public AstralGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new AstralGolemModel<>(context.bakeLayer(AstralGolemModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new AstralGolemArmorLayer<>(this, new HumanoidArmorModel<>(context.bakeLayer(AstralGolemModel.INNER_ARMOR_LOCATION)), new HumanoidArmorModel<>(context.bakeLayer(AstralGolemModel.OUTER_ARMOR_LOCATION)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/golem/astral_golem_0.png");
        if (entity.getMaterial() >= 0 && entity.getMaterial() < AstralGolemMaterials.MATERIAL_NUM) {
            ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/boarwarf/golem/astral_golem_" + entity.getMaterial() + ".png");
        }
        return ENTITY_TEXTURE;
    }
}
