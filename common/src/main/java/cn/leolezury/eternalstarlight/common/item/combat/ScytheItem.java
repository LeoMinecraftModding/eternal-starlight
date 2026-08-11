package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.SickleHarvestable;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.function.Consumer;

public class ScytheItem extends TieredItem {

	public ScytheItem(Tier tier, Properties properties) {
		super(tier, properties.component(DataComponents.TOOL, tier.createToolProperties(BlockTags.MINEABLE_WITH_HOE)));
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, int damage, float speed, float reach, float sweep) {
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (float) damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(EternalStarlight.id("weapon.entity_reach"), reach, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(EternalStarlight.id("weapon.block_reach"), reach, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		if (sweep > 0) {
			builder.add(Attributes.SWEEPING_DAMAGE_RATIO, new AttributeModifier(EternalStarlight.id("weapon.sweeping_damage_ratio"), sweep, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
		}
		return builder.build();
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		if (level.getBlockState(blockpos).getBlock() instanceof SickleHarvestable block && context.getItemInHand().is(ESTags.Items.SICKLES)) {
			Player player = context.getPlayer();
			level.destroyBlock(blockpos, false);
			if (!level.isClientSide) {
				if (player != null && block.getSeed().isPresent()) {
					context.getItemInHand().hurtAndBreak(1, player, context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
					ItemEntity itemEntity = new ItemEntity(
						level,
						blockpos.getX(),
						blockpos.getY(),
						blockpos.getZ(),
						BuiltInRegistries.ITEM.get(block.getSeed().get()).getDefaultInstance()
					);
					itemEntity.setDeltaMovement(
						level.random.triangle(0, 0.035),
						level.random.triangle(0.2, 0.035),
						level.random.triangle(0, 0.035)
					);
					level.addFreshEntity(itemEntity);
				}
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return InteractionResult.PASS;
		}
	}

	public static Consumer<UseOnContext> changeIntoState(BlockState state) {
		return (context) -> {
			context.getLevel().setBlock(context.getClickedPos(), state, 11);
			context.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, context.getClickedPos(), GameEvent.Context.of(context.getPlayer(), state));
		};
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
