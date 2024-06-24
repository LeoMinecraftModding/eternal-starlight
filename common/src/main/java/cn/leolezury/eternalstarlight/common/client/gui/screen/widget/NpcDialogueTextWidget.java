package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

@Environment(EnvType.CLIENT)
public class NpcDialogueTextWidget extends AbstractWidget {
	private int tickCount;

	public NpcDialogueTextWidget(Component text) {
		super(0, 0, 0, 0, text);
	}

	public int getIncrement(int width) {
		List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(getMessage(), width / 5 * 4);
		return Math.max(1, lines.size()) * Minecraft.getInstance().font.lineHeight + 10;
	}

	public void reposition(int x, int y, int width) {
		setRectangle(width, getIncrement(width), x, y);
	}

	public void tick() {
		if (tickCount < getMessage().getString().length()) tickCount++;
	}

	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
		guiGraphics.fillGradient(getX(), getY(), getX() + width, getY() + height, 0x654169e1, 0x654169e1);
		String message = getMessage().getString().substring(0, Math.min(getMessage().getString().length(), tickCount));
		List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(FormattedText.of(message), width / 5 * 4);
		int y = 0;
		for (FormattedCharSequence sequence : lines) {
			guiGraphics.drawCenteredString(Minecraft.getInstance().font, sequence, getX() + width / 2, getY() + y + 5, 0xf2c55c);
			y += Minecraft.getInstance().font.lineHeight;
		}
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
		this.defaultButtonNarrationText(narrationElementOutput);
	}
}
