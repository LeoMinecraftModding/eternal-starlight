package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record OpenGatekeeperGuiPacket(int id) {
    public static OpenGatekeeperGuiPacket read(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new OpenGatekeeperGuiPacket(id);
    }

    public static void write(OpenGatekeeperGuiPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.id());
    }

    public static class Handler {
        public static void handle(OpenGatekeeperGuiPacket message, Player player) {
            ESUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenGatekeeperGui(message));
        }
    }
}