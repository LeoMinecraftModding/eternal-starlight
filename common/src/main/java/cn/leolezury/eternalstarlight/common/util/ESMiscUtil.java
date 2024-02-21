package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;

import java.util.function.Supplier;

public class ESMiscUtil {
    public static void runWhenOnClient(Supplier<Runnable> toRun) {
        if (ESPlatform.INSTANCE.isPhysicalClient()) {
            toRun.get().run();
        }
    }
}
