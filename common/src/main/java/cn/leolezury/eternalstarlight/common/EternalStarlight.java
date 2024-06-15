package cn.leolezury.eternalstarlight.common;

import cn.leolezury.eternalstarlight.common.block.flammable.ESFlammabilityRegistry;
import cn.leolezury.eternalstarlight.common.client.helper.ClientHelper;
import cn.leolezury.eternalstarlight.common.client.helper.ClientSideHelper;
import cn.leolezury.eternalstarlight.common.client.helper.EmptyClientHelper;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicReference;

public class EternalStarlight {
    public static final String ID = "eternal_starlight";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ESFluids.loadClass();
        ESBlocks.loadClass();
        ESDataComponents.loadClass();
        ESArmorMaterials.loadClass();
        ESSpells.loadClass();
        ESItems.loadClass();
        ESCreativeModeTabs.loadClass();
        ESCriteriaTriggers.loadClass();
        ESAttributes.loadClass();
        ESEntities.loadClass();
        ESSensorTypes.loadClass();
        ESMobEffects.loadClass();
        ESBlockEntities.loadClass();
        ESEnchantmentEntityEffects.loadClass();
        ESWorldCarvers.loadClass();
        ESFeatures.loadClass();
        ESPlacers.loadClass();
        ESTreeDecorators.loadClass();
        ESStructureTypes.loadClass();
        ESStructurePieceTypes.loadClass();
        ESStructurePlacementTypes.loadClass();
        ESStructurePoolElementTypes.loadClass();
        ESParticles.loadClass();
        ESSoundEvents.loadClass();
        ESRecipeSerializers.loadClass();
        ESRecipes.loadClass();
        ESDataTransformerTypes.loadClass();
        ESWeathers.loadClass();
        ESBoarwarfProfessions.loadClass();
        ESRegistries.loadClass();
        ESFlammabilityRegistry.registerDefaults();
    }

    public static ResourceLocation id(String string) {
        return ResourceLocation.fromNamespaceAndPath(ID, string);
    }

    public static ClientHelper getClientHelper() {
        AtomicReference<ClientHelper> helper = new AtomicReference<>(new EmptyClientHelper());
        ESMiscUtil.runWhenOnClient(() -> () -> helper.set(new ClientSideHelper()));
        return helper.get();
    }
}
