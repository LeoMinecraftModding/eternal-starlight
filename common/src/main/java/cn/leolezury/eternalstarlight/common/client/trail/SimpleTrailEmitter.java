package cn.leolezury.eternalstarlight.common.client.trail;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

import java.util.function.Function;

public class SimpleTrailEmitter<T extends Entity> implements TrailEmitter<T> {
	protected final float width;
	protected final float length;
	protected final Vector4f color;
	protected final float shrinkSpeed;
	protected final boolean fullBright;
	protected final boolean solid;
	protected final RenderType renderType;
	protected Function<T, Float> widthFunction;
	protected Function<T, Float> lengthFunction;

	public SimpleTrailEmitter(float width, float length, Vector4f color, float shrinkSpeed, boolean fullBright, boolean solid, RenderType renderType) {
		this.width = width;
		this.length = length;
		this.color = color;
		this.shrinkSpeed = shrinkSpeed;
		this.fullBright = fullBright;
		this.solid = solid;
		this.renderType = renderType;
		this.widthFunction = entity -> width;
		this.lengthFunction = entity -> length;
	}

	public SimpleTrailEmitter<T> width(Function<T, Float> widthFunction) {
		this.widthFunction = widthFunction;
		return this;
	}

	public SimpleTrailEmitter<T> length(Function<T, Float> lengthFunction) {
		this.lengthFunction = lengthFunction;
		return this;
	}

	@Override
	public Trail createTrail(T entity) {
		return new Trail(widthFunction.apply(entity), lengthFunction.apply(entity));
	}

	@Override
	public void tick(T entity, Trail trail) {
		Vec3 oldPos = new Vec3(entity.xOld, entity.yOld, entity.zOld);
		trail.update(TrailPoint.cameraFacing(oldPos.add(0, entity.getBbHeight() / 2, 0)).color(color));
		updateLength(entity, trail);
	}

	protected void updateLength(T entity, Trail trail) {
		if (entity.isRemoved()) {
			trail.setLength(Math.max(trail.getLength() - shrinkSpeed, 0));
		}
	}

	@Override
	public int getLight(T entity, float partialTicks) {
		return fullBright ? LightTexture.FULL_BRIGHT : TrailEmitter.super.getLight(entity, partialTicks);
	}

	@Override
	public RenderType getRenderType(T entity, Trail trail) {
		return renderType;
	}

	@Override
	public boolean isSolid(T entity, Trail trail) {
		return solid;
	}
}
