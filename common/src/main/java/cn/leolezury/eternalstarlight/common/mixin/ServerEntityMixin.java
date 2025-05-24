package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.interfaces.StarlightWitch;
import cn.leolezury.eternalstarlight.common.network.UpdateSpellDataPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWitchTypePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.server.level.ServerEntity;
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

	@Inject(method = "addPairing", at = @At(value = "TAIL"))
	private void startSeenByPlayer(ServerPlayer serverPlayer, CallbackInfo ci) {
		if (entity instanceof SpellCaster caster) {
			if (!entity.level().isClientSide) {
				ESPlatform.INSTANCE.sendToClient(serverPlayer, new UpdateSpellDataPacket(entity.getId(), caster.getESSpellData()));
			}
		}
		if (entity instanceof StarlightWitch witch) {
			if (!entity.level().isClientSide) {
				ESPlatform.INSTANCE.sendToClient(serverPlayer, new UpdateWitchTypePacket(entity.getId(), witch.getWitchType()));
			}
		}
	}
}
