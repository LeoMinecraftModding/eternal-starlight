package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public record UpdateBookPacket(Set<ResourceLocation> oldUnlocked, Set<ResourceLocation> unlocked) implements CustomPacketPayload {
	public static final Type<UpdateBookPacket> TYPE = new Type<>(EternalStarlight.id("update_book"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBookPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.collection(HashSet::new, ResourceLocation.STREAM_CODEC), UpdateBookPacket::oldUnlocked,
		ByteBufCodecs.collection(HashSet::new, ResourceLocation.STREAM_CODEC), UpdateBookPacket::unlocked,
		UpdateBookPacket::new
	);

	public static void handle(UpdateBookPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleUpdateBook(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
