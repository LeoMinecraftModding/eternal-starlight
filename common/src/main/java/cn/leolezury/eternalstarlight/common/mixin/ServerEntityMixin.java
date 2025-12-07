package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.network.SyncAttachmentsPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerEntity.class)
public abstract class ServerEntityMixin {
	@Shadow
	@Final
	private Entity entity;

	@Shadow
	@Final
	private ServerLevel level;

	@Inject(method = "addPairing", at = @At(value = "TAIL"))
	private void addPairing(ServerPlayer serverPlayer, CallbackInfo ci) {
		ESDataAttachments.getAttachments().forEach(attachment -> {
			if (attachment.streamCodec() != null && attachment.hasData(entity)) {
				ESPlatform.INSTANCE.sendToClient(serverPlayer, SyncAttachmentsPacket.create(entity, attachment, attachment.getData(entity), level.registryAccess()));
			}
		});
	}
}
