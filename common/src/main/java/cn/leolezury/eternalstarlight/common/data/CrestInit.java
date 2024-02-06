package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.init.SpellInit;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.spell.StarsMapSpell;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;

import java.util.Optional;

public class CrestInit {
    public static final ResourceKey<Crest> BOULDERS_SHIELD = create("boulders_shield");
    public static final ResourceKey<Crest> STARRY_EYE = create("starry_eye");

    public static void bootstrap(BootstapContext<Crest> context) {
        context.register(BOULDERS_SHIELD, new Crest(ManaType.TERRA, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/boulders_shield.png"), null, new Crest.MobEffectWithLevel(MobEffects.DAMAGE_RESISTANCE, 0)));
        context.register(STARRY_EYE, new Crest(ManaType.LUNAR, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/starry_eye.png"), Optional.of(SpellInit.STARS_MAP.get()), Optional.empty()));
    }

    public static ResourceKey<Crest> create(String name) {
        return ResourceKey.create(ESRegistries.CREST, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
