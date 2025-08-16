package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.BookProgressButton;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class BookScreen extends Screen {
	public static final int OVERLAY_Z_OFFSET = 770;
	public static final int BUTTON_Z_OFFSET = 780;
	public static final int TOOLTIP_Z_OFFSET = 790;

	private final BookDefinition book;
	private final BookContext context;
	private final List<List<ConfiguredBookComponent<?, ?>>> unlockedComponents;
	private final List<ConfiguredBookComponent<?, ?>> currentComponents = new ArrayList<>();

	private BookProgressButton upButton;
	private BookProgressButton downButton;

	private int tickCount = 0;
	private int totalHeight = 0;
	private int scrollProgress = 0;
	private boolean scrolling = false;
	private int mouseX, mouseY;

	public BookScreen(BookDefinition book, Set<ResourceLocation> unlocked) {
		super(Component.empty());
		this.book = book;
		this.unlockedComponents = new ArrayList<>();
		for (List<ConfiguredBookComponent<?, ?>> innerList : book.components()) {
			this.unlockedComponents.add(new ArrayList<>(innerList));
		}
		this.unlockedComponents.forEach(list -> list.removeIf(component -> !component.isEnabled(unlocked)));
		this.unlockedComponents.removeIf(List::isEmpty);
		if (!this.unlockedComponents.isEmpty()) {
			this.currentComponents.addAll(this.unlockedComponents.getFirst());
		}
		this.context = new BookContext() {
			@Override
			public int getMouseX() {
				return mouseX;
			}

			@Override
			public int getMouseY() {
				return mouseY;
			}

			@Override
			public Font getFont() {
				return font;
			}

			@Override
			public BookDefinition getBookDefinition() {
				return book;
			}

			@Override
			public int getContentX() {
				return BookScreen.this.getContentX();
			}

			@Override
			public int getContentY() {
				return BookScreen.this.getContentY();
			}

			@Override
			public int getTickCount() {
				return tickCount;
			}

			@Override
			public boolean isComponentEnabled(ResourceLocation id) {
				return unlockedComponents.stream().flatMap(List::stream).anyMatch(component -> component.config().id().equals(id));
			}

			@Override
			public void jumpToComponent(ResourceLocation id) {
				for (List<ConfiguredBookComponent<?, ?>> list : unlockedComponents) {
					int currentHeight = 0;
					for (ConfiguredBookComponent<?, ?> component : list) {
						if (component.config().id().equals(id)) {
							currentComponents.clear();
							currentComponents.addAll(list);
							init();
							setScrollProgress(currentHeight);
							break;
						}
						currentHeight += component.getTotalHeight(context);
					}
				}
			}
		};
	}

	@Override
	protected void init() {
		totalHeight = 0;
		for (ConfiguredBookComponent<?, ?> component : currentComponents) {
			totalHeight += component.getTotalHeight(context);
		}
		upButton = addRenderableWidget(new BookProgressButton(getBaseX() + book.width() - book.buttonWidth() - book.buttonDistanceFromRight(), getBaseY() + book.upButtonOffset(), book, false, button -> {
			setScrollProgress(scrollProgress - font.lineHeight);
		}));
		downButton = addRenderableWidget(new BookProgressButton(getBaseX() + book.width() - book.buttonWidth() - book.buttonDistanceFromRight(), getBaseY() + book.downButtonOffset(), book, true, button -> {
			setScrollProgress(scrollProgress + font.lineHeight);
		}));
		updateButtonVisibility();
	}

	@Override
	public void mouseMoved(double x, double y) {
		super.mouseMoved(x, y);
		mouseX = (int) x;
		mouseY = (int) y;
	}

	@Override
	public boolean mouseClicked(double x, double y, int button) {
		if (x >= getContentX() && x <= getContentX() + book.width() - 2 * book.frameWidth()) {
			int startHeight = 0;
			for (ConfiguredBookComponent<?, ?> component : new ArrayList<>(currentComponents)) {
				int currentY = getContentY() - scrollProgress + startHeight;
				if (y >= currentY && y <= currentY + component.getTotalHeight(context)) {
					component.onClick(context, getContentX(), getContentY() - scrollProgress + startHeight);
				}
				startHeight += component.getTotalHeight(context);
			}
		}
		boolean scrollArea = x >= getBaseX() + book.scrollbarXOffset()
			&& x <= getBaseX() + book.scrollbarXOffset() + book.scrollbarWidth()
			&& y >= getBaseY() + book.scrollbarYOffset()
			&& y <= getBaseY() + book.scrollbarYOffset() + book.scrollbarHeight();
		if (scrollArea && button == 0) {
			this.scrolling = true;
			return true;
		}
		return super.mouseClicked(x, y, button);
	}

	@Override
	public boolean mouseReleased(double x, double y, int button) {
		if (button == 0) {
			this.scrolling = false;
		}

		return super.mouseReleased(x, y, button);
	}

	@Override
	public boolean mouseDragged(double x, double y, int button, double dragX, double dragY) {
		if (this.scrolling) {
			/*if (y < getBaseY() + book.scrollbarYOffset()) {
				scrollProgress = 0;
			} else if (mouseY > getBaseY() + book.scrollbarYOffset() + book.scrollbarHeight()) {
				scrollProgress = totalHeight - book.height() + 2 * book.frameWidth();
			} else {
				setScrollProgress((int) ((y - getBaseY() - book.scrollbarYOffset()) * (double) (totalHeight - book.height() + 2 * book.frameWidth()) / (double) getMaxScroll()));
			}*/
			setScrollProgress((int) ((y - getBaseY() - book.scrollbarYOffset()) * (double) (totalHeight - book.height() + 2 * book.frameWidth()) / (double) getMaxScroll()));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean mouseScrolled(double x, double y, double scrollX, double scrollY) {
		setScrollProgress(scrollProgress - (int) (scrollY * font.lineHeight));
		return true;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		boolean up = keyCode == GLFW.GLFW_KEY_UP;
		boolean down = keyCode == GLFW.GLFW_KEY_DOWN;
		if (up || down) {
			setScrollProgress(scrollProgress + (up ? -1 : 1) * font.lineHeight);
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public void tick() {
		tickCount++;
		int startHeight = 0;
		for (ConfiguredBookComponent<?, ?> component : new ArrayList<>(currentComponents)) {
			component.tick(context, getContentX(), getContentY() - scrollProgress + startHeight);
			startHeight += component.getTotalHeight(context);
		}
		updateButtonVisibility();
	}

	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.blit(book.textures().background(), getBaseX(), getBaseY(), 0, 0, book.width(), book.height(), book.width(), book.height());
		guiGraphics.enableScissor(getContentX(), getContentY(), getContentX() + book.width() - 2 * book.frameWidth(), getContentY() + book.height() - 2 * book.frameWidth());
		int startHeight = 0;
		for (ConfiguredBookComponent<?, ?> component : new ArrayList<>(currentComponents)) {
			if (getContentY() - scrollProgress + startHeight < getContentY() + book.height() - 2 * book.frameWidth()
				&& getContentY() - scrollProgress + startHeight + component.getTotalHeight(context) > getContentY()) {
				component.render(context, guiGraphics, getContentX(), getContentY() - scrollProgress + startHeight);
			}
			startHeight += component.getTotalHeight(context);
		}
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(0.0, 0.0, OVERLAY_Z_OFFSET);
		guiGraphics.disableScissor();
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		guiGraphics.blit(book.textures().overlay(), getBaseX(), getBaseY(), 0, 0, book.width(), book.height(), book.width(), book.height());
		RenderSystem.disableBlend();
		RenderSystem.disableDepthTest();
		RenderSystem.defaultBlendFunc();
		guiGraphics.pose().popPose();
		if (getScrollButtonHeight() < book.scrollbarHeight()) {
			int scrollButtonX = getBaseX() + book.scrollbarXOffset() + (book.scrollbarWidth() - book.scrollButtonWidth()) / 2;
			int scrollButtonY = getBaseY() + book.scrollbarYOffset() + (int) (getMaxScroll() * ((double) scrollProgress / (double) (totalHeight - book.height() + 2 * book.frameWidth())));
			guiGraphics.pose().pushPose();
			guiGraphics.pose().translate(0.0, 0.0, BUTTON_Z_OFFSET);
			guiGraphics.fill(scrollButtonX, scrollButtonY, scrollButtonX + book.scrollButtonWidth(), scrollButtonY + getScrollButtonHeight(), book.scrollButtonColor());
			guiGraphics.pose().popPose();
		}
		startHeight = 0;
		for (ConfiguredBookComponent<?, ?> component : new ArrayList<>(currentComponents)) {
			if (getContentY() - scrollProgress + startHeight < getContentY() + book.height() - 2 * book.frameWidth()
				&& getContentY() - scrollProgress + startHeight + component.getTotalHeight(context) > getContentY()) {
				component.renderDelayed(context, guiGraphics, getContentX(), getContentY() - scrollProgress + startHeight);
			}
			startHeight += component.getTotalHeight(context);
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private void updateButtonVisibility() {
		upButton.visible = scrollProgress > 0;
		downButton.visible = scrollProgress < totalHeight - book.height() + 2 * book.frameWidth();
		if (totalHeight <= book.height() - 2 * book.frameWidth()) {
			upButton.visible = false;
			downButton.visible = false;
		}
	}

	private int getBaseX() {
		return (width - book.width()) / 2;
	}

	private int getContentX() {
		return getBaseX() + book.frameWidth();
	}

	private int getBaseY() {
		return (height - book.height()) / 2;
	}

	private int getContentY() {
		return getBaseY() + book.frameWidth();
	}

	private void setScrollProgress(int progress) {
		this.scrollProgress = Mth.clamp(progress, 0, Math.max(totalHeight - book.height() + 2 * book.frameWidth(), 0));
	}

	private int getMaxScroll() {
		return book.scrollbarHeight() - getScrollButtonHeight();
	}

	private int getScrollButtonHeight() {
		return Math.clamp((int) (book.scrollbarHeight() * (double) (book.height() - 2 * book.frameWidth()) / (double) totalHeight), book.scrollButtonWidth(), book.scrollbarHeight());
	}
}
