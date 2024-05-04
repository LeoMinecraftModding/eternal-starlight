package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public record OpenStarlightStoryPacket(int bossProgression) implements CustomPacketPayload {
    public static final Type<OpenStarlightStoryPacket> TYPE = new Type<>(new ResourceLocation(EternalStarlight.MOD_ID, "open_starlight_story"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenStarlightStoryPacket> STREAM_CODEC = StreamCodec.ofMember(OpenStarlightStoryPacket::write, OpenStarlightStoryPacket::read);

    public static OpenStarlightStoryPacket read(FriendlyByteBuf buf) {
        int bossProgression = buf.readInt();
        return new OpenStarlightStoryPacket(bossProgression);
    }

    public static void write(OpenStarlightStoryPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.bossProgression());
    }

    public static void handle(OpenStarlightStoryPacket packet, Player player) {
        ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenStarlightStory(packet));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
