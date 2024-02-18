package cn.leolezury.eternalstarlight.common.network;

import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ESPackets {
    public static final Map<String, PacketInfo<?>> PACKETS = Util.make(new HashMap<>(), map -> {
        // test packet
        map.put("test_packet", new PacketInfo<>(TestPacket.class, TestPacket::write, TestPacket::read, TestPacket.Handler::handle));

        map.put("particle", new PacketInfo<>(ESParticlePacket.class, ESParticlePacket::write, ESParticlePacket::read, ESParticlePacket.Handler::handle));
        map.put("open_book", new PacketInfo<>(OpenBookPacket.class, OpenBookPacket::write, OpenBookPacket::read, OpenBookPacket.Handler::handle));
        map.put("update_weather", new PacketInfo<>(ESWeatherPacket.class, ESWeatherPacket::write, ESWeatherPacket::read, ESWeatherPacket.Handler::handle));
        map.put("cancel_weather", new PacketInfo<>(CancelWeatherPacket.class, CancelWeatherPacket::write, CancelWeatherPacket::read, CancelWeatherPacket.Handler::handle));
        map.put("open_crest_gui", new PacketInfo<>(OpenCrestGuiPacket.class, OpenCrestGuiPacket::write, OpenCrestGuiPacket::read, OpenCrestGuiPacket.Handler::handle));
        map.put("update_crests", new PacketInfo<>(UpdateCrestsPacket.class, UpdateCrestsPacket::write, UpdateCrestsPacket::read, UpdateCrestsPacket.Handler::handle));
        map.put("open_gatekeeper_gui", new PacketInfo<>(OpenGatekeeperGuiPacket.class, OpenGatekeeperGuiPacket::write, OpenGatekeeperGuiPacket::read, OpenGatekeeperGuiPacket.Handler::handle));
        map.put("close_gatekeeper_gui", new PacketInfo<>(CloseGatekeeperGuiPacket.class, CloseGatekeeperGuiPacket::write, CloseGatekeeperGuiPacket::read, CloseGatekeeperGuiPacket.Handler::handle));
    });

    public record PacketInfo<T>(Class<T> packetClass, BiConsumer<T, FriendlyByteBuf> write, Function<FriendlyByteBuf, T> read, Handler<T> handle) {

    }

    public interface Handler<T> {
        void handle(T object, Player player);
    }
}
