package cn.leolezury.eternalstarlight.entity.ai.goal;

import cn.leolezury.eternalstarlight.entity.npc.boarwarf.golem.AstralGolem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AstralGolemRandomStrollNearVillageGoal extends WaterAvoidingRandomStrollGoal {
    public AstralGolemRandomStrollNearVillageGoal(AstralGolem golem) {
        super(golem, 0.3);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (mob instanceof AstralGolem golem) {
            BlockPos homePos = golem.homePos;
            Vec3 homeVec = new Vec3(homePos.getX(), homePos.getY(), homePos.getZ());
            if (this.mob.isInWaterOrBubble()) {
                Vec3 vec3 = LandRandomPos.getPosTowards(golem, 15, 7, homeVec);
                return vec3 == null ? super.getPosition() : vec3;
            } else {
                return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPosTowards(golem, 15, 7, homeVec) : super.getPosition();
            }
        } else return null;
    }
}
