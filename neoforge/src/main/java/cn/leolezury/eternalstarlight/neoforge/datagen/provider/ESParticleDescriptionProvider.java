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
		spriteSet(ESParticles.STARDUST.get(), loc("stardust"), 2, false);
		spriteSet(ESParticles.FIREFLY.get(), loc("firefly"), 5, false);
		spriteSet(ESParticles.SCARLET_LEAVES.get(), loc("scarlet_leaves"), 5, false);
		spriteSet(ESParticles.SHADEGRIEVE_LEAVES.get(), loc("shadegrieve_leaves"), 4, false);
		spriteSet(ESParticles.SPIRAL_KELP_LEAVES.get(), loc("spiral_kelp_leaves"), 2, false);
		sprite(ESParticles.FALLING_RED_CRYSTAL_MOSS.get(), loc("falling_red_crystal_moss"));
		sprite(ESParticles.FALLING_BLUE_CRYSTAL_MOSS.get(), loc("falling_blue_crystal_moss"));
		sprite(ESParticles.ENERGY.get(), loc("energy"));
		spriteSet(ESParticles.BLADE_SHOCKWAVE.get(), mcLoc("sweep"), 8, false);
		sprite(ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), loc("crystallized_moth_sonar"));
		sprite(ESParticles.AMARAMBER_FLAME.get(), loc("amaramber_flame"));
		spriteSet(ESParticles.EXPLOSION.get(), mcLoc("explosion"), 16, false);
		spriteSet(ESParticles.SMOKE.get(), loc("big_smoke"), 12, false);
		sprite(ESParticles.RING_EXPLOSION.get(), loc("ring"));
		sprite(ESParticles.GLOW.get(), loc("glow"));
		sprite(ESParticles.AETHERSENT_SMOKE.get(), loc("big_smoke_3"));
		spriteSet(ESParticles.ASHEN_SNOW.get(), loc("ashen_snow"), 4, false);
		spriteSet(ESParticles.ORBITAL_ASHEN_SNOW.get(), loc("ashen_snow"), 4, false);
		sprite(ESParticles.EXPLOSION_SHOCK.get(), loc("explosion_shock"));
		spriteSet(ESParticles.TOWER_SQUID_INK.get(), mcLoc("generic"), 8, true);
		sprite(ESParticles.AMARAMBER_WAX_ON.get(), loc("amaramber_wax_on"));
		sprite(ESParticles.DRIPPING_MUD.get(), mcLoc("drip_hang"));
		sprite(ESParticles.FALLING_MUD.get(), mcLoc("drip_fall"));
		sprite(ESParticles.LANDING_MUD.get(), mcLoc("drip_land"));
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
