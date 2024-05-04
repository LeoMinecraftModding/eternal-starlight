package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record UpdateCameraPacket(int cameraId) implements CustomPacketPayload {
    public static final Type<UpdateCameraPacket> TYPE = new Type<>(EternalStarlight.id("update_camera"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateCameraPacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, UpdateCameraPacket::cameraId, UpdateCameraPacket::new);

    public static void handle(UpdateCameraPacket packet, Player player) {
        EternalStarlight.getClientHelper().handleUpdateCamera(packet);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
