package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.network.OpenBookPacket;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;

public interface IClientHelper {
    void handleOpenBook(OpenBookPacket message);
    void handleParticlePacket(ESParticlePacket message);
    void handleOpenCrestGui(OpenCrestGuiPacket message);
}
