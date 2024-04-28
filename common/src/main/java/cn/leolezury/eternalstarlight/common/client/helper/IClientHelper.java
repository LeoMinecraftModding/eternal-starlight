package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.*;

public interface IClientHelper {
    void handleOpenBook(OpenBookPacket message);
    void handleParticlePacket(ParticlePacket message);
    void handleOpenCrestGui(OpenCrestGuiPacket message);
    void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket message);
    void handleUpdateCamera(UpdateCameraPacket message);
}
