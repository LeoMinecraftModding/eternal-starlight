package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ESLootTables {
	public static final ResourceKey<LootTable> BOSS_COMMON = create("bosses/boss_common");
	public static final ResourceKey<LootTable> BOSS_THE_GATEKEEPER = create("bosses/the_gatekeeper");
	public static final ResourceKey<LootTable> BOSS_PERMAFROST = create("bosses/permafrost");
	public static final ResourceKey<LootTable> BOSS_STARLIGHT_GOLEM = create("bosses/starlight_golem");
	public static final ResourceKey<LootTable> BOSS_LUNAR_MONSTROSITY = create("bosses/lunar_monstrosity");

	public static final ResourceKey<LootTable> CHEST_MUSIC_DISCS = create("chests/music_discs");
	public static final ResourceKey<LootTable> CHEST_ACCESSORIES = create("chests/accessories");
	public static final ResourceKey<LootTable> CHEST_GOLEM_FORGE = create("chests/golem_forge");
	public static final ResourceKey<LootTable> CHEST_CURSED_GARDEN = create("chests/cursed_garden");
	public static final ResourceKey<LootTable> CHEST_DUSK_LOCKBOX = create("chests/dusk_lockbox");

	public static final ResourceKey<LootTable> GAMEPLAY_STRANGHOUL_BARTERING = create("gameplay/stranghoul_bartering");
	public static final ResourceKey<LootTable> GAMEPLAY_STARFIRE_BIRD_GIFT = create("gameplay/starfire_bird_gift");
	public static final ResourceKey<LootTable> GAMEPLAY_FISHING = create("gameplay/fishing");
	public static final ResourceKey<LootTable> GAMEPLAY_FISHING_FISH = create("gameplay/fishing/fish");
	public static final ResourceKey<LootTable> GAMEPLAY_FISHING_JUNK = create("gameplay/fishing/junk");
	public static final ResourceKey<LootTable> GAMEPLAY_FISHING_TREASURE = create("gameplay/fishing/treasure");

	public static ResourceKey<LootTable> create(String name) {
		return ResourceKey.create(Registries.LOOT_TABLE, EternalStarlight.id(name));
	}
}
