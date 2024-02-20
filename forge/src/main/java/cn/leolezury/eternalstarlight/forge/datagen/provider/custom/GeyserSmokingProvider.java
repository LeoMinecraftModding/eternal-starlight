package cn.leolezury.eternalstarlight.forge.datagen.provider.custom;

import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class GeyserSmokingProvider implements DataProvider {
    private final PackOutput output;
    private final String modId;
    protected final Map<String, List<Item>> builder = Maps.newLinkedHashMap();

    public GeyserSmokingProvider(PackOutput output, String modId) {
        this.output = output;
        this.modId = modId;
    }

    public abstract void addSmokingRecipes();

    public void add(Item input, Item output) {
        add(BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_" + BuiltInRegistries.ITEM.getKey(input).getPath(), input, output);
    }

    public void add(String name, Item input, Item output) {
        this.builder.put(name, List.of(input, output));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        builder.clear();
        addSmokingRecipes();
        ImmutableList.Builder<CompletableFuture<?>> completableFutureBuilder = new ImmutableList.Builder<>();

        for (Map.Entry<String, List<Item>> entry : builder.entrySet()) {
            String name = entry.getKey();
            List<Item> recipe = entry.getValue();

            List<String> list = builder.keySet().stream()
                    .filter(s -> BuiltInRegistries.ITEM.containsValue(recipe.get(0)))
                    .filter(s -> BuiltInRegistries.ITEM.containsValue(recipe.get(1)))
                    .filter(s -> !this.builder.containsKey(s))
                    .toList();

            if (!list.isEmpty()) {
                throw new IllegalArgumentException(String.format("Duplicate Geyser Smoking Recipe: [%s]", list.stream().map(Object::toString).collect(Collectors.joining(", "))));
            } else {
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("type", BuiltInRegistries.RECIPE_SERIALIZER.getKey(ESRecipeSerializers.GEYSER_SMOKING.get()).toString());
                jsonObject.addProperty("input", BuiltInRegistries.ITEM.getKey(recipe.get(0)).toString());
                jsonObject.addProperty("output", BuiltInRegistries.ITEM.getKey(recipe.get(1)).toString());

                Path path = this.output.getOutputFolder().resolve("data/" + modId + "/recipes/geyser_smoking/" + name + ".json");;
                completableFutureBuilder.add(DataProvider.saveStable(cachedOutput, jsonObject, path));
            }
        }

        return CompletableFuture.allOf(completableFutureBuilder.build().toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Geyser Smoking Recipes for " + modId;
    }
}
