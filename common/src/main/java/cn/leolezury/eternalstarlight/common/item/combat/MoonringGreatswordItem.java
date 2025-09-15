package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class MoonringGreatswordItem extends GreatswordItem {
	public MoonringGreatswordItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postHurtEnemy(stack, target, attacker);
		Level level = attacker.level();
		if (!level.isClientSide && !SpecialItemCooldown.isOnCooldown(attacker, this)) {
			float radiusIncrement = Math.min(target.getBbWidth() * 0.75f, 3);
			for (int i = 0; i < 4; i++) {
				float radius = (i + 1) * radiusIncrement;
				int num = Math.max(Math.round(7 * radius), 5);
				float startAngle = attacker.getRandom().nextFloat() * Mth.PI;
				for (int j = 0; j < num; j++) {
					float angle = startAngle + (Mth.TWO_PI / num) * j;
					MoonringBowItem.createThorn(level, attacker, target.getX() + Math.cos(angle) * radius, target.getY(), target.getZ() + Math.sin(angle) * radius, Mth.wrapDegrees(-angle * Mth.RAD_TO_DEG), 40, i * 7);
				}
			}
			SpecialItemCooldown.setCooldown(attacker, this, 125);
		}
	}
}
