package cn.leolezury.eternalstarlight.common.crests.util;

import cn.leolezury.eternalstarlight.common.crests.Crest;
import net.minecraft.resources.ResourceLocation;

public class CrestType {
    public String name;
    public String nameSpace;
    public Crest crest;
    public CrestType(String nameSpace, String name, Crest crest) {
        this.nameSpace = nameSpace;
        this.name = name;
        this.crest = crest;
    }

    public String getName() {
        return this.name;
    }

    public String getNameSpace() {
        return this.nameSpace;
    }

    public Crest getCrest() {
        return this.crest;
    }


    public static CrestType get(String nameSpace, String path, Crest crest) {
        return new CrestType(nameSpace, path, crest);
    }
}
