package cn.leolezury.eternalstarlight.common;

import cn.leolezury.eternalstarlight.common.block.flammable.ESFlammabilityRegistry;
import cn.leolezury.eternalstarlight.common.client.helper.ClientHelper;
import cn.leolezury.eternalstarlight.common.client.helper.EmptyClientHelper;
import cn.leolezury.eternalstarlight.common.client.helper.IClientHelper;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.init.*;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;

public class EternalStarlight {
    public static final String MOD_ID = "eternal_starlight";

    public static void init() {
        FluidInit.loadClass();
        BlockInit.loadClass();
        ItemInit.loadClass();
        CreativeModeTabInit.loadClass();
        EntityInit.loadClass();
        MobEffectInit.loadClass();
        BlockEntityInit.loadClass();
        EnchantmentInit.loadClass();
        WorldCarverInit.loadClass();
        FeatureInit.loadClass();
        PlacerInit.loadClass();
        TreeDecoratorInit.loadClass();
        StructurePlacementTypeInit.loadClass();
        POIInit.loadClass();
        ParticleInit.loadClass();
        SoundEventInit.loadClass();
        RecipeSerializerInit.loadClass();
        RecipeInit.loadClass();
        DataTransformerTypeInit.loadClass();
        ESRegistries.loadClass();
        ESFlammabilityRegistry.registerDefaults();
    }

    public static IClientHelper getClientHelper() {
        if (ESPlatform.INSTANCE.isPhysicalClient()) {
            return new ClientHelper();
        } else {
            return new EmptyClientHelper();
        }
    }
}
