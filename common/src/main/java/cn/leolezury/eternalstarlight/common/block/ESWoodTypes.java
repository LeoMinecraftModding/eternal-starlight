package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ESWoodTypes {
    public static final BlockSetType LUNAR_SET = new BlockSetType(EternalStarlight.MOD_ID + ":lunar");
    public static final BlockSetType NORTHLAND_SET = new BlockSetType(EternalStarlight.MOD_ID + ":northland");
    public static final BlockSetType STARLIGHT_MANGROVE_SET = new BlockSetType(EternalStarlight.MOD_ID + ":starlight_mangrove");
    public static final BlockSetType SCARLET_SET = new BlockSetType(EternalStarlight.MOD_ID + ":scarlet");
    public static WoodType LUNAR = WoodType.register(new WoodType(EternalStarlight.MOD_ID + ":lunar", LUNAR_SET));
    public static WoodType NORTHLAND = WoodType.register(new WoodType(EternalStarlight.MOD_ID + ":northland", NORTHLAND_SET));
    public static WoodType STARLIGHT_MANGROVE = WoodType.register(new WoodType(EternalStarlight.MOD_ID + ":starlight_mangrove", STARLIGHT_MANGROVE_SET));
    public static WoodType SCARLET = WoodType.register(new WoodType(EternalStarlight.MOD_ID + ":scarlet", SCARLET_SET));
}
