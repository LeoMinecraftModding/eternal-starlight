package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import cn.leolezury.eternalstarlight.common.entity.attack.LunarThorn;
import cn.leolezury.eternalstarlight.common.entity.living.phase.AttackPhase;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LunarMonstrosityThornPhase extends AttackPhase<LunarMonstrosity> {
    public static final int ID = 3;

    public LunarMonstrosityThornPhase() {
        super(ID, 1, 90, 200);
    }

    @Override
    public boolean canStart(LunarMonstrosity entity, boolean cooldownOver) {
        return cooldownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(LunarMonstrosity entity) {

    }

    @Override
    public void tick(LunarMonstrosity entity) {
        LivingEntity target = entity.getTarget();
        if (entity.getAttackTicks() % 15 == 0 && target != null) {
            double d0 = Math.min(target.getY(), entity.getY());
            double d1 = Math.max(target.getY(), entity.getY()) + 1.0;
            float f = (float)Mth.atan2(target.getZ() - entity.getZ(), target.getX() - entity.getX());
            for (int i = 0; i < (entity.getAttackTicks() / 15 + 1) * 6; i++) {
                double d2 = 1.25 * (double)(i + 1);
                this.createThorn(entity, entity.getX() + (double)Mth.cos(f) * d2, entity.getZ() + (double)Mth.sin(f) * d2, d0, d1, i);
            }
        }
    }

    private void createThorn(LunarMonstrosity entity, double x, double z, double minY, double maxY, int delay) {
        BlockPos blockpos = BlockPos.containing(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = entity.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(entity.level(), blockpos1, Direction.UP)) {
                if (!entity.level().isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = entity.level().getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(entity.level(), blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            LunarThorn thorn = new LunarThorn(ESEntities.LUNAR_THORN.get(), entity.level());
            thorn.setPos(x, (double)blockpos.getY() + d0, z);
            thorn.setAttackMode(1);
            thorn.setSpawnedTicks(-delay);
            thorn.setOwner(entity);
            entity.level().addFreshEntity(thorn);
        }
    }

    @Override
    public boolean canContinue(LunarMonstrosity entity) {
        return true;
    }

    @Override
    public void onStop(LunarMonstrosity entity) {

    }
}
