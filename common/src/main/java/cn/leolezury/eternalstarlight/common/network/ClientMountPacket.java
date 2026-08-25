package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientMountPacket(int riderId, int vehicleId) implements CustomPacketPayload {
	public static final Type<ClientMountPacket> TYPE = new Type<>(EternalStarlight.id("client_mount"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientMountPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, ClientMountPacket::riderId,
		ByteBufCodecs.INT, ClientMountPacket::vehicleId,
		ClientMountPacket::new
	);

	public static void handle(ClientMountPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleClientMount(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
