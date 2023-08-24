package cn.leolezury.eternalstarlight.entity.misc;

import cn.leolezury.eternalstarlight.init.EntityInit;
import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.init.SoundEventInit;
import cn.leolezury.eternalstarlight.platform.ESPlatform;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EyeOfSeeking extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(EyeOfSeeking.class, EntityDataSerializers.ITEM_STACK);
    private double tx;
    private double ty;
    private double tz;
    private int life;

    public EyeOfSeeking(EntityType<? extends EyeOfSeeking> p_36957_, Level p_36958_) {
        super(p_36957_, p_36958_);
    }

    public EyeOfSeeking(Level p_36960_, double p_36961_, double p_36962_, double p_36963_) {
        this(EntityInit.EYE_OF_SEEKING.get(), p_36960_);
        this.setPos(p_36961_, p_36962_, p_36963_);
    }

    public void setItem(ItemStack p_36973_) {
        if (!p_36973_.is(ItemInit.SEEKING_EYE.get()) || p_36973_.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, Util.make(p_36973_.copy(), (p_36978_) -> {
                p_36978_.setCount(1);
            }));
        }
    }

    private ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(ItemInit.SEEKING_EYE.get()) : itemstack;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public boolean shouldRenderAtSqrDistance(double p_36966_) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return p_36966_ < d0 * d0;
    }

    public void signalTo(BlockPos p_36968_) {
        double d0 = (double)p_36968_.getX();
        int i = p_36968_.getY();
        double d1 = (double)p_36968_.getZ();
        double d2 = d0 - this.getX();
        double d3 = d1 - this.getZ();
        double d4 = Math.sqrt(d2 * d2 + d3 * d3);
        if (d4 > 12.0D) {
            this.tx = this.getX() + d2 / d4 * 12.0D;
            this.tz = this.getZ() + d3 / d4 * 12.0D;
            this.ty = this.getY() + 8.0D;
        } else {
            this.tx = d0;
            this.ty = (double)i;
            this.tz = d1;
        }

        this.life = 0;
    }

    public void lerpMotion(double p_36984_, double p_36985_, double p_36986_) {
        this.setDeltaMovement(p_36984_, p_36985_, p_36986_);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = Math.sqrt(p_36984_ * p_36984_ + p_36986_ * p_36986_);
            this.setYRot((float)(Mth.atan2(p_36984_, p_36986_) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(p_36985_, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

    }

    protected static float lerpRotation(float p_37274_, float p_37275_) {
        while(p_37275_ - p_37274_ < -180.0F) {
            p_37274_ -= 360.0F;
        }

        while(p_37275_ - p_37274_ >= 180.0F) {
            p_37274_ += 360.0F;
        }

        return Mth.lerp(0.2F, p_37274_, p_37275_);
    }

    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        double d3 = vec3.horizontalDistance();
        this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d3) * (double)(180F / (float)Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI))));
        if (!this.level().isClientSide) {
            double d4 = this.tx - d0;
            double d5 = this.tz - d2;
            float f = (float)Math.sqrt(d4 * d4 + d5 * d5);
            float f1 = (float)Mth.atan2(d5, d4);
            double d6 = Mth.lerp(0.0025D, d3, (double)f);
            double d7 = vec3.y;
            if (f < 1.0F) {
                d6 *= 0.8D;
                d7 *= 0.8D;
            }

            int j = this.getY() < this.ty ? 1 : -1;
            vec3 = new Vec3(Math.cos((double)f1) * d6, d7 + ((double)j - d7) * (double)0.015F, Math.sin((double)f1) * d6);
            this.setDeltaMovement(vec3);
        }

        float f2 = 0.25F;
        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25D, d1 - vec3.y * 0.25D, d2 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }
        } else {
            this.level().addParticle(ParticleTypes.PORTAL, d0 - vec3.x * 0.25D + this.random.nextDouble() * 0.6D - 0.3D, d1 - vec3.y * 0.25D - 0.5D, d2 - vec3.z * 0.25D + this.random.nextDouble() * 0.6D - 0.3D, vec3.x, vec3.y, vec3.z);
        }

        if (!this.level().isClientSide) {
            this.setPos(d0, d1, d2);
            ++this.life;
            if (this.life > 80 && !this.level().isClientSide) {
                this.playSound(SoundEventInit.SEEKING_EYE_DEATH.get(), 1.0F, 1.0F);
                this.discard();
                this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
            }
        } else {
            this.setPosRaw(d0, d1, d2);
        }

    }

    public void addAdditionalSaveData(CompoundTag p_36975_) {
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            p_36975_.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    public void readAdditionalSaveData(CompoundTag p_36970_) {
        ItemStack itemstack = ItemStack.of(p_36970_.getCompound("Item"));
        this.setItem(itemstack);
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    public boolean isAttackable() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        Packet<ClientGamePacketListener> packet = ESPlatform.INSTANCE.getAddEntityPacket(this);
        return packet == null ? super.getAddEntityPacket() : packet;
    }
}
