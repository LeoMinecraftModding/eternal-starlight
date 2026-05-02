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
		spriteSet(ESParticles.CRADLEWOOD_LEAVES.get(), loc("cradlewood_leaves"), 8, false);
		spriteSet(ESParticles.SHADEGRIEVE_LEAVES.get(), loc("shadegrieve_leaves"), 4, false);
		spriteSet(ESParticles.SPIRAL_KELP_LEAVES.get(), loc("spiral_kelp_leaves"), 2, false);
		sprite(ESParticles.FALLING_RED_CRYSTAL_MOSS.get(), loc("falling_red_crystal_moss"));
		sprite(ESParticles.FALLING_BLUE_CRYSTAL_MOSS.get(), loc("falling_blue_crystal_moss"));
		sprite(ESParticles.ENERGY.get(), loc("energy"));
		sprite(ESParticles.ELECTRIC_SPARK.get(), loc("electric_spark"));
		sprite(ESParticles.LUNAR_SLASH.get(), loc("electric_spark"));
		sprite(ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), loc("crystallized_moth_sonar"));
		sprite(ESParticles.AMARAMBER_FLAME.get(), loc("amaramber_flame"));
		spriteSet(ESParticles.EXPLOSION.get(), mcLoc("explosion"), 16, false);
		spriteSet(ESParticles.BLAST.get(), loc("blast"), 4, false);
		spriteSet(ESParticles.SMOKE.get(), loc("big_smoke"), 12, false);
		sprite(ESParticles.RING_EXPLOSION.get(), loc("ring"));
		sprite(ESParticles.RING.get(), loc("ring"));
		sprite(ESParticles.ORBITAL_TRAIL.get(), loc("trail"));
		sprite(ESParticles.METEOR.get(), loc("trail"));
		sprite(ESParticles.PARRY.get(), loc("parry_trail"));
		sprite(ESParticles.GATHERING_ENERGY.get(), loc("energy_trail"));
		sprite(ESParticles.GATHERING_SOUL.get(), loc("soul_trail"));
		sprite(ESParticles.GATHERING_FLARE.get(), loc("flare_trail"));
		sprite(ESParticles.GLOW.get(), loc("glow"));
		sprite(ESParticles.AETHERSENT_SMOKE.get(), loc("big_smoke_3"));
		spriteSet(ESParticles.ASHEN_SNOW.get(), loc("ashen_snow"), 4, false);
		spriteSet(ESParticles.ORBITAL_ASHEN_SNOW.get(), loc("ashen_snow"), 4, false);
		sprite(ESParticles.EXPLOSION_SHOCK.get(), loc("blank"));
		spriteSet(ESParticles.COLORED_INK.get(), mcLoc("generic"), 8, true);
		sprite(ESParticles.AMARAMBER_WAX_ON.get(), loc("amaramber_wax_on"));
		sprite(ESParticles.DRIPPING_MUD.get(), mcLoc("drip_hang"));
		sprite(ESParticles.FALLING_MUD.get(), mcLoc("drip_fall"));
		sprite(ESParticles.LANDING_MUD.get(), mcLoc("drip_land"));
		sprite(ESParticles.ALLIED.get(), loc("allied"));
		spriteSet(ESParticles.PUNGENCY_FRUIT_SMOKE.get(), mcLoc("generic"), 8, true);
		sprite(ESParticles.STARFIRE.get(), loc("starfire"));
		spriteSet(ESParticles.STARFIRE_EXPLOSION.get(), loc("starfire_explosion"), 5, false);
		spriteSet(ESParticles.STARFIRE_EXPLOSION_SMALL.get(), loc("starfire_explosion_small"), 6, false);
		spriteSet(ESParticles.SOUL_TRAIL.get(), loc("soul_trail"), 5, false);
		sprite(ESParticles.CANDLASH_TRAIL.get(), loc("candlash_trail"));
		sprite(ESParticles.ETHER_TRAIL.get(), loc("ether_trail"));
		spriteSet(ESParticles.GEYSER_BASE.get(), loc("geyser_base"), 8, false);
		spriteSet(ESParticles.GEYSER_POOF.get(), loc("geyser_poof"), 8, false);
		spriteSet(ESParticles.GEYSER_PLUME.get(), loc("geyser_plume"), 8, false);
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
