package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record UpdateCrestsPacket(Crest.Set crests) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<UpdateCrestsPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("update_crests"));

	public static final Codec<UpdateCrestsPacket> CODEC = Crest.Set.CODEC.xmap(UpdateCrestsPacket::new, UpdateCrestsPacket::crests);

	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateCrestsPacket> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

	public static void handle(UpdateCrestsPacket packet, Player player) {
		if (!player.level().isClientSide) {
			List<Crest.Instance> crestList = packet.crests().crests();
			List<Crest.Instance> ownedCrests = ESCrestUtil.getCrests(player, ESCrestUtil.OWNED_CRESTS).crests();
			if (crestList.stream().anyMatch(crest -> ownedCrests.stream().noneMatch(c -> c.crest().is(crest.crest())))) {
				return;
			}
			ESCrestUtil.setCrests(player, crestList);
		}
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}