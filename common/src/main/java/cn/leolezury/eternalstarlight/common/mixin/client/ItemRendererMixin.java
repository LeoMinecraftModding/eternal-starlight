package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
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

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
	@ModifyVariable(method = "render", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, argsOnly = true)
	public BakedModel render(BakedModel bakedModel, ItemStack stack, ItemDisplayContext itemDisplayContext) {
		ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(stack.getItem());
		if (ClientSetupHandlers.ITEMS_WITH_SPECIAL_MODEL.containsKey(new ModelResourceLocation(itemKey, "inventory")) && ClientSetupHandlers.ITEMS_WITH_SPECIAL_MODEL.get(new ModelResourceLocation(itemKey, "inventory")).containsKey(itemDisplayContext)) {
			BakedModel replacedModel = ClientSetupHandlers.BAKED_MODELS.get(ClientSetupHandlers.getSpecialModel(new ModelResourceLocation(itemKey, "inventory"), itemDisplayContext));
			if (replacedModel != null) {
				return replacedModel.getOverrides().resolve(replacedModel, stack, Minecraft.getInstance().level, null, 0);
			}
		}
		return bakedModel;
	}
}
