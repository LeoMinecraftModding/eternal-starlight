package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public abstract class ChunkSerializerMixin {
	@Inject(method = "write", at = @At(value = "RETURN"))
	private static void write(ServerLevel serverLevel, ChunkAccess chunkAccess, CallbackInfoReturnable<CompoundTag> cir) {
		cir.getReturnValue().putInt("eternal_starlight_version", EternalStarlight.VERSION);
	}
}
