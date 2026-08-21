package cn.leolezury.eternalstarlight.common.posteffect;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class PostEffectType<T extends PostEffectData> {
	public static final int MAX_EFFECTS = 64;

	public static final String UNIFORM_POSITIONS = "uEffectPositions";
	public static final String UNIFORM_RADII = "uEffectRadii";
	public static final String UNIFORM_INTENSITIES = "uEffectIntensities";
	public static final String UNIFORM_AGES = "uEffectAges";
	public static final String UNIFORM_DURATIONS = "uEffectDurations";
	public static final String UNIFORM_COUNT = "uEffectCount";

	private static final float[] POSITIONS = new float[MAX_EFFECTS * 3];
	private static final float[] RADII = new float[MAX_EFFECTS];
	private static final float[] INTENSITIES = new float[MAX_EFFECTS];
	private static final float[] AGES = new float[MAX_EFFECTS];
	private static final float[] DURATIONS = new float[MAX_EFFECTS];

	private final ResourceLocation id;
	private final MapCodec<T> codec;
	private final StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;
	private final ResourceLocation postChainLocation;

	protected PostEffectType(ResourceLocation id, MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
		this.id = id;
		this.codec = codec;
		this.streamCodec = streamCodec;
		this.postChainLocation = id.withPrefix("shaders/post/").withSuffix(".json");
	}

	public ResourceLocation id() {
		return id;
	}

	public MapCodec<T> codec() {
		return codec;
	}

	public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
		return streamCodec;
	}

	public ResourceLocation postChainLocation() {
		return postChainLocation;
	}

	@Nullable
	public EffectInstance getPostEffect(PostChain chain) {
		List<PostPass> passes = chain.passes;
		if (passes.isEmpty()) {
			return null;
		}
		return passes.getFirst().getEffect();
	}

	public final void uploadUniforms(EffectInstance effect, List<PostEffectInstance> instances, float partialTicks) {
		int count = Math.min(instances.size(), MAX_EFFECTS);
		for (int i = 0; i < count; i++) {
			PostEffectInstance instance = instances.get(i);
			Vec3 position = instance.getPosition();
			POSITIONS[i * 3] = (float) position.x;
			POSITIONS[i * 3 + 1] = (float) position.y;
			POSITIONS[i * 3 + 2] = (float) position.z;
			RADII[i] = instance.getRadius();
			INTENSITIES[i] = instance.getIntensity();
			AGES[i] = instance.getAge() + partialTicks;
			DURATIONS[i] = instance.getDuration();
		}
		setUniformArray(effect, UNIFORM_POSITIONS, POSITIONS);
		setUniformArray(effect, UNIFORM_RADII, RADII);
		setUniformArray(effect, UNIFORM_INTENSITIES, INTENSITIES);
		setUniformArray(effect, UNIFORM_AGES, AGES);
		setUniformArray(effect, UNIFORM_DURATIONS, DURATIONS);
		Uniform countUniform = effect.getUniform(UNIFORM_COUNT);
		if (countUniform != null) {
			countUniform.set(count);
		}
		uploadTypeUniforms(effect, instances, partialTicks);
	}

	protected abstract void uploadTypeUniforms(EffectInstance effect, List<PostEffectInstance> instances, float partialTicks);

	protected static void setUniform(EffectInstance effect, String name, float value) {
		Uniform uniform = effect.getUniform(name);
		if (uniform != null) {
			uniform.set(value);
		}
	}

	protected static void setUniformArray(EffectInstance effect, String name, float[] values) {
		Uniform uniform = effect.getUniform(name);
		if (uniform != null) {
			uniform.set(values);
		}
	}

	@SuppressWarnings("unchecked")
	protected T getData(PostEffectInstance instance) {
		return (T) instance.getData();
	}
}
