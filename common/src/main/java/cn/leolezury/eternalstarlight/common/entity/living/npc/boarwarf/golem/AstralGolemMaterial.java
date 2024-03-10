package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record AstralGolemMaterial (Item material,
                                   float attackDamageMultiplier, float defenseMultiplier,
                                   ResourceLocation texture, int tintColor) {
    public static final Codec<AstralGolemMaterial> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("material").forGetter(AstralGolemMaterial::material),
            Codec.FLOAT.fieldOf("attack_damage_multiplier").forGetter(AstralGolemMaterial::attackDamageMultiplier),
            Codec.FLOAT.fieldOf("defense_multiplier").forGetter(AstralGolemMaterial::defenseMultiplier),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(AstralGolemMaterial::texture),
            Codec.INT.fieldOf("tint_color").forGetter(AstralGolemMaterial::tintColor)
    ).apply(instance, AstralGolemMaterial::new));
}
