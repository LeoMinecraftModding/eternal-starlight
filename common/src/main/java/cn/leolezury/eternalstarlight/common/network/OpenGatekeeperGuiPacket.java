package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record OpenGatekeeperGuiPacket(int id, boolean challenged) implements CustomPacketPayload {
	public static final Type<OpenGatekeeperGuiPacket> TYPE = new Type<>(EternalStarlight.id("open_gatekeeper_gui"));
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenGatekeeperGuiPacket> STREAM_CODEC = StreamCodec.ofMember(OpenGatekeeperGuiPacket::write, OpenGatekeeperGuiPacket::read);

	public static OpenGatekeeperGuiPacket read(FriendlyByteBuf buf) {
		int id = buf.readInt();
		boolean challenged = buf.readBoolean();
		return new OpenGatekeeperGuiPacket(id, challenged);
	}

	public static void write(OpenGatekeeperGuiPacket packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.id());
		buf.writeBoolean(packet.challenged());
	}

	public static void handle(OpenGatekeeperGuiPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenGatekeeperGui(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}