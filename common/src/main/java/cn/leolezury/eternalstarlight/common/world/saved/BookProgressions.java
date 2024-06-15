package cn.leolezury.eternalstarlight.common.world.saved;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class BookProgressions extends SavedData {
    private final Map<UUID, List<ResourceLocation>> progressions = new HashMap<>();

    public Map<UUID, List<ResourceLocation>> getProgressions() {
        return progressions;
    }

    public static SavedData.Factory<BookProgressions> factory(ServerLevel serverLevel) {
        return new SavedData.Factory<>(BookProgressions::new, (compoundTag, lookup) -> load(serverLevel, compoundTag), null);
    }

    public static BookProgressions load(ServerLevel serverLevel, CompoundTag compoundTag) {
        BookProgressions progression = new BookProgressions();
        int progressionCount = compoundTag.getInt("ProgressionCount");
        if (progressionCount > 0) {
            for (int i = 0; i < progressionCount; i++) {
                if (compoundTag.contains("Progression" + i, CompoundTag.TAG_COMPOUND)) {
                    CompoundTag progressionTag = compoundTag.getCompound("Progression" + i);
                    UUID id = progressionTag.getUUID("ID");
                    List<ResourceLocation> allUnlocked = new ArrayList<>();
                    int unlockedCount = progressionTag.getInt("UnlockedCount");
                    if (unlockedCount > 0) {
                        for (int j = 0; j < unlockedCount; j++) {
                            ResourceLocation unlocked = ResourceLocation.parse(progressionTag.getString("Unlocked" + j));
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
        compoundTag.putInt("ProgressionCount", progressions.size());
        int i = 0;
        for (Map.Entry<UUID, List<ResourceLocation>> entry : progressions.entrySet()) {
            CompoundTag progressionTag = new CompoundTag();
            progressionTag.putUUID("ID", entry.getKey());
            progressionTag.putInt("UnlockedCount", entry.getValue().size());
            for (int j = 0; j < entry.getValue().size(); j++) {
                progressionTag.putString("Unlocked" + j, entry.getValue().get(j).toString());
            }
            compoundTag.put("Progression" + i, progressionTag);
            i++;
        }
        return compoundTag;
    }
}
