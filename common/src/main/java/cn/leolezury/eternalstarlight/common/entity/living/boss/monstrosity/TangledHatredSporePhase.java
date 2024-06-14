package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviourPhase;
import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;

public class TangledHatredSporePhase extends BehaviourPhase<TangledHatred> {
    public static final int ID = 2;

    public TangledHatredSporePhase() {
        super(ID, 1, 100, 400);
    }

    @Override
    public boolean canStart(TangledHatred entity, boolean cooldownOver) {
        return cooldownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(TangledHatred entity) {

    }

    @Override
    public void tick(TangledHatred entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            int segment = entity.getBehaviourTicks() / 40;
            if (entity.getBehaviourTicks() == segment * 40 + 15 && entity.chain.segments().size() > segment) {
                Vec3 base = entity.chain.segments().get(segment).getMiddlePosition();
                entity.playSound(SoundEvents.GENERIC_EXPLODE.value());
                for (int j = 0; j < 10; j++) {
                    Vec3 vec3 = base.offsetRandom(entity.getRandom(), 5);
                    for (int m = 0; m < serverLevel.players().size(); ++m) {
                        ServerPlayer serverPlayer = serverLevel.players().get(m);
                        serverLevel.sendParticles(serverPlayer, ESExplosionParticleOptions.LUNAR, true, vec3.x, vec3.y, vec3.z, 3, 0, 0, 0, 0);
                    }
                }
                for (int j = 0; j < 10; j++) {
                    LunarSpore spore = new LunarSpore(entity.level(), entity, base.x, base.y, base.z);
                    Vec3 shoot = new Vec3((entity.getRandom().nextFloat() - 0.5), (entity.getRandom().nextFloat() + 0.5) * 2, (entity.getRandom().nextFloat() - 0.5));
                    spore.setDeltaMovement(shoot.normalize().scale(1.2));
                    serverLevel.addFreshEntity(spore);
                }
            }
        }
    }

    @Override
    public boolean canContinue(TangledHatred entity) {
        return true;
    }

    @Override
    public void onStop(TangledHatred entity) {

    }
}
