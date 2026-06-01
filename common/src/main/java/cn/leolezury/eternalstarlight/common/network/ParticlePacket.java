package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ParticlePacket(ParticleOptions particle, double x, double y, double z, double dx, double dy, double dz, boolean longDistance) implements CustomPacketPayload {
	public static final Type<ParticlePacket> TYPE = new Type<>(EternalStarlight.id("particle"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ParticlePacket> STREAM_CODEC = CustomPacketPayload.codec(
		ParticlePacket::write, ParticlePacket::new
	);

	public ParticlePacket(ParticleOptions particle, double x, double y, double z, double dx, double dy, double dz) {
		this(particle, x, y, z, dx, dy, dz, true);
	}

	public ParticlePacket(RegistryFriendlyByteBuf buf) {
		this(ParticleTypes.STREAM_CODEC.decode(buf), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readBoolean());
	}

	public void write(RegistryFriendlyByteBuf buf) {
		ParticleTypes.STREAM_CODEC.encode(buf, this.particle);
		buf.writeDouble(this.x);
		buf.writeDouble(this.y);
		buf.writeDouble(this.z);
		buf.writeDouble(this.dx);
		buf.writeDouble(this.dy);
		buf.writeDouble(this.dz);
		buf.writeBoolean(this.longDistance);
	}

	public static void handle(ParticlePacket packet, Player player) {
		EternalStarlight.getClientHelper().handleParticlePacket(packet);
	}

	@Override
	public Type<ParticlePacket> type() {
		return TYPE;
	}
}
