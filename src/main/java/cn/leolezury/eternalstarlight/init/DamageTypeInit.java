package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
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

public class DamageTypeInit {
    public static final ResourceKey<DamageType> LASER = create("laser");
    public static final ResourceKey<DamageType> GROUND_SHAKE = create("ground_shake");
    public static final ResourceKey<DamageType> FIRE_COLUMN = create("fire_column");
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
        context.register(LASER, new DamageType(slDamage("laser"), 0.0F, DamageEffects.BURNING));
        context.register(GROUND_SHAKE, new DamageType(slDamage("ground_shake"), 0.0F));
        context.register(FIRE_COLUMN, new DamageType(slDamage("fire_column"), 0.0F, DamageEffects.BURNING));
        context.register(POISON, new DamageType(slDamage("poison"), 0.0F));
        context.register(BITE, new DamageType(slDamage("bite"), 0.0F));
    }

    public static String slDamage(String source) {
        return EternalStarlight.MOD_ID + "." + source;
    }
}