package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SolarEggBlockEntity extends BlockEntity implements DuskLightReceptor {
	public SolarEggBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.SOLAR_EGG.get(), pos, state);
	}

	@Override
	public void lightUp(Level level, BlockPos pos, Direction sourceDir) {
		// TODO: hatch the egg
	}
}
