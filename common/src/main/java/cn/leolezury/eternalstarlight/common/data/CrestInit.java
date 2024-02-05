package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;

public class CrestInit {
    public static final ResourceKey<Crest> BOULDERS_SHIELD = create("boulders_shield");

    public static void bootstrap(BootstapContext<Crest> context) {
        context.register(BOULDERS_SHIELD, new Crest(Crest.CrestElement.TERRA, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/boulders_shield.png"), null, new Crest.MobEffectWithLevel(MobEffects.DAMAGE_RESISTANCE, 0)));
    }

    public static ResourceKey<Crest> create(String name) {
        return ResourceKey.create(ESRegistries.CREST, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
