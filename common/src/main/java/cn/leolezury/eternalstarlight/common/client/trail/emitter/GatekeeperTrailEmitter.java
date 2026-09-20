package cn.leolezury.eternalstarlight.common.client.trail.emitter;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.trail.Trail;
import cn.leolezury.eternalstarlight.common.client.trail.TrailEmitter;
import cn.leolezury.eternalstarlight.common.client.trail.TrailPoint;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.*;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.EasingCurve;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

public class GatekeeperTrailEmitter implements TrailEmitter<TheGatekeeper> {
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(EternalStarlight.id("textures/entity/blank.png"));
	private static final float SAMPLE_INTERVAL = 0.15f;
	private static final Vector4f NEAR_COLOR = new Vector4f(1, 1, 1, 1);
	private static final Vector4f FAR_COLOR = new Vector4f(101 / 255f, 125 / 255f, 201 / 255f, 1);

	private static final EasingCurve SWORD_LENGTH = EasingCurve.
		of(Easing.IDENTITY, 0, 0, 8)
		.add(Easing.IN_OUT_SINE, 0, 2, 5)
		.add(Easing.IN_OUT_SINE, 2, 0, 5)
		.add(Easing.IDENTITY, 0, 0, 4)
		.add(Easing.IN_OUT_SINE, 0, 2, 5)
		.add(Easing.IN_OUT_SINE, 2, 0, 5);

	private static final EasingCurve GREATSWORD_LENGTH = EasingCurve.
		of(Easing.IDENTITY, 0, 0, 10)
		.add(Easing.IN_OUT_SINE, 0, 3, 5)
		.add(Easing.IN_OUT_SINE, 3, 0, 5)
		.add(Easing.IDENTITY, 0, 0, 8)
		.add(Easing.IN_OUT_SINE, 0, 3, 5)
		.add(Easing.IN_OUT_SINE, 3, 0, 5);

	private static final EasingCurve GREATSWORD_COMBO_LENGTH = EasingCurve.
		of(Easing.IDENTITY, 0, 0, 17)
		.add(Easing.IN_OUT_SINE, 0, 3, 5)
		.add(Easing.IN_OUT_SINE, 3, 0, 5)
		.add(Easing.IDENTITY, 0, 0, 9)
		.add(Easing.IN_OUT_SINE, 0, 3, 5)
		.add(Easing.IN_OUT_SINE, 3, 0, 5)
		.add(Easing.IDENTITY, 0, 0, 11)
		.add(Easing.IN_OUT_SINE, 0, 3, 5)
		.add(Easing.IN_OUT_SINE, 3, 0, 5)
		.add(Easing.IDENTITY, 0, 0, 9)
		.add(Easing.IN_OUT_SINE, 0, 3, 5)
		.add(Easing.IN_OUT_SINE, 3, 0, 5);

	private static final EasingCurve HAMMER_LENGTH = EasingCurve.
		of(Easing.IDENTITY, 0, 0, 13)
		.add(Easing.IN_OUT_SINE, 0, 2.5f, 5)
		.add(Easing.IN_OUT_SINE, 2.5f, 0, 5);

	@Override
	public Trail createTrail(TheGatekeeper entity) {
		Trail trail = new Trail(0, 0);
		trail.setColorFunction(progress -> new Vector4f(FAR_COLOR).lerp(NEAR_COLOR, progress));
		return trail;
	}

	@Override
	public void tick(TheGatekeeper entity, Trail trail) {

	}

	@Override
	public void frame(TheGatekeeper entity, Trail trail, float partialTicks) {
		int state = entity.getBehaviorState();
		AnimationState animation = getAttackAnimation(entity);
		if (animation == null || !animation.isStarted()) {
			entity.lastBladeTrailState = state;
			trail.setLengthImmediate(0);
			trail.clear();
			return;
		}
		float tick = animation.getAccumulatedTime() / 50f;
		if (state != entity.lastBladeTrailState) {
			entity.lastBladeTrailState = state;
			entity.lastBladeTrailTick = tick - SAMPLE_INTERVAL;
			trail.clear();
			trail.setLengthImmediate(0);
		}
		EasingCurve curve = lengthCurve(state);
		float length = curve == null || tick <= 0 ? 0 : curve.calculate(tick);
		trail.setLengthImmediate(length);
		if (length <= 0) {
			trail.clear();
			return;
		}
		if (tick - entity.lastBladeTrailTick >= SAMPLE_INTERVAL) {
			TrailPoint bladePoint = currentBladePoint(entity);
			if (bladePoint != null) {
				trail.update(bladePoint);
				entity.lastBladeTrailTick = tick;
			}
		}
	}

