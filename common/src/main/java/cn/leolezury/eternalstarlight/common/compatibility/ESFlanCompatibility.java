package cn.leolezury.eternalstarlight.common.compatibility;

import java.util.Iterator;
import java.util.ServiceLoader;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.compatibility.empty.EmptyFlanCompatibility;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface ESFlanCompatibility extends ESModCompatibility {
	ESFlanCompatibility INSTANCE = Util.make(() -> {
		final ServiceLoader<ESFlanCompatibility> loader = ServiceLoader.load(ESFlanCompatibility.class);
		final Iterator<ESFlanCompatibility> iterator = loader.iterator();
		if (!iterator.hasNext()) {
			// Optional compatibility preferably shouldn't cause the mod to error or fail loading
			EternalStarlight.LOGGER.warn("Flan compatibility instance not found! Defaulting to empty...");
			return new EmptyFlanCompatibility();
		} else {
			ESFlanCompatibility instance = iterator.next();
			if (iterator.hasNext()) {
				EternalStarlight.LOGGER.warn("More than one Flan compatibility instance was found! Defaulting to empty...");
				return new EmptyFlanCompatibility();
			}
			return instance;
		}
	});
	
	String MOD_ID = "flan";
	
	// Instance methods
	@Override
	default String getModId() {
		return MOD_ID;
	}
	
	public boolean claimRestrictsBlockBreak(ServerLevel level, BlockPos pos, Entity entity);

	public boolean claimRestrictsBlockPlace(ServerLevel level, BlockPos pos, Entity entity);
	
	// Static methods
	public static boolean restrictBlockBreak(Level level, BlockPos pos, Entity entity) {
		if(ESConfig.INSTANCE.checkFlanClaims && level instanceof ServerLevel serverLevel) {
			return ESFlanCompatibility.INSTANCE.isModLoaded() && ESFlanCompatibility.INSTANCE.claimRestrictsBlockBreak(serverLevel, pos, entity);
		} else {
			return false;
		}
	}

	public static boolean restrictBlockPlace(Level level, BlockPos pos, Entity entity) {
		if(ESConfig.INSTANCE.checkFlanClaims && level instanceof ServerLevel serverLevel) {
			return ESFlanCompatibility.INSTANCE.isModLoaded() && ESFlanCompatibility.INSTANCE.claimRestrictsBlockPlace(serverLevel, pos, entity);
		} else {
			return false;
		}
	}
}
