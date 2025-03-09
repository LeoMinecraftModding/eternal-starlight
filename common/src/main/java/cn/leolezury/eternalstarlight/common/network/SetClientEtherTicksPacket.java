package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record SetClientEtherTicksPacket(int entityId, int ticks) implements CustomPacketPayload {
	public static final Type<SetClientEtherTicksPacket> TYPE = new Type<>(EternalStarlight.id("set_client_ether_ticks"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SetClientEtherTicksPacket> STREAM_CODEC = StreamCodec.ofMember(SetClientEtherTicksPacket::write, SetClientEtherTicksPacket::read);

	public static SetClientEtherTicksPacket read(RegistryFriendlyByteBuf buf) {
		int entityId = buf.readInt();
		int ticks = buf.readInt();
		return new SetClientEtherTicksPacket(entityId, ticks);
	}

	private static void write(SetClientEtherTicksPacket packet, RegistryFriendlyByteBuf buf) {
		buf.writeInt(packet.entityId());
		buf.writeInt(packet.ticks());
	}

	public static void handle(SetClientEtherTicksPacket packet, Player player) {
		Entity entity = player.level().getEntity(packet.entityId());
		if (entity != null) {
			ESEntityUtil.getPersistentData(entity).putInt(CommonHandlers.TAG_CLIENT_IN_ETHER_TICKS, packet.ticks());
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
