package cn.leolezury.eternalstarlight.common.item.weapon;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Iterator;
import java.util.List;

public class CommonScytheItem extends ScytheItem {
	public CommonScytheItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		// Copied from vanilla Player.attack
		if (attacker instanceof Player player) {
			DamageSource damageSource = attacker.damageSources().playerAttack(player);
			LivingEntity livingEntity3;
			float l = (float) (1.0F + player.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) * attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
			List<LivingEntity> list = attacker.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0, 0.25, 1.0));
			Iterator var20 = list.iterator();

			label177:
			while (true) {
				do {
					do {
						do {
							do {
								if (!var20.hasNext()) {
									attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, attacker.getSoundSource(), 1.0F, 1.0F);
									player.sweepAttack();
									break label177;
								}

								livingEntity3 = (LivingEntity) var20.next();
							} while (livingEntity3 == attacker);
						} while (livingEntity3 == entity);
					} while (attacker.isAlliedTo(livingEntity3));
				} while (livingEntity3 instanceof ArmorStand && ((ArmorStand) livingEntity3).isMarker());

				if (attacker.distanceToSqr(livingEntity3) < 9.0) {
					float m = player.getEnchantedDamage(livingEntity3, l, damageSource) * player.getAttackStrengthScale(0.5F);
					livingEntity3.knockback(0.4000000059604645, (double) Mth.sin(attacker.getYRot() * 0.017453292F), -Mth.cos(attacker.getYRot() * 0.017453292F));
					livingEntity3.hurt(damageSource, m);
					if (attacker.level() instanceof ServerLevel serverLevel) {
						EnchantmentHelper.doPostAttackEffects(serverLevel, livingEntity3, damageSource);
					}
				}
			}
		}
		return super.hurtEnemy(stack, entity, attacker);
	}
}
