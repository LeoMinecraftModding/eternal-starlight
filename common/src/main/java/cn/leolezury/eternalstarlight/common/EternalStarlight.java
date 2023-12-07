package cn.leolezury.eternalstarlight.common;

import cn.leolezury.eternalstarlight.common.init.*;

public class EternalStarlight {
    public static final String MOD_ID = "eternal_starlight";

    public static void init() {
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
        StructureInit.loadClass();
        POIInit.loadClass();
        ParticleInit.loadClass();
        SoundEventInit.loadClass();
    }
}
