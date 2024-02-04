package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrestUtil {
    public static List<Crest> getCrests(Player player) {
        List<Crest> result = new ArrayList<>();
        if (!ESUtil.getPersistentData(player).contains("Crests", Tag.TAG_COMPOUND)) {
            ESUtil.getPersistentData(player).put("Crests", new CompoundTag());
        }
        CompoundTag crests = ESUtil.getPersistentData(player).getCompound("Crests");
        Registry<Crest> registry = player.level().registryAccess().registryOrThrow(ESRegistries.CREST);
        registry.forEach((crest -> {
            String id = Objects.requireNonNull(registry.getKey(crest)).toString();
            if (crests.getBoolean(id)) {
                result.add(crest);
            }
        }));
        return result;
    }

    public static void setCrests(Player player, List<Crest> crests) {
        CompoundTag crestsTag = new CompoundTag();
        Registry<Crest> registry = player.level().registryAccess().registryOrThrow(ESRegistries.CREST);
        crests.forEach((crest -> {
            String id = Objects.requireNonNull(registry.getKey(crest)).toString();
            crestsTag.putBoolean(id, true);
        }));
        ESUtil.getPersistentData(player).put("Crests", crestsTag);
    }

    public static boolean giveCrest(Player player, Crest crest) {
        List<Crest> crests = getCrests(player);
        if (crests.stream().anyMatch((c) -> c.element() == crest.element()) || crests.size() >= 5) {
            return false;
        }
        crests.add(crest);
        setCrests(player, crests);
        return true;
    }

    public static boolean removeCrest(Player player, Crest crest) {
        List<Crest> crests = getCrests(player);
        if (!crests.contains(crest)) {
            return false;
        }
        crests.remove(crest);
        setCrests(player, crests);
        return true;
    }
}
