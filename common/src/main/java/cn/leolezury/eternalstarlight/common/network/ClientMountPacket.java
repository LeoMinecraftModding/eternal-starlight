package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientMountPacket(int riderId, int vehicleId) implements CustomPacketPayload {
    public static final Type<ClientMountPacket> TYPE = new Type<>(EternalStarlight.id("client_mount"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMountPacket> STREAM_CODEC = StreamCodec.ofMember(ClientMountPacket::write, ClientMountPacket::read);

    public static ClientMountPacket read(RegistryFriendlyByteBuf buf) {
        int riderId = buf.readInt();
        int vehicleId = buf.readInt();
        return new ClientMountPacket(riderId, vehicleId);
    }

    private static void write(ClientMountPacket packet, RegistryFriendlyByteBuf buf) {
        buf.writeInt(packet.riderId());
        buf.writeInt(packet.vehicleId());
    }

    public static void handle(ClientMountPacket packet, Player player) {
        ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleClientMount(packet));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
