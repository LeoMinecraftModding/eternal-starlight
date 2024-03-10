package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemFlameAttack;
import cn.leolezury.eternalstarlight.common.entity.living.boss.AttackPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;

public class StarlightGolemSpitFlamePhase extends AttackPhase<StarlightGolem> {
    public static final int ID = 2;

    public StarlightGolemSpitFlamePhase() {
        super(ID, 2, 200, 280);
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
        if (entity.getAttackTicks() == 60) {
            GolemFlameAttack flameAttack = new GolemFlameAttack(ESEntities.GOLEM_FLAME_ATTACK.get(), entity.level(), entity, entity.getX(), entity.getEyePosition().y, entity.getZ(), entity.yHeadRot + 90, -entity.getXRot());
            entity.level().addFreshEntity(flameAttack);
        }
        if (entity.getAttackTicks() >= 60 && entity.getAttackTicks() % 40 == 0) {
            CameraShake.createCameraShake(entity.level(), entity.position(), 45, 0.02f, 40, 20);
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
