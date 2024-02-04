package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class CrestInit {
    public static final ResourceKey<Crest> TEST_ONE = create("test_one");
    public static final ResourceKey<Crest> TEST_TWO = create("test_two");
    public static final ResourceKey<Crest> TEST_THREE = create("test_three");
    public static final ResourceKey<Crest> TEST_FOUR = create("test_four");
    public static final ResourceKey<Crest> TEST_FIVE = create("test_five");
    public static final ResourceKey<Crest> TEST_SIX = create("test_six");

    public static void bootstrap(BootstapContext<Crest> context) {
        context.register(TEST_ONE, new Crest(Crest.CrestElement.TERRA, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/test_one.png"), Optional.empty()));
        context.register(TEST_TWO, new Crest(Crest.CrestElement.WIND, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/test_two.png"), Optional.empty()));
        context.register(TEST_THREE, new Crest(Crest.CrestElement.WATER, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/test_three.png"), Optional.empty()));
        context.register(TEST_FOUR, new Crest(Crest.CrestElement.LUNAR, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/test_four.png"), Optional.empty()));
        context.register(TEST_FIVE, new Crest(Crest.CrestElement.BLAZE, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/test_five.png"), Optional.empty()));
        context.register(TEST_SIX, new Crest(Crest.CrestElement.LIGHT, new ResourceLocation(EternalStarlight.MOD_ID, "textures/crest/test_six.png"), Optional.empty()));
    }

    public static ResourceKey<Crest> create(String name) {
        return ResourceKey.create(ESRegistries.CREST, new ResourceLocation(EternalStarlight.MOD_ID, name));
    }
}
