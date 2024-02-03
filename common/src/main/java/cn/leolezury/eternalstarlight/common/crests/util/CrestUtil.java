package cn.leolezury.eternalstarlight.common.crests.util;

import cn.leolezury.eternalstarlight.common.crests.Crest;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CrestUtil {
    public static ResourceLocation getCrestPath(CrestType crest) {
        return new ResourceLocation(crest.getNameSpace(), "textures/crests/" + crest.getName() + ".png");
    }
    public static void giveCrest(Player player, CrestType crest) {
        int count = ESUtil.getPersistentData(player).getInt("PlayerCrestsCount");
        ESUtil.getPersistentData(player).putBoolean(crest.name, true);
        ESUtil.getPersistentData(player).putInt("PlayerCrestsCount", count + 1);
    }

    public static void equipCrest(Player player, CrestType crest, boolean bl) {
        ESUtil.getPersistentData(player).putBoolean(crest.name + "Equipped", bl);
    }

    public static int getCrestsCount(Player player) {
        return ESUtil.getPersistentData(player).getInt("PlayerCrestsCount");
    }

    public static CrestType getType(String name) {
        for (CrestType type : CrestRegister.CRESTS.values().stream().toList()) {
            if (Objects.equals(type.name, name)) {
                return type;
            }
        }
        return null;
    }
}
