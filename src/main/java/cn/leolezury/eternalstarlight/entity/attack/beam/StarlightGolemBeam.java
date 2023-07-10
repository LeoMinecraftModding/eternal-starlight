package cn.leolezury.eternalstarlight.entity.attack.beam;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class StarlightGolemBeam extends AbstractLaserBeam {
    public StarlightGolemBeam(EntityType<? extends AbstractLaserBeam> type, Level world) {
        super(type, world);
    }

    public StarlightGolemBeam(EntityType<? extends AbstractLaserBeam> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration) {
        super(type, world, caster, x, y, z, yaw, pitch, duration);
    }

    @Override
    public void updatePosition() {
        setPos(caster.position().add(0, 1, 0));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {

    }
}
