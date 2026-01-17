package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESBookUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public record UpdateBookProgressionPacket(Set<ResourceLocation> ids) implements CustomPacketPayload {
	public static final Type<UpdateBookProgressionPacket> TYPE = new Type<>(EternalStarlight.id("update_book_progression"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBookProgressionPacket> STREAM_CODEC = StreamCodec.ofMember(UpdateBookProgressionPacket::write, UpdateBookProgressionPacket::read);

	public static UpdateBookProgressionPacket read(FriendlyByteBuf buf) {
		int size = buf.readInt();
		Set<ResourceLocation> ids = new HashSet<>();
		for (int i = 0; i < size; i++) {
			ids.add(ResourceLocation.parse(buf.readUtf()));
		}
		return new UpdateBookProgressionPacket(ids);
	}

	public static void write(UpdateBookProgressionPacket packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.ids().size());
		for (ResourceLocation resourceLocation : packet.ids()) {
			buf.writeUtf(resourceLocation.toString());
		}
	}

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
