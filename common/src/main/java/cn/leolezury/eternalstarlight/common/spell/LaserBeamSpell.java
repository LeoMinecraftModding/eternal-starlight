package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemLaserBeam;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.world.entity.LivingEntity;

public class LaserBeamSpell extends AbstractSpell {
    public LaserBeamSpell(Properties properties) {
        super(properties);
    }

    @Override
    public boolean checkExtraConditions(LivingEntity entity) {
        return true;
    }

    @Override
    public boolean checkExtraConditionsToContinue(LivingEntity entity, int ticks) {
        return true;
    }

    @Override
    public void onPreparationTick(LivingEntity entity, int ticks) {

    }

    @Override
    public void onSpellTick(LivingEntity entity, int ticks) {
        if (!entity.level().isClientSide && ticks == 1) {
            GolemLaserBeam laserBeam = new GolemLaserBeam(ESEntities.GOLEM_LASER_BEAM.get(), entity.level(), entity, entity.getEyePosition().x, entity.getEyePosition().y, entity.getEyePosition().z, entity.getYHeadRot() + 90f, -entity.getXRot());
            entity.level().addFreshEntity(laserBeam);
        }
    }

    @Override
    public void onStart(LivingEntity entity) {

    }

    @Override
    public void onStop(LivingEntity entity, int ticks) {

    }
}
