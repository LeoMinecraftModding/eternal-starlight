package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TangledHatredPart extends Entity {
	private TangledHatred parent;

	public void setParent(TangledHatred parent) {
		this.parent = parent;
	}

	public TangledHatred getParent() {
		return parent;
	}

	public TangledHatredPart(EntityType<? extends Entity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	public void push(Entity entity) {

	}

	@Override
	public void setDeltaMovement(Vec3 vec3) {

	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (parent != null) {
			if (source.getDirectEntity() != null && (source.getEntity() == null || !source.getEntity().getUUID().equals(parent.getUUID()))) {
				return parent.hurt(source, amount);
			}
		}
		return false;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide && tickCount > 10) {
			if (parent == null || parent.isDeadOrDying() || parent.isRemoved()) {
				discard();
			} else {
				boolean hasPart = false;
				for (TangledHatredPart part : parent.parts) {
					if (part.getUUID().equals(getUUID())) {
						hasPart = true;
					}
				}
				if (!hasPart) {
					discard();
				}
			}
		}
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	@Override
	public boolean shouldBeSaved() {
		return false;
	}
}
