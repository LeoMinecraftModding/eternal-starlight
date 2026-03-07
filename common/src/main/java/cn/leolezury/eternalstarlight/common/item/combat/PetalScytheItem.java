package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.attack.PoisonousCloud;
import cn.leolezury.eternalstarlight.common.item.interfaces.SwingAttackWeapon;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PetalScytheItem extends ScytheItem implements SwingAttackWeapon {
	public PetalScytheItem(Tier tier, boolean canTill, Properties properties) {
		super(tier, canTill, properties);
	}

	private void performSpecialAttack(LivingEntity entity) {
		Level level = entity.level();
		if (!level.isClientSide && !SpecialItemCooldown.isOnCooldown(entity, this)) {
			RandomSource random = entity.getRandom();
			Vec3 shootPos = entity.position().add(0, entity.getBbHeight() / 4, 0);
			for (int i = -2; i <= 2; i++) {
				PoisonousCloud cloud = new PoisonousCloud(ESEntities.POISONOUS_CLOUD.get(), level);
				cloud.setOwner(entity);
				cloud.setPos(shootPos);
				cloud.setDeltaMovement(ESMathUtil.rotationToPosition(1, -entity.getViewXRot(0), entity.getViewYRot(0) + 90)
					.add(entity.getViewVector(1)
						.cross(new Vec3(random.nextDouble() * 0.1, 1, random.nextDouble() * 0.1))
						.normalize()
						.scale(i * 0.75))
					.normalize()
					.scale(0.6));
				level.addFreshEntity(cloud);
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
