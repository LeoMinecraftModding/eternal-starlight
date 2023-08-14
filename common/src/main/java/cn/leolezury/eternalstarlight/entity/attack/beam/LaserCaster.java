package cn.leolezury.eternalstarlight.entity.attack.beam;

import net.minecraft.world.phys.Vec3;

public interface LaserCaster {
    public boolean shouldLaserFollowHead();
    public Vec3 getLaserWantedPos();
    public void lookAtLaserEnd(Vec3 endPos);
}
