package cn.leolezury.eternalstarlight.init;

import cn.leolezury.eternalstarlight.EternalStarlight;
import cn.leolezury.eternalstarlight.world.feature.tree.TrunkBerriesDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TreeDecoratorInit {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, EternalStarlight.MOD_ID);

    public static final RegistryObject<TreeDecoratorType<TrunkBerriesDecorator>> TRUNK_BERRIES = TREE_DECORATORS.register("trunk_berries", () -> new TreeDecoratorType<>(TrunkBerriesDecorator.CODEC));
}
