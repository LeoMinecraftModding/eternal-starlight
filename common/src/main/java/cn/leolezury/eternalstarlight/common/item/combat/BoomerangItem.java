package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownBoomerang;
import net.minecraft.core.component.DataComponents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BoomerangItem extends TieredItem implements ProjectileItem {
	public BoomerangItem(Tier tier, Properties properties) {
		super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float damage, float speed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	public static Tool createToolProperties() {
		return new Tool(List.of(), 1.0F, 2);
	}

	public abstract ThrownBoomerang createBoomerang(Level level, @Nullable LivingEntity owner, double x, double y, double z, ItemStack pickupItemStack);

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (isTooDamagedToUse(stack)) {
			return InteractionResultHolder.fail(stack);
		} else {
			if (!level.isClientSide) {
				stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
				Vec3 shootPos = player.getEyePosition();
				ThrownBoomerang boomerang = createBoomerang(level, player, shootPos.x, shootPos.y, shootPos.z, stack);
				boomerang.setNoGravity(true);
				boomerang.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 0.3F);
				if (player.hasInfiniteMaterials()) {
					boomerang.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				}
				level.addFreshEntity(boomerang);
				if (!player.hasInfiniteMaterials()) {
					player.getInventory().removeItem(stack);
				}
				player.getCooldowns().addCooldown(this, 15);
			}
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.consume(stack);
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

	@Override
	public int getEnchantmentValue() {
		return 1;
	}
}
