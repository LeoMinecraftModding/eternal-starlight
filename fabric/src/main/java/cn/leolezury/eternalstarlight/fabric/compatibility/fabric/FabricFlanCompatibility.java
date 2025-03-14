package cn.leolezury.eternalstarlight.fabric.compatibility.fabric;

import com.google.auto.service.AutoService;

import cn.leolezury.eternalstarlight.common.compatibility.ESFlanCompatibility;
import io.github.flemmli97.flan.api.ClaimHandler;
import io.github.flemmli97.flan.api.permission.BuiltinPermission;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

@AutoService(ESFlanCompatibility.class)
public class FabricFlanCompatibility implements ESFlanCompatibility, FabricModCompatibility {

	@Override
	public boolean claimRestrictsBlockBreak(ServerLevel level, BlockPos pos, Entity entity) {
		return !ClaimHandler.canInteract(entity instanceof ServerPlayer player ? player : null, pos, BuiltinPermission.BREAK);
	}

	@Override
	public boolean claimRestrictsBlockPlace(ServerLevel level, BlockPos pos, Entity entity) {
		return !ClaimHandler.canInteract(entity instanceof ServerPlayer player ? player : null, pos, BuiltinPermission.PLACE);
	}

}
