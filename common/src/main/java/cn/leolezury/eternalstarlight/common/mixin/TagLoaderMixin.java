package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(TagLoader.class)
public abstract class TagLoaderMixin {
	@Shadow
	@Final
	private String directory;

	@Inject(method = "build(Ljava/util/Map;)Ljava/util/Map;", at = @At(value = "RETURN"))
	public <T> void build(Map<ResourceLocation, List<TagLoader.EntryWithSource>> map, CallbackInfoReturnable<Map<ResourceLocation, Collection<T>>> cir) {
		if (Registries.tagsDirPath(Registries.ITEM).equals(directory)) {
			Map<ResourceLocation, Collection<T>> result = cir.getReturnValue();
			Optional<Collection<T>> collection = result.entrySet().stream().filter(e -> e.getKey().equals(ESTags.Items.UNTRIMMABLE_ARMOR.location())).findFirst().map(Map.Entry::getValue);
			Optional<Collection<T>> vanilla = result.entrySet().stream().filter(e -> e.getKey().equals(ItemTags.TRIMMABLE_ARMOR.location())).findFirst().map(Map.Entry::getValue);
			if (collection.isPresent() && vanilla.isPresent()) {
				Collection<T> untrimmable = collection.get();
				Set<T> modified = new HashSet<>(vanilla.get());
				modified.removeIf(untrimmable::contains);
				result.put(ItemTags.TRIMMABLE_ARMOR.location(), ImmutableSet.copyOf(modified));
			}
		}
	}
}
