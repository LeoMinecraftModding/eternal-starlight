package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThioquartzArrow extends AbstractArrow {
	public ThioquartzArrow(EntityType<? extends ThioquartzArrow> entityType, Level level) {
		super(entityType, level);
	}

	public ThioquartzArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.THIOQUARTZ_ARROW.get(), livingEntity, level, itemStack, itemStack2);
	}

	public ThioquartzArrow(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.THIOQUARTZ_ARROW.get(), d, e, f, level, itemStack, itemStack2);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity livingEntity) {
		super.doPostHurtEffects(livingEntity);
		playSound(SoundEvents.GLASS_BREAK);
		int inEtherTicks = ESDataAttachments.IN_ETHER_TICKS.getData(livingEntity);
		if (inEtherTicks < 400) {
			ESDataAttachments.IN_ETHER_TICKS.setData(livingEntity, Math.min(inEtherTicks + 200, 400));
		}
		List<LivingEntity> affected = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(8));
		affected.removeIf(e -> !ESEntityUtil.shouldHarm(getOwner(), e));
		for (int i = 0; i < 5; i++) {
			ThioquartzShard shard = getOwner() instanceof LivingEntity living ? new ThioquartzShard(level(), living) : new ThioquartzShard(ESEntities.THIOQUARTZ_SHARD.get(), level());
			shard.setPos(position());
			Vec3 movement = new Vec3(getRandom().nextFloat() - 0.5, getRandom().nextFloat() - 0.5, getRandom().nextFloat() - 0.5);
			if (affected.size() > i) {
				LivingEntity target = affected.get(i);
				movement = target.position().add(0, target.getBbHeight() / 2, 0).subtract(position());
			}
			shard.shoot(movement.x, movement.y, movement.z, 0.8f, 0.2f);
			level().addFreshEntity(shard);
		}
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ESItems.THIOQUARTZ_ARROW.get().getDefaultInstance();
	}
}
