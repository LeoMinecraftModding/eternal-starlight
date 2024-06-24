package cn.leolezury.eternalstarlight.common.mixins;

import cn.leolezury.eternalstarlight.common.client.handler.ClientSetupHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(SpriteSourceList.class)
public class SpriteSourceListMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(List<SpriteSource> list, CallbackInfo ci) {
		for (SpriteSource source : list) {
			if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
				List<ResourceLocation> textures = new ArrayList<>(permutations.getTextures());
				for (ResourceLocation location : ClientSetupHandlers.TRIMS) {
					ResourceLocation legLocation = location.withSuffix("_leggings");
					textures.add(location);
					textures.add(legLocation);
				}
				permutations.setTextures(textures);
			}
		}
	}

	@Mixin(PalettedPermutations.class)
	private interface PalettedPermutationsAccessor {
		@Accessor
		List<ResourceLocation> getTextures();

		@Accessor("textures")
		void setTextures(List<ResourceLocation> value);

		@Accessor
		ResourceLocation getPaletteKey();
	}
}
