package cn.leolezury.eternalstarlight.common.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public record ParticleFacing(boolean cameraFacing, Quaternionf rotation) {
	public static final ParticleFacing CAMERA = new ParticleFacing(true, new Quaternionf());

	public static ParticleFacing fixed(Quaternionf rotation) {
		return new ParticleFacing(false, new Quaternionf(rotation));
	}

	public static ParticleFacing fromNormal(Vec3 normal) {
		Vec3 normalized = normal.normalize();
		return fixed(new Quaternionf().rotateTo(new Vector3f(0, 0, 1), new Vector3f((float) normalized.x, (float) normalized.y, (float) normalized.z)));
	}

	public Quaternionf getRotation() {
		return new Quaternionf(rotation);
	}

	public static final Codec<ParticleFacing> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.BOOL.optionalFieldOf("camera_facing", true).forGetter(ParticleFacing::cameraFacing),
		ExtraCodecs.QUATERNIONF.optionalFieldOf("rotation", new Quaternionf()).forGetter(ParticleFacing::rotation)
	).apply(instance, ParticleFacing::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, ParticleFacing> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.BOOL, ParticleFacing::cameraFacing,
		ByteBufCodecs.QUATERNIONF, ParticleFacing::rotation,
		ParticleFacing::new
	);
}
