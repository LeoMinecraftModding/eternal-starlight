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

public record OpenCrestGuiPacket(List<CrestInstance> ownedCrests, List<CrestInstance> crests) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<OpenCrestGuiPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("open_crest_gui"));
	public static final StreamCodec<RegistryFriendlyByteBuf, OpenCrestGuiPacket> STREAM_CODEC = StreamCodec.ofMember(OpenCrestGuiPacket::write, OpenCrestGuiPacket::read);

	public static OpenCrestGuiPacket read(FriendlyByteBuf buf) {
		int ownedSize = buf.readInt();
		List<CrestInstance> ownedCrestList = new ArrayList<>();
		for (int i = 0; i < ownedSize; i++) {
			ownedCrestList.add(new CrestInstance(buf.readUtf(384), buf.readInt()));
		}
		int size = buf.readInt();
		List<CrestInstance> crestList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			crestList.add(new CrestInstance(buf.readUtf(384), buf.readInt()));
		}
		return new OpenCrestGuiPacket(ownedCrestList, crestList);
	}

	public static void write(OpenCrestGuiPacket packet, FriendlyByteBuf buf) {
		buf.writeInt(packet.ownedCrests().size());
		for (CrestInstance instance : packet.ownedCrests()) {
			buf.writeUtf(instance.id(), 384);
			buf.writeInt(instance.level());
		}
		buf.writeInt(packet.crests().size());
		for (CrestInstance instance : packet.crests()) {
			buf.writeUtf(instance.id(), 384);
			buf.writeInt(instance.level());
		}
	}

	public record CrestInstance(String id, int level) {

	}

	public static void handle(OpenCrestGuiPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenCrestGui(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}