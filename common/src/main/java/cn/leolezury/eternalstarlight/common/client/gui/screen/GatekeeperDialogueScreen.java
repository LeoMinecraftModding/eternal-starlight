package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.NpcDialogueChoiceButton;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.NpcDialogueTextWidget;
import cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper.TheGatekeeper;
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
    private final boolean killedDragon;
    private final boolean challenged;
    private boolean packetSent = false;

    public GatekeeperDialogueScreen(TheGatekeeper gatekeeper, boolean killedDragon, boolean challenged) {
        super(Component.empty());
        this.gatekeeper = gatekeeper;
        this.killedDragon = killedDragon;
        this.challenged = challenged;
    }

    @Override
    protected void init() {
        if (currentText == null) {
            currentText = challenged ? Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_give_orb") : (killedDragon ? Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_killed_dragon_hello") : Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_hello"));
        }
        if (this.choiceButtons.isEmpty()) {
            if (challenged) {
                addChoices(new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_ok"), button -> {
                    ESPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(gatekeeper.getId(), 2));
                    packetSent = true;
                    Minecraft.getInstance().setScreen(null);
                }));
            } else {
                addChoices(
                        new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_nothing"), button -> {
                            currentText = Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_bye");
                            addChoices(new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_ok"), button1 -> onClose()));
                        }),
                        new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_ask_portal"), button -> {
                            currentText = Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_portal_intro");
                            addChoices(
                                    new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_prove_myself"), button1 -> {
                                        if (killedDragon) {
                                            currentText = Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_praise");
                                            addChoices(new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_ok"), button2 -> {
                                                ESPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(gatekeeper.getId(), 2));
                                                packetSent = true;
                                                Minecraft.getInstance().setScreen(null);
                                            }));
                                        } else {
                                            currentText = Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_fight_intro");
                                            addChoices(
                                                    new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_accept_fight"), button2 -> {
                                                        ESPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(gatekeeper.getId(), 1));
                                                        packetSent = true;
                                                        Minecraft.getInstance().setScreen(null);
                                                    }),
                                                    new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_deny_fight"), button2 -> {
                                                        currentText = Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_wise_choice");
                                                        addChoices(new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_ok"), button3 -> onClose()));
                                                    })
                                            );
                                        }
                                    }),
                                    new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_fun_to_know"), button1 -> {
                                        currentText = Component.translatable("message." + EternalStarlight.MOD_ID + (killedDragon ? ".gatekeeper_pity" : ".gatekeeper_wise_choice"));
                                        addChoices(new NpcDialogueChoiceButton(Component.translatable("message." + EternalStarlight.MOD_ID + ".gatekeeper_ok"), button2 -> onClose()));
                                    })
                            );
                        })
                );
            }
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

    @Override
    public void removed() {
        if (!packetSent) ESPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(gatekeeper.getId(), 0));
        packetSent = true;
    }

    @Override
    public void onClose() {
        super.onClose();
        if (!packetSent) ESPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(gatekeeper.getId(), 0));
        packetSent = true;
    }
}
