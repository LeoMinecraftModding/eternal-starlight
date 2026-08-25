package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESCodecUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ParticlePacket(ParticleOptions particle, double x, double y, double z, double dx, double dy, double dz, boolean longDistance) implements CustomPacketPayload {
	public static final Type<ParticlePacket> TYPE = new Type<>(EternalStarlight.id("particle"));
	public static final StreamCodec<RegistryFriendlyByteBuf, ParticlePacket> STREAM_CODEC = ESCodecUtil.compositeStreamCodec(
		ParticleTypes.STREAM_CODEC, ParticlePacket::particle,
		ByteBufCodecs.DOUBLE, ParticlePacket::x,
		ByteBufCodecs.DOUBLE, ParticlePacket::y,
		ByteBufCodecs.DOUBLE, ParticlePacket::z,
		ByteBufCodecs.DOUBLE, ParticlePacket::dx,
		ByteBufCodecs.DOUBLE, ParticlePacket::dy,
		ByteBufCodecs.DOUBLE, ParticlePacket::dz,
		ByteBufCodecs.BOOL, ParticlePacket::longDistance,
		ParticlePacket::new
	);

	public ParticlePacket(ParticleOptions particle, double x, double y, double z, double dx, double dy, double dz) {
		this(particle, x, y, z, dx, dy, dz, true);
	}

	public static void handle(ParticlePacket packet, Player player) {
		EternalStarlight.getClientHelper().handleParticlePacket(packet);
	}

	@Override
	public Type<ParticlePacket> type() {
		return TYPE;
	}
}
