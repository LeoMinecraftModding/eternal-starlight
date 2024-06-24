package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public record OpenCrestGuiPacket(List<String> ownedCrests, List<String> crests) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<OpenCrestGuiPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("open_crest_gui"));
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenCrestGuiPacket> STREAM_CODEC = StreamCodec.ofMember(OpenCrestGuiPacket::write, OpenCrestGuiPacket::read);

	public static OpenCrestGuiPacket read(FriendlyByteBuf buf) {
		int ownedSize = buf.readInt();
		List<String> ownedCrestList = new ArrayList<>();
		for (int i = 0; i < ownedSize; i++) {
			ownedCrestList.add(buf.readUtf(384));
		}
		int size = buf.readInt();
		List<String> crestList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			crestList.add(buf.readUtf(384));
		}
		return new OpenCrestGuiPacket(ownedCrestList, crestList);
	}

	public static void write(OpenCrestGuiPacket packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.ownedCrests().size());
		for (String string : packet.ownedCrests()) {
			buf.writeUtf(string, 384);
		}
		buf.writeInt(packet.crests().size());
		for (String string : packet.crests()) {
			buf.writeUtf(string, 384);
		}
	}

	public static void handle(OpenCrestGuiPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenCrestGui(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}