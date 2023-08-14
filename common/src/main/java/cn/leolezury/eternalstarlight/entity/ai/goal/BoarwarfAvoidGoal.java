package cn.leolezury.eternalstarlight.entity.ai.goal;

import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class BoarwarfAvoidGoal extends AvoidEntityGoal<LivingEntity> {
    public BoarwarfAvoidGoal(Boarwarf villager) {
        super(villager, LivingEntity.class, 10, 1.0, 1.0);
    }

    @Override
    public boolean canUse() {
        if (mob instanceof Boarwarf boarwarf) {
            toAvoid = boarwarf.getEntityToAvoid(maxDist);
            if (this.toAvoid == null) {
                return false;
            } else {
                Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
                if (vec3 == null) {
                    return false;
                } else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
                    return false;
                } else {
                    this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                    return this.path != null;
                }
            }
        } else {
            return false;
        }
    }
}
