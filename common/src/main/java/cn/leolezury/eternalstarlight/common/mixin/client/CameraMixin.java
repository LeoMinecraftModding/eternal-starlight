package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public abstract class CameraMixin {
	@Shadow
	public abstract boolean isDetached();

	@Shadow
	public abstract void move(float f, float g, float h);

	@Shadow
	public abstract float getMaxZoom(float f);

	@Inject(method = "setup", at = @At(value = "RETURN"))
	private void setup(BlockGetter blockGetter, Entity entity, boolean bl, boolean bl2, float f, CallbackInfo ci) {
		if (isDetached() && Minecraft.getInstance().player instanceof SpellCaster caster && caster.getESSpellData().hasSpell()) {
			move(-getMaxZoom(2 * Minecraft.getInstance().player.getScale()), 1, 0);
		}
	}
}
