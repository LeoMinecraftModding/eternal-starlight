package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class GatekeeperBowComboPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 10;

	public GatekeeperBowComboPhase() {
		super(ID, 1, 76, 150);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTarget(entity, 18) && canReachTarget(entity, 35);
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		entity.setItemInHand(InteractionHand.MAIN_HAND, ESItems.GLISTERING_BOW.get().getDefaultInstance());
		entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		int ticks = entity.getBehaviorTicks();
		if (target != null) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
			if (ticks == 20 || ticks == 38 || ticks == 55) {
				ItemStack weapon = entity.getMainHandItem();
				if (weapon.getItem() instanceof BowItem) {
					ItemStack projectile = entity.getProjectile(weapon);
					AbstractArrow arrow = ProjectileUtil.getMobArrow(entity, projectile, 1, weapon);
					arrow.setBaseDamage(arrow.getBaseDamage() + 0.75);
					double x = target.getX() - entity.getX();
					double y = target.getY(1.0 / 3.0) - arrow.getY();
					double z = target.getZ() - entity.getZ();
					double xzDist = Math.sqrt(x * x + z * z);
					arrow.shoot(x, y + xzDist * 0.2, z, 2.0F, 0.1F);
					entity.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
					entity.level().addFreshEntity(arrow);
				}
			}
		}
	}

	@Override
	public boolean canContinue(TheGatekeeper entity) {
		return true;
	}
}
