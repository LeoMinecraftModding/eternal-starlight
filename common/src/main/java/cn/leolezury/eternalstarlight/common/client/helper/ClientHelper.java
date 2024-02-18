package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.client.gui.screen.BookRenderData;
import cn.leolezury.eternalstarlight.common.client.gui.screen.CrestSelectionScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.ESBookScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.GatekeeperDialogueScreen;
import cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.network.OpenBookPacket;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.resource.book.BookData;
import cn.leolezury.eternalstarlight.common.resource.book.chapter.ChapterData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientHelper implements IClientHelper {
    @Override
    public void handleOpenBook(OpenBookPacket message) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            List<BookRenderData.ChapterRenderData> chapterRenderDataList = new ArrayList<>();
            for (ChapterData data : message.chapterDataList()) {
                BookRenderData.ChapterRenderData chapterRenderData = new BookRenderData.ChapterRenderData(data.displayEntity(), data.entityDisplayScale(), data.entityOffset(), Component.translatable(data.title()), Component.translatable(data.content()), data.imageLocation());
                chapterRenderDataList.add(chapterRenderData);
            }
            BookData data = message.bookData();
            BookRenderData bookRenderData = new BookRenderData(data.bookWidth(), data.bookHeight(), data.textOffsetX(), data.textOffsetY(), data.backgroundLocation(), data.titleBackgroundLocation(), Component.translatable(data.title()), chapterRenderDataList);
            Minecraft.getInstance().setScreen(new ESBookScreen(bookRenderData));
        }
    }

    @Override
    public void handleParticlePacket(ESParticlePacket message) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            clientLevel.addParticle(message.particle(), message.x(), message.y(), message.z(), message.dx(), message.dy(), message.dz());
        }
    }

    @Override
    public void handleOpenCrestGui(OpenCrestGuiPacket message) {
        Minecraft.getInstance().setScreen(new CrestSelectionScreen(message.ownedCrests(), message.crests()));
    }

    @Override
    public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket message) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel.getEntity(message.id()) instanceof TheGatekeeper gatekeeper) {
            Minecraft.getInstance().setScreen(new GatekeeperDialogueScreen(gatekeeper));
        }
    }
}
