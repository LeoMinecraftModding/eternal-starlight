package cn.leolezury.eternalstarlight.common.client.gui.screen.button;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import cn.leolezury.eternalstarlight.common.util.GuiUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class CrestButton extends Button {
    private static final int CREST_WIDTH = 72;
    private static final int CREST_HEIGHT = 72;
    private final boolean orbit;
    private Crest crest;

    private int prevHoverProgress;
    private int hoverProgress;

    private float prevX;
    private float prevY;

    private float orbitCenterX;
    private float orbitCenterY;
    private float angle;
    private float prevAngle;

    public CrestButton(int x, int y, int width, int height, Component component, OnPress onPress) {
        this(x, y, width, height, false, component, onPress);
    }

    public CrestButton(int x, int y, int width, int height, boolean orbit, Component component, OnPress onPress) {
        super(x, y, width, height, component, onPress, DEFAULT_NARRATION);
        this.orbit = orbit;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setOrbitCenter(float orbitCenterX, float orbitCenterY) {
        this.orbitCenterX = orbitCenterX;
        this.orbitCenterY = orbitCenterY;
    }

    public CrestButton setCrest(Crest crest) {
        if (this.crest != crest && Minecraft.getInstance().level != null) {
            if (crest == null) {
                setTooltip(Tooltip.create(Component.empty()));
            } else {
                Registry<Crest> registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ESRegistries.CREST);
                MutableComponent nameComponent = Component.translatable(Util.makeDescriptionId("crest", registry.getKey(crest)));
                MutableComponent typeComponent = Component.translatable(Util.makeDescriptionId("mana_type", new ResourceLocation(EternalStarlight.MOD_ID, crest.type().getSerializedName()))).withColor(crest.type().getColor());
                MutableComponent descComponent = Component.translatable(Util.makeDescriptionId("crest", registry.getKey(crest)) + ".desc");
                setTooltip(Tooltip.create(nameComponent.append("\n").append(typeComponent).append("\n").append(descComponent)));
            }
        }
        this.crest = crest;
        return this;
    }

    public Crest getCrest() {
        return crest;
    }

    public boolean isEmpty() {
        return this.crest == null;
    }

    public void tick() {
        prevHoverProgress = hoverProgress;
        prevX = getX();
        prevY = getY();
        prevAngle = angle;
        if (isHovered()) {
            if (hoverProgress < 5) {
                hoverProgress++;
            }
        } else {
            if (hoverProgress > 0) {
                hoverProgress--;
            }
        }
        this.active = crest != null;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        float partialTicks = Minecraft.getInstance().getFrameTime();
        float x, y;
        if (orbit) {
            float currentAngle = Mth.lerp(partialTicks, prevAngle, angle);
            Vec3 centerPos = ESUtil.rotationToPosition(new Vec3(orbitCenterX, 0, orbitCenterY), 60, 0, currentAngle);
            x = (float) (centerPos.x - CREST_WIDTH / 2f);
            y = (float) (centerPos.z - CREST_HEIGHT / 2f);
            setPosition((int) x, (int) y);
        } else {
            x = Mth.lerp(partialTicks, prevX, getX());
            y = Mth.lerp(partialTicks, prevY, getY());
        }
        if (crest != null) {
            float progress = (Mth.lerp(partialTicks, prevHoverProgress, hoverProgress) / 40f) + 1f;
            float width = CREST_WIDTH * progress;
            float height = CREST_HEIGHT * progress;
            GuiUtil.blit(guiGraphics, crest.texture(), (x - (width - getWidth()) / 2f), (y - (height - getHeight()) / 2f), width, height, width, height);
        }
    }
}
