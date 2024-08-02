package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.attack.LunarThorn;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;

public class MoonringBowItem extends BowItem {
	public MoonringBowItem(Properties properties) {
		super(properties);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		super.releaseUsing(itemStack, level, livingEntity, n);
		int k = this.getUseDuration(itemStack, livingEntity) - n;
		float powerForTime = getPowerForTime(k);
		if (!level.isClientSide && livingEntity instanceof Player && powerForTime == 1.0) {
			float x = -Mth.sin(livingEntity.getYRot() * Mth.DEG_TO_RAD);
			float z = Mth.cos(livingEntity.getYRot() * Mth.DEG_TO_RAD);
			if (level instanceof ServerLevel serverLevel) {
				ScreenShakeVfx.createInstance(livingEntity.level().dimension(), livingEntity.position(), 30, 30, 0.15f, 0.24f, 4, 5).send(serverLevel);
			}
			for (int i = 0; i < 16; i++) {
				createThorn(level, livingEntity, livingEntity.getX() + x * i * 1.5, livingEntity.getY(), livingEntity.getZ() + z * i * 1.5, 40, i * 5);
			}
		}
	}

	private void createThorn(Level level, LivingEntity owner, double x, double y, double z, double maxDiff, int delay) {
		BlockPos startPos = BlockPos.containing(x, y, z);
		boolean successful = false;
		double finalY = y;

		if (level.getBlockState(startPos).isAir()) {
			ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(level, CollisionContext.of(owner), startPos.getCenter(), startPos.getCenter().add(0, -maxDiff, 0));
			if (result.blockHitResult() != null) {
				finalY = result.blockHitResult().getLocation().y;
				successful = true;
			}
		} else {
			int currentDiff = 0;
			while (!level.getBlockState(startPos).isAir() && currentDiff < maxDiff) {
				startPos = startPos.above();
				currentDiff++;
			}
			if (level.getBlockState(startPos).isAir()) {
				ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(level, CollisionContext.of(owner), startPos.getCenter(), startPos.getCenter().add(0, -maxDiff, 0));
				if (result.blockHitResult() != null) {
					finalY = result.blockHitResult().getLocation().y;
					successful = true;
				}
			}
		}

		if (successful) {
			LunarThorn thorn = new LunarThorn(ESEntities.LUNAR_THORN.get(), level);
			thorn.setPos(x, finalY, z);
			thorn.setOwner(owner);
			thorn.setSpawnedTicks(-delay);
			level.addFreshEntity(thorn);
		}
	}
}
