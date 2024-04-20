package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.OpenBookPacket;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;

public class EmptyClientHelper implements IClientHelper {
    @Override
    public void handleOpenBook(OpenBookPacket message) {

    }

    @Override
    public void handleParticlePacket(ParticlePacket message) {

    }

    @Override
    public void handleOpenCrestGui(OpenCrestGuiPacket message) {

    }

    @Override
    public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket message) {

    }
}
