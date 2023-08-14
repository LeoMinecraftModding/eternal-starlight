package cn.leolezury.eternalstarlight.entity.attack.beam;

import cn.leolezury.eternalstarlight.datagen.generator.DamageTypeGenerator;
import cn.leolezury.eternalstarlight.util.ControlledAnimation;
import cn.leolezury.eternalstarlight.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractLaserBeam extends Entity {
    public LivingEntity caster;
    public double endPosX, endPosY, endPosZ;
    public double collidePosX, collidePosY, collidePosZ;
    public double prevCollidePosX, prevCollidePosY, prevCollidePosZ;
    public float renderYaw, renderPitch;

    public ControlledAnimation appear = new ControlledAnimation(3);

    public boolean on = true;

    public Direction blockSide = null;

    private static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(AbstractLaserBeam.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(AbstractLaserBeam.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(AbstractLaserBeam.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(AbstractLaserBeam.class, EntityDataSerializers.INT);

    public float prevYaw;
    public float prevPitch;

    public AbstractLaserBeam(EntityType<? extends AbstractLaserBeam> type, Level world) {
        super(type, world);
        noCulling = true;
    }

    public AbstractLaserBeam(EntityType<? extends AbstractLaserBeam> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration) {
        this(type, world);
        this.caster = caster;
        this.setYaw(yaw);
        this.setPitch(pitch);
        this.setDuration(duration);
        this.setPos(x, y, z);
        this.calculateEndPos();
        if (!world.isClientSide) {
            this.setCasterID(caster.getId());
        }
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public void tick() {
        super.tick();
        prevCollidePosX = collidePosX;
        prevCollidePosY = collidePosY;
        prevCollidePosZ = collidePosZ;
        prevYaw = renderYaw;
        prevPitch = renderPitch;
        xo = getX();
        yo = getY();
        zo = getZ();
        if (tickCount == 1 && level().isClientSide) {
            caster = (LivingEntity) level().getEntity(getCasterID());
        }

        if (!level().isClientSide) {
            update();
        }

        if (caster != null) {
            if (caster instanceof LaserCaster shooter) {
                if (shooter.shouldLaserFollowHead()) {
                    renderYaw = (float) ((caster.yHeadRot + 90.0d) * Math.PI / 180.0d);
                    renderPitch = (float) (-caster.getXRot() * Math.PI / 180.0d);
                } else {
                    renderYaw = getYaw();
                    renderPitch = getPitch();
                }
            } else {
                renderYaw = (float) ((caster.yHeadRot + 90.0d) * Math.PI / 180.0d);
                renderPitch = (float) (-caster.getXRot() * Math.PI / 180.0d);
            }
        }

        if (!on && appear.getTimer() == 0) {
            this.discard() ;
        }
        if (on && tickCount > 20) {
            appear.increaseTimer();
        } else {
            appear.decreaseTimer();
        }

        if (caster == null || !caster.isAlive()) discard();

        if (tickCount > 20) {
            this.calculateEndPos();
            LaserBeamHitResult result = raytraceEntities(level(), position(), new Vec3(endPosX, endPosY, endPosZ));
            List<LivingEntity> hit = result.entities;
            BlockPos hitPos = result.getBlockHit() != null ? result.getBlockHit().getBlockPos() : null;
            if (blockSide != null) {
                spawnExplosionParticles(2);
            }
            if (!level().isClientSide) {
                boolean canDestroy = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), caster);
                if (canDestroy && hitPos != null) {
                    BlockState blockState = level().getBlockState(hitPos);
                    if (blockState.getDestroySpeed(level(), hitPos) != -1) {
                        level().destroyBlock(hitPos, true, this);
                    }
                }
                for (LivingEntity target : hit) {
                    doHurtTarget(target);
                }
            }
        }
        if (tickCount - 20 > getDuration()) {
            on = false;
        }
    }

    private void spawnExplosionParticles(int amount) {
        for (int i = 0; i < amount; i++) {
            final float velocity = 0.1F;
            float yaw = (float) (random.nextFloat() * 2 * Math.PI);
            float motionY = random.nextFloat() * 0.08F;
            float motionX = velocity * Mth.cos(yaw);
            float motionZ = velocity * Mth.sin(yaw);
            level().addParticle(ParticleTypes.FLAME, collidePosX, collidePosY + 0.1, collidePosZ, motionX, motionY, motionZ);
        }
        for (int i = 0; i < amount / 2; i++) {
            level().addParticle(ParticleTypes.LAVA, collidePosX, collidePosY + 0.1, collidePosZ, 0, 0, 0);
        }
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(YAW, 0F);
        getEntityData().define(PITCH, 0F);
        getEntityData().define(DURATION, 0);
        getEntityData().define(CASTER, -1);
    }

    public float getYaw() {
        return getEntityData().get(YAW);
    }

    public void setYaw(float yaw) {
        getEntityData().set(YAW, yaw);
    }

    public float getPitch() {
        return getEntityData().get(PITCH);
    }

    public void setPitch(float pitch) {
        getEntityData().set(PITCH, pitch);
    }

    public int getDuration() {
        return getEntityData().get(DURATION);
    }

    public void setDuration(int duration) {
        getEntityData().set(DURATION, duration);
    }

    public int getCasterID() {
        return getEntityData().get(CASTER);
    }

    public void setCasterID(int id) {
        getEntityData().set(CASTER, id);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public int getRadius() {
        return 30;
    }

    public void doHurtTarget(LivingEntity target) {
        if (target.hurt(DamageTypeGenerator.getIndirectEntityDamageSource(level(), DamageTypeGenerator.LASER, this, caster), 10)) {
            target.setSecondsOnFire(3);
        }
    }

    public float getRotationSpeed() {
        return 2f;
    }

    private void calculateEndPos() {
        double radius = getRadius();
        if (level().isClientSide()) {
            endPosX = getX() + radius * Math.cos(renderYaw) * Math.cos(renderPitch);
            endPosZ = getZ() + radius * Math.sin(renderYaw) * Math.cos(renderPitch);
            endPosY = getY() + radius * Math.sin(renderPitch);
        }
        else {
            endPosX = getX() + radius * Math.cos(getYaw()) * Math.cos(getPitch());
            endPosZ = getZ() + radius * Math.sin(getYaw()) * Math.cos(getPitch());
            endPosY = getY() + radius * Math.sin(getPitch());
        }
    }

    public LaserBeamHitResult raytraceEntities(Level world, Vec3 from, Vec3 to) {
        LaserBeamHitResult result = new LaserBeamHitResult();
        result.setBlockHit(world.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)));
        if (result.blockHit != null) {
            Vec3 hitVec = result.blockHit.getLocation();
            collidePosX = hitVec.x;
            collidePosY = hitVec.y;
            collidePosZ = hitVec.z;
            blockSide = result.blockHit.getDirection();
        } else {
            collidePosX = endPosX;
            collidePosY = endPosY;
            collidePosZ = endPosZ;
            blockSide = null;
        }
        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, new AABB(Math.min(getX(), collidePosX), Math.min(getY(), collidePosY), Math.min(getZ(), collidePosZ), Math.max(getX(), collidePosX), Math.max(getY(), collidePosY), Math.max(getZ(), collidePosZ)).inflate(1));
        for (LivingEntity entity : entities) {
            if (entity == caster) {
                continue;
            }
            float pad = entity.getPickRadius() + 0.5f;
            AABB aabb = entity.getBoundingBox().inflate(pad);
            Optional<Vec3> hit = aabb.clip(from, to);
            if (aabb.contains(from)) {
                result.addEntityHit(entity);
                continue;
            }
            if (hit.isPresent()) {
                result.addEntityHit(entity);
            }
        }
        return result;
    }

    @Override
    public void push(Entity entityIn) {
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 1024;
    }

    private void update() {
        if (caster == null || !caster.isAlive()) return;
        updateRotations();
        updatePosition();
    }

    protected float rotateTowards(float from, float to, float maxAngle) {
        float f = Mth.degreesDifference(from, to);
        float f1 = Mth.clamp(f, -maxAngle, maxAngle);
        return from + f1;
    }

    public void updateRotations() {
        if (caster instanceof LaserCaster shooter && !shooter.shouldLaserFollowHead()) {
            shooter.lookAtLaserEnd(new Vec3(endPosX, endPosY, endPosZ));
            Vec3 wantedPos = shooter.getLaserWantedPos();

            float wantedYaw = MathUtil.positionToYaw(getX(), wantedPos.x, getZ(), wantedPos.z) * Mth.PI / 180f;
            float wantedPitch = MathUtil.positionToPitch(getX(), wantedPos.x, getY(), wantedPos.y, getZ(), wantedPos.z) * Mth.PI / 180f;
            float currentYaw = getYaw();
            float currentPitch = getPitch();
            setYaw(rotateTowards((float) (currentYaw / Math.PI * 180f), (float) (wantedYaw / Math.PI * 180f), getRotationSpeed()) * Mth.PI / 180f);
            setPitch(rotateTowards((float) (currentPitch / Math.PI * 180f), (float) (wantedPitch / Math.PI * 180f), getRotationSpeed()) * Mth.PI / 180f);
        } else {
            this.setYaw((float) ((caster.yHeadRot + 90) * Math.PI / 180f));
            this.setPitch((float) (-caster.getXRot() * Math.PI / 180f));
        }
    }

    public void updatePosition() {
        this.setPos(caster.position());
    }

    public static class LaserBeamHitResult {
        private BlockHitResult blockHit;

        private final List<LivingEntity> entities = new ArrayList<>();

        public BlockHitResult getBlockHit() {
            return blockHit;
        }

        public void setBlockHit(HitResult rayTraceResult) {
            if (rayTraceResult.getType() == HitResult.Type.BLOCK)
                this.blockHit = (BlockHitResult) rayTraceResult;
        }

        public void addEntityHit(LivingEntity entity) {
            entities.add(entity);
        }
    }
}