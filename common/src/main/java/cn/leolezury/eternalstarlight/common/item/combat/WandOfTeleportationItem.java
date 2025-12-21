package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.attack.TangledHusk;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class WandOfTeleportationItem extends Item {
	public WandOfTeleportationItem(Properties properties) {
		super(properties);
	}

	private void createThornCircle(Level level, Player player, Vec3 target) {
		for (int i = 0; i < 7; i++) {
			float radius = (i + 1) * player.getBbWidth() * 0.75f;
			int num = 5;
			float startAngle = i * 0.3f * Mth.PI;
			for (int j = 0; j < num; j++) {
				float angle = startAngle + (Mth.TWO_PI / num) * j;
				MoonringBowItem.createThorn(level, player, target.x() + Math.cos(angle) * radius, target.y(), target.z() + Math.sin(angle) * radius, Mth.wrapDegrees(-angle * Mth.RAD_TO_DEG), 40, i * 3);
			}
		}
	}

	private void teleportPlayer(Level level, Player player, ItemStack stack, Vec3 target) {
		createThornCircle(level, player, player.position());
		TangledHusk husk = ESEntities.TANGLED_HUSK.get().create(level);
		if (husk != null) {
			husk.moveTo(player.position());
			husk.setYRot(player.getYRot());
			husk.yRotO = husk.getYRot();
			husk.setOwner(player);
			level.addFreshEntity(husk);
		}
		if (ESPlatform.INSTANCE.postTeleportEvent(player, target)) {
			player.teleportTo(target.x, target.y, target.z);
			createThornCircle(level, player, target);
		}
		stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
		player.getCooldowns().addCooldown(this, 600);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Vec3 startPos = player.getEyePosition();
		float lookYaw = player.getYHeadRot() + 90.0f;
		float lookPitch = -player.getXRot();
		Vec3 endPos = ESMathUtil.rotationToPosition(startPos, 64, lookPitch, lookYaw);
		ESEntityUtil.RaytraceResult result = ESEntityUtil.raytrace(player.level(), CollisionContext.of(player), startPos, endPos);
		if (!result.entities().isEmpty()) {
			for (int i = 0; i < result.entities().size(); i++) {
				if (result.entities().get(i) != player) {
					Vec3 target = result.entities().get(i).position();
					teleportPlayer(level, player, stack, target);
					player.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
				}
			}
		}
		if (result.blockHitResult() != null) {
			Vec3 target = result.blockHitResult().getLocation();
			Vec3 diff = player.position().subtract(target);
			target = target.add(diff.normalize().scale(Math.min(diff.length(), 2)));
			teleportPlayer(level, player, stack, target);
			player.awardStat(Stats.ITEM_USED.get(this));
			return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
		}
		return super.use(level, player, hand);
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
		return repairCandidate.is(ESItems.SOUL_DEW.get()) || super.isValidRepairItem(stack, repairCandidate);
	}
}
