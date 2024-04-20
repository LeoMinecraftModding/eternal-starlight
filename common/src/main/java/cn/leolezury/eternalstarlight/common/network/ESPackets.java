package cn.leolezury.eternalstarlight.common.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class ESPackets {
    public static final PacketInfo<TestPacket> TEST = new PacketInfo<>(TestPacket.TYPE, TestPacket.STREAM_CODEC, TestPacket::handle);
    public static final PacketInfo<ParticlePacket> PARTICLE = new PacketInfo<>(ParticlePacket.TYPE, ParticlePacket.STREAM_CODEC, ParticlePacket::handle);
    public static final PacketInfo<OpenBookPacket> OPEN_BOOK = new PacketInfo<>(OpenBookPacket.TYPE, OpenBookPacket.STREAM_CODEC, OpenBookPacket::handle);
    public static final PacketInfo<UpdateWeatherPacket> UPDATE_WEATHER = new PacketInfo<>(UpdateWeatherPacket.TYPE, UpdateWeatherPacket.STREAM_CODEC, UpdateWeatherPacket::handle);
    public static final PacketInfo<CancelWeatherPacket> CANCEL_WEATHER = new PacketInfo<>(CancelWeatherPacket.TYPE, CancelWeatherPacket.STREAM_CODEC, CancelWeatherPacket::handle);
    public static final PacketInfo<OpenCrestGuiPacket> OPEN_CREST_GUI = new PacketInfo<>(OpenCrestGuiPacket.TYPE, OpenCrestGuiPacket.STREAM_CODEC, OpenCrestGuiPacket::handle);
    public static final PacketInfo<UpdateCrestsPacket> UPDATE_CRESTS = new PacketInfo<>(UpdateCrestsPacket.TYPE, UpdateCrestsPacket.STREAM_CODEC, UpdateCrestsPacket::handle);
    public static final PacketInfo<OpenGatekeeperGuiPacket> OPEN_GATEKEEPER_GUI = new PacketInfo<>(OpenGatekeeperGuiPacket.TYPE, OpenGatekeeperGuiPacket.STREAM_CODEC, OpenGatekeeperGuiPacket::handle);
    public static final PacketInfo<CloseGatekeeperGuiPacket> CLOSE_GATEKEEPER_GUI = new PacketInfo<>(CloseGatekeeperGuiPacket.TYPE, CloseGatekeeperGuiPacket.STREAM_CODEC, CloseGatekeeperGuiPacket::handle);

    public record PacketInfo<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, Handler<T> handler) {

    }

    public interface Handler<T extends CustomPacketPayload> {
        void handle(T object, Player player);
    }
}
