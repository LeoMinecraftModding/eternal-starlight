package cn.leolezury.eternalstarlight.common;

import cn.leolezury.eternalstarlight.common.init.*;

public class EternalStarlight {
    public static final String MOD_ID = "eternal_starlight";

    public static void init() {
        BlockInit.postRegistry();
        ItemInit.postRegistry();
        CreativeModeTabInit.postRegistry();
        EntityInit.postRegistry();
        BlockEntityInit.postRegistry();
        EnchantmentInit.postRegistry();
        FeatureInit.postRegistry();
        PlacerInit.postRegistry();
        TreeDecoratorInit.postRegistry();
        StructurePlacementTypeInit.postRegistry();
        StructureInit.postRegistry();
        POIInit.postRegistry();
        ParticleInit.postRegistry();
        SoundEventInit.postRegistry();
    }
}
