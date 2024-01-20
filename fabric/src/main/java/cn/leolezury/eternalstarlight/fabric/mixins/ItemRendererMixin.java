package cn.leolezury.eternalstarlight.fabric.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "render", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, argsOnly = true)
    public BakedModel es_render(BakedModel bakedModel, ItemStack stack, ItemDisplayContext itemDisplayContext) {
        ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (itemDisplayContext == ItemDisplayContext.GUI && ClientSetupHandlers.ITEMS_WITH_INV_ICON.containsKey(new ModelResourceLocation(itemKey, "inventory"))) {
            return ClientSetupHandlers.BAKED_MODELS.get(ClientSetupHandlers.ITEMS_WITH_INV_ICON.get(new ModelResourceLocation(itemKey, "inventory")));
        }
        return bakedModel;
    }
}
