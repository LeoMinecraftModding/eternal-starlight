package cn.leolezury.eternalstarlight.common.weather;

import cn.leolezury.eternalstarlight.common.client.ClientWeatherState;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class MeteorRainWeather extends AbstractWeather {
	public MeteorRainWeather(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canStart(ServerLevel level) {
		return true;
	}

	@Override
	public boolean canContinue(ServerLevel level, int ticks) {
		return true;
	}

	@Override
	public void serverTick(ServerLevel level, int ticks) {

	}

	@Override
	public void tickBlock(ServerLevel level, int ticks, BlockPos pos) {
		if (level.getRandom().nextInt(3000) == 0) {
			int targetX = pos.getX();
			int targetY = pos.getY();
			int targetZ = pos.getZ();
			RandomSource random = level.getRandom();
			AethersentMeteor meteor = new AethersentMeteor(level, null, targetX + (random.nextFloat() - 0.5) * 3, targetY + 200 + (random.nextFloat() - 0.5) * 5, targetZ + (random.nextFloat() - 0.5) * 3);
			meteor.setSize(10);
			meteor.setTargetPos(new Vec3(targetX, targetY, targetZ));
			meteor.setOnlyHurtEnemy(false);
			level.addFreshEntity(meteor);
			level.sendParticles(ParticleTypes.EXPLOSION, meteor.getX(), meteor.getY(), meteor.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.0D);
		}
	}

	@Override
	public void onStart(ServerLevel level) {

	}

	@Override
	public void onStop(ServerLevel level, int ticks) {

	}

	@Environment(EnvType.CLIENT)
	@Override
	public void clientTick() {
		ClientLevel level = Minecraft.getInstance().level;
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		if (level != null && level.getGameTime() % 20 == 0) {
			Vec3 randomPos = camera.getPosition().offsetRandom(level.getRandom(), 75f);
			int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) randomPos.x, (int) randomPos.z);
			level.addParticle(ESParticles.METEOR.get(), true, randomPos.x, Math.max(height + 75, camera.getPosition().y + 75), randomPos.z, 0, 0, 0);
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public float modifyRainLevel(float original) {
		float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
		ClientWeatherState.levelTarget = 1;
		return ClientWeatherState.getRainLevel(partialTick);
	}
}
