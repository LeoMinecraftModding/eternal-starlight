package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record OpenGatekeeperGuiPacket(int id, boolean challenged) implements CustomPacketPayload {
	public static final Type<OpenGatekeeperGuiPacket> TYPE = new Type<>(EternalStarlight.id("open_gatekeeper_gui"));
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenGatekeeperGuiPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, OpenGatekeeperGuiPacket::id,
		ByteBufCodecs.BOOL, OpenGatekeeperGuiPacket::challenged,
		OpenGatekeeperGuiPacket::new
	);

	public static void handle(OpenGatekeeperGuiPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenGatekeeperGui(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}