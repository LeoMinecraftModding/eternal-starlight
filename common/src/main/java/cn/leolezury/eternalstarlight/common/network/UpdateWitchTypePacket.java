package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record UpdateWitchTypePacket(int witchId, String witchType) implements CustomPacketPayload {
	public static final Type<UpdateWitchTypePacket> TYPE = new Type<>(EternalStarlight.id("update_witch_type"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateWitchTypePacket> STREAM_CODEC = StreamCodec.ofMember(UpdateWitchTypePacket::write, UpdateWitchTypePacket::read);

	public static UpdateWitchTypePacket read(RegistryFriendlyByteBuf buf) {
		int witchId = buf.readInt();
		String witchType = buf.readUtf();
		return new UpdateWitchTypePacket(witchId, witchType);
	}

	private static void write(UpdateWitchTypePacket packet, RegistryFriendlyByteBuf buf) {
		buf.writeInt(packet.witchId());
		buf.writeUtf(packet.witchType());
	}

	public static void handle(UpdateWitchTypePacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleUpdateWitchType(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
