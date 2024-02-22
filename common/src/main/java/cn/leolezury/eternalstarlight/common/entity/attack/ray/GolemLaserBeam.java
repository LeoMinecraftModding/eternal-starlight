package cn.leolezury.eternalstarlight.common.entity.attack.ray;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class GolemLaserBeam extends RayAttack {
    public GolemLaserBeam(EntityType<? extends RayAttack> type, Level world) {
        super(type, world);
    }

    public GolemLaserBeam(EntityType<? extends RayAttack> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch) {
        super(type, world, caster, x, y, z, yaw, pitch);
    }

    @Override
    public void updatePosition() {
        if (tickCount > 120) {
            discard();
        }
        getCaster().ifPresentOrElse(caster -> {
            setPos(caster.position().add(0, caster.getBbHeight() / 2.5f, 0));
        }, this::discard);
    }
}
