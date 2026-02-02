package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Candlash extends Whip {
	public final ConcurrentLinkedQueue<Vec3> tipPositions = new ConcurrentLinkedQueue<>();
	public float lastParticleTick = 0;

	public Candlash(EntityType<? extends Candlash> entityType, Level level) {
		super(entityType, level);
	}

	public Candlash(Level level, Player player, @Nullable ItemStack weapon) {
		super(ESEntities.CANDLASH.get(), level, player, weapon);
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide) {
			Vec3 pos;
			while ((pos = tipPositions.poll()) != null) {
				if (distanceToSqr(pos) < 20 * 20) {
					level().addParticle(ESParticles.AMARAMBER_FLAME.get(), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
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
		return interactionRange + 7;
	}
}
