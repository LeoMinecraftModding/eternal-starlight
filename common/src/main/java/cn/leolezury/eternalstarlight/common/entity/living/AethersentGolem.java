package cn.leolezury.eternalstarlight.common.entity.living;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AethersentGolem extends Mob {
	public final AnimationState shootAnimationState = new AnimationState();
	public final AnimationState shootEndAnimationState = new AnimationState();

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
		} else {
			super.handleEntityEvent(b);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			if (tickCount % 5 == 0) {
				List<AethersentMeteor> meteors = level().getEntitiesOfClass(AethersentMeteor.class, getBoundingBox().inflate(40)).stream().filter(AethersentMeteor::isNatural).toList();
				if (!meteors.isEmpty()) {
					level().broadcastEntityEvent(this, (byte) 100);
					meteors.forEach(meteor -> meteor.dropAndDiscard(true));
					if (level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 25; i++) {
							Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize();
							ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.AETHERSENT, position().x + speed.x * 1.2, position().y + speed.y * 1.2, position().z + speed.z * 1.2, speed.x, speed.y, speed.z));
						}
					}
				}
			}
		} else {
			if (shootAnimationState.isStarted() && (shootAnimationState.getAccumulatedTime() / 1000f) * 20f > 40) {
				shootAnimationState.stop();
				shootEndAnimationState.start(tickCount);
			}
		}
	}
}
