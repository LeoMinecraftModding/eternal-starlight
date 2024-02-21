package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
    public static final ResourceKey<DamageType> GROUND_SHAKE = create("ground_shake");
    public static final ResourceKey<DamageType> SHATTERED_BLADE = create("shattered_blade");

    // starlight golem
    public static final ResourceKey<DamageType> LASER = create("laser");
    public static final ResourceKey<DamageType> ENERGIZED_FLAME = create("energized_flame");

    // lunar monstrosity
    public static final ResourceKey<DamageType> POISON = create("poison");
    public static final ResourceKey<DamageType> BITE = create("bite");


    public static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(EternalStarlight.MOD_ID, name));
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

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(ETHER, new DamageType(esDamage("ether"), 0.0F));
        context.register(METEOR, new DamageType(esDamage("meteor"), 0.0F));
        context.register(CRYSTALLINE_INFECTION, new DamageType(esDamage("crystalline_infection"), 0.0F));
        context.register(GROUND_SHAKE, new DamageType(esDamage("ground_shake"), 0.0F));
        context.register(SHATTERED_BLADE, new DamageType(esDamage("shattered_blade"), 0.0F));
        context.register(LASER, new DamageType(esDamage("laser"), 0.0F, DamageEffects.BURNING));
        context.register(ENERGIZED_FLAME, new DamageType(esDamage("energized_flame"), 0.0F, DamageEffects.BURNING));
        context.register(POISON, new DamageType(esDamage("poison"), 0.0F));
        context.register(BITE, new DamageType(esDamage("bite"), 0.0F));
    }

    public static String esDamage(String source) {
        return EternalStarlight.MOD_ID + "." + source;
    }
}