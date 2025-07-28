package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.FlareSpawnerBlock;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public abstract class FlareSpawner {
	public static final String TAG_SPAWN_DATA = "spawn_data";
	public static final String TAG_SPAWN_POTENTIALS = "spawn_potentials";
	private static final String TAG_SPAWN_DELAY = "spawn_delay";
	private static final String TAG_MIN_SPAWN_DELAY = "min_spawn_delay";
	private static final String TAG_MAX_SPAWN_DELAY = "max_spawn_delay";
	private static final String TAG_SPAWN_COUNT = "spawn_count";
	private static final String TAG_TOTAL_SPAWN_COUNT = "total_spawn_count";
	private static final String TAG_SPAWNED_COUNT = "spawned_count";
	private static final String TAG_MAX_NEARBY_ENTITIES = "max_nearby_entities";
	private static final String TAG_ACTIVATION_PLAYER_RANGE = "activation_player_range";
	private static final String TAG_REQUIRED_PLAYER_RANGE = "required_player_range";
	private static final String TAG_SPAWN_RANGE = "spawn_range";
	private static final String TAG_COOLDOWN = "cooldown";
	private static final String TAG_TRACKED_MOBS = "tracked_mobs";
	private static final int EVENT_SPAWN = 1;
	private int spawnDelay = 20;
	private SimpleWeightedRandomList<SpawnData> spawnPotentials = SimpleWeightedRandomList.empty();
	@Nullable
	private SpawnData nextSpawnData;
	private double spin;
	private double oSpin;
	private int minSpawnDelay = 20;
	private int maxSpawnDelay = 80;
	private int spawnCount = 4;
	private int totalSpawnCount = 10;
	private int spawnedCount = 0;
	@Nullable
	private Entity displayEntity;
	private int maxNearbyEntities = 6;
	private int activationPlayerRange = 4;
	private int requiredPlayerRange = 16;
	private int spawnRange = 4;
	private int cooldown = 0;
	private final Set<UUID> trackedMobs = new HashSet<>();

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

	private static boolean shouldMobBeUntracked(ServerLevel serverLevel, BlockPos blockPos, UUID uuid) {
		Entity entity = serverLevel.getEntity(uuid);
		return entity == null || !entity.isAlive() || !entity.level().dimension().equals(serverLevel.dimension()) || entity.blockPosition().distSqr(blockPos) > 32 * 32;
	}

	private void sendDuskSignal(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.hasProperty(FlareSpawnerBlock.LIT) && state.getValue(FlareSpawnerBlock.LIT)) {
			if (level.getBlockEntity(pos.above()) instanceof DuskLightReceptor receptor) {
				receptor.lightUp(level, pos.above(), Direction.DOWN);
			} else if (level.getBlockState(pos.above()).getBlock() instanceof DuskLightReceptor receptor) {
				receptor.lightUp(level, pos.above(), Direction.DOWN);
			}
		}
	}

	public void clientTick(Level level, BlockPos pos) {
		sendDuskSignal(level, pos);
		this.oSpin = this.spin;
		if (this.cooldown > 0) {
			return;
		}
		BlockState state = level.getBlockState(pos);
		if (this.isNearPlayer(level, pos, this.requiredPlayerRange) && state.hasProperty(FlareSpawnerBlock.LIT) && state.getValue(FlareSpawnerBlock.LIT)) {
			RandomSource random = level.getRandom();
			double x = pos.getX() + random.nextDouble();
			double y = pos.getY() + random.nextDouble();
			double z = pos.getZ() + random.nextDouble();
			level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
			level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
			if (this.spawnDelay > 0) {
				this.spawnDelay--;
			}

			this.spin = (this.spin + (1000.0 / (this.spawnDelay + 200.0))) % 360.0;
		}
	}

	public void serverTick(ServerLevel serverLevel, BlockPos pos) {
		sendDuskSignal(serverLevel, pos);
		trackedMobs.removeIf(uuid -> shouldMobBeUntracked(serverLevel, pos, uuid));
		BlockState state = serverLevel.getBlockState(pos);
		if (this.cooldown > 0) {
			this.cooldown--;
			this.spawnedCount = 0;
			if (state.hasProperty(FlareSpawnerBlock.LIT) && state.getValue(FlareSpawnerBlock.LIT)) {
				serverLevel.setBlockAndUpdate(pos, state.setValue(FlareSpawnerBlock.LIT, false));
			}
			return;
		}
		RandomSource random = serverLevel.getRandom();
		if (trackedMobs.isEmpty() && this.spawnedCount >= this.totalSpawnCount) {
			ExperienceOrb.award(serverLevel, Vec3.atCenterOf(pos), random.nextInt(50, 101));
			this.cooldown += 36000;
		}
		if (this.isNearPlayer(serverLevel, pos, this.activationPlayerRange)
			&& state.hasProperty(FlareSpawnerBlock.LIT)
			&& !state.getValue(FlareSpawnerBlock.LIT)
			&& this.getOrCreateNextSpawnData(serverLevel, random, pos).getEntityToSpawn().contains("id", CompoundTag.OBJECT_HEADER)
			&& serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
			serverLevel.setBlockAndUpdate(pos, state.setValue(FlareSpawnerBlock.LIT, true));
			for (int i = 0; i < 15; i++) {
				Vec3 speed = new Vec3((random.nextFloat() - random.nextFloat()) * 0.1F, random.nextFloat() * 0.05F, (random.nextFloat() - random.nextFloat()) * 0.1F).normalize();
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.FLARE, pos.getX() + 0.5 + speed.x * 0.2, pos.getY() + 0.5 + speed.y * 0.2, pos.getZ() + 0.5 + speed.z * 0.2, speed.x, speed.y, speed.z));
			}
		}
		if (this.isNearPlayer(serverLevel, pos, this.requiredPlayerRange) && state.hasProperty(FlareSpawnerBlock.LIT) && state.getValue(FlareSpawnerBlock.LIT)) {
			if (this.spawnDelay == -1) {
				this.delay(serverLevel, pos);
			}

			if (this.spawnDelay > 0) {
				this.spawnDelay--;
			} else if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
				boolean success = false;
				SpawnData spawnData = this.getOrCreateNextSpawnData(serverLevel, random, pos);

				for (int i = 0; i < this.spawnCount; i++) {
					if (this.spawnedCount >= this.totalSpawnCount) {
						this.delay(serverLevel, pos);
						return;
					}

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

							boolean shouldFinalize = spawnData.getEntityToSpawn().size() == 1 && spawnData.getEntityToSpawn().contains("id", CompoundTag.OBJECT_HEADER);
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
							ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.FLARE, entity.getX() + (random.nextFloat() - 0.5f) * entity.getBbWidth() * 1.2f, entity.getY(), entity.getZ() + (random.nextFloat() - 0.5f) * entity.getBbWidth() * 1.2f, 0, 1, 0));
						}
						this.trackedMobs.add(entity.getUUID());
						this.spawnedCount++;

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
		this.spawnPotentials.getRandom(random).ifPresent(wrapper -> this.setNextSpawnData(level, pos, wrapper.data()));
		this.broadcastEvent(level, pos, EVENT_SPAWN);
	}

	public void load(@Nullable Level level, BlockPos pos, CompoundTag tag) {
		this.spawnDelay = tag.getShort(TAG_SPAWN_DELAY);
		this.spawnedCount = tag.getShort(TAG_SPAWNED_COUNT);
		this.cooldown = tag.getInt(TAG_COOLDOWN);
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

		if (tag.contains(TAG_TOTAL_SPAWN_COUNT, CompoundTag.TAG_ANY_NUMERIC)) {
			this.totalSpawnCount = tag.getShort(TAG_TOTAL_SPAWN_COUNT);
		}

		if (tag.contains(TAG_MAX_NEARBY_ENTITIES, CompoundTag.TAG_ANY_NUMERIC)) {
			this.maxNearbyEntities = tag.getShort(TAG_MAX_NEARBY_ENTITIES);
		}

		if (tag.contains(TAG_REQUIRED_PLAYER_RANGE, CompoundTag.TAG_ANY_NUMERIC)) {
			this.requiredPlayerRange = tag.getShort(TAG_REQUIRED_PLAYER_RANGE);
		}

		if (tag.contains(TAG_ACTIVATION_PLAYER_RANGE, CompoundTag.TAG_ANY_NUMERIC)) {
			this.activationPlayerRange = tag.getShort(TAG_ACTIVATION_PLAYER_RANGE);
		}

		if (tag.contains(TAG_SPAWN_RANGE, CompoundTag.TAG_ANY_NUMERIC)) {
			this.spawnRange = tag.getShort(TAG_SPAWN_RANGE);
		}

		if (tag.contains(TAG_TRACKED_MOBS)) {
			trackedMobs.clear();
			UUIDUtil.CODEC_SET.parse(NbtOps.INSTANCE, tag.get(TAG_TRACKED_MOBS))
				.resultOrPartial(s -> EternalStarlight.LOGGER.warn("Invalid tracked mobs list: {}", s))
				.ifPresent(trackedMobs::addAll);
		}

		this.displayEntity = null;
	}

	public CompoundTag save(CompoundTag tag) {
		tag.putShort(TAG_SPAWN_DELAY, (short) this.spawnDelay);
		tag.putShort(TAG_SPAWNED_COUNT, (short) this.spawnedCount);
		tag.putInt(TAG_COOLDOWN, this.cooldown);
		tag.putShort(TAG_MIN_SPAWN_DELAY, (short) this.minSpawnDelay);
		tag.putShort(TAG_MAX_SPAWN_DELAY, (short) this.maxSpawnDelay);
		tag.putShort(TAG_SPAWN_COUNT, (short) this.spawnCount);
		tag.putShort(TAG_TOTAL_SPAWN_COUNT, (short) this.totalSpawnCount);
		tag.putShort(TAG_MAX_NEARBY_ENTITIES, (short) this.maxNearbyEntities);
		tag.putShort(TAG_REQUIRED_PLAYER_RANGE, (short) this.requiredPlayerRange);
		tag.putShort(TAG_ACTIVATION_PLAYER_RANGE, (short) this.activationPlayerRange);
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
		tag.put(TAG_TRACKED_MOBS, UUIDUtil.CODEC_SET.encodeStart(NbtOps.INSTANCE, this.trackedMobs).getOrThrow());
		return tag;
	}

	@Nullable
	public Entity getOrCreateDisplayEntity(Level level, BlockPos pos) {
		if (this.displayEntity == null) {
			CompoundTag tag = this.getOrCreateNextSpawnData(level, level.getRandom(), pos).getEntityToSpawn();
			if (!tag.contains("id", CompoundTag.OBJECT_HEADER)) {
				return null;
			}
			this.displayEntity = EntityType.loadEntityRecursive(tag, level, Function.identity());
		}
		return this.displayEntity;
	}

	public boolean onEventTriggered(Level level, int id) {
		if (id == EVENT_SPAWN) {
			if (level.isClientSide) {
				this.spawnDelay = this.minSpawnDelay;
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
}
