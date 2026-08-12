package cn.leolezury.eternalstarlight.common.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public class ESPackets {
	public enum Direction {
		SERVER_TO_CLIENT, CLIENT_TO_SERVER, BIDIRECTIONAL
	}

	public static final PacketInfo<SyncAttachmentsPacket> SYNC_ATTACHMENTS = new PacketInfo<>(Direction.SERVER_TO_CLIENT, SyncAttachmentsPacket.TYPE, SyncAttachmentsPacket.STREAM_CODEC, SyncAttachmentsPacket::handle);
	public static final PacketInfo<SimpleActionPacket> NO_PARAMETERS = new PacketInfo<>(Direction.BIDIRECTIONAL, SimpleActionPacket.TYPE, SimpleActionPacket.STREAM_CODEC, SimpleActionPacket::handle);
	public static final PacketInfo<ParticlePacket> PARTICLE = new PacketInfo<>(Direction.SERVER_TO_CLIENT, ParticlePacket.TYPE, ParticlePacket.STREAM_CODEC, ParticlePacket::handle);
	public static final PacketInfo<VfxPacket> VFX = new PacketInfo<>(Direction.SERVER_TO_CLIENT, VfxPacket.TYPE, VfxPacket.STREAM_CODEC, VfxPacket::handle);
	public static final PacketInfo<UpdateWeatherPacket> UPDATE_WEATHER = new PacketInfo<>(Direction.SERVER_TO_CLIENT, UpdateWeatherPacket.TYPE, UpdateWeatherPacket.STREAM_CODEC, UpdateWeatherPacket::handle);
	public static final PacketInfo<OpenCrestGuiPacket> OPEN_CREST_GUI = new PacketInfo<>(Direction.SERVER_TO_CLIENT, OpenCrestGuiPacket.TYPE, OpenCrestGuiPacket.STREAM_CODEC, OpenCrestGuiPacket::handle);
	public static final PacketInfo<UpdateCrestsPacket> UPDATE_CRESTS = new PacketInfo<>(Direction.CLIENT_TO_SERVER, UpdateCrestsPacket.TYPE, UpdateCrestsPacket.STREAM_CODEC, UpdateCrestsPacket::handle);
	public static final PacketInfo<UpdateCameraPacket> UPDATE_CAMERA = new PacketInfo<>(Direction.SERVER_TO_CLIENT, UpdateCameraPacket.TYPE, UpdateCameraPacket.STREAM_CODEC, UpdateCameraPacket::handle);
	public static final PacketInfo<ClientMountPacket> CLIENT_MOUNT = new PacketInfo<>(Direction.SERVER_TO_CLIENT, ClientMountPacket.TYPE, ClientMountPacket.STREAM_CODEC, ClientMountPacket::handle);
	public static final PacketInfo<ClientDismountPacket> CLIENT_DISMOUNT = new PacketInfo<>(Direction.SERVER_TO_CLIENT, ClientDismountPacket.TYPE, ClientDismountPacket.STREAM_CODEC, ClientDismountPacket::handle);
	public static final PacketInfo<OpenGatekeeperGuiPacket> OPEN_GATEKEEPER_GUI = new PacketInfo<>(Direction.SERVER_TO_CLIENT, OpenGatekeeperGuiPacket.TYPE, OpenGatekeeperGuiPacket.STREAM_CODEC, OpenGatekeeperGuiPacket::handle);
	public static final PacketInfo<CloseGatekeeperGuiPacket> CLOSE_GATEKEEPER_GUI = new PacketInfo<>(Direction.CLIENT_TO_SERVER, CloseGatekeeperGuiPacket.TYPE, CloseGatekeeperGuiPacket.STREAM_CODEC, CloseGatekeeperGuiPacket::handle);
	public static final PacketInfo<UpdateBookPacket> UPDATE_BOOK = new PacketInfo<>(Direction.SERVER_TO_CLIENT, UpdateBookPacket.TYPE, UpdateBookPacket.STREAM_CODEC, UpdateBookPacket::handle);
	public static final PacketInfo<UpdateBookProgressionPacket> UPDATE_BOOK_PROGRESSION = new PacketInfo<>(Direction.CLIENT_TO_SERVER, UpdateBookProgressionPacket.TYPE, UpdateBookProgressionPacket.STREAM_CODEC, UpdateBookProgressionPacket::handle);
	public static final PacketInfo<OpenBookPacket> OPEN_BOOK = new PacketInfo<>(Direction.SERVER_TO_CLIENT, OpenBookPacket.TYPE, OpenBookPacket.STREAM_CODEC, OpenBookPacket::handle);
	public static final PacketInfo<GatekeeperTalkPacket> GATEKEEPER_TALK = new PacketInfo<>(Direction.CLIENT_TO_SERVER, GatekeeperTalkPacket.TYPE, GatekeeperTalkPacket.STREAM_CODEC, GatekeeperTalkPacket::handle);
	public static final PacketInfo<UpdateBossBarPacket> UPDATE_BOSS_BAR = new PacketInfo<>(Direction.SERVER_TO_CLIENT, UpdateBossBarPacket.TYPE, UpdateBossBarPacket.STREAM_CODEC, UpdateBossBarPacket::handle);

	public record PacketInfo<T extends CustomPacketPayload>(Direction direction, CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, Handler<T> handler) {

	}

	public interface Handler<T extends CustomPacketPayload> {
		void handle(T object, Player player);
	}
}
