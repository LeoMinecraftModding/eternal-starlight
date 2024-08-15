package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.data.ESDataSerializers;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CrestEntity extends Entity {
	private static final EntityDataAccessor<ResourceKey<Crest>> CREST;
	private static final float FLOAT_HEIGHT = 0.1F;
	public static final float EYE_HEIGHT = 0.2125F;
	private int pickupDelay;
	public final float bobOffs;
	private int moveTime;
	@Nullable
	private UUID target;

	public CrestEntity(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.moveTime = 80;
		this.bobOffs = this.random.nextFloat() * 3.1415927F * 2.0F;
		this.setYRot(this.random.nextFloat() * 360.0F);
	}

	public CrestEntity(ResourceKey<Crest> crest, Level level, double x, double y, double z) {
		this(ESEntities.CREST.get(), level);
		this.setPos(x, y, z);
		this.setCrest(crest);
	}

	@Override
	public void tick() {
		if (this.getCrest() == ESCrests.EMPTY) {
			this.discard();
		} else {
			super.tick();
			if (this.pickupDelay > 0 && this.pickupDelay != 32767) {
				this.pickupDelay -= 1;
			}
			if (moveTime > 0) {
				this.moveTime -= 1;
			}

			this.xo = this.getX();
			this.yo = this.getY();
			this.zo = this.getZ();
			Vec3 vec3 = this.getDeltaMovement();

			if (this.level().isClientSide) {
				this.noPhysics = false;
			} else {
				this.noPhysics = !this.level().noCollision(this, this.getBoundingBox().deflate(1.0E-7));
				if (this.noPhysics) {
					this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
				}
			}

			if (!this.onGround() || this.getDeltaMovement().horizontalDistanceSqr() > 9.999999747378752E-6 || (this.tickCount + this.getId()) % 4 == 0) {
				this.setDeltaMovement(RandomSource.create().nextDouble(), RandomSource.create().nextDouble(), RandomSource.create().nextDouble());
				this.move(MoverType.SELF, this.getDeltaMovement());
				float f = 0.98F;
				if (this.onGround()) {
					f = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.98F;
				}

				this.setDeltaMovement(this.getDeltaMovement().multiply(f, 0.98, f));
				if (this.onGround()) {
					Vec3 vec32 = this.getDeltaMovement();
					if (vec32.y < 0.0) {
						this.setDeltaMovement(vec32.multiply(1.0, -0.5, 1.0));
					}
				}
			}

			this.hasImpulse |= this.updateInWaterStateAndDoFluidPushing();
			if (!this.level().isClientSide) {
				double d = this.getDeltaMovement().subtract(vec3).lengthSqr();
				if (d > 0.01) {
					this.hasImpulse = true;
				}
			}
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(CREST, ESCrests.EMPTY);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.putInt("PickupDelay", this.pickupDelay);
		compoundTag.putInt("MoveTime", this.moveTime);
		if (this.getCrest() != ESCrests.EMPTY) {
			compoundTag.put("Crest", Crest.CODEC.encodeStart(level().registryAccess().createSerializationContext(NbtOps.INSTANCE), getCrestSelf()).getOrThrow());
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {
		if (compoundTag.contains("PickupDelay")) {
			this.pickupDelay = compoundTag.getInt("PickupDelay");
		}
		if (compoundTag.contains("MoveTime")) {
			this.moveTime = compoundTag.getInt("MoveTime");
		}
		if (compoundTag.contains("Crest", 10)) {
			CompoundTag tag = compoundTag.getCompound("Crest");
			Crest.CODEC.parse(level().registryAccess().createSerializationContext(NbtOps.INSTANCE), tag).resultOrPartial((string) -> {
//TODO: who can help my lazy?
//				LOGGERH.error("Tried to load invalid crest: '{}'", string);
			}).ifPresent(crest -> this.level().registryAccess().registryOrThrow(ESRegistries.CREST).forEach(instance -> {
                if (instance == getCrestSelf()) {
                    level().registryAccess().registryOrThrow(ESRegistries.CREST).getResourceKey(instance).ifPresent(
						this::setCrest
                    );
                }
            }));
		} else {
			this.setCrest(ESCrests.EMPTY);
		}
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public void playerTouch(Player player) {
		if (!this.level().isClientSide) {
			ResourceKey<Crest> crest = getCrest();
			if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID()))) {
				this.discard();
				var holder = this.registryAccess().registryOrThrow(ESRegistries.CREST).getHolder(crest);
                holder.ifPresent(crestReference -> ESCrestUtil.giveCrest(player, new Crest.Instance(crestReference, 1)));
			}
		}
	}

	public boolean isAttackable() {
		return false;
	}

	public ResourceKey<Crest> getCrest() {
		return this.getEntityData().get(CREST);
	}

	public Crest getCrestSelf() {
		return this.registryAccess().registryOrThrow(ESRegistries.CREST).getOrThrow(getCrest());
	}

	public void setCrest(ResourceKey<Crest> crest) {
		this.getEntityData().set(CREST, crest);
	}

	static {
		CREST = SynchedEntityData.defineId(CrestEntity.class, ESDataSerializers.CREST);
	}
}
