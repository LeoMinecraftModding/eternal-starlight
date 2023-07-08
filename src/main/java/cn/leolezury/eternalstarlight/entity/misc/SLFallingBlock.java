package cn.leolezury.eternalstarlight.entity.misc;

import cn.leolezury.eternalstarlight.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.Optional;

public class SLFallingBlock extends Entity {
    public int duration;

    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(SLFallingBlock.class, EntityDataSerializers.BLOCK_POS);

    private static final EntityDataAccessor<Optional<BlockState>> BLOCK_STATE = SynchedEntityData.defineId(SLFallingBlock.class, EntityDataSerializers.OPTIONAL_BLOCK_STATE);

    public SLFallingBlock(EntityType<SLFallingBlock> type, Level level) {
        super(type, level);
        this.duration = 20;
    }

    public SLFallingBlock(Level p_31953_, double p_31954_, double p_31955_, double p_31956_, BlockState p_31957_, int duration) {
        this(EntityInit.FALLING_BLOCK.get(), p_31953_);
        setBlock(p_31957_);
        setPos(p_31954_, p_31955_ + ((1.0F - getBbHeight()) / 2.0F), p_31956_);
        setDeltaMovement(Vec3.ZERO);
        this.duration = duration;
        this.xo = p_31954_;
        this.yo = p_31955_;
        this.zo = p_31956_;
        setStartPos(blockPosition());
    }

    public void setStartPos(BlockPos p_31960_) {
        this.entityData.set(DATA_START_POS, p_31960_);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_START_POS, BlockPos.ZERO);
        getEntityData().define(BLOCK_STATE, Optional.of(Blocks.DIRT.defaultBlockState()));
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
        move(MoverType.SELF, getDeltaMovement());
        setDeltaMovement(getDeltaMovement().scale(0.98D));
        if (this.onGround() && this.tickCount > this.duration)
            discard();
        if (this.tickCount > 300)
            discard();
    }

    protected void addAdditionalSaveData(CompoundTag p_31973_) {
        BlockState blockState = getBlock();
        if (blockState != null)
            p_31973_.put("Block", NbtUtils.writeBlockState(blockState));
        p_31973_.putInt("Time", this.duration);
    }

    protected void readAdditionalSaveData(CompoundTag p_31964_) {
        Tag blockStateCompound = p_31964_.get("Block");
        if (blockStateCompound != null) {
            BlockState blockState = NbtUtils.readBlockState(level().holderLookup(Registries.BLOCK), (CompoundTag) blockStateCompound);
            setBlock(blockState);
        }
        this.duration = p_31964_.getInt("Time");
    }

    public boolean displayFireAnimation() {
        return false;
    }

    public BlockState getBlockState() {
        Optional<BlockState> bsOp = getEntityData().get(BLOCK_STATE);
        return bsOp.orElse(null);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
