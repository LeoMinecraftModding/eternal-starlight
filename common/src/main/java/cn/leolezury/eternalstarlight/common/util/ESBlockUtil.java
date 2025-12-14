package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ESBlockUtil {
	public static boolean isEntityInBlock(Entity entity, Block block) {
		AABB box = entity.getBoundingBox();
		BlockPos fromPos = BlockPos.containing(box.minX + 1.0E-7, box.minY + 1.0E-7, box.minZ + 1.0E-7);
		BlockPos toPos = BlockPos.containing(box.maxX - 1.0E-7, box.maxY - 1.0E-7, box.maxZ - 1.0E-7);
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (int i = fromPos.getX(); i <= toPos.getX(); ++i) {
			for (int j = fromPos.getY(); j <= toPos.getY(); ++j) {
				for (int k = fromPos.getZ(); k <= toPos.getZ(); ++k) {
					mutableBlockPos.set(i, j, k);
					BlockState blockState = entity.level().getBlockState(mutableBlockPos);
					if (blockState.is(block)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static List<BlockPos> getBlocksInBoundingBox(AABB box) {
		List<BlockPos> posList = new ArrayList<>();
		BlockPos fromPos = BlockPos.containing(box.minX + 1.0E-7, box.minY + 1.0E-7, box.minZ + 1.0E-7);
		BlockPos toPos = BlockPos.containing(box.maxX - 1.0E-7, box.maxY - 1.0E-7, box.maxZ - 1.0E-7);
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (int i = fromPos.getX(); i <= toPos.getX(); ++i) {
			for (int j = fromPos.getY(); j <= toPos.getY(); ++j) {
				for (int k = fromPos.getZ(); k <= toPos.getZ(); ++k) {
					mutableBlockPos.set(i, j, k);
					posList.add(mutableBlockPos.immutable());
				}
			}
		}
		return posList;
	}

	public static VoxelShape rotateVoxelShape(VoxelShape shape, Direction direction) {
		return rotateVoxelShape(shape, 0, 0, direction);
	}

	public static VoxelShape rotateVoxelShape(VoxelShape shape, int xRotOffset, int yRotOffset, Direction direction) {
		AtomicReference<VoxelShape> result = new AtomicReference<>(Shapes.empty());
		shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
			float rotX = (direction == Direction.DOWN ? 180 : direction == Direction.UP ? 0 : 90) * Mth.DEG_TO_RAD
				+ xRotOffset * Mth.DEG_TO_RAD;
			float rotY = (-direction.toYRot() + 180) * Mth.DEG_TO_RAD
				+ yRotOffset * Mth.DEG_TO_RAD;
			Vec3 min = new Vec3(minX - 0.5, minY - 0.5, minZ - 0.5);
			min = min.xRot(rotX).yRot(rotY);
			Vec3 max = new Vec3(maxX - 0.5, maxY - 0.5, maxZ - 0.5);
			max = max.xRot(rotX).yRot(rotY);
			result.set(Shapes.or(result.get(), Shapes.create(
				Math.min(min.x, max.x) + 0.5,
				Math.min(min.y, max.y) + 0.5,
				Math.min(min.z, max.z) + 0.5,
				Math.max(min.x, max.x) + 0.5,
				Math.max(min.y, max.y) + 0.5,
				Math.max(min.z, max.z) + 0.5
			)));
		});
		return result.get();
	}
}
