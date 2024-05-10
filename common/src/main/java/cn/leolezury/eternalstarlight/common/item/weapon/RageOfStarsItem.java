package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.item.interfaces.Swingable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class RageOfStarsItem extends SwordItem implements Swingable {
    public RageOfStarsItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void swing(ItemStack stack, LivingEntity entity) {
        double range = 10;
        Vec3 eyePosition = entity.getEyePosition();
        Vec3 viewVector = entity.getViewVector(1.0F);
        Vec3 vec3 = eyePosition.add(viewVector.x * range, viewVector.y * range, viewVector.z * range);
        AABB aabb = entity.getBoundingBox().expandTowards(viewVector.scale(range)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity, eyePosition, vec3, aabb, (entity1) -> !entity1.isSpectator() && entity1 instanceof LivingEntity, range * range);
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel serverLevel) {
            Vec3 location = livingEntity.position();
            AethersentMeteor.createMeteorShower(serverLevel, entity, livingEntity, location.x, location.y, location.z, 200, false);
        }
    }
}
