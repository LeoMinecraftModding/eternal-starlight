package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.spell.*;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;

import java.util.List;

public class ESSpells {
	public static final RegistrationProvider<AbstractSpell> SPELLS = RegistrationProvider.newRegistry(ResourceKey.createRegistryKey(EternalStarlight.id("spell")), EternalStarlight.ID);
	public static final Codec<AbstractSpell> CODEC = SPELLS.registry().byNameCodec();

	public static final RegistryObject<AbstractSpell, TeleportationSpell> TELEPORTATION = SPELLS.register("teleportation", () -> new TeleportationSpell(new AbstractSpell.Properties(List.of(ManaType.WIND, ManaType.LUNAR), 40, 20, 300)));
	public static final RegistryObject<AbstractSpell, GuidanceOfStarsSpell> GUIDANCE_OF_STARS = SPELLS.register("guidance_of_stars", () -> new GuidanceOfStarsSpell(new AbstractSpell.Properties(List.of(ManaType.TERRA, ManaType.WIND, ManaType.LUNAR), 30, 30, 200)));
	public static final RegistryObject<AbstractSpell, LaserBeamSpell> LASER_BEAM = SPELLS.register("laser_beam", () -> new LaserBeamSpell(new AbstractSpell.Properties(List.of(ManaType.BLAZE, ManaType.LIGHT), 30, 400, 250)));
	public static final RegistryObject<AbstractSpell, BurstSparkSpell> BURST_SPARK = SPELLS.register("burst_spell", () -> new BurstSparkSpell(new AbstractSpell.Properties(List.of(ManaType.BLAZE, ManaType.LIGHT), 30, 400, 250)));
	public static final RegistryObject<AbstractSpell, FlameAftershockSpell> FLAME_AFTERSHOCK = SPELLS.register("flame_aftershock", () -> new FlameAftershockSpell(new AbstractSpell.Properties(List.of(ManaType.BLAZE, ManaType.TERRA), 30, 400, 250)));
	public static final RegistryObject<AbstractSpell, FlameRingSpell> FLAME_RING = SPELLS.register("flame_ring", () -> new FlameRingSpell(new AbstractSpell.Properties(List.of(ManaType.BLAZE, ManaType.LIGHT), 30, 400, 250)));
	public static final RegistryObject<AbstractSpell, FlameArcSpell> FLAME_ARC = SPELLS.register("flame_arc", () -> new FlameArcSpell(new AbstractSpell.Properties(List.of(ManaType.BLAZE, ManaType.LIGHT), 30, 400, 250)));
	public static final RegistryObject<AbstractSpell, MergedFireballSpell> MERGED_FIREBALL = SPELLS.register("merged_fireball", () -> new MergedFireballSpell(new AbstractSpell.Properties(List.of(ManaType.BLAZE, ManaType.LIGHT), 30, 400, 250)));
	public static final RegistryObject<AbstractSpell, SurroundedFireballsSpell> SURROUNDED_FIREBALLS = SPELLS.register("surrounded_fireballs", () -> new SurroundedFireballsSpell(new AbstractSpell.Properties(List.of(ManaType.BLAZE, ManaType.LIGHT), 30, 400, 250)));
	public static final RegistryObject<AbstractSpell, FrozenFogSpell> FROZEN_FOG = SPELLS.register("frozen_fog", () -> new FrozenFogSpell(new AbstractSpell.Properties(List.of(ManaType.WATER, ManaType.LUNAR), 30, 400, 250)));
	public static final RegistryObject<AbstractSpell, IcySpikesSpell> ICY_SPIKES_SPELL = SPELLS.register("icy_spikes", () -> new IcySpikesSpell(new AbstractSpell.Properties(List.of(ManaType.WATER, ManaType.WIND), 30, 400, 250)));

	public static void loadClass() {
	}
}
