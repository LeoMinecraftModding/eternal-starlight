package cn.leolezury.eternalstarlight.fabric.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;

public class ESASM implements Runnable {
    @Override
    public void run() {
        extendEnums();
    }

    private static void extendEnums() {
        ClassTinkerers.enumBuilder(mapC("class_1886")) // EnchantmentCategory
                .addEnumSubclass("ES_WEAPON", "cn.leolezury.eternalstarlight.fabric.asm.ESWeaponEnchantmentCategory")
                .build();

        ClassTinkerers.enumBuilder(mapC("class_1814"), "L" + mapC("class_124") + ";")  // Rarity // ChatFormatting
                .addEnum("STARLIGHT", () -> new Object[] { ChatFormatting.DARK_AQUA })
                .build();
    }

    public static String mapC(String intermediaryName) {
        return FabricLoader.getInstance().getMappingResolver()
                .mapClassName("intermediary", "net.minecraft." + intermediaryName);
    }
}
