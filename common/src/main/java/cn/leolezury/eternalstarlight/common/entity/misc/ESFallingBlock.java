package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class ESFallingBlock extends Entity {
    public int duration;

    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(ESFallingBlock.class, EntityDataSerializers.BLOCK_POS);

    private static final EntityDataAccessor<Optional<BlockState>> BLOCK_STATE = SynchedEntityData.defineId(ESFallingBlock.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE);

    public ESFallingBlock(EntityType<ESFallingBlock> type, Level level) {
        super(type, level);
        this.duration = 20;
    }

    public ESFallingBlock(Level level, double x, double y, double z, BlockState state, int duration) {
        this(ESEntities.FALLING_BLOCK.get(), level);
        setBlock(state);
        setPos(x, y + ((1.0F - getBbHeight()) / 2.0F), z);
        setDeltaMovement(Vec3.ZERO);
        this.duration = duration;
        this.xo = x;
        this.yo = y;
        this.zo = z;
        setStartPos(blockPosition());
    }

    public void setStartPos(BlockPos pos) {
        this.entityData.set(DATA_START_POS, pos);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_START_POS, BlockPos.ZERO);
        builder.define(BLOCK_STATE, Optional.of(Blocks.DIRT.defaultBlockState()));
    }

    public BlockState getBlock() {
        Optional<BlockState> bsOp = getEntityData().get(BLOCK_STATE);
        return bsOp.orElse(null);
    }

    public void setBlock(BlockState block) {
        getEntityData().set(BLOCK_STATE, Optional.of(block));
    }

    public void tick() {
        if (!isNoGravity())
            setDeltaMovement(getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        setPos(this.getX() + getDeltaMovement().x, this.getY() + getDeltaMovement().y, this.getZ() + getDeltaMovement().z);
        setDeltaMovement(getDeltaMovement().scale(0.98D));
        for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox())) {
            living.hurt(damageSources().fallingBlock(this), 5);
        }
        if (this.onGround() && this.tickCount > this.duration)
            discard();
        if (this.tickCount > 300)
            discard();
    }

    protected void addAdditionalSaveData(CompoundTag tag) {
        BlockState blockState = getBlock();
        if (blockState != null)
            tag.put("Block", NbtUtils.writeBlockState(blockState));
        tag.putInt("Time", this.duration);
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        Tag blockStateCompound = tag.get("Block");
        if (blockStateCompound != null) {
            BlockState blockState = NbtUtils.readBlockState(level().holderLookup(Registries.BLOCK), (CompoundTag) blockStateCompound);
            setBlock(blockState);
        }
        this.duration = tag.getInt("Time");
    }

    public boolean displayFireAnimation() {
        return false;
    }

    public BlockState getBlockState() {
        Optional<BlockState> optionalBlockState = getEntityData().get(BLOCK_STATE);
        return optionalBlockState.orElse(null);
    }
}
