package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ESDamageTypes {
	// misc
	public static final ResourceKey<DamageType> ETHER = create("ether");
	public static final ResourceKey<DamageType> METEOR = create("meteor");
	public static final ResourceKey<DamageType> CRYSTALLINE_INFECTION = create("crystalline_infection");
	public static final ResourceKey<DamageType> GROUND_SMASH = create("ground_smash");
	public static final ResourceKey<DamageType> SHATTERED_BLADE = create("shattered_blade");
	public static final ResourceKey<DamageType> SONAR = create("sonar");
	public static final ResourceKey<DamageType> DAGGER_OF_HUNGER = create("dagger_of_hunger");
	public static final ResourceKey<DamageType> SOUL_ABSORB = create("soul_absorb");

	// starlight golem
	public static final ResourceKey<DamageType> LASER = create("laser");
	public static final ResourceKey<DamageType> ENERGIZED_FLAME = create("energized_flame");

	// lunar monstrosity
	public static final ResourceKey<DamageType> POISON = create("poison");
	public static final ResourceKey<DamageType> BITE = create("bite");


	public static ResourceKey<DamageType> create(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, EternalStarlight.id(name));
	}

	public static DamageSource getDamageSource(Level level, ResourceKey<DamageType> type) {
		return getEntityDamageSource(level, type, null);
	}

	public static DamageSource getEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker) {
		return getIndirectEntityDamageSource(level, type, attacker, attacker);
	}

	public static DamageSource getIndirectEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker, @Nullable Entity indirectAttacker) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), attacker, indirectAttacker);
	}

	public static void bootstrap(BootstrapContext<DamageType> context) {
		context.register(ETHER, new DamageType(name("ether"), 0.1F));
		context.register(METEOR, new DamageType(name("meteor"), 0.1F));
		context.register(CRYSTALLINE_INFECTION, new DamageType(name("crystalline_infection"), 0.1F));
		context.register(GROUND_SMASH, new DamageType(name("ground_smash"), 0.1F));
		context.register(SHATTERED_BLADE, new DamageType(name("shattered_blade"), 0.1F));
		context.register(SONAR, new DamageType(name("sonar"), 0.1F));
		context.register(DAGGER_OF_HUNGER, new DamageType(name("dagger_of_hunger"), 0.1F));
		context.register(SOUL_ABSORB, new DamageType(name("soul_absorb"), 0.1F));
		context.register(LASER, new DamageType(name("laser"), 0.1F, DamageEffects.BURNING));
		context.register(ENERGIZED_FLAME, new DamageType(name("energized_flame"), 0.1F, DamageEffects.BURNING));
		context.register(POISON, new DamageType(name("poison"), 0.1F));
		context.register(BITE, new DamageType(name("bite"), 0.1F));
	}

	public static String name(String source) {
		return EternalStarlight.ID + "." + source;
	}
}