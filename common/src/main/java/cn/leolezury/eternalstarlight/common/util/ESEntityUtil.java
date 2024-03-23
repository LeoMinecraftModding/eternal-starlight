package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.entity.interfaces.PersistentDataHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.ArrayList;
import java.util.List;

public class ESEntityUtil {
    public static CompoundTag getPersistentData(Entity entity) {
        if (entity instanceof PersistentDataHolder holder) {
            return holder.getESPersistentData();
        }
        return new CompoundTag();
    }

    public static RaytraceResult raytrace(LevelAccessor level, CollisionContext context, Vec3 from, Vec3 to) {
        HitResult hitResult = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, context));
        RaytraceResult result = new RaytraceResult(new ArrayList<>(), hitResult.getType() == HitResult.Type.BLOCK ? hitResult : null);
        List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(Math.min(from.x, to.x), Math.min(from.y, to.y), Math.min(from.z, to.z), Math.max(from.x, to.x), Math.max(from.y, to.y), Math.max(from.z, to.z)).inflate(1));
        for (Entity entity : entities) {
            AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius() + 0.5f);
            if (aabb.contains(from)) {
                result.entities().add(entity);
                continue;
            }
            if (aabb.clip(from, to).isPresent()) {
                result.entities().add(entity);
            }
        }
        result.entities().sort((o1, o2) -> (int) Math.signum(o1.position().distanceTo(from) - o2.position().distanceTo(from)));
        return result;
    }

    public record RaytraceResult (List<Entity> entities, HitResult blockHit) {
    }
}
