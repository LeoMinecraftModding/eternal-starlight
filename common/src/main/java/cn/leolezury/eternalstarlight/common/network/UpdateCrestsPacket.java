package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record UpdateCrestsPacket(List<String> crests) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateCrestsPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("update_crests"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateCrestsPacket> STREAM_CODEC = StreamCodec.ofMember(UpdateCrestsPacket::write, UpdateCrestsPacket::read);

    public static UpdateCrestsPacket read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<String> crestList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            crestList.add(buf.readUtf(384));
        }
        return new UpdateCrestsPacket(crestList);
    }

    public static void write(UpdateCrestsPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.crests().size());
        for (String string : packet.crests()) {
            buf.writeUtf(string, 384);
        }
    }

    public static void handle(UpdateCrestsPacket packet, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            Registry<Crest> registry = serverPlayer.level().registryAccess().registryOrThrow(ESRegistries.CREST);
            List<Crest> crestList = packet.crests().stream().map(s -> registry.get(new ResourceLocation(s))).toList();
            for (int i = 0; i < crestList.size(); i++) {
                Crest crest = crestList.get(i);
                for (int j = i + 1; j < crestList.size(); j++) {
                    if (crestList.get(j).type() == crest.type()) {
                        return;
                    }
                }
            }
            List<Crest> ownedCrests = ESCrestUtil.getCrests(player, "OwnedCrests");
            if (crestList.stream().anyMatch(crest -> !ownedCrests.contains(crest))) {
                return;
            }
            ESCrestUtil.setCrests(player, crestList);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}