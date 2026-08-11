package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class ESShortBushBlock extends ShortBushBlock implements SickleHarvestable {
	private final Optional<ResourceKey<Item>> seed;

	public ESShortBushBlock(Properties properties, Optional<ResourceKey<Item>> seed) {
		super(properties);
		this.seed = seed;
	}

	public ESShortBushBlock(int height,Properties properties, Optional<ResourceKey<Item>> seed) {
		super(height, properties);
		this.seed = seed;
	}

	public Optional<ResourceKey<Item>> getSeed() {
		return seed;
	}
}
