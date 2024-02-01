package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.init.ParticleInit;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import cn.leolezury.eternalstarlight.common.util.SpellUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class TeleportationSpell extends AbstractSpell {
    public TeleportationSpell(Properties properties) {
        super(properties);
    }

    @Override
    public boolean checkExtraConditions(LivingEntity entity) {
        return true;
    }

    @Override
    public boolean checkExtraConditionsToContinue(LivingEntity entity, int ticks) {
        return true;
    }

    @Override
    public void onPreparationTick(LivingEntity entity, int ticks) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            doHurtEnemies(serverLevel, entity);
        }
    }

    @Override
    public void onSpellTick(LivingEntity entity, int ticks) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 5; i++) {
                serverLevel.sendParticles(ParticleTypes.PORTAL, entity.getX() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), entity.getY() + entity.getBbHeight() / 2d + (entity.getRandom().nextDouble() - 0.5) * entity.getBbHeight(), entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), 20, 0.5, 0.5, 0.5, 0);
            }
            doHurtEnemies(serverLevel, entity);
        }
    }

    @Override
    public void onStart(LivingEntity entity) {

    }

    @Override
    public void onStop(LivingEntity entity, int ticks) {
        if (ticks >= spellProperties().spellTicks()) {
            doTeleport(entity);
        }
    }

    public void doHurtEnemies(ServerLevel serverLevel, LivingEntity entity) {
        for (int i = 0; i < 360; i += 5) {
            Vec3 vec3 = ESUtil.rotationToPosition(entity.position().add(0, entity.getBbHeight() / 2f, 0), 4 * entity.getBbWidth(), 0, i);
            serverLevel.sendParticles(ParticleInit.STARLIGHT.get(), vec3.x, vec3.y, vec3.z, 1, 0, 0, 0, 0);
        }
        for (LivingEntity livingEntity : serverLevel.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(entity.getBbWidth() * 3.5f))) {
            if (livingEntity.distanceTo(entity) <= 4 * entity.getBbWidth()) {
                if ((!(entity instanceof Enemy) && livingEntity instanceof Enemy) || (entity instanceof Enemy && !(livingEntity instanceof Enemy))) {
                    livingEntity.hurt(entity instanceof Player player ? serverLevel.damageSources().playerAttack(player) : serverLevel.damageSources().mobAttack(entity), 4);
                }
            }
        }
    }

    public void doTeleport(LivingEntity entity) {
        Vec3 startPos = entity.getEyePosition();
        float lookYaw = entity.getYHeadRot() + 90.0f;
        float lookPitch = -entity.getXRot();
        Vec3 endPos = ESUtil.rotationToPosition(startPos, 30, lookPitch, lookYaw);
        SpellUtil.RaytraceResult result = SpellUtil.raytrace(entity.level(), CollisionContext.of(entity), startPos, endPos);
        if (!result.entities().isEmpty()) {
            for (int i = 0; i < result.entities().size(); i++) {
                if (!result.entities().get(i).getUUID().equals(entity.getUUID())) {
                    Vec3 target = result.entities().get(i).position();
                    entity.teleportTo(target.x, target.y, target.z);
                    return;
                }
            }
        }
        if (result.blockHit() != null) {
            Vec3 target = result.blockHit().getLocation();
            entity.teleportTo(target.x, target.y, target.z);
        }
    }
}
