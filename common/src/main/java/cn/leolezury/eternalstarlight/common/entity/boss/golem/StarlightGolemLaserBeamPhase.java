package cn.leolezury.eternalstarlight.common.entity.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.attack.beam.GolemLaserBeam;
import cn.leolezury.eternalstarlight.common.entity.boss.AttackPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;

public class StarlightGolemLaserBeamPhase extends AttackPhase<StarlightGolem> {
    public static final int ID = 1;

    public StarlightGolemLaserBeamPhase() {
        super(ID, 2, 200, 500);
    }

    @Override
    public boolean canStart(StarlightGolem entity, boolean coolDownOver) {
        return true;
    }

    @Override
    public void onStart(StarlightGolem entity) {

    }

    @Override
    public void tick(StarlightGolem entity) {
        if (entity.getAttackTicks() == 60) {
            entity.playSound(ESSoundEvents.STARLIGHT_GOLEM_PREPARE_BEAM.get());
            GolemLaserBeam beam = new GolemLaserBeam(ESEntities.GOLEM_LASER_BEAM.get(), entity.level(), entity, entity.getX(), entity.getY() + entity.getBbHeight() / 2.5f, entity.getZ(), entity.yHeadRot + 90, -entity.getXRot());
            entity.level().addFreshEntity(beam);
        }
        if (entity.getAttackTicks() >= 60 && entity.getAttackTicks() % 40 == 0) {
            CameraShake.createCameraShake(entity.level(), entity.position(), 45, 0.02f, 40, 20);
            entity.spawnEnergizedFlame(3, 15, false);
        }
    }

    @Override
    public boolean canContinue(StarlightGolem entity) {
        return true;
    }

    @Override
    public void onStop(StarlightGolem entity) {

    }
}
