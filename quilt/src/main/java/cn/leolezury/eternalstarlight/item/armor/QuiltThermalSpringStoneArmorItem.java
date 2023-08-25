package cn.leolezury.eternalstarlight.item.armor;

import cn.leolezury.eternalstarlight.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.util.ESUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.rendering.entity.api.client.ArmorRenderingRegistry;

public class QuiltThermalSpringStoneArmorItem extends ThermalSpringStoneArmorItem {
    public QuiltThermalSpringStoneArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
        ESUtil.runWhenOnClient(() -> this::initializeClient);
    }



    @ClientOnly
    public void initializeClient() {
        ArmorRenderingRegistry.registerModelProvider(ModelProvider.INSTANCE, this);
        ArmorRenderingRegistry.registerTextureProvider(TextureProvider.INSTANCE ,this);
    }

    @ClientOnly
    private static final class ModelProvider implements ArmorRenderingRegistry.ModelProvider {
        private static final ArmorRenderingRegistry.ModelProvider INSTANCE = new ModelProvider();

        private ThermalSpringStoneArmorModel<LivingEntity> innerModel;
        private ThermalSpringStoneArmorModel<LivingEntity> outerModel;

        @Override
        public @NotNull HumanoidModel<LivingEntity> getArmorModel(@NotNull HumanoidModel<LivingEntity> model, @NotNull LivingEntity entity, @NotNull ItemStack itemStack, @NotNull EquipmentSlot slot) {
            if (innerModel == null || outerModel == null) {
                innerModel = new ThermalSpringStoneArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.INNER_LOCATION));
                outerModel = new ThermalSpringStoneArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.OUTER_LOCATION));
            }

            if (itemStack.is(ItemInit.THERMAL_SPRINGSTONE_HELMET.get()) || itemStack.is(ItemInit.THERMAL_SPRINGSTONE_CHESTPLATE.get()) || itemStack.is(ItemInit.THERMAL_SPRINGSTONE_BOOTS.get())) {
                return outerModel;
            } else {
                return innerModel;
            }
        }
    }

    @ClientOnly
    private static final class TextureProvider implements ArmorRenderingRegistry.TextureProvider {
        private static final ArmorRenderingRegistry.TextureProvider INSTANCE = new TextureProvider();
        @Override
        public @NotNull ResourceLocation getArmorTexture(@NotNull ResourceLocation texture, @NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull EquipmentSlot slot, boolean useSecondLayer, @Nullable String suffix) {
            return new ResourceLocation(getTexture(stack, entity, slot, null));
        }
    }
}
