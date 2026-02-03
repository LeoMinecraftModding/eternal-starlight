package cn.leolezury.eternalstarlight.common.entity.living.boss;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviorUser;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.GlobalVec3;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ESBoss extends Monster implements MultiBehaviorUser {
	private static final String TAG_INITIAL_POS = "initial_pos";
	private static final String TAG_SPAWNED = "spawned";
	private static final String TAG_PHASE = "phase";
	private static final String TAG_ACTIVATED = "activated";

	protected final List<UUID> fightParticipants = new ArrayList<>();

	protected ESBoss(EntityType<? extends ESBoss> type, Level level) {
		super(type, level);
		if (level.isClientSide) {
			ESClientHandler.BOSSES.add(this);
		}
	}

	protected static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);

	public int getPhase() {
		return this.getEntityData().get(PHASE);
	}

	public void setPhase(int phase) {
		this.getEntityData().set(PHASE, phase);
	}

	protected static final EntityDataAccessor<Integer> BEHAVIOR_STATE = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);

	public int getBehaviorState() {
		return this.getEntityData().get(BEHAVIOR_STATE);
	}

	public void setBehaviorState(int behaviourState) {
		this.getEntityData().set(BEHAVIOR_STATE, behaviourState);
	}

	protected static final EntityDataAccessor<Integer> BEHAVIOR_TICKS = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);

	public int getBehaviorTicks() {
		return this.getEntityData().get(BEHAVIOR_TICKS);
	}

	public void setBehaviorTicks(int behaviourTicks) {
		this.getEntityData().set(BEHAVIOR_TICKS, behaviourTicks);
	}

	protected static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.BOOLEAN);

	public boolean isActivated() {
		return this.getEntityData().get(ACTIVATED);
	}

	public void setActivated(boolean activated) {
		this.getEntityData().set(ACTIVATED, activated);
		if (activated) {
			setBehaviorState(0);
			setBehaviorTicks(0);
		}
	}

	private GlobalVec3 initialPos = GlobalVec3.of(Level.OVERWORLD, Vec3.ZERO);
	private boolean spawned = false;

	public GlobalVec3 getInitialPos() {
		return initialPos;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BEHAVIOR_STATE, 0)
			.define(BEHAVIOR_TICKS, 0)
			.define(PHASE, 0)
			.define(ACTIVATED, true);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
		spawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
		initializeBossOnFirstSpawn();
		return spawnGroupData;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains(TAG_INITIAL_POS)) {
			GlobalVec3.CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_INITIAL_POS)).resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse boss initial pos: {}", s)).ifPresent(pos -> this.initialPos = pos);
		}
		spawned = compoundTag.getBoolean(TAG_SPAWNED);
		setPhase(compoundTag.getInt(TAG_PHASE));
		if (compoundTag.contains(TAG_ACTIVATED, CompoundTag.TAG_INT)) {
			setActivated(compoundTag.getBoolean(TAG_ACTIVATED));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.put(TAG_INITIAL_POS, GlobalVec3.CODEC.encodeStart(NbtOps.INSTANCE, initialPos).getOrThrow());
		compoundTag.putBoolean(TAG_SPAWNED, spawned);
		compoundTag.putInt(TAG_PHASE, getPhase());
		compoundTag.putBoolean(TAG_ACTIVATED, isActivated());
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean success = super.hurt(source, amount);
		if (success && source.getEntity() instanceof ServerPlayer player && !fightParticipants.contains(player.getUUID())) {
			fightParticipants.add(player.getUUID());
		}
		return success;
	}

	@Override
	public void die(DamageSource source) {
		if (!level().isClientSide) {
			for (UUID uuid : fightParticipants) {
				Player player = level().getPlayerByUUID(uuid);
				if (player instanceof ServerPlayer serverPlayer && player.isAlive() && player.level().dimension() == level().dimension()) {
					CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger(serverPlayer, this, source);
				}
			}
		}
		super.die(source);
	}

	@Override
	public boolean removeWhenFarAway(double dist) {
		return false;
	}

	@Override
	public boolean canChangeDimensions(Level level, Level level1) {
		return false;
	}

	@Override
	public boolean startRiding(Entity entity, boolean bl) {
		return false;
	}

	public boolean canBossMove() {
		return true;
	}

	public void initializeBoss() {
		initialPos = GlobalVec3.of(level().dimension(), position());
	}

	public void initializeBossOnFirstSpawn() {
		if (!spawned) {
			initializeBoss();
			spawned = true;
		}
	}

	public boolean shouldPlayBossMusic() {
		return isAlive() && getBossMusic() != null;
	}

	public SoundEvent getBossMusic() {
		return ESSoundEvents.MUSIC_BOSS.get();
	}

	public ResourceKey<LootTable> getBossLootTable() {
		ResourceLocation lootTable;
		ResourceLocation resourcelocation = BuiltInRegistries.ENTITY_TYPE.getKey(getType());

		lootTable = resourcelocation.withPrefix("bosses/");

		return ResourceKey.create(Registries.LOOT_TABLE, lootTable);
	}

	public ItemStack getBossLootBag() {
		ItemStack lootBag = new ItemStack(ESItems.LOOT_BAG.get());
		lootBag.applyComponentsAndValidate(DataComponentPatch.builder()
			.set(DataComponents.LORE, new ItemLore(List.of(getDisplayName())))
			.set(ESDataComponents.LOOT_TABLE.get(), getBossLootTable()).build());
		return lootBag;
	}

	@Override
	protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
		super.dropCustomDeathLoot(serverLevel, damageSource, bl);
		ItemStack lootBag = getBossLootBag();
		if (fightParticipants.stream().noneMatch(uuid -> level().getPlayerByUUID(uuid) != null)) {
			ItemEntity item = spawnAtLocation(lootBag.copy());
			if (item != null) {
				ESDataAttachments.IMPORTANT_ITEM.setData(item, true);
				item.setGlowingTag(true);
				item.setExtendedLifetime();
			}
		}
		for (UUID uuid : fightParticipants) {
			Player player = level().getPlayerByUUID(uuid);
			if (player instanceof ServerPlayer serverPlayer && player.isAlive() && player.level().dimension() == level().dimension()) {
				ItemEntity item = player.spawnAtLocation(lootBag.copy());
				if (item != null) {
					ESDataAttachments.IMPORTANT_ITEM.setData(item, true);
					item.setTarget(player.getUUID());
					item.setGlowingTag(true);
					item.setExtendedLifetime();
				}
				dropExtraLoot(serverPlayer);
			}
		}
	}

	public void dropExtraLoot(ServerPlayer player) {

	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			initializeBossOnFirstSpawn();
			if (!canBossMove() && level().dimension() == initialPos.dimension()) {
				setPos(initialPos.pos().x, position().y, initialPos.pos().z);
			}
			fightParticipants.removeIf(uuid -> {
				Player player = level().getPlayerByUUID(uuid);
				return player == null || !player.isAlive();
			});
		}
	}
}
