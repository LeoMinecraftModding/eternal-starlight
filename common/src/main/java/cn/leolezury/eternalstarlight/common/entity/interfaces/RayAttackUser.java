package cn.leolezury.eternalstarlight.common.entity.interfaces;

import net.minecraft.world.phys.Vec3;

public interface RayAttackUser {
    boolean isRayFollowingHeadRotation();
    Vec3 getRayRotationTarget();
    void updateRayEnd(Vec3 endPos);
}
