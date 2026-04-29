package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.MechanicalSpawnerBlock;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public abstract class MechanicalSpawner {
	public static final String TAG_SPAWN_DATA = "spawn_data";
	public static final String TAG_SPAWN_POTENTIALS = "spawn_potentials";
	private static final String TAG_SPAWN_DELAY = "spawn_delay";
	private static final String TAG_MIN_SPAWN_DELAY = "min_spawn_delay";
	private static final String TAG_MAX_SPAWN_DELAY = "max_spawn_delay";
	private static final String TAG_SPAWN_COUNT = "spawn_count";
	private static final String TAG_MAX_NEARBY_ENTITIES = "max_nearby_entities";
	private static final String TAG_REQUIRED_PLAYER_RANGE = "required_player_range";
	private static final String TAG_SPAWN_RANGE = "spawn_range";
	private static final int EVENT_SPAWN = 1;
	private int spawnDelay = 20;
	private SimpleWeightedRandomList<SpawnData> spawnPotentials = SimpleWeightedRandomList.empty();
	@Nullable
	private SpawnData nextSpawnData;
	private double spin;
	private double oSpin;
	private float animationScale;
	private float oAnimationScale;
	private int minSpawnDelay = 20;
	private int maxSpawnDelay = 30;
	private int spawnCount = 1;
	@Nullable
	private Entity displayEntity;
	private int maxNearbyEntities = 6;
	private int requiredPlayerRange = 16;
	private int spawnRange = 4;

	public void setEntityId(EntityType<?> type, @Nullable Level level, RandomSource random, BlockPos pos) {
		this.getOrCreateNextSpawnData(level, random, pos)
			.getEntityToSpawn()
			.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(type).toString());
	}

	private boolean isNearPlayer(Level level, BlockPos pos, int range) {
		return !level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(range)).stream().filter(player -> (level.isClientSide || inLineOfSight(level, pos.getCenter(), player.getEyePosition())) && !player.isCreative() && !player.isSpectator()).toList().isEmpty();
	}

	private static boolean inLineOfSight(Level level, Vec3 block, Vec3 target) {
		BlockHitResult blockHitResult = level.clip(new ClipContext(target, block, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, CollisionContext.empty()));
		return blockHitResult.getBlockPos().equals(BlockPos.containing(block)) || blockHitResult.getType() == HitResult.Type.MISS;
	}

	public void clientTick(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		this.oSpin = this.spin;
		this.oAnimationScale = this.animationScale;
		if (this.isNearPlayer(level, pos, this.requiredPlayerRange) && state.getValue(MechanicalSpawnerBlock.POWER) > 0) {
			RandomSource random = level.getRandom();
			double x = pos.getX() + random.nextDouble();
			double y = pos.getY() + random.nextDouble() * 2 - 1;
			double z = pos.getZ() + random.nextDouble();
			level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
			level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
			if (this.spawnDelay > 0) {
				this.spawnDelay--;
			}

			this.spin = (this.spin + (1000.0 / (this.spawnDelay + 200.0))) % 360.0;
			this.animationScale = Mth.clamp(this.animationScale + 0.5F, 0, 1);
		} else {
			this.animationScale = Mth.clamp(this.animationScale - 0.2F, 0, 1);
		}
	}

	public void serverTick(ServerLevel serverLevel, BlockPos pos) {
		BlockState state = serverLevel.getBlockState(pos);
		RandomSource random = serverLevel.getRandom();
		if (this.isNearPlayer(serverLevel, pos, this.requiredPlayerRange)) {
			if (this.spawnDelay == -1) {
				this.delay(serverLevel, pos);
			}

			if (this.spawnDelay > 0) {
				if (state.getValue(MechanicalSpawnerBlock.POWER) > 0) {
					this.spawnDelay--;
				}
			} else if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
				boolean success = false;
				SpawnData spawnData = this.getOrCreateNextSpawnData(serverLevel, random, pos);

				for (int i = 0; i < this.spawnCount; i++) {
					CompoundTag entityToSpawn = spawnData.getEntityToSpawn();
					Optional<EntityType<?>> type = EntityType.by(entityToSpawn);
					if (type.isEmpty()) {
						this.delay(serverLevel, pos);
						return;
					}

					ListTag posTag = entityToSpawn.getList("Pos", CompoundTag.TAG_DOUBLE);
					int posSize = posTag.size();
					double x = posSize >= 1
						? posTag.getDouble(0)
						: pos.getX() + (random.nextDouble() - random.nextDouble()) * this.spawnRange + 0.5;
					double y = posSize >= 2 ? posTag.getDouble(1) : (pos.getY() + random.nextInt(3) - 1);
					double z = posSize >= 3
						? posTag.getDouble(2)
						: pos.getZ() + (random.nextDouble() - random.nextDouble()) * this.spawnRange + 0.5;
					if (serverLevel.noCollision(type.get().getSpawnAABB(x, y, z)) && inLineOfSight(serverLevel, pos.getCenter(), new Vec3(x, y, z))) {
						BlockPos spawnPos = BlockPos.containing(x, y, z);
						if (spawnData.getCustomSpawnRules().isPresent()) {
							if (!type.get().getCategory().isFriendly() && serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
								continue;
							}

							SpawnData.CustomSpawnRules rules = spawnData.getCustomSpawnRules().get();
							if (!rules.isValidPosition(spawnPos, serverLevel)) {
								continue;
							}
						} else if (!SpawnPlacements.checkSpawnRules(type.get(), serverLevel, MobSpawnType.TRIAL_SPAWNER, spawnPos, serverLevel.getRandom())) {
							continue;
						}

						Entity entity = EntityType.loadEntityRecursive(entityToSpawn, serverLevel, e -> {
							e.moveTo(x, y, z, e.getYRot(), e.getXRot());
							return e;
						});
						if (entity == null) {
							this.delay(serverLevel, pos);
							return;
						}

						int entitiesCount = serverLevel.getEntities(EntityTypeTest.forExactClass(entity.getClass()), new AABB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1)).inflate(this.spawnRange), EntitySelector.NO_SPECTATORS).size();
						if (entitiesCount >= this.maxNearbyEntities) {
							this.delay(serverLevel, pos);
							return;
						}

						entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), random.nextFloat() * 360.0F, 0.0F);
						if (entity instanceof Mob mob) {
							if (!mob.checkSpawnObstruction(serverLevel)) {
								continue;
							}

							boolean shouldFinalize = spawnData.getEntityToSpawn().size() == 1 && spawnData.getEntityToSpawn().contains("id", CompoundTag.TAG_STRING);
							if (shouldFinalize) {
								mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.TRIAL_SPAWNER, null);
							}

							spawnData.getEquipment().ifPresent(mob::equip);
						}

						if (!serverLevel.tryAddFreshEntityWithPassengers(entity)) {
							this.delay(serverLevel, pos);
							return;
						}

						serverLevel.levelEvent(LevelEvent.PARTICLES_MOBBLOCK_SPAWN, pos, 0);
						serverLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, spawnPos);
						if (entity instanceof Mob mob) {
							mob.spawnAnim();
						}
						for (int j = 0; j <= 10; j++) {
							ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.ENERGY, entity.getX() + (random.nextFloat() - 0.5f) * entity.getBbWidth() * 1.2f, entity.getY(), entity.getZ() + (random.nextFloat() - 0.5f) * entity.getBbWidth() * 1.2f, 0, 1, 0));
						}

						success = true;
					}
				}

				if (success) {
					this.delay(serverLevel, pos);
				}
			}
		}
	}

	private void delay(Level level, BlockPos pos) {
		RandomSource random = level.getRandom();
		if (this.maxSpawnDelay <= this.minSpawnDelay) {
			this.spawnDelay = this.minSpawnDelay;
		} else {
			this.spawnDelay = this.minSpawnDelay + random.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
		}
		BlockState state = level.getBlockState(pos);
		this.spawnDelay *= state.hasProperty(MechanicalSpawnerBlock.POWER) ? (16 - state.getValue(MechanicalSpawnerBlock.POWER)) : 1;
		this.spawnPotentials.getRandom(random).ifPresent(wrapper -> this.setNextSpawnData(level, pos, wrapper.data()));
		this.broadcastEvent(level, pos, EVENT_SPAWN);
	}

	public void load(@Nullable Level level, BlockPos pos, CompoundTag tag) {
		this.spawnDelay = tag.getShort(TAG_SPAWN_DELAY);
		boolean hasSpawnData = tag.contains(TAG_SPAWN_DATA, CompoundTag.TAG_COMPOUND);
		if (hasSpawnData) {
			SpawnData data = SpawnData.CODEC
				.parse(NbtOps.INSTANCE, tag.getCompound(TAG_SPAWN_DATA))
				.resultOrPartial(s -> EternalStarlight.LOGGER.warn("Invalid SpawnData: {}", s))
				.orElseGet(SpawnData::new);
			this.setNextSpawnData(level, pos, data);
		}

		boolean hasSpawnPotentials = tag.contains(TAG_SPAWN_POTENTIALS, CompoundTag.TAG_LIST);
		if (hasSpawnPotentials) {
			ListTag list = tag.getList(TAG_SPAWN_POTENTIALS, CompoundTag.TAG_COMPOUND);
			this.spawnPotentials = SpawnData.LIST_CODEC
				.parse(NbtOps.INSTANCE, list)
				.resultOrPartial(s -> EternalStarlight.LOGGER.warn("Invalid SpawnPotentials list: {}", s))
				.orElseGet(SimpleWeightedRandomList::empty);
		} else {
			this.spawnPotentials = SimpleWeightedRandomList.single(this.nextSpawnData != null ? this.nextSpawnData : new SpawnData());
		}

		if (tag.contains(TAG_MIN_SPAWN_DELAY, CompoundTag.TAG_ANY_NUMERIC)) {
			this.minSpawnDelay = tag.getShort(TAG_MIN_SPAWN_DELAY);
		}

		if (tag.contains(TAG_MAX_SPAWN_DELAY, CompoundTag.TAG_ANY_NUMERIC)) {
			this.maxSpawnDelay = tag.getShort(TAG_MAX_SPAWN_DELAY);
		}

		if (tag.contains(TAG_SPAWN_COUNT, CompoundTag.TAG_ANY_NUMERIC)) {
			this.spawnCount = tag.getShort(TAG_SPAWN_COUNT);
		}

		if (tag.contains(TAG_MAX_NEARBY_ENTITIES, CompoundTag.TAG_ANY_NUMERIC)) {
			this.maxNearbyEntities = tag.getShort(TAG_MAX_NEARBY_ENTITIES);
		}

		if (tag.contains(TAG_REQUIRED_PLAYER_RANGE, CompoundTag.TAG_ANY_NUMERIC)) {
			this.requiredPlayerRange = tag.getShort(TAG_REQUIRED_PLAYER_RANGE);
		}

		if (tag.contains(TAG_SPAWN_RANGE, CompoundTag.TAG_ANY_NUMERIC)) {
			this.spawnRange = tag.getShort(TAG_SPAWN_RANGE);
		}

		this.displayEntity = null;
	}

	public CompoundTag save(CompoundTag tag) {
		tag.putShort(TAG_SPAWN_DELAY, (short) this.spawnDelay);
		tag.putShort(TAG_MIN_SPAWN_DELAY, (short) this.minSpawnDelay);
		tag.putShort(TAG_MAX_SPAWN_DELAY, (short) this.maxSpawnDelay);
		tag.putShort(TAG_SPAWN_COUNT, (short) this.spawnCount);
		tag.putShort(TAG_MAX_NEARBY_ENTITIES, (short) this.maxNearbyEntities);
		tag.putShort(TAG_REQUIRED_PLAYER_RANGE, (short) this.requiredPlayerRange);
		tag.putShort(TAG_SPAWN_RANGE, (short) this.spawnRange);
		if (this.nextSpawnData != null) {
			tag.put(
				TAG_SPAWN_DATA,
				SpawnData.CODEC
					.encodeStart(NbtOps.INSTANCE, this.nextSpawnData)
					.getOrThrow(s -> new IllegalStateException("Invalid SpawnData: " + s))
			);
		}
		tag.put(TAG_SPAWN_POTENTIALS, SpawnData.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.spawnPotentials).getOrThrow());
		return tag;
	}

	@Nullable
	public Entity getOrCreateDisplayEntity(Level level, BlockPos pos) {
		if (this.displayEntity == null) {
			CompoundTag tag = this.getOrCreateNextSpawnData(level, level.getRandom(), pos).getEntityToSpawn();
			if (!tag.contains("id", CompoundTag.TAG_STRING)) {
				return null;
			}
			this.displayEntity = EntityType.loadEntityRecursive(tag, level, Function.identity());
		}
		return this.displayEntity;
	}

	public boolean onEventTriggered(Level level, BlockState state, int id) {
		if (id == EVENT_SPAWN) {
			if (level.isClientSide) {
				this.spawnDelay = this.minSpawnDelay * (state.hasProperty(MechanicalSpawnerBlock.POWER) ? (16 - state.getValue(MechanicalSpawnerBlock.POWER)) : 1);
			}
			return true;
		} else {
			return false;
		}
	}

	protected void setNextSpawnData(@Nullable Level level, BlockPos pos, SpawnData nextSpawnData) {
		this.nextSpawnData = nextSpawnData;
	}

	private SpawnData getOrCreateNextSpawnData(@Nullable Level level, RandomSource random, BlockPos pos) {
		if (this.nextSpawnData == null) {
			this.setNextSpawnData(level, pos, this.spawnPotentials.getRandom(random).map(WeightedEntry.Wrapper::data).orElseGet(SpawnData::new));
		}
		return this.nextSpawnData;
	}

	public abstract void broadcastEvent(Level level, BlockPos pos, int eventId);

	public double getSpin() {
		return this.spin;
	}

	public double getOSpin() {
		return this.oSpin;
	}

	public float getAnimationScale() {
		return this.animationScale;
	}

	public float getOAnimationScale() {
		return this.oAnimationScale;
	}
}
