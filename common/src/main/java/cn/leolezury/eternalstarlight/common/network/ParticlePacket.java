package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public record ParticlePacket(ParticleOptions particle,
                             double x, double y, double z,
                             double dx, double dy, double dz) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ParticlePacket> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(EternalStarlight.MOD_ID, "particle"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ParticlePacket> STREAM_CODEC = StreamCodec.ofMember(ParticlePacket::write, ParticlePacket::read);

    public static ParticlePacket read(RegistryFriendlyByteBuf buf) {
        ParticleOptions options = readParticle(buf);
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        double dx = buf.readDouble();
        double dy = buf.readDouble();
        double dz = buf.readDouble();
        return new ParticlePacket(options, x, y, z, dx, dy, dz);
    }

    private static <T extends ParticleOptions> T readParticle(RegistryFriendlyByteBuf buf) {
        int id = buf.readInt();
        ParticleType<T> type = (ParticleType<T>) BuiltInRegistries.PARTICLE_TYPE.byId(id);
        return type.streamCodec().decode(buf);
    }

    public static void write(ParticlePacket message, RegistryFriendlyByteBuf buf) {
        writeParticle(message.particle(), buf);
        buf.writeDouble(message.x);
        buf.writeDouble(message.y);
        buf.writeDouble(message.z);
        buf.writeDouble(message.dx);
        buf.writeDouble(message.dy);
        buf.writeDouble(message.dz);
    }

    private static <T extends ParticleOptions> void writeParticle(T particle, RegistryFriendlyByteBuf buf) {
        buf.writeInt(BuiltInRegistries.PARTICLE_TYPE.getId(particle.getType()));
        ParticleType<T> type = (ParticleType<T>) particle.getType();
        type.streamCodec().encode(buf, particle);
    }

    public static void handle(ParticlePacket message, Player player) {
        EternalStarlight.getClientHelper().handleParticlePacket(message);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
