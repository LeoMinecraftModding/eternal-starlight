package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.attack.LunarThorn;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemLaserBeam;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.LunarMonstrosityBreath;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import cn.leolezury.eternalstarlight.common.entity.living.animal.*;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.TangledHatred;
import cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity.TangledHatredPart;
import cn.leolezury.eternalstarlight.common.entity.living.monster.*;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import cn.leolezury.eternalstarlight.common.entity.misc.ESChestBoat;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.entity.misc.EyeOfSeeking;
import cn.leolezury.eternalstarlight.common.entity.projectile.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ESEntities {
	public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(Registries.ENTITY_TYPE, EternalStarlight.ID);
	public static final RegistryObject<EntityType<?>, EntityType<ESFallingBlock>> FALLING_BLOCK = ENTITIES.register("falling_block", () -> EntityType.Builder.<ESFallingBlock>of(ESFallingBlock::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(1).build("falling_block"));
	public static final RegistryObject<EntityType<?>, EntityType<AethersentMeteor>> AETHERSENT_METEOR = ENTITIES.register("aethersent_meteor", () -> EntityType.Builder.<AethersentMeteor>of(AethersentMeteor::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(1).build(EternalStarlight.id("aethersent_meteor").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<ESBoat>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<ESBoat>of(ESBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("boat"));
	public static final RegistryObject<EntityType<?>, EntityType<ESChestBoat>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<ESChestBoat>of(ESChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("chest_boat"));
	public static final RegistryObject<EntityType<?>, EntityType<Boarwarf>> BOARWARF = ENTITIES.register("boarwarf", () -> EntityType.Builder.of(Boarwarf::new, MobCategory.CREATURE).sized(0.6F, 1.7F).clientTrackingRange(8).build(EternalStarlight.id("boarwarf").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<AstralGolem>> ASTRAL_GOLEM = ENTITIES.register("astral_golem", () -> EntityType.Builder.of(AstralGolem::new, MobCategory.CREATURE).sized(0.5F, 1.25F).build(EternalStarlight.id("astral_golem").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<Gleech>> GLEECH = ENTITIES.register("gleech", () -> EntityType.Builder.of(Gleech::new, MobCategory.MONSTER).sized(0.5F, 0.4F).eyeHeight(0.13F).passengerAttachments(0.2375F).clientTrackingRange(8).build(EternalStarlight.id("gleech").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<GleechEgg>> GLEECH_EGG = ENTITIES.register("gleech_egg", () -> EntityType.Builder.<GleechEgg>of(GleechEgg::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(EternalStarlight.id("gleech_egg").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<LonestarSkeleton>> LONESTAR_SKELETON = ENTITIES.register("lonestar_skeleton", () -> EntityType.Builder.of(LonestarSkeleton::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).build(EternalStarlight.id("lonestar_skeleton").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<NightfallSpider>> NIGHTFALL_SPIDER = ENTITIES.register("nightfall_spider", () -> EntityType.Builder.of(NightfallSpider::new, MobCategory.MONSTER).sized(0.75F, 0.75F).clientTrackingRange(8).build(EternalStarlight.id("nightfall_spider").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<ThirstWalker>> THIRST_WALKER = ENTITIES.register("thirst_walker", () -> EntityType.Builder.of(ThirstWalker::new, MobCategory.MONSTER).sized(0.6F, 2.9F).eyeHeight(2.55F).passengerAttachments(2.80625F).clientTrackingRange(8).build(EternalStarlight.id("thirst_walker").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<Ent>> ENT = ENTITIES.register("ent", () -> EntityType.Builder.of(Ent::new, MobCategory.CREATURE).sized(0.7F, 0.3F).clientTrackingRange(8).build(EternalStarlight.id("ent").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<Ratlin>> RATLIN = ENTITIES.register("ratlin", () -> EntityType.Builder.of(Ratlin::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(8).build(EternalStarlight.id("ratlin").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<Yeti>> YETI = ENTITIES.register("yeti", () -> EntityType.Builder.of(Yeti::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(8).build(EternalStarlight.id("yeti").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<AuroraDeer>> AURORA_DEER = ENTITIES.register("aurora_deer", () -> EntityType.Builder.of(AuroraDeer::new, MobCategory.CREATURE).sized(0.9F, 1.5F).clientTrackingRange(8).build(EternalStarlight.id("aurora_deer").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<CrystallizedMoth>> CRYSTALLIZED_MOTH = ENTITIES.register("crystallized_moth", () -> EntityType.Builder.of(CrystallizedMoth::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(8).build(EternalStarlight.id("crystallized_moth").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<ShimmerLacewing>> SHIMMER_LACEWING = ENTITIES.register("shimmer_lacewing", () -> EntityType.Builder.of(ShimmerLacewing::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(8).build(EternalStarlight.id("shimmer_lacewing").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<GrimstoneGolem>> GRIMSTONE_GOLEM = ENTITIES.register("grimstone_golem", () -> EntityType.Builder.of(GrimstoneGolem::new, MobCategory.CREATURE).sized(0.5F, 0.9F).clientTrackingRange(8).build(EternalStarlight.id("grimstone_golem").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<LuminoFish>> LUMINOFISH = ENTITIES.register("luminofish", () -> EntityType.Builder.of(LuminoFish::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(10).build(EternalStarlight.id("luminofish").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<Luminaris>> LUMINARIS = ENTITIES.register("luminaris", () -> EntityType.Builder.of(Luminaris::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(10).build(EternalStarlight.id("luminaris").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<TwilightGaze>> TWILIGHT_GAZE = ENTITIES.register("twilight_gaze", () -> EntityType.Builder.of(TwilightGaze::new, MobCategory.WATER_CREATURE).sized(0.9F, 0.6F).clientTrackingRange(10).build(EternalStarlight.id("twilight_gaze").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<EyeOfSeeking>> EYE_OF_SEEKING = ENTITIES.register("eye_of_seeking", () -> EntityType.Builder.<EyeOfSeeking>of(EyeOfSeeking::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(4).build(EternalStarlight.id("eye_of_seeking").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<TheGatekeeper>> THE_GATEKEEPER = ENTITIES.register("the_gatekeeper", () -> EntityType.Builder.of(TheGatekeeper::new, MobCategory.MONSTER).sized(0.6f, 1.99f).fireImmune().build(EternalStarlight.id("the_gatekeeper").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<GatekeeperFireball>> GATEKEEPER_FIREBALL = ENTITIES.register("gatekeeper_fireball", () -> EntityType.Builder.<GatekeeperFireball>of(GatekeeperFireball::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).updateInterval(1).build(EternalStarlight.id("gatekeeper_fireball").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<StarlightGolem>> STARLIGHT_GOLEM = ENTITIES.register("starlight_golem", () -> EntityType.Builder.of(StarlightGolem::new, MobCategory.MONSTER).sized(2.5f, 2.5f).fireImmune().build(EternalStarlight.id("starlight_golem").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<GolemLaserBeam>> GOLEM_LASER_BEAM = ENTITIES.register("golem_laser_beam", () -> EntityType.Builder.<GolemLaserBeam>of(GolemLaserBeam::new, MobCategory.MISC).sized(0f, 0f).clientTrackingRange(32).build(EternalStarlight.id("golem_laser_beam").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<EnergizedFlame>> ENERGIZED_FLAME = ENTITIES.register("energized_flame", () -> EntityType.Builder.of(EnergizedFlame::new, MobCategory.MISC).sized(0f, 0f).clientTrackingRange(32).build(EternalStarlight.id("energized_flame").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<Freeze>> FREEZE = ENTITIES.register("freeze", () -> EntityType.Builder.of(Freeze::new, MobCategory.MONSTER).sized(0.5f, 1.25f).fireImmune().build(EternalStarlight.id("freeze").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<FrozenTube>> FROZEN_TUBE = ENTITIES.register("frozen_tube", () -> EntityType.Builder.<FrozenTube>of(FrozenTube::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(EternalStarlight.id("frozen_tube").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<LunarMonstrosity>> LUNAR_MONSTROSITY = ENTITIES.register("lunar_monstrosity", () -> EntityType.Builder.of(LunarMonstrosity::new, MobCategory.MONSTER).sized(0.9f, 3f).build(EternalStarlight.id("lunar_monstrosity").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<LunarMonstrosityBreath>> LUNAR_MONSTROSITY_BREATH = ENTITIES.register("lunar_monstrosity_breath", () -> EntityType.Builder.<LunarMonstrosityBreath>of(LunarMonstrosityBreath::new, MobCategory.MISC).sized(0f, 0f).clientTrackingRange(32).build(EternalStarlight.id("lunar_monstrosity_breath").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<LunarSpore>> LUNAR_SPORE = ENTITIES.register("lunar_spore", () -> EntityType.Builder.<LunarSpore>of(LunarSpore::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(6).updateInterval(1).build(EternalStarlight.id("lunar_spore").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<LunarThorn>> LUNAR_THORN = ENTITIES.register("lunar_thorn", () -> EntityType.Builder.of(LunarThorn::new, MobCategory.MISC).sized(0.3f, 1f).build(EternalStarlight.id("lunar_thorn").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<Tangled>> TANGLED = ENTITIES.register("tangled", () -> EntityType.Builder.of(Tangled::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).build(EternalStarlight.id("tangled").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<TangledSkull>> TANGLED_SKULL = ENTITIES.register("tangled_skull", () -> EntityType.Builder.of(TangledSkull::new, MobCategory.MONSTER).sized(0.5F, 0.5F).clientTrackingRange(8).build(EternalStarlight.id("tangled_skull").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<TangledHatred>> TANGLED_HATRED = ENTITIES.register("tangled_hatred", () -> EntityType.Builder.of(TangledHatred::new, MobCategory.MONSTER).sized(0.9f, 1f).build(EternalStarlight.id("tangled_hatred").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<TangledHatredPart>> TANGLED_HATRED_PART = ENTITIES.register("tangled_hatred_part", () -> EntityType.Builder.of(TangledHatredPart::new, MobCategory.MONSTER).sized(1.5f, 1.5f).build(EternalStarlight.id("tangled_hatred_part").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<ShatteredBlade>> SHATTERED_BLADE = ENTITIES.register("shattered_blade", () -> EntityType.Builder.<ShatteredBlade>of(ShatteredBlade::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(6).updateInterval(1).build(EternalStarlight.id("shattered_blade").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<AmaramberArrow>> AMARAMBER_ARROW = ENTITIES.register("amaramber_arrow", () -> EntityType.Builder.<AmaramberArrow>of(AmaramberArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(EternalStarlight.id("amaramber_arrow").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<SonarBomb>> SONAR_BOMB = ENTITIES.register("sonar_bomb", () -> EntityType.Builder.<SonarBomb>of(SonarBomb::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(EternalStarlight.id("sonar_bomb").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<SoulitSpectator>> SOULIT_SPECTATOR = ENTITIES.register("soulit_spectator", () -> EntityType.Builder.<SoulitSpectator>of(SoulitSpectator::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(EternalStarlight.id("soulit_spectator").toString()));
	public static final RegistryObject<EntityType<?>, EntityType<ChainOfSouls>> CHAIN_OF_SOULS = ENTITIES.register("chain_of_souls", () -> EntityType.Builder.<ChainOfSouls>of(ChainOfSouls::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(EternalStarlight.id("chain_of_souls").toString()));

	public static void loadClass() {
	}
}
