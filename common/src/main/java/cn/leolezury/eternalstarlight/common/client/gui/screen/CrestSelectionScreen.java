package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.CrestButton;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.CrestPageButton;
import cn.leolezury.eternalstarlight.common.client.shader.ESShaders;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateCrestsPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class CrestSelectionScreen extends Screen {
	private final List<CrestButton> crestButtons = new ArrayList<>();
	private final List<Crest.Instance> ownedCrests;
	private List<OpenCrestGuiPacket.CrestInstance> crestIds;
	private CrestButton selectedCrestButton;
	private CrestPageButton nextPage;
	private CrestPageButton previousPage;
	private Crest.Instance selectedCrest;
	private int selected;
	private int tickCount;

	public CrestSelectionScreen(List<OpenCrestGuiPacket.CrestInstance> ownedCrests, List<OpenCrestGuiPacket.CrestInstance> crests) {
		super(Component.empty());
		Registry<Crest> registry;
		if (Minecraft.getInstance().level != null) {
			registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ESRegistries.CREST);
		} else {
			registry = null;
		}
		this.ownedCrests = ownedCrests.stream().map(instance -> registry == null ? null : new Crest.Instance(registry.get(ResourceLocation.read(instance.id()).getOrThrow()), instance.level())).sorted((o1, o2) -> registry == null ? 0 : registry.getId(o1.crest()) - registry.getId(o2.crest())).toList();
		this.crestIds = crests;
	}

	@Override
	protected void init() {
		if (Minecraft.getInstance().level != null) {
			Registry<Crest> registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ESRegistries.CREST);
			this.previousPage = this.addRenderableWidget(new CrestPageButton(this.width / 4 - 24, this.height / 2 - 12 - 50, 48, 24, false, Component.empty(), (button -> previousPage())));
			this.nextPage = this.addRenderableWidget(new CrestPageButton(this.width / 4 - 24, this.height / 2 - 12 + 50, 48, 24, true, Component.empty(), (button -> nextPage())));
			List<Crest.Instance> crests = crestIds == null ? this.crestButtons.stream().map(CrestButton::getCrest).toList() : crestIds.stream().map(instance -> new Crest.Instance(registry.get(ResourceLocation.read(instance.id()).getOrThrow()), instance.level())).toList();
			this.crestButtons.clear();
			for (int i = 0; i < 5; i++) {
				int ordinal = i;
				CrestButton crestButton = this.addRenderableWidget(new CrestButton((int) ((this.width / 9f) * 5f), (int) (this.height / 2f), 72, 72, true, Component.empty(), (button -> cancelCrest(ordinal))).setCrest(i >= crests.size() ? null : crests.get(i)));
				this.crestButtons.add(crestButton);
			}
			this.selectedCrestButton = this.addRenderableWidget(new CrestButton(this.width / 4 - 36, this.height / 2 - 36, 72, 72, Component.empty(), (button -> selectCrest())));
			updateGui();
			crestIds = null;
		} else {
			onClose();
		}
	}

	@Override
	public void tick() {
		for (CrestButton button : crestButtons) {
			button.tick();
		}
		selectedCrestButton.tick();
		nextPage.tick();
		previousPage.tick();
		updateGui();
		tickCount++;
	}

	public void updateGui() {
		if (!ownedCrests.isEmpty()) {
			this.nextPage.active = selected < ownedCrests.size() - 1;
			this.previousPage.active = selected > 0;
			this.selectedCrest = ownedCrests.get(selected);
			this.selectedCrestButton.setCrest(selectedCrest);
		} else {
			this.nextPage.active = false;
			this.previousPage.active = false;
			this.selectedCrest = null;
			this.selectedCrestButton.setCrest(null);
			this.selectedCrestButton.active = false;
		}
		List<CrestButton> notEmptyButtons = crestButtons.stream().filter((crestButton -> !crestButton.isEmpty())).toList();
		for (int n = 0; n < notEmptyButtons.size(); n++) {
			CrestButton crestButton = crestButtons.get(n);
			crestButton.setOrbitCenter((this.width / 9f) * 5f, this.height / 2f);
			crestButton.setAngle((360f / notEmptyButtons.size()) * n + tickCount);
		}
	}

	private void previousPage() {
		if (selected > 0) {
			selected--;
			updateGui();
		}
	}

	private void nextPage() {
		if (selected < ownedCrests.size() - 1) {
			selected++;
			updateGui();
		}
	}

	private void cancelCrest(int ordinal) {
		if (!crestButtons.get(ordinal).isEmpty()) {
			List<Crest.Instance> crests = new ArrayList<>();
			crestButtons.get(ordinal).setCrest(null);
			for (CrestButton crestButton : crestButtons) {
				if (!crestButton.isEmpty()) {
					crests.add(crestButton.getCrest());
				}
			}
			for (int i = 0; i < crestButtons.size(); i++) {
				CrestButton crestButton = crestButtons.get(i);
				crestButton.setCrest(i >= crests.size() ? null : crests.get(i));
			}
		}
	}

	private void selectCrest() {
		if (crestButtons.stream().anyMatch((crestButton -> !crestButton.isEmpty() && crestButton.getCrest().crest().type() == selectedCrest.crest().type()))) {
			return;
		}
		for (CrestButton crestButton : crestButtons) {
			if (crestButton.isEmpty()) {
				crestButton.setCrest(selectedCrest);
				return;
			}
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int i, int j, float f) {
		Minecraft client = Minecraft.getInstance();
		Window window = client.getWindow();
		int x = window.getGuiScaledWidth();
		int y = window.getGuiScaledHeight();
		Matrix4f matrix4f = guiGraphics.pose().last().pose();
		ShaderInstance instance = ESShaders.getCrestSelectionGui();
		if (instance != null) {
			Uniform tickUniform = instance.getUniform("TickCount");
			Uniform ratioUniform = instance.getUniform("Ratio");
			if (tickUniform != null) {
				tickUniform.set((float) tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true));
			}
			if (ratioUniform != null) {
				ratioUniform.set((float) y / x);
			}
		}
		RenderSystem.setShader(ESShaders::getCrestSelectionGui);
		BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.addVertex(matrix4f, 0, 0, 0).setUv(0, 0);
		bufferBuilder.addVertex(matrix4f, 0, y, 0).setUv(0, 1);
		bufferBuilder.addVertex(matrix4f, x, y, 0).setUv(1, 1);
		bufferBuilder.addVertex(matrix4f, x, 0, 0).setUv(1, 0);
		BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
		super.render(guiGraphics, i, j, f);
	}

	@Override
	public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {

	}

	@Override
	public void onClose() {
		super.onClose();
		if (Minecraft.getInstance().level != null) {
			Registry<Crest> registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ESRegistries.CREST);
			List<OpenCrestGuiPacket.CrestInstance> crests = new ArrayList<>();
			for (CrestButton button : crestButtons) {
				if (!button.isEmpty()) {
					crests.add(new OpenCrestGuiPacket.CrestInstance(Objects.requireNonNull(registry.getKey(button.getCrest().crest())).toString(), button.getCrest().level()));
				}
			}
			ESPlatform.INSTANCE.sendToServer(new UpdateCrestsPacket(crests));
		}
	}
}
