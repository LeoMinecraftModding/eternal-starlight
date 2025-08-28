package cn.leolezury.eternalstarlight.common.weather;

import cn.leolezury.eternalstarlight.common.client.ClientWeatherState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class EmptyWeather extends AbstractWeather {
	public EmptyWeather(Properties properties) {
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

	}

	@Environment(EnvType.CLIENT)
	@Override
	public float modifyRainLevel(float original) {
		float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
		ClientWeatherState.levelTarget = 1;
		return ClientWeatherState.getRainLevel(partialTick);
	}
}
