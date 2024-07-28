package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.network.UpdateCameraPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SoulitSpectator extends ThrowableItemProjectile {
	private static final String TAG_NO_MOVEMENT = "no_movement";

	private static final TicketType<ChunkPos> CHUNK_LOADING_TICKET_TYPE = TicketType.create(EternalStarlight.ID + ":soulit_spectator", Comparator.comparingLong(ChunkPos::toLong));

	private boolean noMovement;
	private final List<ChunkPos> loadedChunks = new ArrayList<>();

	public SoulitSpectator(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public SoulitSpectator(Level level, LivingEntity livingEntity) {
		super(ESEntities.SOULIT_SPECTATOR.get(), livingEntity, level);
	}

	public SoulitSpectator(Level level, double x, double y, double z) {
		super(ESEntities.SOULIT_SPECTATOR.get(), x, y, z, level);
	}

	@Override
	public void tick() {
		setNoGravity(true);
		super.tick();
		if (!level().isClientSide && level() instanceof ServerLevel serverLevel) {
			if (!loadedChunks.contains(chunkPosition())) {
				// don't know if it really works
				serverLevel.getChunkSource().addRegionTicket(CHUNK_LOADING_TICKET_TYPE, chunkPosition(), 3, chunkPosition());
				loadedChunks.add(chunkPosition());
			}
			if (tickCount == 40 && getOwner() instanceof ServerPlayer serverPlayer) {
				ESPlatform.INSTANCE.sendToClient(serverPlayer, new UpdateCameraPacket(getId()));
				serverLevel.sendParticles(ParticleTypes.SOUL, getX(), getY(), getZ(), 5, 0.3, 0.3, 0.3, 0);
			}
			if (tickCount > 140) {
				noMovement = true;
			}
			if (tickCount > 200) {
				for (ChunkPos chunkPos : loadedChunks) {
					serverLevel.getChunkSource().removeRegionTicket(CHUNK_LOADING_TICKET_TYPE, chunkPos, 3, chunkPos);
				}
				if (getOwner() instanceof ServerPlayer serverPlayer) {
					ESPlatform.INSTANCE.sendToClient(serverPlayer, new UpdateCameraPacket(-1));
				}
				serverLevel.sendParticles(ParticleTypes.SOUL, getX(), getY(), getZ(), 5, 0.3, 0.3, 0.3, 0);
				discard();
			}
		}
	}

	@Override
	public float getViewXRot(float partialTicks) {
		if (getOwner() instanceof LivingEntity livingEntity) {
			return livingEntity.getViewXRot(partialTicks);
		}
		return super.getViewXRot(partialTicks);
	}

	@Override
	public float getViewYRot(float partialTicks) {
		if (getOwner() instanceof LivingEntity livingEntity) {
			return livingEntity.getViewYRot(partialTicks);
		}
		return super.getViewYRot(partialTicks);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		noMovement = true;
	}

	@Override
	public void setDeltaMovement(Vec3 vec3) {
		super.setDeltaMovement(noMovement ? Vec3.ZERO : vec3);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		noMovement = compoundTag.getBoolean(TAG_NO_MOVEMENT);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_NO_MOVEMENT, noMovement);
	}

	@Override
	protected @NotNull Item getDefaultItem() {
		return ESItems.SOULIT_SPECTATOR.get();
	}
}
