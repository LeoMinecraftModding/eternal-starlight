package cn.leolezury.eternalstarlight.common.client.renderer.entity.state;

import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.AbstractBoarwarfProfession;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.BoarwarfType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class BoarwarfRenderState extends LivingEntityRenderState {
	public AnimationState idleAnimationState = new AnimationState();
	public BoarwarfType type;
	public AbstractBoarwarfProfession profession;
}
