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

public record OpenBookPacket(ResourceLocation bookId, Set<ResourceLocation> unlocked) implements CustomPacketPayload {
	public static final Type<OpenBookPacket> TYPE = new Type<>(EternalStarlight.id("open_book"));
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenBookPacket> STREAM_CODEC = StreamCodec.ofMember(OpenBookPacket::write, OpenBookPacket::read);

	public static OpenBookPacket read(FriendlyByteBuf buf) {
		ResourceLocation bookId = ResourceLocation.parse(buf.readUtf());
		int size = buf.readInt();
		Set<ResourceLocation> unlocked = new HashSet<>();
		for (int i = 0; i < size; i++) {
			unlocked.add(ResourceLocation.parse(buf.readUtf()));
		}
		return new OpenBookPacket(bookId, unlocked);
	}

	public static void write(OpenBookPacket packet, FriendlyByteBuf buf) {
		buf.writeUtf(packet.bookId().toString());
		buf.writeInt(packet.unlocked().size());
		for (ResourceLocation resourceLocation : packet.unlocked()) {
			buf.writeUtf(resourceLocation.toString());
		}
	}

	public static void handle(OpenBookPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenBook(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
