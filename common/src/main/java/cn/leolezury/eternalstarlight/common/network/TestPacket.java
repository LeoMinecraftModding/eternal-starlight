package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

// a reserved packet to test the network system when porting to a new version
public class TestPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TestPacket> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(EternalStarlight.MOD_ID, "test"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TestPacket> STREAM_CODEC = StreamCodec.ofMember(TestPacket::write, TestPacket::read);

    private final String name;
    
    public TestPacket(String s) {
        name = s;
    }

    public static TestPacket read(FriendlyByteBuf buf) {
        return new TestPacket(buf.readUtf(384));
    }

    public static void write(TestPacket message, FriendlyByteBuf buf) {
        buf.writeUtf(message.name);
    }

    public static void handle(TestPacket message, Player player) {
        LogUtils.getLogger().info("Received a test packet, name of the player: " + message.name + ", is physical client: " + ESPlatform.INSTANCE.isPhysicalClient());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
