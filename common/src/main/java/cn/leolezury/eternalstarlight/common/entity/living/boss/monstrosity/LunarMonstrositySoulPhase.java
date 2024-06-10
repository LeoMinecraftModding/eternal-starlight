package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.AttackPhase;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;

public class LunarMonstrositySoulPhase extends AttackPhase<LunarMonstrosity> {
    public static final int ID = 8;

    public LunarMonstrositySoulPhase() {
        super(ID, 1, 100, 0);
    }

    @Override
    public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
        return false;
    }

    @Override
    public void onStart(LunarMonstrosity entity) {
        entity.playSound(ESSoundEvents.LUNAR_MONSTROSITY_ROAR.get(), 0.5f, 1);
    }

    @Override
    public void tick(LunarMonstrosity entity) {

    }

    @Override
    public boolean canContinue(LunarMonstrosity entity) {
        return true;
    }

    @Override
    public void onStop(LunarMonstrosity entity) {

    }
}
