package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.*;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemLaserBeam;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.LunarMonstrosityBreath;
import cn.leolezury.eternalstarlight.common.entity.living.AethersentGolem;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import cn.leolezury.eternalstarlight.common.entity.living.animal.*;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.living.monster.*;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.entity.misc.*;
import cn.leolezury.eternalstarlight.common.entity.projectile.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;

public class ESEntities {
	public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(Registries.ENTITY_TYPE, EternalStarlight.ID);
	public static final RegistryObject<EntityType<?>, EntityType<ESFallingBlock>> FALLING_BLOCK = ENTITIES.register(
		"falling_block",
		() -> EntityType.Builder.<ESFallingBlock>of(ESFallingBlock::new, MobCategory.MISC)
			.sized(0.98F, 0.98F)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build("falling_block")
	);
	public static final RegistryObject<EntityType<?>, EntityType<ESPainting>> PAINTING = ENTITIES.register(
		"painting",
		() -> EntityType.Builder.<ESPainting>of(ESPainting::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(10)
			.updateInterval(Integer.MAX_VALUE)
			.build("painting")
	);
	public static final RegistryObject<EntityType<?>, EntityType<AethersentMeteor>> AETHERSENT_METEOR = ENTITIES.register(
		"aethersent_meteor",
		() -> EntityType.Builder.<AethersentMeteor>of(AethersentMeteor::new, MobCategory.MISC)
			.sized(0.98F, 0.98F)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("aethersent_meteor").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<AetherstrikeRocketEntity>> AETHERSTRIKE_ROCKET = ENTITIES.register(
		"aetherstrike_rocket",
		() -> EntityType.Builder.<AetherstrikeRocketEntity>of(AetherstrikeRocketEntity::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(10)
			.build(EternalStarlight.id("aetherstrike_rocket").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ESBoat>> BOAT = ENTITIES.register(
		"boat",
		() -> EntityType.Builder.<ESBoat>of(ESBoat::new, MobCategory.MISC)
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
			.build("boat")
	);
	public static final RegistryObject<EntityType<?>, EntityType<ESChestBoat>> CHEST_BOAT = ENTITIES.register(
		"chest_boat",
		() -> EntityType.Builder.<ESChestBoat>of(ESChestBoat::new, MobCategory.MISC)
			.sized(1.375F, 0.5625F)
			.eyeHeight(0.5625F)
			.clientTrackingRange(10)
			.build("chest_boat")
	);
	public static final RegistryObject<EntityType<?>, EntityType<Boarwarf>> BOARWARF = ENTITIES.register(
		"boarwarf",
		() -> EntityType.Builder.of(Boarwarf::new, MobCategory.CREATURE)
			.sized(0.6F, 1.7F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("boarwarf").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<AstralGolem>> ASTRAL_GOLEM = ENTITIES.register(
		"astral_golem",
		() -> EntityType.Builder.of(AstralGolem::new, MobCategory.CREATURE)
			.sized(0.5F, 1.25F)
			.ridingOffset(-0.625F)
			.build(EternalStarlight.id("astral_golem").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Gleech>> GLEECH = ENTITIES.register(
		"gleech",
		() -> EntityType.Builder.of(Gleech::new, MobCategory.MONSTER)
			.sized(0.5F, 0.4F)
			.eyeHeight(0.13F)
			.passengerAttachments(0.2375F)
			.ridingOffset(-0.0625F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("gleech").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<GleechEgg>> GLEECH_EGG = ENTITIES.register(
		"gleech_egg",
		() -> EntityType.Builder.<GleechEgg>of(GleechEgg::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("gleech_egg").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<LonestarSkeleton>> LONESTAR_SKELETON = ENTITIES.register(
		"lonestar_skeleton",
		() -> EntityType.Builder.of(LonestarSkeleton::new, MobCategory.MONSTER)
			.sized(0.6F, 1.99F)
			.eyeHeight(1.74F)
			.ridingOffset(-0.7F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("lonestar_skeleton").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<NightfallSpider>> NIGHTFALL_SPIDER = ENTITIES.register(
		"nightfall_spider",
		() -> EntityType.Builder.of(NightfallSpider::new, MobCategory.MONSTER)
			.sized(0.75F, 0.75F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("nightfall_spider").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Seeker>> SEEKER = ENTITIES.register(
		"seeker",
		() -> EntityType.Builder.of(Seeker::new, MobCategory.MONSTER)
			.sized(1.0F, 1.0F)
			.clientTrackingRange(8)
			.fireImmune()
			.build(EternalStarlight.id("seeker").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ThirstWalker>> THIRST_WALKER = ENTITIES.register(
		"thirst_walker",
		() -> EntityType.Builder.of(ThirstWalker::new, MobCategory.MONSTER)
			.sized(0.6F, 2.5F)
			.ridingOffset(-0.125F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("thirst_walker").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Creteor>> CRETEOR = ENTITIES.register(
		"creteor",
		() -> EntityType.Builder.of(Creteor::new, MobCategory.MONSTER)
			.sized(0.6F, 1.5F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("creteor").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<TinyCreteor>> TINY_CRETEOR = ENTITIES.register(
		"tiny_creteor",
		() -> EntityType.Builder.of(TinyCreteor::new, MobCategory.MONSTER)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("tiny_creteor").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Stranghoul>> STRANGHOUL = ENTITIES.register(
		"stranghoul",
		() -> EntityType.Builder.of(Stranghoul::new, MobCategory.MONSTER)
			.sized(0.6F, 1.99F)
			.eyeHeight(1.74F)
			.ridingOffset(-0.7F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("stranghoul").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Ent>> ENT = ENTITIES.register(
		"ent",
		() -> EntityType.Builder.of(Ent::new, MobCategory.CREATURE)
			.sized(0.5F, 0.6875F)
			.passengerAttachments(0.35F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("ent").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Ratlin>> RATLIN = ENTITIES.register(
		"ratlin",
		() -> EntityType.Builder.of(Ratlin::new, MobCategory.CREATURE)
			.sized(0.9F, 0.775F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("ratlin").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ZombifiedRatlin>> ZOMBIFIED_RATLIN = ENTITIES.register(
		"zombified_ratlin",
		() -> EntityType.Builder.of(ZombifiedRatlin::new, MobCategory.CREATURE)
			.sized(0.9F, 0.775F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("zombified_ratlin").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ShadowSnail>> SHADOW_SNAIL = ENTITIES.register(
		"shadow_snail",
		() -> EntityType.Builder.of(ShadowSnail::new, MobCategory.CREATURE)
			.sized(0.5F, 0.4F)
			.eyeHeight(0.13F)
			.passengerAttachments(0.2375F)
			.ridingOffset(-0.0625F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("shadow_snail").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Yeti>> YETI = ENTITIES.register(
		"yeti",
		() -> EntityType.Builder.of(Yeti::new, MobCategory.CREATURE)
			.sized(0.9F, 0.9F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("yeti").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<AuroraDeer>> AURORA_DEER = ENTITIES.register(
		"aurora_deer",
		() -> EntityType.Builder.of(AuroraDeer::new, MobCategory.CREATURE)
			.sized(0.9F, 1.75F)
			.passengerAttachments(1F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("aurora_deer").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<CrystallizedMoth>> CRYSTALLIZED_MOTH = ENTITIES.register(
		"crystallized_moth",
		() -> EntityType.Builder.of(CrystallizedMoth::new, MobCategory.MONSTER)
			.sized(0.5F, 0.4375F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("crystallized_moth").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ShimmerLacewing>> SHIMMER_LACEWING = ENTITIES.register(
		"shimmer_lacewing",
		() -> EntityType.Builder.of(ShimmerLacewing::new, MobCategory.AMBIENT)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("shimmer_lacewing").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<StarfireBird>> STARFIRE_BIRD = ENTITIES.register(
		"starfire_bird",
		() -> EntityType.Builder.of(StarfireBird::new, MobCategory.AMBIENT)
			.sized(0.5F, 0.625F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("starfire_bird").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<GrimstoneGolem>> GRIMSTONE_GOLEM = ENTITIES.register(
		"grimstone_golem",
		() -> EntityType.Builder.of(GrimstoneGolem::new, MobCategory.CREATURE)
			.sized(0.5F, 1F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("grimstone_golem").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<AethersentGolem>> AETHERSENT_GOLEM = ENTITIES.register(
		"aethersent_golem",
		() -> EntityType.Builder.of(AethersentGolem::new, MobCategory.CREATURE)
			.sized(0.5F, 1.75F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("aethersent_golem").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Rookfish>> ROOKFISH = ENTITIES.register(
		"rookfish",
		() -> EntityType.Builder.of(Rookfish::new, MobCategory.WATER_CREATURE)
			.sized(0.6F, 0.6F)
			.eyeHeight(0.4F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("rookfish").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Luminofish>> LUMINOFISH = ENTITIES.register(
		"luminofish",
		() -> EntityType.Builder.of(Luminofish::new, MobCategory.WATER_AMBIENT)
			.sized(0.5F, 0.3F)
			.clientTrackingRange(10)
			.build(EternalStarlight.id("luminofish").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Luminaris>> LUMINARIS = ENTITIES.register(
		"luminaris",
		() -> EntityType.Builder.of(Luminaris::new, MobCategory.WATER_AMBIENT)
			.sized(0.5F, 0.3F)
			.clientTrackingRange(10)
			.build(EternalStarlight.id("luminaris").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<TwilightGaze>> TWILIGHT_GAZE = ENTITIES.register(
		"twilight_gaze",
		() -> EntityType.Builder.of(TwilightGaze::new, MobCategory.WATER_CREATURE)
			.sized(0.9F, 0.6F)
			.clientTrackingRange(10)
			.build(EternalStarlight.id("twilight_gaze").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<EyeOfSeeking>> EYE_OF_SEEKING = ENTITIES.register(
		"eye_of_seeking",
		() -> EntityType.Builder.<EyeOfSeeking>of(EyeOfSeeking::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(4)
			.build(EternalStarlight.id("eye_of_seeking").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<CrestEntity>> CREST = ENTITIES.register(
		"crest",
		() -> EntityType.Builder.<CrestEntity>of(CrestEntity::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(4)
			.build(EternalStarlight.id("crest").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<TheGatekeeper>> THE_GATEKEEPER = ENTITIES.register(
		"the_gatekeeper",
		() -> EntityType.Builder.of(TheGatekeeper::new, MobCategory.MONSTER)
			.sized(0.75f, 1.99f)
			.clientTrackingRange(32)
			.eyeHeight(1.74F)
			.fireImmune()
			.build(EternalStarlight.id("the_gatekeeper").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<GatekeeperFireball>> GATEKEEPER_FIREBALL = ENTITIES.register(
		"gatekeeper_fireball",
		() -> EntityType.Builder.<GatekeeperFireball>of(GatekeeperFireball::new, MobCategory.MISC)
			.sized(1.0F, 1.0F)
			.clientTrackingRange(4)
			.updateInterval(1)
			.build(EternalStarlight.id("gatekeeper_fireball").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<StarlightGolem>> STARLIGHT_GOLEM = ENTITIES.register(
		"starlight_golem",
		() -> EntityType.Builder.of(StarlightGolem::new, MobCategory.MONSTER)
			.sized(1.25f, 2.5f)
			.clientTrackingRange(32)
			.fireImmune()
			.build(EternalStarlight.id("starlight_golem").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<GolemLaserBeam>> GOLEM_LASER_BEAM = ENTITIES.register(
		"golem_laser_beam",
		() -> EntityType.Builder.<GolemLaserBeam>of(GolemLaserBeam::new, MobCategory.MISC)
			.sized(0f, 0f)
			.clientTrackingRange(32)
			.build(EternalStarlight.id("golem_laser_beam").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<EnergizedFlame>> ENERGIZED_FLAME = ENTITIES.register(
		"energized_flame",
		() -> EntityType.Builder.of(EnergizedFlame::new, MobCategory.MISC)
			.sized(0f, 0f)
			.build(EternalStarlight.id("energized_flame").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Freeze>> FREEZE = ENTITIES.register(
		"freeze",
		() -> EntityType.Builder.of(Freeze::new, MobCategory.MONSTER)
			.sized(0.5f, 1.25f)
			.fireImmune()
			.build(EternalStarlight.id("freeze").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<FrozenTube>> FROZEN_TUBE = ENTITIES.register(
		"frozen_tube",
		() -> EntityType.Builder.<FrozenTube>of(FrozenTube::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("frozen_tube").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Permafrost>> PERMAFROST = ENTITIES.register(
		"permafrost",
		() -> EntityType.Builder.of(Permafrost::new, MobCategory.MONSTER)
			.sized(0.75f, 2.25f)
			.clientTrackingRange(32)
			.fireImmune()
			.build(EternalStarlight.id("permafrost").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<PermafrostSpit>> PERMAFROST_SPIT = ENTITIES.register(
		"permafrost_spit",
		() -> EntityType.Builder.<PermafrostSpit>of(PermafrostSpit::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("permafrost_spit").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<PermafrostCloud>> PERMAFROST_CLOUD = ENTITIES.register(
		"permafrost_cloud",
		() -> EntityType.Builder.of(PermafrostCloud::new, MobCategory.MISC)
			.sized(3.5f, 0.3f)
			.clientTrackingRange(10)
			.build(EternalStarlight.id("permafrost_cloud").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Coldsnap>> COLDSNAP = ENTITIES.register(
		"coldsnap",
		() -> EntityType.Builder.<Coldsnap>of(Coldsnap::new, MobCategory.MISC)
			.sized(0.0f, 0.0f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("coldsnap").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<LunarMonstrosity>> LUNAR_MONSTROSITY = ENTITIES.register(
		"lunar_monstrosity",
		() -> EntityType.Builder.of(LunarMonstrosity::new, MobCategory.MONSTER)
			.sized(0.9f, 3.5f)
			.clientTrackingRange(32)
			.build(EternalStarlight.id("lunar_monstrosity").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<LunarMonstrosityBreath>> LUNAR_MONSTROSITY_BREATH = ENTITIES.register(
		"lunar_monstrosity_breath",
		() -> EntityType.Builder.<LunarMonstrosityBreath>of(LunarMonstrosityBreath::new, MobCategory.MISC)
			.sized(0f, 0f)
			.clientTrackingRange(32)
			.build(EternalStarlight.id("lunar_monstrosity_breath").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<LunarSpore>> LUNAR_SPORE = ENTITIES.register(
		"lunar_spore",
		() -> EntityType.Builder.<LunarSpore>of(LunarSpore::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(6)
			.updateInterval(1)
			.build(EternalStarlight.id("lunar_spore").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<LunarThorn>> LUNAR_THORN = ENTITIES.register(
		"lunar_thorn",
		() -> EntityType.Builder.of(LunarThorn::new, MobCategory.MISC)
			.sized(0.3f, 1f)
			.build(EternalStarlight.id("lunar_thorn").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<PoisonousCloud>> POISONOUS_CLOUD = ENTITIES.register(
		"poisonous_cloud",
		() -> EntityType.Builder.of(PoisonousCloud::new, MobCategory.MISC)
			.sized(5f, 1f)
			.clientTrackingRange(10)
			.build(EternalStarlight.id("poisonous_cloud").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Tangled>> TANGLED = ENTITIES.register(
		"tangled",
		() -> EntityType.Builder.of(Tangled::new, MobCategory.MONSTER)
			.sized(0.6F, 1.99F)
			.eyeHeight(1.74F)
			.ridingOffset(-0.7F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("tangled").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<TangledSkull>> TANGLED_SKULL = ENTITIES.register(
		"tangled_skull",
		() -> EntityType.Builder.of(TangledSkull::new, MobCategory.MONSTER)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("tangled_skull").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<SolarCreeper>> SOLAR_CREEPER = ENTITIES.register(
		"solar_creeper",
		() -> EntityType.Builder.of(SolarCreeper::new, MobCategory.MONSTER)
			.sized(1.5f, 2.75f)
			.clientTrackingRange(32)
			.fireImmune()
			.build(EternalStarlight.id("solar_creeper").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<TangledHusk>> TANGLED_HUSK = ENTITIES.register(
		"tangled_husk",
		() -> EntityType.Builder.of(TangledHusk::new, MobCategory.MISC)
			.sized(0.6F, 1.8F)
			.eyeHeight(1.62F)
			.vehicleAttachment(Player.DEFAULT_VEHICLE_ATTACHMENT)
			.clientTrackingRange(32)
			.updateInterval(2)
			.build(EternalStarlight.id("tangled_husk").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ThrownShatteredBlade>> SHATTERED_BLADE = ENTITIES.register(
		"shattered_blade",
		() -> EntityType.Builder.<ThrownShatteredBlade>of(ThrownShatteredBlade::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(6)
			.updateInterval(1)
			.build(EternalStarlight.id("shattered_blade").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ThrownMalariteSpear>> MALARITE_SPEAR = ENTITIES.register(
		"malarite_spear",
		() -> EntityType.Builder.<ThrownMalariteSpear>of(ThrownMalariteSpear::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(6)
			.updateInterval(1)
			.build(EternalStarlight.id("malarite_spear").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<TearBomb>> TEAR_BOMB = ENTITIES.register(
		"tear_bomb",
		() -> EntityType.Builder.<TearBomb>of(TearBomb::new, MobCategory.MISC)
			.fireImmune()
			.sized(0.98F, 0.98F)
			.eyeHeight(0.15F)
			.clientTrackingRange(10)
			.updateInterval(10)
			.build(EternalStarlight.id("tear_bomb").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<TearBombMinecart>> TEAR_BOMB_MINECART = ENTITIES.register(
		"tear_bomb_minecart",
		() -> EntityType.Builder.<TearBombMinecart>of(TearBombMinecart::new, MobCategory.MISC)
			.sized(0.98F, 0.7F)
			.passengerAttachments(0.1875F)
			.clientTrackingRange(8)
			.build(EternalStarlight.id("tear_bomb_minecart").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ThrownPungencyFruitSpear>> PUNGENCY_FRUIT_SPEAR = ENTITIES.register(
		"pungency_fruit_spear",
		() -> EntityType.Builder.<ThrownPungencyFruitSpear>of(ThrownPungencyFruitSpear::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(6)
			.updateInterval(1)
			.build(EternalStarlight.id("pungency_fruit_spear").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ThioquartzArrow>> THIOQUARTZ_ARROW = ENTITIES.register(
		"thioquartz_arrow",
		() -> EntityType.Builder.<ThioquartzArrow>of(ThioquartzArrow::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(EternalStarlight.id("thioquartz_arrow").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ThioquartzShard>> THIOQUARTZ_SHARD = ENTITIES.register(
		"thioquartz_shard",
		() -> EntityType.Builder.<ThioquartzShard>of(ThioquartzShard::new, MobCategory.MISC)
			.sized(0.2f, 0.2f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("thioquartz_shard").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<AethersentArrow>> AETHERSENT_ARROW = ENTITIES.register(
		"aethersent_arrow",
		() -> EntityType.Builder.<AethersentArrow>of(AethersentArrow::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(EternalStarlight.id("aethersent_arrow").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<GlaciteArrow>> GLACITE_ARROW = ENTITIES.register(
		"glacite_arrow",
		() -> EntityType.Builder.<GlaciteArrow>of(GlaciteArrow::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(EternalStarlight.id("glacite_arrow").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<MalariteArrow>> MALARITE_ARROW = ENTITIES.register(
		"malarite_arrow",
		() -> EntityType.Builder.<MalariteArrow>of(MalariteArrow::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(EternalStarlight.id("malarite_arrow").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<AmaramberArrow>> AMARAMBER_ARROW = ENTITIES.register(
		"amaramber_arrow",
		() -> EntityType.Builder.<AmaramberArrow>of(AmaramberArrow::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(EternalStarlight.id("amaramber_arrow").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<VoraciousArrow>> VORACIOUS_ARROW = ENTITIES.register(
		"voracious_arrow",
		() -> EntityType.Builder.<VoraciousArrow>of(VoraciousArrow::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(EternalStarlight.id("voracious_arrow").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<AirSacArrow>> AIR_SAC_ARROW = ENTITIES.register(
		"air_sac_arrow",
		() -> EntityType.Builder.<AirSacArrow>of(AirSacArrow::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(EternalStarlight.id("air_sac_arrow").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<SonarBomb>> SONAR_BOMB = ENTITIES.register(
		"sonar_bomb",
		() -> EntityType.Builder.<SonarBomb>of(SonarBomb::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("sonar_bomb").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<AshenSnowball>> ASHEN_SNOWBALL = ENTITIES.register(
		"ashen_snowball",
		() -> EntityType.Builder.<AshenSnowball>of(AshenSnowball::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("ashen_snowball").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<FrozenBomb>> FROZEN_BOMB = ENTITIES.register(
		"frozen_bomb",
		() -> EntityType.Builder.<FrozenBomb>of(FrozenBomb::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("frozen_bomb").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<WiltedPetal>> WILTED_PETAL = ENTITIES.register(
		"wilted_petal",
		() -> EntityType.Builder.<WiltedPetal>of(WiltedPetal::new, MobCategory.MISC)
			.sized(0.2f, 0.2f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("wilted_petal").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ShotSeeds>> SHOT_SEEDS = ENTITIES.register(
		"shot_seeds",
		() -> EntityType.Builder.<ShotSeeds>of(ShotSeeds::new, MobCategory.MISC)
			.sized(0.2f, 0.2f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("shot_seeds").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ThrownStarfire>> STARFIRE = ENTITIES.register(
		"starfire",
		() -> EntityType.Builder.<ThrownStarfire>of(ThrownStarfire::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("starfire").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<Candlash>> CANDLASH = ENTITIES.register(
		"candlash",
		() -> EntityType.Builder.<Candlash>of(Candlash::new, MobCategory.MISC)
			.sized(0.0f, 0.0f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("candlash").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<TentacleSpike>> TENTACLE_SPIKE = ENTITIES.register(
		"tentacle_spike",
		() -> EntityType.Builder.<TentacleSpike>of(TentacleSpike::new, MobCategory.MISC)
			.sized(0.0f, 0.0f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("tentacle_spike").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<EnergySpark>> ENERGY_SPARK = ENTITIES.register(
		"energy_spark",
		() -> EntityType.Builder.<EnergySpark>of(EnergySpark::new, MobCategory.MISC)
			.sized(0.5f, 0.5f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("energy_spark").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<BallLightning>> BALL_LIGHTNING = ENTITIES.register(
		"ball_lightning",
		() -> EntityType.Builder.<BallLightning>of(BallLightning::new, MobCategory.MISC)
			.sized(0.5f, 0.5f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("ball_lightning").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<CrystalCluster>> CRYSTAL_CLUSTER = ENTITIES.register(
		"crystal_cluster",
		() -> EntityType.Builder.of(CrystalCluster::new, MobCategory.MISC)
			.sized(0.75f, 0.75f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("crystal_cluster").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ThrownEnergyBoomerang>> ENERGY_BOOMERANG = ENTITIES.register(
		"energy_boomerang",
		() -> EntityType.Builder.<ThrownEnergyBoomerang>of(ThrownEnergyBoomerang::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(6)
			.updateInterval(1)
			.build(EternalStarlight.id("energy_boomerang").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<SoulitSpectator>> SOULIT_SPECTATOR = ENTITIES.register(
		"soulit_spectator",
		() -> EntityType.Builder.<SoulitSpectator>of(SoulitSpectator::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("soulit_spectator").toString())
	);
	public static final RegistryObject<EntityType<?>, EntityType<ChainOfSouls>> CHAIN_OF_SOULS = ENTITIES.register(
		"chain_of_souls",
		() -> EntityType.Builder.<ChainOfSouls>of(ChainOfSouls::new, MobCategory.MISC)
			.sized(0.3f, 0.3f)
			.clientTrackingRange(10)
			.updateInterval(1)
			.build(EternalStarlight.id("chain_of_souls").toString())
	);

	public static void loadClass() {
	}
}
