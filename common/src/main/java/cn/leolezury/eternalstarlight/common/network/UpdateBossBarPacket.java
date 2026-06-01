package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public record UpdateBossBarPacket(UUID barId, int barType) implements CustomPacketPayload {
	public static final Type<UpdateBossBarPacket> TYPE = new Type<>(EternalStarlight.id("update_boss_bar"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBossBarPacket> STREAM_CODEC = StreamCodec.composite(
		UUIDUtil.STREAM_CODEC, UpdateBossBarPacket::barId,
		ByteBufCodecs.VAR_INT, UpdateBossBarPacket::barType,
		UpdateBossBarPacket::new
	);

	public static void handle(UpdateBossBarPacket packet, Player player) {
		EternalStarlight.getClientHelper().handleUpdateBossBar(packet);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
