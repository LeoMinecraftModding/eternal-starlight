package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.init.ESEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CameraShake extends Entity {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(CameraShake.class, EntityDataSerializers.FLOAT);
    public float getRadius() {
        return getEntityData().get(RADIUS);
    }
    public void setRadius(float radius) {
        getEntityData().set(RADIUS, radius);
    }
    private static final EntityDataAccessor<Float> MAGNITUDE = SynchedEntityData.defineId(CameraShake.class, EntityDataSerializers.FLOAT);
    public float getMagnitude() {
        return getEntityData().get(MAGNITUDE);
    }
    public void setMagnitude(float magnitude) {
        getEntityData().set(MAGNITUDE, magnitude);
    }
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(CameraShake.class, EntityDataSerializers.INT);
    public int getDuration() {
        return getEntityData().get(DURATION);
    }
    public void setDuration(int duration) {
        getEntityData().set(DURATION, duration);
    }
    private static final EntityDataAccessor<Integer> FADE_DURATION = SynchedEntityData.defineId(CameraShake.class, EntityDataSerializers.INT);
    public int getFadeDuration() {
        return getEntityData().get(FADE_DURATION);
    }
    public void setFadeDuration(int fadeDuration) {
        getEntityData().set(FADE_DURATION, fadeDuration);
    }

    public CameraShake(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(RADIUS, 0f);
        getEntityData().define(MAGNITUDE, 0f);
        getEntityData().define(DURATION, 0);
        getEntityData().define(FADE_DURATION, 0);
    }

    public CameraShake(Level level, Vec3 pos, float radius, float magnitude, int duration, int fadeDuration) {
        super(ESEntities.CAMERA_SHAKE.get(), level);
        setRadius(radius);
        setMagnitude(magnitude);
        setDuration(duration);
        setFadeDuration(fadeDuration);
        setPos(pos.x, pos.y, pos.z);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        setRadius(compoundTag.getFloat("Radius"));
        setMagnitude(compoundTag.getFloat("Magnitude"));
        setDuration(compoundTag.getInt("Duration"));
        setFadeDuration(compoundTag.getInt("FadeDuration"));
        tickCount = compoundTag.getInt("SpawnedTicks");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putFloat("Radius", getRadius());
        compoundTag.putFloat("Magnitude", getMagnitude());
        compoundTag.putInt("Duration", getDuration());
        compoundTag.putInt("FadeDuration", getFadeDuration());
        compoundTag.putInt("SpawnedTicks", tickCount);
    }

    @Environment(EnvType.CLIENT)
    public float getShakeAmount(Player player, float partialTicks) {
        float dist = (float) position().distanceTo(player.getEyePosition());
        if (dist > getRadius()) {
            return 0;
        }
        float ticks = tickCount + partialTicks;
        float fadeFactor = 1f - (ticks - getDuration()) / getFadeDuration();
        float distFactor =  1f - dist / getRadius();
        return (float) ((tickCount <= getDuration() ? getMagnitude() : (float) (getMagnitude() * Math.pow(fadeFactor, 2))) * Math.pow(distFactor, 2));
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount > getDuration() + getFadeDuration()) {
            discard();
        }
    }

    public static void createCameraShake(Level level, Vec3 pos, float radius, float magnitude, int duration, int fadeDuration) {
        if (!level.isClientSide) {
            CameraShake cameraShake = new CameraShake(level, pos, radius, magnitude, duration, fadeDuration);
            level.addFreshEntity(cameraShake);
        }
    }
}

