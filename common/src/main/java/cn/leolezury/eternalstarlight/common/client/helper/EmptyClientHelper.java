package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.network.*;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import net.minecraft.world.phys.Vec3;

public class EmptyClientHelper implements ClientHelper {
	@Override
	public void handleS2cNoParam(NoParametersPacket packet) {

	}

	@Override
	public void handleParticlePacket(ParticlePacket packet) {

	}

	@Override
	public void handleOpenCrestGui(OpenCrestGuiPacket packet) {

	}

	@Override
	public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket packet) {

	}

	@Override
	public void handleUpdateCamera(UpdateCameraPacket packet) {

	}

	@Override
	public void handleClientMount(ClientMountPacket packet) {

	}

	@Override
	public void handleClientDismount(ClientDismountPacket packet) {

	}

	@Override
	public void handleOpenStarlightStory(OpenStarlightStoryPacket packet) {

	}

	@Override
	public void spawnStellarRackParticles(Vec3 center) {

	}

	@Override
	public void spawnStellarRackItemParticles(Vec3 center) {

	}

	@Override
	public void spawnManaCrystalItemParticles(ManaType type, Vec3 center) {

	}
}
