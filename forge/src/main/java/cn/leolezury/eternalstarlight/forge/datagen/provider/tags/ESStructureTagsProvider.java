package cn.leolezury.eternalstarlight.forge.datagen.provider.tags;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESStructures;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ESStructureTagsProvider extends StructureTagsProvider {
	public ESStructureTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		tag(ESTags.Structures.BOSS_STRUCTURES).add(
			ESStructures.GOLEM_FORGE,
			ESStructures.CURSED_GARDEN
		);
		tag(ESTags.Structures.GOLEM_FORGE).add(
			ESStructures.GOLEM_FORGE
		);
		tag(ESTags.Structures.CURSED_GARDEN).add(
			ESStructures.CURSED_GARDEN
		);
	}
}