package cn.leolezury.eternalstarlight.entity.ai.goal;

import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BoarwarfRandomStrollNearHomeGoal extends RandomStrollGoal {
    public BoarwarfRandomStrollNearHomeGoal(Boarwarf villager) {
        super(villager, 0.3, 10);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (mob instanceof Boarwarf boarwarf) {
            BlockPos homePos = boarwarf.homePos;
            Vec3 homeVec = new Vec3(homePos.getX(), homePos.getY(), homePos.getZ());
            return DefaultRandomPos.getPosTowards(boarwarf, 15, 7, homeVec, (float)Math.PI / 2F);
        } else return null;
    }
}
