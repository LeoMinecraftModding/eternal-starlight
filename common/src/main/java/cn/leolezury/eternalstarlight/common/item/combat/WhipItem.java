package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.attack.Whip;
import cn.leolezury.eternalstarlight.common.item.interfaces.SwingAttackWeapon;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class WhipItem extends TieredItem implements SwingAttackWeapon {
	public WhipItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float damage, float speed) {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	@NotNull
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (hand == InteractionHand.MAIN_HAND && !(level.getEntity(ESDataAttachments.WHIP.getData(player)) instanceof Whip)) {
			if (!level.isClientSide) {
				stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
				float strength = player.getAttackStrengthScale(0.5F);
				float damageScale = 0.2F + strength * strength * 0.8F;
				Whip whip = createWhip(level, player, stack, damageScale);
				level.addFreshEntity(whip);
				whip.playSound(ESSoundEvents.WHIP_SWISH.get(), 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
				player.awardStat(Stats.ITEM_USED.get(this));
			}
			player.resetAttackStrengthTicker();
			return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
		}
		return InteractionResultHolder.pass(stack);
	}

	@Override
	public void performSwingAttack(ItemStack stack, Player player) {
		Level level = player.level();
		if (!level.isClientSide && !(level.getEntity(ESDataAttachments.WHIP.getData(player)) instanceof Whip)) {
			stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
			float strength = player.getAttackStrengthScale(0.5F);
			float damageScale = 0.2F + strength * strength * 0.8F;
			Whip whip = createWhip(level, player, stack, damageScale);
			level.addFreshEntity(whip);
			whip.playSound(ESSoundEvents.WHIP_SWISH.get(), 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
			player.resetAttackStrengthTicker();
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		doPostHurtEffects(null, target);
	}

	public void doPostHurtEffects(@Nullable Whip whip, Entity entity) {
		if (entity.level() instanceof ServerLevel serverLevel) {
			double x = entity.getX() + (entity.getRandom().nextFloat() - 0.5) * entity.getBbWidth();
			double y = entity.getY() + entity.getRandom().nextFloat() * entity.getBbHeight();
			double z = entity.getZ() + (entity.getRandom().nextFloat() - 0.5) * entity.getBbWidth();
			serverLevel.sendParticles(ESExplosionParticleOptions.BLAST, x, y, z, 1, 0.2, 0.2, 0.2, 0.0);
			for (int i = 0; i < 4; i++) {
				Vec3 speed = new Vec3((entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F, entity.getRandom().nextFloat() * 0.05F, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F).normalize();
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.BLAST, x + speed.x * 0.6, y + speed.y * 0.6, z + speed.z * 0.6, speed.x, speed.y, speed.z));
			}
		}
	}

	public abstract Whip createWhip(Level level, Player owner, ItemStack weapon, float damageScale);
}
