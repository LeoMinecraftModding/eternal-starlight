package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class ESSynchedEntityData {
    public static final StreamCodec<RegistryFriendlyByteBuf, SynchedData> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, SynchedData>() {
        @Override
        public void encode(RegistryFriendlyByteBuf friendlyByteBuf, SynchedData object) {
            friendlyByteBuf.writeById(ESSpells.SPELLS.registry()::getId, object.spell());
            friendlyByteBuf.writeInt(object.castTicks());
            friendlyByteBuf.writeBoolean(object.hasSpell());
        }

        @Override
        public SynchedData decode(RegistryFriendlyByteBuf friendlyByteBuf) {
            boolean hasSpell = friendlyByteBuf.readBoolean();
            AbstractSpell spell = friendlyByteBuf.readById(value -> ESSpells.SPELLS.registry().byId(value));
            int ticks = friendlyByteBuf.readInt();
            return new SynchedData(hasSpell, spell, ticks);
        }
    };

    public static final EntityDataSerializer<SynchedData> SYNCHED_DATA_SERIALIZER = new EntityDataSerializer<>() {
        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, SynchedData> codec() {
            return STREAM_CODEC;
        }

        @Override
        public SynchedData copy(SynchedData object) {
            return object;
        }
    };

    public static void registerSerializer() {
        EntityDataSerializers.registerSerializer(SYNCHED_DATA_SERIALIZER);
    }

    // ALL synched stuff should go here
    public record SynchedData (boolean hasSpell, AbstractSpell spell, int castTicks) {
        public static SynchedData getDefault() {
            return new SynchedData(false, ESSpells.GUIDANCE_OF_STARS.get(), 0);
        }

        @Override
        public boolean hasSpell() {
            return hasSpell && spell() != null;
        }
    }
}
