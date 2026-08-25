package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.CreativeTabSectionButton;
import cn.leolezury.eternalstarlight.common.item.tab.ESCreativeModeTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {
	@Shadow
	@Final
	private boolean displayOperatorCreativeTab;

	@Shadow
	private void refreshCurrentTabContents(Collection<ItemStack> items) {
	}

	@Unique
	private final List<CreativeTabSectionButton> eternalStarlightSectionButtons = new ArrayList<>();

	@Inject(method = "selectTab", at = @At("HEAD"))
	private void onSelectTab(CreativeModeTab tab, CallbackInfo ci) {
		CreativeModeInventoryScreen self = (CreativeModeInventoryScreen) (Object) this;
		for (CreativeTabSectionButton button : this.eternalStarlightSectionButtons) {
			self.removeWidget(button);
		}
		this.eternalStarlightSectionButtons.clear();

		ESCreativeModeTab group = ESCreativeModeTab.byTab(tab);
		if (group != null && group.getSectionCount() > 1) {
			for (int i = 0; i < group.getSectionCount(); i++) {
				int x = self.leftPos - 27 - (i / CreativeTabSectionButton.BUTTONS_PER_COLUMN) * CreativeTabSectionButton.COLUMN_SPACING;
				int y = self.topPos + 10 + (i % CreativeTabSectionButton.BUTTONS_PER_COLUMN) * CreativeTabSectionButton.SPACING;
				int sectionIndex = i;
				CreativeTabSectionButton button = new CreativeTabSectionButton(x, y, group, sectionIndex, () -> {
					if (Screen.hasShiftDown()) {
						group.toggleSection(sectionIndex);
					} else {
						group.selectSection(sectionIndex);
					}

					LocalPlayer player = Minecraft.getInstance().player;

					if (player != null) {
						CreativeModeTab.ItemDisplayParameters parameters = new CreativeModeTab.ItemDisplayParameters(
							player.connection.enabledFeatures(),
							player.canUseGameMasterBlocks() && this.displayOperatorCreativeTab,
							player.level().registryAccess()
						);
						tab.buildContents(parameters);
						this.refreshCurrentTabContents(group.tab().getDisplayItems());
					}
				});
				this.eternalStarlightSectionButtons.add(button);
				self.addRenderableWidget(button);
			}
		}
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		for (CreativeTabSectionButton button : this.eternalStarlightSectionButtons) {
			if (button.isHovered()) {
				List<Component> tooltip = new ArrayList<>();
				tooltip.add(button.getMessage());
				tooltip.add(Component.translatable("tab." + EternalStarlight.ID + ".tooltip"));
				guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, tooltip, mouseX, mouseY);
				break;
			}
		}
	}
}
