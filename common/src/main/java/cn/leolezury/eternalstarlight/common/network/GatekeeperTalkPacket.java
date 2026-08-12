package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record GatekeeperTalkPacket(int id) implements CustomPacketPayload {
	public static final Type<GatekeeperTalkPacket> TYPE = new Type<>(EternalStarlight.id("gatekeeper_talk"));
	public static final StreamCodec<RegistryFriendlyByteBuf, GatekeeperTalkPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, GatekeeperTalkPacket::id,
		GatekeeperTalkPacket::new
	);

	public static void handle(GatekeeperTalkPacket packet, Player player) {
		if (player instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().getEntity(packet.id()) instanceof TheGatekeeper gatekeeper
			&& gatekeeper.distanceToSqr(serverPlayer) <= 4096) {
			serverPlayer.serverLevel().broadcastEntityEvent(gatekeeper, TheGatekeeper.EVENT_TALK);
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
