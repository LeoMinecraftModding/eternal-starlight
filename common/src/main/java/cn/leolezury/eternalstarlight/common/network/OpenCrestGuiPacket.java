package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record OpenCrestGuiPacket(List<String> crests) {
    public static OpenCrestGuiPacket read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<String> crestList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            crestList.add(buf.readUtf(384));
        }
        return new OpenCrestGuiPacket(crestList);
    }

    public static void write(OpenCrestGuiPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.crests().size());
        for (String string : message.crests()) {
            buf.writeUtf(string, 384);
        }
    }

    public static class Handler {
        public static void handle(OpenCrestGuiPacket message, Player player) {
            ESUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenCrestGui(message));
        }
    }
}