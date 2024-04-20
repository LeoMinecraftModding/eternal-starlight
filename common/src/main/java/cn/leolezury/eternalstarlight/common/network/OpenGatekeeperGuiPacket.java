package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public record OpenGatekeeperGuiPacket(int id, boolean killedDragon, boolean challenged) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenGatekeeperGuiPacket> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(EternalStarlight.MOD_ID, "open_gatekeeper_gui"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenGatekeeperGuiPacket> STREAM_CODEC = StreamCodec.ofMember(OpenGatekeeperGuiPacket::write, OpenGatekeeperGuiPacket::read);

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

    public static void handle(OpenGatekeeperGuiPacket message, Player player) {
        EternalStarlight.getClientHelper().handleOpenGatekeeperGui(message);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}