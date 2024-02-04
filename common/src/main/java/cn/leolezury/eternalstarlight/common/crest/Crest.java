package cn.leolezury.eternalstarlight.common.crest;

import cn.leolezury.eternalstarlight.common.init.SpellInit;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record Crest(CrestElement element, ResourceLocation texture, Optional<AbstractSpell> spell, Optional<List<MobEffectWithLevel>> effect) {
    public Crest(CrestElement element, ResourceLocation texture, Optional<AbstractSpell> spell, MobEffectWithLevel... effects) {
        this(element, texture, spell, Optional.of(new ArrayList<>(List.of(effects))));
    }

    public static final Codec<Crest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CrestElement.CODEC.fieldOf("element").forGetter(Crest::element),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(Crest::texture),
            SpellInit.CODEC.optionalFieldOf("spell").forGetter(Crest::spell),
            MobEffectWithLevel.CODEC.listOf().optionalFieldOf("effect").forGetter(Crest::effect)
    ).apply(instance, Crest::new));

    public enum CrestElement implements StringRepresentable {
        TERRA("terra"),
        WIND("wind"),
        WATER("water"),
        LUNAR("lunar"),
        BLAZE("blaze"),
        LIGHT("light");

        public static final Codec<CrestElement> CODEC = StringRepresentable.fromEnum(CrestElement::values);
        private final String name;

        CrestElement(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public record MobEffectWithLevel(Integer level, MobEffect effect) {
        public static final Codec<MobEffectWithLevel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("level").forGetter(MobEffectWithLevel::level),
                BuiltInRegistries.MOB_EFFECT.byNameCodec().fieldOf("effects").forGetter(MobEffectWithLevel::effect)
        ).apply(instance, MobEffectWithLevel::new));
    }
}