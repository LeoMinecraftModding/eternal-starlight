package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;

public class StarlightGolemSummonFlamePhase extends BehaviourPhase<StarlightGolem> {
    public static final int ID = 2;

    public StarlightGolemSummonFlamePhase() {
        super(ID, 2, 210, 250);
    }

    @Override
    public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
        return cooldownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(StarlightGolem entity) {

    }

    @Override
    public void tick(StarlightGolem entity) {
        if (entity.getBehaviourTicks() % 30 == 0) {
            CameraShake.createCameraShake(entity.level(), entity.position(), 45, 0.02f, 40, 20);
            entity.spawnEnergizedFlame(2, 15, true);
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
