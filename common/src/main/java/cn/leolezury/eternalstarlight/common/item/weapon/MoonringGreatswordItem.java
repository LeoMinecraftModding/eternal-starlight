package cn.leolezury.eternalstarlight.common.item.weapon;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class MoonringGreatswordItem extends GreatswordItem {
	public MoonringGreatswordItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
		if (livingEntity.getPose() != Pose.STANDING) {
			livingEntity.stopUsingItem();
		}
	}

	@Override
	public int getUseDuration(ItemStack itemStack, LivingEntity entity) {
		return 72000;
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		} else if (player.getPose() == Pose.STANDING) {
			player.startUsingItem(interactionHand);
			return InteractionResultHolder.consume(itemStack);
		}
		return InteractionResultHolder.pass(itemStack);
	}
}