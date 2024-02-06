package cn.leolezury.eternalstarlight.common.spell;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum ManaType implements StringRepresentable {
    EMPTY("empty"),
    TERRA("terra"),
    WIND("wind"),
    WATER("water"),
    LUNAR("lunar"),
    BLAZE("blaze"),
    LIGHT("light");

    public static final Codec<ManaType> CODEC = StringRepresentable.fromEnum(ManaType::values);
    private final String name;

    ManaType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
