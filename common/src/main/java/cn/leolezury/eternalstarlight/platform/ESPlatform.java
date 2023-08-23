package cn.leolezury.eternalstarlight.platform;

import net.minecraft.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface ESPlatform {
    ESPlatform INSTANCE = Util.make(() -> {
        final ServiceLoader<ESPlatform> loader = ServiceLoader.load(ESPlatform.class);
        final Iterator<ESPlatform> it = loader.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Platform instance not found!");
        } else {
            final ESPlatform platform = it.next();
            if (it.hasNext()) {
                throw new RuntimeException("More than one platform instance was found!");
            }
            return platform;
        }
    });

    enum Loader {
        FORGE,
        FABRIC,
        QUILT
    }

    Loader getLoader();

    FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties);
}
