package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.menu.CrystalbornCatalystMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class CrystalbornCatalystScreen extends AbstractContainerScreen<CrystalbornCatalystMenu> implements MenuAccess<CrystalbornCatalystMenu> {
	private static final ResourceLocation CONTAINER_BACKGROUND = EternalStarlight.id("textures/gui/screen/crystalborn_catalyst/background.png");
	private static final ResourceLocation PROGRESS = EternalStarlight.id("screen/crystalborn_catalyst/progress");

	public CrystalbornCatalystScreen(CrystalbornCatalystMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageHeight = 148;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		guiGraphics.blit(CONTAINER_BACKGROUND, x, y, 0, 0, this.imageWidth, this.imageHeight);
		if (menu.getChargeProgress() > 0) {
			int progress = (Mth.ceil(menu.getChargeProgress() * 13) + 1);
			guiGraphics.blitSprite(PROGRESS, 14, 14, 0, 14 - progress, this.leftPos + 9, this.topPos + 34 - progress, 14, progress);
		}
	}
}