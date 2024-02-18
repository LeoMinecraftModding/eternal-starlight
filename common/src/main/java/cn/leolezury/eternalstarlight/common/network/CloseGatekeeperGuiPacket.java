package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper.TheGatekeeper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record CloseGatekeeperGuiPacket(int id, int operation) {
    public static CloseGatekeeperGuiPacket read(FriendlyByteBuf buf) {
        int id = buf.readInt();
        int operation = buf.readInt();
        return new CloseGatekeeperGuiPacket(id, operation);
    }

    public static void write(CloseGatekeeperGuiPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.id());
        buf.writeInt(message.operation());
    }

    public static class Handler {
        public static void handle(CloseGatekeeperGuiPacket message, Player player) {
            if (player instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().getEntity(message.id()) instanceof TheGatekeeper gatekeeper) {
                gatekeeper.handleDialogueClose(message.operation());
            }
        }
    }
}