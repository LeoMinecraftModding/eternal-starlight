package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.network.UpdateBookPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.world.saved.BookProgressions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ESBookUtil {
	public static BookProgressions getOrCreateBookProgressions(ServerLevel serverLevel) {
		return serverLevel.getDataStorage().computeIfAbsent(BookProgressions.factory(serverLevel), "book_progressions");
	}

	public static Set<ResourceLocation> getUnlockedParts(ServerPlayer player) {
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

	public static void unlock(ServerPlayer player, ResourceLocation... locations) {
		MinecraftServer server = player.getServer();
		if (server != null) {
			ServerLevel level = player.getServer().getLevel(Level.OVERWORLD);
			if (level != null) {
				BookProgressions progressions = getOrCreateBookProgressions(level);
				Set<ResourceLocation> unlocked = getUnlockedParts(player);
				if (!unlocked.containsAll(Set.of(locations))) {
					Set<ResourceLocation> oldUnlocked = new HashSet<>(unlocked);
					Set<ResourceLocation> newUnlocked = new HashSet<>(unlocked);
					newUnlocked.addAll(Set.of(locations));
					progressions.getProgressions().put(player.getUUID(), newUnlocked);
					progressions.setDirty();
					List<String> listeningNamespaces = ESDataAttachments.GUIDEBOOK_LISTENING_NAMESPACES.getData(player);
					if (newUnlocked.stream().anyMatch(id -> !oldUnlocked.contains(id) && listeningNamespaces.contains(id.getNamespace()))) {
						ESPlatform.INSTANCE.sendToClient(player, new UpdateBookPacket(
							oldUnlocked.stream().filter(id -> listeningNamespaces.contains(id.getNamespace())).collect(Collectors.toSet()),
							newUnlocked.stream().filter(id -> listeningNamespaces.contains(id.getNamespace())).collect(Collectors.toSet())
						));
					}
				}
			}
		}
	}
}
