package cn.leolezury.eternalstarlight.common.network;

import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class ESPackets {
    public static final Map<String, Handler<?>> PACKETS = Util.make(new HashMap<>(), map -> {
        // test packet
        map.put("test_packet", new Handler<>(TestPacket.class, TestPacket::write, TestPacket::read, TestPacket.Handler::handle));

        map.put("particle", new Handler<>(ESParticlePacket.class, ESParticlePacket::write, ESParticlePacket::read, ESParticlePacket.Handler::handle));
        map.put("open_book", new Handler<>(OpenBookPacket.class, OpenBookPacket::write, OpenBookPacket::read, OpenBookPacket.Handler::handle));
        map.put("update_weather", new Handler<>(ESWeatherPacket.class, ESWeatherPacket::write, ESWeatherPacket::read, ESWeatherPacket.Handler::handle));
        map.put("cancel_weather", new Handler<>(CancelWeatherPacket.class, CancelWeatherPacket::write, CancelWeatherPacket::read, CancelWeatherPacket.Handler::handle));
    });

    public record Handler<T>(Class<T> packetClass, BiConsumer<T, FriendlyByteBuf> write, Function<FriendlyByteBuf, T> read, Consumer<T> handle) {

    }
}
