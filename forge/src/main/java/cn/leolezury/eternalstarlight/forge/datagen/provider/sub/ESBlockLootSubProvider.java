package cn.leolezury.eternalstarlight.forge.datagen.provider.sub;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.BerriesVines;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.stream.Collectors;

public class ESBlockLootSubProvider extends BlockLootSubProvider {
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    public ESBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        add(BlockInit.BERRIES_VINES.get(), this::createBerriesVinesDrop);
        add(BlockInit.BERRIES_VINES_PLANT.get(), this::createBerriesVinesDrop);

        add(BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ItemInit.RED_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));
        add(BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get(), (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ItemInit.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(ItemInit.BLUE_STARLIGHT_CRYSTAL_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));

        dropSelf(BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get());
        dropSelf(BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());

        dropSelf(BlockInit.RED_CRYSTAL_MOSS_CARPET.get());
        dropSelf(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get());

        add(BlockInit.LUNAR_LEAVES.get(), (block) -> this.createLunarLeavesDrops(block, BlockInit.LUNAR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(BlockInit.LUNAR_LOG.get());
        dropSelf(BlockInit.LUNAR_WOOD.get());
        dropSelf(BlockInit.LUNAR_PLANKS.get());
        dropSelf(BlockInit.STRIPPED_LUNAR_LOG.get());
        dropSelf(BlockInit.STRIPPED_LUNAR_WOOD.get());
        add(BlockInit.LUNAR_DOOR.get(), this::createDoorTable);
        dropSelf(BlockInit.LUNAR_TRAPDOOR.get());
        dropSelf(BlockInit.LUNAR_PRESSURE_PLATE.get());
        dropSelf(BlockInit.LUNAR_BUTTON.get());
        dropSelf(BlockInit.LUNAR_FENCE.get());
        dropSelf(BlockInit.LUNAR_FENCE_GATE.get());
        add(BlockInit.LUNAR_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.LUNAR_STAIRS.get());
        dropSelf(BlockInit.LUNAR_SIGN.get());
        dropSelf(BlockInit.LUNAR_WALL_SIGN.get());
        dropSelf(BlockInit.LUNAR_HANGING_SIGN.get());
        dropSelf(BlockInit.LUNAR_WALL_HANGING_SIGN.get());
        dropSelf(BlockInit.LUNAR_SAPLING.get());
        dropPottedContents(BlockInit.POTTED_LUNAR_SAPLING.get());

        add(BlockInit.NORTHLAND_LEAVES.get(), (block) -> this.createLeavesDrops(block, BlockInit.NORTHLAND_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(BlockInit.NORTHLAND_LOG.get());
        dropSelf(BlockInit.NORTHLAND_WOOD.get());
        dropSelf(BlockInit.NORTHLAND_PLANKS.get());
        dropSelf(BlockInit.STRIPPED_NORTHLAND_LOG.get());
        dropSelf(BlockInit.STRIPPED_NORTHLAND_WOOD.get());
        add(BlockInit.NORTHLAND_DOOR.get(), this::createDoorTable);
        dropSelf(BlockInit.NORTHLAND_TRAPDOOR.get());
        dropSelf(BlockInit.NORTHLAND_PRESSURE_PLATE.get());
        dropSelf(BlockInit.NORTHLAND_BUTTON.get());
        dropSelf(BlockInit.NORTHLAND_FENCE.get());
        dropSelf(BlockInit.NORTHLAND_FENCE_GATE.get());
        add(BlockInit.NORTHLAND_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.NORTHLAND_STAIRS.get());
        dropSelf(BlockInit.NORTHLAND_SIGN.get());
        dropSelf(BlockInit.NORTHLAND_WALL_SIGN.get());
        dropSelf(BlockInit.NORTHLAND_HANGING_SIGN.get());
        dropSelf(BlockInit.NORTHLAND_WALL_HANGING_SIGN.get());
        dropSelf(BlockInit.NORTHLAND_SAPLING.get());
        dropPottedContents(BlockInit.POTTED_NORTHLAND_SAPLING.get());

        add(BlockInit.STARLIGHT_MANGROVE_LEAVES.get(), (block) -> this.createLeavesDrops(block, BlockInit.STARLIGHT_MANGROVE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(BlockInit.STARLIGHT_MANGROVE_LOG.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_WOOD.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_PLANKS.get());
        dropSelf(BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get());
        dropSelf(BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get());
        add(BlockInit.STARLIGHT_MANGROVE_DOOR.get(), this::createDoorTable);
        dropSelf(BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_BUTTON.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_FENCE.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get());
        add(BlockInit.STARLIGHT_MANGROVE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.STARLIGHT_MANGROVE_STAIRS.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_SIGN.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_SAPLING.get());
        dropPottedContents(BlockInit.POTTED_STARLIGHT_MANGROVE_SAPLING.get());
        dropSelf(BlockInit.STARLIGHT_MANGROVE_ROOTS.get());
        dropSelf(BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

        dropSelf(BlockInit.GRIMSTONE.get());
        dropSelf(BlockInit.GRIMSTONE_BRICKS.get());
        add(BlockInit.GRIMSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.GRIMSTONE_BRICK_STAIRS.get());
        dropSelf(BlockInit.GRIMSTONE_BRICK_WALL.get());
        dropSelf(BlockInit.POLISHED_GRIMSTONE.get());
        add(BlockInit.POLISHED_GRIMSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_GRIMSTONE_STAIRS.get());
        dropSelf(BlockInit.POLISHED_GRIMSTONE_WALL.get());
        dropSelf(BlockInit.CHISELED_GRIMSTONE.get());

        dropSelf(BlockInit.VOIDSTONE.get());
        dropSelf(BlockInit.VOIDSTONE_BRICKS.get());
        add(BlockInit.VOIDSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.VOIDSTONE_BRICK_STAIRS.get());
        dropSelf(BlockInit.VOIDSTONE_BRICK_WALL.get());
        dropSelf(BlockInit.POLISHED_VOIDSTONE.get());
        add(BlockInit.POLISHED_VOIDSTONE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_VOIDSTONE_STAIRS.get());
        dropSelf(BlockInit.POLISHED_VOIDSTONE_WALL.get());
        dropSelf(BlockInit.CHISELED_VOIDSTONE.get());
        dropSelf(BlockInit.GLOWING_VOIDSTONE.get());

        dropSelf(BlockInit.NIGHTSHADE_MUD.get());
        dropSelf(BlockInit.GLOWING_NIGHTSHADE_MUD.get());
        dropSelf(BlockInit.PACKED_NIGHTSHADE_MUD.get());
        dropSelf(BlockInit.NIGHTSHADE_MUD_BRICKS.get());
        add(BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get());
        dropSelf(BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get());

        dropSelf(BlockInit.DOOMED_TORCH.get());
        dropOther(BlockInit.WALL_DOOMED_TORCH.get(), ItemInit.DOOMED_TORCH.get());
        dropSelf(BlockInit.DOOMED_REDSTONE_TORCH.get());
        dropOther(BlockInit.WALL_DOOMED_REDSTONE_TORCH.get(), ItemInit.DOOMED_REDSTONE_TORCH.get());
        dropSelf(BlockInit.DOOMEDEN_BRICKS.get());
        add(BlockInit.DOOMEDEN_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.DOOMEDEN_BRICK_STAIRS.get());
        dropSelf(BlockInit.DOOMEDEN_BRICK_WALL.get());
        dropSelf(BlockInit.POLISHED_DOOMEDEN_BRICKS.get());
        add(BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get());
        dropSelf(BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get());
        dropSelf(BlockInit.DOOMEDEN_TILE.get());
        dropSelf(BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        dropSelf(BlockInit.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        dropSelf(BlockInit.DOOMEDEN_LIGHT.get());
        dropSelf(BlockInit.DOOMEDEN_KEYHOLE.get());
        dropSelf(BlockInit.REDSTONE_DOOMEDEN_KEYHOLE.get());

        dropSelf(BlockInit.STARLIGHT_FLOWER.get());
        dropPottedContents(BlockInit.POTTED_STARLIGHT_FLOWER.get());
        dropSelf(BlockInit.NIGHT_SPROUTS.get());
        dropSelf(BlockInit.SMALL_NIGHT_SPROUTS.get());
        dropSelf(BlockInit.GLOWING_NIGHT_SPROUTS.get());
        dropSelf(BlockInit.SMALL_GLOWING_NIGHT_SPROUTS.get());
        dropSelf(BlockInit.LUNAR_GRASS.get());
        dropSelf(BlockInit.GLOWING_LUNAR_GRASS.get());
        dropSelf(BlockInit.CRESCENT_GRASS.get());
        dropSelf(BlockInit.GLOWING_CRESCENT_GRASS.get());
        dropSelf(BlockInit.PARASOL_GRASS.get());
        dropSelf(BlockInit.GLOWING_PARASOL_GRASS.get());
        add(BlockInit.LUNAR_REED.get(), BlockLootSubProvider::createDoublePlantShearsDrop);
        dropSelf(BlockInit.GLOWING_MUSHROOM.get());
        add(BlockInit.GLOWING_MUSHROOM_BLOCK.get(), (block -> createMushroomBlockDrop(block, BlockInit.GLOWING_MUSHROOM.get())));

        dropSelf(BlockInit.SWAMP_ROSE.get());
        dropPottedContents(BlockInit.POTTED_SWAMP_ROSE.get());
        dropSelf(BlockInit.FANTABUD.get());
        dropSelf(BlockInit.GREEN_FANTABUD.get());
        dropSelf(BlockInit.FANTAFERN.get());
        dropSelf(BlockInit.GREEN_FANTAFERN.get());
        dropSelf(BlockInit.FANTAGRASS.get());
        dropSelf(BlockInit.GREEN_FANTAGRASS.get());
        add(BlockInit.NIGHTSHADE_GRASS_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.NIGHTSHADE_DIRT.get()));
        add(BlockInit.FANTASY_GRASS_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, BlockInit.NIGHTSHADE_MUD.get()));
        dropSelf(BlockInit.NIGHTSHADE_DIRT.get());

        dropSelf(BlockInit.AETHERSENT_BLOCK.get());
        dropSelf(BlockInit.SPRINGSTONE.get());
        dropSelf(BlockInit.THERMAL_SPRINGSTONE.get());
        add(BlockInit.SWAMP_SILVER_ORE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ItemInit.SWAMP_SILVER_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        dropSelf(BlockInit.SWAMP_SILVER_BLOCK.get());

        // nothing
        add(BlockInit.ENERGY_BLOCK.get(), noDrop());
        add(BlockInit.STARLIGHT_GOLEM_SPAWNER.get(), noDrop());
        add(BlockInit.LUNAR_MONSTROSITY_SPAWNER.get(), noDrop());
        add(BlockInit.STARLIGHT_PORTAL.get(), noDrop());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(EternalStarlight.MOD_ID)).collect(Collectors.toList());
    }

    private LootTable.Builder createBerriesVinesDrop(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemInit.LUNAR_BERRIES.get())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BerriesVines.BERRIES, true))));
    }

    private LootTable.Builder createLunarLeavesDrops(Block leaves, Block sapling, float... saplingChances) {
        return this.createLeavesDrops(leaves, sapling, saplingChances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(leaves, LootItem.lootTableItem(ItemInit.LUNAR_BERRIES.get()))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
}
