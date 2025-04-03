package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.entity.misc.TearBomb;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TearBombBlock extends TntBlock {
	public static final MapCodec<TearBombBlock> CODEC = simpleCodec(TearBombBlock::new);

	public TearBombBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public void wasExploded(Level level, BlockPos blockPos, Explosion explosion) {
		if (!level.isClientSide) {
			TearBomb bomb = new TearBomb(level, blockPos.getX() + 0.5F, blockPos.getY(), blockPos.getZ() + 0.5F, explosion.getIndirectSourceEntity());
			int fuse = bomb.getFuse();
			bomb.setFuse(level.random.nextInt(fuse / 4) + fuse / 8);
			level.addFreshEntity(bomb);
		}
	}
}
