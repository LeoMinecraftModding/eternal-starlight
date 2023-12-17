package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.SoundEventInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ESSoundProvider extends SoundDefinitionsProvider {
    public ESSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, EternalStarlight.MOD_ID, helper);
    }

    private ResourceLocation mcLoc(String s) {
        return new ResourceLocation(s);
    }

    private ResourceLocation loc(String s) {
        return new ResourceLocation(EternalStarlight.MOD_ID, s);
    }

    @Override
    public void registerSounds() {
        add(SoundEventInit.MUSIC_DIMENSION_SL.get(), definition().with(sound(loc("music/dimension/default")).stream()));
        add(SoundEventInit.MUSIC_BIOME_STARLIGHT_FOREST.get(), definition().with(sound(loc("music/biome/starlight_forest")).stream()));
        add(SoundEventInit.MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST.get(), definition().with(sound(loc("music/biome/starlight_permafrost_forest")).stream()));
        add(SoundEventInit.MUSIC_BIOME_DARK_SWAMP.get(), definition().with(sound(loc("music/biome/dark_swamp")).stream()));
        add(SoundEventInit.MUSIC_BOSS.get(), definition().with(sound(loc("music/boss/common")).stream()));

        add(SoundEventInit.ARMOR_EQUIP_AETHERSENT.get(), definition().with(sound(mcLoc("item/armor/equip_leather1")), sound(mcLoc("item/armor/equip_leather2")), sound(mcLoc("item/armor/equip_leather3")), sound(mcLoc("item/armor/equip_leather4")), sound(mcLoc("item/armor/equip_leather5")), sound(mcLoc("item/armor/equip_leather6"))).subtitle("subtitles.item.armor.equip_aethersent"));
        add(SoundEventInit.ARMOR_EQUIP_THERMAL_SPRINGSTONE.get(), definition().with(sound(mcLoc("item/armor/equip_iron1")), sound(mcLoc("item/armor/equip_iron2")), sound(mcLoc("item/armor/equip_iron3")), sound(mcLoc("item/armor/equip_iron4")), sound(mcLoc("item/armor/equip_iron5")), sound(mcLoc("item/armor/equip_iron6"))).subtitle("subtitles.item.armor.equip_thermal_springstone"));
        add(SoundEventInit.ARMOR_EQUIP_SWAMP_SILVER.get(), definition().with(sound(mcLoc("item/armor/equip_diamond1")), sound(mcLoc("item/armor/equip_diamond2")), sound(mcLoc("item/armor/equip_diamond3")), sound(mcLoc("item/armor/equip_diamond4")), sound(mcLoc("item/armor/equip_diamond5")), sound(mcLoc("item/armor/equip_diamond6"))).subtitle("subtitles.item.armor.equip_swamp_silver"));

        add(SoundEventInit.SEEKING_EYE_LAUNCH.get(), definition().with(sound(mcLoc("entity/endereye/endereye_launch1")), sound(mcLoc("entity/endereye/endereye_launch2"))).subtitle("subtitles.entity.seeking_eye.launch"));
        add(SoundEventInit.SEEKING_EYE_DEATH.get(), definition().with(sound(mcLoc("entity/endereye/dead1")).volume(1.3), sound(mcLoc("entity/endereye/dead2")).volume(1.3)).subtitle("subtitles.entity.seeking_eye.death"));

        add(SoundEventInit.BOARWARF_AMBIENT.get(), definition().with(sound(mcLoc("mob/piglin/idle1")).volume(0.66), sound(mcLoc("mob/piglin/idle2")).volume(0.66), sound(mcLoc("mob/piglin/idle3")).volume(0.66), sound(mcLoc("mob/piglin/idle4")).volume(0.66), sound(mcLoc("mob/piglin/idle5")).volume(0.66)).subtitle("subtitles.entity.boarwarf.ambient"));
        add(SoundEventInit.BOARWARF_HURT.get(), definition().with(sound(mcLoc("mob/piglin/hurt1")).volume(0.7), sound(mcLoc("mob/piglin/hurt2")).volume(0.7), sound(mcLoc("mob/piglin/hurt3")).volume(0.7)).subtitle("subtitles.entity.boarwarf.hurt"));
        add(SoundEventInit.BOARWARF_DEATH.get(), definition().with(sound(mcLoc("mob/piglin/death1")).volume(0.7), sound(mcLoc("mob/piglin/death2")).volume(0.7), sound(mcLoc("mob/piglin/death3")).volume(0.8), sound(mcLoc("mob/piglin/death4")).volume(0.8)).subtitle("subtitles.entity.boarwarf.death"));
        add(SoundEventInit.BOARWARF_TRADE.get(), definition().with(sound(mcLoc("mob/piglin/admire1")).volume(0.8), sound(mcLoc("mob/piglin/admire2")).volume(0.8), sound(mcLoc("mob/piglin/celebrate2")).volume(0.8), sound(mcLoc("mob/piglin/celebrate4")).volume(0.85)).subtitle("subtitles.entity.boarwarf.trade"));
        add(SoundEventInit.BOARWARF_YES.get(), definition().with(sound(mcLoc("mob/piglin/celebrate1")).volume(0.8), sound(mcLoc("mob/piglin/celebrate2")).volume(0.8), sound(mcLoc("mob/piglin/celebrate3")).volume(0.75), sound(mcLoc("mob/piglin/celebrate4")).volume(0.8)).subtitle("subtitles.entity.boarwarf.yes"));
        add(SoundEventInit.BOARWARF_NO.get(), definition().with(sound(mcLoc("mob/piglin/angry1")).volume(0.7), sound(mcLoc("mob/piglin/angry2")).volume(0.7), sound(mcLoc("mob/piglin/angry3")).volume(0.7), sound(mcLoc("mob/piglin/angry4")).volume(0.7)).subtitle("subtitles.entity.boarwarf.no"));

        add(SoundEventInit.ASTRAL_GOLEM_ATTACK.get(), definition().with(sound(mcLoc("mob/irongolem/throw"))).subtitle("subtitles.entity.astral_golem.attack"));
        add(SoundEventInit.ASTRAL_GOLEM_HURT.get(), definition().with(sound(mcLoc("mob/irongolem/hit1")), sound(mcLoc("mob/irongolem/hit2")), sound(mcLoc("mob/irongolem/hit3")), sound(mcLoc("mob/irongolem/hit4"))).subtitle("subtitles.entity.astral_golem.hurt"));
        add(SoundEventInit.ASTRAL_GOLEM_DEATH.get(), definition().with(sound(mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity.astral_golem.death"));
        add(SoundEventInit.ASTRAL_GOLEM_REPAIR.get(), definition().with(sound(mcLoc("mob/irongolem/repair"))).subtitle("subtitles.entity.astral_golem.repair"));

        add(SoundEventInit.DRYAD_HURT.get(), definition().with(sound(loc("mob/dryad_hurt"))).subtitle("subtitles.entity.dryad.hurt"));

        add(SoundEventInit.LUMINO_FISH_HURT.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity.lumino_fish.hurt"));
        add(SoundEventInit.LUMINO_FISH_DEATH.get(), definition().with(sound(mcLoc("entity/fish/hurt1")), sound(mcLoc("entity/fish/hurt2")), sound(mcLoc("entity/fish/hurt3")), sound(mcLoc("entity/fish/hurt4"))).subtitle("subtitles.entity.lumino_fish.death"));
        add(SoundEventInit.LUMINO_FISH_FLOP.get(), definition().with(sound(mcLoc("entity/fish/flop1")).volume(0.3), sound(mcLoc("entity/fish/flop2")).volume(0.3), sound(mcLoc("entity/fish/flop3")).volume(0.3), sound(mcLoc("entity/fish/flop4")).volume(0.3)).subtitle("subtitles.entity.lumino_fish.flop"));

        add(SoundEventInit.STARLIGHT_GOLEM_HURT.get(), definition().with(sound(mcLoc("mob/irongolem/damage1")).volume(0.8), sound(mcLoc("mob/irongolem/damage1")).pitch(0.8).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(0.8).volume(0.8)).subtitle("subtitles.entity.starlight_golem.hurt"));
        add(SoundEventInit.STARLIGHT_GOLEM_DEATH.get(), definition().with(sound(mcLoc("mob/irongolem/death"))).subtitle("subtitles.entity.starlight_golem.death"));
        add(SoundEventInit.STARLIGHT_GOLEM_BLOCK.get(), definition().with(sound(mcLoc("random/anvil_land"))).subtitle("subtitles.entity.starlight_golem.block"));
        add(SoundEventInit.STARLIGHT_GOLEM_PREPARE_CHARGE.get(), definition().with(sound(mcLoc("mob/irongolem/repair"))).subtitle("subtitles.entity.astral_golem.prepare_charge"));
        add(SoundEventInit.STARLIGHT_GOLEM_PREPARE_BEAM.get(), definition().with(sound(mcLoc("block/beacon/activate"))).subtitle("subtitles.entity.astral_golem.prepare_beam"));

        add(SoundEventInit.LUNAR_MONSTROSITY_HURT.get(), definition().with(sound(mcLoc("mob/irongolem/damage1")).pitch(1.5).volume(0.8), sound(mcLoc("mob/irongolem/damage1")).pitch(1.2).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(1.5).volume(0.8), sound(mcLoc("mob/irongolem/damage2")).pitch(1.2).volume(0.8)).subtitle("subtitles.entity.lunar_monstrosity.hurt"));
        add(SoundEventInit.LUNAR_MONSTROSITY_DEATH.get(), definition().with(sound(mcLoc("mob/blaze/death"))).subtitle("subtitles.entity.lunar_monstrosity.death"));
        add(SoundEventInit.LUNAR_MONSTROSITY_BITE.get(), definition().with(sound(mcLoc("mob/evocation_illager/fangs"))).subtitle("subtitles.entity.lunar_monstrosity.bite"));
        add(SoundEventInit.LUNAR_MONSTROSITY_ROAR.get(), definition().with(sound(loc("mob/lunar_monstrosity_roar"))).subtitle("subtitles.entity.lunar_monstrosity.roar"));
    }
}
