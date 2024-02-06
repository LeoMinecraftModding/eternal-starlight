package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrestUtil {
    public static List<Crest> getCrests(Player player) {
        return getCrests(player, "Crests");
    }

    public static List<Crest> getCrests(Player player, String tagId) {
        if (!ESUtil.getPersistentData(player).contains(tagId, Tag.TAG_COMPOUND)) {
            ESUtil.getPersistentData(player).put(tagId, new CompoundTag());
        }
        CompoundTag crests = ESUtil.getPersistentData(player).getCompound(tagId);
        return getCrests(player.level().registryAccess(), crests);
    }

    public static List<Crest> getCrests(RegistryAccess access, CompoundTag tag) {
        List<Crest> result = new ArrayList<>();
        Registry<Crest> registry = access.registryOrThrow(ESRegistries.CREST);
        registry.forEach((crest -> {
            String id = Objects.requireNonNull(registry.getKey(crest)).toString();
            if (tag.getBoolean(id)) {
                result.add(crest);
            }
        }));
        return result;
    }

    public static void setCrests(Player player, List<Crest> crests) {
        setCrests(player, crests, "Crests");
    }

    public static void setCrests(Player player, List<Crest> crests, String tagId) {
        CompoundTag crestsTag = new CompoundTag();
        setCrests(player.level().registryAccess(), crestsTag, crests);
        ESUtil.getPersistentData(player).put(tagId, crestsTag);
    }

    public static void setCrests(RegistryAccess access, CompoundTag tag, List<Crest> crests) {
        Registry<Crest> registry = access.registryOrThrow(ESRegistries.CREST);
        crests.forEach((crest -> {
            String id = Objects.requireNonNull(registry.getKey(crest)).toString();
            tag.putBoolean(id, true);
        }));
    }

    public static void giveCrest(Player player, Crest crest) {
        List<Crest> crests = getCrests(player, "OwnedCrests");
        crests.add(crest);
        setCrests(player, crests, "OwnedCrests");
    }

    public static boolean removeCrest(Player player, Crest crest) {
        List<Crest> crests = getCrests(player, "OwnedCrests");
        if (!crests.contains(crest)) {
            return false;
        }
        crests.remove(crest);
        setCrests(player, crests, "OwnedCrests");
        return true;
    }
}
