package cn.leolezury.eternalstarlight.forge;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraftforge.fml.common.Mod;

@Mod(EternalStarlight.MOD_ID)
public class ESForge {
    public ESForge () {
        EternalStarlight.init();
    }
}
