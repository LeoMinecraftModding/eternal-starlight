package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.spell.*;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ESSpells {
    public static final RegistrationProvider<AbstractSpell> SPELLS = RegistrationProvider.newRegistry(ResourceKey.createRegistryKey(new ResourceLocation(EternalStarlight.MOD_ID, "spell")), EternalStarlight.MOD_ID);
    public static final Codec<AbstractSpell> CODEC = SPELLS.registry().byNameCodec();
    public static final RegistryObject<AbstractSpell, TeleportationSpell> TELEPORTATION = SPELLS.register("teleportation", () -> new TeleportationSpell(new AbstractSpell.Properties(ManaType.LUNAR, 40, 20, 300)));
    public static final RegistryObject<AbstractSpell, GuidanceOfStarsSpell> GUIDANCE_OF_STARS = SPELLS.register("guidance_of_stars", () -> new GuidanceOfStarsSpell(new AbstractSpell.Properties(ManaType.LUNAR, 30, 30, 0)));
    public static final RegistryObject<AbstractSpell, LaserBeamSpell> LASER_BEAM = SPELLS.register("laser_beam", () -> new LaserBeamSpell(new AbstractSpell.Properties(ManaType.BLAZE, 30, 400, 20)));

    public static void loadClass() {}
}
