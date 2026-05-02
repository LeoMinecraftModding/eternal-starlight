package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.attack.Candlash;
import cn.leolezury.eternalstarlight.common.entity.attack.Whip;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
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

public class CandlashItem extends WhipItem {
	private static final List<Vector3f> PARTICLE_COLORS = List.of(
		new Vector3f(255, 255, 197),
		new Vector3f(220, 253, 154),
		new Vector3f(255, 201, 137),
		new Vector3f(255, 158, 108),
		new Vector3f(249, 127, 161),
		new Vector3f(194, 99, 126)
	);

	public CandlashItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	@Override
	public void doPostHurtEffects(@Nullable Whip whip, Entity entity) {
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
		if (entity.getRandom().nextFloat() < 0.75) {
			if (entity instanceof LivingEntity living) {
				living.addEffect(new MobEffectInstance(ESMobEffects.FLAMMABLE.asHolder(), 200, 0));
			}
			entity.igniteForSeconds(3);
		}
	}

	@Override
	public Whip createWhip(Level level, Player owner, ItemStack weapon, float damageScale) {
		return new Candlash(level, owner, weapon, damageScale);
	}
}
