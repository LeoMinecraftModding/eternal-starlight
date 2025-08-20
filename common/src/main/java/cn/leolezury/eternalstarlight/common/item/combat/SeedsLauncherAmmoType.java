package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.data.ESSeedsLauncherAmmoTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.Optional;

public record SeedsLauncherAmmoType(Holder<Item> item, float damageMultiplier, float speedMultiplier, float cooldown) {
	public static final Codec<SeedsLauncherAmmoType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(SeedsLauncherAmmoType::item),
		Codec.FLOAT.fieldOf("damage_multiplier").forGetter(SeedsLauncherAmmoType::damageMultiplier),
		Codec.FLOAT.fieldOf("speed_multiplier").forGetter(SeedsLauncherAmmoType::speedMultiplier),
		Codec.FLOAT.fieldOf("cooldown").forGetter(SeedsLauncherAmmoType::cooldown)
	).apply(instance, SeedsLauncherAmmoType::new));

	public static Holder<SeedsLauncherAmmoType> getAmmoType(HolderLookup.Provider registryAccess, Item item) {
		HolderLookup.RegistryLookup<SeedsLauncherAmmoType> registry = registryAccess.lookupOrThrow(ESRegistries.SEEDS_LAUNCHER_AMMO_TYPE);
		Optional<Holder.Reference<SeedsLauncherAmmoType>> optional = registry.listElements().filter(reference -> reference.value().item().value() == item).findFirst().or(() -> registry.get(ESSeedsLauncherAmmoTypes.WHEAT));
		return optional.orElseThrow();
	}

	public int cooldownAsTicks() {
		return (int) (cooldown() * 20);
	}
}
