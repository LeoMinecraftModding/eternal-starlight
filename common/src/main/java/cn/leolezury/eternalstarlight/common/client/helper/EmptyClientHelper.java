package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.network.OpenBookPacket;

public class EmptyClientHelper implements IClientHelper {
    @Override
    public void handleOpenBook(OpenBookPacket message) {

    }

    @Override
    public void handleParticlePacket(ESParticlePacket message) {

    }
}
