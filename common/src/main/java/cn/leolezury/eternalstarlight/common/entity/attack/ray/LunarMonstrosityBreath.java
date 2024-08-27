package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosityToxicBreathPhase;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LunarMonstrosityBreath extends RayAttack {
	public LunarMonstrosityBreath(EntityType<? extends RayAttack> type, Level level) {
		super(type, level);
	}

	public LunarMonstrosityBreath(EntityType<? extends RayAttack> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch) {
		super(type, world, caster, x, y, z, yaw, pitch);
	}

	@Override
	public void onHit(ESEntityUtil.RaytraceResult result) {
		for (Entity target : result.entities()) {
			if (target instanceof LivingEntity living) {
				doHurtTarget(living);
			}
		}
	}

	@Override
	public float getAttackDamage() {
		return 3 * (float) ESConfig.INSTANCE.mobsConfig.lunarMonstrosity.attackDamageScale();
	}

	@Override
	public float getRotationSpeed() {
		return 1.4f;
	}

	@Override
	public void doHurtTarget(LivingEntity target) {
		getCaster().ifPresent(caster -> {
			if (!caster.getUUID().equals(target.getUUID())) {
				if (target.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.POISON, this, caster), getAttackDamage())) {
					target.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
				}
			}
		});
	}

	@Override
	public void updatePosition() {
		getCaster().ifPresentOrElse(caster -> {
			if (caster instanceof LunarMonstrosity monstrosity) {
				setPos(caster.position().add(0, caster.getBbHeight(), 0));
				if (monstrosity.getBehaviorState() != LunarMonstrosityToxicBreathPhase.ID || monstrosity.isDeadOrDying()) {
					discard();
				}
			}
		}, this::discard);
	}

	@Override
	public void addEndParticles(Vec3 endPos) {
		Vec3 from = getCaster().isPresent() && getCaster().get() instanceof LunarMonstrosity monstrosity ? monstrosity.headPos : position();
		Vec3 delta = endPos.subtract(from);
		for (int i = 0; i < 10; i++) {
			double dx = delta.x();
			double dy = delta.y();
			double dz = delta.z();

			double spread = 5.0D + random.nextFloat() * 2.5D;
			double velocity = 3.0D + random.nextFloat() * 0.15D;

			dx += random.nextGaussian() * 0.0075D * spread;
			dy += random.nextGaussian() * 0.0075D * spread;
			dz += random.nextGaussian() * 0.0075D * spread;
			dx *= velocity;
			dy *= velocity;
			dz *= velocity;
			endPos.add((random.nextFloat() - 0.5f) * 2.5f, (random.nextFloat() - 0.5f) * 2.5f, (random.nextFloat() - 0.5f) * 2.5f);
			level().addParticle(ESSmokeParticleOptions.LUNAR_BREATH, from.x, from.y, from.z, dx, dy, dz);
		}
	}
}
