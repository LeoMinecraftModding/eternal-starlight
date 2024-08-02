package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.vfx.VfxInstance;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record VfxPacket(VfxInstance instance) implements CustomPacketPayload {
	public static final Type<VfxPacket> TYPE = new Type<>(EternalStarlight.id("vfx"));

	public static final Codec<VfxPacket> CODEC = VfxInstance.CODEC.xmap(VfxPacket::new, VfxPacket::instance);

	public static final StreamCodec<RegistryFriendlyByteBuf, VfxPacket> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

	public static void handle(VfxPacket packet, Player player) {
		CompoundTag data = packet.instance().data();
		packet.instance().type().ifPresent(t -> t.spawnOnClient(data));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
