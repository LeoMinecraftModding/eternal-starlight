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
			if (entity instanceof TrailOwner && entity.tickCount > 2 && visualEffects.stream().noneMatch(effect -> effect instanceof TrailVisualEffect<?> trail && trail.getEntity().getId() == entity.getId())) {
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
		if (entity.isRemoved() && effect.getLength() <= 0) {
			shouldRemove = true;
		}
		entity.updateTrail(effect);
	}

	@Override
	public void render(MultiBufferSource source, PoseStack stack, float partialTicks) {
		boolean entityRemoved = entity.isRemoved();
		float x = (float) (entityRemoved ? entity.getX() : Mth.lerp(partialTicks, entity.xOld, entity.getX()));
		float y = (float) (entityRemoved ? entity.getY() : Mth.lerp(partialTicks, entity.yOld, entity.getY()));
		float z = (float) (entityRemoved ? entity.getZ() : Mth.lerp(partialTicks, entity.zOld, entity.getZ()));
		this.effect.prepareRender(new Vec3(x, y, z).add(0, entity.getBbHeight() / 2, 0), new Vec3(entity.getX(), entity.getY(), entity.getZ()).subtract(new Vec3(entity.xOld, entity.yOld, entity.zOld)), partialTicks);

		List<TrailEffect.TrailPoint> adjustedVertical = this.effect.getVerticalRenderPoints().stream().map(p -> entity.adjustPoint(p, true, partialTicks)).toList();
		this.effect.getVerticalRenderPoints().clear();
		this.effect.getVerticalRenderPoints().addAll(adjustedVertical);
		List<TrailEffect.TrailPoint> adjustedHorizontal = this.effect.getHorizontalRenderPoints().stream().map(p -> entity.adjustPoint(p, false, partialTicks)).toList();
		this.effect.getHorizontalRenderPoints().clear();
		this.effect.getHorizontalRenderPoints().addAll(adjustedHorizontal);

		this.effect.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(entity.getTrailRenderType()), stack, true, entity.getTrailColor().x, entity.getTrailColor().y, entity.getTrailColor().z, entity.getTrailColor().w, entity.isTrailFullBright() ? ClientHandlers.FULL_BRIGHT : Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(entity, partialTicks));
		if (entity.shouldRenderHorizontal()) {
			this.effect.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(entity.getTrailRenderType()), stack, false, entity.getTrailColor().x, entity.getTrailColor().y, entity.getTrailColor().z, entity.getTrailColor().w, entity.isTrailFullBright() ? ClientHandlers.FULL_BRIGHT : Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(entity, partialTicks));
		}
	}

	@Override
	public boolean shouldRemove() {
		return shouldRemove;
	}
}
