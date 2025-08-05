package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.NpcDialogueChoiceButton;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.NpcDialogueTextWidget;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.network.CloseGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GatekeeperDialogueScreen extends Screen {
	private Component currentText;
	private NpcDialogueTextWidget text;
	private final List<NpcDialogueChoiceButton> choiceButtons = new ArrayList<>();
	private final TheGatekeeper gatekeeper;
	private final boolean challenged;
	private boolean packetSent = false;

	public GatekeeperDialogueScreen(TheGatekeeper gatekeeper, boolean challenged) {
		super(Component.empty());
		this.gatekeeper = gatekeeper;
		this.challenged = challenged;
	}

	@Override
	protected void init() {
		if (currentText == null) {
			currentText = Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_intro");
		}
		if (this.choiceButtons.isEmpty()) {
			addChoices(
				new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_challenge"), button -> {
					if (challenged) {
						currentText = Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_rematch");
					} else {
						currentText = Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_challenge_confirm");
					}
					addChoices(
						new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_challenge"), button1 -> {
							sendClosePacket(TheGatekeeper.GUI_RESPONSE_CHALLENGE);
							Minecraft.getInstance().setScreen(null);
						}),
						new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_do_not"), button1 -> {
							sendClosePacket(TheGatekeeper.GUI_RESPONSE_EMPTY);
							Minecraft.getInstance().setScreen(null);
						})
					);
				}),
				new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_trade"), button -> {
					if (!challenged) {
						currentText = Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_deny_trade");
						addChoices(
							new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_confirm"), button1 -> {
								sendClosePacket(TheGatekeeper.GUI_RESPONSE_EMPTY);
								Minecraft.getInstance().setScreen(null);
							})
						);
					} else {
						sendClosePacket(TheGatekeeper.GUI_RESPONSE_TRADE);
						Minecraft.getInstance().setScreen(null);
					}
				}),
				new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_leave"), button -> {
					currentText = Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_leave_confirm");
					addChoices(
						new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_leave"), button1 -> {
							sendClosePacket(TheGatekeeper.GUI_RESPONSE_LEAVE);
							Minecraft.getInstance().setScreen(null);
						}),
						new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_do_not"), button1 -> {
							sendClosePacket(TheGatekeeper.GUI_RESPONSE_EMPTY);
							Minecraft.getInstance().setScreen(null);
						})
					);
				}),
				new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_nothing"), button -> {
					currentText = Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_farewell");
					addChoices(
						new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.ID + ".gatekeeper_confirm"), button1 -> {
							sendClosePacket(TheGatekeeper.GUI_RESPONSE_EMPTY);
							Minecraft.getInstance().setScreen(null);
						})
					);
				})
			);
		} else {
			int y = height;
			this.text = this.addRenderableWidget(new NpcDialogueTextWidget(currentText));
			for (NpcDialogueChoiceButton button : choiceButtons) {
				this.addRenderableWidget(button);
				y -= button.getIncrement(width / 4 * 3);
				button.reposition(width / 4, y, width / 4 * 3);
			}
			y -= this.text.getIncrement(width / 4 * 3);
			this.text.reposition(width / 4, y, width / 4 * 3);
		}
	}

	private void addChoices(NpcDialogueChoiceButton... buttons) {
		clearWidgets();
		choiceButtons.clear();
		int y = height;
		this.text = this.addRenderableWidget(new NpcDialogueTextWidget(currentText));
		for (int i = buttons.length - 1; i >= 0; i--) {
			choiceButtons.add(addRenderableWidget(buttons[i]));
			y -= buttons[i].getIncrement(width / 4 * 3);
			buttons[i].reposition(width / 4, y, width / 4 * 3);
		}
		y -= this.text.getIncrement(width / 4 * 3);
		this.text.reposition(width / 4, y, width / 4 * 3);
	}

	@Override
	public void tick() {
		if (this.text != null) {
			this.text.tick();
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int i, int j, float f) {
		super.render(guiGraphics, i, j, f);
		InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, 0, height / 2, width / 4, height, 60, 0.0625F, i, j, gatekeeper);
	}

	@Override
	public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {

	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private void sendClosePacket(int id) {
		if (!packetSent) ESPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(gatekeeper.getId(), id));
		packetSent = true;
	}

	@Override
	public void removed() {
		super.removed();
		sendClosePacket(TheGatekeeper.GUI_RESPONSE_EMPTY);
	}

	@Override
	public void onClose() {
		super.onClose();
		sendClosePacket(TheGatekeeper.GUI_RESPONSE_EMPTY);
	}
}
