package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpellUtil {
    public static RaytraceResult raytrace(Level level, CollisionContext context, Vec3 from, Vec3 to) {
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

    public static int getCoolDownFor(LivingEntity entity, AbstractSpell spell) {
        String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
        if (!ESUtil.getPersistentData(entity).contains("SpellCoolDowns", Tag.TAG_COMPOUND)) {
            ESUtil.getPersistentData(entity).put("SpellCoolDowns", new CompoundTag());
        }
        CompoundTag spellCoolDowns = ESUtil.getPersistentData(entity).getCompound("SpellCoolDowns");
        return spellCoolDowns.getInt(id);
    }

    public static void setCoolDownFor(LivingEntity entity, AbstractSpell spell, int coolDown) {
        String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
        if (!ESUtil.getPersistentData(entity).contains("SpellCoolDowns", Tag.TAG_COMPOUND)) {
            ESUtil.getPersistentData(entity).put("SpellCoolDowns", new CompoundTag());
        }
        CompoundTag spellCoolDowns = ESUtil.getPersistentData(entity).getCompound("SpellCoolDowns");
        spellCoolDowns.putInt(id, coolDown);
    }

    public static void ticksSpellCoolDowns(LivingEntity entity) {
        if (!ESUtil.getPersistentData(entity).contains("SpellCoolDowns", Tag.TAG_COMPOUND)) {
            ESUtil.getPersistentData(entity).put("SpellCoolDowns", new CompoundTag());
        }
        CompoundTag spellCoolDowns = ESUtil.getPersistentData(entity).getCompound("SpellCoolDowns");
        ESSpells.SPELLS.registry().forEach((spell -> {
            String id = Objects.requireNonNull(ESSpells.SPELLS.registry().getKey(spell)).toString();
            if (spellCoolDowns.getInt(id) > 0) {
                spellCoolDowns.putInt(id, spellCoolDowns.getInt(id) - 1);
            }
        }));
    }
    
    public record RaytraceResult (List<Entity> entities, HitResult blockHit) {
    }
}
