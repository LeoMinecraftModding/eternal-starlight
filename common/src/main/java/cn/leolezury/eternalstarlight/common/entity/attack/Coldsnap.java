package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Coldsnap extends Whip {
	public final ConcurrentLinkedQueue<Vec3> tipPositions = new ConcurrentLinkedQueue<>();
	private Vec3 lastParticlePos;
	public float lastParticleTick = 0;

	private boolean cloudSpawned = false;

	public Coldsnap(EntityType<? extends Coldsnap> entityType, Level level) {
		super(entityType, level);
	}

	public Coldsnap(Level level, Player player, @Nullable ItemStack weapon, float damageScale) {
		super(ESEntities.COLDSNAP.get(), level, player, weapon, damageScale);
	}

	public boolean isCloudSpawned() {
		return cloudSpawned;
	}

	public void setCloudSpawned() {
		this.cloudSpawned = true;
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide) {
			Vec3 pos;
			while ((pos = tipPositions.poll()) != null) {
				if (distanceToSqr(pos) < 20 * 20) {
					if (getSpawnedTicks() > 1) {
						level().addParticle(ParticleTypes.SNOWFLAKE, pos.x, pos.y, pos.z, (getRandom().nextDouble() - 0.5) * 0.2, 0.0, (getRandom().nextDouble() - 0.5) * 0.2);
						if (lastParticlePos != null) {
							int count = (int) (lastParticlePos.distanceTo(pos) / 0.4);
							if (count > 0 && count < 20) {
								for (int i = 0; i < count; i++) {
									Vec3 extraPos = ESMathUtil.lerpVec((float) (i + 1) / (count + 1), lastParticlePos, pos);
									level().addParticle(ParticleTypes.SNOWFLAKE, extraPos.x, extraPos.y, extraPos.z, (getRandom().nextDouble() - 0.5) * 0.2, 0.0, (getRandom().nextDouble() - 0.5) * 0.2);
								}
							}
						}
					}
					lastParticlePos = pos;
				}
			}
		}
	}

	@Override
	public int getLifespan() {
		return 10;
	}

	@Override
	public float getWhipRange(float interactionRange) {
		return interactionRange + 2.5F;
	}
}
