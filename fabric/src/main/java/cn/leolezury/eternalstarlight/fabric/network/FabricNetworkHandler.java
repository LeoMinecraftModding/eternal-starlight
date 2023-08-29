package cn.leolezury.eternalstarlight.fabric.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.network.ESPackets;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class FabricNetworkHandler {
    private static final Map<Class<?>, BiConsumer<?, FriendlyByteBuf>> ENCODERS = new ConcurrentHashMap<>();
    private static final Map<Class<?>, ResourceLocation> PACKET_LOCATION_MAP = new ConcurrentHashMap<>();
    private static boolean client = true;

    public static void init(boolean client) {
        FabricNetworkHandler.client = client;
        ESPackets.PACKETS.forEach(FabricNetworkHandler::register);
    }

    private static <T> void register(String path, ESPackets.Handler<T> handler) {
        registerMessage(path, handler.packetClass(), handler.write(), handler.read(), handler.handle());
    }

    private static <T> void registerMessage(String path, Class<T> packetClass, BiConsumer<T, FriendlyByteBuf> encode, Function<FriendlyByteBuf, T> decode, Consumer<T> handler) {
        ENCODERS.put(packetClass, encode);
        PACKET_LOCATION_MAP.put(packetClass, new ResourceLocation(EternalStarlight.MOD_ID, path));
        if (client) {
            registerClientReceiver(path, decode, handler);
        } else {
            registerServerReceiver(path, decode, handler);
        }
    }

    public static <T> void sendToClient(ServerPlayer player, T packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        BiConsumer<T, FriendlyByteBuf> encoder = (BiConsumer<T, FriendlyByteBuf>) ENCODERS.get(packet.getClass());
        encoder.accept(packet, buf);
        ServerPlayNetworking.send(player, PACKET_LOCATION_MAP.get(packet.getClass()), buf);
    }

    @Environment(EnvType.CLIENT)
    public static <T> void sendToServer(T packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        BiConsumer<T, FriendlyByteBuf> encoder = (BiConsumer<T, FriendlyByteBuf>) ENCODERS.get(packet.getClass());
        encoder.accept(packet, buf);
        ClientPlayNetworking.send(PACKET_LOCATION_MAP.get(packet.getClass()), buf);
    }

    @Environment(EnvType.CLIENT)
    public static <T> void registerClientReceiver(String path, Function<FriendlyByteBuf, T> read, Consumer<T> handler) {
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(EternalStarlight.MOD_ID, path), (minecraft, clientPacketListener, buf, packetSender) -> {
            buf.retain();
            minecraft.execute(() -> {
                T packet = read.apply(buf);
                handler.accept(packet);
                buf.release();
            });
        });
    }

    public static <T> void registerServerReceiver(String path, Function<FriendlyByteBuf, T> read, Consumer<T> handler) {
        ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(EternalStarlight.MOD_ID, path), (server, listener, player, buf, packetSender) -> {
            buf.retain();
            server.execute(() -> {
                T packet = read.apply(buf);
                handler.accept(packet);
                buf.release();
            });
        });
    }
}