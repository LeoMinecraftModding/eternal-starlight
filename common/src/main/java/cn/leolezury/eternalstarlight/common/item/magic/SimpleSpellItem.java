package cn.leolezury.eternalstarlight.common.item.magic;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
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
	public int getUseDuration(ItemStack itemStack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (!level.isClientSide && spell.value().canCast(player, false)) {
			itemStack.hurtAndBreak(1, player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
			if (player instanceof SpellCaster) {
				ESDataAttachments.SPELL_SOURCE.setData(player, new SpellCastData.ItemSpellSource(this, interactionHand));
			}
			spell.value().start(player, false);
		}
		return InteractionResultHolder.consume(itemStack);
	}
}
