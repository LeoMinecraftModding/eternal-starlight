package cn.leolezury.eternalstarlight.neoforge.datagen.provider;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class ESParticleDescriptionProvider extends ParticleDescriptionProvider {
	public ESParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
		super(output, fileHelper);
	}

	@Override
	protected void addDescriptions() {
		spriteSet(ESParticles.STARLIGHT.get(), loc("glitter"), 6, false);
		spriteSet(ESParticles.FIREFLY.get(), loc("firefly"), 5, false);
		spriteSet(ESParticles.SCARLET_LEAVES.get(), loc("scarlet_leaves"), 5, false);
		spriteSet(ESParticles.SHADEGRIEVE_LEAVES.get(), loc("shadegrieve_leaves"), 4, false);
		sprite(ESParticles.ENERGY.get(), loc("energy"));
		spriteSet(ESParticles.BLADE_SHOCKWAVE.get(), mcLoc("sweep"), 8, false);
		sprite(ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), loc("crystallized_moth_sonar"));
		sprite(ESParticles.AMARAMBER_FLAME.get(), loc("amaramber_flame"));
		spriteSet(ESParticles.EXPLOSION.get(), mcLoc("explosion"), 16, false);
		spriteSet(ESParticles.SMOKE.get(), loc("big_smoke"), 12, false);
		sprite(ESParticles.RING_EXPLOSION.get(), loc("ring"));
		sprite(ESParticles.GLOW.get(), loc("glow"));
		sprite(ESParticles.AETHERSENT_SMOKE.get(), loc("big_smoke_3"));
		sprite(ESParticles.ADVANCED_GLOW.get(), loc("glow"));
		sprite(ESParticles.SHINE.get(), loc("shine"));
	}

	private ResourceLocation loc(String s) {
		return EternalStarlight.id(s);
	}

	private ResourceLocation mcLoc(String s) {
		return ResourceLocation.withDefaultNamespace(s);
	}
}
