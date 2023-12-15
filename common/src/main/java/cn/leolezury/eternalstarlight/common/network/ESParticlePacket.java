package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;

public class ESParticlePacket {
    private final ParticleOptions particle;
    private final double x, y, z, dx, dy, dz;

    public ESParticlePacket(ParticleOptions particle, double x, double y, double z, double dx, double dy, double dz) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public static ESParticlePacket read(FriendlyByteBuf buf) {
        ParticleOptions options = readParticle(buf);
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        double dx = buf.readDouble();
        double dy = buf.readDouble();
        double dz = buf.readDouble();
        return new ESParticlePacket(options, x, y, z, dx, dy, dz);
    }

    private static <T extends ParticleOptions> T readParticle(FriendlyByteBuf buf) {
        int id = buf.readInt();
        ParticleType<T> type = (ParticleType<T>) BuiltInRegistries.PARTICLE_TYPE.byId(id);
        return type.getDeserializer().fromNetwork(type, buf);
    }

    public static void write(ESParticlePacket message, FriendlyByteBuf buf) {
        buf.writeInt(BuiltInRegistries.PARTICLE_TYPE.getId(message.particle.getType()));
        message.particle.writeToNetwork(buf);
        buf.writeDouble(message.x);
        buf.writeDouble(message.y);
        buf.writeDouble(message.z);
        buf.writeDouble(message.dx);
        buf.writeDouble(message.dy);
        buf.writeDouble(message.dz);
    }

    public static class Handler {
        public static void handle(ESParticlePacket message) {
            ESUtil.runWhenOnClient(() -> () -> {
                ClientLevel clientLevel = Minecraft.getInstance().level;
                if (clientLevel != null) {
                    clientLevel.addParticle(message.particle, message.x, message.y, message.z, message.dx, message.dy, message.dz);
                }
            });
        }
    }
}
