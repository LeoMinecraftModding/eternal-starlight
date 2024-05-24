package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ESMathUtil {
    public static float positionToPitch(Vec3 start, Vec3 end) {
        return positionToPitch(end.subtract(start));
    }

    public static float positionToYaw(Vec3 start, Vec3 end) {
        return positionToYaw(end.subtract(start));
    }

    public static float positionToPitch(Vec3 vec3) {
        return positionToPitch(vec3.x, vec3.y, vec3.z);
    }

    public static float positionToYaw(Vec3 vec3) {
        return positionToYaw(vec3.x, vec3.z);
    }

    public static float positionToPitch(double diffX, double diffY, double diffZ) {
        double horizontalDist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        return !(Math.abs(diffY) > (double)1.0E-5F) && !(Math.abs(horizontalDist) > (double)1.0E-5F) ? 0 : (float)((Mth.atan2(diffY, horizontalDist) * Mth.RAD_TO_DEG));
    }

    public static float positionToYaw(double diffX, double diffZ) {
        return !(Math.abs(diffZ) > (double)1.0E-5F) && !(Math.abs(diffX) > (double)1.0E-5F) ? 0 : (float)(Mth.atan2(diffZ, diffX) * Mth.RAD_TO_DEG);
    }

    public static Vec3 rotationToPosition(float radius, float pitch, float yaw) {
        double endPosX = radius * Math.cos(yaw * Mth.DEG_TO_RAD) * Math.cos(pitch * Mth.DEG_TO_RAD);
        double endPosY = radius * Math.sin(pitch * Mth.DEG_TO_RAD);
        double endPosZ = radius * Math.sin(yaw * Mth.DEG_TO_RAD) * Math.cos(pitch * Mth.DEG_TO_RAD);
        return new Vec3(endPosX, endPosY, endPosZ);
    }

    public static Vec3 rotationToPosition(Vec3 startPos, float radius, float pitch, float yaw) {
        return startPos.add(rotationToPosition(radius, pitch, yaw));
    }

    public static Vec3 lerpVec(float progress, Vec3 from, Vec3 to) {
        return new Vec3(Mth.lerp(progress, from.x, to.x), Mth.lerp(progress, from.y, to.y), Mth.lerp(progress, from.z, to.z));
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

    public static double distBetweenLineAndDot(Vec3 start, Vec3 end, Vec3 point) {
        return distBetweenLineAndDot(start.x, start.y, start.z, end.x, end.y, end.z, point.x, point.y, point.z);
    }

    public static double distBetweenLineAndDot(double x1, double y1, double z1, double x2, double y2, double z2, double px, double py, double pz) {
        return Math.sqrt(distSqrBetweenLineAndDot(x1, y1, z1, x2, y2, z2, px, py, pz));
    }

    public static double distSqrBetweenLineAndDot(Vec3 start, Vec3 end, Vec3 point) {
        return distSqrBetweenLineAndDot(start.x, start.y, start.z, end.x, end.y, end.z, point.x, point.y, point.z);
    }

    public static double distSqrBetweenLineAndDot(double x1, double y1, double z1, double x2, double y2, double z2, double px, double py, double pz) {
        double a = px - x1;
        double b = py - y1;
        double c = pz - z1;
        double d = x2 - x1;
        double e = y2 - y1;
        double f = z2 - z1;

        double dot = a * d + b * e + c * f;
        double lenSq = d * d + e * e + f * f;
        double param = dot / lenSq;

        double closestX, closestY, closestZ;

        if (param < 0) {
            closestX = x1;
            closestY = y1;
            closestZ = z1;
        } else if (param > 1) {
            closestX = x2;
            closestY = y2;
            closestZ = z2;
        } else {
            closestX = x1 + param * d;
            closestY = y1 + param * e;
            closestZ = z1 + param * f;
        }

        double dx = px - closestX;
        double dy = py - closestY;
        double dz = pz - closestZ;

        return dx * dx + dy * dy + dz * dz;
    }

    public static double distBetweenLines(Vec3 start1, Vec3 end1, Vec3 start2, Vec3 end2) {
        return distBetweenLines(start1.x, start1.y, start1.z, end1.x, end1.y, end1.z, start2.x, start2.y, start2.z, end2.x, end2.y, end2.z);
    }

    public static double distBetweenLines(double x1, double y1, double z1,
                                          double x2, double y2, double z2,
                                          double x3, double y3, double z3,
                                          double x4, double y4, double z4)
    {
        return Math.sqrt(distSqrBetweenLines(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4));
    }

    public static double distSqrBetweenLines(Vec3 start1, Vec3 end1, Vec3 start2, Vec3 end2) {
        return distSqrBetweenLines(start1.x, start1.y, start1.z, end1.x, end1.y, end1.z, start2.x, start2.y, start2.z, end2.x, end2.y, end2.z);
    }

    public static double distSqrBetweenLines(double x1, double y1, double z1,
                                             double x2, double y2, double z2,
                                             double x3, double y3, double z3,
                                             double x4, double y4, double z4)
    {
        double ux = x2 - x1;
        double uy = y2 - y1;
        double uz = z2 - z1;

        double vx = x4 - x3;
        double vy = y4 - y3;
        double vz = z4 - z3;

        double wx = x1 - x3;
        double wy = y1 - y3;
        double wz = z1 - z3;

        double a = (ux * ux + uy * uy + uz * uz);
        double b = (ux * vx + uy * vy + uz * vz);
        double c = (vx * vx + vy * vy + vz * vz);
        double d = (ux * wx + uy * wy + uz * wz);
        double e = (vx * wx + vy * wy + vz * wz);
        double dt = a * c - b * b;

        double sd = dt;
        double td = dt;

        double sn;
        double tn;

        if (Math.abs(dt) < 1e-7) {
            sn = 0.0;
            sd = 1.00;

            tn = e;
            td = c;
        } else {
            sn = (b * e - c * d);
            tn = (a * e - b * d);
            if (sn < 0.0) {
                sn = 0.0;
                tn = e;
                td = c;
            } else if (sn > sd) {
                sn = sd;
                tn = e + b;
                td = c;
            }
        }
        if (tn < 0.0) {
            tn = 0.0;
            if (-d < 0.0)
                sn = 0.0;
            else if (-d > a)
                sn = sd;
            else {
                sn = -d;
                sd = a;
            }
        } else if (tn > td) {
            tn = td;
            if ((-d + b) < 0.0)
                sn = 0.0;
            else if ((-d + b) > a)
                sn = sd;
            else {
                sn = (-d + b);
                sd = a;
            }
        }

        double sc;
        double tc;

        if (Math.abs(sn) < 1e-7)
            sc = 0.0;
        else
            sc = sn / sd;

        if (Math.abs(tn) < 1e-7)
            tc = 0.0;
        else
            tc = tn / td;

        double dx = wx + (sc * ux) - (tc * vx);
        double dy = wy + (sc * uy) - (tc * vy);
        double dz = wz + (sc * uz) - (tc * vz);
        return dx * dx + dy * dy + dz * dz;
    }
}
