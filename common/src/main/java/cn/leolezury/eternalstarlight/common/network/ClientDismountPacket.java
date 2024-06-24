package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientDismountPacket(int riderId) implements CustomPacketPayload {
	public static final Type<ClientDismountPacket> TYPE = new Type<>(EternalStarlight.id("client_dismount"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientDismountPacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, ClientDismountPacket::riderId, ClientDismountPacket::new);

	public static void handle(ClientDismountPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleClientDismount(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
