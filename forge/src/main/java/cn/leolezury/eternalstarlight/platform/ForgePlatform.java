package cn.leolezury.eternalstarlight.platform;

import com.google.auto.service.AutoService;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class ForgePlatform implements ESPlatform {
    @Override
    public Loader getLoader() {
        return Loader.FORGE;
    }

    @Override
    public FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(pot, flower, properties);
    }
}
