package java.cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.platform.ESPlatform;
import com.google.auto.service.AutoService;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class FabricPlatform implements ESPlatform {
    @Override
    public Loader getLoader() {
        return Loader.FABRIC;
    }

    @Override
    public FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(flower.get(), properties);
    }
}
