package cn.leolezury.eternalstarlight.common.client.renderer.layer;

import cn.leolezury.eternalstarlight.common.client.model.entity.TheGatekeeperModel;
import cn.leolezury.eternalstarlight.common.entity.boss.TheGatekeeper;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.server.players.GameProfileCache;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;

@Environment(EnvType.CLIENT)
public class TheGatekeeperHeadLayer<T extends TheGatekeeper> extends RenderLayer<T, TheGatekeeperModel<T>> {
    private final TheGatekeeperModel<T> model;
    private String prevPlayerName = "steve";
    @Nullable
    private static GameProfileCache profileCache;
    @Nullable
    private static MinecraftSessionService sessionService;
    @Nullable
    private static Executor mainThreadExecutor;
    @Nullable
    private GameProfile playerProfile;
    private boolean changed = true;

    public TheGatekeeperHeadLayer(RenderLayerParent<T, TheGatekeeperModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new TheGatekeeperModel<>(modelSet.bakeLayer(TheGatekeeperModel.LAYER_LOCATION), false);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        /*if (entityIn.getCustomName() == null || entityIn.getCustomName().getString().isEmpty()) {
            return;
        }
        String name = entityIn.getCustomName().getString();

        GameProfile gameProfile;
        if (!name.equals(prevPlayerName) && changed) {
            changed = false;
            gameProfile = new GameProfile(null, name);
            playerProfile = gameProfile;
            updatePlayerProfile();
        } else {
            gameProfile = playerProfile;
        }

        if (gameProfile != null) {
            ResourceLocation TEXTURE = Minecraft.getInstance().getSkinManager().getInsecureSkin(gameProfile).texture();
            getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(TEXTURE));
            this.model.renderHeadToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(entityIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }*/
    }
}
