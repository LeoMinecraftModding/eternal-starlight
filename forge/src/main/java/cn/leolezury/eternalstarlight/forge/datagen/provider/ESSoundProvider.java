package cn.leolezury.eternalstarlight.forge.datagen.provider;

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
        add(ESSoundEvents.MUSIC_DIMENSION_TRANQUILITY.get(), definition().with(sound(loc("music/dimension/tranquility")).stream()));
        add(ESSoundEvents.MUSIC_BIOME_STARLIGHT_FOREST.get(), definition().with(sound(loc("music/biome/starlight_forest")).stream(), sound(loc("music/dimension/tranquility")).stream()));
        add(ESSoundEvents.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.get(), definition().with(sound(loc("music/biome/starlight_permafrost_forest")).stream(), sound(loc("music/dimension/tranquility")).stream()));
        add(ESSoundEvents.MUSIC_BIOME_DARK_SWAMP.get(), definition().with(sound(loc("music/biome/dark_swamp")).stream(), sound(loc("music/dimension/tranquility")).stream()));
        add(ESSoundEvents.MUSIC_BIOME_SCARLET_FOREST.get(), definition().with(sound(loc("music/biome/scarlet_forest")).stream(), sound(loc("music/dimension/tranquility")).stream()));
        add(ESSoundEvents.MUSIC_BIOME_TORREYA_FOREST.get(), definition().with(sound(loc("music/biome/torreya_forest")).stream(), sound(loc("music/dimension/tranquility")).stream()));
        add(ESSoundEvents.MUSIC_BIOME_STARLIT_SEA.get(), definition().with(sound(loc("music/biome/starlit_sea")).stream()));
        add(ESSoundEvents.MUSIC_BIOME_THE_ABYSS.get(), definition().with(sound(loc("music/biome/atlantis")).stream()));
        add(ESSoundEvents.MUSIC_BOSS.get(), definition().with(sound(loc("music/boss/common")).stream()));
        add(ESSoundEvents.MUSIC_BOSS_GATEKEEPER.get(), definition().with(sound(loc("music/boss/gatekeeper")).stream()));
        add(ESSoundEvents.MUSIC_BOSS_STARLIGHT_GOLEM.get(), definition().with(sound(loc("music/boss/starlight_golem")).stream()));
        add(ESSoundEvents.MUSIC_BOSS_LUNAR_MONSTROSITY.get(), definition().with(sound(loc("music/boss/lunar_monstrosity")).stream()));

        add(ESSoundEvents.ARMOR_EQUIP_ALCHEMIST.get(), definition().with(sound(mcLoc("item/armor/equip_leather1")), sound(mcLoc("item/armor/equip_leather2")), sound(mcLoc("item/armor/equip_leather3")), sound(mcLoc("item/armor/equip_leather4")), sound(mcLoc("item/armor/equip_leather5")), sound(mcLoc("item/armor/equip_leather6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_alchemist"));
        add(ESSoundEvents.ARMOR_EQUIP_AMARAMBER.get(), definition().with(sound(mcLoc("item/armor/equip_leather1")), sound(mcLoc("item/armor/equip_leather2")), sound(mcLoc("item/armor/equip_leather3")), sound(mcLoc("item/armor/equip_leather4")), sound(mcLoc("item/armor/equip_leather5")), sound(mcLoc("item/armor/equip_leather6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_amaramber"));
        add(ESSoundEvents.ARMOR_EQUIP_AETHERSENT.get(), definition().with(sound(mcLoc("item/armor/equip_leather1")), sound(mcLoc("item/armor/equip_leather2")), sound(mcLoc("item/armor/equip_leather3")), sound(mcLoc("item/armor/equip_leather4")), sound(mcLoc("item/armor/equip_leather5")), sound(mcLoc("item/armor/equip_leather6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_aethersent"));
        add(ESSoundEvents.ARMOR_EQUIP_THERMAL_SPRINGSTONE.get(), definition().with(sound(mcLoc("item/armor/equip_iron1")), sound(mcLoc("item/armor/equip_iron2")), sound(mcLoc("item/armor/equip_iron3")), sound(mcLoc("item/armor/equip_iron4")), sound(mcLoc("item/armor/equip_iron5")), sound(mcLoc("item/armor/equip_iron6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_thermal_springstone"));
        add(ESSoundEvents.ARMOR_EQUIP_GLACITE.get(), definition().with(sound(mcLoc("item/armor/equip_iron1")), sound(mcLoc("item/armor/equip_iron2")), sound(mcLoc("item/armor/equip_iron3")), sound(mcLoc("item/armor/equip_iron4")), sound(mcLoc("item/armor/equip_iron5")), sound(mcLoc("item/armor/equip_iron6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_glacite"));
        add(ESSoundEvents.ARMOR_EQUIP_SWAMP_SILVER.get(), definition().with(sound(mcLoc("item/armor/equip_diamond1")), sound(mcLoc("item/armor/equip_diamond2")), sound(mcLoc("item/armor/equip_diamond3")), sound(mcLoc("item/armor/equip_diamond4")), sound(mcLoc("item/armor/equip_diamond5")), sound(mcLoc("item/armor/equip_diamond6"))).subtitle("subtitles.item.armor." + EternalStarlight.ID + ".equip_swamp_silver"));

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
        
        add(ESSoundEvents.THIRST_WALKER_AMBIENT.get(), definition().with(sound(loc("mob/thirst_walker/idle1")), sound(loc("mob/thirst_walker/idle2")), sound(loc("mob/thirst_walker/idle3"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".thirst_walker.ambient"));
        add(ESSoundEvents.THIRST_WALKER_HURT.get(), definition().with(sound(loc("mob/thirst_walker/hurt1")), sound(loc("mob/thirst_walker/hurt2"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".thirst_walker.hurt"));
        add(ESSoundEvents.THIRST_WALKER_DEATH.get(), definition().with(sound(loc("mob/thirst_walker/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".thirst_walker.death"));

        add(ESSoundEvents.LUMINOFISH_HURT.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminofish.hurt"));
        add(ESSoundEvents.LUMINOFISH_DEATH.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminofish.death"));
        add(ESSoundEvents.LUMINOFISH_FLOP.get(), definition().with(sound(mcLoc("entity/fish/flop1")).volume(0.3), sound(mcLoc("entity/fish/flop2")).volume(0.3), sound(mcLoc("entity/fish/flop3")).volume(0.3), sound(mcLoc("entity/fish/flop4")).volume(0.3)).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminofish.flop"));

        add(ESSoundEvents.LUMINARIS_HURT.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.hurt"));
        add(ESSoundEvents.LUMINARIS_DEATH.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.death"));
        add(ESSoundEvents.LUMINARIS_FLOP.get(), definition().with(sound(mcLoc("entity/fish/flop1")).volume(0.3), sound(mcLoc("entity/fish/flop2")).volume(0.3), sound(mcLoc("entity/fish/flop3")).volume(0.3), sound(mcLoc("entity/fish/flop4")).volume(0.3)).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.flop"));

        add(ESSoundEvents.TWILIGHT_GAZE_HURT.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.hurt"));
        add(ESSoundEvents.TWILIGHT_GAZE_DEATH.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".luminaris.death"));

        add(ESSoundEvents.STARLIGHT_GOLEM_HURT.get(), definition().with(sound(mcLoc("mob/irongolem/damage1")).volume(0.8), sound(mcLoc("mob/irongolem/damage1")).pitch(0.8).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(0.8).volume(0.8)).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.hurt"));
        add(ESSoundEvents.STARLIGHT_GOLEM_DEATH.get(), definition().with(sound(mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.death"));
        add(ESSoundEvents.STARLIGHT_GOLEM_BLOCK.get(), definition().with(sound(mcLoc("random/anvil_land"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.block"));
        add(ESSoundEvents.STARLIGHT_GOLEM_PREPARE_CHARGE.get(), definition().with(sound(mcLoc("mob/irongolem/repair"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.prepare_charge"));
        add(ESSoundEvents.STARLIGHT_GOLEM_PREPARE_BEAM.get(), definition().with(sound(mcLoc("block/beacon/activate"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".starlight_golem.prepare_beam"));

        add(ESSoundEvents.LUNAR_MONSTROSITY_HURT.get(), definition().with(sound(mcLoc("mob/irongolem/damage1")).pitch(1.5).volume(0.8), sound(mcLoc("mob/irongolem/damage1")).pitch(1.2).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(1.5).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(1.2).volume(0.8)).subtitle("subtitles.entity." + EternalStarlight.ID + ".lunar_monstrosity.hurt"));
        add(ESSoundEvents.LUNAR_MONSTROSITY_DEATH.get(), definition().with(sound(mcLoc("mob/blaze/death"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".lunar_monstrosity.death"));
        add(ESSoundEvents.LUNAR_MONSTROSITY_BITE.get(), definition().with(sound(mcLoc("mob/evocation_illager/fangs"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".lunar_monstrosity.bite"));
        add(ESSoundEvents.LUNAR_MONSTROSITY_ROAR.get(), definition().with(sound(loc("mob/lunar_monstrosity/roar"))).subtitle("subtitles.entity." + EternalStarlight.ID + ".lunar_monstrosity.roar"));
    }
}
