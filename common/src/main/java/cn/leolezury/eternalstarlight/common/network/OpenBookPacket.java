package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.resource.book.BookData;
import cn.leolezury.eternalstarlight.common.resource.book.chapter.ChapterData;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record OpenBookPacket(BookData bookData, List<ChapterData> chapterDataList) {

    public static OpenBookPacket read(FriendlyByteBuf buf) {
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
            chapterBuilder.withEntityOffset(buf.readInt());
            dataList.add(chapterBuilder.build());
        }
        return new OpenBookPacket(builder.build(), dataList);
    }

    public static void write(OpenBookPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.bookData.bookWidth());
        buf.writeInt(message.bookData.bookHeight());
        buf.writeInt(message.bookData.textOffsetX());
        buf.writeInt(message.bookData.textOffsetY());
        buf.writeUtf(message.bookData.backgroundLocation().toString(), 384);
        buf.writeUtf(message.bookData.titleBackgroundLocation().toString(), 384);
        buf.writeUtf(message.bookData.title(), 384);
        buf.writeInt(message.chapterDataList.size());
        for (ChapterData chapterData : message.chapterDataList) {
            buf.writeUtf(chapterData.imageLocation().toString(), 384);
            buf.writeUtf(chapterData.displayEntity().toString(), 384);
            buf.writeUtf(chapterData.title(), 384);
            buf.writeUtf(chapterData.content(), 384);
            buf.writeFloat(chapterData.entityDisplayScale());
            buf.writeInt(chapterData.entityOffset());
        }
    }

    public static class Handler {
        public static void handle(OpenBookPacket message) {
            ESUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenBook(message));
        }
    }
}