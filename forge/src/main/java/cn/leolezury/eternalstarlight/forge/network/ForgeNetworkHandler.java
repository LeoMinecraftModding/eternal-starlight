package cn.leolezury.eternalstarlight.forge.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.network.ESPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeNetworkHandler {
    @SubscribeEvent
    public static <T> void onNetworkInit(RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(EternalStarlight.MOD_ID)
                .optional();
        for (Map.Entry<String, ESPackets.Handler<?>> entry : ESPackets.PACKETS.entrySet()) {
            registerMessage(registrar, entry);
        }
    }

    public static <T> void registerMessage(IPayloadRegistrar registrar, Map.Entry<String, ESPackets.Handler<?>> entry) {
        ResourceLocation id = new ResourceLocation(EternalStarlight.MOD_ID, entry.getKey());
        registrar.play(id, byteBuf -> new ESPayload<T>(id, (Function<FriendlyByteBuf, T>) entry.getValue().read(), (BiConsumer<T, FriendlyByteBuf>) entry.getValue().write(), (Consumer<T>) entry.getValue().handle(), byteBuf), (arg, context) -> context.workHandler().submitAsync(() -> arg.handler().accept(arg.packet())));
    }

    public static <T> void sendToClient(ServerPlayer serverPlayer, Object packet) {
        for (String id : ESPackets.PACKETS.keySet()) {
            ESPackets.Handler<?> handler = ESPackets.PACKETS.get(id);
            if (packet.getClass() == handler.packetClass()) {
                PacketDistributor.PLAYER.with(serverPlayer).send(new ESPayload<T>(new ResourceLocation(EternalStarlight.MOD_ID, id), (BiConsumer<T, FriendlyByteBuf>) handler.write(), (Consumer<T>) handler.handle(), (T) packet));
            }
        }
    }

    public static <T> void sendToServer(Object packet) {
        for (String id : ESPackets.PACKETS.keySet()) {
            ESPackets.Handler<?> handler = ESPackets.PACKETS.get(id);
            if (packet.getClass() == handler.packetClass()) {
                PacketDistributor.SERVER.noArg().send(new ESPayload<T>(new ResourceLocation(EternalStarlight.MOD_ID, id), (BiConsumer<T, FriendlyByteBuf>) handler.write(), (Consumer<T>) handler.handle(), (T) packet));
            }
        }
    }
}
