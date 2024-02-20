package cn.leolezury.eternalstarlight.common.client.particle.lightning;


import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

import java.util.Locale;

public class LightningParticleOptions implements ParticleOptions {
    protected final Vector3f color;

    public Vector3f getColor() {
        return this.color;
    }

    public static final Codec<LightningParticleOptions> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ExtraCodecs.VECTOR3F.fieldOf("color").forGetter((options) -> {
            return options.color;
        })).apply(instance, LightningParticleOptions::new);
    });
    public static final ParticleOptions.Deserializer<LightningParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<LightningParticleOptions>() {
        public LightningParticleOptions fromCommand(ParticleType<LightningParticleOptions> type, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = LightningParticleOptions.readVector3f(reader);
            return new LightningParticleOptions(vector3f);
        }

        public LightningParticleOptions fromNetwork(ParticleType<LightningParticleOptions> type, FriendlyByteBuf buf) {
            return new LightningParticleOptions(LightningParticleOptions.readVector3f(buf));
        }
    };

    public LightningParticleOptions(Vector3f vector3f) {
        this.color = vector3f;
    }

    public ParticleType<LightningParticleOptions> getType() {
        return ESParticles.LIGHTNING.get();
    }

    public static Vector3f readVector3f(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        float f = reader.readFloat();
        reader.expect(' ');
        float f1 = reader.readFloat();
        reader.expect(' ');
        float f2 = reader.readFloat();
        return new Vector3f(f, f1, f2);
    }

    public static Vector3f readVector3f(FriendlyByteBuf buf) {
        return new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.color.x());
        buf.writeFloat(this.color.y());
        buf.writeFloat(this.color.z());
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.color.x(), this.color.y(), this.color.z());
    }
}
