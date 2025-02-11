package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MoonlightBushBlock extends ESShortBushBlock {
	public MoonlightBushBlock(Properties properties) {
		super(9, properties);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(4) == 0) {
			ParticleUtils.spawnParticleInBlock(level, pos, random.nextInt(1, 4), random.nextInt(4) == 0 ? ESParticles.FIREFLY.get() : ESParticles.STARDUST.get());
		}
	}
}
