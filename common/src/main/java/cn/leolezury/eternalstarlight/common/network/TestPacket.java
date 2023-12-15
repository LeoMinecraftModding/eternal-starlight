package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;

public class TestPacket {
    private String name;
    
    public TestPacket(String s) {
        name = s;
    }

    public static TestPacket read(FriendlyByteBuf buf) {
        return new TestPacket(buf.readUtf(384));
    }

    public static void write(TestPacket message, FriendlyByteBuf buf) {
        buf.writeUtf(message.name);
    }

    public static class Handler {
        public static void handle(TestPacket message) {
            LogUtils.getLogger().info("Received a test packet, name of the player: " + message.name + ", is physical client: " + ESPlatform.INSTANCE.isPhysicalClient());
        }
    }
}
