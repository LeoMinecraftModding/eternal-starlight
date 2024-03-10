package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.boss.AttackPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.GatekeeperFireball;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GatekeeperCastFireballPhase extends AttackPhase<TheGatekeeper> {
    public static final int ID = 4;

    public GatekeeperCastFireballPhase() {
        super(ID, 1, 20, 400);
    }

    @Override
    public boolean canStart(TheGatekeeper entity, boolean coolDownOver) {
        return coolDownOver && entity.canReachTarget(100) && !entity.canReachTarget(6);
    }

    @Override
    public void onStart(TheGatekeeper entity) {
        LivingEntity target = entity.getTarget();
        if (target != null) {
            for (int i = 0; i < 6; i++) {
                Vec3 pos = ESMathUtil.rotationToPosition(entity.position(), 3, 0, (360f / 6f) * i);
                GatekeeperFireball fireball = new GatekeeperFireball(entity.level(), entity, 0, 1, 0);
                fireball.setTarget(target);
                fireball.setPos(pos);
                entity.level().addFreshEntity(fireball);
            }
        }
    }

    @Override
    public void tick(TheGatekeeper entity) {
        
    }

    @Override
    public boolean canContinue(TheGatekeeper entity) {
        return true;
    }

    @Override
    public void onStop(TheGatekeeper entity) {

    }
}
