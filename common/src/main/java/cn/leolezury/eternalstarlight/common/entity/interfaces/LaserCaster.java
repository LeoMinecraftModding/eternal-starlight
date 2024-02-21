package cn.leolezury.eternalstarlight.common.entity.interfaces;

import net.minecraft.world.phys.Vec3;

public interface LaserCaster {
    boolean isLaserFollowingHeadRotation();
    Vec3 getLaserRotationTarget();
    void lookAtLaserEnd(Vec3 endPos);
}
