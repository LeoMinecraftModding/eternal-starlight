package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.Book;
import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.IndexBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.TextBookComponent;
import cn.leolezury.eternalstarlight.common.client.gui.screen.BookScreen;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

// a reserved packet to test the network system when porting to a new version
public class TestPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TestPacket> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(EternalStarlight.MOD_ID, "test"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TestPacket> STREAM_CODEC = StreamCodec.ofMember(TestPacket::write, TestPacket::read);

    private final String name;
    
    public TestPacket(String string) {
        name = string;
    }

    public static TestPacket read(FriendlyByteBuf buf) {
        return new TestPacket(buf.readUtf(384));
    }

    public static void write(TestPacket message, FriendlyByteBuf buf) {
        buf.writeUtf(message.name);
    }

    public static void handle(TestPacket message, Player player) {
        LogUtils.getLogger().info("Received a test packet, name of the player: {}, is physical client: {}", message.name, ESPlatform.INSTANCE.isPhysicalClient());
        // todo delete this debug code
        if (ESPlatform.INSTANCE.isPhysicalClient()) {
            TextBookComponent component1 = new TextBookComponent(Component.literal("Hamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\nHamoud?\n"), 98, 144);
            TextBookComponent component2 = new TextBookComponent(Component.literal("HamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\nHamoudHabibi\n"), 98, 144);
            IndexBookComponent index = new IndexBookComponent(Component.literal("Ello Gays, Today we have a list of top 5 minecraft mod loaders"), 98, 144,
                    new IndexBookComponent.IndexItem(Component.literal("First Test Chapter"), new ResourceLocation(EternalStarlight.MOD_ID, "chapter1")),
                    new IndexBookComponent.IndexItem(Component.literal("Second Test Chapter"), new ResourceLocation(EternalStarlight.MOD_ID, "chapter2")));
            Book book = new Book(List.of(
                    new BookComponentDefinition(index, new ResourceLocation(EternalStarlight.MOD_ID, "index"), 11, 6, 5, 6),
                    new BookComponentDefinition(component1, new ResourceLocation(EternalStarlight.MOD_ID, "chapter1"), 11, 6, 5, 6),
                    new BookComponentDefinition(component2, new ResourceLocation(EternalStarlight.MOD_ID, "chapter2"), 11, 6, 5, 6)
            ), 226, 165, 18, 10, new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/book"),
                    new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/book_flip_left"),
                    new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/book_flip_right"));
            Minecraft.getInstance().setScreen(new BookScreen(book));
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
