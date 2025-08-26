package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.EntityDataAttachment;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record SyncAttachmentsPacket(int id, EntityDataAttachment<?> attachment, byte[] data) implements CustomPacketPayload {
	public static final Type<SyncAttachmentsPacket> TYPE = new Type<>(EternalStarlight.id("sync_attachments"));

	public static final StreamCodec<RegistryFriendlyByteBuf, SyncAttachmentsPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, SyncAttachmentsPacket::id,
		ResourceLocation.STREAM_CODEC.map((id) -> Objects.requireNonNull(ESDataAttachments.byId(id)), EntityDataAttachment::id), SyncAttachmentsPacket::attachment,
		ByteBufCodecs.BYTE_ARRAY, SyncAttachmentsPacket::data,
		SyncAttachmentsPacket::new);

	public static SyncAttachmentsPacket create(Entity entity, EntityDataAttachment<?> attachment, @Nullable Object value, RegistryAccess access) {
		StreamCodec<? super RegistryFriendlyByteBuf, Object> codec = (StreamCodec<? super RegistryFriendlyByteBuf, Object>) attachment.streamCodec();

		RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), access);

		if (value != null) {
			buf.writeBoolean(true);
			codec.encode(buf, value);
		} else {
			buf.writeBoolean(false);
		}

		byte[] encoded = buf.array();

		return new SyncAttachmentsPacket(entity.getId(), attachment, encoded);
	}

	public static void handle(SyncAttachmentsPacket packet, Player player) {
		applyData(packet, player.level());
	}

	private static @Nullable Object decodeValue(SyncAttachmentsPacket packet, RegistryAccess access) {
		StreamCodec<? super RegistryFriendlyByteBuf, Object> codec = (StreamCodec<? super RegistryFriendlyByteBuf, Object>) packet.attachment().streamCodec();
		if (codec == null) {
			return null;
		}
		RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.copiedBuffer(packet.data()), access);
		return !buf.readBoolean() ? null : codec.decode(buf);
	}

	private static void applyData(SyncAttachmentsPacket packet, Level level) {
		Entity entity = level.getEntity(packet.id());
		if (entity != null) {
			Object value = decodeValue(packet, level.registryAccess());
			if (value == null) {
				((EntityDataAttachment<Object>) packet.attachment()).removeData(entity);
			} else {
				((EntityDataAttachment<Object>) packet.attachment()).setData(entity, value);
			}
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
