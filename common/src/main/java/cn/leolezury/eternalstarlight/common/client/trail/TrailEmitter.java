package cn.leolezury.eternalstarlight.common.client.trail;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface TrailEmitter<T extends Entity> {
	Trail createTrail(T entity);

	void tick(T entity, Trail trail);

	default void frame(T entity, Trail trail, float partialTicks) {
	}

	@Nullable
	default TrailPoint getHeadPoint(T entity, float partialTicks) {
		Vec3 pos = entity.isRemoved() ? entity.position() : entity.getPosition(partialTicks);
		return TrailPoint.cameraFacing(pos.add(0, entity.getBbHeight() / 2, 0));
	}

	default int getLight(T entity, float partialTicks) {
		return Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(entity, partialTicks);
	}

	RenderType getRenderType(T entity, Trail trail);

	default boolean isSolid(T entity, Trail trail) {
		return false;
	}

	default boolean shouldRemove(T entity, Trail trail) {
		return entity.isRemoved() && trail.getLength() <= 0;
	}
}
