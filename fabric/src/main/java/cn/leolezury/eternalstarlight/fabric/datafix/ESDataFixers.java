package cn.leolezury.eternalstarlight.fabric.datafix;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.BlockRenameFix;
import net.minecraft.util.datafix.fixes.ItemRenameFix;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ESDataFixers {
	public static void addFixers(DataFixerBuilder builder) {
		Schema schema = builder.addSchema(SharedConstants.getCurrentVersion().getDataVersion().getVersion(), NamespacedSchema::new);
		builder.addFixer(BlockRenameFix.create(schema, "Rename swamp_silver in block ids to deepsilver", id -> id.startsWith(EternalStarlight.ID + ":") ? id.replace("swamp_silver_ore", "nightfall_mud_deepsilver_ore").replace("swamp_silver", "deepsilver") : id));
		builder.addFixer(ItemRenameFix.create(schema, "Rename swamp_silver in item ids to deepsilver", id -> id.startsWith(EternalStarlight.ID + ":") ? id.replace("swamp_silver_ore", "nightfall_mud_deepsilver_ore").replace("swamp_silver", "deepsilver") : id));
		builder.addFixer(BlockRenameFix.create(schema, "Rename atalphaite in block ids to starcore", id -> id.startsWith(EternalStarlight.ID + ":") ? id.replace("atalphaite", "starcore") : id));
		builder.addFixer(ItemRenameFix.create(schema, "Rename atalphaite in item ids to starcore", id -> id.startsWith(EternalStarlight.ID + ":") ? id.replace("atalphaite", "starcore") : id));
		builder.addFixer(ItemRenameFix.create(schema, "Rename trapped_soul to soul_dew", id -> id.equals(EternalStarlight.id("trapped_soul").toString()) ? EternalStarlight.id("soul_dew").toString() : id));
		builder.addFixer(BlockRenameFix.create(schema, "Rename starlight_mangrove in block ids to banyin", id -> id.startsWith(EternalStarlight.ID + ":") ? id.replace("starlight_mangrove", "banyin") : id));
		builder.addFixer(ItemRenameFix.create(schema, "Rename starlight_mangrove in item ids to banyin", id -> id.startsWith(EternalStarlight.ID + ":") ? id.replace("starlight_mangrove", "banyin") : id));
		builder.addFixer(BlockRenameFix.create(schema, "Rename dusted_slag in block ids to dimslag", id -> id.startsWith(EternalStarlight.ID + ":") ? id.replace("dusted_slag", "dimslag") : id));
		builder.addFixer(ItemRenameFix.create(schema, "Rename dusted_slag in item ids to dimslag", id -> id.startsWith(EternalStarlight.ID + ":") ? id.replace("dusted_slag", "dimslag") : id));
		builder.addFixer(BlockRenameFix.create(schema, "Remove removed block ids", id -> switch (id) {
			case EternalStarlight.ID + ":enchanted_grimstone_bricks", EternalStarlight.ID + ":stellar_rack" -> ResourceLocation.withDefaultNamespace("air").toString();
			default -> id;
		}));
		builder.addFixer(ItemRenameFix.create(schema, "Remove removed item ids", id -> switch (id) {
			case EternalStarlight.ID + ":enchanted_grimstone_bricks", EternalStarlight.ID + ":stellar_rack", EternalStarlight.ID + ":mana_crystal", EternalStarlight.ID + ":mana_crystal_shard", EternalStarlight.ID + ":terra_crystal", EternalStarlight.ID + ":wind_crystal", EternalStarlight.ID + ":water_crystal", EternalStarlight.ID + ":lunar_crystal", EternalStarlight.ID + ":blaze_crystal", EternalStarlight.ID + ":light_crystal" -> "minecraft:air";
			default -> id;
		}));
	}
}
