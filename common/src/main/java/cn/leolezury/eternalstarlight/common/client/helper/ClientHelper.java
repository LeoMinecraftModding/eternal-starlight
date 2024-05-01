package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.client.gui.screen.CrestSelectionScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.GatekeeperDialogueScreen;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.network.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public class ClientHelper implements IClientHelper {
    @Override
    public void handleParticlePacket(ParticlePacket message) {
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
        if (clientLevel != null && clientLevel.getEntity(message.id()) instanceof TheGatekeeper gatekeeper) {
            Minecraft.getInstance().setScreen(new GatekeeperDialogueScreen(gatekeeper, message.killedDragon(), message.challenged()));
        }
    }

    @Override
    public void handleUpdateCamera(UpdateCameraPacket message) {
        if (message.cameraId() == -1) {
            ClientHandlers.resetCameraIn = 0;
        } else {
            if (Minecraft.getInstance().level != null && !(Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator)) {
                Entity camera = Minecraft.getInstance().level.getEntity(message.cameraId());
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
    public void handleClientMount(ClientMountPacket message) {
        if (Minecraft.getInstance().level != null) {
            Entity rider = Minecraft.getInstance().level.getEntity(message.riderId());
            Entity vehicle = Minecraft.getInstance().level.getEntity(message.vehicleId());
            if (rider != null && vehicle != null) {
                rider.startRiding(vehicle, true);
            }
        }
    }

    @Override
    public void handleClientDismount(ClientDismountPacket message) {
        if (Minecraft.getInstance().level != null) {
            Entity rider = Minecraft.getInstance().level.getEntity(message.riderId());
            if (rider != null) {
                rider.stopRiding();
            }
        }
    }
}
