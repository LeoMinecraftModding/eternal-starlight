package cn.leolezury.eternalstarlight.common.entity.living.boss;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviorUser;
import cn.leolezury.eternalstarlight.common.item.component.ResourceKeyComponent;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ESBoss extends Monster implements MultiBehaviorUser {
	private static final String TAG_INITIAL_X = "initial_x";
	private static final String TAG_INITIAL_Y = "initial_y";
	private static final String TAG_INITIAL_Z = "initial_z";
	private static final String TAG_SPAWNED = "spawned";
	private static final String TAG_PHASE = "phase";
	private static final String TAG_ACTIVATED = "activated";

	protected final List<String> fightParticipants = new ArrayList<>();

	protected ESBoss(EntityType<? extends Monster> type, Level level) {
		super(type, level);
		if (level.isClientSide) {
			ClientHandlers.BOSSES.add(this);
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

	private Vec3 initialPos = Vec3.ZERO;
	private boolean spawned = false;

	public Vec3 getInitialPos() {
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

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		initialPos = new Vec3(compoundTag.getDouble(TAG_INITIAL_X), compoundTag.getDouble(TAG_INITIAL_Y), compoundTag.getDouble(TAG_INITIAL_Z));
		spawned = compoundTag.getBoolean(TAG_SPAWNED);
		setPhase(compoundTag.getInt(TAG_PHASE));
		if (compoundTag.contains(TAG_ACTIVATED, CompoundTag.TAG_INT)) {
			setActivated(compoundTag.getBoolean(TAG_ACTIVATED));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putDouble(TAG_INITIAL_X, initialPos.x);
		compoundTag.putDouble(TAG_INITIAL_Y, initialPos.y);
		compoundTag.putDouble(TAG_INITIAL_Z, initialPos.z);
		compoundTag.putBoolean(TAG_SPAWNED, spawned);
		compoundTag.putInt(TAG_PHASE, getPhase());
		compoundTag.putBoolean(TAG_ACTIVATED, isActivated());
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return super.hurt(source, amount);
		} else if (source.equals(level().damageSources().fall())) {
			return false;
		}
		if (source.getEntity() instanceof ServerPlayer player && !fightParticipants.contains(player.getName().getString())) {
			fightParticipants.add(player.getName().getString());
		}
		return super.hurt(source, Math.min(amount, 20));
	}

	@Override
	public void die(DamageSource source) {
		if (!level().isClientSide && level().getServer() instanceof MinecraftServer server) {
			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				for (String name : fightParticipants) {
					if (player.getName().getString().equals(name) && player.isAlive() && player.level().dimension() == level().dimension()) {
						CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger(player, this, source);
					}
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
	public boolean addEffect(MobEffectInstance effectInstance, @Nullable Entity entity) {
		if (!effectInstance.getEffect().value().isBeneficial()) {
			return false;
		}
		return super.addEffect(effectInstance, entity);
	}

	@Override
	public boolean startRiding(Entity entity, boolean bl) {
		return false;
	}

	public boolean canBossMove() {
		return true;
	}

	public void initializeBoss() {
		initialPos = position();
	}

	public boolean shouldPlayBossMusic() {
		return isAlive();
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

	@Override
	protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
		super.dropCustomDeathLoot(serverLevel, damageSource, bl);
		if (!level().isClientSide) {
			for (Player player : level().players()) {
				if (player instanceof ServerPlayer serverPlayer) {
					if (fightParticipants.stream().anyMatch(s -> s.equals(player.getName().getString())) && player.isAlive()) {
						ItemStack lootBag = new ItemStack(ESItems.LOOT_BAG.get());
						lootBag.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.LOOT_TABLE.get(), new ResourceKeyComponent<>(getBossLootTable())).build());
						ItemEntity item = player.spawnAtLocation(lootBag);
						if (item != null) {
							item.setGlowingTag(true);
							item.setExtendedLifetime();
						}
						dropExtraLoot(serverPlayer);
					}
				}
			}
		}
	}

	public void dropExtraLoot(ServerPlayer player) {

	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			if (!spawned) {
				initializeBoss();
				spawned = true;
			}
			if (!canBossMove()) {
				setPos(initialPos.x, position().y, initialPos.z);
			}
		}
	}
}
