package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

public enum ManaType implements StringRepresentable {
    EMPTY("empty", 0xd16bbb),
    TERRA("terra", 0xa35a53),
    WIND("wind", 0xffffbc),
    WATER("water", 0xb8daeb),
    LUNAR("lunar", 0x33526a),
    BLAZE("blaze", 0xc54e55),
    LIGHT("light", 0x9295b0);

    public static final Codec<ManaType> CODEC = StringRepresentable.fromEnum(ManaType::values);
    private final String name;
    private final int color;

    ManaType(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public TagKey<Item> getCrystalsTag() {
        return this == EMPTY ? ESTags.Items.MANA_CRYSTALS : TagKey.create(Registries.ITEM, EternalStarlight.id(name + "_crystals"));
    }

    public int getColor() {
        return color;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
