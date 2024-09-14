package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolemLaserBeamPhase;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GolemLaserBeam extends RayAttack {
	public GolemLaserBeam(EntityType<? extends RayAttack> type, Level world) {
		super(type, world);
	}

	public GolemLaserBeam(EntityType<? extends RayAttack> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch) {
		super(type, world, caster, x, y, z, yaw, pitch);
	}

	@Override
	public float getAttackDamage() {
		return (getCaster().isPresent() && getCaster().get() instanceof StarlightGolem) ? 10f * (float) ESConfig.INSTANCE.mobsConfig.starlightGolem.attackDamageScale() : 3f + (getCaster().isPresent() && getCaster().get() instanceof SpellCaster caster ? caster.getESSpellData().strength() * 0.5f : 0);
	}

	@Override
	public void updatePosition() {
		if (tickCount % 15 == 0) {
			playSound(ESSoundEvents.LASER_BEAM_HUM.get());
		}
		getCaster().ifPresentOrElse(caster -> {
			setPos(getPositionForCaster(caster, caster.position()));
			if (caster instanceof StarlightGolem golem) {
				if (golem.getBehaviorState() != StarlightGolemLaserBeamPhase.ID || !golem.isAlive()) {
					discard();
				}
			} else {
				if (caster instanceof SpellCaster spellCaster && (!spellCaster.getESSpellData().hasSpell() || spellCaster.getESSpellData().spell() != ESSpells.LASER_BEAM.get())) {
					discard();
				}
			}
		}, this::discard);
	}

	@Override
	public Vec3 getPositionForCaster(Entity caster, Vec3 casterPos) {
		return caster instanceof StarlightGolem ? casterPos.add(0, caster.getBbHeight() / 2.5f, 0) : casterPos.add(0, caster.getEyeHeight(caster.getPose()), 0);
	}
}
