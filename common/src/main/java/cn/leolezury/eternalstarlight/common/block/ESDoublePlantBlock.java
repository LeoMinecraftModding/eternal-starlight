package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class ESDoublePlantBlock extends DoublePlantOnSandBlock implements SickleHarvestable {
	private final Optional<ResourceKey<Item>> seed;

	public ESDoublePlantBlock(Properties properties, Optional<ResourceKey<Item>> seed) {
		super(properties);
		this.seed = seed;
	}

	public Optional<ResourceKey<Item>> getSeed() {
		return seed;
	}
}
