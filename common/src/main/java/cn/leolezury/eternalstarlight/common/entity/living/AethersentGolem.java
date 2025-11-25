package cn.leolezury.eternalstarlight.common.entity.living;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class AethersentGolem extends AbstractGolem {
	public final AnimationState shootAnimationState = new AnimationState();
	public final AnimationState shootEndAnimationState = new AnimationState();
	public Vec3 leftMuzzlePos = Vec3.ZERO;
	public Vec3 rightMuzzlePos = Vec3.ZERO;
	private Vec3 lookPos = Vec3.ZERO;
	private int peaceTicks = 80;
	public boolean shouldAddShootParticle = false;
	public boolean shootPosTracked = true;
	public boolean useLeftHand = true;

	public AethersentGolem(EntityType<? extends AethersentGolem> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.aethersentGolem.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.aethersentGolem.armor())
			.add(Attributes.KNOCKBACK_RESISTANCE, 1)
			.add(Attributes.MOVEMENT_SPEED, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new GolemLookAtTargetGoal());
	}

	private class GolemLookAtTargetGoal extends Goal {
		public GolemLookAtTargetGoal() {
			setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return true;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			AethersentGolem.this.getLookControl().setLookAt(AethersentGolem.this.lookPos.x, AethersentGolem.this.lookPos.y, AethersentGolem.this.lookPos.z, 360, 360);
		}
	}

	public void stopAllAnimStates() {
		shootAnimationState.stop();
		shootEndAnimationState.stop();
	}

	@Override
	public void handleEntityEvent(byte b) {
		if (b == 100) {
			stopAllAnimStates();
			shootAnimationState.start(tickCount);
			shootPosTracked = false;
			useLeftHand = !useLeftHand;
		} else {
			super.handleEntityEvent(b);
		}
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (!itemStack.is(ESItems.AETHERSENT_INGOT.get())) {
			return InteractionResult.PASS;
		} else {
			float f = this.getHealth();
			this.heal(25.0F);
			if (this.getHealth() == f) {
				return InteractionResult.PASS;
			} else {
				itemStack.consume(1, player);
				if (level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i <= 10; i++) {
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.AETHERSENT, getX() + (random.nextFloat() - 0.5f) * getBbWidth() * 2, getY(), getZ() + (random.nextFloat() - 0.5f) * getBbWidth() * 2, 0, 1, 0));
					}
				}
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (peaceTicks >= 80) {
			lookPos = new Vec3(lookPos.x, getEyePosition().y, lookPos.z);
		}
		peaceTicks++;
		if (!level().isClientSide) {
			if (tickCount % 20 == 0) {
				List<AethersentMeteor> meteors = level().getEntitiesOfClass(AethersentMeteor.class, getBoundingBox().inflate(75)).stream().filter(AethersentMeteor::isNatural).toList();
				List<LivingEntity> mobTargets = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(50)).stream().filter(living -> living.getType().is(ESTags.EntityTypes.AETHERSENT_GOLEM_TARGETS)).toList();
				if (!meteors.isEmpty() || !mobTargets.isEmpty()) {
					level().broadcastEntityEvent(this, (byte) 100);
					meteors.forEach(meteor -> meteor.dropAndDiscard(true));
					mobTargets.forEach(living -> living.hurt(damageSources().magic(), 8));
					if (!meteors.isEmpty()) {
						lookPos = meteors.getFirst().position();
					} else {
						lookPos = mobTargets.getFirst().position();
					}
					playSound(ESSoundEvents.AETHERSENT_GOLEM_SHOOT.get());
					peaceTicks = 0;
				}
			}
		} else {
			if (shootAnimationState.isStarted()) {
				if (shouldAddShootParticle) {
					if (useLeftHand) {
						for (int i = 0; i < 15; i++) {
							Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize().scale(0.15);
							level().addParticle(ParticleTypes.LARGE_SMOKE, leftMuzzlePos.x, leftMuzzlePos.y, leftMuzzlePos.z, speed.x, speed.y, speed.z);
						}
						level().addParticle(ESExplosionParticleOptions.AETHERSENT, leftMuzzlePos.x, leftMuzzlePos.y, leftMuzzlePos.z, 0, 0, 0);
					} else {
						for (int i = 0; i < 15; i++) {
							Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize().scale(0.15);
							level().addParticle(ParticleTypes.LARGE_SMOKE, rightMuzzlePos.x, rightMuzzlePos.y, rightMuzzlePos.z, speed.x, speed.y, speed.z);
						}
						level().addParticle(ESExplosionParticleOptions.AETHERSENT, rightMuzzlePos.x, rightMuzzlePos.y, rightMuzzlePos.z, 0, 0, 0);
					}
					shouldAddShootParticle = false;
				}
				if ((shootAnimationState.getAccumulatedTime() / 1000f) * 20f > 60) {
					shootAnimationState.stop();
					shootEndAnimationState.start(tickCount);
				}
			}
		}
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource source) {
		return ESSoundEvents.AETHERSENT_GOLEM_HURT.get();
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return ESSoundEvents.AETHERSENT_GOLEM_DEATH.get();
	}
}
