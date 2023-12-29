package cn.leolezury.eternalstarlight.common.entity.interfaces;

import net.minecraft.world.phys.Vec3;

public interface LaserCaster {
    boolean shouldLaserFollowHead();
    Vec3 getLaserWantedPos();
    void lookAtLaserEnd(Vec3 endPos);
}
