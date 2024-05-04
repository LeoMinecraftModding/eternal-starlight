package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.world.saved.BookProgressions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class ESBookUtil {
    public static BookProgressions getOrCreateBookProgressions(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(BookProgressions.factory(serverLevel), "book_progressions");
    }

    public static List<ResourceLocation> getUnlockedPartsFor(ServerPlayer player) {
        BookProgressions progressions = getOrCreateBookProgressions(player.serverLevel());
        return progressions.getProgressions().getOrDefault(player.getUUID(), new ArrayList<>());
    }

    public static void unlockFor(ServerPlayer player, ResourceLocation... locations) {
        BookProgressions progressions = getOrCreateBookProgressions(player.serverLevel());
        List<ResourceLocation> unlocked = getUnlockedPartsFor(player);
        unlocked.addAll(List.of(locations));
        progressions.getProgressions().put(player.getUUID(), unlocked);
        progressions.setDirty();
    }
}
