package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;
import java.util.Locale;

public class CrystalGreatswordItem extends GreatswordItem {
	private static final List<Vector3f> PARTICLE_COLORS = List.of(
		new Vector3f(151, 150, 232),
		new Vector3f(196, 80, 132),
		new Vector3f(65, 49, 110),
		new Vector3f(160, 65, 135),
		new Vector3f(126, 197, 203),
		new Vector3f(69, 146, 177),
		new Vector3f(59, 61, 129),
		new Vector3f(75, 128, 166)
	);

	private static final float[] NUMB = new float[]{
		soundPitch(7), soundPitch(10), soundPitch(7),
		soundPitch(12), soundPitch(15), soundPitch(14),
		soundPitch(7), soundPitch(10), soundPitch(7),
		soundPitch(15), soundPitch(14), soundPitch(10)
	};

	private static final float[] ALICE_MAESTRA = new float[]{
		soundPitch(16), soundPitch(19), soundPitch(18),
		soundPitch(19), soundPitch(18), soundPitch(16),
		soundPitch(14), soundPitch(11), soundPitch(14),
		soundPitch(9), soundPitch(11), soundPitch(4),
		soundPitch(16), soundPitch(23), soundPitch(21),
		soundPitch(19), soundPitch(18), soundPitch(14),
		soundPitch(11), soundPitch(14), soundPitch(9),
		soundPitch(11), soundPitch(4)
	};

	public CrystalGreatswordItem(Tier tier, Properties properties) {
		super(tier, properties);
	}

	public static float soundPitch(int i) {
		return (float) Math.pow(2.0, (i - 12) / 12.0);
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postHurtEnemy(stack, target, attacker);
		int existingLevel = 0;
		MobEffectInstance instance = target.getEffect(ESMobEffects.CRYSTAL_INFECTION.asHolder());
		if (instance != null) {
			existingLevel = instance.getAmplifier();
		}
		float soundPitch = -1;
		if (existingLevel >= 4) {
			target.removeEffect(ESMobEffects.CRYSTAL_INFECTION.asHolder());
			if (target.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 3; i++) {
					double x = target.getX() + (target.getRandom().nextFloat() - 0.5) * target.getBbWidth();
					double y = target.getY() + target.getRandom().nextFloat() * target.getBbHeight();
					double z = target.getZ() + (target.getRandom().nextFloat() - 0.5) * target.getBbWidth();
					serverLevel.sendParticles(ESExplosionParticleOptions.fromIntColor(ESParticles.BLAST.get(), PARTICLE_COLORS.get(target.getRandom().nextInt(PARTICLE_COLORS.size())), PARTICLE_COLORS.get(target.getRandom().nextInt(PARTICLE_COLORS.size())), 0.5f), x, y, z, 1, 0.2, 0.2, 0.2, 0.0);
					for (int j = 0; j < 4; j++) {
						Vec3 speed = new Vec3((target.getRandom().nextFloat() - target.getRandom().nextFloat()) * 0.1F, target.getRandom().nextFloat() * 0.05F, (target.getRandom().nextFloat() - target.getRandom().nextFloat()) * 0.1F).normalize();
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.fromIntColor(PARTICLE_COLORS.get(target.getRandom().nextInt(PARTICLE_COLORS.size())), PARTICLE_COLORS.get(target.getRandom().nextInt(PARTICLE_COLORS.size())), 0.3f, 0.06f, 0.5f), x + speed.x * 0.6, y + speed.y * 0.6, z + speed.z * 0.6, speed.x, speed.y, speed.z));
					}
				}
			}
			soundPitch = 1.3f;
		} else {
			int newLevel = Math.min(existingLevel + 1, 4);
			if (target.addEffect(new MobEffectInstance(ESMobEffects.CRYSTAL_INFECTION.asHolder(), 60, newLevel))) {
				soundPitch = Mth.lerp(newLevel / 4f, 0.05f, 1.2f);
			}
		}
		String itemName = stack.getOrDefault(DataComponents.CUSTOM_NAME, Component.empty()).getString().toLowerCase(Locale.ROOT);
		if (itemName.contains("linkin") || itemName.contains("numb")) {
			int index = ESDataAttachments.CRYSTAL_GREATSWORD_MUSIC_INDEX.getData(attacker);
			soundPitch = NUMB[index % NUMB.length];
			ESDataAttachments.CRYSTAL_GREATSWORD_MUSIC_INDEX.setData(attacker, (index + 1) % NUMB.length);
		}
		if (itemName.contains("alice") && itemName.contains("maestra")) {
			int index = ESDataAttachments.CRYSTAL_GREATSWORD_MUSIC_INDEX.getData(attacker);
			soundPitch = ALICE_MAESTRA[index % ALICE_MAESTRA.length];
			ESDataAttachments.CRYSTAL_GREATSWORD_MUSIC_INDEX.setData(attacker, (index + 1) % ALICE_MAESTRA.length);
		}
		if (soundPitch >= 0 && !attacker.isSilent()) {
			attacker.level().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), ESSoundEvents.CRYSTAL_GREATSWORD_CHIME.get(), attacker.getSoundSource(), 1, soundPitch);
		}
	}

	@Override
	public float getAttackDamageBonus(Entity target, float damage, DamageSource source) {
		if (target instanceof LivingEntity living) {
			MobEffectInstance instance = living.getEffect(ESMobEffects.CRYSTAL_INFECTION.asHolder());
			if (instance != null) {
				return instance.getAmplifier() * (instance.getAmplifier() >= 4 ? 0.25f : 0.15f) * damage;
			}
		}
		return 0;
	}
}
