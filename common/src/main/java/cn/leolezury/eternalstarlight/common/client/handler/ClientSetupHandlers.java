package cn.leolezury.eternalstarlight.common.client.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.entity.ESWoodTypes;
import cn.leolezury.eternalstarlight.common.client.model.*;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimation;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimationState;
import cn.leolezury.eternalstarlight.common.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.common.client.particle.lightning.LightningParticle;
import cn.leolezury.eternalstarlight.common.client.renderer.*;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import cn.leolezury.eternalstarlight.common.init.*;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;


@Environment(EnvType.CLIENT)
public class ClientSetupHandlers {
    public interface BlockColorRegisterStrategy {
        void register(BlockColor blockColor, Block... itemLikes);
    }

    public interface ItemColorRegisterStrategy {
        void register(ItemColor itemColor, ItemLike... itemLikes);
    }

    public interface ParticleProviderRegisterStrategy {
        <T extends ParticleOptions> void register(ParticleType<T> particle, ParticleEngine.SpriteParticleRegistration<T> provider);
    }

    public interface EntityRendererRegisterStrategy {
        <T extends Entity> void register(EntityType<? extends T> entityType, EntityRendererProvider<T> entityRendererProvider);
    }

    public interface RendererLayerRegisterStrategy {
        void register(ModelLayerLocation layerLocation, Supplier<LayerDefinition> supplier);
    }

    public static final List<Supplier<Block>> cutoutMippedBlocks = List.of(
            BlockInit.LUNAR_LEAVES,
            BlockInit.NORTHLAND_LEAVES,
            BlockInit.STARLIGHT_MANGROVE_LEAVES,
            BlockInit.STARLIGHT_MANGROVE_ROOTS,
            BlockInit.MUDDY_STARLIGHT_MANGROVE_ROOTS
    );

    public static final List<Supplier<Block>> cutoutBlocks = List.of(
            BlockInit.BERRIES_VINES,
            BlockInit.BERRIES_VINES_PLANT,
            BlockInit.LUNAR_SAPLING,
            BlockInit.POTTED_LUNAR_SAPLING,
            BlockInit.LUNAR_TRAPDOOR,
            BlockInit.LUNAR_DOOR,
            BlockInit.NORTHLAND_SAPLING,
            BlockInit.POTTED_NORTHLAND_SAPLING,
            BlockInit.NORTHLAND_TRAPDOOR,
            BlockInit.NORTHLAND_DOOR,
            BlockInit.STARLIGHT_MANGROVE_SAPLING,
            BlockInit.POTTED_STARLIGHT_MANGROVE_SAPLING,
            BlockInit.STARLIGHT_MANGROVE_TRAPDOOR,
            BlockInit.STARLIGHT_MANGROVE_DOOR,
            BlockInit.BLUE_STARLIGHT_CRYSTAL_CLUSTER,
            BlockInit.RED_STARLIGHT_CRYSTAL_CLUSTER,
            BlockInit.STARLIGHT_FLOWER,
            BlockInit.POTTED_STARLIGHT_FLOWER,
            BlockInit.NIGHT_SPROUTS,
            BlockInit.SMALL_NIGHT_SPROUTS,
            BlockInit.GLOWING_NIGHT_SPROUTS,
            BlockInit.SMALL_GLOWING_NIGHT_SPROUTS,
            BlockInit.LUNAR_GRASS,
            BlockInit.GLOWING_LUNAR_GRASS,
            BlockInit.CRESCENT_GRASS,
            BlockInit.GLOWING_CRESCENT_GRASS,
            BlockInit.PARASOL_GRASS,
            BlockInit.GLOWING_PARASOL_GRASS,
            BlockInit.LUNAR_REED,
            BlockInit.GLOWING_MUSHROOM,
            BlockInit.SWAMP_ROSE,
            BlockInit.POTTED_SWAMP_ROSE,
            BlockInit.FANTABUD,
            BlockInit.GREEN_FANTABUD,
            BlockInit.FANTAFERN,
            BlockInit.GREEN_FANTAFERN,
            BlockInit.FANTAGRASS,
            BlockInit.GREEN_FANTAGRASS,
            BlockInit.SWAMP_ROSE
    );

    public static final List<Supplier<Block>> translucentBlocks = List.of(
            BlockInit.STARLIGHT_PORTAL
    );

    public static final Map<ModelResourceLocation, ModelResourceLocation> itemModelsInInventoryMap = new HashMap<>();

    public static final Map<ResourceLocation, BakedModel> bakedModelsMap = new HashMap<>();

    public static boolean renderingFirstPersonPlayer = false;
    public interface PlayerAnimationStateGetter {
        PlayerAnimationState get(ItemStack itemStack, float tickCount);
    }
    public static final Map<Supplier<Item>, PlayerAnimationStateGetter> playerAnimatingItemMap = new HashMap<>();

