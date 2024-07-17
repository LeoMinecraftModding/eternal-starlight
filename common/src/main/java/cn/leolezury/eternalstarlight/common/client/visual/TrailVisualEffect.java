package cn.leolezury.eternalstarlight.common.client.visual;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@Environment(EnvType.CLIENT)
public class TrailVisualEffect<T extends Entity & TrailOwner> implements WorldVisualEffect {
	private final T entity;
	private final TrailEffect effect;
	private boolean shouldRemove;

	public TrailVisualEffect(Entity entity) {
		if (entity instanceof TrailOwner) {
			this.entity = (T) entity;
			this.effect = ((T) entity).newTrail();
		} else {
			throw new UnsupportedOperationException("Entity using TrailVisualEffect must implement TrailOwner");
		}
	}

	public T getEntity() {
		return entity;
	}

	public static void clientTick(ClientLevel level, List<WorldVisualEffect> visualEffects) {
		for (Entity entity : level.entitiesForRendering()) {
			if (entity instanceof TrailOwner && visualEffects.stream().noneMatch(effect -> effect instanceof TrailVisualEffect<?> trail && trail.getEntity().getUUID().equals(entity.getUUID()))) {
				visualEffects.add(new TrailVisualEffect<>(entity));
			}
		}
	}

	@Override
	public void worldTick() {
		if (Minecraft.getInstance().level == null) {
			shouldRemove = true;
			return;
		}
		boolean shouldRender = false;
		for (Entity toRender : Minecraft.getInstance().level.entitiesForRendering()) {
			if (toRender.getUUID().equals(entity.getUUID())) {
				shouldRender = true;
			}
		}
		if (!shouldRender && !entity.isRemoved()) {
			shouldRemove = true;
			return;
		}
		if (entity.isRemoved() && effect.trailPointsAllSame()) {
			shouldRemove = true;
		}
		entity.updateTrail(effect);
		/*new AdvancedParticleOptions()
			.speed(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, -0.2f, 0.2f, 1),
				SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0, 0.2f, 1),
				SmoothSegmentedValue.of(Easing.IN_OUT_SINE, -0.2f, 0.2f, 1))
			.quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0, 1.5f, 0.6f).add(Easing.IN_OUT_ELASTIC, 1.5f, 0, 0.4f))
			.lifetime((int) (250 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 150))
			.color(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 100 * 0.3f / 255f, 198 * 0.3f / 255f, 1),
				SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 228 * 0.3f / 255f, 124 * 0.3f / 255f, 1),
				SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 254 * 0.3f / 255f, 180 * 0.3f / 255f, 1),
				SmoothSegmentedValue.of(Easing.OUT_QUINT, 0, 1f, 0.7f).add(Easing.IN_OUT_SINE, 1f, 0, 0.3f))
			.defaultOperators()
			.spawn(BuiltInRegistries.PARTICLE_TYPE.getKey(ESParticles.ADVANCED_GLOW.get()), (float) entity.getX(), (float) entity.getY(), (float) entity.getZ());*/
	}

	@Override
	public void render(MultiBufferSource source, PoseStack stack, float partialTicks) {
		boolean entityRemoved = entity.isRemoved();
		if (!entityRemoved && Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()) {
			float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
			float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
			float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
			this.effect.createCurrentPoint(new Vec3(x, y, z).add(0, entity.getBbHeight() / 2, 0), new Vec3(x, y, z).subtract(new Vec3(entity.xOld, entity.yOld, entity.zOld)));
		}

		List<TrailEffect.TrailPoint> adjustedVertical = this.effect.verticalPoints().stream().map(p -> entity.adjustPoint(p, true, partialTicks)).toList();
		this.effect.verticalPoints().clear();
		this.effect.verticalPoints().addAll(adjustedVertical);
		List<TrailEffect.TrailPoint> adjustedHorizontal = this.effect.horizontalPoints().stream().map(p -> entity.adjustPoint(p, false, partialTicks)).toList();
		this.effect.horizontalPoints().clear();
		this.effect.horizontalPoints().addAll(adjustedHorizontal);

		this.effect.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(entity.getTrailRenderType()), stack, partialTicks, true, entity.getTrailColor().x, entity.getTrailColor().y, entity.getTrailColor().z, entity.getTrailColor().w, entity.isTrailFullBright() ? ClientHandlers.FULL_BRIGHT : Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(entity, partialTicks));
		if (entity.shouldRenderHorizontal()) {
			this.effect.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(entity.getTrailRenderType()), stack, partialTicks, false, entity.getTrailColor().x, entity.getTrailColor().y, entity.getTrailColor().z, entity.getTrailColor().w, entity.isTrailFullBright() ? ClientHandlers.FULL_BRIGHT : Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(entity, partialTicks));
		}

		if (!entityRemoved && Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()) {
			this.effect.removeNearest();
		}
	}

	@Override
	public boolean shouldRemove() {
		return shouldRemove;
	}
}
