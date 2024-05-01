package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.attack.LunarVine;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemFlameAttack;
import cn.leolezury.eternalstarlight.common.entity.attack.ray.GolemLaserBeam;
import cn.leolezury.eternalstarlight.common.entity.living.GrimstoneGolem;
import cn.leolezury.eternalstarlight.common.entity.living.animal.*;
import cn.leolezury.eternalstarlight.common.entity.living.boss.LunarMonstrosity;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Freeze;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Gleech;
import cn.leolezury.eternalstarlight.common.entity.living.monster.LonestarSkeleton;
import cn.leolezury.eternalstarlight.common.entity.living.monster.NightfallSpider;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.Boarwarf;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.entity.misc.*;
import cn.leolezury.eternalstarlight.common.entity.projectile.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ESEntities {
    public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(Registries.ENTITY_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<EntityType<?>, EntityType<ESFallingBlock>> FALLING_BLOCK = ENTITIES.register("falling_block", () -> EntityType.Builder.<ESFallingBlock>of(ESFallingBlock::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(1).build("falling_block"));
    public static final RegistryObject<EntityType<?>, EntityType<AetherSentMeteor>> AETHERSENT_METEOR = ENTITIES.register("aethersent_meteor", () -> EntityType.Builder.<AetherSentMeteor>of(AetherSentMeteor::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(1).build(new ResourceLocation(EternalStarlight.MOD_ID, "aethersent_meteor").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<ESBoat>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<ESBoat>of(ESBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("boat"));
    public static final RegistryObject<EntityType<?>, EntityType<ESChestBoat>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<ESChestBoat>of(ESChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("chest_boat"));
    public static final RegistryObject<EntityType<?>, EntityType<CameraShake>> CAMERA_SHAKE = ENTITIES.register("camera_shake", () -> EntityType.Builder.<CameraShake>of(CameraShake::new, MobCategory.MISC).sized(0f, 0f).build(new ResourceLocation(EternalStarlight.MOD_ID, "camera_shake").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<Boarwarf>> BOARWARF = ENTITIES.register("boarwarf", () -> EntityType.Builder.of(Boarwarf::new, MobCategory.CREATURE).sized(0.6F, 1.7F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "boarwarf").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<AstralGolem>> ASTRAL_GOLEM = ENTITIES.register("astral_golem", () -> EntityType.Builder.of(AstralGolem::new, MobCategory.CREATURE).sized(0.5F, 1.25F).build(new ResourceLocation(EternalStarlight.MOD_ID, "astral_golem").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<Gleech>> GLEECH = ENTITIES.register("gleech", () -> EntityType.Builder.of(Gleech::new, MobCategory.MONSTER).sized(0.4F, 0.3F).eyeHeight(0.13F).passengerAttachments(0.2375F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "gleech").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<GleechEgg>> GLEECH_EGG = ENTITIES.register("gleech_egg", () -> EntityType.Builder.<GleechEgg>of(GleechEgg::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(new ResourceLocation(EternalStarlight.MOD_ID, "gleech_egg").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<LonestarSkeleton>> LONESTAR_SKELETON = ENTITIES.register("lonestar_skeleton", () -> EntityType.Builder.of(LonestarSkeleton::new, MobCategory.MONSTER).sized(0.6F, 1.99F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "lonestar_skeleton").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<NightfallSpider>> NIGHTFALL_SPIDER = ENTITIES.register("nightfall_spider", () -> EntityType.Builder.of(NightfallSpider::new, MobCategory.MONSTER).sized(0.75F, 0.75F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "nightfall_spider").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<Ent>> ENT = ENTITIES.register("ent", () -> EntityType.Builder.of(Ent::new, MobCategory.CREATURE).sized(0.7F, 0.3F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "ent").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<Ratlin>> RATLIN = ENTITIES.register("ratlin", () -> EntityType.Builder.of(Ratlin::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "ratlin").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<Yeti>> YETI = ENTITIES.register("yeti", () -> EntityType.Builder.of(Yeti::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "yeti").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<AuroraDeer>> AURORA_DEER = ENTITIES.register("aurora_deer", () -> EntityType.Builder.of(AuroraDeer::new, MobCategory.CREATURE).sized(0.9F, 1.5F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "aurora_deer").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<CrystallizedMoth>> CRYSTALLIZED_MOTH = ENTITIES.register("crystallized_moth", () -> EntityType.Builder.of(CrystallizedMoth::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "crystallized_moth").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<ShimmerLacewing>> SHIMMER_LACEWING = ENTITIES.register("shimmer_lacewing", () -> EntityType.Builder.of(ShimmerLacewing::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "shimmer_lacewing").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<GrimstoneGolem>> GRIMSTONE_GOLEM = ENTITIES.register("grimstone_golem", () -> EntityType.Builder.of(GrimstoneGolem::new, MobCategory.CREATURE).sized(0.5F, 0.9F).clientTrackingRange(8).build(new ResourceLocation(EternalStarlight.MOD_ID, "grimstone_golem").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<LuminoFish>> LUMINOFISH = ENTITIES.register("luminofish", () -> EntityType.Builder.of(LuminoFish::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(10).build(new ResourceLocation(EternalStarlight.MOD_ID, "luminofish").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<Luminaris>> LUMINARIS = ENTITIES.register("luminaris", () -> EntityType.Builder.of(Luminaris::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).clientTrackingRange(10).build(new ResourceLocation(EternalStarlight.MOD_ID, "luminaris").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<TwilightGaze>> TWILIGHT_GAZE = ENTITIES.register("twilight_gaze", () -> EntityType.Builder.of(TwilightGaze::new, MobCategory.WATER_CREATURE).sized(0.9F, 0.6F).clientTrackingRange(10).build(new ResourceLocation(EternalStarlight.MOD_ID, "twilight_gaze").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<EyeOfSeeking>> EYE_OF_SEEKING = ENTITIES.register("eye_of_seeking", () -> EntityType.Builder.<EyeOfSeeking>of(EyeOfSeeking::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(4).build(new ResourceLocation(EternalStarlight.MOD_ID, "eye_of_seeking").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<TheGatekeeper>> THE_GATEKEEPER = ENTITIES.register("the_gatekeeper", () -> EntityType.Builder.of(TheGatekeeper::new, MobCategory.MONSTER).sized(0.6f, 1.99f).build(new ResourceLocation(EternalStarlight.MOD_ID, "the_gatekeeper").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<GatekeeperFireball>> GATEKEEPER_FIREBALL = ENTITIES.register("gatekeeper_fireball", () -> EntityType.Builder.<GatekeeperFireball>of(GatekeeperFireball::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4).updateInterval(1).build(new ResourceLocation(EternalStarlight.MOD_ID, "gatekeeper_fireball").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<StarlightGolem>> STARLIGHT_GOLEM = ENTITIES.register("starlight_golem", () -> EntityType.Builder.of(StarlightGolem::new, MobCategory.MONSTER).sized(2.5f, 2.5f).fireImmune().build(new ResourceLocation(EternalStarlight.MOD_ID, "starlight_golem").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<Freeze>> FREEZE = ENTITIES.register("freeze", () -> EntityType.Builder.of(Freeze::new, MobCategory.MONSTER).sized(0.5f, 1.25f).fireImmune().build(new ResourceLocation(EternalStarlight.MOD_ID, "freeze").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<FrozenTube>> FROZEN_TUBE = ENTITIES.register("frozen_tube", () -> EntityType.Builder.<FrozenTube>of(FrozenTube::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(new ResourceLocation(EternalStarlight.MOD_ID, "frozen_tube").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<LunarMonstrosity>> LUNAR_MONSTROSITY = ENTITIES.register("lunar_monstrosity", () -> EntityType.Builder.of(LunarMonstrosity::new, MobCategory.MONSTER).sized(0.9f, 3f).build(new ResourceLocation(EternalStarlight.MOD_ID, "lunar_monstrosity").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<GolemLaserBeam>> GOLEM_LASER_BEAM = ENTITIES.register("golem_laser_beam", () -> EntityType.Builder.<GolemLaserBeam>of(GolemLaserBeam::new, MobCategory.MISC).sized(0f, 0f).clientTrackingRange(32).build(new ResourceLocation(EternalStarlight.MOD_ID, "golem_laser_beam").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<GolemFlameAttack>> GOLEM_FLAME_ATTACK = ENTITIES.register("golem_flame_attack", () -> EntityType.Builder.<GolemFlameAttack>of(GolemFlameAttack::new, MobCategory.MISC).sized(0f, 0f).clientTrackingRange(32).build(new ResourceLocation(EternalStarlight.MOD_ID, "golem_flame_attack").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<EnergizedFlame>> ENERGIZED_FLAME = ENTITIES.register("energized_flame", () -> EntityType.Builder.of(EnergizedFlame::new, MobCategory.MISC).sized(0f, 0f).clientTrackingRange(32).build(new ResourceLocation(EternalStarlight.MOD_ID, "energized_flame").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<LunarSpore>> LUNAR_SPORE = ENTITIES.register("lunar_spore", () -> EntityType.Builder.<LunarSpore>of(LunarSpore::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(6).updateInterval(1).build(new ResourceLocation(EternalStarlight.MOD_ID, "lunar_spore").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<LunarVine>> LUNAR_VINE = ENTITIES.register("lunar_vine", () -> EntityType.Builder.of(LunarVine::new, MobCategory.MISC).sized(0.3f, 1f).build(new ResourceLocation(EternalStarlight.MOD_ID, "lunar_vine").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<ThrownShatteredBlade>> THROWN_SHATTERED_BLADE = ENTITIES.register("thrown_shattered_blade", () -> EntityType.Builder.<ThrownShatteredBlade>of(ThrownShatteredBlade::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(6).updateInterval(1).build(new ResourceLocation(EternalStarlight.MOD_ID, "thrown_shattered_blade").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<AmaramberArrow>> AMARAMBER_ARROW = ENTITIES.register("amaramber_arrow", () -> EntityType.Builder.<AmaramberArrow>of(AmaramberArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(new ResourceLocation(EternalStarlight.MOD_ID, "amaramber_arrow").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<SonarBomb>> SONAR_BOMB = ENTITIES.register("sonar_bomb", () -> EntityType.Builder.<SonarBomb>of(SonarBomb::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(new ResourceLocation(EternalStarlight.MOD_ID, "sonar_bomb").toString()));
    public static final RegistryObject<EntityType<?>, EntityType<SoulitSpectator>> SOULIT_SPECTATOR = ENTITIES.register("soulit_spectator", () -> EntityType.Builder.<SoulitSpectator>of(SoulitSpectator::new, MobCategory.MISC).sized(0.3f, 0.3f).clientTrackingRange(10).updateInterval(1).build(new ResourceLocation(EternalStarlight.MOD_ID, "soulit_spectator").toString()));

    public static void loadClass() {}
}
