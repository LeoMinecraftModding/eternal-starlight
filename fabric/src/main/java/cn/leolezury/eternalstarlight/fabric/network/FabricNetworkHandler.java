package cn.leolezury.eternalstarlight.fabric.network;

import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import cn.leolezury.eternalstarlight.common.network.ESPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class FabricNetworkHandler {
	public static void registerPackets() {
		ESCommonSetupHandler.registerPackets(new ESCommonSetupHandler.NetworkRegisterStrategy() {
			@Override
			public <T extends CustomPacketPayload> void register(ESPackets.PacketInfo<T> packetInfo) {
				PayloadTypeRegistry.playC2S().register(packetInfo.type(), packetInfo.streamCodec());
				PayloadTypeRegistry.playS2C().register(packetInfo.type(), packetInfo.streamCodec());
			}
		});
	}

	public static void registerPacketReceivers() {
		ESCommonSetupHandler.registerPackets(new ESCommonSetupHandler.NetworkRegisterStrategy() {
			@Override
			public <T extends CustomPacketPayload> void register(ESPackets.PacketInfo<T> packetInfo) {
				ServerPlayNetworking.registerGlobalReceiver(packetInfo.type(), (payload, context) -> packetInfo.handler().handle(payload, context.player()));
			}
		});
	}

	@Environment(EnvType.CLIENT)
	public static void registerClientPacketReceivers() {
		ESCommonSetupHandler.registerPackets(new ESCommonSetupHandler.NetworkRegisterStrategy() {
			@Override
			public <T extends CustomPacketPayload> void register(ESPackets.PacketInfo<T> packetInfo) {
				ClientPlayNetworking.registerGlobalReceiver(packetInfo.type(), (payload, context) -> packetInfo.handler().handle(payload, context.player()));
			}
		});
	}

	public static void sendToClient(ServerPlayer serverPlayer, CustomPacketPayload packet) {
		ServerPlayNetworking.send(serverPlayer, packet);
	}

	public static void sendToServer(CustomPacketPayload packet) {
		ClientPlayNetworking.send(packet);
	}
}
