package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GolemFlameAttack extends RayAttack {
    public GolemFlameAttack(EntityType<? extends RayAttack> type, Level world) {
        super(type, world);
    }

    public GolemFlameAttack(EntityType<? extends RayAttack> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch) {
        super(type, world, caster, x, y, z, yaw, pitch);
    }

    @Override
    public void updatePosition() {
        if (tickCount > 120) {
            discard();
        }
        getCaster().ifPresentOrElse(caster -> {
            setPos(caster.getEyePosition());
        }, this::discard);
    }

    @Override
    public void onHit(ESEntityUtil.RaytraceResult result) {
        for (Entity target : result.entities()) {
            if (target instanceof LivingEntity living) {
                getCaster().ifPresent(caster -> {
                    if (living.hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), DamageTypes.MOB_ATTACK, this, caster), 5)) {
                        living.setSecondsOnFire(3);
                    }
                });
            }
        }
    }

    @Override
    public void addEndParticles(Vec3 endPos) {
        Vec3 delta = endPos.subtract(position());
        for (int i = 0; i < 10; i++) {
            double dx = delta.x();
            double dy = delta.y();
            double dz = delta.z();

            double spread = 5.0D + random.nextFloat() * 2.5D;
            double velocity = 3.0D + random.nextFloat() * 0.15D;

            dx += random.nextGaussian() * 0.0075D * spread;
            dy += random.nextGaussian() * 0.0075D * spread;
            dz += random.nextGaussian() * 0.0075D * spread;
            dx *= velocity;
            dy *= velocity;
            dz *= velocity;
            endPos.add((random.nextFloat() - 0.5f) * 2.5f, (random.nextFloat() - 0.5f) * 2.5f, (random.nextFloat() - 0.5f) * 2.5f);
            level().addParticle(ESParticles.ENERGIZED_FLAME_SPIT.get(), getX(), position().y, getZ(), dx, dy, dz);
        }
    }
}
