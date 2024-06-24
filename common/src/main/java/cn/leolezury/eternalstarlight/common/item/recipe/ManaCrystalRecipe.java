package cn.leolezury.eternalstarlight.common.item.recipe;

import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class ManaCrystalRecipe extends CustomRecipe {
	private final ManaType manaType;
	private final Item manaCrystal;

	public ManaCrystalRecipe(CraftingBookCategory craftingBookCategory, ManaType type, Item item) {
		super(craftingBookCategory);
		this.manaType = type;
		this.manaCrystal = item;
	}

	@Override
	public boolean matches(CraftingInput recipeInput, Level level) {
		int day = (int) (level.getDayTime() / 24000L);
		boolean checkDay = day % 6 == List.of(ManaType.values()).indexOf(manaType) - 1;
		boolean checkEmpty = recipeInput.getItem(0).isEmpty() && recipeInput.getItem(2).isEmpty() && recipeInput.getItem(6).isEmpty() && recipeInput.getItem(8).isEmpty();
		boolean checkIngredients = recipeInput.getItem(1).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS) && recipeInput.getItem(3).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS) && recipeInput.getItem(4).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS) && recipeInput.getItem(5).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS) && recipeInput.getItem(7).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS);
		return recipeInput.width() == 3 && recipeInput.height() == 3 && checkDay && checkEmpty && checkIngredients;
	}

	@Override
	public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
		return manaCrystal.getDefaultInstance();
	}

	public boolean canCraftInDimensions(int i, int j) {
		return i >= 2 && j >= 2;
	}

	public RecipeSerializer<?> getSerializer() {
		return ESRecipeSerializers.MANA_CRYSTAL.get();
	}

	public static class Serializer implements RecipeSerializer<ManaCrystalRecipe> {
		private static final MapCodec<ManaCrystalRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CraftingRecipe::category),
			ManaType.CODEC.fieldOf("mana_type").forGetter(recipe -> recipe.manaType),
			BuiltInRegistries.ITEM.byNameCodec().fieldOf("crystal").forGetter(recipe -> recipe.manaCrystal)
		).apply(instance, ManaCrystalRecipe::new));

		@Override
		public MapCodec<ManaCrystalRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ManaCrystalRecipe> streamCodec() {
			return new StreamCodec<>() {
				@Override
				public ManaCrystalRecipe decode(RegistryFriendlyByteBuf friendlyByteBuf) {
					CraftingBookCategory category = friendlyByteBuf.readEnum(CraftingBookCategory.class);
					ManaType type = friendlyByteBuf.readEnum(ManaType.class);
					Item output = friendlyByteBuf.readById(BuiltInRegistries.ITEM::byId);
					return new ManaCrystalRecipe(category, type, output);
				}

				@Override
				public void encode(RegistryFriendlyByteBuf friendlyByteBuf, ManaCrystalRecipe recipe) {
					friendlyByteBuf.writeEnum(recipe.category());
					friendlyByteBuf.writeEnum(recipe.manaType);
					friendlyByteBuf.writeById(BuiltInRegistries.ITEM::getId, recipe.manaCrystal);
				}
			};
		}
	}
}