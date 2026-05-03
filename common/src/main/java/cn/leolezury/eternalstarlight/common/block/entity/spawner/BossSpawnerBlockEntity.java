package cn.leolezury.eternalstarlight.common.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class BossSpawnerBlockEntity<T extends Mob> extends BlockEntity {
	private static final String TAG_SPAWN_COOLDOWN = "spawn_cooldown";

	protected final EntityType<T> entityType;
	protected boolean spawnedBoss = false;
	protected int spawnCooldown = 0;

	protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.entityType = entityType;
	}

	public void setSpawnCooldown(int spawnCooldown) {
		this.spawnCooldown = spawnCooldown;
		setChanged();
	}

	public boolean anyPlayerInRange() {
		return getLevel() != null && getLevel().hasNearbyAlivePlayer(getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.5D, getBlockPos().getZ() + 0.5D, getRange());
	}

	public static void tick(Level level, BlockPos pos, BlockState state, BossSpawnerBlockEntity<?> entity) {
		if (entity.spawnCooldown > 0) {
			entity.spawnCooldown--;
			if (entity.spawnCooldown % 10 == 0) {
				entity.setChanged();
			}
		}
		if (entity.spawnedBoss || !entity.anyPlayerInRange()) {
			return;
		}
		if (level.isClientSide) {
			RandomSource random = level.getRandom();
			if (random.nextFloat() < 0.3f) {
				Vec3 particlePos = pos.getCenter().add((random.nextDouble() - 0.5) * 1.25, (random.nextDouble() - 0.5) * 1.25, (random.nextDouble() - 0.5) * 1.25);
				level.addParticle(entity.getSpawnerParticle(), particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0);
			}
		} else if (level.getDifficulty() != Difficulty.PEACEFUL && entity.spawnCooldown <= 0 && level instanceof ServerLevelAccessor serverLevel && entity.spawnBoss(serverLevel)) {
			level.destroyBlock(pos, false);
			entity.spawnedBoss = true;
		}
	}

	protected boolean spawnBoss(ServerLevelAccessor accessor) {
		T mob = createBoss();
		if (mob == null) {
			return false;
		}
		mob.moveTo(getBlockPos(), accessor.getLevel().getRandom().nextFloat() * 360.0F, 0.0F);
		mob.finalizeSpawn(accessor, accessor.getCurrentDifficultyAt(getBlockPos()), MobSpawnType.SPAWNER, null);
		initializeBoss(mob);
		return accessor.addFreshEntity(mob);
	}

	protected int getRange() {
		return 50;
	}

	public abstract ParticleOptions getSpawnerParticle();

	protected void initializeBoss(T mob) {
		mob.restrictTo(getBlockPos(), 50);
		mob.setPersistenceRequired();
	}

	protected T createBoss() {
		if (getLevel() == null) {
			return null;
		}
		return this.entityType.create(getLevel());
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.putInt(TAG_SPAWN_COOLDOWN, spawnCooldown);
	}

	@Override
	protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		spawnCooldown = compoundTag.getInt(TAG_SPAWN_COOLDOWN);
	}
}