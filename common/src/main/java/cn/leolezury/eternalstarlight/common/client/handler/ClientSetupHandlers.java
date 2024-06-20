package cn.leolezury.eternalstarlight.common.client.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESParticleRenderType;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.PlayerAnimation;
import cn.leolezury.eternalstarlight.common.client.model.armor.AlchemistArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.*;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession.*;
import cn.leolezury.eternalstarlight.common.client.model.item.GlaciteShieldModel;
import cn.leolezury.eternalstarlight.common.client.particle.effect.*;
import cn.leolezury.eternalstarlight.common.client.particle.environment.FireflyParticle;
import cn.leolezury.eternalstarlight.common.client.particle.environment.ScarletLeavesParticle;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.ESPortalRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.*;
import cn.leolezury.eternalstarlight.common.client.shader.ESShaders;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import cn.leolezury.eternalstarlight.common.item.magic.OrbOfProphecyItem;
import cn.leolezury.eternalstarlight.common.item.weapon.ShatteredSwordItem;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ClientSetupHandlers {
    public interface BlockColorRegisterStrategy {
        void register(BlockColor blockColor, Block... blocks);
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

    public interface ShaderRegisterStrategy {
        void register(ResourceLocation location, VertexFormat format, Consumer<ShaderInstance> loaded);
    }

    public static final List<Supplier<? extends Block>> BLOCKS_CUTOUT_MIPPED = List.of(
            ESBlocks.NIGHTFALL_GRASS_BLOCK,
            ESBlocks.LUNAR_LEAVES,
            ESBlocks.NORTHLAND_LEAVES,
            ESBlocks.STARLIGHT_MANGROVE_LEAVES,
            ESBlocks.STARLIGHT_MANGROVE_ROOTS,
            ESBlocks.MUDDY_STARLIGHT_MANGROVE_ROOTS,
            ESBlocks.SCARLET_LEAVES,
            ESBlocks.SCARLET_LEAVES_PILE,
            ESBlocks.TORREYA_LEAVES
    );

    public static final List<Supplier<? extends Block>> BLOCKS_CUTOUT = List.of(
            ESBlocks.BERRIES_VINES,
            ESBlocks.BERRIES_VINES_PLANT,
            ESBlocks.CAVE_MOSS,
            ESBlocks.CAVE_MOSS_PLANT,
            ESBlocks.CAVE_MOSS_VEIN,
            ESBlocks.ABYSSAL_KELP,
            ESBlocks.ABYSSAL_KELP_PLANT,
            ESBlocks.ORBFLORA,
            ESBlocks.ORBFLORA_PLANT,
            ESBlocks.JINGLING_PICKLE,
            ESBlocks.DEAD_TENTACLES_CORAL,
            ESBlocks.TENTACLES_CORAL,
            ESBlocks.DEAD_TENTACLES_CORAL_FAN,
            ESBlocks.TENTACLES_CORAL_FAN,
            ESBlocks.DEAD_TENTACLES_CORAL_WALL_FAN,
            ESBlocks.TENTACLES_CORAL_WALL_FAN,
            ESBlocks.DEAD_GOLDEN_CORAL,
            ESBlocks.GOLDEN_CORAL,
            ESBlocks.DEAD_GOLDEN_CORAL_FAN,
            ESBlocks.GOLDEN_CORAL_FAN,
            ESBlocks.DEAD_GOLDEN_CORAL_WALL_FAN,
            ESBlocks.GOLDEN_CORAL_WALL_FAN,
            ESBlocks.DEAD_CRYSTALLUM_CORAL,
            ESBlocks.CRYSTALLUM_CORAL,
            ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN,
            ESBlocks.CRYSTALLUM_CORAL_FAN,
            ESBlocks.DEAD_CRYSTALLUM_CORAL_WALL_FAN,
            ESBlocks.CRYSTALLUM_CORAL_WALL_FAN,
            ESBlocks.LUNAR_SAPLING,
            ESBlocks.POTTED_LUNAR_SAPLING,
            ESBlocks.LUNAR_TRAPDOOR,
            ESBlocks.LUNAR_DOOR,
            ESBlocks.NORTHLAND_SAPLING,
            ESBlocks.POTTED_NORTHLAND_SAPLING,
            ESBlocks.NORTHLAND_TRAPDOOR,
            ESBlocks.NORTHLAND_DOOR,
            ESBlocks.STARLIGHT_MANGROVE_SAPLING,
            ESBlocks.POTTED_STARLIGHT_MANGROVE_SAPLING,
            ESBlocks.STARLIGHT_MANGROVE_TRAPDOOR,
            ESBlocks.STARLIGHT_MANGROVE_DOOR,
            ESBlocks.SCARLET_SAPLING,
            ESBlocks.POTTED_SCARLET_SAPLING,
            ESBlocks.SCARLET_TRAPDOOR,
            ESBlocks.SCARLET_DOOR,
            ESBlocks.TORREYA_SAPLING,
            ESBlocks.POTTED_TORREYA_SAPLING,
            ESBlocks.TORREYA_TRAPDOOR,
            ESBlocks.TORREYA_DOOR,
            ESBlocks.TORREYA_VINES,
            ESBlocks.TORREYA_VINES_PLANT,
            ESBlocks.TORREYA_CAMPFIRE,
            ESBlocks.ABYSSAL_FIRE,
            ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER,
            ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER,
            ESBlocks.THIOQUARTZ_CLUSTER,
            ESBlocks.DOOMED_TORCH,
            ESBlocks.WALL_DOOMED_TORCH,
            ESBlocks.DOOMED_REDSTONE_TORCH,
            ESBlocks.WALL_DOOMED_REDSTONE_TORCH,
            ESBlocks.STARLIGHT_FLOWER,
            ESBlocks.POTTED_STARLIGHT_FLOWER,
            ESBlocks.CONEBLOOM,
            ESBlocks.POTTED_CONEBLOOM,
            ESBlocks.NIGHTFAN,
            ESBlocks.POTTED_NIGHTFAN,
            ESBlocks.PINK_ROSE,
            ESBlocks.POTTED_PINK_ROSE,
            ESBlocks.STARLIGHT_TORCHFLOWER,
            ESBlocks.POTTED_STARLIGHT_TORCHFLOWER,
            ESBlocks.NIGHTFAN_BUSH,
            ESBlocks.PINK_ROSE_BUSH,
            ESBlocks.NIGHT_SPROUTS,
            ESBlocks.SMALL_NIGHT_SPROUTS,
            ESBlocks.GLOWING_NIGHT_SPROUTS,
            ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS,
            ESBlocks.LUNAR_GRASS,
            ESBlocks.GLOWING_LUNAR_GRASS,
            ESBlocks.CRESCENT_GRASS,
            ESBlocks.GLOWING_CRESCENT_GRASS,
            ESBlocks.PARASOL_GRASS,
            ESBlocks.GLOWING_PARASOL_GRASS,
            ESBlocks.LUNAR_BUSH,
            ESBlocks.GLOWING_LUNAR_BUSH,
            ESBlocks.TALL_CRESCENT_GRASS,
            ESBlocks.TALL_GLOWING_CRESCENT_GRASS,
            ESBlocks.LUNAR_REED,
            ESBlocks.WHISPERBLOOM,
            ESBlocks.GLADESPIKE,
            ESBlocks.VIVIDSTALK,
            ESBlocks.TALL_GLADESPIKE,
            ESBlocks.GLOWING_MUSHROOM,
            ESBlocks.SWAMP_ROSE,
            ESBlocks.POTTED_SWAMP_ROSE,
            ESBlocks.FANTABUD,
            ESBlocks.GREEN_FANTABUD,
            ESBlocks.FANTAFERN,
            ESBlocks.GREEN_FANTAFERN,
            ESBlocks.FANTAGRASS,
            ESBlocks.GREEN_FANTAGRASS,
            ESBlocks.ORANGE_SCARLET_BUD,
            ESBlocks.PURPLE_SCARLET_BUD,
            ESBlocks.RED_SCARLET_BUD,
            ESBlocks.SCARLET_GRASS,
            ESBlocks.WITHERED_STARLIGHT_FLOWER,
            ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER,
            ESBlocks.DEAD_LUNAR_BUSH,
            ESBlocks.POTTED_DEAD_LUNAR_BUSH,
            ESBlocks.DESERT_AMETHYSIA,
            ESBlocks.POTTED_DESERT_AMETHYSIA,
            ESBlocks.WITHERED_DESERT_AMETHYSIA,
            ESBlocks.POTTED_WITHERED_DESERT_AMETHYSIA,
            ESBlocks.SUNSET_THORNBLOOM,
            ESBlocks.POTTED_SUNSET_THORNBLOOM,
            ESBlocks.LUNARIS_CACTUS,
            ESBlocks.MOONLIGHT_LILY_PAD,
            ESBlocks.STARLIT_LILY_PAD,
            ESBlocks.MOONLIGHT_DUCKWEED,
            ESBlocks.CRYSTALLIZED_LUNAR_GRASS,
            ESBlocks.RED_CRYSTAL_ROOTS,
            ESBlocks.BLUE_CRYSTAL_ROOTS,
            ESBlocks.TWILVEWRYM_HERB,
            ESBlocks.STELLAFLY_BUSH,
            ESBlocks.GLIMMERFLY_BUSH,
            ESBlocks.SWAMP_ROSE,
            ESBlocks.AMARAMBER_LANTERN,
            ESBlocks.THE_GATEKEEPER_SPAWNER,
            ESBlocks.STARLIGHT_GOLEM_SPAWNER,
            ESBlocks.LUNAR_MONSTROSITY_SPAWNER
    );

    public static final List<Supplier<? extends Block>> BLOCKS_TRANSLUCENT = List.of(
            ESBlocks.LUNARIS_CACTUS_GEL_BLOCK,
            ESBlocks.STARLIGHT_PORTAL
    );

    public static final List<ResourceLocation> TRIMS = List.of(
            EternalStarlight.id("trims/models/armor/keeper"),
            EternalStarlight.id("trims/models/armor/forge")
    );

    public static final Map<ModelResourceLocation, ModelResourceLocation> ITEMS_WITH_INV_ICON = new HashMap<>();

    public static ModelResourceLocation getInvIcon(ModelResourceLocation item) {
        return ITEMS_WITH_INV_ICON.containsKey(item) ? new ModelResourceLocation(ITEMS_WITH_INV_ICON.get(item).id().withPrefix("item/"), ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.FABRIC ? "fabric_resource" : "standalone") : item;
    }

    public static final Map<ModelResourceLocation, BakedModel> BAKED_MODELS = new HashMap<>();

    public static void clientSetup() {
        // hacky
        List<ParticleRenderType> renderTypes = new ArrayList<>(ParticleEngine.RENDER_ORDER);
        int translucent = renderTypes.indexOf(ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT);
        if (translucent != -1) {
            renderTypes.add(translucent + 1, ESParticleRenderType.PARTICLE_SHEET_TRANSLUCENT_BLEND);
        }
        ParticleEngine.RENDER_ORDER = renderTypes;

        ITEMS_WITH_INV_ICON.put(new ModelResourceLocation(EternalStarlight.id("thermal_springstone_hammer"), "inventory"), new ModelResourceLocation(EternalStarlight.id("thermal_springstone_hammer_inventory"), "inventory"));
        ITEMS_WITH_INV_ICON.put(new ModelResourceLocation(EternalStarlight.id("glacite_scythe"), "inventory"), new ModelResourceLocation(EternalStarlight.id("glacite_scythe_inventory"), "inventory"));
        ITEMS_WITH_INV_ICON.put(new ModelResourceLocation(EternalStarlight.id("orb_of_prophecy"), "inventory"), new ModelResourceLocation(EternalStarlight.id("orb_of_prophecy_inventory"), "inventory"));
        ITEMS_WITH_INV_ICON.put(new ModelResourceLocation(EternalStarlight.id("orb_of_prophecy_with_crests"), "inventory"), new ModelResourceLocation(EternalStarlight.id("orb_of_prophecy_with_crests_inventory"), "inventory"));
        ITEMS_WITH_INV_ICON.put(new ModelResourceLocation(EternalStarlight.id("bonemore_broadsword"), "inventory"), new ModelResourceLocation(EternalStarlight.id("bonemore_broadsword_inventory"), "inventory"));
        ITEMS_WITH_INV_ICON.put(new ModelResourceLocation(EternalStarlight.id("doomeden_sword"), "inventory"), new ModelResourceLocation(EternalStarlight.id("doomeden_sword_inventory"), "inventory"));
        ITEMS_WITH_INV_ICON.put(new ModelResourceLocation(EternalStarlight.id("moonring_greatsword"), "inventory"), new ModelResourceLocation(EternalStarlight.id("moonring_greatsword_inventory"), "inventory"));
        ITEMS_WITH_INV_ICON.put(new ModelResourceLocation(EternalStarlight.id("petal_scythe"), "inventory"), new ModelResourceLocation(EternalStarlight.id("petal_scythe_inventory"), "inventory"));

        PlayerAnimator.register(new PlayerAnimator.UseItemAnimationTrigger(ESItems.ENERGY_SWORD), ((player) -> new PlayerAnimator.PlayerAnimationState(PlayerAnimation.MOONRING_GREATSWORD_BLOCK, PlayerAnimation.FIRST_PERSON_GATHER_HANDS, List.of(new PlayerAnimator.UseItemHandAnimationTransformer(), new PlayerAnimator.CopyOuterLayerAnimationTransformer()), true, true, true, true)));
        PlayerAnimator.register(new PlayerAnimator.UseItemAnimationTrigger(ESItems.MOONRING_GREATSWORD), ((player) -> new PlayerAnimator.PlayerAnimationState(PlayerAnimation.MOONRING_GREATSWORD_BLOCK, PlayerAnimation.FIRST_PERSON_GATHER_HANDS, List.of(new PlayerAnimator.UseItemHandAnimationTransformer(), new PlayerAnimator.CopyOuterLayerAnimationTransformer()), true, true, true, true)));
        PlayerAnimator.register(new PlayerAnimator.UseItemAnimationTrigger(ESItems.ORB_OF_PROPHECY), ((player) -> new PlayerAnimator.PlayerAnimationState(PlayerAnimation.ORB_OF_PROPHECY_USE, PlayerAnimation.FIRST_PERSON_ORB_OF_PROPHECY_USE, List.of(new PlayerAnimator.UseItemHandAnimationTransformer(), new PlayerAnimator.CopyOuterLayerAnimationTransformer()), true, true, true, true)));

        BlockEntityRenderers.register(ESBlockEntities.SIGN.get(), SignRenderer::new);
        BlockEntityRenderers.register(ESBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
        BlockEntityRenderers.register(ESBlockEntities.CAMPFIRE.get(), CampfireRenderer::new);
        BlockEntityRenderers.register(ESBlockEntities.STARLIGHT_PORTAL.get(), ESPortalRenderer::new);

        ItemProperties.register(ESItems.SHATTERED_SWORD.get(), ResourceLocation.withDefaultNamespace("no_blade"), (stack, level, entity, i) -> ShatteredSwordItem.hasBlade(stack) ? 0.0F : 1.0F);

        ItemProperties.register(ESItems.CRYSTAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(stack, entity);
            }
        });
        ItemProperties.register(ESItems.CRYSTAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ESItems.CRYSTAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("charged"), (stack, level, entity, i) -> entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ESItems.CRYSTAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (stack, level, entity, i) -> {
            ChargedProjectiles chargedProjectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
            return chargedProjectiles != null && chargedProjectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });

        ItemProperties.register(ESItems.MECHANICAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(stack, entity);
            }
        });
        ItemProperties.register(ESItems.MECHANICAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ESItems.MECHANICAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("charged"), (stack, level, entity, i) -> entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ESItems.MECHANICAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (stack, level, entity, i) -> {
            ChargedProjectiles chargedProjectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
            return chargedProjectiles != null && chargedProjectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });

        ItemProperties.register(ESItems.MOONRING_BOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ESItems.MOONRING_BOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);

        ItemProperties.register(ESItems.STARFALL_LONGBOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ESItems.STARFALL_LONGBOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);

        ItemProperties.register(ESItems.BOW_OF_BLOOD.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ESItems.BOW_OF_BLOOD.get(), ResourceLocation.withDefaultNamespace("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);

        ItemProperties.register(ESItems.GLACITE_SHIELD.get(), ResourceLocation.withDefaultNamespace("blocking"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

        ItemProperties.register(ESItems.ORB_OF_PROPHECY.get(), ResourceLocation.withDefaultNamespace("crests_mode"), (stack, level, entity, i) -> OrbOfProphecyItem.hasCrests(level == null ? null : level.registryAccess(), stack) ? (OrbOfProphecyItem.isTemporary(stack) ? 0.5F : 1.0F) : 0.0F);

        ItemProperties.register(ESItems.DAGGER_OF_HUNGER.get(), ResourceLocation.withDefaultNamespace("hunger_state"), (stack, level, entity, i) -> Math.min(2f, (stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0f) + 1f) * 1.5f) / 2f);
    }

    public static void registerBlockColors(BlockColorRegisterStrategy strategy) {
        BlockColor leavesColor = (state, getter, pos, i) -> getter != null && pos != null ? BiomeColors.getAverageFoliageColor(getter, pos) : FoliageColor.getDefaultColor();
        BlockColor grassColor = (state, getter, pos, i) -> getter != null && pos != null ? BiomeColors.getAverageGrassColor(getter, pos) : GrassColor.getDefaultColor();
        BlockColor dyeColor = (state, getter, pos, i) -> state.getBlock().defaultMapColor().col;
        strategy.register(grassColor, ESBlocks.NIGHTFALL_GRASS_BLOCK.get());
        strategy.register(grassColor, ESBlocks.CAVE_MOSS.get());
        strategy.register(grassColor, ESBlocks.CAVE_MOSS_PLANT.get());
        strategy.register(grassColor, ESBlocks.CAVE_MOSS_VEIN.get());
        strategy.register(grassColor, ESBlocks.MOONLIGHT_LILY_PAD.get());
        strategy.register(grassColor, ESBlocks.STARLIT_LILY_PAD.get());
        strategy.register(grassColor, ESBlocks.MOONLIGHT_DUCKWEED.get());
        strategy.register(leavesColor, ESBlocks.STARLIGHT_MANGROVE_LEAVES.get());
        strategy.register(dyeColor, ESBlocks.WHITE_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.ORANGE_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.MAGENTA_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.LIGHT_BLUE_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.YELLOW_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.LIME_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.PINK_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.GRAY_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.LIGHT_GRAY_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.CYAN_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.PURPLE_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.BLUE_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.BROWN_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.GREEN_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.RED_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.BLACK_YETI_FUR.get());
        strategy.register(dyeColor, ESBlocks.WHITE_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.ORANGE_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.MAGENTA_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.YELLOW_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.LIME_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.PINK_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.GRAY_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.CYAN_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.PURPLE_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.BLUE_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.BROWN_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.GREEN_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.RED_YETI_FUR_CARPET.get());
        strategy.register(dyeColor, ESBlocks.BLACK_YETI_FUR_CARPET.get());
    }

    public static void registerItemColors(ItemColorRegisterStrategy strategy) {
        ItemColor toBlock = (stack, packedLight) -> {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, packedLight);
        };
        strategy.register(toBlock, ESBlocks.NIGHTFALL_GRASS_BLOCK.get());
        strategy.register(toBlock, ESBlocks.CAVE_MOSS.get());
        strategy.register(toBlock, ESBlocks.CAVE_MOSS_PLANT.get());
        strategy.register(toBlock, ESBlocks.CAVE_MOSS_VEIN.get());
        strategy.register(toBlock, ESBlocks.MOONLIGHT_LILY_PAD.get());
        strategy.register(toBlock, ESBlocks.STARLIT_LILY_PAD.get());
        strategy.register(toBlock, ESBlocks.MOONLIGHT_DUCKWEED.get());
        strategy.register(toBlock, ESBlocks.STARLIGHT_MANGROVE_LEAVES.get());
        strategy.register(toBlock, ESBlocks.WHITE_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.ORANGE_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.MAGENTA_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.LIGHT_BLUE_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.YELLOW_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.LIME_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.PINK_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.GRAY_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.LIGHT_GRAY_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.CYAN_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.PURPLE_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.BLUE_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.BROWN_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.GREEN_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.RED_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.BLACK_YETI_FUR.get());
        strategy.register(toBlock, ESBlocks.WHITE_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.ORANGE_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.MAGENTA_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.YELLOW_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.LIME_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.PINK_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.GRAY_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.CYAN_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.PURPLE_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.BLUE_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.BROWN_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.GREEN_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.RED_YETI_FUR_CARPET.get());
        strategy.register(toBlock, ESBlocks.BLACK_YETI_FUR_CARPET.get());
    }

    public static void registerShaders(ShaderRegisterStrategy strategy) {
        strategy.register(EternalStarlight.id("crest_selection_gui"), DefaultVertexFormat.POSITION_TEX, ESShaders::setCrestSelectionGui);
        strategy.register(EternalStarlight.id("meteor_rain"), DefaultVertexFormat.PARTICLE, ESShaders::setMeteorRain);
        strategy.register(EternalStarlight.id("rendertype_laser_beam"), DefaultVertexFormat.NEW_ENTITY, ESShaders::setRenderTypeLaserBeam);
        strategy.register(EternalStarlight.id("rendertype_starlight_portal"), DefaultVertexFormat.BLOCK, ESShaders::setRenderTypeStarlightPortal);
    }

    public static void modifyBakingResult(Map<ModelResourceLocation, BakedModel> models) {
        for (ModelResourceLocation id : models.keySet()) {
            if (id.id().toString().contains(EternalStarlight.ID + ":thermal_springstone_")) {
                models.put(id, ESPlatform.INSTANCE.getGlowingBakedModel(models.get(id)));
            }
            BAKED_MODELS.put(id, models.get(id));
        }
    }

    public static void registerExtraBakedModels(Consumer<ModelResourceLocation> registration) {
        registration.accept(new ModelResourceLocation(EternalStarlight.id("thermal_springstone_hammer_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(EternalStarlight.id("glacite_scythe_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(EternalStarlight.id("orb_of_prophecy_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(EternalStarlight.id("bonemore_broadsword_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(EternalStarlight.id("doomeden_sword_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(EternalStarlight.id("moonring_greatsword_inventory"), "inventory"));
        registration.accept(new ModelResourceLocation(EternalStarlight.id("petal_scythe_inventory"), "inventory"));
    }

    public static void registerParticleProviders(ParticleProviderRegisterStrategy strategy) {
        strategy.register(ESParticles.STARLIGHT.get(), EndRodParticle.Provider::new);
        strategy.register(ESParticles.FIREFLY.get(), FireflyParticle.Provider::new);
        strategy.register(ESParticles.SCARLET_LEAVES.get(), ScarletLeavesParticle.Provider::new);
        strategy.register(ESParticles.ENERGY.get(), FlameParticle.Provider::new);
        strategy.register(ESParticles.LIGHTNING.get(), LightningParticle.Provider::new);
        strategy.register(ESParticles.BLADE_SHOCKWAVE.get(), ShockwaveParticle.Provider::new);
        strategy.register(ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), SonarParticle.Provider::new);
        strategy.register(ESParticles.AMARAMBER_FLAME.get(), FlameParticle.Provider::new);
        strategy.register(ESParticles.EXPLOSION.get(), ESExplosionParticle.Provider::new);
        strategy.register(ESParticles.SMOKE.get(), ESSmokeParticle.Provider::new);
        strategy.register(ESParticles.RING_EXPLOSION.get(), RingExplosionParticle.Provider::new);
        strategy.register(ESParticles.AETHERSENT_SMOKE.get(), AethersentSmokeParticle.Provider::new);
        strategy.register(ESParticles.SMOKE_TRAIL.get(), SmokeTrailParticle.Provider::new);
        strategy.register(ESParticles.AETHERSENT_EXPLOSION.get(), AethersentExplosionParticle.Provider::new);
    }

    public static void registerEntityRenderers(EntityRendererRegisterStrategy strategy) {
        strategy.register(ESEntities.FALLING_BLOCK.get(), ESFallingBlockRenderer::new);
        strategy.register(ESEntities.AETHERSENT_METEOR.get(), AethersentMeteorRenderer::new);
        strategy.register(ESEntities.BOAT.get(), (context) -> new ESBoatRenderer(context, false));
        strategy.register(ESEntities.CHEST_BOAT.get(), (context) -> new ESBoatRenderer(context, true));
        strategy.register(ESEntities.CAMERA_SHAKE.get(), EmptyRenderer::new);
        strategy.register(ESEntities.EYE_OF_SEEKING.get(), ThrownItemRenderer::new);
        strategy.register(ESEntities.BOARWARF.get(), BoarwarfRenderer::new);
        strategy.register(ESEntities.ASTRAL_GOLEM.get(), AstralGolemRenderer::new);
        strategy.register(ESEntities.GLEECH.get(), GleechRenderer::new);
        strategy.register(ESEntities.GLEECH_EGG.get(), ThrownItemRenderer::new);
        strategy.register(ESEntities.LONESTAR_SKELETON.get(), LonestarSkeletonRenderer::new);
        strategy.register(ESEntities.NIGHTFALL_SPIDER.get(), NightfallSpiderRenderer::new);
        strategy.register(ESEntities.THIRST_WALKER.get(), ThirstWalkerRenderer::new);
        strategy.register(ESEntities.ENT.get(), EntRenderer::new);
        strategy.register(ESEntities.RATLIN.get(), RatlinRenderer::new);
        strategy.register(ESEntities.YETI.get(), YetiRenderer::new);
        strategy.register(ESEntities.AURORA_DEER.get(), AuroraDeerRenderer::new);
        strategy.register(ESEntities.CRYSTALLIZED_MOTH.get(), CrystallizedMothRenderer::new);
        strategy.register(ESEntities.SHIMMER_LACEWING.get(), ShimmerLacewingRenderer::new);
        strategy.register(ESEntities.GRIMSTONE_GOLEM.get(), GrimstoneGolemRenderer::new);
        strategy.register(ESEntities.LUMINOFISH.get(), LuminoFishRenderer::new);
        strategy.register(ESEntities.LUMINARIS.get(), LuminarisRenderer::new);
        strategy.register(ESEntities.TWILIGHT_GAZE.get(), TwilightGazeRenderer::new);
        strategy.register(ESEntities.THE_GATEKEEPER.get(), TheGatekeeperRenderer::new);
        strategy.register(ESEntities.GATEKEEPER_FIREBALL.get(), GatekeeperFireballRenderer::new);
        strategy.register(ESEntities.STARLIGHT_GOLEM.get(), StarlightGolemRenderer::new);
        strategy.register(ESEntities.GOLEM_LASER_BEAM.get(), StarlightGolemBeamRenderer::new);
        strategy.register(ESEntities.ENERGIZED_FLAME.get(), EmptyRenderer::new);
        strategy.register(ESEntities.FREEZE.get(), FreezeRenderer::new);
        strategy.register(ESEntities.FROZEN_TUBE.get(), FrozenTubeRenderer::new);
        strategy.register(ESEntities.LUNAR_MONSTROSITY.get(), LunarMonstrosityRenderer::new);
        strategy.register(ESEntities.LUNAR_MONSTROSITY_BREATH.get(), EmptyRenderer::new);
        strategy.register(ESEntities.LUNAR_SPORE.get(), LunarSporeRenderer::new);
        strategy.register(ESEntities.LUNAR_THORN.get(), LunarThornRenderer::new);
        strategy.register(ESEntities.TANGLED_HATRED.get(), TangledHatredRenderer::new);
        strategy.register(ESEntities.TANGLED_HATRED_PART.get(), EmptyRenderer::new);
        strategy.register(ESEntities.SHATTERED_BLADE.get(), ThrownShatteredBladeRenderer::new);
        strategy.register(ESEntities.AMARAMBER_ARROW.get(), AmaramberArrowRenderer::new);
        strategy.register(ESEntities.SONAR_BOMB.get(), ThrownItemRenderer::new);
        strategy.register(ESEntities.SOULIT_SPECTATOR.get(), ThrownItemRenderer::new);
    }

    private static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0f);
    private static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5f);

    public static void registerLayers(RendererLayerRegisterStrategy strategy) {
        strategy.register(ThermalSpringStoneArmorModel.INNER_LOCATION, () -> ThermalSpringStoneArmorModel.createArmorLayer(INNER_ARMOR_DEFORMATION));
        strategy.register(ThermalSpringStoneArmorModel.OUTER_LOCATION, () -> ThermalSpringStoneArmorModel.createArmorLayer(OUTER_ARMOR_DEFORMATION));
        strategy.register(AlchemistArmorModel.LAYER_LOCATION, AlchemistArmorModel::createBodyLayer);
        strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.LUNAR), BoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.LUNAR), ChestBoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.NORTHLAND), BoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.NORTHLAND), ChestBoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.STARLIGHT_MANGROVE), BoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.STARLIGHT_MANGROVE), ChestBoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.SCARLET), BoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.SCARLET), ChestBoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.TORREYA), BoatModel::createBodyModel);
        strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.TORREYA), ChestBoatModel::createBodyModel);
        strategy.register(BoarwarfModel.LAYER_LOCATION, BoarwarfModel::createBodyLayer);
        strategy.register(BoarwarfBlacksmithModel.LAYER_LOCATION, BoarwarfBlacksmithModel::createBodyLayer);
        strategy.register(BoarwarfChefModel.LAYER_LOCATION, BoarwarfChefModel::createBodyLayer);
        strategy.register(BoarwarfDruidModel.LAYER_LOCATION, BoarwarfDruidModel::createBodyLayer);
        strategy.register(BoarwarfDyerModel.LAYER_LOCATION, BoarwarfDyerModel::createBodyLayer);
        strategy.register(BoarwarfSilversmithModel.LAYER_LOCATION, BoarwarfSilversmithModel::createBodyLayer);
        strategy.register(AstralGolemModel.LAYER_LOCATION, AstralGolemModel::createBodyLayer);
        strategy.register(AstralGolemModel.INNER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(AstralGolemModel.OUTER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(GleechModel.LAYER_LOCATION, GleechModel::createBodyLayer);
        strategy.register(LonestarSkeletonRenderer.LONESTAR, SkeletonModel::createBodyLayer);
        strategy.register(LonestarSkeletonRenderer.LONESTAR_INNER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(LonestarSkeletonRenderer.LONESTAR_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
        strategy.register(NightfallSpiderModel.LAYER_LOCATION, NightfallSpiderModel::createBodyLayer);
        strategy.register(ThirstWalkerModel.LAYER_LOCATION, ThirstWalkerModel::createBodyLayer);
        strategy.register(EntModel.LAYER_LOCATION, EntModel::createBodyLayer);
        strategy.register(RatlinModel.LAYER_LOCATION, RatlinModel::createBodyLayer);
        strategy.register(YetiModel.LAYER_LOCATION, YetiModel::createBodyLayer);
        strategy.register(AuroraDeerModel.LAYER_LOCATION, AuroraDeerModel::createBodyLayer);
        strategy.register(CrystallizedMothModel.LAYER_LOCATION, CrystallizedMothModel::createBodyLayer);
        strategy.register(ShimmerLacewingModel.LAYER_LOCATION, ShimmerLacewingModel::createBodyLayer);
        strategy.register(GrimstoneGolemModel.LAYER_LOCATION, GrimstoneGolemModel::createBodyLayer);
        strategy.register(LuminoFishModel.LAYER_LOCATION, LuminoFishModel::createBodyLayer);
        strategy.register(LuminarisModel.LAYER_LOCATION, LuminarisModel::createBodyLayer);
        strategy.register(TwilightGazeModel.LAYER_LOCATION, TwilightGazeModel::createBodyLayer);
        strategy.register(TheGatekeeperModel.LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(false));
        strategy.register(TheGatekeeperModel.SLIM_LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(true));
        strategy.register(TheGatekeeperModel.OUTER_LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(false, 0.5f));
        strategy.register(TheGatekeeperModel.SLIM_OUTER_LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(true, 0.5f));
        strategy.register(StarlightGolemModel.LAYER_LOCATION, StarlightGolemModel::createBodyLayer);
        strategy.register(FreezeModel.LAYER_LOCATION, FreezeModel::createBodyLayer);
        strategy.register(FrozenTubeModel.LAYER_LOCATION, FrozenTubeModel::createBodyLayer);
        strategy.register(LunarMonstrosityModel.LAYER_LOCATION, LunarMonstrosityModel::createBodyLayer);
        strategy.register(LunarSporeModel.LAYER_LOCATION, LunarSporeModel::createBodyLayer);
        strategy.register(LunarThornModel.LAYER_LOCATION, LunarThornModel::createBodyLayer);
        strategy.register(TangledHatredModel.LAYER_LOCATION, TangledHatredModel::createBodyLayer);
        strategy.register(GlaciteShieldModel.LAYER_LOCATION, GlaciteShieldModel::createBodyLayer);
    }
}
