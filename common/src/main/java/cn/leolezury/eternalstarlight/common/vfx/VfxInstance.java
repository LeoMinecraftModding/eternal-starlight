package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.network.VfxPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.Optional;

public record VfxInstance(Optional<SyncedVfxType> type, CompoundTag data) {
	public static final Codec<VfxInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ResourceLocation.CODEC.xmap(VfxRegistry::get, t -> VfxRegistry.getKey(t.orElse(null))).fieldOf("type").forGetter(VfxInstance::type),
		CompoundTag.CODEC.fieldOf("data").forGetter(VfxInstance::data)
	).apply(instance, VfxInstance::new));

	public VfxInstance(SyncedVfxType type, CompoundTag data) {
		this(Optional.of(type), data);
	}

	public void send(ServerLevel level) {
		ESPlatform.INSTANCE.sendToAllClients(level, new VfxPacket(this));
	}
}
