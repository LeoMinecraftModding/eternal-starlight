package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record SimpleActionPacket(String id) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<SimpleActionPacket> TYPE = new Type<>(EternalStarlight.id("simple_action"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SimpleActionPacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, SimpleActionPacket::id, SimpleActionPacket::new);

	public static final String C2S_SWING_ATTACK = "swing_attack";
	public static final String C2S_SWITCH_CREST = "switch_crest";

	public static final String S2C_CLEAR_WEATHER = "clear_weather";

	public static void handle(SimpleActionPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleServerToClientSimpleAction(packet));
		if (player instanceof ServerPlayer serverPlayer) {
			ESCommonHandler.onClientToServerSimpleAction(serverPlayer, packet.id());
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
