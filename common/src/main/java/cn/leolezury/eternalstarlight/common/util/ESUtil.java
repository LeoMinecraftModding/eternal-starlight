package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.entity.interfaces.PersistentDataHolder;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ESUtil {
    public static void runWhenOnClient(Supplier<Runnable> toRun) {
        if (ESPlatform.INSTANCE.isPhysicalClient()) {
            toRun.get().run();
        }
    }

    public static float positionToPitch(Vec3 start, Vec3 end) {
        return positionToPitch(start.x, end.x, start.y, end.y, start.z, end.z);
    }

    public static float positionToYaw(Vec3 start, Vec3 end) {
        return positionToYaw(start.x, end.x, start.z, end.z);
    }

    public static float positionToPitch(double startX, double endX, double startY, double endY, double startZ, double endZ) {
        double d0 = endX - startX;
        double d1 = endY - startY;
        double d2 = endZ - startZ;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return !(Math.abs(d1) > (double)1.0E-5F) && !(Math.abs(d3) > (double)1.0E-5F) ? 0 : (float)((Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)));
    }

    public static float positionToYaw(double startX, double endX, double startZ, double endZ) {
        double d0 = endX - startX;
        double d1 = endZ - startZ;
        return !(Math.abs(d1) > (double)1.0E-5F) && !(Math.abs(d0) > (double)1.0E-5F) ? 0 : (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI));
    }

    public static Vec3 rotationToPosition(Vec3 startPos, float radius, float pitch, float yaw) {
        double endPosX = startPos.x + radius * Math.cos(yaw * Math.PI / 180d) * Math.cos(pitch * Math.PI / 180d);
        double endPosY = startPos.y + radius * Math.sin(pitch * Math.PI / 180d);
        double endPosZ = startPos.z + radius * Math.sin(yaw * Math.PI / 180d) * Math.cos(pitch * Math.PI / 180d);
        return new Vec3(endPosX, endPosY, endPosZ);
    }

    public static boolean isPointInEllipsoid(double x, double y, double z, double a, double b, double c) {
        double value = (x * x) / (a * a) + (y * y) / (b * b) + (z * z) / (c * c);
        return value <= 1;
    }

    public static List<int[]> getBresenham3DPoints(int x1, int y1, int z1, int x2, int y2, int z2) {
        List<int[]> points = new ArrayList<>();
        points.add(new int[]{x1, y1, z1});
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dz = Math.abs(z2 - z1);
        int xs;
        int ys;
        int zs;
        if (x2 > x1) {
            xs = 1;
        } else {
            xs = -1;
        }
        if (y2 > y1) {
            ys = 1;
        } else {
            ys = -1;
        }
        if (z2 > z1) {
            zs = 1;
        } else {
            zs = -1;
        }

        if (dx >= dy && dx >= dz) {
            int p1 = 2 * dy - dx;
            int p2 = 2 * dz - dx;
            while (x1 != x2) {
                x1 += xs;
                if (p1 >= 0) {
                    y1 += ys;
                    p1 -= 2 * dx;
                }
                if (p2 >= 0) {
                    z1 += zs;
                    p2 -= 2 * dx;
                }
                p1 += 2 * dy;
                p2 += 2 * dz;
                points.add(new int[]{x1, y1, z1});
            }
        } else if (dy >= dx && dy >= dz) {
            int p1 = 2 * dx - dy;
            int p2 = 2 * dz - dy;
            while (y1 != y2) {
                y1 += ys;
                if (p1 >= 0) {
                    x1 += xs;
                    p1 -= 2 * dy;
                }
                if (p2 >= 0) {
                    z1 += zs;
                    p2 -= 2 * dy;
                }
                p1 += 2 * dx;
                p2 += 2 * dz;
                points.add(new int[]{x1, y1, z1});
            }
        } else {
            int p1 = 2 * dy - dz;
            int p2 = 2 * dx - dz;
            while (z1 != z2) {
                z1 += zs;
                if (p1 >= 0) {
                    y1 += ys;
                    p1 -= 2 * dz;
                }
                if (p2 >= 0) {
                    x1 += xs;
                    p2 -= 2 * dz;
                }
                p1 += 2 * dy;
                p2 += 2 * dx;
                points.add(new int[]{x1, y1, z1});
            }
        }
        return points;
    }

    public static CompoundTag getPersistentData(Entity entity) {
        if (entity instanceof PersistentDataHolder holder) {
            return holder.getESPersistentData();
        }
        return new CompoundTag();
    }
}
