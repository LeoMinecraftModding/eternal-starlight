package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public record OpenBookPacket(ResourceLocation bookId, Set<ResourceLocation> unlocked, boolean allUnlocked) implements CustomPacketPayload {
	public static final Type<OpenBookPacket> TYPE = new Type<>(EternalStarlight.id("open_book"));
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenBookPacket> STREAM_CODEC = StreamCodec.composite(
		ResourceLocation.STREAM_CODEC, OpenBookPacket::bookId,
		ByteBufCodecs.collection(HashSet::new, ResourceLocation.STREAM_CODEC), OpenBookPacket::unlocked,
		ByteBufCodecs.BOOL, OpenBookPacket::allUnlocked,
		OpenBookPacket::new
	);

	public static void handle(OpenBookPacket packet, Player player) {
		EternalStarlight.getClientHelper().handleOpenBook(packet);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
