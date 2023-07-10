package cn.leolezury.eternalstarlight.client.renderer;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.client.model.TheGatekeeperModel;
import cn.leolezury.eternalstarlight.client.renderer.layer.TheGatekeeperHeadLayer;
import cn.leolezury.eternalstarlight.entity.attack.Vine;
import cn.leolezury.eternalstarlight.entity.boss.TheGatekeeper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TheGatekeeperRenderer<T extends TheGatekeeper> extends MobRenderer<T, TheGatekeeperModel<T>> {
    ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/the_gatekeeper.png");
    ResourceLocation SLIM_ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/the_gatekeeper_slim.png");
    private final TheGatekeeperModel<T> normalModel;
    private final TheGatekeeperModel<T> slimModel;

    public TheGatekeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new TheGatekeeperModel<>(context.bakeLayer(TheGatekeeperModel.LAYER_LOCATION), false), 0.5f);
        normalModel = getModel();
        slimModel = new TheGatekeeperModel<>(context.bakeLayer(TheGatekeeperModel.SLIM_LAYER_LOCATION), true);

        this.addLayer(new TheGatekeeperHeadLayer<>(this, context.getModelSet()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel<>(context.bakeLayer(TheGatekeeperModel.INNER_ARMOR_LOCATION)), new HumanoidArmorModel<>(context.bakeLayer(TheGatekeeperModel.OUTER_ARMOR_LOCATION)), context.getModelManager()));
    }

    @Override
    public void render(T entity, float f1, float f2, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        model = entity.isSlim() ? slimModel : normalModel;
        super.render(entity, f1, f2, stack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return entity.isSlim() ? SLIM_ENTITY_TEXTURE : ENTITY_TEXTURE;
    }
}
