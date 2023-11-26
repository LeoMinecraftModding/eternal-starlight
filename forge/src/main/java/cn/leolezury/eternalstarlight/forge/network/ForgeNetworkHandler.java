package cn.leolezury.eternalstarlight.forge.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.network.ESPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.simple.SimpleChannel;

import java.util.Map;

public class ForgeNetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel SIMPLE_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(EternalStarlight.MOD_ID, "main_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        int registered = 0;
        for (Map.Entry<String, ESPackets.Handler<?>> entry : ESPackets.PACKETS.entrySet()) {
            registerMessage(registered++, entry.getValue());
        }
    }

    public static <T> void registerMessage(int idx, ESPackets.Handler<T> handler) {
        SIMPLE_CHANNEL.registerMessage(idx, handler.packetClass(), (object, arg) -> handler.write().accept(object, arg), arg -> handler.read().apply(arg), (msg, context) -> {
            context.enqueueWork(() -> handler.handle().accept(msg));
        });
    }

    public static void sendToClient(ServerPlayer serverPlayer, Object packet) {
        SIMPLE_CHANNEL.sendTo(packet, serverPlayer.connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        SIMPLE_CHANNEL.sendToServer(packet);
    }
}
