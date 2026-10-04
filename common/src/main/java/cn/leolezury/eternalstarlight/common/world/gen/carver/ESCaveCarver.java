package cn.leolezury.eternalstarlight.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

public class ESCaveCarver extends CaveWorldCarver {
	public ESCaveCarver(Codec<CaveCarverConfiguration> codec) {
		super(codec);
	}

	@Override
	protected boolean canReplaceBlock(CaveCarverConfiguration carverConfiguration, BlockState blockState) {
		return super.canReplaceBlock(carverConfiguration, blockState) && blockState.getFluidState().isEmpty();
	}

	@Override
	public BlockState getCarveState(CarvingContext carvingContext, CaveCarverConfiguration carverConfiguration, BlockPos blockPos, Aquifer aquifer) {
		if (blockPos.getY() <= carverConfiguration.lavaLevel.resolveY(carvingContext)) {
			return LAVA.createLegacyBlock();
		} else {
			return CAVE_AIR;
		}
	}

	@Override
	protected boolean carveBlock(CarvingContext context, CaveCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeGetter, CarvingMask carvingMask, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos checkPos, Aquifer aquifer, MutableBoolean reachedSurface) {
		boolean carved = super.carveBlock(context, config, chunk, biomeGetter, carvingMask, pos, checkPos, aquifer, reachedSurface);
		BlockState carveState = getCarveState(context, config, pos, aquifer);
		if (carved && carveState != null && carveState.isAir()) {
			for (Direction direction : Direction.values()) {
				if (direction != Direction.DOWN) {
					BlockPos side = pos.relative(direction);
					if (!chunk.getBlockState(side).getFluidState().isEmpty()) {
						chunk.markPosForPostprocessing(side);
					}
				}
			}
		}
		return carved;
	}
}
