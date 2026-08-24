package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESBookUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public record UpdateBookProgressionPacket(Set<ResourceLocation> ids) implements CustomPacketPayload {
	public static final Type<UpdateBookProgressionPacket> TYPE = new Type<>(EternalStarlight.id("update_book_progression"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBookProgressionPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.collection(HashSet::new, ResourceLocation.STREAM_CODEC), UpdateBookProgressionPacket::ids,
		UpdateBookProgressionPacket::new
	);

	public static void handle(UpdateBookProgressionPacket packet, Player player) {
		if (player instanceof ServerPlayer serverPlayer) {
			ESBookUtil.unlock(serverPlayer, packet.ids().toArray(new ResourceLocation[0]));
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
