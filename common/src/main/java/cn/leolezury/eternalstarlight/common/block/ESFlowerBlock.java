package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.block.FlowerBlock;

import java.util.Optional;

public class ESFlowerBlock extends FlowerBlock implements SickleHarvestable {
	private final Optional<ResourceKey<Item>> seed;

	public ESFlowerBlock(SuspiciousStewEffects suspiciousStewEffects, Properties properties) {
		super(suspiciousStewEffects, properties);
		this.seed = Optional.empty();
	}

	public ESFlowerBlock(Holder<MobEffect> holder, float f, Properties properties, Optional<ResourceKey<Item>> seed) {
		super(holder, f, properties);
		this.seed = seed;
	}

	public Optional<ResourceKey<Item>> getSeed() {
		return seed;
	}
}
