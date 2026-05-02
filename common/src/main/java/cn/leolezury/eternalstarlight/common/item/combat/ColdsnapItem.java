package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.attack.Coldsnap;
import cn.leolezury.eternalstarlight.common.entity.attack.PermafrostCloud;
import cn.leolezury.eternalstarlight.common.entity.attack.Whip;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class ColdsnapItem extends WhipItem {
	private static final List<Vector3f> PARTICLE_COLORS = List.of(
		new Vector3f(255, 255, 255),
		new Vector3f(155, 244, 244),
		new Vector3f(104, 204, 255),
		new Vector3f(74, 167, 228),
		new Vector3f(69, 114, 185)
	);

	public ColdsnapItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public void doPostHurtEffects(@Nullable Whip whip, Entity entity) {
		if (whip != null) {
			whip.playSound(ESSoundEvents.FROZEN_TUBE_BREAK.get(), 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
		}
		if (entity.level() instanceof ServerLevel serverLevel) {
			double x = entity.getX() + (entity.getRandom().nextFloat() - 0.5) * entity.getBbWidth();
			double y = entity.getY() + entity.getRandom().nextFloat() * entity.getBbHeight();
			double z = entity.getZ() + (entity.getRandom().nextFloat() - 0.5) * entity.getBbWidth();
			serverLevel.sendParticles(ESExplosionParticleOptions.fromIntColor(ESParticles.BLAST.get(), PARTICLE_COLORS.get(entity.getRandom().nextInt(PARTICLE_COLORS.size())), PARTICLE_COLORS.get(entity.getRandom().nextInt(PARTICLE_COLORS.size())), 0.4f), x, y, z, 1, 0.2, 0.2, 0.2, 0.0);
			for (int i = 0; i < 4; i++) {
				Vec3 speed = new Vec3((entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F, entity.getRandom().nextFloat() * 0.05F, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.1F).normalize();
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.fromIntColor(PARTICLE_COLORS.get(entity.getRandom().nextInt(PARTICLE_COLORS.size())), PARTICLE_COLORS.get(entity.getRandom().nextInt(PARTICLE_COLORS.size())), 0.3f, 0.06f, 0.5f), x + speed.x * 0.6, y + speed.y * 0.6, z + speed.z * 0.6, speed.x, speed.y, speed.z));
			}
		}
		if (entity.canFreeze() && entity instanceof LivingEntity living) {
			entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 40, 300));
			MobEffectInstance brittle = living.getEffect(ESMobEffects.BRITTLE.asHolder());
			if (living.hasEffect(ESMobEffects.BRITTLE.asHolder()) && brittle != null) {
				if (entity.getRandom().nextFloat() < 0.5 && brittle.getAmplifier() < 2) {
					living.addEffect(new MobEffectInstance(ESMobEffects.BRITTLE.asHolder(), 200, brittle.getAmplifier() + 1));
				}
			} else if (entity.getRandom().nextFloat() < 0.75) {
				living.addEffect(new MobEffectInstance(ESMobEffects.BRITTLE.asHolder(), 200, 0));
			}
		}
		if (whip instanceof Coldsnap coldsnap && !coldsnap.isCloudSpawned() && coldsnap.getOwner() instanceof LivingEntity living && !SpecialItemCooldown.isOnCooldown(living, this)) {
			PermafrostCloud cloud = new PermafrostCloud(ESEntities.PERMAFROST_CLOUD.get(), entity.level());
			cloud.setOwner(living);
			cloud.setPos(entity.position());
			entity.level().addFreshEntity(cloud);
			coldsnap.setCloudSpawned();
			SpecialItemCooldown.setCooldown(living, this, 160);
		}
	}

	@Override
	public Whip createWhip(Level level, Player owner, ItemStack weapon, float damageScale) {
		return new Coldsnap(level, owner, weapon, damageScale);
	}
}
