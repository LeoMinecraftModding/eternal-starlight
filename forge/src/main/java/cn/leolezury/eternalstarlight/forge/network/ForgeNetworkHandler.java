package cn.leolezury.eternalstarlight.forge.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonSetupHandlers;
import cn.leolezury.eternalstarlight.common.network.ESPackets;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeNetworkHandler {
    @SubscribeEvent
    public static void onNetworkInit(RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(EternalStarlight.MOD_ID)
                .optional();
        CommonSetupHandlers.registerPackets(new CommonSetupHandlers.NetworkRegisterStrategy() {
            @Override
            public <T extends CustomPacketPayload> void register(ESPackets.PacketInfo<T> packetInfo) {
                registrar.play(packetInfo.type(), packetInfo.streamCodec(), (packet, context) -> packetInfo.handler().handle(packet, context.player().orElse(null)));
            }
        });
    }

    public static void sendToClient(ServerPlayer serverPlayer, CustomPacketPayload packet) {
        PacketDistributor.PLAYER.with(serverPlayer).send(packet);
    }

    public static void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.SERVER.noArg().send(packet);
    }
}
