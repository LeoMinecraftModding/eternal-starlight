package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class ESSoundEvents {
	public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, EternalStarlight.ID);
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DIMENSION = register("music.dimension");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_STARLIGHT_FOREST = register("music.biome.starlight_forest");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_DARK_SWAMP = register("music.biome.dark_swamp");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST = register("music.biome.starlight_permafrost_forest");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_SCARLET_FOREST = register("music.biome.scarlet_forest");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_TORREYA_FOREST = register("music.biome.torreya_forest");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_CRYSTALLIZED_DESERT = register("music.biome.crystallized_desert");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_STARLIT_SEA = register("music.biome.starlit_sea");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_THE_ABYSS = register("music.biome.the_abyss");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BOSS_GATEKEEPER = register("music.boss.gatekeeper");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BOSS_STARLIGHT_GOLEM = register("music.boss.starlight_golem");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BOSS_LUNAR_MONSTROSITY = register("music.boss.lunar_monstrosity");

	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_WHISPER_OF_THE_STARS = register("music_disc.whisper_of_the_stars");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_DUSK_O_EREYESTERDAY = register("music_disc.dusk_o_ereyesterday");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_TRANQUILITY = register("music_disc.tranquility");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_NEST = register("music_disc.nest");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_POSTERITY = register("music_disc.posterity");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_THE_THORNY_REIGN = register("music_disc.the_thorny_reign");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_PROFUNDITY = register("music_disc.profundity");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_WAILING_WELL = register("music_disc.wailing_well");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_STARS_SHINING_UPON_THE_SEA = register("music_disc.stars_shining_upon_the_sea");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_OPTIMIZED_OPTION = register("music_disc.optimized_option");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_MECHANICAL_FOSSIL = register("music_disc.mechanical_fossil");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_FAKE_LIGHT = register("music_disc.fake_light");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_TRANQUILITY_II = register("music_disc.tranquility_ii");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_ATLANTIS = register("music_disc.atlantis");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_SACRED_DESERT = register("music_disc.sacred_desert");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_SPIRIT = register("music_disc.spirit");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_ETHER_RAIN = register("music_disc.ether_rain");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_BRISK = register("music_disc.brisk");
	public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DISC_MOONLIGHT = register("music_disc.moonlight");

	public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_ALCHEMIST = register("item.armor.equip_alchemist");
	public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_AMARAMBER = register("item.armor.equip_amaramber");
	public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_AETHERSENT = register("item.armor.equip_aethersent");
	public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_THERMAL_SPRINGSTONE = register("item.armor.equip_thermal_springstone");
	public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_GLACITE = register("item.armor.equip_glacite");
	public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_STARLIT_DIAMOND = register("item.armor.equip_starlit_diamond");
	public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_DEEPSILVER = register("item.armor.equip_deepsilver");
	public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_UNREALIUM = register("item.armor.equip_unrealium");

	public static final RegistryObject<SoundEvent, SoundEvent> WHIP_SWISH = register("item.whip.swish");
	public static final RegistryObject<SoundEvent, SoundEvent> WHIP_CRACK = register("item.whip.crack");

	public static final RegistryObject<SoundEvent, SoundEvent> CRYSTAL_GREATSWORD_CHIME = register("item.crystal_greatsword.chime");

	public static final RegistryObject<SoundEvent, SoundEvent> CHAIN_OF_SOULS_ABSORB = register("item.chain_of_souls.absorb");

	public static final RegistryObject<SoundEvent, SoundEvent> CRESCENT_SPEAR_THROW = register("item.crescent_spear.throw");

	public static final RegistryObject<SoundEvent, SoundEvent> SEEDS_LAUNCHER_SHOOT = register("item.seeds_launcher.shoot");

	public static final RegistryObject<SoundEvent, SoundEvent> AETHERSENT_METEOR_WHOOSH = register("entity.aethersent_meteor.whoosh");

	public static final RegistryObject<SoundEvent, SoundEvent> LASER_BEAM_HUM = register("entity.laser_beam.hum");

	public static final RegistryObject<SoundEvent, SoundEvent> FROZEN_TUBE_THROW = register("entity.frozen_tube.throw");

	public static final RegistryObject<SoundEvent, SoundEvent> FROZEN_TUBE_BREAK = register("entity.frozen_tube.break");

	public static final RegistryObject<SoundEvent, SoundEvent> SONAR_BOMB_EXPLODE = register("entity.sonar_bomb.explode");

	public static final RegistryObject<SoundEvent, SoundEvent> STARFIRE_WHOOSH = register("entity.starfire.whoosh");

	public static final RegistryObject<SoundEvent, SoundEvent> SEEKING_EYE_LAUNCH = register("entity.seeking_eye.launch");
	public static final RegistryObject<SoundEvent, SoundEvent> SEEKING_EYE_DEATH = register("entity.seeking_eye.death");

	public static final RegistryObject<SoundEvent, SoundEvent> BOARWARF_AMBIENT = register("entity.boarwarf.ambient");
	public static final RegistryObject<SoundEvent, SoundEvent> BOARWARF_HURT = register("entity.boarwarf.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> BOARWARF_DEATH = register("entity.boarwarf.death");
	public static final RegistryObject<SoundEvent, SoundEvent> BOARWARF_TRADE = register("entity.boarwarf.trade");
	public static final RegistryObject<SoundEvent, SoundEvent> BOARWARF_YES = register("entity.boarwarf.yes");
	public static final RegistryObject<SoundEvent, SoundEvent> BOARWARF_NO = register("entity.boarwarf.no");

	public static final RegistryObject<SoundEvent, SoundEvent> ASTRAL_GOLEM_ATTACK = register("entity.astral_golem.attack");
	public static final RegistryObject<SoundEvent, SoundEvent> ASTRAL_GOLEM_HURT = register("entity.astral_golem.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> ASTRAL_GOLEM_DEATH = register("entity.astral_golem.death");
	public static final RegistryObject<SoundEvent, SoundEvent> ASTRAL_GOLEM_REPAIR = register("entity.astral_golem.repair");

	public static final RegistryObject<SoundEvent, SoundEvent> ENT_HURT = register("entity.ent.hurt");

	public static final RegistryObject<SoundEvent, SoundEvent> AURORA_DEER_AMBIENT = register("entity.aurora_deer.ambient");
	public static final RegistryObject<SoundEvent, SoundEvent> AURORA_DEER_HURT = register("entity.aurora_deer.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> AURORA_DEER_DEATH = register("entity.aurora_deer.death");

	public static final RegistryObject<SoundEvent, SoundEvent> STARFIRE_BIRD_AMBIENT = register("entity.starfire_bird.ambient");
	public static final RegistryObject<SoundEvent, SoundEvent> STARFIRE_BIRD_HURT = register("entity.starfire_bird.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> STARFIRE_BIRD_DEATH = register("entity.starfire_bird.death");

	public static final RegistryObject<SoundEvent, SoundEvent> AETHERSENT_GOLEM_HURT = register("entity.aethersent_golem.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> AETHERSENT_GOLEM_DEATH = register("entity.aethersent_golem.death");
	public static final RegistryObject<SoundEvent, SoundEvent> AETHERSENT_GOLEM_SHOOT = register("entity.aethersent_golem.shoot");

	public static final RegistryObject<SoundEvent, SoundEvent> SEEKER_HURT = register("entity.seeker.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> SEEKER_DEATH = register("entity.seeker.death");

	public static final RegistryObject<SoundEvent, SoundEvent> THIRST_WALKER_AMBIENT = register("entity.thirst_walker.ambient");
	public static final RegistryObject<SoundEvent, SoundEvent> THIRST_WALKER_HURT = register("entity.thirst_walker.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> THIRST_WALKER_DEATH = register("entity.thirst_walker.death");

	public static final RegistryObject<SoundEvent, SoundEvent> CRETEOR_ROLL = register("entity.creteor.roll");
	public static final RegistryObject<SoundEvent, SoundEvent> CRETEOR_HURT = register("entity.creteor.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> CRETEOR_DEATH = register("entity.creteor.death");
	public static final RegistryObject<SoundEvent, SoundEvent> CRETEOR_CHARGE = register("entity.creteor.charge");
	public static final RegistryObject<SoundEvent, SoundEvent> CRETEOR_EXPLODE = register("entity.creteor.explode");

	public static final RegistryObject<SoundEvent, SoundEvent> STRANGHOUL_AMBIENT = register("entity.stranghoul.ambient");
	public static final RegistryObject<SoundEvent, SoundEvent> STRANGHOUL_HURT = register("entity.stranghoul.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> STRANGHOUL_DEATH = register("entity.stranghoul.death");
	public static final RegistryObject<SoundEvent, SoundEvent> STRANGHOUL_SHOOT = register("entity.stranghoul.shoot");

	public static final RegistryObject<SoundEvent, SoundEvent> LUMINOFISH_HURT = register("entity.luminofish.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> LUMINOFISH_DEATH = register("entity.luminofish.death");
	public static final RegistryObject<SoundEvent, SoundEvent> LUMINOFISH_FLOP = register("entity.luminofish.flop");

	public static final RegistryObject<SoundEvent, SoundEvent> LUMINARIS_HURT = register("entity.luminaris.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> LUMINARIS_DEATH = register("entity.luminaris.death");
	public static final RegistryObject<SoundEvent, SoundEvent> LUMINARIS_FLOP = register("entity.luminaris.flop");

	public static final RegistryObject<SoundEvent, SoundEvent> TWILIGHT_GAZE_HURT = register("entity.twilight_gaze.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> TWILIGHT_GAZE_DEATH = register("entity.twilight_gaze.death");

	public static final RegistryObject<SoundEvent, SoundEvent> THE_GATEKEEPER_PARRY = register("entity.the_gatekeeper.parry");

	public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_HURT = register("entity.starlight_golem.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_DEATH = register("entity.starlight_golem.death");
	public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_BLOCK = register("entity.starlight_golem.block");
	public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_PREPARE_BEAM = register("entity.starlight_golem.prepare_beam");
	public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_PREPARE_CHARGE = register("entity.starlight_golem.prepare_charge");

	public static final RegistryObject<SoundEvent, SoundEvent> FREEZE_HURT = register("entity.freeze.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> FREEZE_DEATH = register("entity.freeze.death");

	public static final RegistryObject<SoundEvent, SoundEvent> PERMAFROST_HURT = register("entity.permafrost.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> PERMAFROST_DEATH = register("entity.permafrost.death");
	public static final RegistryObject<SoundEvent, SoundEvent> PERMAFROST_SNEEZE = register("entity.permafrost.sneeze");

	public static final RegistryObject<SoundEvent, SoundEvent> LUNAR_MONSTROSITY_HURT = register("entity.lunar_monstrosity.hurt");
	public static final RegistryObject<SoundEvent, SoundEvent> LUNAR_MONSTROSITY_DEATH = register("entity.lunar_monstrosity.death");
	public static final RegistryObject<SoundEvent, SoundEvent> LUNAR_MONSTROSITY_BITE = register("entity.lunar_monstrosity.bite");
	public static final RegistryObject<SoundEvent, SoundEvent> LUNAR_MONSTROSITY_ROAR = register("entity.lunar_monstrosity.roar");

	public static final RegistryObject<SoundEvent, SoundEvent> TANGLED_SKULL_AMBIENT = register("entity.tangled_skull.ambient");
	public static final RegistryObject<SoundEvent, SoundEvent> TANGLED_SKULL_ROAR = register("entity.tangled_skull.roar");

	public static final RegistryObject<SoundEvent, SoundEvent> ETHER_TRANSFORM = register("block.ether.transform");
	public static final RegistryObject<SoundEvent, SoundEvent> STELLAR_RACK_AMBIENT = register("block.stellar_rack.ambient");

	public static RegistryObject<SoundEvent, SoundEvent> register(String name) {
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(EternalStarlight.id(name)));
	}

	public static void loadClass() {
	}
}
