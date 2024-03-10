package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.entity.TheGatekeeperModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.TheGatekeeperClothingLayer;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class TheGatekeeperRenderer<T extends TheGatekeeper> extends MobRenderer<T, TheGatekeeperModel<T>> {
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/the_gatekeeper.png");
    private static final ResourceLocation SLIM_ENTITY_TEXTURE = new ResourceLocation(EternalStarlight.MOD_ID, "textures/entity/the_gatekeeper_slim.png");
    private static final Map<String, GameProfile> PROFILES = new HashMap<>();
    private final TheGatekeeperModel<T> normalModel;
    private final TheGatekeeperModel<T> slimModel;

    public TheGatekeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new TheGatekeeperModel<>(context.bakeLayer(TheGatekeeperModel.LAYER_LOCATION), false), 0.5f);
        normalModel = getModel();
        slimModel = new TheGatekeeperModel<>(context.bakeLayer(TheGatekeeperModel.SLIM_LAYER_LOCATION), true);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new TheGatekeeperClothingLayer<>(this, context.getModelSet()));
    }

    @Override
    public void render(T entity, float f1, float f2, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        SkinManager skinManager = Minecraft.getInstance().getSkinManager();
        getGameProfile(entity).ifPresent(p -> model = skinManager.getInsecureSkin(p).model() == PlayerSkin.Model.SLIM ? slimModel : normalModel);
        super.render(entity, f1, f2, stack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        ResourceLocation texture = model == slimModel ? SLIM_ENTITY_TEXTURE : ENTITY_TEXTURE;
        SkinManager skinManager = Minecraft.getInstance().getSkinManager();
        Optional<GameProfile> profile = getGameProfile(entity);
        if (profile.isPresent()) {
            texture = skinManager.getInsecureSkin(profile.get()).texture();
        }
        return texture;
    }

    public static Optional<GameProfile> getGameProfile(TheGatekeeper entity) {
        if (entity.getCustomName() != null) {
            String customName = entity.getCustomName().getString();
            if (!PROFILES.containsKey(customName)) {
                SkullBlockEntity.fetchGameProfile(customName).thenAccept((optional) -> optional.ifPresent(p -> PROFILES.put(customName, p)));
            }
            if (PROFILES.containsKey(customName)) {
                return Optional.ofNullable(PROFILES.get(customName));
            }
        }
        return Optional.empty();
    }
}
