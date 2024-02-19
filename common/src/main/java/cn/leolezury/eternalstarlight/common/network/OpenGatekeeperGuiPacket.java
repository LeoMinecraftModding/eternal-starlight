package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record OpenGatekeeperGuiPacket(int id, boolean killedDragon, boolean challenged) {
    public static OpenGatekeeperGuiPacket read(FriendlyByteBuf buf) {
        int id = buf.readInt();
        boolean killedDragon = buf.readBoolean();
        boolean challenged = buf.readBoolean();
        return new OpenGatekeeperGuiPacket(id, killedDragon, challenged);
    }

    public static void write(OpenGatekeeperGuiPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.id());
        buf.writeBoolean(message.killedDragon());
        buf.writeBoolean(message.challenged());
    }

    public static class Handler {
        public static void handle(OpenGatekeeperGuiPacket message, Player player) {
            ESUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenGatekeeperGui(message));
        }
    }
}