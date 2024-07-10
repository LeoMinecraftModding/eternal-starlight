package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record NoParametersPacket(String id) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<NoParametersPacket> TYPE = new Type<>(EternalStarlight.id("no_parameters"));
	public static final StreamCodec<RegistryFriendlyByteBuf, NoParametersPacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, NoParametersPacket::id, NoParametersPacket::new);

	public static void handle(NoParametersPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleS2cNoParam(packet));
		if (player instanceof ServerPlayer serverPlayer) {
			CommonHandlers.onC2sNoParamPacket(serverPlayer, packet.id());
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
