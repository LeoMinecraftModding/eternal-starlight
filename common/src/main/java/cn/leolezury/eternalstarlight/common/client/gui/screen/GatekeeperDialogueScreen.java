package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.NpcDialogueChoiceButton;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.NpcDialogueTextWidget;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.network.CloseGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.network.TriggerEntityEventPacket;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

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
				y -= button.getIncrement(width);
				button.reposition(0, y, width);
			}
			y -= this.text.getIncrement(width);
			this.text.reposition(0, y, width);
		}
	}

	private void addChoices(NpcDialogueChoiceButton... buttons) {
		clearWidgets();
		choiceButtons.clear();
		int y = height;
		this.text = this.addRenderableWidget(new NpcDialogueTextWidget(currentText));
		for (int i = buttons.length - 1; i >= 0; i--) {
			choiceButtons.add(addRenderableWidget(buttons[i]));
			y -= buttons[i].getIncrement(width);
			buttons[i].reposition(0, y, width);
		}
		y -= this.text.getIncrement(width);
		this.text.reposition(0, y, width);
		ESClientPlatform.INSTANCE.sendToServer(new TriggerEntityEventPacket(gatekeeper.getId(), TheGatekeeper.EVENT_TALK));
	}

	@Override
	public void tick() {
		if (this.text != null) {
			this.text.tick();
		}
	}

	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private void sendClosePacket(int id) {
		if (!packetSent) ESClientPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(gatekeeper.getId(), id));
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
