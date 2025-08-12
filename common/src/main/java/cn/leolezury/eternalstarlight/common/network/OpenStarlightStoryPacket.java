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

public record OpenStarlightStoryPacket(ResourceLocation bookId, Set<ResourceLocation> unlocked) implements CustomPacketPayload {
	public static final Type<OpenStarlightStoryPacket> TYPE = new Type<>(EternalStarlight.id("open_starlight_story"));
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenStarlightStoryPacket> STREAM_CODEC = StreamCodec.ofMember(OpenStarlightStoryPacket::write, OpenStarlightStoryPacket::read);

	public static OpenStarlightStoryPacket read(FriendlyByteBuf buf) {
		ResourceLocation bookId = ResourceLocation.parse(buf.readUtf());
		int size = buf.readInt();
		Set<ResourceLocation> unlocked = new HashSet<>();
		for (int i = 0; i < size; i++) {
			unlocked.add(ResourceLocation.parse(buf.readUtf()));
		}
		return new OpenStarlightStoryPacket(bookId, unlocked);
	}

	public static void write(OpenStarlightStoryPacket packet, FriendlyByteBuf buf) {
		buf.writeUtf(packet.bookId().toString());
		buf.writeInt(packet.unlocked().size());
		for (ResourceLocation resourceLocation : packet.unlocked()) {
			buf.writeUtf(resourceLocation.toString());
		}
	}

	public static void handle(OpenStarlightStoryPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenStarlightStory(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
