package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NightfallMudBlock extends MudBlock {
	public static final MapCodec<NightfallMudBlock> CODEC = simpleCodec(NightfallMudBlock::new);

	public NightfallMudBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(80) == 0 && level.getBlockState(pos.below()).isAir()) {
			for (int i = 0; i < random.nextInt(1, 4); i++) {
				level.addParticle(random.nextInt(3) == 0 ? ParticleTypes.DRIPPING_WATER : ESParticles.DRIPPING_MUD.get(), Mth.lerp(level.random.nextDouble(), pos.getX(), pos.getX() + 1), pos.getY(), Mth.lerp(level.random.nextDouble(), pos.getZ(), pos.getZ() + 1), 0.0, 0.0, 0.0);
			}
		}
	}
}
