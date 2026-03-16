package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TentacleSpike extends Whip {
	public TentacleSpike(EntityType<? extends TentacleSpike> entityType, Level level) {
		super(entityType, level);
	}

	public TentacleSpike(Level level, Player player, @Nullable ItemStack weapon, float damageScale) {
		super(ESEntities.TENTACLE_SPIKE.get(), level, player, weapon, damageScale);
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
