package cn.leolezury.eternalstarlight.common.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BossSpawnerBlockEntity<T extends Mob> extends BlockEntity {
    protected final EntityType<T> entityType;
    protected boolean spawnedBoss = false;

    protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.entityType = entityType;
    }

    public boolean anyPlayerInRange() {
        return getLevel().hasNearbyAlivePlayer(getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.5D, getBlockPos().getZ() + 0.5D, getRange());
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BossSpawnerBlockEntity<?> entity) {
        if (entity.spawnedBoss || !entity.anyPlayerInRange()) {
            return;
        }
        if (level.isClientSide) {
            double rx = (pos.getX() - 0.2F + level.getRandom().nextFloat() * 1.25F);
            double ry = (pos.getY() - 0.2F + level.getRandom().nextFloat() * 1.25F);
            double rz = (pos.getZ() - 0.2F + level.getRandom().nextFloat() * 1.25F);
            level.addParticle(entity.getSpawnerParticle(), rx, ry, rz, 0.0D, 0.0D, 0.0D);
        } else if (level.getDifficulty() != Difficulty.PEACEFUL && entity.spawnBoss((ServerLevelAccessor)level)) {
            level.destroyBlock(pos, false);
            entity.spawnedBoss = true;
        }
    }

    protected boolean spawnBoss(ServerLevelAccessor accessor) {
        T mob = makeMob();

        mob.moveTo(getBlockPos(), accessor.getLevel().getRandom().nextFloat() * 360.0F, 0.0F);
        mob.finalizeSpawn(accessor, accessor.getCurrentDifficultyAt(getBlockPos()), MobSpawnType.SPAWNER, null);

        initializeCreature(mob);

        return accessor.addFreshEntity(mob);
    }

    public abstract ParticleOptions getSpawnerParticle();

    protected void initializeCreature(T mob) {
        mob.restrictTo(getBlockPos(), 50);
    }

    protected int getRange() {
        return 50;
    }

    protected T makeMob() {
        return this.entityType.create(getLevel());
    }
}