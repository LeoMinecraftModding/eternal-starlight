package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookAccess;
import cn.leolezury.eternalstarlight.common.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayBookComponent extends BookComponent {
	private final List<TextDisplay> textDisplays = new ArrayList<>();
	private final List<EntityDisplay> entityDisplays = new ArrayList<>();
	private final Map<EntityDisplay, LivingEntity> entities = new HashMap<>();
	private final List<ItemDisplay> itemDisplays = new ArrayList<>();
	private final List<ImageDisplay> imageDisplays = new ArrayList<>();

	public DisplayBookComponent(int width, int height) {
		super(width, height);
	}

	@Override
	public int getPageCount(Font font) {
		return 1;
	}

	@Override
	public void render(BookAccess access, GuiGraphics graphics, Font font, int x, int y, int mouseX, int mouseY) {
		for (ImageDisplay display : imageDisplays) {
			graphics.blit(display.location(), x + display.x(), y + display.y(), 0, 0, display.width(), display.height(), display.width(), display.height());
		}
		for (EntityDisplay display : entityDisplays) {
			if (!entities.containsKey(display) && Minecraft.getInstance().level != null) {
				LivingEntity livingEntity = display.type().create(Minecraft.getInstance().level);
				if (livingEntity != null) {
					livingEntity.yBodyRot = display.yRot();
					livingEntity.setXRot(display.xRot());
					livingEntity.setYRot(display.yRot());
					livingEntity.yHeadRot = livingEntity.getYRot();
					livingEntity.yHeadRotO = livingEntity.getYRot();
					entities.put(display, livingEntity);
				}
			}
			if (entities.containsKey(display)) {
				InventoryScreen.renderEntityInInventory(graphics, x + display.x(), y + display.y(), display.scale(), new Vector3f(), display.rotation, null, entities.get(display));
			}
		}
		for (ItemDisplay display : itemDisplays) {
			graphics.renderItem(display.stack(), x + display.x(), y + display.y());
		}
		for (TextDisplay display : textDisplays) {
			graphics.pose().pushPose();
			graphics.pose().translate(x + display.x(), y + display.y(), 0);
			graphics.pose().scale(display.scale(), display.scale(), display.scale());
			graphics.drawString(font, display.text(), -font.width(display.text()) / 2, -font.lineHeight, Color.BLACK.argb(), false);
			graphics.pose().popPose();
		}
	}

	@Override
	public void tick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {

	}

	@Override
	public void singleTick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {

	}

	@Override
	public void onClick(BookAccess access, Font font, int x, int y, int mouseX, int mouseY) {

	}

	public DisplayBookComponent textDisplay(Component text, int x, int y, float scale) {
		textDisplays.add(new TextDisplay(text, x, y, scale));
		return this;
	}

	public DisplayBookComponent entityDisplay(EntityType<? extends LivingEntity> type, int x, int y, float xRot, float yRot, float scale, Quaternionf rotation) {
		entityDisplays.add(new EntityDisplay(type, x, y, xRot, yRot, scale, rotation));
		return this;
	}

	public DisplayBookComponent itemDisplay(ItemStack stack, int x, int y) {
		itemDisplays.add(new ItemDisplay(stack, x, y));
		return this;
	}

	public DisplayBookComponent imageDisplay(ResourceLocation location, int x, int y, int width, int height) {
		imageDisplays.add(new ImageDisplay(location, x, y, width, height));
		return this;
	}

	private record TextDisplay(Component text, int x, int y, float scale) {

	}

	private record EntityDisplay(EntityType<? extends LivingEntity> type, int x, int y, float xRot, float yRot, float scale, Quaternionf rotation) {

	}

	private record ItemDisplay(ItemStack stack, int x, int y) {

	}

	private record ImageDisplay(ResourceLocation location, int x, int y, int width, int height) {

	}
}
