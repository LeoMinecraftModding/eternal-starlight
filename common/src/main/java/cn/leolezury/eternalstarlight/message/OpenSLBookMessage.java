package cn.leolezury.eternalstarlight.message;

import cn.leolezury.eternalstarlight.client.gui.screens.BookRenderData;
import cn.leolezury.eternalstarlight.client.gui.screens.SLBookScreen;
import cn.leolezury.eternalstarlight.manager.book.BookData;
import cn.leolezury.eternalstarlight.manager.book.chapter.ChapterData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OpenSLBookMessage {
    private BookData bookData;
    private List<ChapterData> chapterDataList;

    public OpenSLBookMessage(BookData bookData, List<ChapterData> chapterDataList) {
        this.bookData = bookData;
        this.chapterDataList = chapterDataList;
    }

    public static OpenSLBookMessage read(FriendlyByteBuf buf) {
        BookData.Builder builder = new BookData.Builder();
        builder.withWidth(buf.readInt());
        builder.withHeight(buf.readInt());
        builder.withTextOffsetX(buf.readInt());
        builder.withTextOffsetY(buf.readInt());
        builder.withBackground(new ResourceLocation(buf.readUtf(384)));
        builder.withTitleBackground(new ResourceLocation(buf.readUtf(384)));
        builder.withTitle(buf.readUtf(384));
        int size = buf.readInt();
        List<ChapterData> dataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ChapterData.Builder chapterBuilder = new ChapterData.Builder();
            chapterBuilder.withImage(new ResourceLocation(buf.readUtf(384)));
            chapterBuilder.withEntityDisplay(new ResourceLocation(buf.readUtf(384)));
            chapterBuilder.withTitle(buf.readUtf(384));
            chapterBuilder.withContent(buf.readUtf(384));
            chapterBuilder.withDisplayScale(buf.readFloat());
            dataList.add(chapterBuilder.build());
        }
        return new OpenSLBookMessage(builder.build(), dataList);
    }

    public static void write(OpenSLBookMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.bookData.bookWidth);
        buf.writeInt(message.bookData.bookHeight);
        buf.writeInt(message.bookData.textOffsetX);
        buf.writeInt(message.bookData.textOffsetY);
        buf.writeUtf(message.bookData.getBackgroundLocation().toString(), 384);
        buf.writeUtf(message.bookData.getTitleBackgroundLocation().toString(), 384);
        buf.writeUtf(message.bookData.getTitle(), 384);
        buf.writeInt(message.chapterDataList.size());
        for (ChapterData chapterData : message.chapterDataList) {
            buf.writeUtf(chapterData.getImageLocation().toString(), 384);
            buf.writeUtf(chapterData.getDisplayEntity().toString(), 384);
            buf.writeUtf(chapterData.getTitle(), 384);
            buf.writeUtf(chapterData.getContent(), 384);
            buf.writeFloat(chapterData.entityDisplayScale);
        }
    }

    public static class Handler {
        public static void handle(OpenSLBookMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                context.enqueueWork(() -> {
                    LocalPlayer localPlayer = Minecraft.getInstance().player;
                    if (localPlayer != null) {
                        List<BookRenderData.ChapterRenderData> chapterRenderDataList = getRenderDataList(message);
                        BookRenderData bookRenderData = new BookRenderData(message.bookData.getBackgroundLocation(), message.bookData.getTitleBackgroundLocation(), Component.translatable(message.bookData.getTitle()), chapterRenderDataList, message.bookData.bookWidth, message.bookData.bookHeight, message.bookData.textOffsetX, message.bookData.textOffsetY);
                        Minecraft.getInstance().setScreen(new SLBookScreen(bookRenderData));
                    }
                });
            }
            context.setPacketHandled(true);
        }

        @NotNull
        private static List<BookRenderData.ChapterRenderData> getRenderDataList(OpenSLBookMessage message) {
            List<BookRenderData.ChapterRenderData> chapterRenderDataList = new ArrayList<>();
            for (ChapterData data : message.chapterDataList) {
                BookRenderData.ChapterRenderData chapterRenderData = new BookRenderData.ChapterRenderData(data.getDisplayEntity(), data.getImageLocation(), Component.translatable(data.getTitle()), Component.translatable(data.getContent()), data.entityDisplayScale);
                chapterRenderDataList.add(chapterRenderData);
                System.out.println("building CRD list, content:" + chapterRenderData.getContent().getString());
            }
            return chapterRenderDataList;
        }
    }
}