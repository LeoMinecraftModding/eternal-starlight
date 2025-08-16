package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public record UpdateBookPacket(Set<ResourceLocation> oldUnlocked, Set<ResourceLocation> unlocked) implements CustomPacketPayload {
	public static final Type<UpdateBookPacket> TYPE = new Type<>(EternalStarlight.id("update_book"));
	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBookPacket> STREAM_CODEC = StreamCodec.ofMember(UpdateBookPacket::write, UpdateBookPacket::read);

	public static UpdateBookPacket read(FriendlyByteBuf buf) {
		int oldSize = buf.readInt();
		Set<ResourceLocation> oldUnlocked = new HashSet<>();
		for (int i = 0; i < oldSize; i++) {
			oldUnlocked.add(ResourceLocation.parse(buf.readUtf()));
		}
		int size = buf.readInt();
		Set<ResourceLocation> unlocked = new HashSet<>();
		for (int i = 0; i < size; i++) {
			unlocked.add(ResourceLocation.parse(buf.readUtf()));
		}
		return new UpdateBookPacket(oldUnlocked, unlocked);
	}

	public static void write(UpdateBookPacket packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.oldUnlocked().size());
		for (ResourceLocation resourceLocation : packet.oldUnlocked()) {
			buf.writeUtf(resourceLocation.toString());
		}
		buf.writeInt(packet.unlocked().size());
		for (ResourceLocation resourceLocation : packet.unlocked()) {
			buf.writeUtf(resourceLocation.toString());
		}
	}

	public static void handle(UpdateBookPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleUpdateBook(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
