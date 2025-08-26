package cn.leolezury.eternalstarlight.common.platform;

import cn.leolezury.eternalstarlight.common.network.SyncAttachmentsPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface EntityDataAttachment<T> {
	ResourceLocation id();

	boolean hasData(Entity entity);

	T getData(Entity entity);

	Optional<T> getExistingData(Entity entity);

	@Nullable T setDataUnsynced(Entity entity, T data);

	default @Nullable T setData(Entity entity, T data) {
		T previousValue = setDataUnsynced(entity, data);
		if (streamCodec() != null && entity.level() instanceof ServerLevel serverLevel && shouldSync(previousValue, data)) {
			ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, SyncAttachmentsPacket.create(entity, this, data, serverLevel.registryAccess()));
		}
		return previousValue;
	}

	@Nullable T removeDataUnsynced(Entity entity);

	default @Nullable T removeData(Entity entity) {
		T previousValue = removeDataUnsynced(entity);
		if (streamCodec() != null && entity.level() instanceof ServerLevel serverLevel && shouldSync(previousValue, null)) {
			ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, entity, SyncAttachmentsPacket.create(entity, this, null, serverLevel.registryAccess()));
		}
		return previousValue;
	}

	@Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec();

	boolean shouldSync(T v1, T v2);
}
