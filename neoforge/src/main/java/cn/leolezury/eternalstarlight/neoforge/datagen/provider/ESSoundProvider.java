package cn.leolezury.eternalstarlight.neoforge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ESSoundProvider extends SoundDefinitionsProvider {
	public ESSoundProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, EternalStarlight.ID, helper);
	}

	private ResourceLocation mcLoc(String s) {
		return ResourceLocation.withDefaultNamespace(s);
	}

	private ResourceLocation loc(String s) {
		return EternalStarlight.id(s);
	}

	@Override
	public void registerSounds() {
		add(ESSoundEvents.MUSIC_DIMENSION.get(), definition().with(sound(loc("music/dimension/tranquility")).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BIOME_STARLIGHT_FOREST.get(), definition().with(sound(loc("music/dimension/tranquility")).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.get(), definition().with(sound(loc("music/biome/starlight_permafrost_forest")).weight(5).stream(), sound(loc("music/dimension/tranquility")).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BIOME_DARK_SWAMP.get(), definition().with(sound(loc("music/biome/wailing_well")).weight(5).stream(), sound(loc("music/dimension/tranquility")).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BIOME_SCARLET_FOREST.get(), definition().with(sound(loc("music/biome/scarlet_forest")).weight(5).stream(), sound(loc("music/dimension/tranquility")).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BIOME_TORREYA_FOREST.get(), definition().with(sound(loc("music/biome/the_thorny_reign")).weight(5).stream(), sound(loc("music/dimension/tranquility")).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BIOME_CRYSTALLIZED_DESERT.get(), definition().with(sound(loc("music/biome/posterity")).weight(5).stream(), sound(loc("music/dimension/tranquility")).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BIOME_STARLIT_SEA.get(), definition().with(sound(loc("music/biome/stars_shining_upon_the_sea")).weight(5).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BIOME_THE_ABYSS.get(), definition().with(sound(loc("music/biome/profundity")).weight(5).stream(), sound(loc("music/dimension/whisper_of_the_stars")).stream(), sound(loc("music/dimension/dusk_o_ereyesterday")).stream(), sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_BOSS_GATEKEEPER.get(), definition().with(sound(loc("music/boss/gatekeeper")).stream()));
		add(ESSoundEvents.MUSIC_BOSS_STARLIGHT_GOLEM.get(), definition().with(sound(loc("music/boss/starlight_golem")).stream()));
		add(ESSoundEvents.MUSIC_BOSS_LUNAR_MONSTROSITY.get(), definition().with(sound(loc("music/boss/lunar_monstrosity")).stream()));

		add(ESSoundEvents.MUSIC_DISC_WHISPER_OF_THE_STARS.get(), definition().with(sound(loc("music/dimension/whisper_of_the_stars")).stream()));
		add(ESSoundEvents.MUSIC_DISC_DUSK_O_EREYESTERDAY.get(), definition().with(sound(loc("music/dimension/dusk_o_ereyesterday")).stream()));
		add(ESSoundEvents.MUSIC_DISC_TRANQUILITY.get(), definition().with(sound(loc("music/dimension/tranquility")).stream()));
		add(ESSoundEvents.MUSIC_DISC_NEST.get(), definition().with(sound(loc("music/dimension/nest")).stream()));
		add(ESSoundEvents.MUSIC_DISC_POSTERITY.get(), definition().with(sound(loc("music/biome/posterity")).stream()));
		add(ESSoundEvents.MUSIC_DISC_THE_THORNY_REIGN.get(), definition().with(sound(loc("music/biome/the_thorny_reign")).stream()));
		add(ESSoundEvents.MUSIC_DISC_PROFUNDITY.get(), definition().with(sound(loc("music/biome/profundity")).stream()));
		add(ESSoundEvents.MUSIC_DISC_WAILING_WELL.get(), definition().with(sound(loc("music/biome/wailing_well")).stream()));
		add(ESSoundEvents.MUSIC_DISC_STARS_SHINING_UPON_THE_SEA.get(), definition().with(sound(loc("music/biome/stars_shining_upon_the_sea")).stream()));
		add(ESSoundEvents.MUSIC_DISC_OPTIMIZED_OPTION.get(), definition().with(sound(loc("music/boss/gatekeeper")).stream()));
		add(ESSoundEvents.MUSIC_DISC_MECHANICAL_FOSSIL.get(), definition().with(sound(loc("music/boss/starlight_golem")).stream()));
		add(ESSoundEvents.MUSIC_DISC_FAKE_LIGHT.get(), definition().with(sound(loc("music/boss/lunar_monstrosity")).stream()));
		add(ESSoundEvents.MUSIC_DISC_TRANQUILITY_II.get(), definition().with(sound(loc("music/disc/tranquility_ii")).stream()));
		add(ESSoundEvents.MUSIC_DISC_ATLANTIS.get(), definition().with(sound(loc("music/disc/atlantis")).stream()));
		add(ESSoundEvents.MUSIC_DISC_SACRED_DESERT.get(), definition().with(sound(loc("music/disc/sacred_desert")).stream()));
		add(ESSoundEvents.MUSIC_DISC_SPIRIT.get(), definition().with(sound(loc("music/disc/spirit")).stream()));
		add(ESSoundEvents.MUSIC_DISC_ETHER_RAIN.get(), definition().with(sound(loc("music/disc/ether_rain")).stream()));
		add(ESSoundEvents.MUSIC_DISC_BRISK.get(), definition().with(sound(loc("music/disc/brisk")).stream()));
		add(ESSoundEvents.MUSIC_DISC_MOONLIGHT.get(), definition().with(sound(loc("music/disc/moonlight")).stream()));

		add(ESSoundEvents.ARMOR_EQUIP_ALCHEMIST.get(), definition().with(sound(mcLoc("item/armor/equip_leather1")), sound(mcLoc("item/armor/equip_leather2")), sound(mcLoc("item/armor/equip_leather3")), sound(mcLoc("item/armor/equip_leather4")), sound(mcLoc("item/armor/equip_leather5")), sound(mcLoc("item/armor/equip_leather6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_alchemist"));
		add(ESSoundEvents.ARMOR_EQUIP_AMARAMBER.get(), definition().with(sound(mcLoc("item/armor/equip_leather1")), sound(mcLoc("item/armor/equip_leather2")), sound(mcLoc("item/armor/equip_leather3")), sound(mcLoc("item/armor/equip_leather4")), sound(mcLoc("item/armor/equip_leather5")), sound(mcLoc("item/armor/equip_leather6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_amaramber"));
		add(ESSoundEvents.ARMOR_EQUIP_AETHERSENT.get(), definition().with(sound(mcLoc("item/armor/equip_leather1")), sound(mcLoc("item/armor/equip_leather2")), sound(mcLoc("item/armor/equip_leather3")), sound(mcLoc("item/armor/equip_leather4")), sound(mcLoc("item/armor/equip_leather5")), sound(mcLoc("item/armor/equip_leather6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_aethersent"));
		add(ESSoundEvents.ARMOR_EQUIP_THERMAL_SPRINGSTONE.get(), definition().with(sound(mcLoc("item/armor/equip_iron1")), sound(mcLoc("item/armor/equip_iron2")), sound(mcLoc("item/armor/equip_iron3")), sound(mcLoc("item/armor/equip_iron4")), sound(mcLoc("item/armor/equip_iron5")), sound(mcLoc("item/armor/equip_iron6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_thermal_springstone"));
		add(ESSoundEvents.ARMOR_EQUIP_GLACITE.get(), definition().with(sound(mcLoc("item/armor/equip_iron1")), sound(mcLoc("item/armor/equip_iron2")), sound(mcLoc("item/armor/equip_iron3")), sound(mcLoc("item/armor/equip_iron4")), sound(mcLoc("item/armor/equip_iron5")), sound(mcLoc("item/armor/equip_iron6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_glacite"));
		add(ESSoundEvents.ARMOR_EQUIP_STARLIT_DIAMOND.get(), definition().with(sound(mcLoc("item/armor/equip_diamond1")), sound(mcLoc("item/armor/equip_diamond2")), sound(mcLoc("item/armor/equip_diamond3")), sound(mcLoc("item/armor/equip_diamond4")), sound(mcLoc("item/armor/equip_diamond5")), sound(mcLoc("item/armor/equip_diamond6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_starlit_diamond"));
		add(ESSoundEvents.ARMOR_EQUIP_DEEPSILVER.get(), definition().with(sound(mcLoc("item/armor/equip_diamond1")), sound(mcLoc("item/armor/equip_diamond2")), sound(mcLoc("item/armor/equip_diamond3")), sound(mcLoc("item/armor/equip_diamond4")), sound(mcLoc("item/armor/equip_diamond5")), sound(mcLoc("item/armor/equip_diamond6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_deepsilver"));
		add(ESSoundEvents.ARMOR_EQUIP_UNREALIUM.get(), definition().with(sound(mcLoc("item/armor/equip_diamond1")), sound(mcLoc("item/armor/equip_diamond2")), sound(mcLoc("item/armor/equip_diamond3")), sound(mcLoc("item/armor/equip_diamond4")), sound(mcLoc("item/armor/equip_diamond5")), sound(mcLoc("item/armor/equip_diamond6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_unrealium"));

		add(ESSoundEvents.WHIP_SWISH.get(), definition().with(sound(loc("item/whip/swish"))).subtitle("subtitles.item." + EternalStarlight.ID + ".whip.swish"));
		add(ESSoundEvents.WHIP_CRACK.get(), definition().with(sound(loc("item/whip/crack"))).subtitle("subtitles.item." + EternalStarlight.ID + ".whip.crack"));

		add(ESSoundEvents.CRYSTAL_GREATSWORD_CHIME.get(), definition().with(sound(loc("item/crystal_greatsword/chime"))).subtitle("subtitles.item." + EternalStarlight.ID + ".crystal_greatsword.chime"));

		add(ESSoundEvents.CHAIN_OF_SOULS_ABSORB.get(), definition().with(sound(loc("item/chain_of_souls/absorb"))).subtitle("subtitles.item." + EternalStarlight.ID + ".chain_of_souls.absorb"));

		add(ESSoundEvents.CRESCENT_SPEAR_THROW.get(), definition().with(sound(loc("item/crescent_spear/throw"))).subtitle("subtitles.item." + EternalStarlight.ID + ".crescent_spear.throw"));

		add(ESSoundEvents.SEEDS_LAUNCHER_SHOOT.get(), definition().with(sound(loc("item/seeds_launcher/shoot"))).subtitle("subtitles.item." + EternalStarlight.ID + ".seeds_launcher.shoot"));

		add(ESSoundEvents.AETHERSENT_METEOR_WHOOSH.get(), definition().with(sound(loc("entity/aethersent_meteor/whoosh"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".aethersent_meteor.whoosh"));

		add(ESSoundEvents.LASER_BEAM_HUM.get(), definition().with(sound(loc("entity/laser_beam/hum"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".laser_beam.hum"));

		add(ESSoundEvents.FROZEN_TUBE_THROW.get(), definition().with(sound(loc("entity/frozen_tube/throw"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".frozen_tube.throw"));
		add(ESSoundEvents.FROZEN_TUBE_BREAK.get(), definition().with(sound(loc("entity/frozen_tube/break"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".frozen_tube.break"));

		add(ESSoundEvents.SONAR_BOMB_EXPLODE.get(), definition().with(sound(loc("entity/sonar_bomb/explode"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".sonar_bomb.explode"));

		add(ESSoundEvents.STARFIRE_WHOOSH.get(), definition().with(sound(loc("entity/starfire/whoosh"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starfire.whoosh"));

		add(ESSoundEvents.SEEKING_EYE_LAUNCH.get(), definition().with(sound(mcLoc("entity/endereye/endereye_launch1")), sound(mcLoc("entity/endereye/endereye_launch2"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".seeking_eye.launch"));
		add(ESSoundEvents.SEEKING_EYE_DEATH.get(), definition().with(sound(mcLoc("entity/endereye/dead1")).volume(1.3), sound(mcLoc("entity/endereye/dead2")).volume(1.3)).subtitle("subtitles.entity." + EternalStarlight.ID + ".seeking_eye.death"));

		add(ESSoundEvents.BOARWARF_AMBIENT.get(), definition().with(sound(mcLoc("mob/piglin/idle1")).volume(0.66), sound(mcLoc("mob/piglin/idle2")).volume(0.66), sound(mcLoc("mob/piglin/idle3")).volume(0.66), sound(mcLoc("mob/piglin/idle4")).volume(0.66), sound(mcLoc("mob/piglin/idle5")).volume(0.66)).subtitle("subtitles.entity." + EternalStarlight.ID + ".boarwarf.ambient"));
		add(ESSoundEvents.BOARWARF_HURT.get(), definition().with(sound(mcLoc("mob/piglin/hurt1")).volume(0.7), sound(mcLoc("mob/piglin/hurt2")).volume(0.7), sound(mcLoc("mob/piglin/hurt3")).volume(0.7)).subtitle("subtitles.entity." + EternalStarlight.ID + ".boarwarf.hurt"));
		add(ESSoundEvents.BOARWARF_DEATH.get(), definition().with(sound(mcLoc("mob/piglin/death1")).volume(0.7), sound(mcLoc("mob/piglin/death2")).volume(0.7), sound(mcLoc("mob/piglin/death3")).volume(0.8), sound(mcLoc("mob/piglin/death4")).volume(0.8)).subtitle("subtitles.entity." + EternalStarlight.ID + ".boarwarf.death"));
		add(ESSoundEvents.BOARWARF_TRADE.get(), definition().with(sound(mcLoc("mob/piglin/admire1")).volume(0.8), sound(mcLoc("mob/piglin/admire2")).volume(0.8), sound(mcLoc("mob/piglin/celebrate2")).volume(0.8), sound(mcLoc("mob/piglin/celebrate4")).volume(0.85)).subtitle("subtitles.entity." + EternalStarlight.ID + ".boarwarf.trade"));
		add(ESSoundEvents.BOARWARF_YES.get(), definition().with(sound(mcLoc("mob/piglin/celebrate1")).volume(0.8), sound(mcLoc("mob/piglin/celebrate2")).volume(0.8), sound(mcLoc("mob/piglin/celebrate3")).volume(0.75), sound(mcLoc("mob/piglin/celebrate4")).volume(0.8)).subtitle("subtitles.entity." + EternalStarlight.ID + ".boarwarf.yes"));
		add(ESSoundEvents.BOARWARF_NO.get(), definition().with(sound(mcLoc("mob/piglin/angry1")).volume(0.7), sound(mcLoc("mob/piglin/angry2")).volume(0.7), sound(mcLoc("mob/piglin/angry3")).volume(0.7), sound(mcLoc("mob/piglin/angry4")).volume(0.7)).subtitle("subtitles.entity." + EternalStarlight.ID + ".boarwarf.no"));

		add(ESSoundEvents.ASTRAL_GOLEM_ATTACK.get(), definition().with(sound(mcLoc("mob/irongolem/throw"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".astral_golem.attack"));
		add(ESSoundEvents.ASTRAL_GOLEM_HURT.get(), definition().with(sound(mcLoc("mob/irongolem/hit1")), sound(mcLoc("mob/irongolem/hit2")), sound(mcLoc("mob/irongolem/hit3")), sound(mcLoc("mob/irongolem/hit4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".astral_golem.hurt"));
		add(ESSoundEvents.ASTRAL_GOLEM_DEATH.get(), definition().with(sound(mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".astral_golem.death"));
		add(ESSoundEvents.ASTRAL_GOLEM_REPAIR.get(), definition().with(sound(mcLoc("mob/irongolem/repair"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".astral_golem.repair"));

		add(ESSoundEvents.ENT_HURT.get(), definition().with(sound(loc("mob/ent/hurt"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".ent.hurt"));

		add(ESSoundEvents.AURORA_DEER_AMBIENT.get(), definition().with(sound(loc("mob/aurora_deer/idle1")), sound(loc("mob/aurora_deer/idle2")), sound(loc("mob/aurora_deer/idle3"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".aurora_deer.ambient"));
		add(ESSoundEvents.AURORA_DEER_HURT.get(), definition().with(sound(loc("mob/aurora_deer/hurt1")), sound(loc("mob/aurora_deer/hurt2"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".aurora_deer.hurt"));
		add(ESSoundEvents.AURORA_DEER_DEATH.get(), definition().with(sound(loc("mob/aurora_deer/hurt1"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".aurora_deer.death"));

		add(ESSoundEvents.STARFIRE_BIRD_AMBIENT.get(), definition().with(sound(loc("mob/starfire_bird/idle"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starfire_bird.ambient"));
		add(ESSoundEvents.STARFIRE_BIRD_HURT.get(), definition().with(sound(loc("mob/starfire_bird/hurt"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starfire_bird.hurt"));
		add(ESSoundEvents.STARFIRE_BIRD_DEATH.get(), definition().with(sound(loc("mob/starfire_bird/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starfire_bird.death"));

		add(ESSoundEvents.AETHERSENT_GOLEM_HURT.get(), definition().with(sound(mcLoc("mob/irongolem/damage1")).volume(0.8), sound(mcLoc("mob/irongolem/damage1")).pitch(0.8).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(0.8).volume(0.8)).subtitle("subtitles.entity." + EternalStarlight.ID + ".aethersent_golem.hurt"));
		add(ESSoundEvents.AETHERSENT_GOLEM_DEATH.get(), definition().with(sound(mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".aethersent_golem.death"));
		add(ESSoundEvents.AETHERSENT_GOLEM_SHOOT.get(), definition().with(sound(loc("mob/aethersent_golem/shoot"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".aethersent_golem.shoot"));

		add(ESSoundEvents.SEEKER_HURT.get(), definition().with(sound(mcLoc("mob/slime/big1")), sound(mcLoc("mob/slime/big2")), sound(mcLoc("mob/slime/big3")), sound(mcLoc("mob/slime/big4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".seeker.hurt"));
		add(ESSoundEvents.SEEKER_DEATH.get(), definition().with(sound(mcLoc("mob/slime/big1")), sound(mcLoc("mob/slime/big2")), sound(mcLoc("mob/slime/big3")), sound(mcLoc("mob/slime/big4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".seeker.death"));

		add(ESSoundEvents.THIRST_WALKER_AMBIENT.get(), definition().with(sound(loc("mob/thirst_walker/idle1")), sound(loc("mob/thirst_walker/idle2")), sound(loc("mob/thirst_walker/idle3"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".thirst_walker.ambient"));
		add(ESSoundEvents.THIRST_WALKER_HURT.get(), definition().with(sound(loc("mob/thirst_walker/hurt1")), sound(loc("mob/thirst_walker/hurt2"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".thirst_walker.hurt"));
		add(ESSoundEvents.THIRST_WALKER_DEATH.get(), definition().with(sound(loc("mob/thirst_walker/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".thirst_walker.death"));

		add(ESSoundEvents.CRETEOR_ROLL.get(), definition().with(sound(loc("mob/creteor/roll"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".creteor.roll"));
		add(ESSoundEvents.CRETEOR_HURT.get(), definition().with(sound(loc("mob/creteor/hurt1")), sound(loc("mob/creteor/hurt2")), sound(loc("mob/creteor/hurt3")), sound(loc("mob/creteor/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".creteor.hurt"));
		add(ESSoundEvents.CRETEOR_DEATH.get(), definition().with(sound(loc("mob/creteor/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".creteor.death"));
		add(ESSoundEvents.CRETEOR_CHARGE.get(), definition().with(sound(loc("mob/creteor/charge"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".creteor.charge"));
		add(ESSoundEvents.CRETEOR_EXPLODE.get(), definition().with(sound(loc("mob/creteor/explode"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".creteor.explode"));

		add(ESSoundEvents.STRANGHOUL_AMBIENT.get(), definition().with(sound(loc("mob/stranghoul/idle1")), sound(loc("mob/stranghoul/idle2"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".stranghoul.ambient"));
		add(ESSoundEvents.STRANGHOUL_HURT.get(), definition().with(sound(loc("mob/stranghoul/hurt1")), sound(loc("mob/stranghoul/hurt2"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".stranghoul.hurt"));
		add(ESSoundEvents.STRANGHOUL_DEATH.get(), definition().with(sound(loc("mob/stranghoul/death1")), sound(loc("mob/stranghoul/death2"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".stranghoul.death"));
		add(ESSoundEvents.STRANGHOUL_SHOOT.get(), definition().with(sound(mcLoc("random/bow"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".stranghoul.shoot"));

		add(ESSoundEvents.LUMINOFISH_HURT.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminofish.hurt"));
		add(ESSoundEvents.LUMINOFISH_DEATH.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminofish.death"));
		add(ESSoundEvents.LUMINOFISH_FLOP.get(), definition().with(sound(mcLoc("entity/fish/flop1")).volume(0.3), sound(mcLoc("entity/fish/flop2")).volume(0.3), sound(mcLoc("entity/fish/flop3")).volume(0.3), sound(mcLoc("entity/fish/flop4")).volume(0.3)).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminofish.flop"));

		add(ESSoundEvents.LUMINARIS_HURT.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.hurt"));
		add(ESSoundEvents.LUMINARIS_DEATH.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.death"));
		add(ESSoundEvents.LUMINARIS_FLOP.get(), definition().with(sound(mcLoc("entity/fish/flop1")).volume(0.3), sound(mcLoc("entity/fish/flop2")).volume(0.3), sound(mcLoc("entity/fish/flop3")).volume(0.3), sound(mcLoc("entity/fish/flop4")).volume(0.3)).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.flop"));

		add(ESSoundEvents.TWILIGHT_GAZE_HURT.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.hurt"));
		add(ESSoundEvents.TWILIGHT_GAZE_DEATH.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.death"));

		add(ESSoundEvents.THE_GATEKEEPER_PARRY.get(), definition().with(sound(loc("mob/the_gatekeeper/parry"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".the_gatekeeper.parry"));

		add(ESSoundEvents.STARLIGHT_GOLEM_HURT.get(), definition().with(sound(loc("mob/starlight_golem/hurt"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.hurt"));
		add(ESSoundEvents.STARLIGHT_GOLEM_DEATH.get(), definition().with(sound(mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.death"));
		add(ESSoundEvents.STARLIGHT_GOLEM_BLOCK.get(), definition().with(sound(loc("mob/starlight_golem/block"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.block"));
		add(ESSoundEvents.STARLIGHT_GOLEM_PREPARE_CHARGE.get(), definition().with(sound(mcLoc("mob/irongolem/repair"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.prepare_charge"));
		add(ESSoundEvents.STARLIGHT_GOLEM_PREPARE_BEAM.get(), definition().with(sound(mcLoc("block/beacon/activate"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.prepare_beam"));

		add(ESSoundEvents.FREEZE_HURT.get(), definition().with(sound(loc("mob/freeze/hurt"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".freeze.hurt"));
		add(ESSoundEvents.FREEZE_DEATH.get(), definition().with(sound(loc("mob/freeze/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".freeze.death"));

		add(ESSoundEvents.PERMAFROST_HURT.get(), definition().with(sound(loc("mob/freeze/hurt"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".permafrost.hurt"));
		add(ESSoundEvents.PERMAFROST_DEATH.get(), definition().with(sound(loc("mob/freeze/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".permafrost.death"));
		add(ESSoundEvents.PERMAFROST_SNEEZE.get(), definition().with(sound(loc("mob/permafrost/sneeze"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".permafrost.sneeze"));

		add(ESSoundEvents.LUNAR_MONSTROSITY_HURT.get(), definition().with(sound(mcLoc("mob/irongolem/damage1")).pitch(1.5).volume(0.8), sound(mcLoc("mob/irongolem/damage1")).pitch(1.2).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(1.5).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(1.2).volume(0.8)).subtitle("subtitles.entity." + EternalStarlight.ID + ".lunar_monstrosity.hurt"));
		add(ESSoundEvents.LUNAR_MONSTROSITY_DEATH.get(), definition().with(sound(loc("mob/lunar_monstrosity/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".lunar_monstrosity.death"));
		add(ESSoundEvents.LUNAR_MONSTROSITY_BITE.get(), definition().with(sound(mcLoc("mob/evocation_illager/fangs"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".lunar_monstrosity.bite"));
		add(ESSoundEvents.LUNAR_MONSTROSITY_ROAR.get(), definition().with(sound(loc("mob/lunar_monstrosity/roar"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".lunar_monstrosity.roar"));

		add(ESSoundEvents.TANGLED_SKULL_AMBIENT.get(), definition().with(sound(loc("mob/tangled_skull/idle"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".tangled_skull.ambient"));
		add(ESSoundEvents.TANGLED_SKULL_ROAR.get(), definition().with(sound(loc("mob/tangled_skull/roar"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".tangled_skull.roar"));

		add(ESSoundEvents.ETHER_TRANSFORM.get(), definition().with(sound(loc("block/ether/transform"))).subtitle("subtitles.block." + EternalStarlight.ID + ".ether.transform"));
		add(ESSoundEvents.STELLAR_RACK_AMBIENT.get(), definition().with(sound(loc("block/stellar_rack/idle"))).subtitle("subtitles.block." + EternalStarlight.ID + ".stellar_rack.ambient"));
	}
}
