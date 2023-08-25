package cn.leolezury.eternalstarlight.network;

import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class ESPackets {
    public static final Map<String, Handler<?>> PACKETS = Util.make(new HashMap<>(), map -> {
        map.put("open_book", new Handler<>(OpenBookPacket.class, OpenBookPacket::write, OpenBookPacket::read, OpenBookPacket.Handler::handle));
    });

    public record Handler<T>(Class<T> packetClass, BiConsumer<T, FriendlyByteBuf> write, Function<FriendlyByteBuf, T> read, Consumer<T> handle) {

    }
}