    public static void clientSetup() {
        itemModelsInInventoryMap.put(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "thermal_springstone_hammer"), "inventory"), new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "thermal_springstone_hammer_inventory"), "inventory"));
        itemModelsInInventoryMap.put(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "prophet_orb"), "inventory"), new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "prophet_orb_inventory"), "inventory"));
        itemModelsInInventoryMap.put(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "bonemore_broadsword"), "inventory"), new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "bonemore_broadsword_inventory"), "inventory"));
        itemModelsInInventoryMap.put(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "doomeden_sword"), "inventory"), new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "doomeden_sword_inventory"), "inventory"));
        itemModelsInInventoryMap.put(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "moonring_greatsword"), "inventory"), new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "moonring_greatsword_inventory"), "inventory"));
        itemModelsInInventoryMap.put(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "petal_scythe"), "inventory"), new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "petal_scythe_inventory"), "inventory"));

        playerAnimatingItemMap.put(ItemInit.MOONRING_GREATSWORD, ((stack, tickCount) -> new PlayerAnimationState(PlayerAnimation.MOONRING_GREATSWORD_BLOCK, true, true, true, true, true)));
        playerAnimatingItemMap.put(ItemInit.PROPHET_ORB, ((stack, tickCount) -> new PlayerAnimationState(PlayerAnimation.PROPHET_ORB_LOCATE, true, true, true, true, true)));

        BlockEntityRenderers.register(BlockEntityInit.SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.HANGING_SIGN_BLOCK_ENTITY.get(), HangingSignRenderer::new);

        ItemProperties.register(ItemInit.CRYSTAL_CROSSBOW.get(), new ResourceLocation("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(stack);
            }
        });
        ItemProperties.register(ItemInit.CRYSTAL_CROSSBOW.get(), new ResourceLocation("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ItemInit.CRYSTAL_CROSSBOW.get(), new ResourceLocation("charged"), (stack, level, entity, i) -> entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ItemInit.CRYSTAL_CROSSBOW.get(), new ResourceLocation("firework"), (stack, level, entity, i) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

        ItemProperties.register(ItemInit.MOONRING_BOW.get(), new ResourceLocation("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemInit.MOONRING_BOW.get(), new ResourceLocation("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);

        ItemProperties.register(ItemInit.STARFALL_LONGBOW.get(), new ResourceLocation("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemInit.STARFALL_LONGBOW.get(), new ResourceLocation("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);

        ItemProperties.register(ItemInit.BLOOD_BOW.get(), new ResourceLocation("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemInit.BLOOD_BOW.get(), new ResourceLocation("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }

    public static void clientWoodSetup() {
        WoodType.register(ESWoodTypes.LUNAR);
        WoodType.register(ESWoodTypes.NORTHLAND);
        WoodType.register(ESWoodTypes.STARLIGHT_MANGROVE);
        registerWoodTypeForSign(ESWoodTypes.LUNAR);
        registerWoodTypeForSign(ESWoodTypes.NORTHLAND);
        registerWoodTypeForSign(ESWoodTypes.STARLIGHT_MANGROVE);
    }

    private static void registerWoodTypeForSign(WoodType woodType) {
        Sheets.SIGN_MATERIALS.put(woodType, Sheets.createSignMaterial(woodType));
    }

    public static void registerBlockColors(BlockColorRegisterStrategy strategy) {
        BlockColor leavesColor = (state, getter, pos, i) -> getter != null && pos != null ? BiomeColors.getAverageFoliageColor(getter, pos) : FoliageColor.getDefaultColor();
        strategy.register(leavesColor, BlockInit.STARLIGHT_MANGROVE_LEAVES.get());
    }

    public static void registerItemColors(ItemColorRegisterStrategy strategy) {
        ItemColor leavesItemColor = (stack, packedLight) -> {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, packedLight);
        };
        strategy.register(leavesItemColor, ItemInit.STARLIGHT_MANGROVE_LEAVES.get());
    }

    public static void modifyBakingResult(Map<ResourceLocation, BakedModel> models) {
        for (ResourceLocation id : models.keySet()) {
            if (id.toString().contains(EternalStarlight.MOD_ID + ":thermal_springstone_")) {
                models.put(id, ESPlatform.INSTANCE.getGlowingBakedModel(models.get(id)));
            }
            bakedModelsMap.put(id, models.get(id));
        }
    }

    public static void registerExtraBakedModels(Consumer<ResourceLocation> registration) {
        registration.accept(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "thermal_springstone_hammer_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "prophet_orb_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "bonemore_broadsword_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "doomeden_sword_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "moonring_greatsword_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(new ResourceLocation(EternalStarlight.MOD_ID, "petal_scythe_inventory"), "inventory"));    }

    public static void registerParticleProviders(ParticleProviderRegisterStrategy strategy) {
        strategy.register(ParticleInit.LIGHTNING.get(), LightningParticle.Provider::new);
        strategy.register(ParticleInit.STARLIGHT.get(), EndRodParticle.Provider::new);
        strategy.register(ParticleInit.POISON.get(), FlameParticle.Provider::new);
        strategy.register(ParticleInit.ENERGY.get(), FlameParticle.Provider::new);
    }

    public static void registerEntityRenderers(EntityRendererRegisterStrategy strategy) {
        strategy.register(EntityInit.FALLING_BLOCK.get(), ESFallingBlockRenderer::new);
        strategy.register(EntityInit.AETHERSENT_METEOR.get(), AetherSentMeteorRenderer::new);
        strategy.register(EntityInit.BOAT.get(), (context) -> new ESBoatRenderer(context, false));
        strategy.register(EntityInit.CHEST_BOAT.get(), (context) -> new ESBoatRenderer(context, true));
        strategy.register(EntityInit.CAMERA_SHAKE.get(), NothingRenderer::new);
        strategy.register(EntityInit.EYE_OF_SEEKING.get(), ThrownItemRenderer::new);
        strategy.register(EntityInit.BOARWARF.get(), BoarwarfRenderer::new);
        strategy.register(EntityInit.ASTRAL_GOLEM.get(), AstralGolemRenderer::new);
        strategy.register(EntityInit.LONESTAR_SKELETON.get(), LonestarSkeletonRenderer::new);
        strategy.register(EntityInit.NIGHTSHADE_SPIDER.get(), NightshadeSpiderRenderer::new);
        strategy.register(EntityInit.TWILIGHT_SQUID.get(), TwilightSquidRenderer::new);
        strategy.register(EntityInit.DRYAD.get(), DryadRenderer::new);
        strategy.register(EntityInit.THE_GATEKEEPER.get(), TheGatekeeperRenderer::new);
        strategy.register(EntityInit.STARLIGHT_GOLEM.get(), StarlightGolemRenderer::new);
        strategy.register(EntityInit.LUNAR_MONSTROSITY.get(), LunarMonstrosityRenderer::new);
        strategy.register(EntityInit.STARLIGHT_GOLEM_BEAM.get(), StarlightGolemBeamRenderer::new);
        strategy.register(EntityInit.FIRE_COLUMN.get(), NothingRenderer::new);
        strategy.register(EntityInit.SPORE.get(), SporeRenderer::new);
        strategy.register(EntityInit.VINE.get(), VineRenderer::new);
    }

    private static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0f);
    private static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5f);

    public static void registerLayers(RendererLayerRegisterStrategy strategy) {
        strategy.register(ThermalSpringStoneArmorModel.INNER_LOCATION, () -> ThermalSpringStoneArmorModel.createArmorLayer(INNER_ARMOR_DEFORMATION));
        strategy.register(ThermalSpringStoneArmorModel.OUTER_LOCATION, () -> ThermalSpringStoneArmorModel.createArmorLayer(OUTER_ARMOR_DEFORMATION));
        strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.LUNAR), BoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.LUNAR), ChestBoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.NORTHLAND), BoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.NORTHLAND), ChestBoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.STARLIGHT_MANGROVE), BoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.STARLIGHT_MANGROVE), ChestBoatModel::createBodyModel);
        strategy.register(BoarwarfModel.LAYER_LOCATION, BoarwarfModel::createBodyLayer);
        strategy.register(AstralGolemModel.LAYER_LOCATION, AstralGolemModel::createBodyLayer);
        strategy.register(AstralGolemModel.INNER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(AstralGolemModel.OUTER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(LonestarSkeletonRenderer.LONESTAR, SkeletonModel::createBodyLayer);
        strategy.register(LonestarSkeletonRenderer.LONESTAR_INNER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(LonestarSkeletonRenderer.LONESTAR_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(NightshadeSpiderRenderer.NIGHTSHADE_SPIDER, SpiderModel::createSpiderBodyLayer);
        strategy.register(TwilightSquidModel.LAYER_LOCATION, TwilightSquidModel::createBodyLayer);
        strategy.register(DryadModel.LAYER_LOCATION, DryadModel::createBodyLayer);
        strategy.register(TheGatekeeperModel.LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(false));
        strategy.register(TheGatekeeperModel.SLIM_LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(true));
        strategy.register(TheGatekeeperModel.INNER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(TheGatekeeperModel.OUTER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(StarlightGolemModel.LAYER_LOCATION, StarlightGolemModel::createBodyLayer);
        strategy.register(LunarMonstrosityModel.LAYER_LOCATION, LunarMonstrosityModel::createBodyLayer);
        strategy.register(SporeModel.LAYER_LOCATION, SporeModel::createBodyLayer);
        strategy.register(VineModel.LAYER_LOCATION, VineModel::createBodyLayer);
    }
}
