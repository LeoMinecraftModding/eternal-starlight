package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.List;

public class DualWieldingSwordItem extends SwordItem {
	public DualWieldingSwordItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (hand == InteractionHand.OFF_HAND && player.getMainHandItem().is(this)) {
			HitResult result = pick(player, player.blockInteractionRange(), player.entityInteractionRange());
			if (result instanceof EntityHitResult entityResult && entityResult.getType() != HitResult.Type.MISS) {
				ESDataAttachments.OFFHAND_ATTACK.setData(player, true);
				player.attack(entityResult.getEntity());
				ESDataAttachments.OFFHAND_ATTACK.setData(player, false);
			}
			ESDataAttachments.OFFHAND_ATTACK_STRENGTH_TIMER.setData(player, 0);
			player.swing(InteractionHand.OFF_HAND);
			player.awardStat(Stats.ITEM_USED.get(this));
		}
		return super.use(level, player, hand);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(CommonComponents.EMPTY);
		list.add(Component.translatable("tooltip." + EternalStarlight.ID + ".dual_wielding").withStyle(ChatFormatting.GRAY));
		list.add(Component.literal(" ").append(Component.translatable("tooltip." + EternalStarlight.ID + ".dual_wielding.offhand_attack").withStyle(ChatFormatting.BLUE)));
		super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
	}

	// copied from GameRenderer
	protected HitResult pick(Entity entity, double blockInteractionRange, double entityInteractionRange) {
		double maxRange = Math.max(blockInteractionRange, entityInteractionRange);
		double maxRangeSqr = Mth.square(maxRange);
		Vec3 eyePos = entity.getEyePosition();
		HitResult pickResult = entity.pick(maxRange, 1, false);
		double dist = pickResult.getLocation().distanceToSqr(eyePos);
		if (pickResult.getType() != HitResult.Type.MISS) {
			maxRangeSqr = dist;
			maxRange = Math.sqrt(dist);
		}
		Vec3 viewVector = entity.getViewVector(1);
		Vec3 endPos = eyePos.add(viewVector.x * maxRange, viewVector.y * maxRange, viewVector.z * maxRange);
		AABB aabb = entity.getBoundingBox().expandTowards(viewVector.scale(maxRange)).inflate(1.0, 1.0, 1.0);
		EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
			entity, eyePos, endPos, aabb, e -> !e.isSpectator() && e.isPickable(), maxRangeSqr
		);
		return entityHitResult != null && entityHitResult.getLocation().distanceToSqr(eyePos) < dist
			? filterHitResult(entityHitResult, eyePos, entityInteractionRange)
			: filterHitResult(pickResult, eyePos, blockInteractionRange);
	}

	private static HitResult filterHitResult(HitResult hitResult, Vec3 pos, double blockInteractionRange) {
		Vec3 location = hitResult.getLocation();
		if (!location.closerThan(pos, blockInteractionRange)) {
			Direction direction = Direction.getNearest(location.x - pos.x, location.y - pos.y, location.z - pos.z);
			return BlockHitResult.miss(location, direction, BlockPos.containing(location));
		} else {
			return hitResult;
		}
	}
}
