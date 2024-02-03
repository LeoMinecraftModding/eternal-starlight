package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crests.*;
import cn.leolezury.eternalstarlight.common.crests.util.CrestRegister;
import cn.leolezury.eternalstarlight.common.crests.util.CrestType;
import cn.leolezury.eternalstarlight.common.crests.util.CrestUtil;
import net.minecraft.resources.ResourceLocation;

public class CrestInit {
    public static CrestType test;
    public static CrestType testt;
    public static CrestType testtt;
    public static CrestType testttt;
    public static CrestType testtttt;
    public static CrestType ttesttttt;
    public static void postInit() {
        test = CrestRegister.registry(new ResourceLocation(EternalStarlight.MOD_ID, "test"), new TestCrest());
        testt = CrestRegister.registry(new ResourceLocation(EternalStarlight.MOD_ID, "testt"), new TesttCrest());
        testtt = CrestRegister.registry(new ResourceLocation(EternalStarlight.MOD_ID, "testtt"), new TestttCrest());
        testttt = CrestRegister.registry(new ResourceLocation(EternalStarlight.MOD_ID, "testttt"), new TesttttCrest());
        testtttt = CrestRegister.registry(new ResourceLocation(EternalStarlight.MOD_ID, "testtttt"), new TestttttCrest());
        ttesttttt = CrestRegister.registry(new ResourceLocation(EternalStarlight.MOD_ID, "ttesttttt"), new TTestttttCrest());
    }
}
