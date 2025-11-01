package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.attack.LunarThorn;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class MoonringBowItem extends BowItem {
	public MoonringBowItem(Properties properties) {
		super(properties);
	}

	@Override
	protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
		Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
		if (projectile instanceof AbstractArrow arrow) {
			arrow.setBaseDamage(arrow.getBaseDamage() + 0.75);
		}
		return projectile;
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
				createThorn(level, livingEntity, livingEntity.getX() + x * i * 1.5, livingEntity.getY(), livingEntity.getZ() + z * i * 1.5, Mth.wrapDegrees(-livingEntity.getYRot()), 40, i * 5);
			}
		}
	}

	public static void createThorn(Level level, LivingEntity owner, double x, double y, double z, float yRot, double maxDiff, int delay) {
		BlockPos startPos = BlockPos.containing(x, y, z);
		boolean successful = false;
		double finalY = y;

		if (level.getBlockState(startPos).isAir()) {
			BlockHitResult result = level.clip(new ClipContext(startPos.getCenter(), startPos.getCenter().add(0, -maxDiff, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, owner));
			if (result.getType() != HitResult.Type.MISS) {
				finalY = result.getLocation().y;
				successful = true;
			}
		} else {
			int currentDiff = 0;
			while (!level.getBlockState(startPos).isAir() && currentDiff < maxDiff) {
				startPos = startPos.above();
				currentDiff++;
			}
			if (level.getBlockState(startPos).isAir()) {
				BlockHitResult result = level.clip(new ClipContext(startPos.getCenter(), startPos.getCenter().add(0, -maxDiff, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, owner));
				if (result.getType() != HitResult.Type.MISS) {
					finalY = result.getLocation().y;
					successful = true;
				}
			}
		}

		if (successful) {
			LunarThorn thorn = new LunarThorn(ESEntities.LUNAR_THORN.get(), level);
			thorn.setPos(x, finalY, z);
			thorn.setOwner(owner);
			thorn.setSpawnedTicks(-delay);
			thorn.setYRot(yRot);
			level.addFreshEntity(thorn);
		}
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
		return repairCandidate.is(ESItems.TENACIOUS_PETAL.get()) || repairCandidate.is(ESItems.TENACIOUS_VINE.get()) || super.isValidRepairItem(stack, repairCandidate);
	}
}
