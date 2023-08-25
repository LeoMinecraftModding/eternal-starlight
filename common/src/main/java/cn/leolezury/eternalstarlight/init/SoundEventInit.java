package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.util.register.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundEventInit {
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, EternalStarlight.MOD_ID);
    public static final RegistryObject<SoundEvent> MUSIC_DIMENSION_SL = register("music.dimension.eternal_starlight");
    public static final RegistryObject<SoundEvent> MUSIC_BOSS = register("music.boss.common");

    public static final RegistryObject<SoundEvent> ARMOR_EQUIP_AETHERSENT = register("item.armor.equip_aethersent");
    public static final RegistryObject<SoundEvent> ARMOR_EQUIP_THERMAL_SPRINGSTONE = register("item.armor.equip_thermal_springstone");
    public static final RegistryObject<SoundEvent> ARMOR_EQUIP_SWAMP_SILVER = register("item.armor.equip_swamp_silver");

    public static final RegistryObject<SoundEvent> SEEKING_EYE_LAUNCH = register("entity.seeking_eye.launch");
    public static final RegistryObject<SoundEvent> SEEKING_EYE_DEATH = register("entity.seeking_eye.death");

    public static final RegistryObject<SoundEvent> BOARWARF_AMBIENT = register("entity.boarwarf.ambient");
    public static final RegistryObject<SoundEvent> BOARWARF_HURT = register("entity.boarwarf.hurt");
    public static final RegistryObject<SoundEvent> BOARWARF_DEATH = register("entity.boarwarf.death");
    public static final RegistryObject<SoundEvent> BOARWARF_TRADE = register("entity.boarwarf.trade");
    public static final RegistryObject<SoundEvent> BOARWARF_YES = register("entity.boarwarf.yes");
    public static final RegistryObject<SoundEvent> BOARWARF_NO = register("entity.boarwarf.no");


    public static final RegistryObject<SoundEvent> ASTRAL_GOLEM_ATTACK = register("entity.astral_golem.attack");
    public static final RegistryObject<SoundEvent> ASTRAL_GOLEM_HURT = register("entity.astral_golem.hurt");
    public static final RegistryObject<SoundEvent> ASTRAL_GOLEM_DEATH = register("entity.astral_golem.death");
    public static final RegistryObject<SoundEvent> ASTRAL_GOLEM_REPAIR = register("entity.astral_golem.repair");

    public static final RegistryObject<SoundEvent> LONESTAR_SKELETON_AMBIENT = register("entity.lonestar_skeleton.ambient");
    public static final RegistryObject<SoundEvent> LONESTAR_SKELETON_HURT = register("entity.lonestar_skeleton.hurt");
    public static final RegistryObject<SoundEvent> LONESTAR_SKELETON_DEATH = register("entity.lonestar_skeleton.death");

    public static final RegistryObject<SoundEvent> DRYAD_HURT = register("entity.dryad.hurt");

    public static final RegistryObject<SoundEvent> TWILIGHT_SQUID_AMBIENT = register("entity.twilight_squid.ambient");
    public static final RegistryObject<SoundEvent> TWILIGHT_SQUID_HURT = register("entity.twilight_squid.hurt");
    public static final RegistryObject<SoundEvent> TWILIGHT_SQUID_DEATH = register("entity.twilight_squid.death");
    public static final RegistryObject<SoundEvent> TWILIGHT_SQUID_SQUIRT = register("entity.twilight_squid.squirt");

    public static final RegistryObject<SoundEvent> STARLIGHT_GOLEM_HURT = register("entity.starlight_golem.hurt");
    public static final RegistryObject<SoundEvent> STARLIGHT_GOLEM_DEATH = register("entity.starlight_golem.death");
    public static final RegistryObject<SoundEvent> STARLIGHT_GOLEM_BLOCK = register("entity.starlight_golem.block");
    public static final RegistryObject<SoundEvent> STARLIGHT_GOLEM_PREPARE_BEAM = register("entity.starlight_golem.prepare_beam");
    public static final RegistryObject<SoundEvent> STARLIGHT_GOLEM_PREPARE_CHARGE = register("entity.starlight_golem.prepare_charge");

    public static final RegistryObject<SoundEvent> FIRE_COLUMN_APPEAR = register("entity.fire_column.appear");

    public static final RegistryObject<SoundEvent> LUNAR_MONSTROSITY_HURT = register("entity.lunar_monstrosity.hurt");
    public static final RegistryObject<SoundEvent> LUNAR_MONSTROSITY_DEATH = register("entity.lunar_monstrosity.death");
    public static final RegistryObject<SoundEvent> LUNAR_MONSTROSITY_BITE = register("entity.lunar_monstrosity.bite");
    public static final RegistryObject<SoundEvent> LUNAR_MONSTROSITY_ROAR = register("entity.lunar_monstrosity.roar");

    public static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(EternalStarlight.MOD_ID, name)));
    }

    public static void postRegistry() {}
}
