package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.network.VfxPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record VfxInstance(VfxType<?> type, ResourceKey<Level> dimension, Vec3 position, int duration, float radius, VfxData data) {
	public void send(ServerLevel level) {
		ESPlatform.INSTANCE.sendToAllClients(level, new VfxPacket(type.id(), data, dimension, position, duration, radius));
	}
}
