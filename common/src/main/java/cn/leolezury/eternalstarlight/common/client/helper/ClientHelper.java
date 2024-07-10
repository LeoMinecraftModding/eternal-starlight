package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.*;

public interface ClientHelper {
	void handleS2cNoParam(NoParametersPacket packet);

	void handleParticlePacket(ParticlePacket packet);

	void handleOpenCrestGui(OpenCrestGuiPacket packet);

	void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket packet);

	void handleUpdateCamera(UpdateCameraPacket packet);

	void handleClientMount(ClientMountPacket packet);

	void handleClientDismount(ClientDismountPacket packet);

	void handleOpenStarlightStory(OpenStarlightStoryPacket packet);
}
