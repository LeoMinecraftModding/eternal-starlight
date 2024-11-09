package cn.leolezury.eternalstarlight.common.client.renderer.entity.state;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolemMaterial;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class AstralGolemRenderState extends HumanoidRenderState {
	public AstralGolemMaterial material;
	public float attackAnimationTick;
	public boolean isBlocking;
}
