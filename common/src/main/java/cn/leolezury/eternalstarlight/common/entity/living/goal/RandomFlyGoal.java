package cn.leolezury.eternalstarlight.common.entity.living.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class RandomFlyGoal extends Goal {
	private final PathfinderMob mob;

	public RandomFlyGoal(PathfinderMob mob) {
		this.mob = mob;
		setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		return mob.getNavigation().isDone() && mob.getRandom().nextInt(5) == 0;
	}

	@Override
	public boolean canContinueToUse() {
		return mob.getNavigation().isInProgress() && mob.getRandom().nextInt(30) != 0;
	}

	@Override
	public void start() {
		Vec3 target = this.getRandomPos();
		mob.getNavigation().moveTo(target.x, target.y, target.z, 1);
	}

	private Vec3 getRandomPos() {
		Vec3 target = mob.position().add((mob.getRandom().nextFloat() - 0.5f) * 2 * 15, 0, (mob.getRandom().nextFloat() - 0.5f) * 2 * 15);

		BlockPos position = mob.blockPosition();
		int blockY = position.getY();
		if (!mob.level().getBlockState(position).isAir()) {
			for (int i = position.getY(); !mob.level().getBlockState(new BlockPos(position.getX(), i, position.getZ())).isAir(); i++) {
				if (i > mob.level().getMaxBuildHeight()) {
					break;
				}
				blockY = i;
			}
		} else {
			for (int i = position.getY(); mob.level().getBlockState(new BlockPos(position.getX(), i, position.getZ())).isAir(); i--) {
				if (i < mob.level().getMinBuildHeight()) {
					break;
				}
				blockY = i + 1;
			}
		}

		target = new Vec3(target.x, blockY + 5 + mob.getRandom().nextInt(3), target.z);

		BlockHitResult result = mob.level().clip(new ClipContext(mob.position(), target, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, mob));
		if (result.getType() != HitResult.Type.MISS) {
			target = result.getLocation();
		}

		return target;
	}
}
