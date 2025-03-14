package cn.leolezury.eternalstarlight.fabric.compatibility.fabric;

import com.google.auto.service.AutoService;

import cn.leolezury.eternalstarlight.common.compatibility.ESFlanCompatibility;
import io.github.flemmli97.flan.api.ClaimHandler;
import io.github.flemmli97.flan.api.permission.BuiltinPermission;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

@AutoService(ESFlanCompatibility.class)
public class FabricFlanCompatibility implements ESFlanCompatibility, FabricModCompatibility {

	public static boolean canInteract(ServerLevel level, Entity entity, BlockPos pos, ResourceLocation permission) {
		return ClaimHandler.getPermissionStorage(level).getForPermissionCheck(pos).canInteract(entity instanceof ServerPlayer player ? player : null, permission, pos);
	}
	
	@Override
	public boolean claimRestrictsBlockBreak(ServerLevel level, BlockPos pos, Entity entity) {
		return !canInteract(level, entity, pos, BuiltinPermission.BREAK);
	}

	@Override
	public boolean claimRestrictsBlockPlace(ServerLevel level, BlockPos pos, Entity entity) {
		return !canInteract(level, entity, pos, BuiltinPermission.PLACE);
	}

}
