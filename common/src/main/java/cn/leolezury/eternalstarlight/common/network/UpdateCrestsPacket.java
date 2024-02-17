package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.util.CrestUtil;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record UpdateCrestsPacket(List<String> crests) {
    public static UpdateCrestsPacket read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<String> crestList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            crestList.add(buf.readUtf(384));
        }
        return new UpdateCrestsPacket(crestList);
    }

    public static void write(UpdateCrestsPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.crests().size());
        for (String string : message.crests()) {
            buf.writeUtf(string, 384);
        }
    }

    public static class Handler {
        public static void handle(UpdateCrestsPacket message, Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                Registry<Crest> registry = serverPlayer.level().registryAccess().registryOrThrow(ESRegistries.CREST);
                List<Crest> crestList = message.crests().stream().map(s -> registry.get(new ResourceLocation(s))).toList();
                for (int i = 0; i < crestList.size(); i++) {
                    Crest crest = crestList.get(i);
                    for (int j = i + 1; j < crestList.size(); j++) {
                        if (crestList.get(j).type() == crest.type()) {
                            return;
                        }
                    }
                }
                List<Crest> ownedCrests = CrestUtil.getCrests(player, "OwnedCrests");
                if (crestList.stream().anyMatch(crest -> !ownedCrests.contains(crest))) {
                    return;
                }
                CrestUtil.setCrests(player, crestList);
            }
        }
    }
}