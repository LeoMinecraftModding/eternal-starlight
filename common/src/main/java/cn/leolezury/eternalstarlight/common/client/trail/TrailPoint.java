package cn.leolezury.eternalstarlight.common.client.trail;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

public final class TrailPoint {
	public enum Facing {
		// The ribbon width is tangent x (point - camera), so the ribbon plane contains the camera
		CAMERA_RIBBON,
		// The ribbon width follows the camera's up axis, projected perpendicular to the tangent
		CAMERA_BILLBOARD,
		// Two fixed points define the ribbon edges
		NONE
	}

	private final Vec3 pos;
	@Nullable
	private final Vec3 offset;
	private final Facing facing;
	private Vector4f color;
	private float u;
	private float progressFactor;

	TrailPoint(Vec3 pos, @Nullable Vec3 offset, Facing facing) {
		this.pos = pos;
		this.offset = offset;
		this.facing = facing;
		this.color = new Vector4f(1, 1, 1, 1);
		this.u = -1;
	}

	public static TrailPoint cameraFacing(Vec3 pos) {
		return new TrailPoint(pos, null, Facing.CAMERA_RIBBON);
	}

	public static TrailPoint cameraBillboard(Vec3 pos) {
		return new TrailPoint(pos, null, Facing.CAMERA_BILLBOARD);
	}

	public static TrailPoint fixed(Vec3 from, Vec3 to) {
		return new TrailPoint(from.add(to).scale(0.5), to.subtract(from).scale(0.5), Facing.NONE);
	}

	public TrailPoint color(Vector4f color) {
		this.color = new Vector4f(color);
		return this;
	}

	public TrailPoint color(float r, float g, float b, float a) {
		this.color = new Vector4f(r, g, b, a);
		return this;
	}

	// u < 0 means the texture coordinate is derived from the point's progress along the trail
	public TrailPoint uv(float u) {
		this.u = u;
		return this;
	}

	public Vec3 pos() {
		return pos;
	}

	@Nullable
	public Vec3 offset() {
		return offset;
	}

	public Facing facing() {
		return facing;
	}

	public Vector4f color() {
		return color;
	}

	public float u() {
		return u;
	}

	public float progressFactor() {
		return progressFactor;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

	public void setU(float u) {
		this.u = u;
	}

	public void setProgressFactor(float progressFactor) {
		this.progressFactor = progressFactor;
	}
}
