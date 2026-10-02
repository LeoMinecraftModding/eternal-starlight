package cn.leolezury.eternalstarlight.common.item.combat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SickleItem extends TieredItem {
	public SickleItem(Tier tier, Properties properties) {
		super(tier, properties.component(DataComponents.TOOL, ShearsItem.createToolProperties()));
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, int damage, float speed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (float) damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	// [Vanilla copy] ShearsItem#mineBlock
	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
		if (!level.isClientSide && !state.is(BlockTags.FIRE)) {
			stack.hurtAndBreak(1, miningEntity, EquipmentSlot.MAINHAND);
		}

		return state.is(BlockTags.LEAVES)
			|| state.is(Blocks.COBWEB)
			|| state.is(Blocks.SHORT_GRASS)
			|| state.is(Blocks.FERN)
			|| state.is(Blocks.DEAD_BUSH)
			|| state.is(Blocks.HANGING_ROOTS)
			|| state.is(Blocks.VINE)
			|| state.is(Blocks.TRIPWIRE)
			|| state.is(BlockTags.WOOL);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
	}
}
