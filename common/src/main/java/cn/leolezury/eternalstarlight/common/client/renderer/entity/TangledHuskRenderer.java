package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.entity.attack.TangledHusk;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;

public class TangledHuskRenderer extends EntityRenderer<TangledHusk> {
	private final EntityRenderDispatcher entityRenderer;

	public TangledHuskRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.entityRenderer = context.getEntityRenderDispatcher();
	}

	@Override
	public void render(TangledHusk entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
		if (entity.level().getEntity(entity.getOwnerId()) instanceof Player player && entity.level() instanceof ClientLevel clientLevel) {
			if (entity.cachedPlayerDisplay == null || !entity.cachedPlayerDisplay.getGameProfile().equals(player.getGameProfile())) {
				RemotePlayer remotePlayer = new RemotePlayer(clientLevel, player.getGameProfile());
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					remotePlayer.setItemSlot(slot, player.getItemBySlot(slot).copy());
				}
				ESDataAttachments.HUSK_OWNER_ID.setData(remotePlayer, entity.getOwnerId());
				entity.cachedPlayerDisplay = remotePlayer;
			}
			if (entity.cachedPlayerDisplay != null) {
				poseStack.pushPose();
				poseStack.mulPose(new Quaternionf().rotateY((float) (-entityYaw + Math.cos(entity.tickCount * 3.25) * Math.PI * (0.4 + entity.getSpawnedTicks() * 0.005)) * Mth.DEG_TO_RAD));
				float swell = Mth.clamp((entity.getSpawnedTicks() + partialTicks) / 100, 0, 1);
				float factor = 1.0F + Mth.sin(swell * 300.0F) * swell * 0.01F;
				swell = Mth.clamp(swell, 0.0F, 1.0F);
				swell *= swell;
				swell *= swell;
				float xz = (1.0F + swell * 0.4F) * factor;
				float y = (1.0F + swell * 0.1F) / factor;
				poseStack.scale(xz, y, xz);
				entityRenderer.render(entity.cachedPlayerDisplay, 0, 0, 0, 0, partialTicks, poseStack, buffer, packedLight);
				poseStack.popPose();
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(TangledHusk entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
