package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record TriggerEntityEventPacket(int id, byte event) implements CustomPacketPayload {
	public static final Type<TriggerEntityEventPacket> TYPE = new Type<>(EternalStarlight.id("trigger_entity_event"));
	public static final StreamCodec<RegistryFriendlyByteBuf, TriggerEntityEventPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, TriggerEntityEventPacket::id,
		ByteBufCodecs.BYTE, TriggerEntityEventPacket::event,
		TriggerEntityEventPacket::new
	);

	public static void handle(TriggerEntityEventPacket packet, Player player) {
		Entity entity = player.level().getEntity(packet.id());
		if (entity != null) {
			player.level().broadcastEntityEvent(entity, packet.event());
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
