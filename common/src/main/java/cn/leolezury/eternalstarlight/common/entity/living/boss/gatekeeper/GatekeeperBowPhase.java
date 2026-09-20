package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ParticleFacing;
import cn.leolezury.eternalstarlight.common.particle.RippleParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class GatekeeperBowPhase extends BehaviorPhase<TheGatekeeper> {
	public static final int ID = 10;

	public GatekeeperBowPhase() {
		super(ID, 1, 47, 150);
	}

	@Override
	public boolean canStart(TheGatekeeper entity, boolean cooldownOver) {
		return cooldownOver && !canReachTarget(entity, 6) && canReachTarget(entity, 25);
	}

	@Override
	public void onStart(TheGatekeeper entity) {
		entity.setItemInHand(InteractionHand.MAIN_HAND, ESItems.GLISTERING_BOW.get().getDefaultInstance());
		entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
	}

	@Override
	public void tick(TheGatekeeper entity) {
		LivingEntity target = entity.getTarget();
		int ticks = entity.getBehaviorTicks();
		if (target != null && entity.level() instanceof ServerLevel serverLevel) {
			ESEntityUtil.instantLook(entity, target.getEyePosition());
			if (ticks == 26) {
				ItemStack weapon = entity.getMainHandItem();
				if (weapon.getItem() instanceof BowItem) {
					ItemStack projectile = entity.getProjectile(weapon);
					AbstractArrow arrow = ProjectileUtil.getMobArrow(entity, projectile, 1, weapon);
					arrow.setBaseDamage(arrow.getBaseDamage() + 0.75);
					double x = target.getX() - entity.getX();
					double y = target.getY(1.0 / 3.0) - arrow.getY();
					double z = target.getZ() - entity.getZ();
					double xzDist = Math.sqrt(x * x + z * z);
					arrow.shoot(x, y + xzDist * 0.2, z, 1.8F, 0.5F);
					entity.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
					entity.level().addFreshEntity(arrow);
					RippleParticleOptions ripple = new RippleParticleOptions(ESParticles.RIPPLE.get(),
						ParticleFacing.fromNormal(arrow.getDeltaMovement()),
						SmoothSegmentedValue.of(Easing.OUT_QUAD, 0, 0.6f, 1),
						SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, 0.25f, 0, 1),
						RippleParticleOptions.WHITE, 10);
					Vec3 pos = new Vec3(arrow.getX(), entity.getY() + entity.getBbHeight() * 0.65, arrow.getZ()).add(0, arrow.getBbHeight() / 2, 0).add(arrow.getDeltaMovement().normalize().scale(0.5));
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ripple, pos.x, pos.y, pos.z, 0, 0, 0));
				}
				entity.hurtMarked = true;
				entity.addDeltaMovement(entity.position().subtract(target.position()).normalize().scale(0.5));
			}
		}
	}

	@Override
	public boolean canContinue(TheGatekeeper entity) {
		return true;
	}
}
