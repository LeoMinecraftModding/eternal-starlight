package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESVfx;
import cn.leolezury.eternalstarlight.common.vfx.VfxData;
import cn.leolezury.eternalstarlight.common.vfx.VfxInstance;
import cn.leolezury.eternalstarlight.common.vfx.VfxType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record VfxPacket(ResourceLocation typeId, VfxData data, ResourceKey<Level> dimension, Vec3 position, int duration, float radius) implements CustomPacketPayload {
	public static final Type<VfxPacket> TYPE = new Type<>(EternalStarlight.id("vfx"));

	public static final StreamCodec<RegistryFriendlyByteBuf, VfxPacket> STREAM_CODEC = StreamCodec.ofMember(VfxPacket::write, VfxPacket::read);

	public static VfxPacket read(RegistryFriendlyByteBuf buf) {
		ResourceLocation typeId = buf.readResourceLocation();
		VfxType<?> type = ESVfx.get(typeId);
		VfxData data = type == null ? null : readData(buf, type);
		ResourceKey<Level> dimension = buf.readResourceKey(Registries.DIMENSION);
		Vec3 position = buf.readVec3();
		int duration = buf.readVarInt();
		float radius = buf.readFloat();
		return new VfxPacket(typeId, data, dimension, position, duration, radius);
	}

	private static <T extends VfxData> T readData(RegistryFriendlyByteBuf buf, VfxType<T> type) {
		return type.streamCodec().decode(buf);
	}

	public static void write(VfxPacket packet, RegistryFriendlyByteBuf buf) {
		buf.writeResourceLocation(packet.typeId());
		writeData(buf, packet.data());
		buf.writeResourceKey(packet.dimension());
		buf.writeVec3(packet.position());
		buf.writeVarInt(packet.duration());
		buf.writeFloat(packet.radius());
	}

	@SuppressWarnings("unchecked")
	private static <T extends VfxData> void writeData(RegistryFriendlyByteBuf buf, VfxData data) {
		((VfxType<T>) data.type()).streamCodec().encode(buf, (T) data);
	}

	public static void handle(VfxPacket packet, Player player) {
		VfxType<?> type = packet.data() == null ? null : ESVfx.get(packet.typeId());
		if (type != null) {
			type.spawnOnClient(new VfxInstance(type, packet.dimension(), packet.position(), packet.duration(), packet.radius(), packet.data()));
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
