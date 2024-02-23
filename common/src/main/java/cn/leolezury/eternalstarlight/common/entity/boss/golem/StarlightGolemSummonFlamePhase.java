package cn.leolezury.eternalstarlight.common.entity.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.boss.AttackPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;

public class StarlightGolemSummonFlamePhase extends AttackPhase<StarlightGolem> {
    public static final int ID = 3;

    public StarlightGolemSummonFlamePhase() {
        super(ID, 2, 200, 250);
    }

    @Override
    public boolean canStart(StarlightGolem entity, boolean coolDownOver) {
        return coolDownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(StarlightGolem entity) {

    }

    @Override
    public void tick(StarlightGolem entity) {
        if (entity.getAttackTicks() % 30 == 0) {
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
