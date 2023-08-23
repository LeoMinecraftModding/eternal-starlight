package cn.leolezury.eternalstarlight.client.renderer.layer;

import cn.leolezury.eternalstarlight.client.model.TheGatekeeperModel;
import cn.leolezury.eternalstarlight.entity.boss.TheGatekeeper;
import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringUtil;
import org.jetbrains.annotations.Nullable;


import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

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
        if (entityIn.getCustomName() == null || entityIn.getCustomName().getString().isEmpty()) {
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

        Minecraft minecraft = Minecraft.getInstance();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(gameProfile);
        boolean hasKey = map.containsKey(MinecraftProfileTexture.Type.SKIN);
        if (!hasKey) {
            return;
        }
        ResourceLocation TEXTURE = minecraft.getSkinManager().registerTexture(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);

        getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTicks);
        this.model.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(TEXTURE));
        this.model.renderHeadToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(entityIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void updatePlayerProfile() {
        updateGameprofile(playerProfile, (profile) -> {
            playerProfile = profile;
            prevPlayerName = profile.getName();
            changed = true;
        });
    }

    public static void updateGameprofile(@Nullable GameProfile profile, Consumer<GameProfile> profileConsumer) {
        if (profile != null && !StringUtil.isNullOrEmpty(profile.getName()) && (!profile.isComplete() || !profile.getProperties().containsKey("textures")) && profileCache != null && sessionService != null) {
            profileCache.getAsync(profile.getName(), (p_182470_) -> {
                Util.backgroundExecutor().execute(() -> {
                    Util.ifElse(p_182470_, (p_276255_) -> {
                        Property property = Iterables.getFirst(p_276255_.getProperties().get("textures"), (Property)null);
                        if (property == null) {
                            MinecraftSessionService minecraftsessionservice = sessionService;
                            if (minecraftsessionservice == null) {
                                return;
                            }

                            p_276255_ = minecraftsessionservice.fillProfileProperties(p_276255_, true);
                        }

                        GameProfile gameprofile = p_276255_;
                        Executor executor = mainThreadExecutor;
                        if (executor != null) {
                            executor.execute(() -> {
                                GameProfileCache gameprofilecache = profileCache;
                                if (gameprofilecache != null) {
                                    gameprofilecache.add(gameprofile);
                                    profileConsumer.accept(gameprofile);
                                }

                            });
                        }

                    }, () -> {
                        Executor executor = mainThreadExecutor;
                        if (executor != null) {
                            executor.execute(() -> {
                                profileConsumer.accept(profile);
                            });
                        }

                    });
                });
            });
        } else {
            profileConsumer.accept(profile);
        }
    }
}
