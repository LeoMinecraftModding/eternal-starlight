package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownSpear;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class SpearItem extends TieredItem implements ProjectileItem {
	public SpearItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
		return !player.isCreative();
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.SPEAR;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
		if (living instanceof Player player) {
			int i = this.getUseDuration(stack, living) - timeLeft;
			if (i >= 10) {
				if (!isTooDamagedToUse(stack)) {
					if (!level.isClientSide) {
						stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(living.getUsedItemHand()));
						Vec3 shootPos = player.getEyePosition();
						ThrownSpear spear = createSpear(level, player, shootPos.x, shootPos.y, shootPos.z, stack);
						spear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
						if (player.hasInfiniteMaterials()) {
							spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
						}
						level.addFreshEntity(spear);
						if (!player.hasInfiniteMaterials()) {
							player.getInventory().removeItem(stack);
						}
					}
					player.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	public abstract ThrownSpear createSpear(Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack);

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (isTooDamagedToUse(itemstack)) {
			return InteractionResultHolder.fail(itemstack);
		} else {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(itemstack);
		}
	}

	private static boolean isTooDamagedToUse(ItemStack stack) {
		return stack.getDamageValue() >= stack.getMaxDamage() - 1;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
	}
}
