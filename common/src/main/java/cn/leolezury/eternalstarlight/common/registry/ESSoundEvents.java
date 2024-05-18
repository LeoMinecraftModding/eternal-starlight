package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class ESSoundEvents {
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, EternalStarlight.ID);
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_DIMENSION_TRANQUILITY = register("music.dimension.tranquility");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_STARLIGHT_FOREST = register("music.biome.starlight_forest");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_DARK_SWAMP = register("music.biome.dark_swamp");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_STARLIGHT_PERMAFROST_FOREST = register("music.biome.starlight_permafrost_forest");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_SCARLET_FOREST = register("music.biome.scarlet_forest");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_TORREYA_FOREST = register("music.biome.torreya_forest");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_STARLIT_SEA = register("music.biome.starlit_sea");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BIOME_THE_ABYSS = register("music.biome.the_abyss");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BOSS = register("music.boss.common");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BOSS_GATEKEEPER = register("music.boss.gatekeeper");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BOSS_STARLIGHT_GOLEM = register("music.boss.starlight_golem");
    public static final RegistryObject<SoundEvent, SoundEvent> MUSIC_BOSS_LUNAR_MONSTROSITY = register("music.boss.lunar_monstrosity");

    public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_AMARAMBER = register("item.armor.equip_amaramber");
    public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_AETHERSENT = register("item.armor.equip_aethersent");
    public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_THERMAL_SPRINGSTONE = register("item.armor.equip_thermal_springstone");
    public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_GLACITE = register("item.armor.equip_glacite");
    public static final RegistryObject<SoundEvent, SoundEvent> ARMOR_EQUIP_SWAMP_SILVER = register("item.armor.equip_swamp_silver");

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

    public static final RegistryObject<SoundEvent, SoundEvent> THIRST_WALKER_AMBIENT = register("entity.thirst_walker.ambient");
    public static final RegistryObject<SoundEvent, SoundEvent> THIRST_WALKER_HURT = register("entity.thirst_walker.hurt");
    public static final RegistryObject<SoundEvent, SoundEvent> THIRST_WALKER_DEATH = register("entity.thirst_walker.death");

    public static final RegistryObject<SoundEvent, SoundEvent> LUMINOFISH_HURT = register("entity.luminofish.hurt");
    public static final RegistryObject<SoundEvent, SoundEvent> LUMINOFISH_DEATH = register("entity.luminofish.death");
    public static final RegistryObject<SoundEvent, SoundEvent> LUMINOFISH_FLOP = register("entity.luminofish.flop");

    public static final RegistryObject<SoundEvent, SoundEvent> LUMINARIS_HURT = register("entity.luminaris.hurt");
    public static final RegistryObject<SoundEvent, SoundEvent> LUMINARIS_DEATH = register("entity.luminaris.death");
    public static final RegistryObject<SoundEvent, SoundEvent> LUMINARIS_FLOP = register("entity.luminaris.flop");

    public static final RegistryObject<SoundEvent, SoundEvent> TWILIGHT_GAZE_HURT = register("entity.twilight_gaze.hurt");
    public static final RegistryObject<SoundEvent, SoundEvent> TWILIGHT_GAZE_DEATH = register("entity.twilight_gaze.death");

    public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_HURT = register("entity.starlight_golem.hurt");
    public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_DEATH = register("entity.starlight_golem.death");
    public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_BLOCK = register("entity.starlight_golem.block");
    public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_PREPARE_BEAM = register("entity.starlight_golem.prepare_beam");
    public static final RegistryObject<SoundEvent, SoundEvent> STARLIGHT_GOLEM_PREPARE_CHARGE = register("entity.starlight_golem.prepare_charge");

    public static final RegistryObject<SoundEvent, SoundEvent> LUNAR_MONSTROSITY_HURT = register("entity.lunar_monstrosity.hurt");
    public static final RegistryObject<SoundEvent, SoundEvent> LUNAR_MONSTROSITY_DEATH = register("entity.lunar_monstrosity.death");
    public static final RegistryObject<SoundEvent, SoundEvent> LUNAR_MONSTROSITY_BITE = register("entity.lunar_monstrosity.bite");
    public static final RegistryObject<SoundEvent, SoundEvent> LUNAR_MONSTROSITY_ROAR = register("entity.lunar_monstrosity.roar");

    public static RegistryObject<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(EternalStarlight.id(name)));
    }

    public static void loadClass() {}
}
