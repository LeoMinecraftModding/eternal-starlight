package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class ThioquartzShard extends ThrowableProjectile {
	public float oSpin, spin;

	public ThioquartzShard(EntityType<? extends ThioquartzShard> entityType, Level level) {
		super(entityType, level);
	}

	public ThioquartzShard(Level level, LivingEntity livingEntity) {
		super(ESEntities.THIOQUARTZ_SHARD.get(), livingEntity, level);
	}

	public ThioquartzShard(Level level, double x, double y, double z) {
		super(ESEntities.THIOQUARTZ_SHARD.get(), x, y, z, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	@Override
	public void tick() {
		super.tick();
		if (tickCount > 80) {
			discard();
		}
		oSpin = spin;
		spin += Mth.PI * 0.06f;
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		switch (hitResult.getDirection().getAxis()) {
			case X -> setDeltaMovement(getDeltaMovement().multiply(-0.5, 1, 1));
			case Y -> setDeltaMovement(getDeltaMovement().multiply(1, -0.5, 1));
			case Z -> setDeltaMovement(getDeltaMovement().multiply(1, 1, -0.5));
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		if (hitResult.getEntity() != getOwner()) {
			if (hitResult.getEntity().hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.ETHER, this, getOwner()), 4) && hitResult.getEntity() instanceof LivingEntity livingEntity) {
				playSound(SoundEvents.GLASS_BREAK);
				int inEtherTicks = ESDataAttachments.IN_ETHER_TICKS.getData(livingEntity);
				if (inEtherTicks < 400) {
					ESDataAttachments.IN_ETHER_TICKS.setData(livingEntity, Math.min(inEtherTicks + 200, 400));
				}
			}
		}
		discard();
	}
}
