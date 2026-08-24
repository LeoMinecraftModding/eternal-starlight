package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record UpdateCrestsPacket(List<Crest.Instance> crests) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<UpdateCrestsPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("update_crests"));

	public static final StreamCodec<RegistryFriendlyByteBuf, UpdateCrestsPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.collection(ArrayList::new, Crest.Instance.STREAM_CODEC), UpdateCrestsPacket::crests,
		UpdateCrestsPacket::new
	);

	public static void handle(UpdateCrestsPacket packet, Player player) {
		if (!player.level().isClientSide) {
			List<Crest.Instance> crestList = packet.crests();
			List<Crest.Instance> ownedCrests = ESCrestUtil.getOwnedCrests(player);
			if (crestList.stream().anyMatch(crest -> {
				int level = crest.level();
				if (level < 1 || level > crest.crest().value().maxLevel()) {
					return true;
				}
				return ownedCrests.stream().filter(c -> c.crest().is(crest.crest())).noneMatch(c -> level <= c.level());
			})) {
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