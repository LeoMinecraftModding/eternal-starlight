package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LunarMonstrositySporePhase extends BehaviourPhase<LunarMonstrosity> {
    public static final int ID = 2;

    public LunarMonstrositySporePhase() {
        super(ID, 1, 100, 200);
    }

    @Override
    public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
        return cooldownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(LunarMonstrosity entity) {

    }

    @Override
    public void tick(LunarMonstrosity entity) {
        LivingEntity target = entity.getTarget();
        if ((entity.getBehaviourTicks() + 5) % 10 == 0 && target != null) {
            Vec3 shootPos = ESMathUtil.rotationToPosition(entity.position().add(0, entity.getBbHeight(), 0), entity.getBbWidth() * 1.2f, 0, entity.yBodyRot + 90);
            LunarSpore spore = new LunarSpore(entity.level(), entity, shootPos.x, shootPos.y, shootPos.z);
            spore.setNoGravity(true);
            spore.setDeltaMovement(entity.getRayRotationTarget().subtract(shootPos).normalize().scale(0.9));
            entity.level().addFreshEntity(spore);
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
