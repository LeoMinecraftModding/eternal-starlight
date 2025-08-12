package cn.leolezury.eternalstarlight.neoforge.datagen.provider.book;

import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ExistingFileHelper.ResourceType;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class BookDefinitionProvider implements DataProvider {
	private static final String DIRECTORY = "eternal_starlight/books";
	protected final ResourceType resourceType;
	protected final PackOutput.PathProvider pathProvider;
	protected final ExistingFileHelper existingFileHelper;
	protected final CompletableFuture<HolderLookup.Provider> lookupProvider;
	protected final String modid;
	protected final Map<ResourceLocation, BookDefinition> definitions = Maps.newHashMap();

	public BookDefinitionProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
		this.resourceType = new ResourceType(PackType.CLIENT_RESOURCES, ".json", DIRECTORY);
		this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, DIRECTORY);
		this.existingFileHelper = existingFileHelper;
		this.modid = modId;
		this.lookupProvider = lookupProvider;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput output) {
		ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();
		return lookupProvider.thenCompose(provider -> {
			gather(provider);

			DynamicOps<JsonElement> dynamicOps = new ConditionalOps<>(RegistryOps.create(JsonOps.INSTANCE, provider), ICondition.IContext.EMPTY);

			this.definitions.forEach((id, definition) -> {
				Path path = this.pathProvider.json(id);
				futuresBuilder.add(CompletableFuture.supplyAsync(() -> BookDefinition.CODEC.encodeStart(dynamicOps, definition).getOrThrow(msg -> new RuntimeException("Failed to encode %s: %s".formatted(path, msg)))).thenComposeAsync(encoded -> DataProvider.saveStable(output, encoded, path)));
			});

			return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
		});
	}

	protected abstract void gather(HolderLookup.Provider provider);

	public void add(ResourceLocation id, BookDefinition definition) {
		this.definitions.put(id, definition);
	}

	@Override
	public String getName() {
		return String.format("Book definitions generator for %s", this.modid);
	}
}
