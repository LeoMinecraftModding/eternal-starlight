package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.SolarEggBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SolarEggBlockEntity extends BlockEntity implements DuskLightReceptor {
	private int checkStructureTicks;

	public SolarEggBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.SOLAR_EGG.get(), pos, state);
	}

	@Override
	public void lightUp(Level level, BlockPos pos, Direction sourceDir) {
		// TODO: hatch the egg
	}

	public static void tick(Level level, BlockPos pos, BlockState state, SolarEggBlockEntity entity) {
		if (!level.isClientSide) {
			entity.checkStructureTicks++;
			if (entity.checkStructureTicks > 20 && state.getBlock() instanceof SolarEggBlock block) {
				block.checkStructure(level, pos);
				entity.checkStructureTicks = 0;
			}
		}
	}
}
