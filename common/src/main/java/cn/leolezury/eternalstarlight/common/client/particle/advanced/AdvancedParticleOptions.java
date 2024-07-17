package cn.leolezury.eternalstarlight.common.client.particle.advanced;

import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class AdvancedParticleOptions {
	public static final RandomSource RANDOM = RandomSource.create();
	private RenderType renderType = ESRenderType.GLOW_PARTICLE;
	private SmoothSegmentedValue xSpeed;
	private SmoothSegmentedValue ySpeed;
	private SmoothSegmentedValue zSpeed;
	private SmoothSegmentedValue quadSize;
	private int lifetime;
	private SmoothSegmentedValue red;
	private SmoothSegmentedValue green;
	private SmoothSegmentedValue blue;
	private SmoothSegmentedValue alpha;
	private final List<Consumer<ParticleOperator>> spawnOperators = new ArrayList<>();
	private final List<Consumer<ParticleOperator>> tickOperators = new ArrayList<>();
	private final List<Consumer<ParticleOperator>> renderOperators = new ArrayList<>();
	private final List<Consumer<ParticleOperator>> removeOperators = new ArrayList<>();

	public AdvancedParticleOptions renderType(RenderType type) {
		this.renderType = type;
		return this;
	}

	public RenderType getRenderType() {
		return renderType;
	}

	public AdvancedParticleOptions speed(SmoothSegmentedValue x, SmoothSegmentedValue y, SmoothSegmentedValue z) {
		this.xSpeed = x;
		this.ySpeed = y;
		this.zSpeed = z;
		return this;
	}

	public Vec3 getSpeed(float progress) {
		return new Vec3(xSpeed.calculate(progress), ySpeed.calculate(progress), zSpeed.calculate(progress));
	}

	public AdvancedParticleOptions quadSize(SmoothSegmentedValue quadSize) {
		this.quadSize = quadSize;
		return this;
	}

	public float getQuadSize(float progress) {
		return quadSize.calculate(progress);
	}

	public AdvancedParticleOptions lifetime(int lifetime) {
		this.lifetime = lifetime;
		return this;
	}

	public AdvancedParticleOptions color(SmoothSegmentedValue r, SmoothSegmentedValue g, SmoothSegmentedValue b) {
		this.red = r;
		this.green = g;
		this.blue = b;
		return this;
	}

	public AdvancedParticleOptions alpha(SmoothSegmentedValue alpha) {
		this.alpha = alpha;
		return this;
	}

	public AdvancedParticleOptions color(SmoothSegmentedValue r, SmoothSegmentedValue g, SmoothSegmentedValue b, SmoothSegmentedValue a) {
		color(r, g, b);
		alpha(a);
		return this;
	}

	public Vector4f getColor(float progress) {
		return new Vector4f(red.calculate(progress), green.calculate(progress), blue.calculate(progress), alpha.calculate(progress));
	}

	public AdvancedParticleOptions spawnOperator(Consumer<ParticleOperator> operator) {
		spawnOperators.add(operator);
		return this;
	}

	public AdvancedParticleOptions tickOperator(Consumer<ParticleOperator> operator) {
		tickOperators.add(operator);
		return this;
	}

	public AdvancedParticleOptions renderOperator(Consumer<ParticleOperator> operator) {
		renderOperators.add(operator);
		return this;
	}

	public AdvancedParticleOptions removeOperator(Consumer<ParticleOperator> operator) {
		removeOperators.add(operator);
		return this;
	}

	public AdvancedParticleOptions defaultOperators() {
		spawnOperator(o -> o.setSpeed(getSpeed(0)));
		spawnOperator(o -> o.setQuadSize(getQuadSize(0)));
		spawnOperator(o -> o.setColor(getColor(0)));
		spawnOperator(o -> o.setLifetime(lifetime));
		tickOperator(o -> o.setSpeed(getSpeed(o.getAge() / o.getLifetime())));
		renderOperator(o -> o.setQuadSize(getQuadSize(o.getAge() / o.getLifetime())));
		renderOperator(o -> o.setColor(getColor(o.getAge() / o.getLifetime())));
		return this;
	}

	public void spawn(ResourceLocation location, float x, float y, float z) {
		spawn(AdvancedParticle::new, location, x, y, z);
	}

	public void spawn(ParticleSpawner spawner, ResourceLocation location, float x, float y, float z) {
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		ParticleStatus particleStatus = Minecraft.getInstance().options.particles().get();
		if (particleStatus == ParticleStatus.DECREASED && RANDOM.nextInt(3) == 0) {
			particleStatus = ParticleStatus.MINIMAL;
		}
		if (camera.getPosition().distanceToSqr(x, y, z) <= 1024.0 && particleStatus != ParticleStatus.MINIMAL) {
			Particle particle = spawner.spawn(Minecraft.getInstance().level, x, y, z, Minecraft.getInstance().particleEngine.spriteSets.get(location), this);
			Minecraft.getInstance().particleEngine.add(particle);
		}
	}

	public void operateSpawn(ParticleOperator operator) {
		for (Consumer<ParticleOperator> consumer : spawnOperators) {
			consumer.accept(operator);
		}
	}

	public void operateTick(ParticleOperator operator) {
		for (Consumer<ParticleOperator> consumer : tickOperators) {
			consumer.accept(operator);
		}
	}

	public void operateRender(ParticleOperator operator) {
		for (Consumer<ParticleOperator> consumer : renderOperators) {
			consumer.accept(operator);
		}
	}

	public void operateRemove(ParticleOperator operator) {
		for (Consumer<ParticleOperator> consumer : removeOperators) {
			consumer.accept(operator);
		}
	}
}
