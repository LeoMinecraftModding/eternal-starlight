package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.network.OpenBookPacket;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;

public class EmptyClientHelper implements IClientHelper {
    @Override
    public void handleOpenBook(OpenBookPacket message) {

    }

    @Override
    public void handleParticlePacket(ESParticlePacket message) {

    }

    @Override
    public void handleOpenCrestGui(OpenCrestGuiPacket message) {

    }

    @Override
    public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket message) {

    }
}
