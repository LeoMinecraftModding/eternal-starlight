package cn.leolezury.eternalstarlight.forge.datagen.provider.custom;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class ESGeyserSmokingProvider extends GeyserSmokingProvider {
    public ESGeyserSmokingProvider(PackOutput output) {
        super(output, EternalStarlight.ID);
    }

    @Override
    public void addSmokingRecipes() {
        add(Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL);
        add(Items.WHITE_DYE, Items.BLACK_DYE);
        add(Items.NETHERRACK, Items.BLACKSTONE);
    }
}
