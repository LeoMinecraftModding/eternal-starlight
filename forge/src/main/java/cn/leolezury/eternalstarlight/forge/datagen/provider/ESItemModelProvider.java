package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
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
        basicItemWithBlockTexture(ItemInit.CONEBLOOM.get());
        basicItemWithBlockTexture(ItemInit.NIGHTFAN.get());
        basicItemWithBlockTexture(ItemInit.PINK_ROSE.get());
        basicItemWithBlockTexture(ItemInit.STARLIGHT_TORCHFLOWER.get());
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
        basicItemWithBlockTexture(ItemInit.GLADESPIKE.get());
        basicItemWithBlockTexture(ItemInit.VIVIDSTALK.get());
        basicItemWithBlockTexture(ItemInit.GLOWING_MUSHROOM.get());
        cubeAll(ItemInit.GLOWING_MUSHROOM_BLOCK.get());

        basicItemWithBlockTexture(ItemInit.SWAMP_ROSE.get());
        basicItemWithBlockTexture(ItemInit.FANTABUD.get());
        basicItemWithBlockTexture(ItemInit.GREEN_FANTABUD.get());
        basicItemWithBlockTexture(ItemInit.FANTAFERN.get());
        basicItemWithBlockTexture(ItemInit.GREEN_FANTAFERN.get());
        basicItemWithBlockTexture(ItemInit.FANTAGRASS.get());
        basicItemWithBlockTexture(ItemInit.GREEN_FANTAGRASS.get());

        basicBlockItem(ItemInit.NIGHTSHADE_DIRT.get());
        basicBlockItem(ItemInit.NIGHTSHADE_GRASS_BLOCK.get());
        basicBlockItem(ItemInit.FANTASY_GRASS_BLOCK.get());

        basicBlockItem(ItemInit.GRIMSTONE.get());
        basicBlockItem(ItemInit.GRIMSTONE_BRICKS.get());
        basicBlockItem(ItemInit.GRIMSTONE_BRICK_SLAB.get());
        basicBlockItem(ItemInit.GRIMSTONE_BRICK_STAIRS.get());
        wall(ItemInit.GRIMSTONE_BRICK_WALL.get(), ItemInit.GRIMSTONE_BRICKS.get());
        basicBlockItem(ItemInit.POLISHED_GRIMSTONE.get());
        basicBlockItem(ItemInit.POLISHED_GRIMSTONE_SLAB.get());
        basicBlockItem(ItemInit.POLISHED_GRIMSTONE_STAIRS.get());
        wall(ItemInit.POLISHED_GRIMSTONE_WALL.get(), ItemInit.POLISHED_GRIMSTONE.get());
        basicBlockItem(ItemInit.CHISELED_GRIMSTONE.get());

        basicBlockItem(ItemInit.VOIDSTONE.get());
        basicBlockItem(ItemInit.VOIDSTONE_BRICKS.get());
        basicBlockItem(ItemInit.VOIDSTONE_BRICK_SLAB.get());
        basicBlockItem(ItemInit.VOIDSTONE_BRICK_STAIRS.get());
        wall(ItemInit.VOIDSTONE_BRICK_WALL.get(), ItemInit.VOIDSTONE_BRICKS.get());
        basicBlockItem(ItemInit.POLISHED_VOIDSTONE.get());
        basicBlockItem(ItemInit.POLISHED_VOIDSTONE_SLAB.get());
        basicBlockItem(ItemInit.POLISHED_VOIDSTONE_STAIRS.get());
        wall(ItemInit.POLISHED_VOIDSTONE_WALL.get(), ItemInit.POLISHED_VOIDSTONE.get());
        basicBlockItem(ItemInit.CHISELED_VOIDSTONE.get());
        basicBlockItem(ItemInit.GLOWING_VOIDSTONE.get());

        basicBlockItem(ItemInit.NIGHTSHADE_MUD.get());
        basicBlockItem(ItemInit.GLOWING_NIGHTSHADE_MUD.get());
        basicBlockItem(ItemInit.PACKED_NIGHTSHADE_MUD.get());
        basicBlockItem(ItemInit.NIGHTSHADE_MUD_BRICKS.get());
        basicBlockItem(ItemInit.NIGHTSHADE_MUD_BRICK_SLAB.get());
        basicBlockItem(ItemInit.NIGHTSHADE_MUD_BRICK_STAIRS.get());
        wall(ItemInit.NIGHTSHADE_MUD_BRICK_WALL.get(), ItemInit.NIGHTSHADE_MUD_BRICKS.get());

        basicBlockItem(ItemInit.ENERGY_BLOCK.get());
        basicBlockItem(ItemInit.STARLIGHT_GOLEM_SPAWNER.get());
        basicBlockItem(ItemInit.LUNAR_MONSTROSITY_SPAWNER.get());
        basicItem(ItemInit.LOOT_BAG.get());
        basicItem(ItemInit.BOOK.get());

        basicBlockItem(ItemInit.AETHERSENT_BLOCK.get());
        basicItem(ItemInit.AETHERSENT_INGOT.get());
        basicHandheld(ItemInit.RAGE_OF_STARS.get());
        bow(ItemInit.STARFALL_LONGBOW.get());
        armorWithTrim((ArmorItem) ItemInit.AETHERSENT_HOOD.get());
        basicItem(ItemInit.AETHERSENT_CAPE.get());
        basicItem(ItemInit.AETHERSENT_BOTTOMS.get());
        basicItem(ItemInit.AETHERSENT_BOOTS.get());

        basicBlockItem(ItemInit.SPRINGSTONE.get());
        basicBlockItem(ItemInit.THERMAL_SPRINGSTONE.get());
        basicItem(ItemInit.THERMAL_SPRINGSTONE_INGOT.get());
        basicHandheld(ItemInit.THERMAL_SPRINGSTONE_SWORD.get());
        basicHandheld(ItemInit.THERMAL_SPRINGSTONE_PICKAXE.get());
        basicHandheld(ItemInit.THERMAL_SPRINGSTONE_AXE.get());
        basicHandheld(ItemInit.THERMAL_SPRINGSTONE_SCYTHE.get());
        inventoryModel(ItemInit.THERMAL_SPRINGSTONE_HAMMER.get());
        basicItem(ItemInit.THERMAL_SPRINGSTONE_HELMET.get());
        basicItem(ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get());
        basicItem(ItemInit.THERMAL_SPRINGSTONE_LEGGINGS.get());
        basicItem(ItemInit.THERMAL_SPRINGSTONE_BOOTS.get());

        basicBlockItem(ItemInit.SWAMP_SILVER_ORE.get());
        basicBlockItem(ItemInit.SWAMP_SILVER_BLOCK.get());
        basicItem(ItemInit.SWAMP_SILVER_INGOT.get());
        basicItem(ItemInit.SWAMP_SILVER_NUGGET.get());
        basicHandheld(ItemInit.SWAMP_SILVER_SWORD.get());
        basicHandheld(ItemInit.SWAMP_SILVER_PICKAXE.get());
        basicHandheld(ItemInit.SWAMP_SILVER_AXE.get());
        basicHandheld(ItemInit.SWAMP_SILVER_SICKLE.get());
        armorWithTrim((ArmorItem) ItemInit.SWAMP_SILVER_HELMET.get());
        armorWithTrim((ArmorItem) ItemInit.SWAMP_SILVER_CHESTPLATE.get());
        basicItem(ItemInit.SWAMP_SILVER_LEGGINGS.get());
        basicItem(ItemInit.SWAMP_SILVER_BOOTS.get());

        basicItem(ItemInit.BROKEN_DOOMEDEN_BONE.get());
        largeHandheld(ItemInit.BONEMORE_BROADSWORD.get());
        inventoryModel(ItemInit.BONEMORE_BROADSWORD.get());
        bow(ItemInit.BOW_OF_BLOOD.get());
        basicHandheld(ItemInit.LIVING_ARM.get());
        basicItemWithBlockTexture(ItemInit.DOOMED_TORCH.get());
        basicItemWithBlockTexture(ItemInit.DOOMED_REDSTONE_TORCH.get());
        basicItem(ItemInit.DOOMEDEN_CARRION.get());
        basicHandheld(ItemInit.ROTTEN_HAM.get());
        basicItem(ItemInit.EYE_OF_DOOM.get());
        basicItem(ItemInit.DOOMEDEN_RAG.get());
        basicItem(ItemInit.DOOMEDEN_FLESH_GRINDER.get());
        inventoryModel(ItemInit.DOOMEDEN_SWORD.get());
        basicBlockItem(ItemInit.DOOMEDEN_BRICKS.get());
        basicBlockItem(ItemInit.DOOMEDEN_BRICK_SLAB.get());
        basicBlockItem(ItemInit.DOOMEDEN_BRICK_STAIRS.get());
        wall(ItemInit.DOOMEDEN_BRICK_WALL.get(), ItemInit.DOOMEDEN_BRICKS.get());
        basicBlockItem(ItemInit.POLISHED_DOOMEDEN_BRICKS.get());
        basicBlockItem(ItemInit.POLISHED_DOOMEDEN_BRICK_SLAB.get());
        basicBlockItem(ItemInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
        wall(ItemInit.POLISHED_DOOMEDEN_BRICK_WALL.get(), ItemInit.POLISHED_DOOMEDEN_BRICKS.get());
        basicBlockItem(ItemInit.DOOMEDEN_TILE.get());
        basicBlockItem(ItemInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        basicBlockItem(ItemInit.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        basicBlockItem(ItemInit.DOOMEDEN_LIGHT.get());
        basicBlockItem(ItemInit.DOOMEDEN_KEYHOLE.get());
        basicBlockItem(ItemInit.REDSTONE_DOOMEDEN_KEYHOLE.get());

        basicItem(ItemInit.GOLEM_STEEL_INGOT.get());
        basicItem(ItemInit.OXIDIZED_GOLEM_STEEL_INGOT.get());
        basicItem(ItemInit.TENACIOUS_PETAL.get());
        crossbow(ItemInit.CRYSTAL_CROSSBOW.get());
        bow(ItemInit.MOONRING_BOW.get());
        largeHandheld(ItemInit.MOONRING_GREATSWORD.get());
        inventoryModel(ItemInit.MOONRING_GREATSWORD.get());
        largeHandheld(ItemInit.PETAL_SCYTHE.get());
        inventoryModel(ItemInit.PETAL_SCYTHE.get());
        basicItem(ItemInit.SEEKING_EYE.get());
        inventoryModel(ItemInit.PROPHET_ORB.get());
    }

    private void armorWithTrim(ArmorItem armor) {
        ItemModelBuilder armorBuilder = basicItem(armor);
        for (ItemModelGenerators.TrimModelData trimModelData : ItemModelGenerators.GENERATED_TRIM_MODELS) {
            ModelFile trimModel = withExistingParent(name(armor) + "_" + trimModelData.name() + "_trim", mcLoc("item/generated"))
                    .texture("layer0", modLoc("item/" + name(armor)))
                    .texture("layer1", mcLoc("trims/items/" + armor.getType().getName() + "_trim_" + trimModelData.name()));
            armorBuilder.override().predicate(new ResourceLocation("trim_type"), trimModelData.itemModelIndex()).model(trimModel).end();
        }
    }

    private void crossbow(Item item) {
        ModelFile pull0 = withExistingParent(name(item) + "_pulling_0", "item/crossbow")
                .texture("layer0", itemTexture(item).withSuffix("_pulling_0"));
        ModelFile pull1 = withExistingParent(name(item) + "_pulling_1", "item/crossbow")
                .texture("layer0", itemTexture(item).withSuffix("_pulling_1"));
        ModelFile pull2 = withExistingParent(name(item) + "_pulling_2", "item/crossbow")
                .texture("layer0", itemTexture(item).withSuffix("_pulling_2"));
        ModelFile arrow = withExistingParent(name(item) + "_arrow", "item/crossbow")
                .texture("layer0", itemTexture(item).withSuffix("_arrow"));
        ModelFile firework = withExistingParent(name(item) + "_firework", "item/crossbow")
                .texture("layer0", itemTexture(item).withSuffix("_firework"));
        withExistingParent(name(item), "item/crossbow")
                .texture("layer0", itemTexture(item).withSuffix("_standby"))
                .override().predicate(new ResourceLocation("pulling"), 1).model(pull0).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.58).model(pull1).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 1.0).model(pull2).end()
                .override().predicate(new ResourceLocation("charged"), 1).model(arrow).end()
                .override().predicate(new ResourceLocation("charged"), 1).predicate(new ResourceLocation("firework"), 1).model(firework).end();
    }

    private void bow(Item item) {
        ModelFile pull0 = withExistingParent(name(item) + "_pulling_0", "item/bow")
                .texture("layer0", itemTexture(item).withSuffix("_pulling_0"));
        ModelFile pull1 = withExistingParent(name(item) + "_pulling_1", "item/bow")
                .texture("layer0", itemTexture(item).withSuffix("_pulling_1"));
        ModelFile pull2 = withExistingParent(name(item) + "_pulling_2", "item/bow")
                .texture("layer0", itemTexture(item).withSuffix("_pulling_2"));
        withExistingParent(name(item), "item/bow")
                .texture("layer0", itemTexture(item))
                .override().predicate(new ResourceLocation("pulling"), 1).model(pull0).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.65).model(pull1).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), (float) 0.9).model(pull2).end();
    }

    private void trapdoor(Item item) {
        withExistingParent(name(item), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(item) + "_bottom"));
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

    private void wall(Item wall, Item stone) {
        getBuilder(name(wall))
                .parent(getExistingFile(mcLoc("block/wall_inventory")))
                .texture("wall", blockTextureFromItem(stone));
    }

    private void cubeAll(Item cube) {
        getBuilder(name(cube))
                .parent(getExistingFile(mcLoc("block/cube_all")))
                .texture("all", blockTextureFromItem(cube));
    }

    private void basicBlockItem(Item item) {
        withExistingParent(name(item), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(item)));
    }

    private void basicItemWithBlockTexture(Item item) {
        basicItem(item, blockTextureFromItem(item));
    }

    private ItemModelBuilder basicItem(Item item, ResourceLocation texture) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", texture);
    }

    private ItemModelBuilder largeHandheld(Item item) {
        return basicHandheld(item, itemTexture(item), true);
    }

    private ItemModelBuilder basicHandheld(Item item) {
        return basicHandheld(item, itemTexture(item), false);
    }

    private ItemModelBuilder basicHandheld(Item item, ResourceLocation texture, boolean large) {
        return getBuilder(item.toString())
                .parent(large ? new ModelFile.UncheckedModelFile(EternalStarlight.MOD_ID + ":item/large_handheld") : new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", texture);
    }

    private ItemModelBuilder inventoryModel(Item item) {
        return getBuilder(item.toString() + "_inventory")
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", itemTexture(item) + "_inventory");
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
