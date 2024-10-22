package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.world.saved.BookProgressions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public class ESBookUtil {
	public static BookProgressions getOrCreateBookProgressions(ServerLevel serverLevel) {
		return serverLevel.getDataStorage().computeIfAbsent(BookProgressions.factory(serverLevel), "book_progressions");
	}

	public static Set<ResourceLocation> getUnlockedPartsFor(ServerPlayer player) {
		MinecraftServer server = player.getServer();
		if (server != null) {
			ServerLevel level = player.getServer().getLevel(Level.OVERWORLD);
			if (level != null) {
				BookProgressions progressions = getOrCreateBookProgressions(level);
				return progressions.getProgressions().getOrDefault(player.getUUID(), new HashSet<>());
			}
		}
		return new HashSet<>();
	}

	public static void unlockFor(ServerPlayer player, ResourceLocation... locations) {
		MinecraftServer server = player.getServer();
		if (server != null) {
			ServerLevel level = player.getServer().getLevel(Level.OVERWORLD);
			if (level != null) {
				BookProgressions progressions = getOrCreateBookProgressions(level);
				Set<ResourceLocation> unlocked = getUnlockedPartsFor(player);
				unlocked.addAll(Set.of(locations));
				progressions.getProgressions().put(player.getUUID(), unlocked);
				progressions.setDirty();
			}
		}
	}
}
