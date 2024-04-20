package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record CloseGatekeeperGuiPacket(int id, int operation) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CloseGatekeeperGuiPacket> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(EternalStarlight.MOD_ID, "close_gatekeeper_gui"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CloseGatekeeperGuiPacket> STREAM_CODEC = StreamCodec.ofMember(CloseGatekeeperGuiPacket::write, CloseGatekeeperGuiPacket::read);

    public static CloseGatekeeperGuiPacket read(FriendlyByteBuf buf) {
        int id = buf.readInt();
        int operation = buf.readInt();
        return new CloseGatekeeperGuiPacket(id, operation);
    }

    public static void write(CloseGatekeeperGuiPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.id());
        buf.writeInt(message.operation());
    }

    public static void handle(CloseGatekeeperGuiPacket message, Player player) {
        if (player instanceof ServerPlayer serverPlayer && serverPlayer.serverLevel().getEntity(message.id()) instanceof TheGatekeeper gatekeeper) {
            gatekeeper.handleDialogueClose(message.operation());
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}