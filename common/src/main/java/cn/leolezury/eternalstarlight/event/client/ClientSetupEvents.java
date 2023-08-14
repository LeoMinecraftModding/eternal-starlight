package cn.leolezury.eternalstarlight.event.client;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.block.entity.SLWoodTypes;
import cn.leolezury.eternalstarlight.client.SLDimensionSpecialEffects;
import cn.leolezury.eternalstarlight.client.model.*;
import cn.leolezury.eternalstarlight.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.client.model.item.GlowingBakedModel;
import cn.leolezury.eternalstarlight.client.particle.lightning.LightningParticle;
import cn.leolezury.eternalstarlight.client.renderer.*;
import cn.leolezury.eternalstarlight.client.renderer.world.SLSkyRenderer;
import cn.leolezury.eternalstarlight.entity.misc.SLBoat;
import cn.leolezury.eternalstarlight.init.*;
import cn.leolezury.eternalstarlight.item.weapon.CrystalCrossbowItem;
import cn.leolezury.eternalstarlight.message.OpenSLBookMessage;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EternalStarlight.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    private static int registered;
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EternalStarlight.NETWORK_WRAPPER.registerMessage(registered++, OpenSLBookMessage.class, OpenSLBookMessage::write, OpenSLBookMessage::read, OpenSLBookMessage.Handler::handle);

        BlockEntityRenderers.register(BlockEntityInit.SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
        BlockEntityRenderers.register(BlockEntityInit.HANGING_SIGN_BLOCK_ENTITY.get(), HangingSignRenderer::new);

        ItemProperties.register(ItemInit.CRYSTAL_CROSSBOW.get(), new ResourceLocation("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return CrystalCrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float)CrystalCrossbowItem.getChargeDuration(stack);
            }
        });
        ItemProperties.register(ItemInit.CRYSTAL_CROSSBOW.get(), new ResourceLocation("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrystalCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ItemInit.CRYSTAL_CROSSBOW.get(), new ResourceLocation("charged"), (stack, level, entity, i) -> entity != null && CrystalCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ItemInit.CRYSTAL_CROSSBOW.get(), new ResourceLocation("firework"), (stack, level, entity, i) -> entity != null && CrystalCrossbowItem.isCharged(stack) && CrystalCrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

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
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void clientWoodSetup(FMLClientSetupEvent event) {
        WoodType.register(SLWoodTypes.LUNAR);
        WoodType.register(SLWoodTypes.NORTHLAND);
        WoodType.register(SLWoodTypes.STARLIGHT_MANGROVE);
        event.enqueueWork(() -> {
            Sheets.addWoodType(SLWoodTypes.LUNAR);
            Sheets.addWoodType(SLWoodTypes.NORTHLAND);
            Sheets.addWoodType(SLWoodTypes.STARLIGHT_MANGROVE);
        });
    }

    @SubscribeEvent
    public static void onRegisterDimEffects(RegisterDimensionSpecialEffectsEvent event) {
        SLSkyRenderer.createStars();
        event.register(new ResourceLocation(EternalStarlight.MOD_ID, "special_effect"), new SLDimensionSpecialEffects(160.0F, false, DimensionSpecialEffects.SkyType.NONE, false, false));
    }

    @SubscribeEvent
    public static void onRegisterBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register((state, getter, pos, i) -> getter != null && pos != null ? BiomeColors.getAverageFoliageColor(getter, pos) : FoliageColor.getDefaultColor(), BlockInit.STARLIGHT_MANGROVE_LEAVES.get());
    }

    @SubscribeEvent
    public static void onRegisterItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((p_92687_, p_92688_) -> {
            BlockState blockstate = ((BlockItem)p_92687_.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, p_92688_);
        }, BlockInit.STARLIGHT_MANGROVE_LEAVES.get());
    }

    @SubscribeEvent
    public static void onBakingCompleted(ModelEvent.ModifyBakingResult event) {
        for (ResourceLocation id : event.getModels().keySet()) {
            if (id.toString().contains(EternalStarlight.MOD_ID + ":thermal_springstone_")) {
                event.getModels().put(id, new GlowingBakedModel(event.getModels().get(id)));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleInit.LIGHTNING.get(), LightningParticle.Provider::new);
        event.registerSpriteSet(ParticleInit.STARLIGHT.get(), EndRodParticle.Provider::new);
        event.registerSpriteSet(ParticleInit.POISON.get(), FlameParticle.Provider::new);
        event.registerSpriteSet(ParticleInit.ENERGY.get(), FlameParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.FALLING_BLOCK.get(), SLFallingBlockRenderer::new);
        event.registerEntityRenderer(EntityInit.AETHERSENT_METEOR.get(), AethersentMeteorRenderer::new);
        event.registerEntityRenderer(EntityInit.BOAT.get(), (context) -> new SLBoatRenderer(context, false));
        event.registerEntityRenderer(EntityInit.CHEST_BOAT.get(), (context) -> new SLBoatRenderer(context, true));
        event.registerEntityRenderer(EntityInit.CAMERA_SHAKE.get(), NothingRenderer::new);
        event.registerEntityRenderer(EntityInit.EYE_OF_SEEKING.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityInit.BOARWARF.get(), BoarwarfRenderer::new);
        event.registerEntityRenderer(EntityInit.ASTRAL_GOLEM.get(), AstralGolemRenderer::new);
        event.registerEntityRenderer(EntityInit.LONESTAR_SKELETON.get(), LonestarSkeletonRenderer::new);
        event.registerEntityRenderer(EntityInit.NIGHTSHADE_SPIDER.get(), NightshadeSpiderRenderer::new);
        event.registerEntityRenderer(EntityInit.TWILIGHT_SQUID.get(), TwilightSquidRenderer::new);
        event.registerEntityRenderer(EntityInit.DRYAD.get(), DryadRenderer::new);
        event.registerEntityRenderer(EntityInit.THE_GATEKEEPER.get(), TheGatekeeperRenderer::new);
        event.registerEntityRenderer(EntityInit.STARLIGHT_GOLEM.get(), StarlightGolemRenderer::new);
        event.registerEntityRenderer(EntityInit.LUNAR_MONSTROSITY.get(), LunarMonstrosityRenderer::new);
        event.registerEntityRenderer(EntityInit.STARLIGHT_GOLEM_BEAM.get(), StarlightGolemBeamRenderer::new);
        event.registerEntityRenderer(EntityInit.FIRE_COLUMN.get(), NothingRenderer::new);
        event.registerEntityRenderer(EntityInit.SPORE.get(), SporeRenderer::new);
        event.registerEntityRenderer(EntityInit.VINE.get(), VineRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ThermalSpringStoneArmorModel.INNER_LOCATION, () -> ThermalSpringStoneArmorModel.createArmorLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION));
        event.registerLayerDefinition(ThermalSpringStoneArmorModel.OUTER_LOCATION, () -> ThermalSpringStoneArmorModel.createArmorLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION));
        event.registerLayerDefinition(SLBoatRenderer.createBoatModelName(SLBoat.Type.LUNAR), BoatModel::createBodyModel);
        event.registerLayerDefinition(SLBoatRenderer.createChestBoatModelName(SLBoat.Type.LUNAR), ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(SLBoatRenderer.createBoatModelName(SLBoat.Type.NORTHLAND), BoatModel::createBodyModel);
        event.registerLayerDefinition(SLBoatRenderer.createChestBoatModelName(SLBoat.Type.NORTHLAND), ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(SLBoatRenderer.createBoatModelName(SLBoat.Type.STARLIGHT_MANGROVE), BoatModel::createBodyModel);
        event.registerLayerDefinition(SLBoatRenderer.createChestBoatModelName(SLBoat.Type.STARLIGHT_MANGROVE), ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(BoarwarfModel.LAYER_LOCATION, BoarwarfModel::createBodyLayer);
        event.registerLayerDefinition(AstralGolemModel.LAYER_LOCATION, AstralGolemModel::createBodyLayer);
        event.registerLayerDefinition(AstralGolemModel.INNER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(AstralGolemModel.OUTER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(LonestarSkeletonRenderer.LONESTAR, SkeletonModel::createBodyLayer);
        event.registerLayerDefinition(LonestarSkeletonRenderer.LONESTAR_INNER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(LonestarSkeletonRenderer.LONESTAR_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(NightshadeSpiderRenderer.NIGHTSHADE_SPIDER, SpiderModel::createSpiderBodyLayer);
        event.registerLayerDefinition(TwilightSquidModel.LAYER_LOCATION, TwilightSquidModel::createBodyLayer);
        event.registerLayerDefinition(DryadModel.LAYER_LOCATION, DryadModel::createBodyLayer);
        event.registerLayerDefinition(TheGatekeeperModel.LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(false));
        event.registerLayerDefinition(TheGatekeeperModel.SLIM_LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(true));
        event.registerLayerDefinition(TheGatekeeperModel.INNER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(TheGatekeeperModel.OUTER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32));
        event.registerLayerDefinition(StarlightGolemModel.LAYER_LOCATION, StarlightGolemModel::createBodyLayer);
        event.registerLayerDefinition(LunarMonstrosityModel.LAYER_LOCATION, LunarMonstrosityModel::createBodyLayer);
        event.registerLayerDefinition(SporeModel.LAYER_LOCATION, SporeModel::createBodyLayer);
        event.registerLayerDefinition(VineModel.LAYER_LOCATION, VineModel::createBodyLayer);
    }
}
