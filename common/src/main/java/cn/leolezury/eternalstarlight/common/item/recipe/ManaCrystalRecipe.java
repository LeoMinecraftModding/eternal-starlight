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
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
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

    public boolean matches(CraftingContainer container, Level level) {
        int day = (int) (level.getDayTime() / 24000L);
        boolean checkDay = day % 6 == List.of(ManaType.values()).indexOf(manaType) - 1;
        boolean checkEmpty = container.getItem(0).isEmpty() && container.getItem(2).isEmpty() && container.getItem(6).isEmpty() && container.getItem(8).isEmpty();
        boolean checkIngredients = container.getItem(1).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS) && container.getItem(3).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS) && container.getItem(4).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS) && container.getItem(5).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS) && container.getItem(7).is(ESTags.Items.MANA_CRYSTAL_INGREDIENTS);
        return container.getWidth() == 3 && container.getHeight() == 3 && checkDay && checkEmpty && checkIngredients;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, HolderLookup.Provider provider) {
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