package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.registry.ESVfx;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public abstract class VfxType<T extends VfxData> {
	private final MapCodec<T> codec;
	private final StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;

	protected VfxType(MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
		this.codec = codec;
		this.streamCodec = streamCodec;
	}

	public ResourceLocation id() {
		return ESVfx.VFX.registry().getKey(this);
	}

	public MapCodec<T> codec() {
		return codec;
	}

	public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
		return streamCodec;
	}

	@SuppressWarnings("unchecked")
	protected T getData(VfxInstance instance) {
		return (T) instance.data();
	}

	public abstract void spawnOnClient(VfxInstance instance);
}
