package cn.leolezury.eternalstarlight.common.client.trail.emitter;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.trail.SimpleTrailEmitter;
import cn.leolezury.eternalstarlight.common.client.trail.Trail;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeper;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeperDashPhase;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeperJumpStartPhase;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeperJumpTransitionPhase;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector4f;

public class SolarCreeperTrailEmitter extends SimpleTrailEmitter<SolarCreeper> {
	public SolarCreeperTrailEmitter() {
		super(0.15f, 0, new Vector4f(1, 1, 1, 1), 0.5f, true, true, RenderType.entityCutoutNoCull(EternalStarlight.id("textures/entity/solar_creeper/solar_trail.png")));
	}

	@Override
	protected void updateLength(SolarCreeper entity, Trail trail) {
		int state = entity.getBehaviorState();
		if (entity.isRemoved() || entity.getDeltaMovement().length() < 0.01
			|| !(state == SolarCreeperJumpStartPhase.ID
			|| state == SolarCreeperJumpTransitionPhase.ID
			|| state == SolarCreeperDashPhase.ID)) {
			trail.setLength(Math.max(trail.getLength() - 0.5f, 0));
		} else {
			trail.setLength(Math.min(trail.getLength() + 2.5f, 8));
		}
	}
}
