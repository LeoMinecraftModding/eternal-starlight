package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.client.posteffect.SeekingEyeOverlay;
import cn.leolezury.eternalstarlight.common.item.misc.SeekingEyeTargets;
import cn.leolezury.eternalstarlight.common.network.SelectSeekingEyeTargetPacket;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.Easing;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SeekingEyeScreen extends Screen {
	public static final int MAX_STARS = 64;
	private static final int MAX_ENTRIES_PER_RING = 12;

	private static final float TRANSITION_DURATION = 0.35F;
	private static final float CLOSE_DURATION = 0.2F;
	private static final float ENTER_STAGGER = 0.03F;
	private static final float ENTER_DELAY_FRACTION = 0.35F;
	private static final float ENTER_STAGGER_CAP = 0.2F;
	private static final float HOVER_LERP_SPEED = 12.0F;
	private static final float HOVER_SCALE = 1.1F;
	private static final float FLY_OUT_DISTANCE = 90.0F;

	private static final float CENTER_RADIUS = 11.0F;
	private static final float CATEGORY_RADIUS = 8.0F;
	private static final float ENTRY_RADIUS = 6.5F;

	private static final int COLOR_CENTER = 0xF7D488;

	private static final float[] STAR_POSITIONS = new float[MAX_STARS * 2];
	private static final float[] STAR_RADII = new float[MAX_STARS];
	private static final float[] STAR_COLORS = new float[MAX_STARS * 3];
	private static final float[] STAR_ALPHAS = new float[MAX_STARS];
	private static final float[] STAR_PHASES = new float[MAX_STARS];
	private static final float[] STAR_ROTATIONS = new float[MAX_STARS];
	private static final float[] STAR_RAY_COUNTS = new float[MAX_STARS];
	private static final float[] STAR_RAY_LENGTHS = new float[MAX_STARS];

	private enum State {
		MAIN, CATEGORY
	}

	private enum Role {
		CENTER, CATEGORY, ENTRY
	}

	private enum Behavior {
		NONE, OPEN_CATEGORY, BACK, SELECT_ENTRY
	}

	private record StarVisuals(float phase, float rotation, float rayCount, float rayLength, float sizeScale) {
		static final StarVisuals EYE = new StarVisuals(2.0F, 0.0F, 4.0F, 1.4F, 1.0F);

		static StarVisuals random(RandomSource random) {
			return new StarVisuals(random.nextFloat() * 6.2832F, random.nextFloat() * 6.2832F, 2.0F + random.nextInt(5), 0.9F + random.nextFloat() * 0.9F, 0.85F + random.nextFloat() * 0.3F);
		}
	}

	private static final class Star {
		// current interpolated state
		float x, y, radius, alpha;
		// source and target
		float fromX, fromY, fromRadius, fromAlpha;
		float toX, toY, toRadius, toAlpha;
		float animStart, animDuration;
		boolean removeAfterAnim;
		final Role role;
		final int layoutRing;
		final int layoutIndex;
		final int layoutTotal;
		final int color;
		final StarVisuals visuals;
		final Component label;
		Behavior behavior;
		final SeekingEyeTargets.TargetType category;
		final SeekingEyeTargets.Entry entry;
		float hoverProgress;

		Star(Role role, int layoutRing, int layoutIndex, int layoutTotal, int color, StarVisuals visuals, Component label, Behavior behavior, SeekingEyeTargets.TargetType category, SeekingEyeTargets.Entry entry) {
			this.role = role;
			this.layoutRing = layoutRing;
			this.layoutIndex = layoutIndex;
			this.layoutTotal = layoutTotal;
			this.color = color;
			this.visuals = visuals;
			this.label = label;
			this.behavior = behavior;
			this.category = category;
			this.entry = entry;
		}

		void update(float time) {
			float progress = Mth.clamp((time - animStart) / animDuration, 0.0F, 1.0F);
			float eased = Easing.IN_OUT_CUBIC.calculate(progress);
			x = Mth.lerp(eased, fromX, toX);
			y = Mth.lerp(eased, fromY, toY);
			radius = Mth.lerp(eased, fromRadius, toRadius);
			alpha = Mth.lerp(eased, fromAlpha, toAlpha);
		}
	}

	private State state = State.MAIN;
	private SeekingEyeTargets.TargetType currentCategory;
	private int entryRingCount = 1;
	private final List<Star> stars = new ArrayList<>();
	private final RandomSource random = RandomSource.create();
	private float animTime;
	private float lastFrameTime = -1.0F;
	private float frameDelta;
	private boolean closing;
	private float closeStart;
	private boolean packetSent;
	private int hoveredIndex = -1;
	private boolean keyboardActive;
	private int focusLevel;
	private int focusIndex;
	private int lastMouseX;
	private int lastMouseY;

	public SeekingEyeScreen() {
		super(ESItems.SEEKING_EYE.get().getDescription());
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
	}

	@Override
	protected void init() {
		if (stars.isEmpty()) {
			animTime = 0;
			buildMainStars();
		} else {
			for (Star star : stars) {
				if (star.removeAfterAnim) {
					continue;
				}
				Vec2 pos = starLayoutPosition(star);
				float radius = starLayoutRadius(star);
				star.fromX = star.toX = star.x = pos.x;
				star.fromY = star.toY = star.y = pos.y;
				star.fromRadius = star.toRadius = star.radius = radius;
				star.fromAlpha = star.toAlpha = star.alpha = 1.0F;
			}
		}
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		lastMouseX = mouseX;
		lastMouseY = mouseY;
		advanceTime();
		updateStars();
		hoveredIndex = transitioning() ? -1 : (keyboardActive ? keyboardSelectedIndex() : pickHoveredStar(mouseX, mouseY));
		fillStarData();
		graphics.flush();
		float fade = closing ? Mth.clamp(1.0F - (animTime - closeStart) / CLOSE_DURATION, 0.0F, 1.0F) : 1.0F;
		RenderSystem.disableDepthTest();
		SeekingEyeOverlay.render(STAR_POSITIONS, STAR_RADII, STAR_COLORS, STAR_ALPHAS, STAR_PHASES, STAR_ROTATIONS, STAR_RAY_COUNTS, STAR_RAY_LENGTHS, Math.min(stars.size(), MAX_STARS), hoveredIndex, fade, animTime, partialTick);
		RenderSystem.enableDepthTest();
		graphics.flush();
		if (hoveredIndex >= 0 && hoveredIndex < stars.size()) {
			Star star = stars.get(hoveredIndex);
			if (star.label != null) {
				graphics.renderTooltip(font, star.label, (int) star.x + 14, (int) star.y - 8);
			}
		}
		if (closing && animTime - closeStart > CLOSE_DURATION + 0.05F && minecraft != null) {
			minecraft.setScreen(null);
		}
	}

	@Override
	public void removed() {
		SeekingEyeOverlay.release();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (!closing && !transitioning()) {
			boolean up = keyCode == GLFW.GLFW_KEY_UP;
			boolean down = keyCode == GLFW.GLFW_KEY_DOWN;
			boolean left = keyCode == GLFW.GLFW_KEY_LEFT;
			boolean right = keyCode == GLFW.GLFW_KEY_RIGHT;
			if (up || down || left || right) {
				Star hovered = hoveredStarAtMouse();
				boolean adopting = hovered != null && !keyboardActive;
				keyboardActive = true;
				if (adopting && !isCenter(hovered)) {
					focusLevel = levelOf(hovered);
					focusIndex = hovered.layoutIndex;
				}
				if (left || right) {
					moveWithinRing(right ? 1 : -1);
				} else {
					switchLevel(down ? 1 : -1);
				}
				return true;
			}
			if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
				Star target = keyboardActive ? selectedStar() : hoveredStarAtMouse();
				if (target != null) {
					performBehavior(target);
					return true;
				}
			}
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button != 0 || closing || transitioning()) {
			return true;
		}
		Star clicked = pickStarAt(mouseX, mouseY);
		if (clicked != null) {
			performBehavior(clicked);
		}
		return true;
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		super.mouseMoved(mouseX, mouseY);
		lastMouseX = (int) mouseX;
		lastMouseY = (int) mouseY;
		if (keyboardActive && pickStarAt(mouseX, mouseY) != null) {
			keyboardActive = false;
		}
	}

	private void performBehavior(Star star) {
		switch (star.behavior) {
			case OPEN_CATEGORY -> enterCategory(star.category);
			case BACK -> backToMain();
			case SELECT_ENTRY -> selectTarget(star);
			default -> {
			}
		}
	}

	private boolean isCenter(Star star) {
		return state == State.CATEGORY ? star.role == Role.CATEGORY && star.behavior == Behavior.BACK : star.role == Role.CENTER;
	}

	private boolean isRingStar(Star star) {
		return state == State.CATEGORY ? star.role == Role.ENTRY : star.role == Role.CATEGORY;
	}

	private Star centerStar() {
		for (Star star : stars) {
			if (isCenter(star)) {
				return star;
			}
		}
		return null;
	}

	private int levelOf(Star star) {
		return isCenter(star) ? 0 : star.layoutRing + 1;
	}

	private int viewLevelCount() {
		return state == State.CATEGORY ? entryRingCount + 1 : 2;
	}

	private int ringSize(int ring) {
		return ringStars(ring).size();
	}

	private List<Star> ringStars(int ring) {
		List<Star> ringStars = new ArrayList<>();
		for (Star star : stars) {
			if (star.alpha < 0.25F || star.removeAfterAnim || !isRingStar(star) || star.layoutRing != ring) {
				continue;
			}
			ringStars.add(star);
		}
		ringStars.sort(Comparator.comparingInt(star -> star.layoutIndex));
		return ringStars;
	}

	private void enterCategory(SeekingEyeTargets.TargetType category) {
		if (transitioning()) {
			return;
		}
		state = State.CATEGORY;
		currentCategory = category;
		resetKeyboard();
		float centerX = width / 2.0F;
		float centerY = height / 2.0F;
		for (Star star : stars) {
			if (star.role == Role.CENTER) {
				startTween(star, centerX, centerY, 0.5F, 0.0F, animTime, TRANSITION_DURATION, false);
			} else if (star.role == Role.CATEGORY && star.category == category) {
				star.behavior = Behavior.BACK;
				startTween(star, centerX, centerY, CENTER_RADIUS, 1.0F, animTime, TRANSITION_DURATION, false);
			} else {
				flyOutAndRemove(star, centerX, centerY);
			}
		}
		buildEntryStars();
	}

	private void backToMain() {
		if (transitioning()) {
			return;
		}
		SeekingEyeTargets.TargetType returningCategory = currentCategory;
		state = State.MAIN;
		currentCategory = null;
		resetKeyboard();
		float centerX = width / 2.0F;
		float centerY = height / 2.0F;
		for (Star star : stars) {
			if (star.role == Role.CENTER) {
				startTween(star, centerX, centerY, CENTER_RADIUS, 1.0F, animTime, TRANSITION_DURATION, false);
			} else if (star.role == Role.CATEGORY && star.behavior == Behavior.BACK) {
				Vec2 pos = ringPosition(star.layoutIndex, star.layoutTotal, centerX, centerY, mainRingRadius());
				star.behavior = Behavior.OPEN_CATEGORY;
				startTween(star, pos.x, pos.y, CATEGORY_RADIUS, 1.0F, animTime, TRANSITION_DURATION, false);
			} else if (star.role == Role.ENTRY) {
				flyOutAndRemove(star, centerX, centerY);
			}
		}
		SeekingEyeTargets.TargetType[] types = SeekingEyeTargets.TargetType.values();
		for (int i = 0; i < types.length; i++) {
			if (types[i] == returningCategory) {
				continue;
			}
			Vec2 pos = ringPosition(i, types.length, centerX, centerY, mainRingRadius());
			createStar(Role.CATEGORY, 0, i, types.length, types[i].getColor(), StarVisuals.random(random), types[i].getLabel(), Behavior.OPEN_CATEGORY, types[i], null, pos.x, pos.y, CATEGORY_RADIUS, (i + 1) * ENTER_STAGGER);
		}
	}

	private void selectTarget(Star star) {
		if (packetSent) {
			return;
		}
		packetSent = true;
		ESClientPlatform.INSTANCE.sendToServer(new SelectSeekingEyeTargetPacket(currentCategory, star.entry.key().location()));
		closing = true;
		closeStart = animTime;
		for (Star s : stars) {
			startTween(s, s.x, s.y, 0.0F, 0.0F, animTime, CLOSE_DURATION, true);
		}
	}

	private void buildMainStars() {
		stars.clear();
		entryRingCount = 1;
		float centerX = width / 2.0F;
		float centerY = height / 2.0F;
		createStar(Role.CENTER, -1, 0, 1, COLOR_CENTER, StarVisuals.EYE, Component.translatable(ESItems.SEEKING_EYE.get().getDescriptionId()), Behavior.NONE, null, null, centerX, centerY, CENTER_RADIUS, 0.0F);
		SeekingEyeTargets.TargetType[] types = SeekingEyeTargets.TargetType.values();
		for (int i = 0; i < types.length; i++) {
			Vec2 pos = ringPosition(i, types.length, centerX, centerY, mainRingRadius());
			createStar(Role.CATEGORY, 0, i, types.length, types[i].getColor(), StarVisuals.random(random), types[i].getLabel(), Behavior.OPEN_CATEGORY, types[i], null, pos.x, pos.y, CATEGORY_RADIUS, (i + 1) * ENTER_STAGGER);
		}
	}

	private void buildEntryStars() {
		List<SeekingEyeTargets.Entry> entries = currentCategory.getEntries();
		int ringCount = (entries.size() + MAX_ENTRIES_PER_RING - 1) / MAX_ENTRIES_PER_RING;
		entryRingCount = ringCount;
		float centerX = width / 2.0F;
		float centerY = height / 2.0F;
		int offset = 0;
		for (int ring = 0; ring < ringCount; ring++) {
			int base = entries.size() / ringCount;
			int extra = entries.size() % ringCount;
			int size = base + (ring >= ringCount - extra ? 1 : 0);
			float radius = entryRingRadius() * (ring + 1) / ringCount;
			for (int slot = 0; slot < size; slot++) {
				SeekingEyeTargets.Entry entry = entries.get(offset + slot);
				Vec2 pos = ringPosition(slot, size, centerX, centerY, radius);
				float delay = TRANSITION_DURATION * ENTER_DELAY_FRACTION + Math.min((offset + slot) * ENTER_STAGGER, ENTER_STAGGER_CAP);
				createStar(Role.ENTRY, ring, slot, size, entry.color(), StarVisuals.random(random), entry.label(), Behavior.SELECT_ENTRY, null, entry, pos.x, pos.y, ENTRY_RADIUS, delay);
			}
			offset += size;
		}
	}

	private void createStar(Role role, int layoutRing, int layoutIndex, int layoutTotal, int color, StarVisuals visuals, Component label, Behavior behavior, SeekingEyeTargets.TargetType category, SeekingEyeTargets.Entry entry, float toX, float toY, float toRadius, float delay) {
		Star star = new Star(role, layoutRing, layoutIndex, layoutTotal, color, visuals, label, behavior, category, entry);
		star.fromX = width / 2.0F;
		star.fromY = height / 2.0F;
		star.fromRadius = 2.5F;
		star.fromAlpha = 0.35F;
		star.toX = toX;
		star.toY = toY;
		star.toRadius = toRadius;
		star.toAlpha = 1.0F;
		star.animStart = animTime + delay;
		star.animDuration = TRANSITION_DURATION;
		star.update(animTime);
		stars.add(star);
	}

	private Vec2 starLayoutPosition(Star star) {
		float centerX = width / 2.0F;
		float centerY = height / 2.0F;
		if (state == State.MAIN) {
			if (star.role == Role.CENTER) {
				return new Vec2(centerX, centerY);
			}
			if (star.role == Role.CATEGORY) {
				return ringPosition(star.layoutIndex, star.layoutTotal, centerX, centerY, mainRingRadius());
			}
		} else {
			if (isCenter(star)) {
				return new Vec2(centerX, centerY);
			}
			if (star.role == Role.ENTRY) {
				return ringPosition(star.layoutIndex, star.layoutTotal, centerX, centerY, ringRadius(star.layoutRing));
			}
		}
		return new Vec2(centerX, centerY);
	}

	private float starLayoutRadius(Star star) {
		if (star.role == Role.CENTER) {
			return state == State.CATEGORY ? 0.5F : CENTER_RADIUS;
		}
		if (star.role == Role.CATEGORY) {
			return isCenter(star) ? CENTER_RADIUS : CATEGORY_RADIUS;
		}
		return ENTRY_RADIUS;
	}

	private float ringRadius(int ring) {
		return entryRingRadius() * (ring + 1) / entryRingCount;
	}

	private static Vec2 ringPosition(int index, int total, float centerX, float centerY, float radius) {
		double angle = -Math.PI / 2 + index * 2.0 * Math.PI / total;
		return new Vec2((float) (centerX + Math.cos(angle) * radius), (float) (centerY + Math.sin(angle) * radius));
	}

	private float mainRingRadius() {
		return Math.min(width, height) * 0.24F;
	}

	private float entryRingRadius() {
		return Math.min(width, height) * 0.32F;
	}

	private void resetKeyboard() {
		keyboardActive = false;
		focusLevel = 0;
		focusIndex = 0;
	}

	private void moveWithinRing(int dir) {
		if (focusLevel == 0) {
			focusLevel = 1;
		}
		int size = ringSize(focusLevel - 1);
		if (size > 0) {
			focusIndex = Math.min(focusIndex, size - 1);
			focusIndex = Math.floorMod(focusIndex + dir, size);
		}
	}

	private void switchLevel(int dir) {
		focusLevel = Math.floorMod(focusLevel + dir, viewLevelCount());
		if (focusLevel > 0) {
			focusIndex = Math.clamp(ringSize(focusLevel - 1) - 1, 0, focusIndex);
		}
	}

	private Star selectedStar() {
		if (focusLevel == 0) {
			return centerStar();
		}
		List<Star> ring = ringStars(focusLevel - 1);
		if (focusIndex < ring.size()) {
			return ring.get(Math.max(focusIndex, 0));
		}
		return null;
	}

	private int keyboardSelectedIndex() {
		Star selected = selectedStar();
		return selected == null ? -1 : stars.indexOf(selected);
	}

	private void advanceTime() {
		long now = Util.getMillis();
		if (lastFrameTime < 0) {
			lastFrameTime = now;
		}
		frameDelta = Math.min((now - lastFrameTime) / 1000.0F, 0.1F);
		lastFrameTime = now;
		animTime += frameDelta;
	}

	private void updateStars() {
		stars.removeIf(star -> star.removeAfterAnim && star.animStart + star.animDuration <= animTime);
		for (Star star : stars) {
			star.update(animTime);
		}
	}

	private boolean transitioning() {
		for (Star star : stars) {
			if (star.animStart + star.animDuration > animTime) {
				return true;
			}
		}
		return false;
	}

	private static void startTween(Star star, float toX, float toY, float toRadius, float toAlpha, float start, float duration, boolean removeAfterAnim) {
		star.fromX = star.x;
		star.fromY = star.y;
		star.fromRadius = star.radius;
		star.fromAlpha = star.alpha;
		star.toX = toX;
		star.toY = toY;
		star.toRadius = toRadius;
		star.toAlpha = toAlpha;
		star.animStart = start;
		star.animDuration = duration;
		star.removeAfterAnim = removeAfterAnim;
	}

	private void flyOutAndRemove(Star star, float centerX, float centerY) {
		Vec2 dir = directionAwayFromCenter(star.x, star.y, centerX, centerY);
		startTween(star, star.x + dir.x * FLY_OUT_DISTANCE, star.y + dir.y * FLY_OUT_DISTANCE, 0.0F, 0.0F, animTime, TRANSITION_DURATION, true);
	}

	private static Vec2 directionAwayFromCenter(float x, float y, float centerX, float centerY) {
		float dx = x - centerX;
		float dy = y - centerY;
		float len = (float) Math.sqrt(dx * dx + dy * dy);
		if (len < 1.0E-3F) {
			return new Vec2(0.0F, 1.0F);
		}
		return new Vec2(dx / len, dy / len);
	}

	private int pickHoveredStar(double mouseX, double mouseY) {
		Star star = pickStarAt(mouseX, mouseY);
		return star == null ? -1 : stars.indexOf(star);
	}

	private Star hoveredStarAtMouse() {
		return pickStarAt(lastMouseX, lastMouseY);
	}

	private Star pickStarAt(double mouseX, double mouseY) {
		for (int i = stars.size() - 1; i >= 0; i--) {
			Star star = stars.get(i);
			if (star.alpha < 0.25F || star.removeAfterAnim) {
				continue;
			}
			double dist = Math.sqrt((mouseX - star.x) * (mouseX - star.x) + (mouseY - star.y) * (mouseY - star.y));
			if (dist <= star.radius * 2.0) {
				return star;
			}
		}
		return null;
	}

	private void fillStarData() {
		int count = Math.min(stars.size(), MAX_STARS);
		float guiScale = minecraft == null ? 1 : (float) minecraft.getWindow().getGuiScale();
		for (int i = 0; i < count; i++) {
			Star star = stars.get(i);
			float target = i == hoveredIndex ? 1.0F : 0.0F;
			star.hoverProgress += (target - star.hoverProgress) * Math.min(1.0F, frameDelta * HOVER_LERP_SPEED);
			float scale = 1.0F + star.hoverProgress * (HOVER_SCALE - 1.0F);
			STAR_POSITIONS[i * 2] = star.x * guiScale;
			STAR_POSITIONS[i * 2 + 1] = (height - star.y) * guiScale;
			STAR_RADII[i] = star.radius * scale * guiScale * star.visuals.sizeScale;
			int colorBase = i * 3;
			STAR_COLORS[colorBase] = ((star.color >> 16) & 0xFF) / 255.0F;
			STAR_COLORS[colorBase + 1] = ((star.color >> 8) & 0xFF) / 255.0F;
			STAR_COLORS[colorBase + 2] = (star.color & 0xFF) / 255.0F;
			STAR_ALPHAS[i] = star.alpha;
			STAR_PHASES[i] = star.visuals.phase;
			float rotation = star.visuals.rotation;
			if (star.role == Role.CENTER) {
				rotation += animTime * 0.25F;
			}
			STAR_ROTATIONS[i] = rotation;
			STAR_RAY_COUNTS[i] = star.visuals.rayCount;
			STAR_RAY_LENGTHS[i] = star.visuals.rayLength;
		}
	}
}
