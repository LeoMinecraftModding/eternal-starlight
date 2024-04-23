package cn.leolezury.eternalstarlight.common.entity.living.boss;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.item.component.ResourceKeyComponent;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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

public class ESBoss extends Monster implements MultiPhaseAttacker {
    private static final Music BOSS_DEFAULT_MUSIC = new Music(ESSoundEvents.MUSIC_BOSS.asHolder(), 0, 0, true);

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

    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);
    public int getAttackState() {
        return entityData.get(ATTACK_STATE);
    }
    public void setAttackState(int attackState) {
        entityData.set(ATTACK_STATE, attackState);
    }

    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(ESBoss.class, EntityDataSerializers.INT);
    public int getAttackTicks() {
        return entityData.get(ATTACK_TICKS);
    }
    public void setAttackTicks(int attackTicks) {
        entityData.set(ATTACK_TICKS, attackTicks);
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
        builder.define(ATTACK_STATE, 0);
        builder.define(ATTACK_TICKS, 0);
        builder.define(PHASE, 0);
        builder.define(ACTIVATED, true);
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
        if (source.is(DamageTypes.GENERIC_KILL)) {
            return super.hurt(source, amount);
        } else if (source.equals(level().damageSources().fall())) {
            return false;
        }
        return super.hurt(source, Math.min(amount, 20));
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
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean isPlayer) {
        super.dropCustomDeathLoot(source, lootingLevel, isPlayer);
        ItemStack lootBag = new ItemStack(ESItems.LOOT_BAG.get());
        lootBag.applyComponentsAndValidate(DataComponentPatch.builder().set(ESDataComponents.LOOT_TABLE.get(), new ResourceKeyComponent<>(getBossLootTable())).build());
        ItemEntity item = spawnAtLocation(lootBag);
        if (item != null) {
            item.setGlowingTag(true);
            item.setExtendedLifetime();
        }
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
