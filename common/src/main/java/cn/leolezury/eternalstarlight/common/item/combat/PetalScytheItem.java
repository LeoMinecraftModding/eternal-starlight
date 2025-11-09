package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import cn.leolezury.eternalstarlight.common.item.interfaces.Swingable;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PetalScytheItem extends ScytheItem implements Swingable {
	public PetalScytheItem(Tier tier, boolean canTill, Properties properties) {
		super(tier, canTill, properties);
	}

	private void performSpecialAttack(LivingEntity entity) {
		Level level = entity.level();
		if (!level.isClientSide && !SpecialItemCooldown.isOnCooldown(entity, this)) {
			Vec3 shootPos = entity.position().add(0, entity.getBbWidth() / 2, 0);
			for (int i = -2; i <= 2; i++) {
				LunarSpore spore = new LunarSpore(level, entity, shootPos.x, shootPos.y, shootPos.z);
				spore.setNoGravity(true);
				spore.setDeltaMovement(ESMathUtil.rotationToPosition(0.9f, -entity.getViewXRot(0) + 5, entity.getViewYRot(0) + 90 + i * 8));
				level.addFreshEntity(spore);
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
	public void swing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
		performSpecialAttack(entity);
	}
}
