package cn.leolezury.eternalstarlight.entity.ai.goal;

import cn.leolezury.eternalstarlight.entity.npc.boarwarf.Boarwarf;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class BoarwarfMoveToBedGoal extends MoveToBlockGoal {
    private final PathfinderMob creature;
    public BoarwarfMoveToBedGoal(PathfinderMob creature, double speedIn, int length) {
        super(creature, speedIn, length);
        this.creature = creature;
    }

    @Override
    public boolean canUse() {
        if (mob instanceof Boarwarf boarwarf) {
            return (boarwarf.wantsToSleep() && !this.creature.isSleeping() && super.canUse());
        } else return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (isReachedTarget())
            this.creature.startSleeping(this.blockPos);
    }

    @Override
    protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        return (blockstate.is(BlockTags.BEDS) && blockstate.hasProperty(BedBlock.OCCUPIED) && !blockstate.getValue(BedBlock.OCCUPIED) && blockstate.hasProperty(BedBlock.PART) && blockstate.getValue(BedBlock.PART) == BedPart.HEAD);
    }

    @Override
    protected boolean findNearestBlock() {
        if (mob instanceof Boarwarf boarwarf) {
            int searchRange = 10;
            int verticalSearchRange = 5;
            BlockPos blockpos = boarwarf.homePos;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int k = this.verticalSearchStart; k <= verticalSearchRange; k = k > 0 ? -k : 1 - k) {
                for(int l = 0; l < searchRange; ++l) {
                    for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                        for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                            blockpos$mutableblockpos.setWithOffset(blockpos, i1, k - 1, j1);
                            if (this.mob.isWithinRestriction(blockpos$mutableblockpos) && this.isValidTarget(this.mob.level(), blockpos$mutableblockpos)) {
                                this.blockPos = blockpos$mutableblockpos;
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public double acceptedDistance() {
        return 2.0D;
    }
}
