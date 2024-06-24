package cn.leolezury.eternalstarlight.common.world.gen.valuemap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class ValueMapGenerator {
	public static void place(ValueMapProvider provider, BlockPlacer placer) {
		placeAt(provider, placer, BlockPos.ZERO, new ArrayList<>());
	}

	private static void placeAt(ValueMapProvider provider, BlockPlacer placer, BlockPos pos, List<BlockPos> visited) {
		visited.add(pos);
		float value = provider.getValue(pos.getX(), pos.getY(), pos.getZ());
		if (value < 1 && value >= 0) {
			placer.place(pos, value);
			for (Direction direction : Direction.values()) {
				if (!visited.contains(pos.relative(direction))) {
					placeAt(provider, placer, pos.relative(direction), visited);
				}
			}
		}
	}

	public interface BlockPlacer {
		void place(BlockPos pos, float value);
	}
}
