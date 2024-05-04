package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.Book;
import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.DisplayBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.IndexBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.TextBookComponent;
import cn.leolezury.eternalstarlight.common.client.gui.screen.BookScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.CrestSelectionScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.GatekeeperDialogueScreen;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.network.*;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ClientHelper implements IClientHelper {
    @Override
    public void handleParticlePacket(ParticlePacket packet) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            clientLevel.addParticle(packet.particle(), packet.x(), packet.y(), packet.z(), packet.dx(), packet.dy(), packet.dz());
        }
    }

    @Override
    public void handleOpenCrestGui(OpenCrestGuiPacket packet) {
        Minecraft.getInstance().setScreen(new CrestSelectionScreen(packet.ownedCrests(), packet.crests()));
    }

    @Override
    public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket packet) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null && clientLevel.getEntity(packet.id()) instanceof TheGatekeeper gatekeeper) {
            Minecraft.getInstance().setScreen(new GatekeeperDialogueScreen(gatekeeper, packet.killedDragon(), packet.challenged()));
        }
    }

    @Override
    public void handleUpdateCamera(UpdateCameraPacket packet) {
        if (packet.cameraId() == -1) {
            ClientHandlers.resetCameraIn = 0;
        } else {
            if (Minecraft.getInstance().level != null && !(Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator)) {
                Entity camera = Minecraft.getInstance().level.getEntity(packet.cameraId());
                if (camera != null) {
                    ClientHandlers.resetCameraIn = 260;
                    ClientHandlers.oldCamera = Minecraft.getInstance().getCameraEntity();
                    ClientHandlers.oldHideGui = Minecraft.getInstance().options.hideGui;
                    Minecraft.getInstance().options.hideGui = true;
                    Minecraft.getInstance().setCameraEntity(camera);
                }
            }
        }
    }

    @Override
    public void handleClientMount(ClientMountPacket packet) {
        if (Minecraft.getInstance().level != null) {
            Entity rider = Minecraft.getInstance().level.getEntity(packet.riderId());
            Entity vehicle = Minecraft.getInstance().level.getEntity(packet.vehicleId());
            if (rider != null && vehicle != null) {
                rider.startRiding(vehicle, true);
            }
        }
    }

    @Override
    public void handleClientDismount(ClientDismountPacket packet) {
        if (Minecraft.getInstance().level != null) {
            Entity rider = Minecraft.getInstance().level.getEntity(packet.riderId());
            if (rider != null) {
                rider.stopRiding();
            }
        }
    }

    private String bookId(String id) {
        return "book." + EternalStarlight.MOD_ID + "." + id;
    }

    @Override
    public void handleOpenStarlightStory(OpenStarlightStoryPacket packet) {
        DisplayBookComponent title = new DisplayBookComponent(105, 130)
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
                .textDisplay(Component.translatable(ESItems.BOOK.get().getDescriptionId()), 52, 115, 1.2f);

        TextBookComponent preface = new TextBookComponent(Component.translatable(bookId("preface")), 105, 130);

        IndexBookComponent index = new IndexBookComponent(Component.translatable(bookId("index")), List.of(
                new IndexBookComponent.IndexItem(Component.literal("•").append(Component.translatable(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId())), new ResourceLocation(EternalStarlight.MOD_ID, "starlight_golem_display")),
                new IndexBookComponent.IndexItem(Component.literal("•").append(Component.translatable(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId())), new ResourceLocation(EternalStarlight.MOD_ID, "lunar_monstrosity_display"), packet.bossProgression() > 0),
                new IndexBookComponent.IndexItem(Component.literal("•").append(Component.translatable(ESEntities.TWILIGHT_GAZE.get().getDescriptionId())), new ResourceLocation(EternalStarlight.MOD_ID, "twilight_gaze_display")),
                new IndexBookComponent.IndexItem(Component.literal("•").append(Component.translatable(ESEntities.SHIMMER_LACEWING.get().getDescriptionId())), new ResourceLocation(EternalStarlight.MOD_ID, "shimmer_lacewing_display"))
        ), 105, 130);

        DisplayBookComponent golemDisplay = new DisplayBookComponent(105, 130)
                .entityDisplay(ESEntities.STARLIGHT_GOLEM.get(), 52, 75, -25, 210, 20, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
                .textDisplay(Component.translatable(ESEntities.STARLIGHT_GOLEM.get().getDescriptionId()), 52, 115, 1.2f);

        TextBookComponent golem = new TextBookComponent(Component.translatable(bookId("starlight_golem")), 105, 130);

        DisplayBookComponent lunarMonstrosityDisplay = new DisplayBookComponent(105, 130)
                .entityDisplay(ESEntities.LUNAR_MONSTROSITY.get(), 52, 80, -25, 210, 16, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
                .textDisplay(Component.translatable(ESEntities.LUNAR_MONSTROSITY.get().getDescriptionId()), 52, 115, 1.1f);

        TextBookComponent lunarMonstrosity = new TextBookComponent(Component.translatable(bookId("lunar_monstrosity")), 105, 130);

        DisplayBookComponent twilightGazeDisplay = new DisplayBookComponent(105, 130)
                .entityDisplay(ESEntities.TWILIGHT_GAZE.get(), 52, 60, 0, 210, 25, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
                .textDisplay(Component.translatable(ESEntities.TWILIGHT_GAZE.get().getDescriptionId()), 52, 115, 1.2f);

        TextBookComponent twilightGaze = new TextBookComponent(Component.translatable(bookId("twilight_gaze")), 105, 130);

        DisplayBookComponent shimmerLacewingDisplay = new DisplayBookComponent(105, 130)
                .entityDisplay(ESEntities.SHIMMER_LACEWING.get(), 52, 65, 0, 210, 25, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F))
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/frame.png"), 18, 20, 68, 68)
                .imageDisplay(new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/separator.png"), 4, 92, 96, 11)
                .textDisplay(Component.translatable(ESEntities.SHIMMER_LACEWING.get().getDescriptionId()), 52, 115, 1.2f);

        TextBookComponent shimmerLacewing = new TextBookComponent(Component.translatable(bookId("shimmer_lacewing")), 105, 130);

        Book book = new Book(Lists.newArrayList(
                new BookComponentDefinition(title, new ResourceLocation(EternalStarlight.MOD_ID, "title"), 11, 6, 5, 6),
                new BookComponentDefinition(preface, new ResourceLocation(EternalStarlight.MOD_ID, "preface"), 11, 6, 5, 6),
                new BookComponentDefinition(index, new ResourceLocation(EternalStarlight.MOD_ID, "index"), 11, 6, 5, 6),
                new BookComponentDefinition(golemDisplay, new ResourceLocation(EternalStarlight.MOD_ID, "starlight_golem_display"), 11, 6, 5, 6),
                new BookComponentDefinition(golem, new ResourceLocation(EternalStarlight.MOD_ID, "starlight_golem"), 11, 6, 5, 6),
                new BookComponentDefinition(lunarMonstrosityDisplay, new ResourceLocation(EternalStarlight.MOD_ID, "lunar_monstrosity_display"), 11, 6, 5, 6, packet.bossProgression() > 0),
                new BookComponentDefinition(lunarMonstrosity, new ResourceLocation(EternalStarlight.MOD_ID, "lunar_monstrosity"), 11, 6, 5, 6, packet.bossProgression() > 0),
                new BookComponentDefinition(twilightGazeDisplay, new ResourceLocation(EternalStarlight.MOD_ID, "twilight_gaze_display"), 11, 6, 5, 6),
                new BookComponentDefinition(twilightGaze, new ResourceLocation(EternalStarlight.MOD_ID, "twilight_gaze"), 11, 6, 5, 6),
                new BookComponentDefinition(shimmerLacewingDisplay, new ResourceLocation(EternalStarlight.MOD_ID, "shimmer_lacewing_display"), 11, 6, 5, 6),
                new BookComponentDefinition(shimmerLacewing, new ResourceLocation(EternalStarlight.MOD_ID, "shimmer_lacewing"), 11, 6, 5, 6)
        ), 240, 165, 18, 7, 12, 27,
                new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/book.png"),
                new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/book_cover.png"),
                new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/book_back_cover.png"),
                new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/book_flip_left.png"),
                new ResourceLocation(EternalStarlight.MOD_ID, "textures/gui/screen/book/book_flip_right.png"));
        Minecraft.getInstance().setScreen(new BookScreen(book));
    }
}
