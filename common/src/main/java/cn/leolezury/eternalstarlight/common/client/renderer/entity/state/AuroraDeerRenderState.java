package cn.leolezury.eternalstarlight.common.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class AuroraDeerRenderState extends LivingEntityRenderState {
	public AnimationState idleAnimationState = new AnimationState();
	public boolean hasLeftHorn, hasRightHorn;
}
