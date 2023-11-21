package cn.leolezury.eternalstarlight.forge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.BerriesVines;
import cn.leolezury.eternalstarlight.common.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.ForgeRegistries;

public class ESBlockStateProvider extends BlockStateProvider {
    // Render Types
    private static final ResourceLocation SOLID = new ResourceLocation("solid");
    private static final ResourceLocation CUTOUT = new ResourceLocation("cutout");
    private static final ResourceLocation CUTOUT_MIPPED = new ResourceLocation("cutout_mipped");
    private static final ResourceLocation TRANSLUCENT = new ResourceLocation("translucent");

    public ESBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EternalStarlight.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        berriesVines(BlockInit.BERRIES_VINES.get());
        berriesVines(BlockInit.BERRIES_VINES_PLANT.get());
        crossBlock(BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER.get());
        crossBlock(BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER.get());
        simpleBlock(BlockInit.RED_STARLIGHT_CRYSTAL_BLOCK.get());
        simpleBlock(BlockInit.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
        carpetBlock(BlockInit.RED_CRYSTAL_MOSS_CARPET.get(), blockTexture(BlockInit.RED_CRYSTAL_MOSS_CARPET.get()));
        carpetBlock(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get(), blockTexture(BlockInit.BLUE_CRYSTAL_MOSS_CARPET.get()));

        // woods
        simpleBlockWithRenderType(BlockInit.LUNAR_LEAVES.get(), CUTOUT_MIPPED);
        woodSet(BlockInit.LUNAR_LOG.get(), BlockInit.LUNAR_WOOD.get(), BlockInit.LUNAR_PLANKS.get(), BlockInit.STRIPPED_LUNAR_LOG.get(), BlockInit.STRIPPED_LUNAR_WOOD.get(), BlockInit.LUNAR_DOOR.get(), false, BlockInit.LUNAR_TRAPDOOR.get(), false, BlockInit.LUNAR_PRESSURE_PLATE.get(), BlockInit.LUNAR_BUTTON.get(), BlockInit.LUNAR_FENCE.get(), BlockInit.LUNAR_FENCE_GATE.get(), BlockInit.LUNAR_SLAB.get(), BlockInit.LUNAR_STAIRS.get(), BlockInit.LUNAR_SIGN.get(), BlockInit.LUNAR_WALL_SIGN.get(), BlockInit.LUNAR_HANGING_SIGN.get(), BlockInit.LUNAR_WALL_HANGING_SIGN.get());
        crossBlock(BlockInit.LUNAR_SAPLING.get());
        pottedPlant(BlockInit.POTTED_LUNAR_SAPLING.get(), blockTexture(BlockInit.LUNAR_SAPLING.get()));

        simpleBlockWithRenderType(BlockInit.NORTHLAND_LEAVES.get(), CUTOUT_MIPPED);
        woodSet(BlockInit.NORTHLAND_LOG.get(), BlockInit.NORTHLAND_WOOD.get(), BlockInit.NORTHLAND_PLANKS.get(), BlockInit.STRIPPED_NORTHLAND_LOG.get(), BlockInit.STRIPPED_NORTHLAND_WOOD.get(), BlockInit.NORTHLAND_DOOR.get(), false, BlockInit.NORTHLAND_TRAPDOOR.get(), false, BlockInit.NORTHLAND_PRESSURE_PLATE.get(), BlockInit.NORTHLAND_BUTTON.get(), BlockInit.NORTHLAND_FENCE.get(), BlockInit.NORTHLAND_FENCE_GATE.get(), BlockInit.NORTHLAND_SLAB.get(), BlockInit.NORTHLAND_STAIRS.get(), BlockInit.NORTHLAND_SIGN.get(), BlockInit.NORTHLAND_WALL_SIGN.get(), BlockInit.NORTHLAND_HANGING_SIGN.get(), BlockInit.NORTHLAND_WALL_HANGING_SIGN.get());
        crossBlock(BlockInit.NORTHLAND_SAPLING.get());
        pottedPlant(BlockInit.POTTED_NORTHLAND_SAPLING.get(), blockTexture(BlockInit.NORTHLAND_SAPLING.get()));

        simpleBlockWithRenderType(BlockInit.STARLIGHT_MANGROVE_LEAVES.get(), CUTOUT_MIPPED);
        woodSet(BlockInit.STARLIGHT_MANGROVE_LOG.get(), BlockInit.STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STARLIGHT_MANGROVE_PLANKS.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get(), BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get(), BlockInit.STARLIGHT_MANGROVE_DOOR.get(), true, BlockInit.STARLIGHT_MANGROVE_TRAPDOOR.get(), true, BlockInit.STARLIGHT_MANGROVE_PRESSURE_PLATE.get(), BlockInit.STARLIGHT_MANGROVE_BUTTON.get(), BlockInit.STARLIGHT_MANGROVE_FENCE.get(), BlockInit.STARLIGHT_MANGROVE_FENCE_GATE.get(), BlockInit.STARLIGHT_MANGROVE_SLAB.get(), BlockInit.STARLIGHT_MANGROVE_STAIRS.get(), BlockInit.STARLIGHT_MANGROVE_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_HANGING_SIGN.get(), BlockInit.STARLIGHT_MANGROVE_WALL_HANGING_SIGN.get());
        crossBlock(BlockInit.STARLIGHT_MANGROVE_SAPLING.get());
        pottedPlant(BlockInit.POTTED_STARLIGHT_MANGROVE_SAPLING.get(), blockTexture(BlockInit.STARLIGHT_MANGROVE_SAPLING.get()));
        mangroveRoots(BlockInit.STARLIGHT_MANGROVE_ROOTS.get());
        mangroveRoots(BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS.get());

        // stones
        simpleBlock(BlockInit.GRIMSTONE.get());
        simpleBlock(BlockInit.CHISELED_GRIMSTONE.get());
        stoneSet(BlockInit.GRIMSTONE_BRICKS.get(), BlockInit.GRIMSTONE_BRICK_SLAB.get(), BlockInit.GRIMSTONE_BRICK_STAIRS.get(), BlockInit.GRIMSTONE_BRICK_WALL.get());
        stoneSet(BlockInit.POLISHED_GRIMSTONE.get(), BlockInit.POLISHED_GRIMSTONE_SLAB.get(), BlockInit.POLISHED_GRIMSTONE_STAIRS.get(), BlockInit.POLISHED_GRIMSTONE_WALL.get());

        simpleBlock(BlockInit.VOIDSTONE.get());
        simpleBlock(BlockInit.CHISELED_VOIDSTONE.get());
        stoneSet(BlockInit.VOIDSTONE_BRICKS.get(), BlockInit.VOIDSTONE_BRICK_SLAB.get(), BlockInit.VOIDSTONE_BRICK_STAIRS.get(), BlockInit.VOIDSTONE_BRICK_WALL.get());
        stoneSet(BlockInit.POLISHED_VOIDSTONE.get(), BlockInit.POLISHED_VOIDSTONE_SLAB.get(), BlockInit.POLISHED_VOIDSTONE_STAIRS.get(), BlockInit.POLISHED_VOIDSTONE_WALL.get());

        simpleBlock(BlockInit.NIGHTSHADE_MUD.get());
        simpleBlock(BlockInit.GLOWING_NIGHTSHADE_MUD.get());
        simpleBlock(BlockInit.PACKED_NIGHTSHADE_MUD.get());
        stoneSet(BlockInit.NIGHTSHADE_MUD_BRICKS.get(), BlockInit.NIGHTSHADE_MUD_BRICK_SLAB.get(), BlockInit.NIGHTSHADE_MUD_BRICK_STAIRS.get(), BlockInit.NIGHTSHADE_MUD_BRICK_WALL.get());

        // doomeden
        simpleBlock(BlockInit.DOOMEDEN_TILE.get());
        simpleBlock(BlockInit.CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        simpleBlock(BlockInit.CHARGED_CHISELED_POLISHED_DOOMEDEN_BRICKS.get());
        torch(BlockInit.DOOMED_TORCH.get(), BlockInit.WALL_DOOMED_TORCH.get());
        redstoneTorch(BlockInit.DOOMED_REDSTONE_TORCH.get(), BlockInit.WALL_DOOMED_REDSTONE_TORCH.get());
        stoneSet(BlockInit.DOOMEDEN_BRICKS.get(), BlockInit.DOOMEDEN_BRICK_SLAB.get(), BlockInit.DOOMEDEN_BRICK_STAIRS.get(), BlockInit.DOOMEDEN_BRICK_WALL.get());
        stoneSet(BlockInit.POLISHED_DOOMEDEN_BRICKS.get(), BlockInit.POLISHED_DOOMEDEN_BRICK_SLAB.get(), BlockInit.POLISHED_DOOMEDEN_BRICK_STAIRS.get(), BlockInit.POLISHED_DOOMEDEN_BRICK_WALL.get());
        onOffBlock(BlockInit.DOOMEDEN_LIGHT.get());
        doomedenKeyhole(BlockInit.DOOMEDEN_KEYHOLE.get(), BlockInit.REDSTONE_DOOMEDEN_KEYHOLE.get());

        crossBlock(BlockInit.STARLIGHT_FLOWER.get());
        pottedPlant(BlockInit.POTTED_STARLIGHT_FLOWER.get(), blockTexture(BlockInit.STARLIGHT_FLOWER.get()));
        crossBlock(BlockInit.NIGHT_SPROUTS.get());
        crossBlock(BlockInit.SMALL_NIGHT_SPROUTS.get());
        crossBlock(BlockInit.GLOWING_NIGHT_SPROUTS.get());
        crossBlock(BlockInit.SMALL_GLOWING_NIGHT_SPROUTS.get());
        crossBlock(BlockInit.LUNAR_GRASS.get());
        crossBlock(BlockInit.GLOWING_LUNAR_GRASS.get());
        crossBlock(BlockInit.CRESCENT_GRASS.get());
        crossBlock(BlockInit.GLOWING_CRESCENT_GRASS.get());
        crossBlock(BlockInit.PARASOL_GRASS.get());
        crossBlock(BlockInit.GLOWING_PARASOL_GRASS.get());
        doublePlant(BlockInit.LUNAR_REED.get());
        crossBlock(BlockInit.GLOWING_MUSHROOM.get());
        singleFace(BlockInit.GLOWING_MUSHROOM_BLOCK.get());

        crossBlock(BlockInit.SWAMP_ROSE.get());
        pottedPlant(BlockInit.POTTED_SWAMP_ROSE.get(), blockTexture(BlockInit.SWAMP_ROSE.get()));
        crossBlock(BlockInit.FANTABUD.get());
        crossBlock(BlockInit.GREEN_FANTABUD.get());
        crossBlock(BlockInit.FANTAFERN.get());
        crossBlock(BlockInit.GREEN_FANTAFERN.get());
        crossBlock(BlockInit.FANTAGRASS.get());
        crossBlock(BlockInit.GREEN_FANTAGRASS.get());

        simpleBlock(BlockInit.NIGHTSHADE_DIRT.get());
        grassBlock(BlockInit.NIGHTSHADE_GRASS_BLOCK.get(), blockTexture(BlockInit.NIGHTSHADE_DIRT.get()));
        simpleGrassBlock(BlockInit.FANTASY_GRASS_BLOCK.get(), blockTexture(BlockInit.NIGHTSHADE_MUD.get()));

        simpleBlock(BlockInit.AETHERSENT_BLOCK.get());
        simpleBlock(BlockInit.SPRINGSTONE.get());
        simpleBlock(BlockInit.THERMAL_SPRINGSTONE.get());
        simpleBlock(BlockInit.SWAMP_SILVER_ORE.get());
        simpleBlock(BlockInit.SWAMP_SILVER_BLOCK.get());
        onOffBlock(BlockInit.ENERGY_BLOCK.get());
        spawner(BlockInit.STARLIGHT_GOLEM_SPAWNER.get());
        spawner(BlockInit.LUNAR_MONSTROSITY_SPAWNER.get());
        portal(BlockInit.STARLIGHT_PORTAL.get());
    }

    private void woodSet(RotatedPillarBlock log, Block wood, Block planks, RotatedPillarBlock strippedLog, Block strippedWood, DoorBlock door, boolean cutoutDoor, TrapDoorBlock trapdoor, boolean cutoutTrapdoor, PressurePlateBlock pressurePlate, ButtonBlock button, FenceBlock fence, FenceGateBlock fenceGate, SlabBlock slab, StairBlock stairs, Block sign, Block wallSign, Block hangingSign, Block wallHangingSign) {
        logBlock(log);
        simpleBlock(wood, models().cubeAll(name(wood), blockTexture(log)));
        simpleBlock(planks);
        logBlock(strippedLog);
        simpleBlock(strippedWood, models().cubeAll(name(strippedWood), blockTexture(log)));
        if (cutoutDoor) {
            doorBlockWithRenderType(door, blockTexture(door).withSuffix("_top"), blockTexture(door).withSuffix("_bottom"), CUTOUT);
        } else {
            doorBlock(door, blockTexture(door).withSuffix("_top"), blockTexture(door).withSuffix("_bottom"));
        }
        if (cutoutTrapdoor) {
            trapdoorBlockWithRenderType(trapdoor, blockTexture(trapdoor), true, CUTOUT);
        } else {
            trapdoorBlock(trapdoor, blockTexture(trapdoor), true);
        }
        pressurePlateBlock(pressurePlate, blockTexture(planks));
        buttonBlock(button, blockTexture(planks));
        fenceBlock(fence, blockTexture(planks));
        fenceGateBlock(fenceGate, blockTexture(planks));
        slabBlock(slab, blockTexture(planks), blockTexture(planks));
        stairsBlock(stairs, blockTexture(planks));
        simpleSign(sign, wallSign, blockTexture(planks));
        simpleSign(hangingSign, wallHangingSign, blockTexture(planks));
    }

    private void stoneSet(Block stone, SlabBlock slab, StairBlock stairs, WallBlock wall) {
        simpleBlock(stone);
        slabBlock(slab, blockTexture(stone), blockTexture(stone));
        stairsBlock(stairs, blockTexture(stone));
        wallBlock(wall, blockTexture(stone));
    }

    private void portal(Block block) {
        ModelFile modelEw = models().getBuilder(name(block) + "_ew")
                .texture("particle", blockTexture(block))
                .texture("portal", blockTexture(block))
                .renderType(TRANSLUCENT)
                .element()
                .from(6, 0, 0)
                .to(10, 16, 16)
                .face(Direction.EAST)
                .uvs(0, 0, 16, 16)
                .texture("#portal")
                .end()
                .face(Direction.WEST)
                .uvs(0, 0, 16, 16)
                .texture("#portal")
                .end()
                .end();
        ModelFile modelNs = models().getBuilder(name(block) + "_ns")
                .texture("particle", blockTexture(block))
                .texture("portal", blockTexture(block))
                .renderType(TRANSLUCENT)
                .element()
                .from(0, 0, 6)
                .to(16, 16, 10)
                .face(Direction.NORTH)
                .uvs(0, 0, 16, 16)
                .texture("#portal")
                .end()
                .face(Direction.SOUTH)
                .uvs(0, 0, 16, 16)
                .texture("#portal")
                .end()
                .end();
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Direction.Axis.X ? modelNs : modelEw).build());
    }

    private void berriesVines(Block block) {
        ModelFile modelNormal = models().cross(name(block), blockTexture(block)).renderType(CUTOUT);
        ModelFile modelLit = models().cross(name(block) + "_lit", blockTexture(block).withSuffix("_lit")).renderType(CUTOUT);
        onOffBlock(block, BerriesVines.BERRIES, modelLit, modelNormal);
    }

    private void mangroveRoots(Block block) {
        cubeColumn(block, blockTexture(block).withSuffix("_top"), blockTexture(block).withSuffix("_side"), CUTOUT_MIPPED);
    }

    private void doomedenKeyhole(Block block, Block redstone) {
        ModelFile modelOn = models().orientable(name(block) + "_lit", blockTexture(block).withSuffix("_on_side"), blockTexture(block).withSuffix("_on_front"), blockTexture(BlockInit.DOOMEDEN_TILE.get()));
        ModelFile modelOff = models().orientable(name(block), blockTexture(block).withSuffix("_off_side"), blockTexture(block).withSuffix("_off_front"), blockTexture(BlockInit.DOOMEDEN_TILE.get()));
        horizontalBlock(block, state -> state.getValue(BlockStateProperties.LIT) ? modelOn : modelOff);

        ModelFile modelOnRedstone = models().orientable(name(redstone) + "_lit", blockTexture(block).withSuffix("_on_side"), blockTexture(redstone).withSuffix("_on"), blockTexture(BlockInit.DOOMEDEN_TILE.get()));
        ModelFile modelOffRedstone = models().orientable(name(redstone), blockTexture(block).withSuffix("_off_side"), blockTexture(redstone).withSuffix("_off"), blockTexture(BlockInit.DOOMEDEN_TILE.get()));
        horizontalBlock(redstone, state -> state.getValue(BlockStateProperties.LIT) ? modelOnRedstone : modelOffRedstone);
    }

    private void spawner(Block block) {
        simpleBlock(block, models().cubeAll(name(block), blockTexture(Blocks.SPAWNER)).renderType(CUTOUT));
    }

    private void simpleGrassBlock(Block grassBlock, ResourceLocation dirt) {
        ModelFile modelFile = models().cubeBottomTop(name(grassBlock), blockTexture(grassBlock).withSuffix("_side"), dirt, blockTexture(grassBlock).withSuffix("_top"));
        getVariantBuilder(grassBlock).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(modelFile).nextModel()
                .rotationY(270).modelFile(modelFile).nextModel()
                .rotationY(180).modelFile(modelFile).nextModel()
                .rotationY(90).modelFile(modelFile).build());
    }

    private void grassBlock(Block grassBlock, ResourceLocation dirt) {
        ModelFile modelNormal = models().withExistingParent(name(grassBlock), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/block")).renderType(CUTOUT_MIPPED)
                .texture("particle", dirt).texture("bottom", dirt).texture("top", blockTexture(grassBlock).withSuffix("_top")).texture("side", blockTexture(grassBlock).withSuffix("_side")).texture("overlay", blockTexture(grassBlock).withSuffix("_side_overlay"))
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .allFaces((dir, builder) -> {
                    var faceBuilder = builder
                            .uvs(0, 0, 16, 16)
                            .texture("#" + (dir == Direction.UP ? "top" : (dir == Direction.DOWN ? "bottom" : "side")))
                            .cullface(dir);
                    if (dir == Direction.UP) {
                        faceBuilder.tintindex(0).end();
                    } else {
                        faceBuilder.end();
                    }
                })
                .end()
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(Direction.NORTH)
                .uvs(0, 0, 16, 16)
                .texture("#overlay")
                .cullface(Direction.NORTH)
                .tintindex(0)
                .end()
                .face(Direction.SOUTH)
                .uvs(0, 0, 16, 16)
                .texture("#overlay")
                .cullface(Direction.SOUTH)
                .tintindex(0)
                .end()
                .face(Direction.WEST)
                .uvs(0, 0, 16, 16)
                .texture("#overlay")
                .cullface(Direction.WEST)
                .tintindex(0)
                .end()
                .face(Direction.EAST)
                .uvs(0, 0, 16, 16)
                .texture("#overlay")
                .cullface(Direction.EAST)
                .tintindex(0)
                .end()
                .end();
        ModelFile modelSnow = models().cubeBottomTop(name(grassBlock) + "_snow", blockTexture(grassBlock).withSuffix("_snow"), dirt, blockTexture(grassBlock).withSuffix("_top"));
        getVariantBuilder(grassBlock).forAllStates(state -> {
            if (state.getValue(BlockStateProperties.SNOWY)) {
                return ConfiguredModel.builder().modelFile(modelSnow).build();
            } else {
                return ConfiguredModel.builder()
                        .modelFile(modelNormal).nextModel()
                        .rotationY(270).modelFile(modelNormal).nextModel()
                        .rotationY(180).modelFile(modelNormal).nextModel()
                        .rotationY(90).modelFile(modelNormal).build();
            }
        });
    }

    private void torch(Block normal, Block wall) {
        ModelFile modelNormal = models().torch(name(normal), blockTexture(normal));
        ModelFile modelWall = models().torchWall(name(wall), blockTexture(normal));
        simpleBlock(normal, modelNormal);
        horizontalBlock(wall, modelWall, 90);
    }

    private void redstoneTorch(Block normal, Block wall) {
        ModelFile modelNormal = models().torch(name(normal) + "_lit", blockTexture(normal));
        ModelFile modelNormalOff = models().torch(name(normal), blockTexture(normal).withSuffix("_off"));
        ModelFile modelWall = models().torchWall(name(wall) + "_lit", blockTexture(normal));
        ModelFile modelWallOff = models().torchWall(name(wall), blockTexture(normal).withSuffix("_off"));
        onOffBlock(normal, BlockStateProperties.LIT, modelNormal, modelNormalOff);
        horizontalBlock(wall, state -> state.getValue(BlockStateProperties.LIT) ? modelWall : modelWallOff, 90);
    }

    private void onOffBlock(Block block) {
        ModelFile on = models().cubeAll(name(block) + "_lit", blockTexture(block).withSuffix("_lit"));
        ModelFile off = models().cubeAll(name(block), blockTexture(block));
        onOffBlock(block, BlockStateProperties.LIT, on, off);
    }

    private void onOffBlock(Block block, BooleanProperty property, ModelFile on, ModelFile off) {
        getVariantBuilder(block)
                .partialState().with(property, false)
                .modelForState().modelFile(off).addModel()
                .partialState().with(property, true)
                .modelForState().modelFile(on).addModel();
    }

    private void simpleSign(Block normal, Block wall, ResourceLocation location) {
        simpleBlock(normal, models().getBuilder(name(normal)).texture("particle", location));
        simpleBlock(wall, models().getBuilder(name(wall)).texture("particle", location));
    }

    private void simpleBlockWithRenderType(Block block, ResourceLocation renderType) {
        simpleBlock(block, models().cubeAll(name(block), blockTexture(block)).renderType(renderType));
    }

    private void pottedPlant(Block potted, ResourceLocation location) {
        ModelFile modelFile = models().singleTexture(name(potted), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/flower_pot_cross"), "plant", location).renderType(CUTOUT);
        simpleBlock(potted, modelFile);
    }

    private void doublePlant(Block block) {
        ModelFile upper = models().cross(name(block) + "_top", blockTexture(block).withSuffix("_top")).renderType(CUTOUT);
        ModelFile lower = models().cross(name(block) + "_bottom", blockTexture(block).withSuffix("_bottom")).renderType(CUTOUT);
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER)
                .modelForState().modelFile(upper).addModel()
                .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
                .modelForState().modelFile(lower).addModel();
    }

    private void cubeColumn(Block block, ResourceLocation end, ResourceLocation side, ResourceLocation renderType) {
        ModelFile modelFile = models().cubeColumn(name(block), side, end).renderType(renderType);
        simpleBlock(block, modelFile);
    }

    private void singleFace(Block block) {
        ModelFile modelFile = models().singleTexture(name(block), new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/template_single_face"), blockTexture(block));
        simpleBlock(block, modelFile);
    }

    private void crossBlock(Block block) {
        crossBlock(block, CUTOUT);
    }

    private void crossBlock(Block block, ResourceLocation renderType) {
        ModelFile modelFile = models().cross(name(block), blockTexture(block)).renderType(renderType);
        simpleBlock(block, modelFile);
    }

    private void carpetBlock(Block block, ResourceLocation wool) {
        ModelFile modelFile = models().carpet(name(block), wool).renderType(CUTOUT);
        simpleBlock(block, modelFile);
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }
}
