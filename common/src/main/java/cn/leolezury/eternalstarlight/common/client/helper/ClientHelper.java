package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.*;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import net.minecraft.world.phys.Vec3;

public interface ClientHelper {
	void handleS2cNoParam(NoParametersPacket packet);

	void handleParticlePacket(ParticlePacket packet);

	void handleOpenCrestGui(OpenCrestGuiPacket packet);

	void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket packet);

	void handleUpdateCamera(UpdateCameraPacket packet);

	void handleClientMount(ClientMountPacket packet);

	void handleClientDismount(ClientDismountPacket packet);

	void handleOpenStarlightStory(OpenStarlightStoryPacket packet);

	void spawnStellarRackParticles(Vec3 center);

	void spawnStellarRackItemParticles(Vec3 center);

	void spawnManaCrystalItemParticles(ManaType type, Vec3 center);
}