	@Nullable
	private static TrailPoint currentBladePoint(TheGatekeeper entity) {
		Vec3 base = getHandPos(entity);
		Vec3 dirPos = getHandDirPos(entity);
		if (base == null || dirPos == null) {
			return null;
		}
		Vec3 direction = dirPos.subtract(base);
		if (direction.lengthSqr() <= 1e-6) {
			return null;
		}
		Vec3 handle = base.add(direction.normalize().scale(weaponHandleLength(entity)));
		Vec3 tip = base.add(direction.normalize().scale(weaponLength(entity)));
		return TrailPoint.fixed(handle, tip);
	}

	@Nullable
	private static AnimationState getAttackAnimation(TheGatekeeper entity) {
		return switch (entity.getBehaviorState()) {
			case GatekeeperSwordPhase.ID -> entity.swordAnimationState;
			case GatekeeperGreatswordPhase.ID -> entity.greatswordAnimationState;
			case GatekeeperGreatswordComboPhase.ID -> entity.greatswordComboAnimationState;
			case GatekeeperHammerPhase.ID -> entity.hammerAnimationState;
			default -> null;
		};
	}

	@Nullable
	@Override
	public TrailPoint getHeadPoint(TheGatekeeper entity, float partialTicks) {
		return currentBladePoint(entity);
	}

	@Override
	public int getLight(TheGatekeeper entity, float partialTicks) {
		return LightTexture.FULL_BRIGHT;
	}

	@Override
	public RenderType getRenderType(TheGatekeeper entity, Trail trail) {
		return RENDER_TYPE;
	}

	@Override
	public boolean isSolid(TheGatekeeper entity, Trail trail) {
		return true;
	}

	@Override
	public boolean shouldRemove(TheGatekeeper entity, Trail trail) {
		return entity.isRemoved();
	}

	@Nullable
	private static Vec3 getHandPos(TheGatekeeper entity) {
		return entity.getMainArm() == HumanoidArm.LEFT ? entity.leftHandPos : entity.rightHandPos;
	}

	@Nullable
	private static Vec3 getHandDirPos(TheGatekeeper entity) {
		return entity.getMainArm() == HumanoidArm.LEFT ? entity.leftHandDirPos : entity.rightHandDirPos;
	}

	private static float weaponHandleLength(TheGatekeeper entity) {
		Item item = entity.getMainHandItem().getItem();
		if (item == ESItems.GLISTERING_GREATSWORD.get()) {
			return 0.5f;
		}
		if (item == ESItems.GLISTERING_SWORD.get()) {
			return 0.3f;
		}
		if (item == ESItems.GLISTERING_MORNING_STAR.get() || item == Items.MACE) {
			return 0.5f;
		}
		return 1.0f;
	}

	private static float weaponLength(TheGatekeeper entity) {
		Item item = entity.getMainHandItem().getItem();
		if (item == ESItems.GLISTERING_GREATSWORD.get()) {
			return 1.8f;
		}
		if (item == ESItems.GLISTERING_SWORD.get()) {
			return 1.0f;
		}
		if (item == ESItems.GLISTERING_MORNING_STAR.get() || item == Items.MACE) {
			return 0.7f;
		}
		return 1.0f;
	}

	@Nullable
	private static EasingCurve lengthCurve(int state) {
		return switch (state) {
			case GatekeeperSwordPhase.ID -> SWORD_LENGTH;
			case GatekeeperGreatswordPhase.ID -> GREATSWORD_LENGTH;
			case GatekeeperGreatswordComboPhase.ID -> GREATSWORD_COMBO_LENGTH;
			case GatekeeperHammerPhase.ID -> HAMMER_LENGTH;
			default -> null;
		};
	}
}
