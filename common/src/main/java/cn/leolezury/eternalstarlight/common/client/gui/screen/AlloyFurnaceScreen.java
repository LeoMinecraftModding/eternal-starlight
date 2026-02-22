package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.item.menu.AlloyFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceMenu> implements MenuAccess<AlloyFurnaceMenu> {
	private static final ResourceLocation CONTAINER_BACKGROUND = EternalStarlight.id("textures/gui/screen/alloy_furnace/background.png");
	private static final ResourceLocation LIT_PROGRESS = EternalStarlight.id("screen/alloy_furnace/lit_progress");
	private static final ResourceLocation BURN_PROGRESS = EternalStarlight.id("screen/alloy_furnace/burn_progress");
	private static final ResourceLocation COOLING_PROGRESS = EternalStarlight.id("screen/alloy_furnace/cooling_progress");
	private static final ResourceLocation OVERHEAT_PROGRESS = EternalStarlight.id("screen/alloy_furnace/overheat_progress");

	public AlloyFurnaceScreen(AlloyFurnaceMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
		this.imageHeight = 166;
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
		int litProgress = Mth.ceil(this.menu.getLitProgress() * 13.0F) + 1;
		guiGraphics.blitSprite(LIT_PROGRESS, 14, 14, 0, 14 - litProgress, this.leftPos + 10, this.topPos + 50 - litProgress, 14, litProgress);
		int burnProgress = Mth.ceil(this.menu.getBurnProgress() * 24.0F);
		guiGraphics.blitSprite(BURN_PROGRESS, 24, 16, 0, 0, this.leftPos + 89, this.topPos + 18, burnProgress, 16);
		int coolingProgress = Mth.ceil(this.menu.getCoolingProgress() * 15.0F) + 1;
		guiGraphics.blitSprite(COOLING_PROGRESS, 16, 16, 0, 16 - coolingProgress, this.leftPos + 85, this.topPos + 50 - coolingProgress, 16, coolingProgress);
		int overheatProgress = Mth.ceil(this.menu.getOverheatProgress() * 18.0F);
		guiGraphics.blitSprite(OVERHEAT_PROGRESS, 26, 18, 0, 18 - overheatProgress, this.leftPos + 89, this.topPos + 34 - overheatProgress, 26, overheatProgress);
	}
}