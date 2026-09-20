package cn.leolezury.eternalstarlight.common.weather;

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

	@Override
	public void clientTick() {

	}
}
