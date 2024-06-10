package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.AttackPhase;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;

public class LunarMonstrosityBitePhase extends AttackPhase<LunarMonstrosity> {
    public static final int ID = 4;

    public LunarMonstrosityBitePhase() {
        super(ID, 1, 20, 100);
    }

    @Override
    public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
        return cooldownOver && entity.getTarget() != null && entity.canBite() && entity.getPhase() == 1;
    }

    @Override
    public void onStart(LunarMonstrosity entity) {

    }

    @Override
    public void tick(LunarMonstrosity entity) {
        if (entity.getAttackTicks() == 0) {
            entity.playSound(ESSoundEvents.LUNAR_MONSTROSITY_BITE.get());
        }
        if (entity.getAttackTicks() == 13) {
            entity.doBiteDamage(20);
        }
    }

    @Override
    public boolean canContinue(LunarMonstrosity entity) {
        return true;
    }

    @Override
    public void onStop(LunarMonstrosity entity) {

    }
}
