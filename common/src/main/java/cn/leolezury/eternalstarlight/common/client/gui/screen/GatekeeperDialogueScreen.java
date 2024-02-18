package cn.leolezury.eternalstarlight.common.client.gui.screen;

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

    public GatekeeperDialogueScreen(TheGatekeeper gatekeeper) {
        super(Component.empty());
        this.gatekeeper = gatekeeper;
    }

    @Override
    protected void init() {
        if (currentText == null) {
            currentText = Component.literal("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        }
        if (this.choiceButtons.isEmpty()) {
            addChoices(
                    new NpcDialogueChoiceButton(Component.literal("qwertyu"), button -> Minecraft.getInstance().player.sendSystemMessage(Component.literal("lol"))),
                    new NpcDialogueChoiceButton(Component.literal("abcdefg"), button -> {
                        currentText = Component.literal("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        addChoices(
                                new NpcDialogueChoiceButton(Component.literal("option3"), button1 -> Minecraft.getInstance().player.sendSystemMessage(Component.literal("u choose option 3"))),
                                new NpcDialogueChoiceButton(Component.literal("option4"), button1 -> Minecraft.getInstance().player.sendSystemMessage(Component.literal("u choose option 4")))
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
    public void onClose() {
        super.onClose();
        ESPlatform.INSTANCE.sendToServer(new CloseGatekeeperGuiPacket(gatekeeper.getId(), 0));
    }
}
