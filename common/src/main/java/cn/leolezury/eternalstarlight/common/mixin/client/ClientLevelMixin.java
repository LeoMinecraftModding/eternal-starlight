package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
	@Shadow
	@Final
	private LevelRenderer levelRenderer;

	@Inject(method = "destroyBlockProgress", at = @At("RETURN"))
	private void destroyBlockProgress(int breakerId, BlockPos pos, int progress, CallbackInfo ci) {
		ClientLevel level = (ClientLevel) (Object) this;
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof AlloyFurnaceBlock) {
			Direction facing = state.getValue(AlloyFurnaceBlock.FACING);
			int x = state.getValue(AlloyFurnaceBlock.X_OFFSET);
			int y = state.getValue(AlloyFurnaceBlock.Y_OFFSET);
			int z = state.getValue(AlloyFurnaceBlock.Z_OFFSET) - 1;
			if (!(x == 0 && y == 0 && z == 0)) {
				Vec3 rotated = new Vec3(x, 0, z).yRot((((int) -facing.toYRot() + 90) % 360) * Mth.DEG_TO_RAD);
				int rotatedX = Math.round((float) rotated.x);
				int rotatedZ = Math.round((float) rotated.z);
				BlockPos centerPos = pos.offset(-rotatedX, -y, -rotatedZ);
				levelRenderer.destroyBlockProgress(breakerId, centerPos, progress);
			}
		}
	}
}
