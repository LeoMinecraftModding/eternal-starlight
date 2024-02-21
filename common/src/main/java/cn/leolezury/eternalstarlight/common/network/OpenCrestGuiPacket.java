package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record OpenCrestGuiPacket(List<String> ownedCrests, List<String> crests) {
    public static OpenCrestGuiPacket read(FriendlyByteBuf buf) {
        int ownedSize = buf.readInt();
        List<String> ownedCrestList = new ArrayList<>();
        for (int i = 0; i < ownedSize; i++) {
            ownedCrestList.add(buf.readUtf(384));
        }
        int size = buf.readInt();
        List<String> crestList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            crestList.add(buf.readUtf(384));
        }
        return new OpenCrestGuiPacket(ownedCrestList, crestList);
    }

    public static void write(OpenCrestGuiPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.ownedCrests().size());
        for (String string : message.ownedCrests()) {
            buf.writeUtf(string, 384);
        }
        buf.writeInt(message.crests().size());
        for (String string : message.crests()) {
            buf.writeUtf(string, 384);
        }
    }

    public static class Handler {
        public static void handle(OpenCrestGuiPacket message, Player player) {
            ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenCrestGui(message));
        }
    }
}