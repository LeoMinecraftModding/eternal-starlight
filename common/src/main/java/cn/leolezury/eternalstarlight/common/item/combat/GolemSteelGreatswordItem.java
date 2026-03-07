package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.projectile.BallLightning;
import cn.leolezury.eternalstarlight.common.item.interfaces.SwingAttackWeapon;
import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GolemSteelGreatswordItem extends GreatswordItem implements SwingAttackWeapon {
	public GolemSteelGreatswordItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	private void performSpecialAttack(LivingEntity entity) {
		Level level = entity.level();
		if (!level.isClientSide && !SpecialItemCooldown.isOnCooldown(entity, this)) {
			Vec3 shootPos = entity.position().add(0, entity.getBbHeight() / 2, 0);
			BallLightning lastBall = null;
			for (int i = -1; i <= 1; i++) {
				BallLightning lightning = new BallLightning(level, shootPos.x, shootPos.y, shootPos.z);
				lightning.setOwner(entity);
				lightning.shootFromRotation(entity, entity.getXRot(), entity.getYRot() + i * 15, 0.0F, 0.8F, 2.0F);
				if (lastBall != null) {
					lightning.setTarget(lastBall);
				}
				level.addFreshEntity(lightning);
				lastBall = lightning;
			}
			SpecialItemCooldown.setCooldown(entity, this, 100);
		}
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postHurtEnemy(stack, target, attacker);
		performSpecialAttack(attacker);
	}

	@Override
	public void performSwingAttack(ItemStack stack, Player player) {
		performSpecialAttack(player);
	}
}
