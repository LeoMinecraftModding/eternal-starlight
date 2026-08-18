package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.posteffect.PostEffectData;
import cn.leolezury.eternalstarlight.common.client.posteffect.PostEffectRegistry;
import cn.leolezury.eternalstarlight.common.client.posteffect.PostEffectType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public record PostEffectPacket(ResourceLocation typeId, PostEffectData data, Vec3 position, int duration, float radius, float intensity) implements CustomPacketPayload {
	public static final Type<PostEffectPacket> TYPE = new Type<>(EternalStarlight.id("post_effect"));

	public static final StreamCodec<RegistryFriendlyByteBuf, PostEffectPacket> STREAM_CODEC = StreamCodec.ofMember(PostEffectPacket::write, PostEffectPacket::read);

	public static PostEffectPacket read(FriendlyByteBuf buf) {
		ResourceLocation typeId = buf.readResourceLocation();
		PostEffectType<?> type = PostEffectRegistry.get(typeId);
		PostEffectData data = type == null ? null : readData(buf, type);
		Vec3 position = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
		int duration = buf.readVarInt();
		float radius = buf.readFloat();
		float intensity = buf.readFloat();
		return new PostEffectPacket(typeId, data, position, duration, radius, intensity);
	}

	private static <T extends PostEffectData> T readData(FriendlyByteBuf buf, PostEffectType<T> type) {
		return type.streamCodec().decode((RegistryFriendlyByteBuf) buf);
	}

	public static void write(PostEffectPacket packet, FriendlyByteBuf buf) {
		buf.writeResourceLocation(packet.typeId());
		writeData(buf, packet.data());
		buf.writeDouble(packet.position().x);
		buf.writeDouble(packet.position().y);
		buf.writeDouble(packet.position().z);
		buf.writeVarInt(packet.duration());
		buf.writeFloat(packet.radius());
		buf.writeFloat(packet.intensity());
	}

	private static void writeData(FriendlyByteBuf buf, PostEffectData data) {
		((PostEffectType) data.type()).streamCodec().encode(buf, data);
	}

	public static void handle(PostEffectPacket packet, Player player) {
		if (packet.data() != null) {
			PostEffectRegistry.spawnOnClient(packet.typeId(), packet.data(), packet.position(), packet.duration(), packet.radius(), packet.intensity());
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
