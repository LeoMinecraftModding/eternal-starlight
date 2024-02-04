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
import java.util.function.Function;

public class FabricNetworkHandler {
    private static final Map<Class<?>, BiConsumer<?, FriendlyByteBuf>> PACKET_TO_WRITE_FUNC = new ConcurrentHashMap<>();
    private static final Map<Class<?>, ResourceLocation> PACKET_TO_LOCATION = new ConcurrentHashMap<>();
    private static boolean client = true;

    public static void init(boolean client) {
        FabricNetworkHandler.client = client;
        ESPackets.PACKETS.forEach(FabricNetworkHandler::register);
    }

    private static <T> void register(String path, ESPackets.PacketInfo<T> handler) {
        PACKET_TO_WRITE_FUNC.put(handler.packetClass(), handler.write());
        PACKET_TO_LOCATION.put(handler.packetClass(), new ResourceLocation(EternalStarlight.MOD_ID, path));
        if (client) {
            registerClientReceiver(path, handler.read(), handler.handle());
        } else {
            registerServerReceiver(path, handler.read(), handler.handle());
        }
    }

    public static <T> void sendToClient(ServerPlayer player, T packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        BiConsumer<T, FriendlyByteBuf> encoder = (BiConsumer<T, FriendlyByteBuf>) PACKET_TO_WRITE_FUNC.get(packet.getClass());
        encoder.accept(packet, buf);
        ServerPlayNetworking.send(player, PACKET_TO_LOCATION.get(packet.getClass()), buf);
    }

    public static <T> void registerServerReceiver(String path, Function<FriendlyByteBuf, T> read, ESPackets.Handler<T> handler) {
        ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(EternalStarlight.MOD_ID, path), (server, listener, player, buf, packetSender) -> {
            buf.retain();
            server.execute(() -> {
                T packet = read.apply(buf);
                handler.handle(packet, player.getPlayer());
                buf.release();
            });
        });
    }

    @Environment(EnvType.CLIENT)
    public static <T> void sendToServer(T packet) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        BiConsumer<T, FriendlyByteBuf> encoder = (BiConsumer<T, FriendlyByteBuf>) PACKET_TO_WRITE_FUNC.get(packet.getClass());
        encoder.accept(packet, buf);
        ClientPlayNetworking.send(PACKET_TO_LOCATION.get(packet.getClass()), buf);
    }

    @Environment(EnvType.CLIENT)
    public static <T> void registerClientReceiver(String path, Function<FriendlyByteBuf, T> read, ESPackets.Handler<T> handler) {
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(EternalStarlight.MOD_ID, path), (minecraft, clientPacketListener, buf, packetSender) -> {
            buf.retain();
            minecraft.execute(() -> {
                T packet = read.apply(buf);
                handler.handle(packet, minecraft.player);
                buf.release();
            });
        });
    }
}
