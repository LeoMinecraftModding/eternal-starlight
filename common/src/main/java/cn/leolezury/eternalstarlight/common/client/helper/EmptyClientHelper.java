package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.*;

public class EmptyClientHelper implements IClientHelper {
    @Override
    public void handleParticlePacket(ParticlePacket message) {

    }

    @Override
    public void handleOpenCrestGui(OpenCrestGuiPacket message) {

    }

    @Override
    public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket message) {

    }

    @Override
    public void handleUpdateCamera(UpdateCameraPacket message) {

    }

    @Override
    public void handleClientMount(ClientMountPacket message) {

    }

    @Override
    public void handleClientDismount(ClientDismountPacket message) {

    }
}
