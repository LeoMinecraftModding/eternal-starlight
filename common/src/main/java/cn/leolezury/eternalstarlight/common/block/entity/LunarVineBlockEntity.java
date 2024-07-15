package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class LunarVineBlockEntity extends BlockEntity {
	private final List<Flower> flowers = new ArrayList<>();
	private BlockState oldState = null;

	public LunarVineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public LunarVineBlockEntity(BlockPos pos, BlockState state) {
		super(ESBlockEntities.LUNAR_VINE.get(), pos, state);
	}

	public List<Flower> getOrCreateFlowers() {
		if (getBlockState() != oldState || flowers.isEmpty()) {
			oldState = getBlockState();
			flowers.clear();
			flowers.addAll(generateFlowers());
		}
		return flowers;
	}

	private List<Flower> generateFlowers() {
		RandomSource random = RandomSource.create(getBlockPos().asLong());
		List<Flower> positions = new ArrayList<>();
		if (random.nextInt(10) == 0) {
			int numFlowers = random.nextInt(3, 5);
			for (int i = 0; i < numFlowers; i++) {
				positions.add(new Flower(new Vec3(random.nextDouble() - 0.5, -random.nextDouble(), 0.5), random.nextInt(120, 150), random.nextInt(-15, 15)));
			}
		}
		return positions;
	}

	public record Flower(Vec3 pos, float xRot, float yRot) {

	}
}
