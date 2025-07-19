package cn.leolezury.eternalstarlight.common.platform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface EntityDataAttachment<T> {
	ResourceLocation id();

	boolean hasData(Entity entity);

	T getData(Entity entity);

	Optional<T> getExistingData(Entity entity);

	@Nullable T setData(Entity entity, T data);

	@Nullable T removeData(Entity entity);
}
