package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class MagicStone extends ThrowableItemProjectile {
	public MagicStone(EntityType<? extends MagicStone> entityType, Level level) {
		super(entityType, level);
	}

	public MagicStone(Level level, LivingEntity livingEntity) {
		super(ESEntities.MAGIC_STONE.get(), livingEntity, level);
	}

	public MagicStone(Level level, double x, double y, double z) {
		super(ESEntities.MAGIC_STONE.get(), x, y, z, level);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide) {
			for (int i = 0; i < 2; i++) {
				this.level().addParticle(
					ESParticles.ECTOSTONE_GLINT.get(),
					this.getX(), this.getY() + 0.125, this.getZ(),
					0, 0, 0
				);
			}
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS && level() instanceof ServerLevel serverLevel) {
			this.playSound(SoundEvents.STONE_BREAK, 1.2f, 1.2f);
			for (int i = 0; i < 10; i++) {
				serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, getItem()), this.getX(), this.getY() + 0.125, this.getZ(), 1, (random.nextDouble() - 0.5) * 0.3, random.nextDouble() * 0.3, (random.nextDouble() - 0.5) * 0.3, 0.0);
			}
			discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS && getOwner() instanceof LivingEntity owner && ESEntityUtil.shouldHarm(owner, hitResult.getEntity())) {
			hitResult.getEntity().hurt(damageSources().thrown(this, owner), (float) switch (getOwner()) {
				case Player ignored -> 4;
				case LivingEntity living -> living.getAttribute(Attributes.ATTACK_DAMAGE) != null ? living.getAttributeValue(Attributes.ATTACK_DAMAGE) : 5;
				default -> 5;
			});
		}
	}

	@Override
	protected @NotNull Item getDefaultItem() {
		return ESItems.MAGIC_STONE.get();
	}
}
