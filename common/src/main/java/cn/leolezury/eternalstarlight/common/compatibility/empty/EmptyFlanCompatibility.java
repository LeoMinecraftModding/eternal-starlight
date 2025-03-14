package cn.leolezury.eternalstarlight.common.compatibility.empty;

import java.util.Optional;

import cn.leolezury.eternalstarlight.common.compatibility.ESFlanCompatibility;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform.Loader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class EmptyFlanCompatibility implements ESFlanCompatibility {

	@Override
	public boolean isModLoaded() {
		return false;
	}
	
	@Override
	public Optional<String> getModVersion() {
		return Optional.empty();
	}

	@Override
	public Optional<Loader> getLoader() {
		return Optional.empty();
	}

	@Override
	public boolean claimRestrictsBlockBreak(ServerLevel level, BlockPos pos, Entity entity) {
		return false;
	}

	@Override
	public boolean claimRestrictsBlockPlace(ServerLevel level, BlockPos pos, Entity entity) {
		return false;
	}

}
