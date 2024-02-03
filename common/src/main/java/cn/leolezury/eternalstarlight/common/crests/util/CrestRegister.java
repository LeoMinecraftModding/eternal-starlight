package cn.leolezury.eternalstarlight.common.crests.util;

import cn.leolezury.eternalstarlight.common.crests.Crest;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CrestRegister {
    public static final HashMap<Crest, CrestType> CRESTS = new LinkedHashMap<>();

    protected static void registry(String nameSpace, String path, Crest crest) {
        CRESTS.put(CrestType.get(nameSpace, path, crest).getCrest(), CrestType.get(nameSpace, path, crest));
    }

    public static CrestType registry(ResourceLocation rl, Crest crest) {
        registry(rl.getNamespace(), rl.getPath(), crest);
        return new CrestType(rl.getNamespace(), rl.getPath(), crest);
    }
}
