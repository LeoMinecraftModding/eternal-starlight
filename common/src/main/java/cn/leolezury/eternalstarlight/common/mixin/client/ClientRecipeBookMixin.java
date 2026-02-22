package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public abstract class ClientRecipeBookMixin {
	@Inject(method = "getCategory", at = @At("HEAD"), cancellable = true)
	private static void getCategory(RecipeHolder<?> recipeHolder, CallbackInfoReturnable<RecipeBookCategories> cir) {
		// disable annoying unknown category warning
		ResourceLocation key = BuiltInRegistries.RECIPE_TYPE.getKey(recipeHolder.value().getType());
		if (key != null && key.getNamespace().equals(EternalStarlight.ID)) {
			cir.setReturnValue(RecipeBookCategories.UNKNOWN);
		}
	}
}
