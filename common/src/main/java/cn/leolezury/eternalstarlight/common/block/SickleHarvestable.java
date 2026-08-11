package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.Optional;

public interface SickleHarvestable {
	Optional<ResourceKey<Item>> getSeed();
}
