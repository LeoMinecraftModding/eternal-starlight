package cn.leolezury.eternalstarlight.common.item.magic;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SimpleSpellItem extends Item {
	private final Holder<? extends AbstractSpell> spell;

	public SimpleSpellItem(Holder<? extends AbstractSpell> spell, Properties properties) {
		super(properties);
		this.spell = spell;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BOW;
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
		if (livingEntity instanceof SpellCaster caster && caster.getSpellData().spell() != spell.value()) {
			livingEntity.stopUsingItem();
			if (livingEntity instanceof Player player) {
				player.getCooldowns().addCooldown(this, spell.value().spellProperties().cooldownTicks());
			}
		}
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
		spell.value().stop(livingEntity, getUseDuration(itemStack, livingEntity) - i - spell.value().spellProperties().preparationTicks());
		if (livingEntity instanceof Player player) {
			player.getCooldowns().addCooldown(this, spell.value().spellProperties().cooldownTicks());
		}
	}

	public int getUseDuration(ItemStack itemStack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (!level.isClientSide && spell.value().canCast(player, false)) {
			player.startUsingItem(interactionHand);
			itemStack.hurtAndBreak(1, player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
			spell.value().start(player, false);
			return InteractionResultHolder.consume(itemStack);
		}
		return InteractionResultHolder.fail(itemStack);
	}
}
