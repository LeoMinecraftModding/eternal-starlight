package cn.leolezury.eternalstarlight.common.entity.living.boss;

public interface MultiPhaseAttacker {
    void setAttackState(int state);
    int getAttackState();
    void setAttackTicks(int ticks);
    int getAttackTicks();
}
