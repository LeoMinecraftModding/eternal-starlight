package cn.leolezury.eternalstarlight.entity.boss;

import cn.leolezury.eternalstarlight.init.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractSLBoss extends Monster {
    protected AbstractSLBoss(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }
    
    protected static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(AbstractSLBoss.class, EntityDataSerializers.INT);
    public int getPhase() {
        return entityData.get(PHASE);
    }
    public void setPhase(int phase) {
        entityData.set(PHASE, phase);
    }

    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(AbstractSLBoss.class, EntityDataSerializers.INT);
    public int getAttackState() {
        return entityData.get(ATTACK_STATE);
    }
    public void setAttackState(int attackState) {
        entityData.set(ATTACK_STATE, attackState);
    }

    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(AbstractSLBoss.class, EntityDataSerializers.INT);
    public int getAttackTicks() {
        return entityData.get(ATTACK_TICKS);
    }
    public void setAttackTicks(int attackTicks) {
        entityData.set(ATTACK_TICKS, attackTicks);
    }

    protected static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(AbstractSLBoss.class, EntityDataSerializers.BOOLEAN);
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ATTACK_STATE, 0);
        entityData.define(ATTACK_TICKS, 0);
        entityData.define(PHASE, 0);
        entityData.define(ACTIVATED, true);
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
        if (!effectInstance.getEffect().isBeneficial()) {
            return false;
        }
        return super.addEffect(effectInstance, entity);
    }

    public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double r) {
        return level().getEntitiesOfClass(entityClass, getBoundingBox().inflate(r, r, r), e -> e != this && distanceTo(e) <= r + e.getBbWidth() / 2f);
    }

    public boolean canBossMove() {
        return true;
    }

    public void initializeBoss() {
        initialPos = position();
    }

    public ResourceLocation getBossLootTable() {
        ResourceLocation lootTable = null;
        ResourceLocation resourcelocation = ForgeRegistries.ENTITY_TYPES.getKey(getType());

        if (resourcelocation != null) {
            lootTable = resourcelocation.withPrefix("bosses/");
        }

        return lootTable;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingLevel, boolean isPlayer) {
        super.dropCustomDeathLoot(source, lootingLevel, isPlayer);
        ItemStack lootBag = new ItemStack(ItemInit.LOOT_BAG.get());
        lootBag.getOrCreateTag().putString("LootTable", getBossLootTable().toString());
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
