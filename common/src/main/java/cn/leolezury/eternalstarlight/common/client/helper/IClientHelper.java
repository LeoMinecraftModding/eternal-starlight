package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.OpenBookPacket;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;

public interface IClientHelper {
    void handleOpenBook(OpenBookPacket message);
    void handleParticlePacket(ParticlePacket message);
    void handleOpenCrestGui(OpenCrestGuiPacket message);
    void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket message);
}
