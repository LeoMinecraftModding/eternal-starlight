package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.data.ESTrimMaterials;
import cn.leolezury.eternalstarlight.common.data.ESTrimPatterns;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(SpriteSourceList.class)
public class SpriteSourceListMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(List<SpriteSource> list, CallbackInfo ci) {
		for (SpriteSource source : list) {
			if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
				List<ResourceLocation> textures = new ArrayList<>(permutations.getTextures());
				for (ResourceKey<TrimPattern> key : ESTrimPatterns.TRIM_PATTERNS) {
					textures.add(key.location().withPrefix("trims/models/armor/"));
					textures.add(key.location().withPrefix("trims/models/armor/").withSuffix("_leggings"));
				}
				permutations.setTextures(textures);
				Map<String, ResourceLocation> map = new HashMap<>(permutations.getPermutations());
				for (ResourceKey<TrimMaterial> key : ESTrimMaterials.TRIM_MATERIALS) {
					map.put(key.location().getPath(), key.location().withPrefix("trims/color_palettes/"));
				}
				permutations.setPermutations(map);
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
		Map<String, ResourceLocation> getPermutations();

		@Accessor("permutations")
		void setPermutations(Map<String, ResourceLocation> value);

		@Accessor
		ResourceLocation getPaletteKey();
	}
}
