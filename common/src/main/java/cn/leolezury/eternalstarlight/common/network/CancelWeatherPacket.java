package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record CancelWeatherPacket(boolean cancel) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CancelWeatherPacket> TYPE = new Type<>(EternalStarlight.id("cancel_weather"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CancelWeatherPacket> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, CancelWeatherPacket::cancel, CancelWeatherPacket::new);

    public static void handle(CancelWeatherPacket packet, Player player) {
        if (packet.cancel) {
            ClientWeatherInfo.WEATHER = null;
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
