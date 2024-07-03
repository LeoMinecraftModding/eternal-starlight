package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ESLootTables {
	public static final ResourceKey<LootTable> BOSS_COMMON = create("bosses/boss_common");
	public static final ResourceKey<LootTable> BOSS_STARLIGHT_GOLEM = create("bosses/starlight_golem");
	public static final ResourceKey<LootTable> BOSS_TANGLED_HATRED = create("bosses/tangled_hatred");
	public static final ResourceKey<LootTable> BOSS_LUNAR_MONSTROSITY = create("bosses/lunar_monstrosity");

	public static final ResourceKey<LootTable> GOLEM_FORGE_CHEST = create("chests/golem_forge");
	public static final ResourceKey<LootTable> CURSED_GARDEN_CHEST = create("chests/cursed_garden");

	public static ResourceKey<LootTable> create(String name) {
		return ResourceKey.create(Registries.LOOT_TABLE, EternalStarlight.id(name));
	}
}
