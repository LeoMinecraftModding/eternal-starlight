package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record OpenCrestGuiPacket(Crest.Set crests, Crest.Set ownedCrests) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<OpenCrestGuiPacket> TYPE = new CustomPacketPayload.Type<>(EternalStarlight.id("open_crest_gui"));

	public static final Codec<OpenCrestGuiPacket> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Crest.Set.CODEC.fieldOf("crests").forGetter(OpenCrestGuiPacket::crests),
		Crest.Set.CODEC.fieldOf("owned_crests").forGetter(OpenCrestGuiPacket::ownedCrests)
	).apply(instance, OpenCrestGuiPacket::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, OpenCrestGuiPacket> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

	public static void handle(OpenCrestGuiPacket packet, Player player) {
		ESMiscUtil.runWhenOnClient(() -> () -> EternalStarlight.getClientHelper().handleOpenCrestGui(packet));
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}