package cn.leolezury.eternalstarlight.common.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class ESPackets {
    public static final PacketInfo<ParticlePacket> PARTICLE = new PacketInfo<>(ParticlePacket.TYPE, ParticlePacket.STREAM_CODEC, ParticlePacket::handle);
    public static final PacketInfo<UpdateWeatherPacket> UPDATE_WEATHER = new PacketInfo<>(UpdateWeatherPacket.TYPE, UpdateWeatherPacket.STREAM_CODEC, UpdateWeatherPacket::handle);
    public static final PacketInfo<CancelWeatherPacket> CANCEL_WEATHER = new PacketInfo<>(CancelWeatherPacket.TYPE, CancelWeatherPacket.STREAM_CODEC, CancelWeatherPacket::handle);
    public static final PacketInfo<OpenCrestGuiPacket> OPEN_CREST_GUI = new PacketInfo<>(OpenCrestGuiPacket.TYPE, OpenCrestGuiPacket.STREAM_CODEC, OpenCrestGuiPacket::handle);
    public static final PacketInfo<UpdateCrestsPacket> UPDATE_CRESTS = new PacketInfo<>(UpdateCrestsPacket.TYPE, UpdateCrestsPacket.STREAM_CODEC, UpdateCrestsPacket::handle);
    public static final PacketInfo<OpenGatekeeperGuiPacket> OPEN_GATEKEEPER_GUI = new PacketInfo<>(OpenGatekeeperGuiPacket.TYPE, OpenGatekeeperGuiPacket.STREAM_CODEC, OpenGatekeeperGuiPacket::handle);
    public static final PacketInfo<CloseGatekeeperGuiPacket> CLOSE_GATEKEEPER_GUI = new PacketInfo<>(CloseGatekeeperGuiPacket.TYPE, CloseGatekeeperGuiPacket.STREAM_CODEC, CloseGatekeeperGuiPacket::handle);
    public static final PacketInfo<UpdateCameraPacket> UPDATE_CAMERA = new PacketInfo<>(UpdateCameraPacket.TYPE, UpdateCameraPacket.STREAM_CODEC, UpdateCameraPacket::handle);
    public static final PacketInfo<ClientMountPacket> CLIENT_MOUNT = new PacketInfo<>(ClientMountPacket.TYPE, ClientMountPacket.STREAM_CODEC, ClientMountPacket::handle);
    public static final PacketInfo<ClientDismountPacket> CLIENT_DISMOUNT = new PacketInfo<>(ClientDismountPacket.TYPE, ClientDismountPacket.STREAM_CODEC, ClientDismountPacket::handle);
    public static final PacketInfo<OpenStarlightStoryPacket> OPEN_STARLIGHT_STORY = new PacketInfo<>(OpenStarlightStoryPacket.TYPE, OpenStarlightStoryPacket.STREAM_CODEC, OpenStarlightStoryPacket::handle);
    public static final PacketInfo<UpdateSpellDataPacket> UPDATE_SPELL_DATA = new PacketInfo<>(UpdateSpellDataPacket.TYPE, UpdateSpellDataPacket.STREAM_CODEC, UpdateSpellDataPacket::handle);

    public record PacketInfo<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, Handler<T> handler) {

    }

    public interface Handler<T extends CustomPacketPayload> {
        void handle(T object, Player player);
    }
}
