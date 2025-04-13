package cn.leolezury.eternalstarlight.fabric.mixin;

import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Mob.class)
public abstract class MobMixin {
	@ModifyVariable(method = "setTarget", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, argsOnly = true)
	public LivingEntity setTarget(LivingEntity target) {
		return CommonHandlers.onLivingChangeTarget((LivingEntity) (Object) this, target);
	}
}
