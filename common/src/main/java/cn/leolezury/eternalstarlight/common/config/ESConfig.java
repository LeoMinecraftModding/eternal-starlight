package cn.leolezury.eternalstarlight.common.config;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ESConfig {
	public static final Path CONFIG_PATH = ESPlatform.INSTANCE.getConfigDir().resolve(EternalStarlight.ID + ".json");
	public static ESConfig INSTANCE = new ESConfig();

	public boolean enableDataFixer = false;
	public boolean blockUnknownAttachmentWarning = true;
	public boolean enablePortalShader = true;
	public boolean enableScreenShake = true;
	public boolean enableLootChest = true;
	public boolean enableBossRespawn = true;
	public int bossRespawnCooldown = 36000;
	public boolean spawnInEternalStarlight = false;
	public boolean respawnInEternalStarlight = false;
	public boolean startWithGuidebook = false;
	public float aethersentMeteorDropRate = 0.0005f;
	public boolean aethersentMeteorReplaceBlocks = false;
	public int mobMaxTearyTicks = 200;
	public MobsConfig mobsConfig = new MobsConfig();
	public ItemsConfig itemsConfig = new ItemsConfig();

	public static class MobsConfig {
		public MobConfig boarwarf = new MobConfig(30, 10, true);
		public AttackingMobConfig astralGolem = new AttackingMobConfig(100, 10, 10, 100, true);
		public AttackingMobConfig gleech = new AttackingMobConfig(8, 0, 1, 16, true);
		public AttackingMobConfig lonestarSkeleton = new AttackingMobConfig(20, 0, 3.2, 16, true);
		public AttackingMobConfig nightfallSpider = new AttackingMobConfig(10, 0, 2, 16, true);
		public AttackingMobConfig seeker = new AttackingMobConfig(15, 0, 3, 16, true);
		public AttackingMobConfig thirstWalker = new AttackingMobConfig(40, 0, 4.5, 32, true);
		public CreteorConfig creteor = new CreteorConfig(15, 0, 5, 48, true, 0.7);
		public AttackingMobConfig tinyCreteor = new AttackingMobConfig(5, 0, 2, 48, true);
		public StranghoulConfig stranghoul = new StranghoulConfig(30, 2, 2, 32, true, 8000);
		public MobConfig ent = new MobConfig(10, 0, true);
		public MobConfig ratlin = new MobConfig(15, 0, true);
		public AttackingMobConfig zombifiedRatlin = new AttackingMobConfig(20, 2, 3, 35, true);
		public MobConfig shadowSnail = new MobConfig(8, 6, true);
		public MobConfig yeti = new MobConfig(20, 0, true);
		public AttackingMobConfig auroraDeer = new AttackingMobConfig(20, 0, 3, 16, true);
		public AttackingMobConfig crystallizedMoth = new AttackingMobConfig(20, 0, 1.5, 64, true);
		public MobConfig shimmerLacewing = new MobConfig(5, 0, true);
		public MobConfig starfireBird = new MobConfig(15, 0, true);
		public MobConfig grimstoneGolem = new MobConfig(20, 0, true);
		public MobConfig aethersentGolem = new MobConfig(40, 10, true);
		public AttackingMobConfig luminofish = new AttackingMobConfig(3, 0, 3, 16, true);
		public AttackingMobConfig luminaris = new AttackingMobConfig(3, 0, 3, 64, true);
		public AttackingMobConfig twilightGaze = new AttackingMobConfig(10, 0, 3, 16, true);
		public GatekeeperConfig theGatekeeper = new GatekeeperConfig(175, 15, 1, 200, true, false);
		public BossConfig starlightGolem = new BossConfig(200, 10, 1, 200, true);
		public AttackingMobConfig freeze = new AttackingMobConfig(15, 0, 3, 32, true);
		public BossConfig permafrost = new BossConfig(120, 10, 1, 100, true);
		public BossConfig lunarMonstrosity = new BossConfig(200, 12, 1, 200, true);
		public AttackingMobConfig tangled = new AttackingMobConfig(20, 0, 5, 64, true);
		public AttackingMobConfig tangledSkull = new AttackingMobConfig(1, 0, 3, 64, true);
		public BossConfig solarCreeper = new BossConfig(250, 10, 1, 200, true);

		public record CreteorConfig(double maxHealth, double armor, double attackDamage, double followRange, boolean canSpawn, double spawnChance) {
		}

		public record StranghoulConfig(double maxHealth, double armor, double attackDamage, double followRange, boolean canSpawn, int hiringCooldown) {
		}

		public record GatekeeperConfig(double maxHealth, double armor, double attackDamage, double followRange, boolean canSpawn, boolean canAlwaysHurtWhenFighting) {
		}

		public record BossConfig(double maxHealth, double armor, double attackDamageScale, double followRange, boolean canSpawn) {
		}

		public record AttackingMobConfig(double maxHealth, double armor, double attackDamage, double followRange, boolean canSpawn) {
		}

		public record MobConfig(double maxHealth, double armor, boolean canSpawn) {
		}
	}

	public static class ItemsConfig {
		public double playerAethersentMeteorDamageScale = 1;
		public ChainOfSoulsConfig chainOfSouls = new ChainOfSoulsConfig(64, 2, 0.5);
		public CrystalbornCatalystConfig crystalbornCatalyst = new CrystalbornCatalystConfig(128, 50);
		public AlloyFurnaceConfig alloyFurnace = new AlloyFurnaceConfig(12000, 3);

		public record ChainOfSoulsConfig(double maxRange, double soulAbsorbDamage, double healPercentage) {
		}

		public record CrystalbornCatalystConfig(int maxRange, int energyPerShard) {
		}

		public record AlloyFurnaceConfig(int totalOverheatTicks, int explosionRadius) {
		}
	}

	public static void load() {
		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
		File file = CONFIG_PATH.toFile();
		if (file.exists()) {
			try (FileReader fileReader = new FileReader(file)) {
				try (JsonReader reader = new JsonReader(fileReader)) {
					INSTANCE = gson.fromJson(reader, ESConfig.class);
					write(gson, file, INSTANCE);
				}
			} catch (IOException e) {
				EternalStarlight.LOGGER.error("Failed to load config");
			}
		} else {
			write(gson, file, INSTANCE);
		}
	}

	private static void write(Gson gson, File file, ESConfig config) {
		try (FileWriter writer = new FileWriter(file)) {
			gson.toJson(config, writer);
		} catch (IOException e) {
			EternalStarlight.LOGGER.error("Failed to write config");
		}
	}
}
