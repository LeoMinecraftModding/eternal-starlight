package cn.leolezury.eternalstarlight.common.network;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.interfaces.SpellCaster;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record UpdateSpellDataPacket(int casterId, SpellCastData data) implements CustomPacketPayload {
    public static final Type<UpdateSpellDataPacket> TYPE = new Type<>(EternalStarlight.id("update_spell_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateSpellDataPacket> STREAM_CODEC = StreamCodec.ofMember(UpdateSpellDataPacket::write, UpdateSpellDataPacket::read);

    public static UpdateSpellDataPacket read(RegistryFriendlyByteBuf buf) {
        int casterId = buf.readInt();
        SpellCastData data = SpellCastData.fromNetwork(buf);
        return new UpdateSpellDataPacket(casterId, data);
    }

    private static void write(UpdateSpellDataPacket packet, RegistryFriendlyByteBuf buf) {
        buf.writeInt(packet.casterId());
        packet.data().toNetwork(buf);
    }

    public static void handle(UpdateSpellDataPacket packet, Player player) {
        if (player != null && player.level().getEntity(packet.casterId()) instanceof SpellCaster caster) {
            caster.setSpellData(packet.data());
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
