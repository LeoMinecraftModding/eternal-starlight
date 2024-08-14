package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.ESGuiUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class CrestButton extends Button {
	private static final int CREST_WIDTH = 72;
	private static final int CREST_HEIGHT = 72;
	private final boolean orbit;
	private Crest.Instance crest;

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

	public CrestButton setCrest(Crest.Instance crest) {
		if (this.crest != crest && Minecraft.getInstance().level != null) {
			if (crest == null) {
				setTooltip(Tooltip.create(Component.empty()));
			} else {
				Registry<Crest> registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(ESRegistries.CREST);
				MutableComponent nameComponent = Component.translatable(Util.makeDescriptionId("crest", registry.getKey(crest.crest().value())));
				MutableComponent levelComponent = Component.translatable("enchantment.level." + crest.level());
				MutableComponent typeComponent = Component.translatable(Util.makeDescriptionId("mana_type", EternalStarlight.id(crest.crest().value().type().getSerializedName()))).withColor(crest.crest().value().type().getColor());
				if (crest.crest().value().spell().isPresent()) {
					AbstractSpell spell = crest.crest().value().spell().get();
					MutableComponent spellTypeComponent = Component.translatable("tooltip." + EternalStarlight.ID + ".crest_spell_elements").withStyle(ChatFormatting.AQUA);
					for (ManaType type : spell.spellProperties().types()) {
						spellTypeComponent.append(" ").append(Component.translatable(Util.makeDescriptionId("mana_type", EternalStarlight.id(type.getSerializedName()))).withColor(type.getColor()));
					}
					typeComponent.append("\n").append(spellTypeComponent);
				}
				MutableComponent descComponent = Component.translatable(Util.makeDescriptionId("crest", registry.getKey(crest.crest().value())) + ".desc");
				if (crest.crest().value().attributeModifiers().isEmpty()) {
					descComponent.append("\n").append(Component.translatable("tooltip." + EternalStarlight.ID + ".unwearable").withStyle(ChatFormatting.BLUE));
				}
				MutableComponent merged = nameComponent.append(" ").append(levelComponent).append("\n").append(typeComponent).append("\n").append(descComponent);
				if (crest.crest().value().attributeModifiers().isPresent()) {
					for (Crest.LevelBasedAttributeModifier modifier : crest.crest().value().attributeModifiers().get()) {
						addModifierTooltip((c) -> merged.append("\n").append(c), modifier.attribute(), modifier.getModifier(crest.level()));
					}
				}
				setTooltip(Tooltip.create(merged));
			}
		}
		this.crest = crest;
		return this;
	}

	private void addModifierTooltip(Consumer<Component> consumer, Holder<Attribute> holder, AttributeModifier attributeModifier) {
		double d = attributeModifier.amount();

		double e;
		if (attributeModifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_BASE && attributeModifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
			if (holder.is(Attributes.KNOCKBACK_RESISTANCE)) {
				e = d * 10.0;
			} else {
				e = d;
			}
		} else {
			e = d * 100.0;
		}

		if (d > 0.0) {
			consumer.accept(Component.translatable("attribute.modifier.plus." + attributeModifier.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(holder.value().getDescriptionId())).withStyle(holder.value().getStyle(true)));
		} else if (d < 0.0) {
			consumer.accept(Component.translatable("attribute.modifier.take." + attributeModifier.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-e), Component.translatable(holder.value().getDescriptionId())).withStyle(holder.value().getStyle(false)));
		}
	}

	public Crest.Instance getCrest() {
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
		float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
		float x, y;
		if (orbit) {
			float currentAngle = Mth.lerp(partialTicks, prevAngle, angle);
			Vec3 centerPos = ESMathUtil.rotationToPosition(new Vec3(orbitCenterX, 0, orbitCenterY), 60, 0, currentAngle);
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
			ESGuiUtil.blit(guiGraphics, crest.crest().value().texture(), (x - (width - getWidth()) / 2f), (y - (height - getHeight()) / 2f), width, height, width, height);
		}
	}
}
