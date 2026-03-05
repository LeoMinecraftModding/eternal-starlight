package cn.leolezury.eternalstarlight.neoforge.mixin.client;

import cn.leolezury.eternalstarlight.common.block.entity.*;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.IBlockEntityRendererExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IBlockEntityRendererExtension.class)
public interface IBlockEntityRendererExtensionMixin {
	@ModifyReturnValue(method = "getRenderBoundingBox", at = @At("RETURN"), remap = false)
	default AABB getRenderBoundingBox(AABB original, @Local(argsOnly = true) BlockEntity blockEntity) {
		if (blockEntity instanceof AlloyFurnaceBlockEntity || blockEntity instanceof SolarEggBlockEntity) {
			return original.inflate(3);
		}
		if (blockEntity instanceof EnergyTransmitterBlockEntity || blockEntity instanceof AbstractDuskLightBlockEntity || blockEntity instanceof EclipseCoreBlockEntity) {
			return original.inflate(64);
		}
		return original;
	}
}
