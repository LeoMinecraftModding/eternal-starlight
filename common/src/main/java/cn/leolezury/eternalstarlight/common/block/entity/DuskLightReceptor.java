package cn.leolezury.eternalstarlight.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public interface DuskLightReceptor {
	void lightUp(Level level, BlockPos pos, Direction sourceDir);
}
