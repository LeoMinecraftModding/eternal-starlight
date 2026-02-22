package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import cn.leolezury.eternalstarlight.common.client.gui.screen.BookScreen;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.StreamSupport;

public class DisplayBookComponent extends BookComponent<DisplayBookComponent.Config> {
	public DisplayBookComponent() {
		super(Config.CODEC);
	}

	@Override
	public int getTotalHeight(Config config, BookContext context) {
		int totalHeight = config.totalHeight();
		for (TextDisplay display : config.textDisplays()) {
			totalHeight = Math.max(totalHeight, display.getLeastComponentHeight(context));
		}
		return totalHeight;
	}

	@Override
	public void render(Config config, BookContext context, GuiGraphics graphics, int x, int y) {
		for (ImageDisplay display : config.imageDisplays()) {
			graphics.blit(display.location(), x + display.x(), y + display.y(), 0, 0, display.width(), display.height(), display.width(), display.height());
		}
		for (EntityDisplay display : config.entityDisplays()) {
			LivingEntity entity = display.getEntity();
			if (entity != null) {
				InventoryScreen.renderEntityInInventory(graphics, x + display.x, y + display.y, display.scale, new Vector3f(), display.rotation, null, entity);
			}
		}
		for (ItemDisplay display : config.itemDisplays()) {
			ItemStack stack = display.getItemStack();
			graphics.renderItem(stack, x + display.x, y + display.y);
		}
		for (ItemTagDisplay display : config.itemTagDisplays()) {
			List<Item> items = StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(display.tag()).spliterator(), false).map(Holder::value).toList();
			if (!items.isEmpty()) {
				ItemStack stack = items.get(Mth.floor(context.getTickCount() / 30.0) % items.size()).getDefaultInstance();
				graphics.renderItem(stack, x + display.x(), y + display.y());
			}
		}
		for (CraftingRecipeDisplay display : config.craftingRecipeDisplays) {
			Ingredient[][] ingredients = display.getIngredients();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					Ingredient ingredient = ingredients[i][j];
					if (ingredient != null) {
						ItemStack[] items = ingredient.getItems();
						ItemStack stack = items.length == 0 ? ItemStack.EMPTY : items[Mth.floor(context.getTickCount() / 30.0) % items.length];
						graphics.renderItem(stack, x + display.x + i * display.slotWidth + (display.slotWidth - 16) / 2, y + display.y + j * display.slotHeight + (display.slotHeight - 16) / 2);
					}
				}
			}
		}
		for (TextDisplay display : config.textDisplays()) {
			graphics.pose().pushPose();
			graphics.pose().translate(x + display.x, y + display.y, 0);
			List<FormattedCharSequence> lines = display.getLines(context);
			graphics.pose().scale(display.scale, display.scale, 1);
			for (int i = 0; i < lines.size(); i++) {
				FormattedCharSequence text = lines.get(i);
				graphics.drawString(context.getFont(), text, display.centered ? -context.getFont().width(text) / 2 : 0, i * display.lineHeight, 0, true);
			}
			graphics.pose().popPose();
		}
	}

	@Override
	public void renderDelayed(Config config, BookContext context, GuiGraphics graphics, int x, int y) {
		for (ItemDisplay display : config.itemDisplays()) {
			ItemStack stack = display.getItemStack();
			if (context.getMouseX() >= x + display.x && context.getMouseX() <= x + display.x + 16
				&& context.getMouseY() >= Math.max(y + display.y, context.getContentY()) && context.getMouseY() <= Math.min(y + display.y + 16, context.getContentY() + context.getBookDefinition().height() - 2 * context.getBookDefinition().frameWidth())) {
				graphics.pose().pushPose();
				graphics.pose().translate(0.0, 0.0, BookScreen.TOOLTIP_Z_OFFSET);
				graphics.renderTooltip(context.getFont(), Screen.getTooltipFromItem(Minecraft.getInstance(), stack), stack.getTooltipImage(), context.getMouseX(), context.getMouseY());
				graphics.pose().popPose();
			}
		}
		for (ItemTagDisplay display : config.itemTagDisplays()) {
			List<Item> items = StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(display.tag()).spliterator(), false).map(Holder::value).toList();
			if (!items.isEmpty()) {
				ItemStack stack = items.get(Mth.floor(context.getTickCount() / 30.0) % items.size()).getDefaultInstance();
				if (context.getMouseX() >= x + display.x() && context.getMouseX() <= x + display.x() + 16
					&& context.getMouseY() >= Math.max(y + display.y(), context.getContentY()) && context.getMouseY() <= Math.min(y + display.y() + 16, context.getContentY() + context.getBookDefinition().height() - 2 * context.getBookDefinition().frameWidth())) {
					graphics.pose().pushPose();
					graphics.pose().translate(0.0, 0.0, BookScreen.TOOLTIP_Z_OFFSET);
					graphics.renderTooltip(context.getFont(), Screen.getTooltipFromItem(Minecraft.getInstance(), stack), stack.getTooltipImage(), context.getMouseX(), context.getMouseY());
					graphics.pose().popPose();
				}
			}
		}
		for (CraftingRecipeDisplay display : config.craftingRecipeDisplays) {
			Ingredient[][] ingredients = display.getIngredients();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					Ingredient ingredient = ingredients[i][j];
					if (ingredient != null) {
						ItemStack[] items = ingredient.getItems();
						ItemStack stack = items.length == 0 ? ItemStack.EMPTY : items[Mth.floor(context.getTickCount() / 30.0) % items.length];
						if (!stack.isEmpty() && context.getMouseX() > x + display.x + i * display.slotWidth + (display.slotWidth - 16) / 2 && context.getMouseX() < x + display.x + i * display.slotWidth + (display.slotWidth - 16) / 2 + 16
							&& context.getMouseY() > Math.max(y + display.y + j * display.slotHeight + (display.slotHeight - 16) / 2, context.getContentY()) && context.getMouseY() < Math.min(y + display.y + j * display.slotHeight + (display.slotHeight - 16) / 2 + 16, context.getContentY() + context.getBookDefinition().height() - 2 * context.getBookDefinition().frameWidth())) {
							graphics.pose().pushPose();
							graphics.pose().translate(0.0, 0.0, BookScreen.TOOLTIP_Z_OFFSET);
							graphics.renderTooltip(context.getFont(), Screen.getTooltipFromItem(Minecraft.getInstance(), stack), stack.getTooltipImage(), context.getMouseX(), context.getMouseY());
							graphics.pose().popPose();
						}
					}
				}
			}
		}
	}

	@Override
	public void onClick(DisplayBookComponent.Config config, BookContext context, int x, int y) {
		for (TextDisplay display : config.textDisplays()) {
			int trueLineHeight = (int) (display.lineHeight * display.scale);
			List<FormattedCharSequence> list = display.getLines(context);
			int line = (context.getMouseY() - (y + display.y)) / trueLineHeight;
			if (context.getMouseY() >= (y + display.y) && line >= 0 && line < list.size()) {
				FormattedCharSequence charSequence = list.get(line);
				int width = (int) (context.getFont().width(charSequence) * display.scale);
				int startX = x + display.x - (display.centered ? width / 2 : 0);
				int endX = x + display.x + (display.centered ? width / 2 : width);
				if (context.getMouseX() >= startX && context.getMouseX() <= endX) {
					Style style = context.getFont().getSplitter().componentStyleAtWidth(charSequence, (int) ((context.getMouseX() - startX) / display.scale));
					if (style != null) {
						ClickEvent event = style.getClickEvent();
						if (event != null && event.getAction() == ClickEvent.Action.CHANGE_PAGE) {
							context.jumpToComponent(ResourceLocation.parse(event.getValue()));
						}
					}
				}
			}
		}
	}

	private static class TextDisplay {
		public static final Codec<TextDisplay> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BookContent.CODEC.fieldOf("text").forGetter(o -> o.text),
			Codec.BOOL.fieldOf("centered").forGetter(o -> o.centered),
			Codec.INT.fieldOf("x").forGetter(o -> o.x),
			Codec.INT.fieldOf("y").forGetter(o -> o.y),
			Codec.INT.fieldOf("width").forGetter(o -> o.width),
			Codec.INT.fieldOf("line_height").forGetter(o -> o.lineHeight),
			Codec.INT.fieldOf("min_distance_to_bottom").forGetter(o -> o.minDistanceToBottom),
			Codec.FLOAT.fieldOf("scale").forGetter(o -> o.scale)
		).apply(instance, TextDisplay::new));

		private final BookContent text;
		private final List<FormattedCharSequence> cachedText = new ArrayList<>();
		private final boolean centered;
		private final int x, y, width, lineHeight, minDistanceToBottom;
		private final float scale;

		public TextDisplay(BookContent text, boolean centered, int x, int y, int width, int lineHeight, int minDistanceToBottom, float scale) {
			this.text = text;
			this.centered = centered;
			this.x = x;
			this.y = y;
			this.width = width;
			this.lineHeight = lineHeight;
			this.minDistanceToBottom = minDistanceToBottom;
			this.scale = scale;
		}

		public List<FormattedCharSequence> getLines(BookContext context) {
			if (cachedText.isEmpty()) {
				cachedText.addAll(context.getFont().split(text.toComponent(), (int) (width / scale)));
			}
			return cachedText;
		}

		public int getLeastComponentHeight(BookContext context) {
			return (int) (y + getLines(context).size() * lineHeight * scale + minDistanceToBottom);
		}
	}

	private static class EntityDisplay {
		public static final Codec<EntityDisplay> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			CompoundTag.CODEC.fieldOf("entity").forGetter(o -> o.entityTag),
			Codec.INT.fieldOf("x").forGetter(o -> o.x),
			Codec.INT.fieldOf("y").forGetter(o -> o.y),
			Codec.FLOAT.fieldOf("x_rot").forGetter(o -> o.xRot),
			Codec.FLOAT.fieldOf("y_rot").forGetter(o -> o.yRot),
			Codec.FLOAT.fieldOf("scale").forGetter(o -> o.scale),
			ExtraCodecs.QUATERNIONF.fieldOf("rotation").forGetter(o -> o.rotation)
		).apply(instance, EntityDisplay::new));

		private LivingEntity cachedEntity;
		private final CompoundTag entityTag;
		private final int x, y;
		private final float xRot, yRot, scale;
		private final Quaternionf rotation;

		public EntityDisplay(CompoundTag entity, int x, int y, float xRot, float yRot, float scale, Quaternionf rotation) {
			this.entityTag = entity;
			this.x = x;
			this.y = y;
			this.xRot = xRot;
			this.yRot = yRot;
			this.scale = scale;
			this.rotation = rotation;
		}

		public LivingEntity getEntity() {
			if (cachedEntity == null && Minecraft.getInstance().level != null) {
				if (EntityType.loadEntityRecursive(entityTag, Minecraft.getInstance().level, e -> e) instanceof LivingEntity entity) {
					cachedEntity = entity;
					cachedEntity.yBodyRot = yRot;
					cachedEntity.setXRot(xRot);
					cachedEntity.setYRot(yRot);
					cachedEntity.yHeadRot = cachedEntity.getYRot();
					cachedEntity.yHeadRotO = cachedEntity.getYRot();
				}
			}
			return cachedEntity;
		}
	}

	private static class ItemDisplay {
		public static final Codec<ItemDisplay> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			CompoundTag.CODEC.fieldOf("item").forGetter(o -> o.itemStackTag),
			Codec.INT.fieldOf("x").forGetter(o -> o.x),
			Codec.INT.fieldOf("y").forGetter(o -> o.y)
		).apply(instance, ItemDisplay::new));

		private ItemStack cachedStack = null;
		private final CompoundTag itemStackTag;
		private final int x, y;

		public ItemDisplay(CompoundTag itemStack, int x, int y) {
			this.itemStackTag = itemStack;
			this.x = x;
			this.y = y;
		}

		public ItemStack getItemStack() {
			if (cachedStack == null && Minecraft.getInstance().level != null) {
				cachedStack = ItemStack.parseOptional(Minecraft.getInstance().level.registryAccess(), itemStackTag);
			}
			return cachedStack == null ? ItemStack.EMPTY : cachedStack;
		}
	}

	private record ItemTagDisplay(TagKey<Item> tag, int x, int y) {
		public static final Codec<ItemTagDisplay> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			TagKey.codec(Registries.ITEM).fieldOf("item_tag").forGetter(ItemTagDisplay::tag),
			Codec.INT.fieldOf("x").forGetter(ItemTagDisplay::x),
			Codec.INT.fieldOf("y").forGetter(ItemTagDisplay::y)
		).apply(instance, ItemTagDisplay::new));
	}

	private static class CraftingRecipeDisplay {
		public static final Codec<CraftingRecipeDisplay> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("recipe").forGetter(o -> o.recipeId),
			Codec.INT.fieldOf("x").forGetter(o -> o.x),
			Codec.INT.fieldOf("y").forGetter(o -> o.y),
			Codec.INT.fieldOf("slot_width").forGetter(o -> o.slotWidth),
			Codec.INT.fieldOf("slot_height").forGetter(o -> o.slotHeight)
		).apply(instance, CraftingRecipeDisplay::new));

		private final Ingredient[][] ingredients = new Ingredient[3][3];
		private final ResourceLocation recipeId;
		private RecipeHolder<?> recipe;
		private final int x, y, slotWidth, slotHeight;
		private boolean recipePlaced = false;

		public CraftingRecipeDisplay(ResourceLocation recipe, int x, int y, int slotWidth, int slotHeight) {
			this.recipeId = recipe;
			this.x = x;
			this.y = y;
			this.slotWidth = slotWidth;
			this.slotHeight = slotHeight;
		}

		public Ingredient[][] getIngredients() {
			if (recipe == null && Minecraft.getInstance().level != null) {
				recipe = Minecraft.getInstance().level.getRecipeManager().byKey(recipeId).orElse(null);
			}
			if (!recipePlaced && recipe != null && recipe.value().getType() == RecipeType.CRAFTING) {
				recipePlaced = true;
				PlaceRecipe<Ingredient> placeRecipe = (ingredient, slot, maxAmount, x, y) -> ingredients[x][y] = ingredient;
				placeRecipe.placeRecipe(3, 3, -1, recipe, recipe.value().getIngredients().iterator(), 0);
			}
			return ingredients;
		}
	}

	private record ImageDisplay(ResourceLocation location, int x, int y, int width, int height) {
		public static final Codec<ImageDisplay> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("location").forGetter(ImageDisplay::location),
			Codec.INT.fieldOf("x").forGetter(ImageDisplay::x),
			Codec.INT.fieldOf("y").forGetter(ImageDisplay::y),
			Codec.INT.fieldOf("width").forGetter(ImageDisplay::width),
			Codec.INT.fieldOf("height").forGetter(ImageDisplay::height)
		).apply(instance, ImageDisplay::new));
	}

	public record Config(ResourceLocation id, HashSet<HashSet<ResourceLocation>> unlockConditions, int totalHeight, List<TextDisplay> textDisplays, List<EntityDisplay> entityDisplays, List<ItemDisplay> itemDisplays, List<ItemTagDisplay> itemTagDisplays, List<CraftingRecipeDisplay> craftingRecipeDisplays, List<ImageDisplay> imageDisplays) implements BookComponentConfig {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("id").forGetter(Config::id),
			ResourceLocation.CODEC.listOf().xmap(Sets::newHashSet, Lists::newArrayList).listOf().xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("unlock_conditions").forGetter(Config::unlockConditions),
			Codec.INT.fieldOf("total_height").forGetter(Config::totalHeight),
			TextDisplay.CODEC.listOf().fieldOf("text_displays").forGetter(Config::textDisplays),
			EntityDisplay.CODEC.listOf().fieldOf("entity_displays").forGetter(Config::entityDisplays),
			ItemDisplay.CODEC.listOf().fieldOf("item_displays").forGetter(Config::itemDisplays),
			ItemTagDisplay.CODEC.listOf().fieldOf("item_tag_displays").forGetter(Config::itemTagDisplays),
			CraftingRecipeDisplay.CODEC.listOf().fieldOf("crafting_recipe_displays").forGetter(Config::craftingRecipeDisplays),
			ImageDisplay.CODEC.listOf().fieldOf("image_displays").forGetter(Config::imageDisplays)
		).apply(instance, Config::new));

		public Config(ResourceLocation id, HashSet<HashSet<ResourceLocation>> unlockConditions, int totalHeight) {
			this(id, unlockConditions, totalHeight, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		}

		public Config textDisplay(BookContent text, boolean centered, int x, int y, int width, int lineHeight, int minDistanceToBottom, float scale) {
			textDisplays.add(new TextDisplay(text, centered, x, y, width, lineHeight, minDistanceToBottom, scale));
			return this;
		}

		public Config entityDisplay(CompoundTag tag, int x, int y, float xRot, float yRot, float scale, Quaternionf rotation) {
			entityDisplays.add(new EntityDisplay(tag, x, y, xRot, yRot, scale, rotation));
			return this;
		}

		public Config itemDisplay(CompoundTag tag, int x, int y) {
			itemDisplays.add(new ItemDisplay(tag, x, y));
			return this;
		}

		public Config itemTagDisplay(TagKey<Item> tag, int x, int y) {
			itemTagDisplays.add(new ItemTagDisplay(tag, x, y));
			return this;
		}

		public Config craftingRecipeDisplay(ResourceLocation recipe, int x, int y, int slotWidth, int slotHeight) {
			craftingRecipeDisplays.add(new CraftingRecipeDisplay(recipe, x, y, slotWidth, slotHeight));
			return this;
		}

		public Config imageDisplay(ResourceLocation location, int x, int y, int width, int height) {
			imageDisplays.add(new ImageDisplay(location, x, y, width, height));
			return this;
		}
	}
}
