package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.ArrayList;
import java.util.List;

public class ESEntityUtil {
	public static RaytraceResult raytrace(LevelAccessor level, CollisionContext context, Vec3 from, Vec3 to) {
		BlockHitResult hitResult = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, context));
		RaytraceResult result = new RaytraceResult(new ArrayList<>(), hitResult.getType() == HitResult.Type.BLOCK ? hitResult : null);
		List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(from, to).inflate(1));
		for (Entity entity : entities) {
			AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius() + 0.5f);
			if (aabb.contains(from)) {
				result.entities().add(entity);
				continue;
			}
			if (aabb.clip(from, to).isPresent()) {
				result.entities().add(entity);
			}
		}
		result.entities().sort((o1, o2) -> (int) Math.signum(o1.position().distanceTo(from) - o2.position().distanceTo(from)));
		return result;
	}

	public record RaytraceResult(List<Entity> entities, BlockHitResult blockHitResult) {
	}

	public static boolean shouldHarm(Entity attacker, Entity victim) {
		if (attacker == null || victim == null) {
			return true;
		}
		if (attacker == victim) {
			return false;
		}
		if (attacker.isAlliedTo(victim) || victim.isAlliedTo(attacker)) {
			return false;
		}
		if (attacker instanceof Player p1 && victim instanceof Player p2 && !p1.canHarmPlayer(p2)) {
			return false;
		}
		return victim.isAttackable();
	}

	public static void instantLook(LivingEntity entity, Vec3 pos) {
		if (entity instanceof Mob mob) {
			mob.getLookControl().setLookAt(pos.x, pos.y, pos.z, 360, 360);
		}
		entity.lookAt(EntityAnchorArgument.Anchor.EYES, pos);
		entity.yBodyRot = entity.getYRot();
		entity.yBodyRotO = entity.getYRot();
		entity.yHeadRot = entity.getYRot();
		entity.yHeadRotO = entity.getYRot();
	}

	public static VillagerTrades.ItemListing simpleTrade(Item cost, int costCount, Item result, int resultCount, int maxUses) {
		return simpleTrade(new ItemStack(cost, costCount), new ItemStack(result, resultCount), maxUses);
	}

	public static VillagerTrades.ItemListing simpleTrade(ItemStack cost, ItemStack result, int maxUses) {
		return (entity, random) -> new MerchantOffer(new ItemCost(cost.getItem(), cost.getCount()), result, maxUses, 0, 0);
	}

	public static void givePlayerItem(Player player, ItemStack stack) {
		int maxStackSize = stack.getMaxStackSize();
		int remaining = stack.getCount();

		while (remaining > 0) {
			int size = Math.min(maxStackSize, remaining);
			remaining -= size;
			ItemStack copyToDrop = stack.copyWithCount(size);
			boolean added = player.getInventory().add(copyToDrop);
			if (added && copyToDrop.isEmpty()) {
				player.level()
					.playSound(
						null,
						player.getX(),
						player.getY(),
						player.getZ(),
						SoundEvents.ITEM_PICKUP,
						SoundSource.PLAYERS,
						0.2F,
						((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
					);
				player.containerMenu.broadcastChanges();
			} else {
				ItemEntity drop = player.drop(copyToDrop, false);
				if (drop != null) {
					drop.setNoPickUpDelay();
					drop.setTarget(player.getUUID());
				}
			}
		}
	}
}
