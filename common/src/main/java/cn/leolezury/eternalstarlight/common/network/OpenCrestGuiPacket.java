package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record OpenCrestGuiPacket(List<Crest.Instance> crests, List<Crest.Instance> ownedCrests) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<OpenCrestGuiPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("open_crest_gui"));

	public static final StreamCodec<RegistryFriendlyByteBuf, OpenCrestGuiPacket> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.collection(ArrayList::new, Crest.Instance.STREAM_CODEC), OpenCrestGuiPacket::crests,
		ByteBufCodecs.collection(ArrayList::new, Crest.Instance.STREAM_CODEC), OpenCrestGuiPacket::ownedCrests,
		OpenCrestGuiPacket::new
	);

	public static void handle(OpenCrestGuiPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenCrestGui(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}