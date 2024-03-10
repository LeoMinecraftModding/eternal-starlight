package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TheGatekeeperModel;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TheGatekeeperRenderer;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class TheGatekeeperClothingLayer<T extends TheGatekeeper> extends RenderLayer<T, TheGatekeeperModel<T>> {
    private static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/the_gatekeeper_overlay.png");
    private static final ResourceLocation SLIM_OVERLAY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/the_gatekeeper_overlay_slim.png");
    private final TheGatekeeperModel<T> normalModel;
    private final TheGatekeeperModel<T> slimModel;

    public TheGatekeeperClothingLayer(RenderLayerParent<T, TheGatekeeperModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.normalModel = new TheGatekeeperModel<>(modelSet.bakeLayer(TheGatekeeperModel.LAYER_LOCATION), false);
        this.slimModel = new TheGatekeeperModel<>(modelSet.bakeLayer(TheGatekeeperModel.SLIM_LAYER_LOCATION), true);
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        TheGatekeeperModel<T> model = normalModel;
        ResourceLocation texture = OVERLAY_TEXTURE;
        SkinManager skinManager = Minecraft.getInstance().getSkinManager();
        Optional<GameProfile> profile = TheGatekeeperRenderer.getGameProfile(entity);
        if (profile.isPresent()) {
            texture = skinManager.getInsecureSkin(profile.get()).model() == PlayerSkin.Model.SLIM ? SLIM_OVERLAY_TEXTURE : OVERLAY_TEXTURE;
            model = skinManager.getInsecureSkin(profile.get()).model() == PlayerSkin.Model.SLIM ? slimModel : normalModel;
        }
        if (!entity.isInvisible()) {
            getParentModel().copyPropertiesTo(model);
            model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(texture));
            model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
