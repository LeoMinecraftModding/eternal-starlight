package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.profession.*;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;

public class ESBoarwarfProfessions {
    public static final RegistrationProvider<AbstractBoarwarfProfession> PROFESSIONS = RegistrationProvider.newRegistry(ResourceKey.createRegistryKey(EternalStarlight.id("boarwarf_profession")), EternalStarlight.ID);
    public static final Codec<AbstractBoarwarfProfession> CODEC = PROFESSIONS.registry().byNameCodec();
    public static final RegistryObject<AbstractBoarwarfProfession, BoarwarfBlacksmithProfession> BLACKSMITH = PROFESSIONS.register("blacksmith", BoarwarfBlacksmithProfession::new);
    public static final RegistryObject<AbstractBoarwarfProfession, BoarwarfChefProfession> CHEF = PROFESSIONS.register("chef", BoarwarfChefProfession::new);
    public static final RegistryObject<AbstractBoarwarfProfession, BoarwarfDruidProfession> DRUID = PROFESSIONS.register("druid", BoarwarfDruidProfession::new);
    public static final RegistryObject<AbstractBoarwarfProfession, BoarwarfDyerProfession> DYER = PROFESSIONS.register("dyer", BoarwarfDyerProfession::new);
    public static final RegistryObject<AbstractBoarwarfProfession, BoarwarfSilversmithProfession> SILVERSMITH = PROFESSIONS.register("silversmith", BoarwarfSilversmithProfession::new);

    public static void loadClass() {}
}
