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

		this.effect.render(ClientHandlers.DELAYED_BUFFER_SOURCE.getBuffer(entity.getTrailRenderType()), stack, partialTicks, entity.getTrailColor().x, entity.getTrailColor().y, entity.getTrailColor().z, entity.getTrailColor().w, Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(entity, partialTicks));

		if (!entityRemoved && Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()) {
			this.effect.removeNearest();
		}
	}

	@Override
	public boolean shouldRemove() {
		return shouldRemove;
	}
}
