package cn.leolezury.eternalstarlight.common.crest;

import cn.leolezury.eternalstarlight.common.init.SpellInit;
import cn.leolezury.eternalstarlight.common.spell.AbstractSpell;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.Optional;

public record Crest(CrestElement element, ResourceLocation texture, Optional<AbstractSpell> spell) {
    public static final Codec<Crest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CrestElement.CODEC.fieldOf("element").forGetter(Crest::element),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(Crest::texture),
            SpellInit.CODEC.optionalFieldOf("spell").forGetter(Crest::spell)
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
}