package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.attack.Whip;
import cn.leolezury.eternalstarlight.common.item.interfaces.Swingable;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class WhipItem extends Item implements Swingable {
	public WhipItem(Properties properties) {
		super(properties);
	}

	@NotNull
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (hand == InteractionHand.MAIN_HAND && !level.isClientSide && !(level.getEntity(ESDataAttachments.WHIP.getData(player)) instanceof Whip)) {
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			level.addFreshEntity(createWhip(level, player, stack));
			player.awardStat(Stats.ITEM_USED.get(this));
		}
		return hand == InteractionHand.MAIN_HAND ? InteractionResultHolder.sidedSuccess(stack, level.isClientSide) : InteractionResultHolder.pass(stack);
	}

	@Override
	public void swing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
		Level level = entity.level();
		if (hand == InteractionHand.MAIN_HAND && !level.isClientSide && entity instanceof Player player && !(level.getEntity(ESDataAttachments.WHIP.getData(player)) instanceof Whip)) {
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			level.addFreshEntity(createWhip(level, player, stack));
		}
	}

	public abstract Whip createWhip(Level level, Player owner, ItemStack weapon);

	@Override
	public int getEnchantmentValue() {
		return 1;
	}
}
