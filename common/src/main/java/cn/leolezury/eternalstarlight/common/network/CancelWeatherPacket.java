package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class CancelWeatherPacket {
    private final boolean cancel;

    public CancelWeatherPacket(boolean cancel) {
        this.cancel = cancel;
    }


    public static CancelWeatherPacket read(FriendlyByteBuf buf) {
        return new CancelWeatherPacket(buf.readBoolean());
    }

    public static void write(CancelWeatherPacket message, FriendlyByteBuf buf) {
        buf.writeBoolean(message.cancel);
    }

    public static class Handler {
        public static void handle(CancelWeatherPacket message, Player player) {
            if (message.cancel) {
                ClientWeatherInfo.WEATHER = null;
            }
        }
    }
}
