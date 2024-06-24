package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolemLaserBeamPhase;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class GolemLaserBeam extends RayAttack {
	public GolemLaserBeam(EntityType<? extends RayAttack> type, Level world) {
		super(type, world);
	}

	public GolemLaserBeam(EntityType<? extends RayAttack> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch) {
		super(type, world, caster, x, y, z, yaw, pitch);
	}

	@Override
	public float getAttackDamage() {
		return (getCaster().isPresent() && getCaster().get() instanceof StarlightGolem) ? 10f : 3f;
	}

	@Override
	public void onHit(ESEntityUtil.RaytraceResult result) {
		if (getCaster().isPresent() && getCaster().get() instanceof StarlightGolem) {
			super.onHit(result);
		} else {
			for (Entity target : result.entities()) {
				if (target instanceof LivingEntity living) {
					doHurtTarget(living);
				}
			}
		}
	}

	@Override
	public void updatePosition() {
		getCaster().ifPresentOrElse(caster -> {
			if (caster instanceof StarlightGolem golem) {
				setPos(caster.position().add(0, caster.getBbHeight() / 2.5f, 0));
				if (golem.getBehaviourState() != StarlightGolemLaserBeamPhase.ID || golem.isDeadOrDying()) {
					discard();
				}
			} else {
				setPos(caster.getEyePosition());
				if (caster instanceof SpellCaster spellCaster && (!spellCaster.getSpellData().hasSpell() || spellCaster.getSpellData().spell() != ESSpells.LASER_BEAM.get())) {
					discard();
				}
			}
		}, this::discard);
	}
}
