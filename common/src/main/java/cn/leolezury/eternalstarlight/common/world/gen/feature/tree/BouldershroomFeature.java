package cn.leolezury.eternalstarlight.common.world.gen.feature.tree;

import cn.leolezury.eternalstarlight.common.block.BouldershroomBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class BouldershroomFeature extends Feature<NoneFeatureConfiguration> {
	public BouldershroomFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		RandomSource random = context.random();
		List<Direction> availableDirs = new ArrayList<>();
		for (Direction direction : Direction.values()) {
			BlockPos attachPos = pos.relative(direction.getOpposite());
			BlockState attachState = level.getBlockState(attachPos);
			if (attachState.isFaceSturdy(level, attachPos, direction) && !(attachState.getBlock() instanceof HugeMushroomBlock)) {
				availableDirs.add(direction);
			}
		}
		if (availableDirs.isEmpty()) {
			return false;
		}
		return BouldershroomBlock.growMushroom(level, random, pos, availableDirs.get(random.nextInt(availableDirs.size())));
	}
}
