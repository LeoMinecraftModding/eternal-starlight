package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.ForgeRegistries;

public class ESItemModelProvider extends ItemModelProvider {
    public ESItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EternalStarlight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicBlockItem(ItemInit.RED_STARLIGHT_CRYSTAL_BLOCK.get());
        basicBlockItem(ItemInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
        basicItemWithBlockTexture(ItemInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
        basicItemWithBlockTexture(ItemInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
        basicBlockItem(ItemInit.RED_CRYSTAL_MOSS_CARPET.get());
        basicBlockItem(ItemInit.BLUE_CRYSTAL_MOSS_CARPET.get());
        basicItem(ItemInit.BLUE_STARLIGHT_CRYSTAL_SHARD.get());
        basicItem(ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get());
        basicItem(ItemInit.LUNAR_BERRIES.get());

        // wood
        basicItemWithBlockTexture(ItemInit.LUNAR_SAPLING.get());
        basicBlockItem(ItemInit.LUNAR_LEAVES.get());
        basicBlockItem(ItemInit.LUNAR_LOG.get());
        basicBlockItem(ItemInit.LUNAR_WOOD.get());
        basicBlockItem(ItemInit.LUNAR_PLANKS.get());
        basicBlockItem(ItemInit.STRIPPED_LUNAR_LOG.get());
        basicBlockItem(ItemInit.STRIPPED_LUNAR_WOOD.get());
        basicItem(ItemInit.LUNAR_DOOR.get());
        trapdoor(ItemInit.LUNAR_TRAPDOOR.get());
        basicBlockItem(ItemInit.LUNAR_PRESSURE_PLATE.get());
        button(ItemInit.LUNAR_BUTTON.get(), ItemInit.LUNAR_PLANKS.get());
        fence(ItemInit.LUNAR_FENCE.get(), ItemInit.LUNAR_PLANKS.get());
        basicBlockItem(ItemInit.LUNAR_FENCE_GATE.get());
        basicBlockItem(ItemInit.LUNAR_SLAB.get());
        basicBlockItem(ItemInit.LUNAR_STAIRS.get());
        basicItem(ItemInit.LUNAR_SIGN.get());
        basicItem(ItemInit.LUNAR_HANGING_SIGN.get());
        basicItem(ItemInit.LUNAR_BOAT.get());
        basicItem(ItemInit.LUNAR_CHEST_BOAT.get());

        basicItemWithBlockTexture(ItemInit.NORTHLAND_SAPLING.get());
        basicBlockItem(ItemInit.NORTHLAND_LEAVES.get());
        basicBlockItem(ItemInit.NORTHLAND_LOG.get());
        basicBlockItem(ItemInit.NORTHLAND_WOOD.get());
        basicBlockItem(ItemInit.NORTHLAND_PLANKS.get());
        basicBlockItem(ItemInit.STRIPPED_NORTHLAND_LOG.get());
        basicBlockItem(ItemInit.STRIPPED_NORTHLAND_WOOD.get());
        basicItem(ItemInit.NORTHLAND_DOOR.get());
        trapdoor(ItemInit.NORTHLAND_TRAPDOOR.get());
        basicBlockItem(ItemInit.NORTHLAND_PRESSURE_PLATE.get());
        button(ItemInit.NORTHLAND_BUTTON.get(), ItemInit.NORTHLAND_PLANKS.get());
        fence(ItemInit.NORTHLAND_FENCE.get(), ItemInit.NORTHLAND_PLANKS.get());
        basicBlockItem(ItemInit.NORTHLAND_FENCE_GATE.get());
        basicBlockItem(ItemInit.NORTHLAND_SLAB.get());
        basicBlockItem(ItemInit.NORTHLAND_STAIRS.get());
        basicItem(ItemInit.NORTHLAND_SIGN.get());
        basicItem(ItemInit.NORTHLAND_HANGING_SIGN.get());
        basicItem(ItemInit.NORTHLAND_BOAT.get());
        basicItem(ItemInit.NORTHLAND_CHEST_BOAT.get());

        basicItemWithBlockTexture(ItemInit.STARLIGHT_MANGROVE_SAPLING.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_LEAVES.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_LOG.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_WOOD.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_PLANKS.get());
        basicBlockItem(ItemInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        basicBlockItem(ItemInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get());
        basicItem(ItemInit.STARLIGHT_MANGROVE_DOOR.get());
        trapdoor(ItemInit.STARLIGHT_MANGROVE_TRAPDOOR.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get());
        button(ItemInit.STARLIGHT_MANGROVE_BUTTON.get(), ItemInit.STARLIGHT_MANGROVE_PLANKS.get());
        fence(ItemInit.STARLIGHT_MANGROVE_FENCE.get(), ItemInit.STARLIGHT_MANGROVE_PLANKS.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_FENCE_GATE.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_SLAB.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_STAIRS.get());
        basicItem(ItemInit.STARLIGHT_MANGROVE_SIGN.get());
        basicItem(ItemInit.STARLIGHT_MANGROVE_HANGING_SIGN.get());
        basicItem(ItemInit.STARLIGHT_MANGROVE_BOAT.get());
        basicItem(ItemInit.STARLIGHT_MANGROVE_CHEST_BOAT.get());
        basicBlockItem(ItemInit.STARLIGHT_MANGROVE_ROOTS.get());
        basicBlockItem(ItemInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

        basicItemWithBlockTexture(ItemInit.STARLIGHT_FLOWER.get());
        basicItemWithBlockTexture(ItemInit.NIGHT_SPROUTS.get());
        basicItemWithBlockTexture(ItemInit.GLOWING_NIGHT_SPROUTS.get());
        basicItemWithBlockTexture(ItemInit.SMALL_NIGHT_SPROUTS.get());
        basicItemWithBlockTexture(ItemInit.SMALL_GLOWING_NIGHT_SPROUTS.get());
        basicItemWithBlockTexture(ItemInit.LUNAR_GRASS.get());
        basicItemWithBlockTexture(ItemInit.GLOWING_LUNAR_GRASS.get());
        basicItemWithBlockTexture(ItemInit.CRESCENT_GRASS.get());
        basicItemWithBlockTexture(ItemInit.GLOWING_CRESCENT_GRASS.get());
        basicItemWithBlockTexture(ItemInit.PARASOL_GRASS.get());
        basicItemWithBlockTexture(ItemInit.GLOWING_PARASOL_GRASS.get());
        basicItem(ItemInit.LUNAR_REED.get(), blockTextureFromItem(ItemInit.LUNAR_REED.get()).withSuffix("_top"));
        basicItemWithBlockTexture(ItemInit.GLOWING_MUSHROOM.get());
        basicBlockItem(ItemInit.GLOWING_MUSHROOM_BLOCK.get());

        // TODO: We'll be right back
    }

    private void trapdoor(Item item) {
        withExistingParent(name(item), name(item) + "_bottom");
    }

    private void button(Item button, Item material) {
        getBuilder(name(button))
                .parent(getExistingFile(mcLoc("block/button_inventory")))
                .texture("texture", blockTextureFromItem(material));
    }

    private void fence(Item fence, Item planks) {
        getBuilder(name(fence))
                .parent(getExistingFile(mcLoc("block/fence_inventory")))
                .texture("texture", blockTextureFromItem(planks));
    }

    private void wall(Item fence, Item stone) {
        getBuilder(name(fence))
                .parent(getExistingFile(mcLoc("block/wall_inventory")))
                .texture("wall", blockTextureFromItem(stone));
    }

    private void basicBlockItem(Item item) {
        withExistingParent(name(item), name(item));
    }

    private void basicItemWithBlockTexture(Item item) {
        basicItem(item, blockTextureFromItem(item));
    }

    private ItemModelBuilder basicItem(Item item, ResourceLocation texture) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", texture);
    }

    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = key(block);
        return texture(name, ModelProvider.BLOCK_FOLDER);
    }

    public ResourceLocation blockTextureFromItem(Item item) {
        ResourceLocation name = key(item);
        return texture(name, ModelProvider.BLOCK_FOLDER);
    }

    public ResourceLocation itemTexture(Item item) {
        ResourceLocation name = key(item);
        return texture(name, ModelProvider.ITEM_FOLDER);
    }

    public ResourceLocation texture(ResourceLocation key, String prefix) {
        return new ResourceLocation(key.getNamespace(), prefix + "/" + key.getPath());
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    private String name(Item item) {
        return key(item).getPath();
    }
}
