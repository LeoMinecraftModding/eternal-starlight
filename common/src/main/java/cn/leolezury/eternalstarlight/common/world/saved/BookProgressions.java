package cn.leolezury.eternalstarlight.common.world.saved;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class BookProgressions extends SavedData {
	private static final String TAG_PROGRESSION_COUNT = "progression_count";
	private static final String TAG_PROGRESSION = "progression";
	private static final String TAG_ID = "id";
	private static final String TAG_UNLOCKED_COUNT = "unlocked_count";
	private static final String TAG_UNLOCKED = "unlocked";

	private final Map<UUID, Set<ResourceLocation>> progressions = new HashMap<>();

	public Map<UUID, Set<ResourceLocation>> getProgressions() {
		return progressions;
	}

	public static SavedData.Factory<BookProgressions> factory(ServerLevel serverLevel) {
		return new SavedData.Factory<>(BookProgressions::new, (compoundTag, lookup) -> load(serverLevel, compoundTag), null);
	}

	public static BookProgressions load(ServerLevel serverLevel, CompoundTag compoundTag) {
		BookProgressions progression = new BookProgressions();
		int progressionCount = compoundTag.getInt(TAG_PROGRESSION_COUNT);
		if (progressionCount > 0) {
			for (int i = 0; i < progressionCount; i++) {
				if (compoundTag.contains(TAG_PROGRESSION + i, CompoundTag.TAG_COMPOUND)) {
					CompoundTag progressionTag = compoundTag.getCompound(TAG_PROGRESSION + i);
					UUID id = progressionTag.getUUID(TAG_ID);
					Set<ResourceLocation> allUnlocked = new HashSet<>();
					int unlockedCount = progressionTag.getInt(TAG_UNLOCKED_COUNT);
					if (unlockedCount > 0) {
						for (int j = 0; j < unlockedCount; j++) {
							ResourceLocation unlocked = ResourceLocation.parse(progressionTag.getString(TAG_UNLOCKED + j));
							allUnlocked.add(unlocked);
						}
					}
					progression.progressions.put(id, allUnlocked);
				}
			}
		}
		return progression;
	}

	@Override
	public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
		compoundTag.putInt(TAG_PROGRESSION_COUNT, progressions.size());
		int i = 0;
		for (Map.Entry<UUID, Set<ResourceLocation>> entry : progressions.entrySet()) {
			CompoundTag progressionTag = new CompoundTag();
			progressionTag.putUUID(TAG_ID, entry.getKey());
			progressionTag.putInt(TAG_UNLOCKED_COUNT, entry.getValue().size());
			List<ResourceLocation> list = entry.getValue().stream().sorted().toList();
			for (int j = 0; j < list.size(); j++) {
				progressionTag.putString(TAG_UNLOCKED + j, list.get(j).toString());
			}
			compoundTag.put(TAG_PROGRESSION + i, progressionTag);
			i++;
		}
		return compoundTag;
	}
}
