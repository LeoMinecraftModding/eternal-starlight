package cn.leolezury.eternalstarlight.common.entity.living.boss;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.living.phase.MultiBehaviourUser;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ESBoss extends Monster implements MultiBehaviourUser {
	private static final Music BOSS_DEFAULT_MUSIC = new Music(ESSoundEvents.MUSIC_BOSS.asHolder(), 0, 0, true);
	private final List<ServerPlayer> fightParticipants = new ArrayList<>();

	protected ESBoss(EntityType<? extends Monster> type, Level level) {
		super(type, level);
		if (level.isClientSide) {
			ClientHandlers.BOSSES.add(this);
		}
	}

	protected static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);

	public int getPhase() {
		return entityData.get(PHASE);
	}

	public void setPhase(int phase) {
		entityData.set(PHASE, phase);
	}

	protected static final EntityDataAccessor<Integer> BEHAVIOUR_STATE = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);

	public int getBehaviourState() {
		return entityData.get(BEHAVIOUR_STATE);
	}

	public void setBehaviourState(int behaviourState) {
		entityData.set(BEHAVIOUR_STATE, behaviourState);
	}

	protected static final EntityDataAccessor<Integer> BEHAVIOUR_TICKS = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);

	public int getBehaviourTicks() {
		return entityData.get(BEHAVIOUR_TICKS);
	}

	public void setBehaviourTicks(int behaviourTicks) {
		entityData.set(BEHAVIOUR_TICKS, behaviourTicks);
	}

	protected static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.BOOLEAN);

	public boolean isActivated() {
		return entityData.get(ACTIVATED);
	}

	public void setActivated(boolean activated) {
		entityData.set(ACTIVATED, activated);
	}

	private Vec3 initialPos = Vec3.ZERO;
	private boolean spawned = false;

	public Vec3 getInitialPos() {
		return initialPos;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BEHAVIOUR_STATE, 0)
			.define(BEHAVIOUR_TICKS, 0)
			.define(PHASE, 0)
			.define(ACTIVATED, true);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		initialPos = new Vec3(compoundTag.getDouble("InitialX"), compoundTag.getDouble("InitialY"), compoundTag.getDouble("InitialZ"));
		spawned = compoundTag.getBoolean("Spawned");
		setPhase(compoundTag.getInt("Phase"));
		setActivated(compoundTag.getBoolean("Activated"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putDouble("InitialX", initialPos.x);
		compoundTag.putDouble("InitialY", initialPos.y);
		compoundTag.putDouble("InitialZ", initialPos.z);
		compoundTag.putBoolean("Spawned", spawned);
		compoundTag.putInt("Phase", getPhase());
		compoundTag.putBoolean("Activated", isActivated());
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return super.hurt(source, amount);
		} else if (source.equals(level().damageSources().fall())) {
			return false;
		}
		if (source.getEntity() instanceof ServerPlayer player && !fightParticipants.contains(player)) {
			fightParticipants.add(player);
		}
		return super.hurt(source, Math.min(amount, 20));
	}

	@Override
	public void die(DamageSource source) {
		for (ServerPlayer player : fightParticipants) {
			if (player.isAlive() && player.level().dimension() == level().dimension()) {
				CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger(player, this, source);
			}
		}
		super.die(source);
	}

	@Override
	public boolean removeWhenFarAway(double dist) {
		return false;
	}

	@Override
	public boolean addEffect(MobEffectInstance effectInstance, @Nullable Entity entity) {
		if (!effectInstance.getEffect().value().isBeneficial()) {
			return false;
		}
		return super.addEffect(effectInstance, entity);
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

	public Music getBossMusic() {
		return BOSS_DEFAULT_MUSIC;
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
		for (ServerPlayer player : fightParticipants) {
			if (player.isAlive() && player.level().dimension() == level().dimension()) {
				ItemStack lootBag = new ItemStack(ESItems.LOOT_BAG.get());
				lootBag.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.LOOT_TABLE.get(), new ResourceKeyComponent<>(getBossLootTable())).build());
				ItemEntity item = player.spawnAtLocation(lootBag);
				if (item != null) {
					item.setGlowingTag(true);
					item.setExtendedLifetime();
				}
				dropExtraLoot(player);
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
